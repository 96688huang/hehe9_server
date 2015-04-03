package cn.hehe9.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

	/**
	 * 连接远程url
	 * @param connectUrl		远程url
	 * @param reconnectCount	重连次数
	 * @param connectTimeOut	连接超时时间
	 * @param reconnectInterval	重连间隔(毫秒)
	 * @param logPrefix			日志前缀
	 * @return
	 */
	public static Document connect(String connectUrl, int connectTimeOut, int reconnectCount, long reconnectInterval,
			String logPrefix) {
		Document doc = null;
		for (int i = 0; i < reconnectCount; i++) {
			try {
				doc = Jsoup.connect(connectUrl).timeout(connectTimeOut).get();
			} catch (Exception e) {
				logger.warn("{}connect faile, reconnect after {} seconds, url = {}", new Object[] { logPrefix,
						reconnectInterval / 1000, connectUrl });
				try {
					Thread.sleep(reconnectInterval);
				} catch (InterruptedException e1) {
					// do nothing
				}
			}
		}
		if (doc == null) {
			logger.error("{}connect faile, url = {}", logPrefix, connectUrl);
		}
		return doc;
	}
}
