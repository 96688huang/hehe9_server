package cn.hehe9.service.job.youku;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

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
public class YoukuService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(YoukuVideoCollectService.class);

	@Resource
	private YoukuVideoCollectService youkuVideoCollectService;

	@Resource
	private YoukuEpisodeCollectService youkuEpisodeCollectService;

	@Resource
	private VideoSourceService videoSourceService;

	@Resource
	private VideoService videoService;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService threadPool = Executors.newFixedThreadPool(processCount + 1);

	private static final String YOUKU_SERVICE = ComConstant.LogPrefix.YOUKU_SERVICE;

	private static final int QUERY_COUNT_PER_TIME = 500;

	public void collectEpisode() {
		int page = 1;
		String sourceName = VideoSourceName.YOUKU.getName();
		VideoSource source = videoSourceService.findByName(sourceName);
		if (source == null) {
			logger.error("{}source is null. sourceName = " + sourceName);
			return;
		}

		while (true) {
			List<Video> videoList = videoService.listExceptBigData(source.getId(), page, QUERY_COUNT_PER_TIME);
			if (videoList == null || videoList.isEmpty()) {
				return;
			}

			// 计数器
			final AtomicInteger episodeCounter = createCouter();
			// 同步锁对象
			final Object episodeSyncObj = createSyncObject();

			for (Video video : videoList) {
				collectEpisodeFromListPageAsync(video, videoList.size(), episodeCounter, episodeSyncObj);
			}
			// 等待被唤醒(被唤醒后, 重置计数器)
			int lastCount = waitingForNotify(episodeCounter, videoList.size(), episodeSyncObj, YOUKU_SERVICE, logger);
			if (logger.isDebugEnabled()) {
				logger.debug("{}任务线程被唤醒, 本次计算了的分集数 = {}, 重置计数器 = {}.", new Object[] { YOUKU_SERVICE, lastCount,
						episodeCounter.get() });
			}

			page++;
		}
	}

	private void collectEpisodeFromListPageAsync(final Video video, final int totalEpisodeCount,
			final AtomicInteger episodeCounter, final Object episodeSyncObj) {
		Runnable episodeTask = new Runnable() {
			public void run() {
				try {
					youkuEpisodeCollectService.collectEpisodeFromListPage(video);
				} finally {
					String logMsg = logger.isDebugEnabled() ? String.format("%s准备唤醒任务线程. 本线程已计算了 %s 个分集, 本次计算分集数 = %s",
							new Object[] { YOUKU_SERVICE, episodeCounter.get() + 1, totalEpisodeCount }) : null;
					notifyMasterThreadIfNeeded(episodeCounter, totalEpisodeCount, episodeSyncObj, logMsg, logger);
				}
			}
		};
		threadPool.execute(episodeTask);
	}

	/**
	 * 从第三方平台采集视频
	 */
	public void collectVideos() {
		VideoSource source = null;
		try {
			String sourceName = VideoSourceName.YOUKU.getName();
			source = videoSourceService.findByName(sourceName);
			if (source == null) {
				logger.error("{}source is null. sourceName = " + sourceName);
				return;
			}

			youkuVideoCollectService.collect(source);
		} catch (Exception e) {
			logger.error(
					YOUKU_SERVICE + "collect from video source fail, source = " + JacksonUtil.encodeQuietly(source), e);
		}
	}
}
