package cn.hehe9.tmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.app.AppHelper;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.common.utils.StringUtil;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;

public class VideoParser {
	public static void main(String[] args) throws Exception {
		String listPageUrl = "http://www.youku.com/show_page/id_zcc001f06962411de83b1.html?from=y1.12-100";
		Document doc = Jsoup.connect(listPageUrl).get();
		System.out.println(doc);
		
		Elements liEles = doc.select("#showInfo .baseinfo li");

		// 倒数二个li都是演员/导演信息
		StringBuilder authorBuf = new StringBuilder(50);
		if (liEles.size() >= 2) {
			Element autorLi = liEles.get(liEles.size() - 2);
			String authorActors = autorLi.select("span").first().text();
			if (StringUtils.isNotBlank(authorActors)) {
				authorBuf.append(authorActors).append("<br />");
			}
		}

		Element authorLastLi = liEles.last();
		Elements authorSpans = authorLastLi.select("span");
		for (Element spanItem : authorSpans) {
			String authorDirector = spanItem.text();
			if (StringUtils.isNotBlank(authorDirector)) {
				authorBuf.append(authorDirector).append("<br />");
			}
		}

		// 总播放量
		String playCountTotal = doc.select("#showInfo .basedata .play").text().replace("总播放:", "");
		
		Elements spanEles = doc.select("#show_info_short span");
		String storyLine = spanEles.last().text();
		storyLine = AppHelper.subString(storyLine, AppConfig.CONTENT_MAX_LENGTH, "...");
		
		// 分集(解析的这一部分没有截图, 故不采用)
//		List<String> reloadLiIdList = new ArrayList<String>(100);
//		Elements reload_liEles = doc.select("#reload_showInfo #overview_wrap #overview #zySeriesTab li");
//		for(Element item : reload_liEles){
//			reloadLiIdList.add(item.attr("data"));
//		}
//		
//		for(String id : reloadLiIdList){
//			Elements episodeLiEles = doc.select("#" + id +" #episode_wrap #episode ul li");
//			for(Element liItem : episodeLiEles){
//				String playPageUrl = liItem.select("a").attr("href");
//				String title = liItem.select("a").text();
//				String episodeNo = StringUtil.pickInteger(title);
//			}
//		}
		
		// 分集
		List<String> pointReloadLiIdList = new ArrayList<String>(100);
		Elements point_reload_liEles = doc.select("#reload_point zySeriesTab li");
		for(Element item : point_reload_liEles){
			pointReloadLiIdList.add(item.attr("data"));
		}
		
		// TODO 需要点击才能出现下面的html 代码(使用 HtmlUnit)
		for(String id : pointReloadLiIdList){
			Elements episodeDivEles = doc.select("#reload_point #" + id +" .item");
			for(Element divItem : episodeDivEles){
				VideoEpisode episode = new VideoEpisode();
				String playPageUrl = divItem.select(".link a").attr("href");
				String title = divItem.select(".link a").attr("title");
				String episodeNo = StringUtil.pickInteger(title);
				
				String snapshotUrl = divItem.select(".thumb img").attr("src");
				String time = divItem.select(".time .num").text();
				
				episode.setPlayPageUrl(playPageUrl);
				episode.setTitle(title);
				episode.setEpisodeNo(Integer.parseInt(episodeNo));
				episode.setSnapshotUrl(snapshotUrl);
				System.out.println(JacksonUtil.encodeQuietly(episode));
			}
		}
	}

	private static void parseYoukuVideos() throws IOException {
		String collectPageUrl = "http://www.youku.com/v_olist/c_100.html";
		Document doc = Jsoup.connect(collectPageUrl).get();
		Elements yk_co13_Eles = doc.select(".yk-col3");
		for (Element item : yk_co13_Eles) {
			Video video = new Video();
			String iconUrl = item.select(".p .p-thumb img").attr("src");
			String name = item.select(".p .p-thumb img").attr("alt");
			if (StringUtils.isBlank(name)) {
				name = item.select(".p .p-meta .p-meta-title").text();
			}

			String updateRemark = item.select(".p .p-thumb .p-thumb-taglb .p-status").text();

			String listPageUrl = item.select(".p .p-link a").attr("href");
			if (StringUtils.isBlank(listPageUrl)) {
				listPageUrl = item.select(".p .p-meta .p-meta-title a").attr("href");
			}

			String author = item.select(".p .p-meta .p-meta-entry .p-actor").text();
			String playCountTotal = item.select(".p .p-meta .p-meta-entry .p-num").text();

			video.setName(name);
			video.setIconUrl(iconUrl);
			video.setUpdateRemark(updateRemark);
			video.setListPageUrl(listPageUrl);
			video.setAuthor(author);
			video.setPlayCountTotal(playCountTotal);
			System.out.println(JacksonUtil.encodeQuietly(video));
		}

		String nextPage = doc.select(".yk-pager .yk-pages .next a").attr("href");
		System.out.println("nextPage -->" + nextPage);
	}
}
