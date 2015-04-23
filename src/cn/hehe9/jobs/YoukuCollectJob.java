package cn.hehe9.jobs;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.service.job.youku.YoukuService;

@Component
public class YoukuCollectJob extends AbstractJob {

	private final static Logger logger = LoggerFactory.getLogger(YoukuCollectJob.class);

	private final static String YOUKU_JOB = ComConstant.LogPrefix.YOUKU_JOB;

	@Resource
	private YoukuService youkuService;

	// NOTE: 可采用下面注解的方式配置, 也可以 spring 配置文件中配置.
	//	@Scheduled(cron = "0 0 3 * * ?")
	public void executeJob() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("{}video job start...", YOUKU_JOB);

			youkuService.collectVideos();

			logger.info("{}video job complete. used {} s", YOUKU_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(YOUKU_JOB + "video job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}

		startTime = System.currentTimeMillis();
		try {
			logger.info("{}episode job start...", YOUKU_JOB);

			youkuService.collectEpisode();

			logger.info("{}episode job complete. used {} s", YOUKU_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(YOUKU_JOB + "episode job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}
	}
}
