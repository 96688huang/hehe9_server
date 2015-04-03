package cn.hehe9.service.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.mock.MockActionInvocation;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.utils.BeanUtil;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.common.utils.ListUtil;
import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.dao.VideoEpisodeDao;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.persistent.entity.VideoSource;

@Component
public class SohuCartoonCollectService {
	private static final Logger logger = LoggerFactory.getLogger(SohuCartoonCollectService.class);

	@Resource
	private VideoDao videoDao;

	@Resource
	private VideoEpisodeDao videoEpisodeDao;

	private static final String SOHU_CARTOON = ComConstant.LogPrefix.SOHU_CARTOON;

	public static void main(String[] args) throws IOException {
		String listPageUrl = "http://tv.sohu.com/s2013/onepiece/";
		Document doc = Jsoup.connect(listPageUrl).get();
		Elements pagecontDiv = doc.select(".pagecont");
		for (Element pagecontItem : pagecontDiv) {
			Elements listJs = pagecontItem.select(".listJs");
			for (Element listJsItem : listJs) {
				String bitText = listJsItem.select(".bti").text();
				String wzText = listJsItem.select(".wz").text();
				boolean isStoryLine = bitText.contains("剧情");
				if (isStoryLine) {
					String storyLine = wzText;
					System.out.println(storyLine);
					continue;
				}

				System.out.println("bitText-->" + bitText);
				String title = wzText;
				System.out.println("title-->" + title);
			}
		}
	}

	//	传奇海盗哥尔·D·罗杰在临死前曾留下关于其毕生的财富“One Piece”的消息，由此引得群雄并起，众海盗们为了这笔传说中的巨额财富展开争夺，各种势力、政权不断交替，整个世界进入了动荡混乱的“大海贼时代”。生长在东海某小村庄的路飞受到海贼香克斯的精神指引，决定成为一名出色的海盗。为了达成这个目标，并找到万众瞩目的One Piece，路飞踏上艰苦的旅程。一路上他遇到了无数磨难，也结识了索隆、娜美、乌索普、香吉、罗宾等一众性格各异的好友。他们携手一同展开充满传奇色彩的大冒险。
	//	bitText-->航海王第1集 观 看
	//	title-->引发最后的奇迹！突破正义之门引发最后的奇迹！突破正义之门。
	//	bitText-->航海王第2集 观 看
	//	title-->海军本部，营救艾斯之旅。
	//	bitText-->航海王第3集 观 看
	//	title-->伙伴的行踪。
	//	bitText-->航海王第4集 观 看
	//	title-->伙伴的行踪。
	//	bitText-->航海王第5集 观 看
	//	title-->伙伴的行踪。
	//	bitText-->航海王第6集 观 看
	//	title-->伙伴的行踪。
	//	bitText-->航海王第7集 观 看
	//	title-->海边总部临近回想物别篇，兄弟的誓言。
	//	bitText-->航海王第8集 观 看
	//	title-->海边总部临近回想物别篇，集结！三大将。
	//	bitText-->航海王第9集 观 看
	//	title-->决战临近，海军最强布阵完成。
	//	bitText-->航海王第10集 观 看
	//	title-->巨大舰队出现，袭来！白胡子海贼团。

	/** 用于比较Video的属性名称 */
	private static List<String> videoCompareFieldNames = new ArrayList<String>();
	private static List<String> episodeCompareFieldNames = new ArrayList<String>();
	static {
		// video fields
		videoCompareFieldNames.add("name");
		videoCompareFieldNames.add("playCountWeekly");
		videoCompareFieldNames.add("playCountTotal");
		videoCompareFieldNames.add("posterBigUrl");
		videoCompareFieldNames.add("posterMidUrl");
		videoCompareFieldNames.add("posterSmallUrl");
		videoCompareFieldNames.add("iconUrl");
		videoCompareFieldNames.add("listPageUrl");
		videoCompareFieldNames.add("updateRemark");

		// video episode fields
		episodeCompareFieldNames.add("title");
		episodeCompareFieldNames.add("play_page_url");
		episodeCompareFieldNames.add("snapshot_url");
		episodeCompareFieldNames.add("file_url");
	}

	public void collect(VideoSource vs) {
		collectAllCartoons(vs.getId(), vs.getCollectPageUrl(), vs.getRootUrl());
	}

