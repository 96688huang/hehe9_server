package cn.hehe9.jobs;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.service.job.sohu.SohuService;

@Component
public class SohuCollectJob extends AbstractJob {

	private final static Logger logger = LoggerFactory.getLogger(SohuCollectJob.class);

	private final static String COLLECT_VIDEO = ComConstant.LogPrefix.SOHU_SERVICE;

	@Resource
	private SohuService sohuService;

	// NOTE: 可采用下面注解的方式配置, 也可以 spring 配置文件中配置.
	//	@Scheduled(cron = "0 0 3 * * ?")
	public void executeJob() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("{}job start...", COLLECT_VIDEO);

			sohuService.collectVideos();

			logger.info("{}job complete. used {} s", COLLECT_VIDEO, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(COLLECT_VIDEO + "job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}

		startTime = System.currentTimeMillis();
		try {
			logger.info("{}job start...", COLLECT_VIDEO);

			sohuService.collectEpisode();

			logger.info("{}job complete. used {} s", COLLECT_VIDEO, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(COLLECT_VIDEO + "job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}
	}
}
