package cn.hehe9.service.job.comic;

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
import cn.hehe9.common.constants.ComicSourceName;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicSource;
import cn.hehe9.service.biz.ComicService;
import cn.hehe9.service.biz.ComicSourceService;
import cn.hehe9.service.job.base.BaseTask;

@Component
public class TencentService extends BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(TencentComicCollectService.class);

	@Resource
	private TencentComicCollectService tencentComicCollectService;

	@Resource
	private TencentEpisodeCollectService tencentEpisodeCollectService;

	@Resource
	private ComicSourceService comicSourceService;

	@Resource
	private ComicService comicService;

	// 线程池
	private int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService threadPool = Executors.newFixedThreadPool(processCount + 1);

	private static final String COMIC_TENCENT_JOB = ComConstant.LogPrefix.COMIC_TENCENT_JOB;

	private static final int QUERY_COUNT_PER_TIME = 500;

	public void collectEpisode() {
		int page = 1;
		String sourceName = ComicSourceName.TENCENT.getName();
		ComicSource source = comicSourceService.findByName(sourceName);
		if (source == null) {
			logger.error("{}source is null. sourceName = " + sourceName);
			return;
		}

		while (true) {
			List<Comic> comicList = comicService.listExceptBigData(source.getId(), page, QUERY_COUNT_PER_TIME);
			if (CollectionUtils.isEmpty(comicList)) {
				return;
			}

			List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>(comicList.size());
			for (Comic comic : comicList) {
				Future<Boolean> future = collectEpisodeFromListPageAsync(comic);
				futureList.add(future);
			}

			// 等待检查 future task 是否完成
			String prefixLog = COMIC_TENCENT_JOB + "collectEpisode";
			String partLog = String.format("sourceId = %s, page = %s, comicListSize = %s, futureListSize = %s",
					source.getId(), page, comicList.size(), futureList.size());
			waitForFutureTasksDone(futureList, logger, prefixLog, partLog);

			page++;
		}
	}

	private Future<Boolean> collectEpisodeFromListPageAsync(final Comic comic) {
		Future<Boolean> future = threadPool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				tencentEpisodeCollectService.collectEpisodeFromListPage(comic);
				return true;
			}
		});
		return future;
	}

	/**
	 * 从第三方平台采集视频
	 */
	public void collectComicsFromSource() {
		ComicSource source = null;
		try {
			String sourceName = ComicSourceName.TENCENT.getName();
			source = comicSourceService.findByName(sourceName);
			if (source == null) {
				logger.error("{}source is null. sourceName = " + sourceName);
				return;
			}

			tencentComicCollectService.collect(source);
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_JOB + "collectComicsFromSource fail, source = " + JacksonUtil.encodeQuietly(source), e);
		}
	}
}
