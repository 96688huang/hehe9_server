package cn.hehe9.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.common.utils.JsoupUtil;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.CacheService;
import cn.hehe9.service.biz.ComicService;
import cn.hehe9.service.biz.VideoEpisodeService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class VideoAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3787420603032130842L;

	private static final Logger logger = LoggerFactory.getLogger(VideoAction.class);

	@Resource
	private CacheService cacheService;

	@Resource
	private VideoService videoService;

	@Resource
	private ComicService comicService;

	@Resource
	private VideoEpisodeService videoEpisodeService;

	private InputStream inputStream;

	private List<List<Video>> hotVideoListHolder;

	private List<List<Comic>> hotComicListHolder;

	private List<VideoEpisode> hotEpisodeList;
	private List<Map<Video, List<VideoEpisode>>> hotEpisodeListHolder;

	private List<Video> menuVideoList;

	/** 首页热门视频数量 */
	int MAIN_HOT_VIDEOS_COUNT = 21;
	/** 首页每行热门视频的数量 */
	private final int MAIN_HOT_VIDEOS_COUNT_PER_LINE_ = 7;
	/** 首页热门视频数量(用于展示分集) */

	private final int MAIN_HOT_VIDEOS_COUNT_FOR_EPISODE = 4;
	/** 分集数量 */
	private final int MAIN_HOT_VIDEOS_ESPICODE_COUNT = 10;

	//	/** 动画片大全的视频数量 */
	//	private final int MAIN_MENU_VIDEOS_COUNT = 40;

	/** 字母菜单每组视频的数量 */
	int COUNT_PER_FIRST_CHAR = 30;

	/** 字母菜单 */
	private Map<String, Set<String>> letterMenuVideoMap;

	/** 字母漫画菜单 */
	private Map<String, Set<String>> letterMenuComicMap;

	/** 自动请求首页的标识 */
	private static final AtomicBoolean isRequestingIndex = new AtomicBoolean(false);
	private static final Executor thread = Executors.newSingleThreadExecutor();

	private static final String MAIN_PAGE = PageUrlFlagEnum.MAIN_PAGE.getUrlFlag();

	{
		// 初始化菜单视频容器
		letterMenuVideoMap = new LinkedHashMap<String, Set<String>>(ComConstant.LETTERS.length + 1);
		for (String letter : ComConstant.LETTERS) {
			letterMenuVideoMap.put(letter, new LinkedHashSet<String>(32));
		}
		letterMenuVideoMap.put(ComConstant.OTHER_CNS, new LinkedHashSet<String>());

		// 初始化菜单漫画容器
		letterMenuComicMap = new LinkedHashMap<String, Set<String>>(ComConstant.LETTERS.length + 1);
		for (String letter : ComConstant.LETTERS) {
			letterMenuComicMap.put(letter, new LinkedHashSet<String>(32));
		}
		letterMenuComicMap.put(ComConstant.OTHER_CNS, new LinkedHashSet<String>());
	}

	public String toMain() throws Exception {
		try {
			// check cache
			if (AppConfig.MEMCACHE_ENABLE) {
				String indexPageContent = cacheService.getIndexPageCache();
				if (StringUtils.isNotBlank(indexPageContent)) {
					inputStream = new ByteArrayInputStream(indexPageContent.getBytes("UTF-8"));
					return SUCCESS;
				}
			}

			// 最热门的漫画列表
			initHotComics();

			// 最热门的视频列表
			List<Video> hotVideoList = initHotVideos();

			// 最热门的视频分集列表
			initHotEpisodes(hotVideoList);

			//		// 动画片大全
			//		menuVideoList = videoService.listBrief(1, MAIN_MENU_VIDEOS_COUNT);

			// 字母菜单视频
			initLetterVideos();

			// 字母菜单漫画
			initLetterComics();

			// 异步请求首页内容, 并保存到缓存中
			saveIndexCacheAsyncIfNeeded();
			return MAIN_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	void initLetterVideos() {
		List<Video> letterVideoList = videoService.listBriefGroupByFirstChar(COUNT_PER_FIRST_CHAR);
		for (;;) {
			if (CollectionUtils.isEmpty(letterVideoList)) {
				break;
			}

			// 归类
			Video video = letterVideoList.get(0);
			Set<String> groupVides = letterMenuVideoMap.get(video.getFirstChar().toUpperCase());
			if (groupVides == null) { // 此处只判断null，不要判断是否empty，因为初始化容器时， 是empty。
				groupVides = letterMenuVideoMap.get(ComConstant.OTHER_CNS);
			}
			groupVides.add(video.getName());

			// 删除该元素
			letterVideoList.remove(0);
		}
	}

	void initLetterComics() {
		List<Comic> letterComicList = comicService.listBriefGroupByFirstChar(COUNT_PER_FIRST_CHAR);
		for (;;) {
			if (CollectionUtils.isEmpty(letterComicList)) {
				break;
			}

			// 归类
			Comic comic = letterComicList.get(0);
			Set<String> groupComics = letterMenuComicMap.get(comic.getFirstChar().toUpperCase());
			if (groupComics == null) { // 此处只判断null，不要判断是否empty，因为初始化容器时， 是empty。
				groupComics = letterMenuComicMap.get(ComConstant.OTHER_CNS);
			}
			groupComics.add(comic.getName());

			// 删除该元素
			letterComicList.remove(0);
		}
	}

	private void initHotEpisodes(List<Video> hotVideoList) {
		hotVideoList = hotVideoList.subList(0, MAIN_HOT_VIDEOS_COUNT_FOR_EPISODE);
		hotEpisodeListHolder = new ArrayList<Map<Video, List<VideoEpisode>>>();
		for (Video video : hotVideoList) {
			List<VideoEpisode> episodeList = videoEpisodeService.list(video.getId(), 1, MAIN_HOT_VIDEOS_ESPICODE_COUNT);
			Map<Video, List<VideoEpisode>> map = new HashMap<Video, List<VideoEpisode>>(1);
			map.put(video, episodeList);
			hotEpisodeListHolder.add(map);
		}
	}

	List<Comic> initHotComics() {
		List<Comic> hotComicList = comicService.listBrief(1, MAIN_HOT_VIDEOS_COUNT);
		hotComicListHolder = new ArrayList<List<Comic>>();
		int count = 0;
		for (;;) {
			int preNextCount = count + MAIN_HOT_VIDEOS_COUNT_PER_LINE_;
			int nextCount = preNextCount > hotComicList.size() ? hotComicList.size() : preNextCount;
			hotComicListHolder.add(hotComicList.subList(count, nextCount));
			count = preNextCount;

			if (nextCount >= hotComicList.size()) {
				break;
			}
		}
		return hotComicList;
	}

	List<Video> initHotVideos() {
		List<Video> hotVideoList = videoService.listBrief(1, MAIN_HOT_VIDEOS_COUNT);
		hotVideoListHolder = new ArrayList<List<Video>>();
		int count = 0;
		for (;;) {
			int preNextCount = count + MAIN_HOT_VIDEOS_COUNT_PER_LINE_;
			int nextCount = preNextCount > hotVideoList.size() ? hotVideoList.size() : preNextCount;
			hotVideoListHolder.add(hotVideoList.subList(count, nextCount));
			count = preNextCount;

			if (nextCount >= hotVideoList.size()) {
				break;
			}
		}
		return hotVideoList;
	}

	/**
	 *  异步请求首页内容, 并保存到缓存中
	 */
	private void saveIndexCacheAsyncIfNeeded() {
		// 请求首页html内容(加上判断是为了避免递归 死循环请求)
		if (AppConfig.MEMCACHE_ENABLE && !isRequestingIndex.getAndSet(true)) {
			Runnable requestTask = new Runnable() {
				@Override
				public void run() {
					Document doc = JsoupUtil.connect(AppConfig.INDEX_URL, 10000, 3, 1000, "Request Index");
					if (doc == null) {
						logger.error("save index data to cache fail, as request index fail. indexUrl = "
								+ AppConfig.INDEX_URL);
					}
					String indexHtml = doc.html();
					cacheService.saveIndexPageCache(indexHtml);
					logger.info("save index page to cached complete.");
					isRequestingIndex.set(false); // 还原请求标识
				}
			};
			thread.execute(requestTask);
		}
	}

	public List<List<Video>> getHotVideoListHolder() {
		return hotVideoListHolder;
	}

	public void setHotVideoListHolder(List<List<Video>> hotVideoListHolder) {
		this.hotVideoListHolder = hotVideoListHolder;
	}

	public List<List<Comic>> getHotComicListHolder() {
		return hotComicListHolder;
	}

	public void setHotComicListHolder(List<List<Comic>> hotComicListHolder) {
		this.hotComicListHolder = hotComicListHolder;
	}

	public List<VideoEpisode> getHotEpisodeList() {
		return hotEpisodeList;
	}

	public void setHotEpisodeList(List<VideoEpisode> hotEpisodeList) {
		this.hotEpisodeList = hotEpisodeList;
	}

	public List<Map<Video, List<VideoEpisode>>> getHotEpisodeListHolder() {
		return hotEpisodeListHolder;
	}

	public void setHotEpisodeListHolder(List<Map<Video, List<VideoEpisode>>> hotEpisodeListHolder) {
		this.hotEpisodeListHolder = hotEpisodeListHolder;
	}

	public List<Video> getMenuVideoList() {
		return menuVideoList;
	}

	public void setMenuVideoList(List<Video> menuVideoList) {
		this.menuVideoList = menuVideoList;
	}

	public Map<String, Set<String>> getLetterMenuVideoMap() {
		return letterMenuVideoMap;
	}

	public void setLetterMenuVideoMap(Map<String, Set<String>> letterMenuVideoMap) {
		this.letterMenuVideoMap = letterMenuVideoMap;
	}

	public Map<String, Set<String>> getLetterMenuComicMap() {
		return letterMenuComicMap;
	}

	public void setLetterMenuComicMap(Map<String, Set<String>> letterMenuComicMap) {
		this.letterMenuComicMap = letterMenuComicMap;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public VideoService getVideoService() {
		return videoService;
	}

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	public ComicService getComicService() {
		return comicService;
	}

	public void setComicService(ComicService comicService) {
		this.comicService = comicService;
	}

	// List<List<Map<String,Object>>> jsonList = new
	// ArrayList<List<Map<String,Object>>>();
	// List<Map<String, Object>> itemList = new ArrayList<Map<String,Object>>();
	// for(int i = 0; i < list.size(); i ++)
	// {
	// if(i==7){ // 7个视频为一排
	// jsonList.add(itemList);
	// itemList = new ArrayList<Map<String,Object>>();
	// }
	//
	// Map<String,Object> map=new HashMap<String, Object>();
	// map.put("word", rs.getString("word"));
	// map.put("wordcount", rs.getInt("wordcount"));
	// list.add(map);
	// }
	// JSONArray jsarry = new JSONArray();
	// jsarry=JSONArray.fromObject(list);
	// result=jsarry.toString();
	// }
}
