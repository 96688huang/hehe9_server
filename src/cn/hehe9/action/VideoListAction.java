package cn.hehe9.action;

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
	private String searchName;

	//---- 回复参数
	/** 展示的标题 */
	private String displayTitle;

	/** 视频列表 */
	private List<Video> videoList;

	/** 分页参数*/
	private Pagination pagination = new Pagination();

	private static final String MAIN_PAGE = PageUrlFlagEnum.MAIN_PAGE.getUrlFlag();
	private static final String LIST_PAGE = PageUrlFlagEnum.LIST_PAGE.getUrlFlag();

	public String listHot() {
		displayTitle = VideoListTitleEnum.VIDEOS_HOT.getTitle();
		videoList = videoService.listBrief(pagination.getPage(), pagination.getQueryCount());
		pagination.setTotal(videoService.countBy());
		return LIST_PAGE;
	}

	public String list() {
		if (StringUtils.isEmpty(searchName)) {
			displayTitle = VideoListTitleEnum.VIDEO_BOOK.getTitle();
		} else {
			displayTitle = VideoListTitleEnum.SEARCH_RESULT.getTitle();
		}

		videoList = videoService.findBriefByName(searchName, pagination.getPage(), pagination.getQueryCount());
		pagination.setTotal(videoService.countBy(searchName));
		return LIST_PAGE;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}

	public List<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}

	public Pagination getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
