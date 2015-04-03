package cn.hehe9.service.job;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.service.biz.VideoService;
import cn.hehe9.service.biz.VideoSourceService;

@Component
public class VideosCollectService {
	private static final Logger logger = LoggerFactory.getLogger(SohuVideoCollectService.class);

	@Resource
	private SohuVideoCollectService sohuVideoCollectService;

	@Resource
	private SohuEpisodeCollectService sohuEpisodeCollectService;

	@Resource
	private VideoSourceService videoSourceService;

	@Resource
	private VideoService videoService;

	private static final String COLLECT_VIDEO = ComConstant.LogPrefix.COLLECT_VIDEOS;

	private static final int QUERY_COUNT_PER_TIME = 500;

	public void collectVideos() {
		List<VideoSource> list = videoSourceService.list();
		if (list == null || list.isEmpty()) {
			logger.warn("{}no video source records found, please check.", COLLECT_VIDEO);
			return;
		}

		for (VideoSource vs : list) {
			collectFromSource(vs);
		}
	}

	public void collectEpisode() {
		int page = 1;
		while (true) {
			List<Video> list = videoService.listBrief(page, QUERY_COUNT_PER_TIME);
			if (list == null || list.isEmpty()) {
				return;
			}

			for (Video v : list) {
				try {
					switch (v.getSourceId().intValue()) {
					case 1: // sohu cartoon
						sohuEpisodeCollectService.collectEpisodeFromListPage(v);
						break;
					default:
						logger.error("{}unknown video source.", JacksonUtil.encode(v));
					}
				} catch (Exception e) {
					logger.error(COLLECT_VIDEO + "collect video episode fail, videoId = " + v.getId(), e);
				}

			}
			page++;
		}
	}

	private void collectFromSource(VideoSource vs) {
		try {
			switch (vs.getId().intValue()) {
			case 1: // sohu cartoon
				sohuVideoCollectService.collect(vs);
				break;
			default:
				logger.error("{}unknown video source.", JacksonUtil.encode(vs));
			}
		} catch (Exception e) {
			logger.error(COLLECT_VIDEO + "collect from video source fail, id = " + vs.getId(), e);
		}
	}
}
