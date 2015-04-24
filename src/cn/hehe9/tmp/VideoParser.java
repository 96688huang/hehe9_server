package cn.hehe9.tmp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.UrlEncoded;
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

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class VideoParser {
	public static void main(String[] args) throws Exception {
		parseYoukuEpisodeInfos();
	}

	private static void parseFileUrl() throws IOException {
		String playPageUrl = "http://v.youku.com/v_show/id_XODk1NDk4NDky.html";
		Document doc = Jsoup.connect(playPageUrl).get();

		// 从分享的地方获取file url
		String fileUrl = doc.select("#link2").attr("value");
		System.out.println(fileUrl);
	}

	/**
	 * 该方法无效, 因为youku采用js的方式播放视频, 解析难度较大;
	 *
	 * @throws Exception
	 */
	private static void parseFileUrl_NotUsed() throws Exception {
		String playPageUrl = "http://v.youku.com/v_show/id_XODk1NDk4NDky.html";
		Document doc = Jsoup.connect(playPageUrl).get();
		Elements playObject = doc.select("#movie_player");
		String fileUrlPart = playObject.attr("data");
		String flashVarsValue = playObject.select("param[name=flashvars]").attr("value");

		// decode 前:
		//		VideoIDS=XODk1NDk4NDky&ShowId=19461&category=100&Cp=authorized&sv=true&Light=on&THX=off&unCookie=0&frame=0&pvid=14296871070827LaPZc&uepflag=0&Tid=0&isAutoPlay=true&Version=/v1.0.1046&show_ce=0&winType=interior&embedid=AjIyMzg3NDYyMwJ3d3cueW91a3UuY29tAi9zaG93X3BhZ2UvaWRfemNjMDAxZjA2OTYyNDExZGU4M2IxLmh0bWw=&vext=bc%3D%26pid%3D14296871070827LaPZc%26unCookie%3D0%26frame%3D0%26type%3D0%26svt%3D1%26stg%3D620%26emb%3DAjIyMzg3NDYyMwJ3d3cueW91a3UuY29tAi9zaG93X3BhZ2UvaWRfemNjMDAxZjA2OTYyNDExZGU4M2IxLmh0bWw%3D%26dn%3D%E7%BD%91%E9%A1%B5%26hwc%3D1%26mtype%3Doth&pageStartTime=1429687107080
		// decode 后:
		//		VideoIDS=XODk1NDk4NDky&ShowId=19461&category=100&Cp=authorized&sv=true&Light=on&THX=off&unCookie=0&frame=0&pvid=14296871070827LaPZc&uepflag=0&Tid=0&isAutoPlay=true&Version=/v1.0.1046&show_ce=0&winType=interior&embedid=AjIyMzg3NDYyMwJ3d3cueW91a3UuY29tAi9zaG93X3BhZ2UvaWRfemNjMDAxZjA2OTYyNDExZGU4M2IxLmh0bWw=&vext=bc=&pid=14296871070827LaPZc&unCookie=0&frame=0&type=0&svt=1&stg=620&emb=AjIyMzg3NDYyMwJ3d3cueW91a3UuY29tAi9zaG93X3BhZ2UvaWRfemNjMDAxZjA2OTYyNDExZGU4M2IxLmh0bWw=&dn=网页&hwc=1&mtype=oth&pageStartTime=1429687107080
		// 可能有其他样式:
		//		VideoIDS=XOTM0MzI2MDY0&amp;ShowId=19461&amp;category=100&amp;Cp=authorized&amp;sv=true&amp;Light=on&amp;THX=off&amp;unCookie=0&amp;frame=0&amp;pvid=1429587386821ruDXe7&amp;uepflag=0&amp;Tid=0&amp;isAutoPlay=true&amp;Version=/v1.0.1046&amp;show_ce=0&amp;winType=interior&amp;embedid=AjIzMzU4MTUxNgJ3d3cueW91a3UuY29tAi9zaG93X3BhZ2UvaWRfemNjMDAxZjA2OTYyNDExZGU4M2IxLmh0bWw=&amp;vext=bc=&pid=1429587386821ruDXe7&unCookie=0&frame=0&type=0&svt=1&stg=628&emb=AjIzMzU4MTUxNgJ3d3cueW91a3UuY29tAi9zaG93X3BhZ2UvaWRfemNjMDAxZjA2OTYyNDExZGU4M2IxLmh0bWw=&dn=网页&hwc=1&mtype=oth&amp;pageStartTime=1429587386819

		flashVarsValue = URLDecoder.decode(flashVarsValue, "UTF-8");

		String videoIds = null;
		flashVarsValue = flashVarsValue.replace("&amp;", "&");
		String[] valueItemArr = flashVarsValue.split("&");
		for (String item : valueItemArr) {
			item = item.trim();
			if (StringUtils.startsWithIgnoreCase(item, "VideoIDS")) {
				videoIds = item;
				break;
			}
		}
		String fileUrl = fileUrlPart + "&" + videoIds + "&";
		fileUrl = UrlEncoded.encodeString(fileUrl);
		System.out.println(fileUrl);
	}

	private static void parseYoukuEpisodeInfos() throws IOException, MalformedURLException, InterruptedException {
		String listPageUrl = "http://www.youku.com/show_page/id_z0d8abd568d1f11e296da.html";
		String referer = listPageUrl;
		WebClient client = new WebClient(BrowserVersion.CHROME);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setThrowExceptionOnFailingStatusCode(false);
		client.getOptions().setThrowExceptionOnScriptError(false);
		client.getOptions().setTimeout(5 * 1000); // TODO 超时重试机制

		// 设置请求中的内容
		WebRequest request = new WebRequest(new URL(listPageUrl));
		request.setAdditionalHeader("Referer", referer);
		//		request.setCharset("UTF-8");

		HtmlPage page = client.getPage(request);

		// 找到"分集剧情"的超链接
//		Iterator<DomElement> iit = page.getElementById("subnav_point").getChildElements().iterator();
//		HtmlAnchor anchor = (HtmlAnchor) iit.next();
//		System.out.println(anchor.asXml());
//		page = anchor.click(); // 点击"分集剧情"
//		Thread.sleep(200);
//		page = anchor.click(); //  再次点击"分集剧情", 获取点击后的页面内容
		
		// 上面点击后, 想要的"zySeriesTab"不一定会出现, 直接运行超链接上的js, 再次触发它出现, 双重保障.
		ScriptResult jsResult = page.executeJavaScript("y.tab.change(this,'point');");
		page = (HtmlPage) jsResult.getNewPage();
		
		List<DomElement> ulList = page.getElementsByIdAndOrName("zySeriesTab"); // 点击"分集剧情"后, 页面中会出现两个id=zySeriesTab的ul
		
		Thread.sleep(500);
		jsResult = page.executeJavaScript("y.tab.change(this,'point');");
		page = (HtmlPage) jsResult.getNewPage();

		DomElement episodeListLiEles = null;
		ulList = page.getElementsByIdAndOrName("zySeriesTab"); // 点击"分集剧情"后, 页面中会出现两个id=zySeriesTab的ul

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

		Thread.sleep(500); //等待对方服务器加载完成;
		page = ha.click(); //获取点击完所有分集展示超链接后的页面内容(这里多点击了一次)

		String htmlPage = page.asXml();
		client.close();

		// 交给 jsoup 解析具体内容
		Document doc = Jsoup.parse(htmlPage);
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

		Elements spanEles = doc.select("#show_info_short");	// 有些页面会有2个span元素, 而有些页面没有span元素, 故直接取下面的text内容, 不再区分子元素.
		String storyLine = spanEles.text();
		storyLine = AppHelper.subString(storyLine, AppConfig.STORYLINE_MAX_LENGTH, "...");

		// 注 : 需要点击指定的超链接, 才能出现下面的html代码(使用 HtmlUnit)
		Elements episodeAreaDivs = doc.select("#point_area .item");
		for (Element episodeDiv : episodeAreaDivs) {
			try {
				VideoEpisode episode = new VideoEpisode();
				String playPageUrl = episodeDiv.select(".link a").attr("href");
				String title = episodeDiv.select(".link a").attr("title");
				String episodeNo = StringUtil.pickInteger(title);
				if (StringUtils.isBlank(episodeNo)) {
					// log error msg
					continue;
				}

				String snapshotUrl = episodeDiv.select(".thumb img").attr("src");
				String time = episodeDiv.select(".time .num").text();

				episode.setPlayPageUrl(playPageUrl);
				episode.setTitle(title);
				episode.setEpisodeNo(Integer.parseInt(episodeNo));
				episode.setSnapshotUrl(snapshotUrl);
				System.out.println(JacksonUtil.encodeQuietly(episode));
			} catch (Exception e) {
				// log error msg
			}
		}

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
		//		List<String> pointReloadLiIdList = new ArrayList<String>(100);
		//		Elements point_reload_liEles = doc.select("#reload_point zySeriesTab li");
		//		for(Element item : point_reload_liEles){
		//			pointReloadLiIdList.add(item.attr("data"));
		//		}
		//		
		//		// TODO 需要点击才能出现下面的html 代码(使用 HtmlUnit)
		//		for(String id : pointReloadLiIdList){
		//			Elements episodeDivEles = doc.select("#reload_point #" + id +" .item");
		//			for(Element divItem : episodeDivEles){
		//				VideoEpisode episode = new VideoEpisode();
		//				String playPageUrl = divItem.select(".link a").attr("href");
		//				String title = divItem.select(".link a").attr("title");
		//				String episodeNo = StringUtil.pickInteger(title);
		//				
		//				String snapshotUrl = divItem.select(".thumb img").attr("src");
		//				String time = divItem.select(".time .num").text();
		//				
		//				episode.setPlayPageUrl(playPageUrl);
		//				episode.setTitle(title);
		//				episode.setEpisodeNo(Integer.parseInt(episodeNo));
		//				episode.setSnapshotUrl(snapshotUrl);
		//				System.out.println(JacksonUtil.encodeQuietly(episode));
		//			}
		//		}
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
