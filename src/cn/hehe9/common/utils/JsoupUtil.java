package cn.hehe9.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

	/**
	 * 连接远程url, 默认模拟chrome浏览器
	 * @param connectUrl		远程url
	 * @param reconnectCount	重连次数
	 * @param connectTimeOut	连接超时时间(毫秒)
	 * @param reconnectInterval	重连间隔(毫秒)
	 * @param logPrefix			日志前缀
	 * @param referrer			referrer
	 * @return
	 */
	public static Document connect(String connectUrl, int connectTimeOut, int reconnectCount, long reconnectInterval,
			String logPrefix, String referrer) {
		return connect(connectUrl, connectTimeOut, reconnectCount, reconnectInterval, logPrefix, UserAgentUtil.CHROME,
				referrer);
	}

	/**
	 * 连接远程url, 默认模拟chrome浏览器, referrer 默认为connectUrl
	 * @param connectUrl		远程url
	 * @param reconnectCount	重连次数
	 * @param connectTimeOut	连接超时时间(毫秒)
	 * @param reconnectInterval	重连间隔(毫秒)
	 * @param logPrefix			日志前缀
	 * @return
	 */
	public static Document connect(String connectUrl, int connectTimeOut, int reconnectCount, long reconnectInterval,
			String logPrefix) {
		return connect(connectUrl, connectTimeOut, reconnectCount, reconnectInterval, logPrefix, UserAgentUtil.CHROME,
				connectUrl);
	}

	/**
	 * 连接远程url
	 * @param connectUrl		远程url
	 * @param reconnectCount	重连次数
	 * @param connectTimeOut	连接超时时间(毫秒)
	 * @param reconnectInterval	重连间隔(毫秒)
	 * @param logPrefix			日志前缀
	 * @param userAgent			userAgent
	 * @param referrer			referrer
	 * @return
	 */
	public static Document connect(String connectUrl, int connectTimeOut, int reconnectCount, long reconnectInterval,
			String logPrefix, String userAgent, String referrer) {
		if (StringUtils.isBlank(connectUrl)) {
			return null;
		}

		Document doc = null;
		for (int i = 0; i < reconnectCount; i++) {
			try {
				Connection conn = Jsoup.connect(connectUrl);
				if (StringUtils.isNotBlank(userAgent)) {
					conn.userAgent(userAgent);
				}

				if (StringUtils.isNotBlank(referrer)) {
					conn.referrer(referrer);
				}

				doc = conn.timeout(connectTimeOut).get();
				if (doc != null) {
					return doc;
				}
			} catch (Exception e) {
				logger.warn("{}connect fail, reconnect after {} seconds, url = {}", new Object[] { logPrefix,
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
