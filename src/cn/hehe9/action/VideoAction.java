package cn.hehe9.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.CacheService;
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
	private VideoEpisodeService videoEpisodeService;

	private InputStream inputStream;

	private List<List<Video>> hotVideoListHolder;

	private List<VideoEpisode> hotEpisodeList;
	private List<Map<Video, List<VideoEpisode>>> hotEpisodeListHolder;

	private List<Video> menuVideoList;

	/** 首页热门视频数量 */
	private final int MAIN_HOT_VIDEOS_COUNT = 21;
	/** 首页每行热门视频的数量 */
	private final int MAIN_HOT_VIDEOS_COUNT_PER_LINE_ = 7;
	/** 首页热门视频数量(用于展示分集) */
	
	private final int MAIN_HOT_VIDEOS_COUNT_FOR_EPISODE = 4;
	/** 分集数量 */
	private final int MAIN_HOT_VIDEOS_ESPICODE_COUNT = 10;

//	/** 动画片大全的视频数量 */
//	private final int MAIN_MENU_VIDEOS_COUNT = 40;

	/** 字母菜单每组视频的数量 */
	private final int COUNT_PER_FIRST_CHAR = 30;

	/** 字母菜单 */
	private Map<String, List<Video>> letterMenuVideoMap;

	/** 自动请求首页的标识 */
	private static final AtomicBoolean isRequestingIndex = new AtomicBoolean(false);
	private static final Executor thread = Executors.newSingleThreadExecutor();

	private static final String MAIN_PAGE = PageUrlFlagEnum.MAIN_PAGE.getUrlFlag();

	{
		// 初始化菜单视频容器
		letterMenuVideoMap = new LinkedHashMap<String, List<Video>>(ComConstant.LETTERS.length + 1);
		for (String letter : ComConstant.LETTERS) {
			letterMenuVideoMap.put(letter, new ArrayList<Video>(32));
		}
		letterMenuVideoMap.put(ComConstant.OTHER_CNS, new ArrayList<Video>());
	}

	public String toMain() throws Exception {

		// check cache
		if(AppConfig.MEMCACHE_ENABLE){
			String indexPageContent = cacheService.getIndexPageCache();
			if (StringUtils.isNotBlank(indexPageContent)) {
				inputStream = new ByteArrayInputStream(indexPageContent.getBytes("UTF-8"));
				return SUCCESS;
			}
		}

		// 最热门的视频列表
		List<Video> hotVideoList = initHotVideos();

		// 最热门的视频分集列表
		initHotEpisodes(hotVideoList);

		//		// 动画片大全
		//		menuVideoList = videoService.listBrief(1, MAIN_MENU_VIDEOS_COUNT);

		// 字母菜单视频
		initLetterVideos();

		// 异步请求首页内容, 并保存到缓存中
		saveIndexCacheAsyncIfNeeded();
		return MAIN_PAGE;
	}

	private void initLetterVideos() {
		List<Video> letterVideoList = videoService.listBriefGroupByFirstChar(COUNT_PER_FIRST_CHAR);
		for (;;) {
			if (CollectionUtils.isEmpty(letterVideoList)) {
				break;
			}

			// 归类
			Video video = letterVideoList.get(0);
			List<Video> groupVides = letterMenuVideoMap.get(video.getFirstChar().toUpperCase());
			if (groupVides == null) { // 此处只判断null，不要判断是否empty，因为初始化容器时， 是empty。
				groupVides = letterMenuVideoMap.get(ComConstant.OTHER_CNS);
			}
			groupVides.add(video);

			// 删除该元素
			letterVideoList.remove(0);
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

	private List<Video> initHotVideos() {
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

	public Map<String, List<Video>> getLetterMenuVideoMap() {
		return letterMenuVideoMap;
	}

	public void setLetterMenuVideoMap(Map<String, List<Video>> letterMenuVideoMap) {
		this.letterMenuVideoMap = letterMenuVideoMap;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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
