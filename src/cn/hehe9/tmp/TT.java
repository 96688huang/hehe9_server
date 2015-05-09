package cn.hehe9.tmp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TT {
	public static void main(String[] args) throws Exception {
		Document doc = Jsoup.connect("http://www.dmvcd.com").get();
		System.out.println(doc.html());
	}
}
