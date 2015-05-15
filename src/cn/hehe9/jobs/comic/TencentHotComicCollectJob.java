package cn.hehe9.jobs.comic;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.jobs.base.AbstractJob;
import cn.hehe9.service.job.comic.TencentHotComicService;

@Component
public class TencentHotComicCollectJob extends AbstractJob {

	private final static Logger logger = LoggerFactory.getLogger(TencentHotComicCollectJob.class);

	private final static String COMIC_TENCENT_HOT_JOB = ComConstant.LogPrefix.COMIC_TENCENT_HOT_JOB;

	@Resource
	private TencentHotComicService tencentHotComicService;

	// NOTE: 可采用下面注解的方式配置, 也可以 spring 配置文件中配置.
	//	@Scheduled(cron = "0 0 3 * * ?")
	public void executeJob() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("{}hot comic job start...", COMIC_TENCENT_HOT_JOB);

			tencentHotComicService.collectHotComics();

			logger.info("{}hot comic job complete. used {} s", COMIC_TENCENT_HOT_JOB, getUsedTimeAsSecond(startTime));
		} catch (Exception e) {
			logger.error(COMIC_TENCENT_HOT_JOB + "comic job fail! used " + getUsedTimeAsSecond(startTime) + " s", e);
		}
		System.gc(); //release
	}
}
