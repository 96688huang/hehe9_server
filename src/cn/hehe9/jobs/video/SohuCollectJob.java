package cn.hehe9.jobs.video;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.jobs.base.AbstractJob;
import cn.hehe9.service.job.video.sohu.SohuService;

@Component
public class SohuCollectJob extends AbstractJob {

	private final static Logger logger = LoggerFactory.getLogger(SohuCollectJob.class);

	private final static String SOHU_JOB = ComConstant.LogPrefix.VIDEO_SOHU_JOB;

	@Resource
	private SohuService sohuService;

	// NOTE: 可采用下面注解的方式配置, 也可以 spring 配置文件中配置.
	//	@Scheduled(cron = "0 0 3 * * ?")
	public void executeJob() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("{}video job start...", SOHU_JOB);

			sohuService.collectVideosFromSource();

			logger.info("{}video job complete. used {} s", SOHU_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(SOHU_JOB + "video job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}

		startTime = System.currentTimeMillis();
		try {
			logger.info("{}episode job start...", SOHU_JOB);

			sohuService.collectEpisode();

			logger.info("{}episode job complete. used {} s", SOHU_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(SOHU_JOB + "episode job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}
	}
}
