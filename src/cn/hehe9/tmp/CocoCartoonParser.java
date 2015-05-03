package cn.hehe9.tmp;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.hehe9.common.app.AppHelper;
import cn.hehe9.common.utils.HtmlUnitUtil;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.StringUtil;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CocoCartoonParser {
	public static void main(String[] args) {
		
	}

	private static void parsePicUrl_NotUsed() {
		String readPageUrl = "http://www.cococomic.com/coco/241/2713.htm?s=1";
		HtmlPage page = HtmlUnitUtil.getPage(HtmlUnitUtil.createSimpleWebClient(), readPageUrl, 3000, 3, 3000, "");
		ScriptResult sr = page.executeJavaScript("window.onload=function(){get_sel('1')};");
		page = (HtmlPage) sr.getNewPage();

		Document doc = Jsoup.parse(page.asXml());
		Elements optionEles = doc.select("body option");
		List<Integer> episodeNoList = new ArrayList<Integer>(optionEles.size());
		for (Element optionItem : optionEles) {
			String episodeNoStr = optionItem.attr("value");
			episodeNoList.add(Integer.parseInt(episodeNoStr));
		}

		List<String> picUrlList = new ArrayList<String>(episodeNoList.size());
		for (int no : episodeNoList) {
			String readPageUrlAdded = readPageUrl + "*v=" + no;
			HtmlPage pageItem = HtmlUnitUtil.getPage(HtmlUnitUtil.createSimpleWebClient(), readPageUrlAdded, 3000, 3,
					3000, "");
			ScriptResult srItem = pageItem.executeJavaScript("window.onload=function(){get_sel('" + no + "')};");
			page = (HtmlPage) srItem.getNewPage();

			Document docItem = Jsoup.parse(pageItem.asXml());
			String picUrl = docItem.select("#ComicPic").attr("src");
			picUrlList.add(picUrl);
			System.out.println(picUrl);
		}
		System.out.println(episodeNoList);
		System.out.println(picUrlList);
	}

	private static void parseComicEpisode() {
		String rootUrl = "http://www.cococomic.com";
		String listPageUrl = "http://www.cococomic.com/comic/241";
		Document doc = JsoupUtil.connect(listPageUrl, 3000, 3, 3000, "");
		Element fontEle = doc.select(".c_right_title font").last();
		String serizcedStatus = fontEle != null ? fontEle.text() : "";
		String iconUrl = doc.select(".c_right_img img").attr("src");

		System.out.println(serizcedStatus);
		System.out.println(iconUrl);

		Elements liEles = doc.select(".c_right_comic ul li");
		for (Element liItem : liEles) {
			Element aEle = liItem.select("a").first();
			String title = aEle.text();
			String episodeNoStr = StringUtil.pickInteger(title);
			String readPageUrl = aEle.attr("href");
			readPageUrl = AppHelper.addRootUrlIfNeeded(readPageUrl, rootUrl);
			String picUrls = "";

			System.out.println(title);
			System.out.println(episodeNoStr);
			System.out.println(readPageUrl);
			System.out.println(picUrls);
		}
	}

	public static void parseComic() {
		String rootUrl = "http://www.cococomic.com";
		String collecPageUrl = "http://www.cococomic.com/sitemap/";
		HtmlPage page = HtmlUnitUtil.getPage(HtmlUnitUtil.createSimpleWebClient(), collecPageUrl, 3000, 3, 3000, "");
		Document doc = Jsoup.parse(page.asXml());
		Elements a_Eles = doc.select(".allb a");
		for (Element aItem : a_Eles) {
			String name = aItem.text();
			String listPageUrl = aItem.attr("href");
			listPageUrl = AppHelper.addRootUrlIfNeeded(listPageUrl, rootUrl);
			System.out.println(name);
			System.out.println(listPageUrl);
		}
	}
}
