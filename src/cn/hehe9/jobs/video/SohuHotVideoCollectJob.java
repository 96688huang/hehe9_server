package cn.hehe9.jobs.video;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.jobs.base.AbstractJob;
import cn.hehe9.service.job.video.sohu.SohuHotVideoService;

@Component
public class SohuHotVideoCollectJob extends AbstractJob {

	private final static Logger logger = LoggerFactory.getLogger(SohuHotVideoCollectJob.class);

	private final static String VIDEO_SOHU_HOT_JOB = ComConstant.LogPrefix.VIDEO_SOHU_HOT_JOB;

	@Resource
	private SohuHotVideoService sohuHotVideoService;

	// NOTE: 可采用下面注解的方式配置, 也可以 spring 配置文件中配置.
	//	@Scheduled(cron = "0 0 3 * * ?")
	public void executeJob() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("{}hot video job start...", VIDEO_SOHU_HOT_JOB);

			sohuHotVideoService.collectHotVideos();
			
			logger.info("{}hot video job complete. used {} s", VIDEO_SOHU_HOT_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(VIDEO_SOHU_HOT_JOB + "video job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}
	}
}
