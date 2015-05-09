package cn.hehe9.common.app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化servlet
 */
public class AppInitServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(AppInitServlet.class);

	/**
	 *	serialVersionUID
	 */
	private static final long serialVersionUID = -1803582555430969917L;

	@Override
	public void init() throws ServletException {
//		logger.info("start to init application ...");
//		long startTime = System.currentTimeMillis();
//		try {
//			super.init();
////			SpringUtil.init();	// NOTE:不能初始化 SpringUtil上下文对象, 否则会加载两遍bean, 定时任务会一起运行;
//		} catch (Exception e) {
//			logger.error("init app fail.", e);
//		}
//		long endTime = System.currentTimeMillis();
//		logger.info("init application complete, used " + ((endTime - startTime) / 1000) + " s");
	}
}
