package cn.hehe9.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.service.biz.ComicService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class SeoAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2907146293587266041L;

	private static final Logger logger = LoggerFactory.getLogger(SeoAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private ComicService comicService;

	private static String[] prefixWordArr;

	static {
		String prefixWordStr = "很久以前,Long time ago,相传,传闻,多年前,传说中";
		prefixWordArr = prefixWordStr.split(",");
	}

	private static String getRandomPrefixWord() {
		int index = new Random().nextInt(prefixWordArr.length - 1);
		return prefixWordArr[index] + ",";
	}

	public static void mixWords() throws Exception {
		String seoWordsFilePath = "E:/Winson/My_projects/Git/hehe9_server/env/seo_words.txt";
		BufferedReader reader = new BufferedReader(new FileReader(new File(seoWordsFilePath)));
		Map<String, String> wordMap = new HashMap<>(30000);
		String line = null;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			String[] wordArr = line.split(",");
			if (wordArr.length < 2) {
				continue;
			}
			String word_1 = wordArr[0];
			String word_2 = wordArr[1];

			wordMap.put(word_1, word_2);
			wordMap.put(word_2, word_1);
		}
		reader.close();

		String storyLine = "传奇海盗哥尔·D·罗杰在临死前曾留下关于其毕生的财富“One Piece”的消息，由此引得群雄并起，众海盗们为了这笔传说中的巨额财富展开争夺，各种势力、政权不断交替，整个世界进入了动荡混乱的“大海贼时代”。生长在东海某小村庄的路飞受到海贼香克斯的精神指引，决定成为一名出色的海盗。为了达成这个目标，并找到万众瞩目的One Piece，路飞踏上艰苦的旅程。一路上他遇到了无数磨难，也结识了索隆、娜美、乌索普、香吉、罗宾等一众性格各异的好友。他们携手一同展开充满传奇色彩的大冒险。 引发最后的奇迹！突破正义之门引发最后的奇迹！突破正义之门。 海军本部，营救艾斯之旅。 伙伴的行踪。 伙伴的行踪。 伙伴的行...";
		for (String key : wordMap.keySet()) {
			String value = wordMap.get(key);
			storyLine = storyLine.replaceAll(key, value);
		}
		String finalStroyLine = getRandomPrefixWord() + storyLine;
		String[] storyLineItemArr = finalStroyLine.split("。");
		StringBuilder buf = new StringBuilder(finalStroyLine.length() + 100);
		for (String item : storyLineItemArr) {
			buf.append(item + "。");
			if (item.length() > 100) {
				buf.append("(www.dmvcd.com) ");
			}
		}
		System.out.println(buf.toString());
	}

	public static void clean() throws Exception {
		String cleanFilePath = "E:/Winson/My_projects/Git/hehe9_server/env/seo_words.txt";
		String seoWordsFilePath = "E:/Winson/My_projects/Git/hehe9_server/env/seo_words_orign.txt";
		BufferedReader reader = new BufferedReader(new FileReader(new File(seoWordsFilePath)));
		String line = null;
		Set<String> set = new TreeSet<String>();
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			set.add(line);
		}
		reader.close();

		PrintWriter writer = new PrintWriter(new FileWriter(cleanFilePath));
		for (String item : set) {
			writer.println(item);
		}
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		Document doc = JsoupUtil.connect("http://ac.qq.com/Comic/all/search/time/page/1", 2000, 2, 2000, "");
		Elements noResult = doc.select(".ret-search-result-fail");
		System.out.println(CollectionUtils.isEmpty(noResult));
		
	}

}
