package cn.hehe9.tmp;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.hehe9.common.utils.JsoupUtil;

public class QqHotComicParser {
	public static void main(String[] args) {
		String hotComicCollectPageUrl = "http://ac.qq.com/Rank/comicRank/type/pgv";
		Document doc = JsoupUtil.connect(hotComicCollectPageUrl, 3000, 3, 3000, "");
		Element rank_list_wrap_DIV = doc.select(".rank-list-wrap").first();
		Elements comicsA = rank_list_wrap_DIV.select(".rank-ul a");
		for (Element comicItem : comicsA) {
			String listPageUrl = comicItem.attr("href");
			String name = comicItem.text();
			System.out.println(name);
			System.out.println(listPageUrl);
		}
		System.out.println("hot comic size -->" + comicsA.size());
	}
}
