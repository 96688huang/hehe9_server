package cn.hehe9.common.app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.hehe9.jobs.comic.TencentHotComicCollectJob;
import cn.hehe9.jobs.video.SohuHotVideoCollectJob;

public class AppContextListener implements ServletContextListener {

	private WebApplicationContext springContext;

	private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//		logger.info("start to destroy application ...");
		//		long startTime = System.currentTimeMillis();
		//		try {
		//			DruidDataSource dataSource = SpringUtil.getBean("dataSource", DruidDataSource.class);
		//			dataSource.close();
		//		} catch (Exception e) {
		//			logger.error("destroy app fail.", e);
		//		}
		//		long endTime = System.currentTimeMillis();
		//		logger.info("destroy application complete, used " + ((endTime - startTime) / 1000) + " s");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		if (springContext == null) {
			logger.error("get spring context fail.");
			return;
		}

		// run hot jobs
		if (AppConfig.RUN_HOT_JOBS_SINCE_START) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					TencentHotComicCollectJob hotComic = springContext.getBean("tencentHotComicCollectJob",
							TencentHotComicCollectJob.class);
					SohuHotVideoCollectJob hotVideo = springContext.getBean("sohuHotVideoCollectJob",
							SohuHotVideoCollectJob.class);

					hotComic.executeJob();
					hotVideo.executeJob();
				}
			}).start();
		}

		//		logger.info("start to init application ...");
		//		long startTime = System.currentTimeMillis();
		//		try {
		//			SpringUtil.init();
		//		} catch (Exception e) {
		//			logger.error("init app fail.", e);
		//		}
		//		long endTime = System.currentTimeMillis();
		//		logger.info("init application complete, used " + ((endTime - startTime) / 1000) + " s");
	}

}
