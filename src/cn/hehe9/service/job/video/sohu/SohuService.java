package cn.hehe9.service.job.video.sohu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.constants.VideoSourceName;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.service.biz.VideoService;
import cn.hehe9.service.biz.VideoSourceService;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class SohuService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(SohuVideoCollectService.class);

	@Resource
	private SohuVideoCollectService sohuVideoCollectService;

	@Resource
	private SohuEpisodeCollectService sohuEpisodeCollectService;

	@Resource
	private VideoSourceService videoSourceService;

	@Resource
	private VideoService videoService;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService threadPool = Executors.newFixedThreadPool(processCount + 1);

	private static final String SOHU_JOB = ComConstant.LogPrefix.VIDEO_SOHU_JOB;

	private static final int QUERY_COUNT_PER_TIME = 500;

	public void collectEpisode() {
		int page = 1;
		String sourceName = VideoSourceName.SOHU.getName();
		VideoSource source = videoSourceService.findByName(sourceName);
		if (source == null) {
			logger.error("{}source is null. sourceName = " + sourceName);
			return;
		}

		while (true) {
			List<Video> videoList = videoService.listExceptBigData(source.getId(), page, QUERY_COUNT_PER_TIME);
			if (CollectionUtils.isEmpty(videoList)) {
				return;
			}

			String prefixLog = SOHU_JOB + "collectEpisode";
			String partLog = String.format("sourceId = %s, page = %s, videoListSize = %s", source.getId(), page,
					videoList.size());
			collectEpisoeFromListPageWithFuture(videoList, prefixLog, partLog);
			page++;
		}
	}

	public void collectEpisoeFromListPageWithFuture(List<Video> videoList, String prefixLog, String partLog) {
		List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(videoList.size());
		for (Video video : videoList) {
			Future<Boolean> future = collectEpisodeFromListPageAsync(video);
			futureList.add(future);
		}

		// 等待检查 future task 是否完成
		waitForFutureTasksDone(futureList, logger, prefixLog, partLog);
	}

	private Future<Boolean> collectEpisodeFromListPageAsync(final Video video) {
		Future<Boolean> future = threadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				sohuEpisodeCollectService.collectEpisodeFromListPage(video);
				return true;
			}
		});
		return future;
	}

	/**
	 * 从第三方平台采集视频
	 */
	public void collectVideosFromSource() {
		VideoSource source = null;
		try {
			String sourceName = VideoSourceName.SOHU.getName();
			source = videoSourceService.findByName(sourceName);
			if (source == null) {
				logger.error("{}source is null. sourceName = " + sourceName);
				return;
			}

			sohuVideoCollectService.collect(source);
		} catch (Exception e) {
			logger.error(SOHU_JOB + "collectVideosFromSource fail, source = " + JacksonUtil.encodeQuietly(source), e);
		}
	}
}
