package cn.hehe9.jobs.comic;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.jobs.base.AbstractJob;
import cn.hehe9.service.job.comic.TencentService;

@Component
public class TencentComicCollectJob extends AbstractJob {

	private final static Logger logger = LoggerFactory.getLogger(TencentComicCollectJob.class);

	private final static String COMIC_TENCENT_JOB = ComConstant.LogPrefix.COMIC_TENCENT_JOB;

	@Resource
	private TencentService tencentService;

	// NOTE: 可采用下面注解的方式配置, 也可以 spring 配置文件中配置.
	//	@Scheduled(cron = "0 0 3 * * ?")
	public void executeJob() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("{}video job start...", COMIC_TENCENT_JOB);

			tencentService.collectComicsFromSource();

			logger.info("{}video job complete. used {} s", COMIC_TENCENT_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_JOB + "video job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}

		startTime = System.currentTimeMillis();
		try {
			logger.info("{}episode job start...", COMIC_TENCENT_JOB);

			tencentService.collectEpisode();

			logger.info("{}episode job complete. used {} s", COMIC_TENCENT_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_JOB + "episode job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}
	}
}
