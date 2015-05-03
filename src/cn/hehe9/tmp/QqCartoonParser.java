package cn.hehe9.tmp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.hehe9.common.utils.HtmlUnitUtil;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.common.utils.StringUtil;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class QqCartoonParser {
	public static void main(String[] args) throws Exception {
		parseComic();
	}

	private static void parseComic() {
		int pageNo = 1;
		String collectPageUrl = "http://ac.qq.com/Comic/all/search/time/page/";

		String currCollectPageUrl = collectPageUrl + pageNo;
		Document doc = JsoupUtil.connect(collectPageUrl, 5000, 2, 3000, "");
		Elements liEles = doc.select(".ret-search-item");
		for (Element liItem : liEles) {
			Element img_a = liItem.select(".mod-cover-list-thumb").first();
			String name = img_a.attr("title");
			String listPageUrl = img_a.attr("href");
			String iconUrl = img_a.select("img").first().attr("data-original");
			String updateRemark = liItem.select(".mod-cover-list-text").first().text();
			String author = liItem.select(".ret-works-author").first().attr("title");
			Elements types = liItem.select(".ret-works-tags a");
			StringBuilder typesBuf = new StringBuilder();
			for (Element type : types) {
				typesBuf.append(type.text()).append("`");
			}

			String storyLine = liItem.select(".ret-works-decs").first().text();

			System.out.println(name);
			System.out.println(listPageUrl);
			System.out.println(iconUrl);
			System.out.println(updateRemark);
			System.out.println(author);
			System.out.println(typesBuf.toString());
			System.out.println(storyLine);
			System.out.println("----------");
		}
		
		pageNo ++;
		System.out.println("next : " + currCollectPageUrl + pageNo);
	}

	private static void parseComicEpisodes() {
		// 
		String listPageUrl = "http://ac.qq.com/Comic/comicInfo/id/505430";
		Document doc = JsoupUtil.connect(listPageUrl, 5000, 2, 2000, "");
		Element works_cover_Div = doc.select(".works-cover").first();
		Element img_a = works_cover_Div.select("a").first();
		String name = img_a.attr("title");
		String iconUrl = img_a.select("img").attr("src");
		String serializeTips = works_cover_Div.select(".works-intro-status").text();
		String storyLine = doc.select(".works-intro-short").text();

		System.out.println(name);
		System.out.println(iconUrl);
		System.out.println(serializeTips);
		System.out.println(storyLine);
		
		// 分集信息
		Element chapter_page_all_OL = doc.select("ol").first();
		Elements works_chapter_item_Spans = chapter_page_all_OL.select(".works-chapter-item");
		for (Element spanItem : works_chapter_item_Spans) {
			Element a = spanItem.select("a").first();
			String title = a.text();
			String episodeNo = StringUtil.pickInteger(title.split(" ")[0]);
			String readPageUrl = a.attr("href");
			System.out.println(episodeNo + "  " + title + "  " + readPageUrl);
		}
	}
	
	private static void parsePicUrl_NotUsed() throws IOException {
		String outPutFile = "E:/Mine/My_Projects/Git/hehe9_server/src/cn/hehe9/tmp/tt.html";
		String readPageUrl = "http://ac.qq.com/ComicView/chapter/id/505430/cid/2";
		HtmlPage page = HtmlUnitUtil.getPage(HtmlUnitUtil.createSimpleWebClient(), readPageUrl, 5000, 2, 2000, "");
		String scrollToBottomJs = "$('html, body').animate({scrollTop : $(document).height()}, 'slow');";
		ScriptResult sr = page.executeJavaScript(scrollToBottomJs);
		page = (HtmlPage) sr.getNewPage();

		PrintWriter writer = new PrintWriter(new FileWriter(new File(outPutFile)));
		writer.println(page.asXml());
		writer.close();

		System.out.println(page.asXml());
	}
}