	/**
	 * 解析所有动漫信息
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void collectAllCartoons(int sourceId, String collectPageUrl, String rootUrl) {
		try {
			// 是否已包含根域名
			collectPageUrl = collectPageUrl.contains(rootUrl) ? collectPageUrl : (rootUrl + (!collectPageUrl
					.startsWith("/") ? "/" + collectPageUrl : collectPageUrl));

			Document doc = Jsoup.connect(collectPageUrl).get();
			Elements liEle = doc.select("ul.st-list>li");
			// 分别解析每部动漫的信息
			for (Element liItem : liEle) {
				parseVideoInfo(sourceId, liItem);

				return; // TODO for testing
			}

			// 下一页
			String nextPage = doc.select("body div.ssPages>a[title=下一页]").attr("href");
			if (logger.isDebugEnabled()) {
				logger.debug("{}nextPage : ", SOHU_CARTOON, nextPage);
			}

			// 递归解析
			if (StringUtils.isNotEmpty(nextPage)) {
				collectAllCartoons(sourceId, nextPage, rootUrl);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private void parseVideoInfo(int sourceId, Element liItem) {
		try {
			Video video = new Video();
			video.setSourceId(sourceId);

			Elements st_picDiv = liItem.select("div.st-pic");

			// 分集列表页url, sk码, icon
			Elements items = st_picDiv.select("a[href]");
			String listPageUrl = items.attr("href");
			String sk = items.attr("_s_k");
			String iconUrl = items.select("img").attr("src");

			video.setListPageUrl(listPageUrl);
			video.setIconUrl(iconUrl);

			// update remark
			String updateRemark = st_picDiv.select("span.maskTx").html();
			video.setUpdateRemark(updateRemark);

			// play count per week
			String playCountWeekly = liItem.select("p.num-bf").html();
			video.setPlayCountWeekly(playCountWeekly);

			Elements list_hover_Div = liItem.select("div.list-hover");

			// name
			Element aEle = list_hover_Div.select("a[_s_k=" + sk + "]").first();
			String name = aEle.attr("title");
			if (StringUtils.isEmpty(name)) {
				name = aEle.html();
			}
			video.setName(name);

			// story line brief
			String brief = list_hover_Div.select("p.lh-info").html();
			video.setStoryLineBrief(brief);

			// play count total
			String playCountTotal = list_hover_Div.select("a.acount").first().html();
			video.setPlayCountTotal(playCountTotal);

			List<Video> list = videoDao.searchBriefByName(video.getName());
			if (list == null || list.isEmpty()) {
				videoDao.save(video);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", SOHU_CARTOON, JacksonUtil.encode(video));
				}
				return;
			}

			boolean isMatcheRecord = false;
			for (Video oldVideo : list) {
				// (多个名字时, 以英文逗号分隔)
				boolean contains = ListUtil.asList(StringUtils.deleteWhitespace(oldVideo.getName()).split(","))
						.contains(StringUtils.deleteWhitespace(video.getName()));
				if (contains) { // 名字相同, 则更新
					isMatcheRecord = true;
					// 比较关键字段是否有更新
					boolean isSame = BeanUtil.isFieldsValueSame(video, oldVideo, videoCompareFieldNames, null);
					if (!isSame) {
						video.setId(oldVideo.getId()); // id
						videoDao.udpate(video); // 不同则更新
						// for log
						video.setStoryLine(null);
						video.setStoryLineBrief(null);
						logger.info("{}update video : \r\n OLD : {}\r\n NEW : {}", new Object[] { SOHU_CARTOON,
								JacksonUtil.encode(oldVideo), JacksonUtil.encode(video) });
					}
				}
			}
			if (!isMatcheRecord) { // 如果找不到要更新的记录, 则新增
				videoDao.save(video);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add new video : {}", SOHU_CARTOON, JacksonUtil.encode(video));
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 解析播放页面的视频url
	 * 
	 * @param playUrl
	 * @return
	 * @throws IOException
	 */
	public String parseVideoSrc(String playUrl) throws IOException {
		Document doc = Jsoup.connect(playUrl).get();
		String videoSrc = doc.select("meta[property=og:videosrc]").attr("content");
		return videoSrc;
	}

