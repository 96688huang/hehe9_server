package cn.hehe9.service.job.video;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.constants.VideoSourceName;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.ListUtil;
import cn.hehe9.common.utils.ReferrerUtil;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.service.biz.VideoService;
import cn.hehe9.service.biz.VideoSourceService;
import cn.hehe9.service.job.base.BaseTask;
import cn.hehe9.service.job.sohu.SohuService;

@Component
public class SohuHotVideoService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuHotVideoService.class);

	private static final String VIDEO_SOHU_HOT_VIDEO = ComConstant.LogPrefix.VIDEO_SOHU_HOT_VIDEO;

	@Resource
	private VideoSourceService videoSourceService;

	@Resource
	private VideoService videoService;

	@Resource
	private SohuService sohuService;

	public void collectHotVideos() {
		String hotVideoCollectPageUrl = AppConfig.SOHU_HOT_VIDEO_COLLECT_PAGE_URL;
		Document doc = JsoupUtil.connect(hotVideoCollectPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
				VIDEO_SOHU_HOT_VIDEO, ReferrerUtil.SOHU);
		String sourceName = VideoSourceName.SOHU.getName();
		VideoSource source = videoSourceService.findByName(sourceName);

		Element rList_subCon_Ele = doc.select(".rList_subCon").first();
		Elements vName_A = rList_subCon_Ele.select(".vName .at");
		List<Video> hotVideos = new ArrayList<Video>(vName_A.size());
		for (Element a_Item : vName_A) {
			String name = a_Item.text();
			String playPageUrl = a_Item.attr("href");
			Document playDoc = JsoupUtil.connect(playPageUrl, CONN_TIME_OUT, RECONN_COUNT, RECONN_INTERVAL,
					VIDEO_SOHU_HOT_VIDEO, ReferrerUtil.SOHU);
			String listPageUrl = playDoc.select(".crumbs a").last().attr("href");

			// 根据 listPageUrl 查询出 Video对象
			if (StringUtils.isBlank(listPageUrl)) {
				logger.error("{}listPageUrl parsed is empty. please check. name={}, playPageUrl={}", new Object[] {
						VIDEO_SOHU_HOT_VIDEO, name, playPageUrl });
				continue;
			}

			List<Video> videoList = videoService.findBriefByListPageUrl(source.getId(), listPageUrl);
			if (CollectionUtils.isEmpty(videoList)) {
				// 没有对应的视频, 则新增.
				Video video = new Video();
				video.setSourceId(source.getId());
				video.setName(AppConfig.getAliasNameIfExist(name));
				video.setListPageUrl(listPageUrl);
				videoService.save(video);
				hotVideos.add(video);
				logger.info("{}add new hot video. sourceId = {}, name = {}, listPageUrl = {}", new Object[] {
						VIDEO_SOHU_HOT_VIDEO, source.getId(), name, listPageUrl });
				continue;
			}

			hotVideos.add(videoList.get(0));
			if (videoList.size() > 1) { // 是否有listPageUrl相同的情况
				logger.error("{}more than one videos have same listPageUrl. sourceId = {}, name={}, listPageUrl = {}",
						new Object[] { VIDEO_SOHU_HOT_VIDEO, source.getId(), name, listPageUrl });
			}
		}

		// 如果采集到热门视频, 则重置排名
		if (CollectionUtils.isNotEmpty(hotVideos)) {
			videoService.updateRank(source.getId(), AppConfig.DEFAULT_RANK);
		}

		// 设置排名
		int totalUpdateVideos = 0;
		for (int i = 0; i < hotVideos.size(); i++) {
			Video video = hotVideos.get(i);
			video.setRank(i + 1);
			totalUpdateVideos += videoService.update(video);
		}

		// 更新分集信息
		String partLog = String.format("sourceId = %s, hotVideoListSize = %s", source.getId(), hotVideos.size());
		sohuService.collectEpisoeFromListPageWithFuture(hotVideos, partLog);
		logger.info(
				"{}finish to update hot videos, need to update {}, total update {}, video names : {}",
				new Object[] { VIDEO_SOHU_HOT_VIDEO, hotVideos.size(), totalUpdateVideos,
						ListUtil.wrapFieldValueList(hotVideos, "name") });
	}
}
