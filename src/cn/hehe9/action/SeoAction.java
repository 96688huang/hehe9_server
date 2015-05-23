package cn.hehe9.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.common.utils.DateUtil;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.UrlEncodeUtil;
import cn.hehe9.model.SitemapItem;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicEpisode;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.CacheService;
import cn.hehe9.service.biz.ComicEpisodeService;
import cn.hehe9.service.biz.ComicService;
import cn.hehe9.service.biz.VideoEpisodeService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class SeoAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2907146293587266041L;

	private static final Logger logger = LoggerFactory.getLogger(SeoAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private VideoEpisodeService videoEpisodeService;

	@Resource
	private ComicService comicService;

	@Resource
	private ComicEpisodeService comicEpisodeService;

	@Resource
	private CacheService cacheService;

	private List<SitemapItem> siteMapVideoList;

	private List<SitemapItem> siteMapComicList;

	private static String dateTime;

	private InputStream inputStream;

	private static String[] prefixWordArr;

	private static final String SITEMAP_PAGE = PageUrlFlagEnum.SITEMAP_PAGE.getUrlFlag();

	static {
		String prefixWordStr = "很久以前,Long time ago,相传,传闻,多年前,传说中";
		prefixWordArr = prefixWordStr.split(",");
	}

	public String makeSiteMap() throws Exception {
		try {
			dateTime = dateTime == null ? DateUtil.formatDateNormal(new Date()) : dateTime;

			// check cache
			if (AppConfig.MEMCACHE_ENABLE) {
				siteMapVideoList = cacheService.getSitemapVideoCache();
				siteMapComicList = cacheService.getSitemapComicCache();
				if (!CollectionUtils.isEmpty(siteMapVideoList) && !CollectionUtils.isEmpty(siteMapComicList)) {
					logger.info("sitemap items found in cache. siteMapVideoList(size)={}, siteMapComicList(size)={}",
							siteMapVideoList.size(), siteMapComicList.size());
					return SITEMAP_PAGE;
				}
			}

			String basePath = "http://www.dmvcd.com/";
			VideoAction videoAction = new VideoAction();
			videoAction.setVideoService(videoService);
			videoAction.setComicService(comicService);
			videoAction.MAIN_HOT_VIDEOS_COUNT = 100;
			videoAction.initHotVideos();
			videoAction.initHotComics();

			siteMapVideoList = new ArrayList<SitemapItem>(videoAction.MAIN_HOT_VIDEOS_COUNT);
			siteMapComicList = new ArrayList<SitemapItem>(videoAction.MAIN_HOT_VIDEOS_COUNT);

			// 最新热门url
			for (List<Video> videoList : videoAction.getHotVideoListHolder()) {
				for (Video video : videoList) {
					List<VideoEpisode> videoEpisodeList = videoEpisodeService.list(video.getId(), 1, 3);
					if (CollectionUtils.isEmpty(videoEpisodeList)) {
						continue;
					}

					String name = video.getName();
					for (VideoEpisode ve : videoEpisodeList) {
						String url = basePath + "play_video/vid/" + video.getId() + "/eid/" + ve.getId() + "/eno/"
								+ ve.getEpisodeNo() + ".html";

						String title = "动漫VCD网-" + name + " 第" + ve.getEpisodeNo() + "集 "
								+ (StringUtils.isBlank(ve.getTitle()) ? "" : "「" + ve.getTitle() + "」");
						siteMapVideoList.add(new SitemapItem(url, title));
					}
				}
			}

			for (List<Comic> comicList : videoAction.getHotComicListHolder()) {
				for (Comic comic : comicList) {
					List<ComicEpisode> comicEpisodeList = comicEpisodeService.list(comic.getId(), 1, 3);
					if (CollectionUtils.isEmpty(comicEpisodeList)) {
						continue;
					}

					String name = comic.getName();
					for (ComicEpisode ce : comicEpisodeList) {
						String url = basePath + "read_comic/cid/" + comic.getId() + "/eid/" + ce.getId() + "/eno/"
								+ ce.getEpisodeNo() + ".html";

						String title = "动漫VCD网-" + name + " 第" + ce.getEpisodeNo() + "篇 "
								+ (StringUtils.isBlank(ce.getTitle()) ? "" : "「" + ce.getTitle() + "」");
						siteMapComicList.add(new SitemapItem(url, title));
					}
				}
			}

			// add to cache
			if (AppConfig.MEMCACHE_ENABLE) {
				dateTime = DateUtil.formatDateNormal(new Date());
				cacheService.createSitemapVideoCache(siteMapVideoList);
				cacheService.createSitemapComicCache(siteMapComicList);
				logger.info("sitemap items save to cache. siteMapVideoList(size)={}, siteMapComicList(size)={}",
						siteMapVideoList.size(), siteMapComicList.size());
			}
			return SITEMAP_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	private static String getRandomPrefixWord() {
		int index = new Random().nextInt(prefixWordArr.length - 1);
		return prefixWordArr[index] + ",";
	}

	public static void mixWords() throws Exception {
		String seoWordsFilePath = "E:/Winson/My_projects/Git/hehe9_server/env/seo_words.txt";
		BufferedReader reader = new BufferedReader(new FileReader(new File(seoWordsFilePath)));
		Map<String, String> wordMap = new HashMap<>(30000);
		String line = null;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			String[] wordArr = line.split(",");
			if (wordArr.length < 2) {
				continue;
			}
			String word_1 = wordArr[0];
			String word_2 = wordArr[1];

			wordMap.put(word_1, word_2);
			wordMap.put(word_2, word_1);
		}
		reader.close();

		String storyLine = "传奇海盗哥尔·D·罗杰在临死前曾留下关于其毕生的财富“One Piece”的消息，由此引得群雄并起，众海盗们为了这笔传说中的巨额财富展开争夺，各种势力、政权不断交替，整个世界进入了动荡混乱的“大海贼时代”。生长在东海某小村庄的路飞受到海贼香克斯的精神指引，决定成为一名出色的海盗。为了达成这个目标，并找到万众瞩目的One Piece，路飞踏上艰苦的旅程。一路上他遇到了无数磨难，也结识了索隆、娜美、乌索普、香吉、罗宾等一众性格各异的好友。他们携手一同展开充满传奇色彩的大冒险。 引发最后的奇迹！突破正义之门引发最后的奇迹！突破正义之门。 海军本部，营救艾斯之旅。 伙伴的行踪。 伙伴的行踪。 伙伴的行...";
		for (String key : wordMap.keySet()) {
			String value = wordMap.get(key);
			storyLine = storyLine.replaceAll(key, value);
		}
		String finalStroyLine = getRandomPrefixWord() + storyLine;
		String[] storyLineItemArr = finalStroyLine.split("。");
		StringBuilder buf = new StringBuilder(finalStroyLine.length() + 100);
		for (String item : storyLineItemArr) {
			buf.append(item + "。");
			if (item.length() > 100) {
				buf.append("(www.dmvcd.com) ");
			}
		}
		System.out.println(buf.toString());
	}

	public static void clean() throws Exception {
		String cleanFilePath = "E:/Winson/My_projects/Git/hehe9_server/env/seo_words.txt";
		String seoWordsFilePath = "E:/Winson/My_projects/Git/hehe9_server/env/seo_words_orign.txt";
		BufferedReader reader = new BufferedReader(new FileReader(new File(seoWordsFilePath)));
		String line = null;
		Set<String> set = new TreeSet<String>();
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			set.add(line);
		}
		reader.close();

		PrintWriter writer = new PrintWriter(new FileWriter(cleanFilePath));
		for (String item : set) {
			writer.println(item);
		}
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		//		Document doc = JsoupUtil.connect("http://www.dmvcd.com", 5000, 3, 3000, "");
		//		System.out.println(doc == null ? "null" : doc.toString());

		convertSitemapTxtToXml();
	}

	private static void convertSitemapTxtToXml() throws FileNotFoundException, IOException {
		String sitemapTxtPath = "E:/Winson/My_projects/Git/hehe9_server/sitemap/sitemap.txt";
		String sitemapXmlPath = "E:/Winson/My_projects/Git/hehe9_server/sitemap/sitemap.xml";
		BufferedReader reader = new BufferedReader(new FileReader(new File(sitemapTxtPath)));
		String line = null;
		List<String> urlList = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			urlList.add(line);
		}
		reader.close();

		PrintWriter writer = new PrintWriter(new FileWriter(new File(sitemapXmlPath)));
		writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		writer.println("<urlset>");
		for (int i = 0; i < urlList.size(); i++) {
			String url = urlList.get(i);
			StringBuilder buf = new StringBuilder(500);
			buf.append("<url>");
			buf.append("<loc>").append(url).append("</loc>");
			buf.append("<lastmod>").append(DateUtil.formatCurrentTime("yyyy-MM-dd")).append("</lastmod>");
			buf.append("<changefreq>daily</changefreq>");
			buf.append("<priority>0.5</priority>");
			buf.append("</url>");
			writer.println(buf.toString());
		}
		writer.println("</urlset>");
		writer.flush();
		writer.close();
		System.out.println("done");
	}

	public void makeSiteMapUrls() {
		try {
			PrintWriter siteMapItemsWriter = new PrintWriter(new FileWriter("E:/Winson/site_map_items.txt"));
			PrintWriter urllistWriter = new PrintWriter(new FileWriter("E:/Winson/url_list.txt"));

			String basePath = "http://www.dmvcd.com/";
			VideoAction videoAction = new VideoAction();
			videoAction.setVideoService(videoService);
			videoAction.setComicService(comicService);
			videoAction.COUNT_PER_FIRST_CHAR = Integer.MAX_VALUE;
			videoAction.MAIN_HOT_VIDEOS_COUNT = 100;

			videoAction.initHotVideos();
			videoAction.initHotComics();

			videoAction.initLetterVideos();
			videoAction.initLetterComics();

			// 最新热门url
			for (List<Video> videoList : videoAction.getHotVideoListHolder()) {
				for (Video video : videoList) {
					List<VideoEpisode> videoEpisodeList = videoEpisodeService.list(video.getId(), 1, 3);
					if (CollectionUtils.isEmpty(videoEpisodeList)) {
						continue;
					}

					String name = video.getName();
					for (VideoEpisode ve : videoEpisodeList) {
						String url = basePath + "play_video/vid/" + video.getId() + "/eid/" + ve.getId() + "/eno/"
								+ ve.getEpisodeNo() + ".html";

						String title = "动漫VCD网-" + name + " 第" + ve.getEpisodeNo() + "集 "
								+ (StringUtils.isBlank(ve.getTitle()) ? "" : "「" + ve.getTitle() + "」");
						String content = "<tr><td class=\"lpage\"><a href=\"" + url + "\" title=\"" + title + "\">"
								+ title + "</a></td></tr>";
						siteMapItemsWriter.println(content);
						urllistWriter.println(url);
					}
				}
			}

			for (List<Comic> comicList : videoAction.getHotComicListHolder()) {
				for (Comic comic : comicList) {
					List<ComicEpisode> comicEpisodeList = comicEpisodeService.list(comic.getId(), 1, 3);
					if (CollectionUtils.isEmpty(comicEpisodeList)) {
						continue;
					}

					String name = comic.getName();
					for (ComicEpisode ce : comicEpisodeList) {
						String url = basePath + "read_comic/cid/" + comic.getId() + "/eid/" + ce.getId() + "/eno/"
								+ ce.getEpisodeNo() + ".html";

						String title = "动漫VCD网-" + name + " 第" + ce.getEpisodeNo() + "篇 "
								+ (StringUtils.isBlank(ce.getTitle()) ? "" : "「" + ce.getTitle() + "」");
						String content = "<tr><td class=\"lpage\"><a href=\"" + url + "\" title=\"" + title + "\">"
								+ title + "</a></td></tr>";
						siteMapItemsWriter.println(content);
						urllistWriter.println(url);
					}
				}
			}

			// 大全url
			for (String key : videoAction.getLetterMenuVideoMap().keySet()) {
				Set<String> videoNameSet = videoAction.getLetterMenuVideoMap().get(key);
				for (String name : videoNameSet) {
					if (StringUtils.isBlank(name)) {
						continue;
					}

					String url = basePath + "search_videos/name/" + UrlEncodeUtil.base64Encode(name) + ".html";
					String title = "动漫VCD网-" + name + "|" + name + "动漫|" + name + "在线观看|" + name + "剧情分析|" + name
							+ "连载|" + name + "高清在线";
					String content = "<tr><td class=\"lpage\"><a href=\"" + url + "\" title=\"" + title + "\">" + title
							+ "</a></td></tr>";
					siteMapItemsWriter.println(content);
					urllistWriter.println(url);
				}
			}

			for (String key : videoAction.getLetterMenuComicMap().keySet()) {
				Set<String> comicNameSet = videoAction.getLetterMenuComicMap().get(key);
				for (String name : comicNameSet) {
					if (StringUtils.isBlank(name)) {
						continue;
					}

					String url = basePath + "search_comics/name/" + UrlEncodeUtil.base64Encode(name) + ".html";
					String title = "动漫VCD网-" + name + "|" + name + "漫画|" + name + "在线观看|" + name + "剧情研究|" + name
							+ "连载|" + name + "高清画质";
					String content = "<tr><td class=\"lpage\"><a href=\"" + url + "\" title=\"" + title + "\">" + title
							+ "</a></td></tr>";
					siteMapItemsWriter.println(content);
					urllistWriter.println(url);
				}
			}
			siteMapItemsWriter.flush();
			urllistWriter.flush();

			siteMapItemsWriter.close();
			urllistWriter.close();
			System.out.println("done");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public List<SitemapItem> getSiteMapVideoList() {
		return siteMapVideoList;
	}

	public void setSiteMapVideoList(List<SitemapItem> siteMapVideoList) {
		this.siteMapVideoList = siteMapVideoList;
	}

	public List<SitemapItem> getSiteMapComicList() {
		return siteMapComicList;
	}

	public void setSiteMapComicList(List<SitemapItem> siteMapComicList) {
		this.siteMapComicList = siteMapComicList;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
