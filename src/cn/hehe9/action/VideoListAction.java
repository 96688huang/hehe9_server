package cn.hehe9.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.common.constants.Pagination;
import cn.hehe9.common.constants.VideoListTitleEnum;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class VideoListAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(VideoListAction.class);

	@Resource
	private VideoService videoService;

	//--- 请求参数 ----
	/** 视频名称 */
	private String searchName;
	/** 视频名称首字母 */
	private String firstChar;

	//---- 回复参数
	/** 展示的标题 */
	private String displayTitle;

	/** 视频列表容器 */
	private List<List<Video>> videoListHolder;

	/** 分页参数*/
	private Pagination pagination = new Pagination();
	
	/** 列表页视频的数量 */
	private final int VIDEOS_COUNT_PER_LINE = 6;

	private static final String MAIN_PAGE = PageUrlFlagEnum.MAIN_PAGE.getUrlFlag();
	private static final String LIST_PAGE = PageUrlFlagEnum.LIST_PAGE.getUrlFlag();

//	public String listHot() {
//		displayTitle = VideoListTitleEnum.VIDEOS_HOT.getTitle();
//		List<Video> videoList = videoService.listBrief(pagination.getPage(), pagination.getQueryCount());
//		pagination.setTotal(videoService.countBy());
//		return LIST_PAGE;
//	}

	public String list() {
		if (StringUtils.isEmpty(searchName)) {
			displayTitle = VideoListTitleEnum.VIDEO_BOOK.getTitle();
		} else {
			displayTitle = VideoListTitleEnum.SEARCH_RESULT.getTitle();
		}

		videoListHolder = new ArrayList<List<Video>>(VIDEOS_COUNT_PER_LINE);
		List<Video> videoList = videoService.findBriefByName(firstChar, searchName, pagination.getPage(), pagination.getQueryCount());
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
		pagination.setTotal(videoService.countBy(firstChar, searchName));
		return LIST_PAGE;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
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
