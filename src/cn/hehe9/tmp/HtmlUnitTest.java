package cn.hehe9.tmp;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLIElement;
import com.gargoylesoftware.htmlunit.util.WebClientUtils;

public class HtmlUnitTest {
	public static void main(String[] args) throws Exception {
		String url = "http://www.youku.com/show_page/id_zcc001f06962411de83b1.html?from=y1.12-100";
		WebClient client = new WebClient(BrowserVersion.CHROME);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setThrowExceptionOnFailingStatusCode(true);
		client.getOptions().setThrowExceptionOnScriptError(true);

		HtmlPage page = client.getPage(url);
		PrintWriter writer = new PrintWriter(new FileWriter(
				"E:/Mine/My_Projects/Git/hehe9_server/src/cn/hehe9/tmp/youku_list_1.html"));

		// 找到"分集剧情"的超链接
		Iterator<DomElement> iit = page.getElementById("subnav_point").getChildElements().iterator();
		HtmlAnchor anchor = (HtmlAnchor) iit.next();
		System.out.println(anchor.asXml());
		page = anchor.click(); // 点击"分集剧情"
		Thread.sleep(200);
		page = anchor.click(); //  再次点击"分集剧情", 获取点击后的页面内容
		writer.println(page.asXml());
		writer.close();

		DomElement episodeListLiEles = null;
		List<DomElement> ulList = page.getElementsByIdAndOrName("zySeriesTab"); // 点击"分集剧情"后, 页面中会出现两个id=zySeriesTab的ul

		// 筛选出我们想要的div(因为包含截图信息)
		for (DomElement ulItem : ulList) {
			Iterator<DomElement> liIt = ulItem.getChildElements().iterator();
			DomElement li = liIt.next();
			HtmlAnchor ha = (HtmlAnchor) li.getChildElements().iterator().next();

			String onClickJs = ha.getAttribute("onclick");
			if (onClickJs.contains("point_reload_")) {
				episodeListLiEles = ulItem;
				break;
			}
		}

		// 遍历分集li, 点击各范围分集出现的超链接
		HtmlAnchor ha = null;
		Iterator<DomElement> liIt = episodeListLiEles.getChildElements().iterator();
		while (liIt.hasNext()) {
			DomElement liItem = liIt.next();
			// 得到分集的超链接点击位置 (点击后, 会出现范围内的分集列表信息, 如 1-20集)
			ha = (HtmlAnchor) liItem.getChildElements().iterator().next();
			// 点击
			ha.click();
			// 随机睡眠 50 - 100ms, 等待对方服务器加载完成;
			long sleepTime = new Random().nextInt(50) + 50;
			Thread.sleep(sleepTime);
		}
		
		Thread.sleep(500);	//等待对方服务器加载完成;
		page = ha.click();	//获取点击完所有分集展示超链接后的页面内容(这里多点击了一次)
		
		DomElement episodeDiv = page.getElementById("point_reload_1");
		DomElement episodeDiv21 = page.getElementById("point_reload_21");
		System.out.println(episodeDiv.asXml());
		System.out.println("--------------------------------");
		System.out.println(episodeDiv21.asXml());
		
		client.closeAllWindows();
		
		//		
		////		Iterable<DomNode> it = page.getElementById("aspectTab").getFirstChild().getFirstChild().getChildNodes();
		////		Iterable<DomElement> it = page.getElementById("zySeriesTab").getChildElements();
		//		PrintWriter writer2 = new PrintWriter(new FileWriter(
		//				"E:/Mine/My_Projects/Git/hehe9_server/src/cn/hehe9/tmp/youku_list.html"));
		//		HtmlAnchor deItemTmp = null;
		//		while (it.iterator().hasNext()) {
		//			DomNode de = it.iterator().next();
		//			if (de.getChildNodes().iterator().hasNext()) {
		//				deItemTmp = (HtmlAnchor) de.getChildNodes().iterator().next();
		//				deItemTmp.click();
		//				long randomTime = new Random().nextInt(50) + 50;
		//				Thread.sleep(randomTime);	
		//				//				DomElement dde = page2.getElementById("point_reload_1");
		//			}
		//		}
		//
		//		Thread.sleep(1000);
		//		HtmlPage pp = deItemTmp.click();
		//		writer2.println(pp.asXml());
		//		writer2.close();
		//		client.close();
		//		
		//		
		//		DomElement dde = pp.getElementById("point_reload_1");
		//		System.out.println(dde.asXml());
	}
}
