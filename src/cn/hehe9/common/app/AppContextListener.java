package cn.hehe9.common.app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

import cn.hehe9.common.utils.SpringUtil;

public class AppContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
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
	public void contextInitialized(ServletContextEvent arg0) {
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
