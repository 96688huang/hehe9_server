package cn.hehe9.tmp;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.hehe9.common.utils.JsoupUtil;

public class SohuHotVideosParser {
	public static void main(String[] args) {
		String collectPageUrl = "http://tv.sohu.com/hotcomic/";

		Document doc = JsoupUtil.connect(collectPageUrl, 3000, 3, 3000, "");
		Element rList_subCon_Ele = doc.select(".rList_subCon").first();
		
//		Elements vNameIn_ELES = doc.select(".vNameIn");
		
		Elements vName_A = rList_subCon_Ele.select(".vName .at");
		for (Element a_Item : vName_A) {
			String playPageUrl = a_Item.attr("href");
			System.out.println("name -->" + a_Item.text());
			System.out.println("playPageUrl-->" + playPageUrl);
			
			Document playDoc = JsoupUtil.connect(playPageUrl, 3000, 3, 3000, "");
			String listPageUrl = playDoc.select(".crumbs a").last().attr("href");
			System.out.println("listPageUrl-->" + listPageUrl);
			
			
			// 根据 listPageUrl 查询出 Video对象
			// 再调用 SohuService.collectEpisoeFromListPageWithFuture(video);
		}
	}
}
