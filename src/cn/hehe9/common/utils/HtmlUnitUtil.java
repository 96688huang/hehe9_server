package cn.hehe9.common.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitUtil {
	private static final Logger logger = LoggerFactory.getLogger(HtmlUnitUtil.class);

	private static final int TIME_OUT = 5000; //ms

	/**
	 * 创建一个简单的 WebClient 对象, 默认模拟 Chrome浏览器, 不支持css, 支持js, 不抛出异常, 连接超时时间为5s; js执行超时时间为10s<p>
	 * 类似如下代码:<p>
	 * <li>
	 * 	WebClient client = new WebClient(BrowserVersion.CHROME);<li>
	 * 	client.getOptions().setCssEnabled(false);<li>
	 * 	client.getOptions().setJavaScriptEnabled(true);<li>
	 * 	client.getOptions().setThrowExceptionOnFailingStatusCode(false);<li>
	 * 	client.getOptions().setThrowExceptionOnScriptError(false);<li>
	 * 	client.getOptions().setTimeout(5000);<li>
	 *  client.setJavaScriptTimeout(5000);<p>
	 *  client.waitForBackgroundJavaScript(5000);
	 *
	 * @return WebClient 对象
	 */
	public static WebClient createSimpleWebClient() {
		WebClient client = new WebClient(BrowserVersion.CHROME);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setThrowExceptionOnFailingStatusCode(false);
		client.getOptions().setThrowExceptionOnScriptError(false);
		client.getOptions().setTimeout(TIME_OUT);
		client.setJavaScriptTimeout(TIME_OUT * 2);
		client.waitForBackgroundJavaScript(TIME_OUT * 2);
		return client;
	}

	/**
	 * 远程连接(含重试机制)
	 *
	 * @param client
	 * @param url
	 * @param connectTimeOut
	 * @param reconnectCount
	 * @param reconnectInterval
	 * @param logPrefix
	 * @return
	 */
	public static HtmlPage getPage(WebClient client, String url, int connectTimeOut, int reconnectCount,
			long reconnectInterval, String logPrefix) {
		if (client == null || StringUtils.isBlank(url)) {
			return null;
		}

		WebRequest request;
		try {
			request = new WebRequest(new URL(url));
			return getPage(client, request, connectTimeOut, reconnectCount, reconnectInterval, logPrefix);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * 远程连接(含重试机制)
	 *
	 * @param client
	 * @param request
	 * @param connectTimeOut
	 * @param reconnectCount
	 * @param reconnectInterval
	 * @param logPrefix
	 * @return
	 */
	public static HtmlPage getPage(WebClient client, WebRequest request, int connectTimeOut, int reconnectCount,
			long reconnectInterval, String logPrefix) {
		if (client == null || request == null) {
			return null;
		}

		HtmlPage page = null;
		for (int i = 0; i < reconnectCount; i++) {
			try {
				page = client.getPage(request);
				if (page != null) {
					return page;
				}
			} catch (Exception e) {
				logger.warn("{}connect faile, reconnect after {} seconds, url = {}", new Object[] { logPrefix,
						reconnectInterval / 1000, request.getUrl().getHost() });
				try {
					Thread.sleep(reconnectInterval);
				} catch (InterruptedException e1) {
					// do nothing
				}
			}
		}
		if (page == null) {
			logger.error("{}connect faile, url = {}", logPrefix, request.getUrl().getHost());
		}
		return page;
	}
}
