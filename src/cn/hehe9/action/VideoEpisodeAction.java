package cn.hehe9.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.common.constants.Pagination;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.VideoEpisodeService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class VideoEpisodeAction extends ActionSupport {

	/**
	 *	serialVersionUID
	 */
	private static final long serialVersionUID = 6795352514687692824L;

	private static final Logger logger = LoggerFactory.getLogger(VideoEpisodeAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private VideoEpisodeService videoEpisodeService;

	/** 视频id */
	private Integer videoId;

	/** 视频信息 */
	private Video video;

	//	/** 页码 */
	//	private Integer page = 1;
	//
	//	/** 查询分集的数量 */
	//	private Integer queryCount = 20;
	//
	//	/** 页的数量 */
	//	private Integer pageCount;
	//
	//	/** 分集总数 */
	//	private Integer total;

	private Pagination pagination;

	/** 分集列表 */
	private List<VideoEpisode> episodeList;

	private static final String EPISODE_LIST_PAGE = PageUrlFlagEnum.VIDEO_EPISODE_LIST_PAGE.getUrlFlag();

	public String list() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
			}

			video = videoService.findById(videoId);
			episodeList = videoEpisodeService.list(videoId, pagination.getPage(), pagination.getPageCount());
			pagination.setTotal(videoEpisodeService.count(videoId));
			return EPISODE_LIST_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	//	public Integer getPage() {
	//		return page;
	//	}
	//
	//	public void setPage(Integer page) {
	//		this.page = page;
	//	}
	//
	//	public Integer getQueryCount() {
	//		return queryCount;
	//	}
	//
	//	public void setQueryCount(Integer queryCount) {
	//		this.queryCount = queryCount;
	//	}
	//
	//	public Integer getPageCount() {
	//		return pageCount;
	//	}
	//
	//	public void setPageCount(Integer pageCount) {
	//		this.pageCount = pageCount;
	//	}
	//
	//	public Integer getTotal() {
	//		return total;
	//	}
	//
	//	public void setTotal(Integer total) {
	//		this.total = total;
	//	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<VideoEpisode> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<VideoEpisode> episodeList) {
		this.episodeList = episodeList;
	}
}