	/**
	 * 解析某视频的播放url, 视频url, 第几集等信息
	 * 
	 * @param indexUrl
	 * @throws Exception
	 */
	public void collectEpisodeFromListPage(Video video) throws Exception {
		VideoEpisode ve = new VideoEpisode();
		ve.setVideoId(video.getId());

		Document doc = Jsoup.connect(video.getListPageUrl()).get();

		// 剧情, 分集标题
		String storyLineFromTitleList = null;
		Elements pagecontDiv = doc.select(".pagecont");
		//{ key : episodeNo, value : title }
		Map<Integer, String> titleMap = new HashMap<Integer, String>();
		for (Element pagecontItem : pagecontDiv) {
			Elements listJs = pagecontItem.select(".listJs");
			int i = 1;
			for (Element listJsItem : listJs) {
				String bitText = listJsItem.select(".bti").text();
				String wzText = listJsItem.select(".wz").text();
				boolean isStoryLine = bitText.contains("剧情");
				if (isStoryLine) {
					storyLineFromTitleList = wzText;
					continue;
				}
				if (bitText.contains("第" + i + "集")) {
					String title = wzText;
					titleMap.put(i, title);
				}
				i++;
			}
		}

		// 大图
		String posterBigUrl = doc.select("#picFocus>a>img").attr("src");
		video.setPosterBigUrl(posterBigUrl);

		// 简介
		String storyLine = doc.select("#ablum2").select("div.wz").text();
		video.setStoryLine(StringUtils.isEmpty(storyLine) ? storyLineFromTitleList : storyLine);

		// 作者， 简介， 年份， 类型等信息
		// 动漫名称
		String name = doc.select("div.right div.blockRA h2 span").text();
		if (StringUtils.isNotEmpty(name) && !video.getName().contains(name)) {
			logger.error("{}name from episode is diff with video, nameFromEpisoce = {}, nameFromVide={}", new Object[] {
					SOHU_CARTOON, name, video.getName() });
		}

		StringBuffer buf = new StringBuffer();
		Elements holeEles = doc.select("div.right div.blockRA div.cont p");
		for (Element element : holeEles) {
			String authorEtc = element.text();
			buf.append(authorEtc).append(SystemUtils.LINE_SEPARATOR);
		}
		video.setAuthor(buf.toString());
		videoDao.udpate(video);

		// 播放页url， 分集截图，集数等
		Element div = doc.select("div.similarLists").first();
		Elements liElements = div.select("ul>li");
		for (Element ele : liElements) {
			String playPageUrl = ele.select("a").first().attr("href");
			String snapshotUrl = ele.select("img").attr("src");
			String episodeNoStr = ele.select("img").attr("alt");

			// parse episodeNo
			Pattern p = Pattern.compile("[0-9\\.]+");
			Matcher m = p.matcher(episodeNoStr);
			String no = "";
			while (m.find()) {
				no += m.group();
			}

			Integer episodeNo = StringUtils.isEmpty(no) ? null : Integer.parseInt(no);
			String fileUrl = parseVideoSrc(playPageUrl);

			ve.setSnapshotUrl(snapshotUrl);
			ve.setPlayPageUrl(playPageUrl);
			ve.setEpisodeNo(episodeNo);
			ve.setFileUrl(fileUrl);
			ve.setTitle(titleMap.get(episodeNo));

			VideoEpisode veFromDb = videoEpisodeDao.findByVideoIdEpisodeNo(video.getId(), ve.getEpisodeNo());
			if (veFromDb == null) {
				videoEpisodeDao.save(ve);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add video episode : {}", SOHU_CARTOON, JacksonUtil.encode(ve));
				}
				continue;
			}

			// (多个名字时, 以英文逗号分隔)
			boolean isSame = BeanUtil.isFieldsValueSame(ve, veFromDb, episodeCompareFieldNames, null);
			if (!isSame) {
				ve.setId(veFromDb.getId()); // 主键id
				videoEpisodeDao.udpate(ve); // 不同则更新
				logger.info("{}update video episode : \r\n OLD : {}\r\n NEW : {}", new Object[] { SOHU_CARTOON,
						JacksonUtil.encode(veFromDb), JacksonUtil.encode(ve) });
			}
		}

		// Elements eles = doc
		// .select("ul>li span>strong>a[target=_blank]:not(a[title])");
		// for (Element ele : eles) {
		// String playPageUrl = ele.attr("href");
		// int episodeNum = Integer.parseInt(StringUtils
		// .deleteWhitespace(ele.text()).replace("第", "")
		// .replace("集", ""));
		// String fileUrl = parseVideoSrc(playPageUrl);
		//
		// ve.setPlayPageUrl(playPageUrl);
		// ve.setEpisodeNo(episodeNum);
		// ve.setFileUrl(fileUrl);
		//
		// VideoEpisode veFromDb = videoEpisodeDao.findByEpisodeNo(ve
		// .getEpisodeNo());
		// if (veFromDb == null) {
		// videoEpisodeDao.save(ve);
		// continue;
		// }
		//
		// // (多个名字时, 以英文逗号分隔)
		// boolean isSame = BeanUtil.isFieldsValueSame(ve, veFromDb,
		// episodeCompareFieldNames, null);
		// if (!isSame) {
		// veFromDb.setId(ve.getId()); // 主键id
		// videoEpisodeDao.udpate(ve); // 不同则更新
		// logger.info(
		// "{}update video episode : \r\n OLD : {}\r\n NEW : {}",
		// new Object[] { SOHU_CARTOON,
		// JacksonUtil.encode(veFromDb),
		// JacksonUtil.encode(ve) });
		// }
		// }
	}

}
