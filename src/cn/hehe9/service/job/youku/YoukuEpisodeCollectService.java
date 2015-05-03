package cn.hehe9.service.job.youku;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.app.AppHelper;
import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.utils.BeanUtil;
import cn.hehe9.common.utils.HtmlUnitUtil;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.ReferrerUtil;
import cn.hehe9.common.utils.StringUtil;
import cn.hehe9.common.utils.UserAgentUtil;
import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.dao.VideoEpisodeDao;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.job.base.BaseTask;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Component
public class YoukuEpisodeCollectService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(YoukuEpisodeCollectService.class);

	@Resource
	private VideoDao videoDao;

	@Resource
	private VideoEpisodeDao videoEpisodeDao;

	private static final String YOUKU_EPISODE = ComConstant.LogPrefix.VIDEO_YOUKU_EPISODE;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService episodeThreadPool = Executors.newFixedThreadPool(processCount + 1);

	/** 用于比较Episode的属性名称 */
	private static List<String> episodeCompareFieldNames = new ArrayList<String>();
	static {
		// video episode fields
		episodeCompareFieldNames.add("video_id");
		episodeCompareFieldNames.add("title");
		episodeCompareFieldNames.add("play_page_url");
		episodeCompareFieldNames.add("snapshot_url");
		episodeCompareFieldNames.add("file_url");
	}

	/**
	 * 解析某视频的播放url, 视频url, 第几集等信息
	 * 
	 * @param indexUrl
	 * @throws Exception
	 */
	public void collectEpisodeFromListPage(final Video video) {
		try {
			String listPageUrl = video.getListPageUrl();
			Document doc = JsoupUtil.connect(listPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, YOUKU_EPISODE,
					ReferrerUtil.YOUKU);
			Elements liEles = doc.select("#showInfo .baseinfo li");

			// 倒数二个li都是演员/导演信息
			StringBuilder authorBuf = new StringBuilder(50);
			if (liEles.size() >= 2) {
				Element autorLi = liEles.get(liEles.size() - 2);
				String authorActors = autorLi.select("span").first().text();
				if (StringUtils.isNotBlank(authorActors)) {
					authorBuf.append(authorActors).append("<br />");
				}
			}

			Element authorLastLi = liEles.last();
			Elements authorSpans = authorLastLi.select("span");
			for (Element spanItem : authorSpans) {
				String authorDirector = spanItem.text();
				if (StringUtils.isNotBlank(authorDirector)) {
					authorBuf.append(authorDirector).append("<br />");
				}
			}
			video.setAuthor(authorBuf.toString());

			// 总播放量
			String playCountTotal = doc.select("#showInfo .basedata .play").text().replace("总播放:", "");
			video.setPlayCountTotal(playCountTotal);

			// 剧情
			// NOTE : 有些页面会有2个span元素, 而有些页面没有span元素, 故直接取下面的text内容, 不再区分子元素.
			Elements spanEles = doc.select("#show_info_short");
			String storyLine = spanEles.text();
			storyLine = AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "...");
			video.setStoryLine(storyLine);
			videoDao.udpate(video);

			WebClient client = HtmlUnitUtil.createSimpleWebClient();

			// 设置请求中的内容
			WebRequest request = new WebRequest(new URL(listPageUrl));
			request.setAdditionalHeader("Referer", ReferrerUtil.YOUKU);
			request.setAdditionalHeader("User-Agent", UserAgentUtil.CHROME);
			//		request.setCharset("UTF-8");

			HtmlPage page = client.getPage(request);

			// 找到"分集剧情"的超链接
			DomElement subnav_point_Li = page.getElementById("subnav_point");
			if (subnav_point_Li == null) { // 若获取不到该元素, 则重试;
				for (int i = 0; i < 5; i++) {
					if (subnav_point_Li != null) {
						break;
					}
					page = client.getPage(request);
				}
			}

			Iterable<DomElement> iitChilds = subnav_point_Li.getChildElements();
			Iterator<DomElement> iit = iitChilds.iterator();
			HtmlAnchor anchor = (HtmlAnchor) iit.next();

			DomElement episodeListUl = getEpisodeListUl(anchor);
			if (episodeListUl == null) {
				logger.error("{}collectEpisodeFromListPage fail after retray, episode list UL not found. video : {}",
						YOUKU_EPISODE, JacksonUtil.encodeQuietly(video));
			}

			if (episodeListUl != null) {
				// 遍历分集li, 点击各范围分集出现的超链接
				HtmlAnchor ha = null;
				Iterator<DomElement> liIt = episodeListUl.getChildElements().iterator();
				while (liIt.hasNext()) {
					DomElement liItem = liIt.next();
					// 得到分集的超链接点击位置 (点击后, 会出现范围内的分集列表信息, 如 1-20集)
					ha = (HtmlAnchor) liItem.getChildElements().iterator().next();
					// 点击
					ha.click();
					// 随机睡眠 50 - 100ms, 等待对方服务器加载完成;
					long sleepTime = new Random().nextInt(50) + 50;
					Thread.sleep(sleepTime);
				}

				Thread.sleep(500); //等待对方服务器加载完成;
				page = ha.click(); //获取点击完所有分集展示超链接后的页面内容(这里多点击了一次)
			}

			String htmlPage = page.asXml();
			client.close();

			// 交给 jsoup 解析具体内容
			doc = Jsoup.parse(htmlPage);

			// 注 : 需要点击指定的超链接, 才能出现下面的html代码(使用 HtmlUnit). 
			// 不同视频, div结构稍有不同, 有些是没有分集点击超链接的, 比如"熊出没之夺宝熊兵"
			Elements episodeAreaDivs = doc.select("#point_area .item");
			if (episodeAreaDivs == null) {
				episodeAreaDivs = doc.select("#reload_point .item");
			}

			if (CollectionUtils.isEmpty(episodeAreaDivs)) {
				logger.error(YOUKU_EPISODE + "collect episodes fail, as reload_point item element not found. video = "
						+ JacksonUtil.encodeQuietly(video));
				return;
			}

			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(episodeAreaDivs.size());
			for (Element episodeDiv : episodeAreaDivs) {
				Future<Boolean> future = parseEpisodeAsync(video, episodeDiv);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = YOUKU_EPISODE + "collectEpisodeFromListPage";
			String partLog = String.format(
					"videoId = %s, videoName = %s, episodeAreaDivsSize = %s, futureListSize = %s", video.getId(),
					video.getName(), episodeAreaDivs.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);
		} catch (Exception e) {
			logger.error(
					YOUKU_EPISODE + "collectEpisodeFromListPage fail. delete video. video : " + JacksonUtil.encodeQuietly(video), e);
			videoDao.deleteById(video.getId());
		}
	}

	private DomElement getEpisodeListUl(HtmlAnchor anchor) {
		// 最多重试10次
		for (int i = 0; i < 10; i++) {
			try {
				HtmlPage page = anchor.click(); // 点击"分集剧情"

				// 上面点击后, 想要的"zySeriesTab"不一定会出现, 直接运行超链接上的js, 再次触发它出现, 双重保障.
				ScriptResult jsResult = page.executeJavaScript("y.tab.change(this,'point');");
				page = (HtmlPage) jsResult.getNewPage();

				List<DomElement> ulList = page.getElementsByIdAndOrName("zySeriesTab"); // 点击"分集剧情"后, 页面中会出现两个id=zySeriesTab的ul

				// 筛选出我们想要的div(因为包含截图信息)
				for (DomElement episodeListUl : ulList) {
					Iterator<DomElement> liIt = episodeListUl.getChildElements().iterator();
					DomElement li = liIt.next();
					HtmlAnchor ha = (HtmlAnchor) li.getChildElements().iterator().next();

					String onClickJs = ha.getAttribute("onclick");
					if (onClickJs.contains("point_reload_")) {
						// 找到了想要的ul元素
						return episodeListUl;
					}
				}
				// 页面没加载出来时, 睡眠一段时间, 再重试;
				Thread.sleep(100);
			} catch (Exception e) {
				logger.error("{}getEpisodeListUl fail. exception msg : {}, retray again.", YOUKU_EPISODE,
						e.getMessage());
			}
		}
		return null;
	}

	private Future<Boolean> parseEpisodeAsync(final Video video, final Element episodeDiv) {
		Future<Boolean> future = episodeThreadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				parseEpisode(video, episodeDiv);
				return true;
			}
		});

		return future;
	}

	private void parseEpisode(Video video, Element episodeDiv) {
		try {
			VideoEpisode episodeFromNet = new VideoEpisode();
			String playPageUrl = episodeDiv.select(".link a").attr("href");
			String title = AppHelper.subString(episodeDiv.select(".link a").attr("title"), AppConfig.TITLE_MAX_LENGTH);

			// NOTE:有可能出现这样的内容: "第116话 视线360度!白眼的死角", 需要挑出集数.
			String subTitle = title;
			if (title.contains(" ")) {
				subTitle = title.split(" ")[0];
			} else if (title.contains("话")) {
				subTitle = title.split("话")[0];
			} else if (title.contains("集")) {
				subTitle = title.split("集")[0];
			} else if (title.contains("讲")) {
				subTitle = title.split("讲")[0];
			}

			String episodeNo = StringUtil.pickInteger(subTitle);
			if (StringUtils.isBlank(episodeNo)) {
				// 没有集数, 并且没有播放url, 则不处理(有可能是预告信息)
				if (StringUtils.isBlank(playPageUrl)) {
					logger.error("{}parseEpisode fail. as parse episodeNo is blank. video : {}", YOUKU_EPISODE,
							JacksonUtil.encodeQuietly(video));
					return;
				}
				episodeNo = "1"; // 没有集数, 则默认为1集
			}

			String snapshotUrl = episodeDiv.select(".thumb img").attr("src");
			String fileUrl = parseVideoFileUrl(playPageUrl);
			//	String time = episodeDiv.select(".time .num").text();	// 播放时长

			episodeFromNet.setVideoId(video.getId());
			episodeFromNet.setSnapshotUrl(snapshotUrl);
			episodeFromNet.setPlayPageUrl(playPageUrl);
			episodeFromNet.setEpisodeNo(Integer.parseInt(episodeNo));
			episodeFromNet.setTitle(title);
			episodeFromNet.setFileUrl(fileUrl);

			VideoEpisode episodeFromDb = videoEpisodeDao.findByVideoIdEpisodeNo(video.getId(),
					episodeFromNet.getEpisodeNo());
			if (episodeFromDb == null) {
				videoEpisodeDao.save(episodeFromNet);
				if (logger.isDebugEnabled()) {
					logger.debug("{}add video episode : {}", YOUKU_EPISODE, JacksonUtil.encode(episodeFromNet));
				}
				return;
			}

			boolean isSame = BeanUtil.isFieldsValueSame(episodeFromNet, episodeFromDb, episodeCompareFieldNames, null);
			if (!isSame) {
				episodeFromNet.setId(episodeFromDb.getId()); // 主键id
				videoEpisodeDao.udpate(episodeFromNet); // 不同则更新
				logger.info("{}update video episode : \r\n OLD : {}\r\n NEW : {}", new Object[] { YOUKU_EPISODE,
						JacksonUtil.encode(episodeFromDb), JacksonUtil.encode(episodeFromNet) });
			}

		} catch (Exception e) {
			logger.error(YOUKU_EPISODE + "parseEpisode fail. video : " + JacksonUtil.encodeQuietly(video), e);
		}
	}

	/**
	 * 解析播放页面的视频url
	 * 
	 * @param playPageUrl	播放页面url
	 * @return	视频url
	 * @throws IOException
	 */
	private String parseVideoFileUrl(String playPageUrl) throws IOException {
		Document doc = JsoupUtil.connect(playPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL, YOUKU_EPISODE,
				ReferrerUtil.YOUKU);
		if (doc == null) {
			return null;
		}

		// 从分享的地方获取file url
		String fileUrl = doc.select("#link2").attr("value");
		if (StringUtils.isNotBlank(fileUrl)) {
			return fileUrl;
		}

		// id = link3
		if (StringUtils.isBlank(fileUrl)) {
			fileUrl = doc.select("#link3").attr("value");
		}

		// id = s_msn1
		if (StringUtils.isBlank(fileUrl)) {
			String shareUrl = doc.select("#s_msn1").attr("href");
			String[] itemArr = shareUrl.replace("&amp;", "").split("&");
			for (String item : itemArr) {
				if (StringUtils.startsWithIgnoreCase(item, "swfurl=")) {
					fileUrl = item.split("=")[1];
					break;
				}
			}
		}

		// id = s_mop1
		if (StringUtils.isBlank(fileUrl)) {
			String shareUrl = doc.select("#s_mop1").attr("href");
			String[] itemArr = shareUrl.replace("&amp;", "").split("&");
			for (String item : itemArr) {
				if (StringUtils.startsWithIgnoreCase(item, "swfurl=")) {
					fileUrl = item.split("=")[1];
					break;
				}
			}
		}
		return fileUrl;
	}
}
