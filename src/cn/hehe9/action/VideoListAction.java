package cn.hehe9.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.app.AppHelper;
import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.common.constants.Pagination;
import cn.hehe9.common.constants.VideoListTitleEnum;
import cn.hehe9.common.utils.UrlEncodeUtil;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.service.biz.CacheService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class VideoListAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5777334391815076701L;

	private static final Logger logger = LoggerFactory.getLogger(VideoListAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private CacheService cacheService;

	/** 列表页视频的数量 */
	private final int VIDEOS_COUNT_PER_LINE = 7;

	//--- 请求参数 ----
	/** 视频名称 */
	private String searchName;
	// 默认采用base64编码(如果是form表单提交， 则无需要采用base64编码)
	private Boolean isSearchNameEncode = true;
	/** 视频名称首字母 */
	private String firstChar;

	//---- 回复参数
	/** 展示的标题 */
	private String displayTitle;

	/** 视频列表容器 */
	private List<List<Video>> videoListHolder = new ArrayList<List<Video>>(VIDEOS_COUNT_PER_LINE);;

	/** 分页参数*/
	private Pagination pagination = new Pagination();

	private static final String LIST_PAGE = PageUrlFlagEnum.VIDEO_LIST_PAGE.getUrlFlag();

	public String list() throws Exception {
		try {
			searchName = isSearchNameEncode ? UrlEncodeUtil.base64Decode(searchName) : searchName;
			firstChar = UrlEncodeUtil.base64Decode(firstChar);
			
			// 预防搜索内容过长, 以及预防 XSS 攻击
			searchName = ActionHelper.cleanText(searchName);
			firstChar = ActionHelper.cleanText(firstChar);

			if (StringUtils.isNotBlank(searchName)) {
				displayTitle = searchName + "  " + VideoListTitleEnum.SEARCH_RESULT.getTitle();
			} else if (StringUtils.isNotBlank(firstChar)) {
				displayTitle = firstChar.trim() + VideoListTitleEnum.FIRST_CHAR_VIDEO.getTitle();
			} else {
				displayTitle = VideoListTitleEnum.VIDEO_BOOK.getTitle();
			}

			// 先从缓存中取
			List<Video> videoList = null;
			AtomicInteger total = new AtomicInteger(0);
			if (AppConfig.MEMCACHE_ENABLE) {
				List<Integer> sourceIdList = cacheService.getOrCreateVideoSourceIdsCache();
				videoList = pickVideosFromCache(total, sourceIdList);
			}

			// 如果缓存中没有, 则从DB中取
			if (CollectionUtils.isEmpty(videoList)) {
				videoList = pickVideosFromDb(total);
			}

			// 排版
			int count = 0;
			for (;;) {
				int preNextCount = count + VIDEOS_COUNT_PER_LINE;
				int nextCount = preNextCount > videoList.size() ? videoList.size() : preNextCount;
				videoListHolder.add(videoList.subList(count, nextCount));
				count = preNextCount;

				if (nextCount >= videoList.size()) {
					break;
				}
			}
			pagination.setTotal(total.get());
			return LIST_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	private List<Video> pickVideosFromDb(AtomicInteger total) {
		List<Video> videoList;
		videoList = videoService.findBriefByName(firstChar, searchName, pagination.getPage(),
				pagination.getQueryCount());

		// 赋值视频来源名称
		ActionHelper.setSourceName(videoList);

		total.set(videoService.countBy(firstChar, searchName));
		return videoList;
	}

	private List<Video> pickVideosFromCache(AtomicInteger total, List<Integer> sourceIdList) {
		if (CollectionUtils.isEmpty(sourceIdList)) {
			return null;
		}

		List<Video> queryVideos = new ArrayList<Video>(30);
		int fromIndex = (pagination.getPage() - 1) * pagination.getQueryCount();
		int toIndex = fromIndex + pagination.getQueryCount() + 1;

		List<Video> allVideos = cacheService.getOrCreateSourceVideosCache(sourceIdList);
		for (Video video : allVideos) {
			if (StringUtils.isNotBlank(searchName) && video.getName().contains(searchName)) {
				pickVideos(total, queryVideos, toIndex, video);
			} else if (StringUtils.isNotBlank(firstChar) && video.getFirstChar().equalsIgnoreCase(firstChar)) {
				pickVideos(total, queryVideos, toIndex, video);
			} else if (StringUtils.isBlank(searchName) && StringUtils.isBlank(firstChar)) { // 无查询条件, 则查询所有
				pickVideos(total, queryVideos, toIndex, video);
			}
		}

		if (total.get() == 0 || CollectionUtils.isEmpty(queryVideos)) {
			return null;
		}

		// sub list by query page and count
		toIndex = Math.min(total.get(), toIndex); // 可能查询的数量大于总数
		toIndex = Math.min(toIndex, queryVideos.size()); // 可能元素数量少于查询的数量
		fromIndex = Math.min(fromIndex, toIndex); // 预防非法篡改分页参数
		queryVideos = queryVideos.subList(fromIndex, toIndex);
		return queryVideos;
	}

	/**
	 * 根据需求, 挑选视频
	 *
	 * @param total			符合条件的视频总数
	 * @param queryVideos	(查询结果得到的)视频列表
	 * @param toIndex		要查询到的下标
	 * @param video			视频信息
	 */
	private void pickVideos(AtomicInteger total, List<Video> queryVideos, int toIndex, Video video) {
		if (total.get() < toIndex) {
			queryVideos.add(video);
		}
		total.incrementAndGet();
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Boolean getIsSearchNameEncode() {
		return isSearchNameEncode;
	}

	public void setIsSearchNameEncode(Boolean isSearchNameEncode) {
		this.isSearchNameEncode = isSearchNameEncode;
	}

	public String getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}

	public List<List<Video>> getVideoListHolder() {
		return videoListHolder;
	}

	public void setVideoListHolder(List<List<Video>> videoListHolder) {
		this.videoListHolder = videoListHolder;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
