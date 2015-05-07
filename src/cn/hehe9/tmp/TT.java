package cn.hehe9.tmp;

import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TT {
	public static void main(String[] args) throws Exception {
//		Document doc = Jsoup.connect("http://localhost:8080/hehe9_server").get();
//		System.out.println(doc.html());
		
		System.out.println(URLEncoder.encode("海贼王","UTF-8"));
		
		
	}
}
