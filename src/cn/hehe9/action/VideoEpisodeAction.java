package cn.hehe9.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.utils.StringUtil;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.VideoEpisodeService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class VideoEpisodeAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(VideoEpisodeAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private VideoEpisodeService videoEpisodeService;

	/** 视频id */
	private Integer videoId;

	/** 视频信息 */
	private Video video;

	/** 页码 */
	private Integer page = 1;

	/** 查询分集的数量 */
	private Integer queryCount = 20;

	/** 页的数量 */
	private Integer pageCount;

	/** 分集总数 */
	private Integer total;

	private List<VideoEpisode> episodeList;

	public String list() {
		if (page <= 0 || !StringUtil.isNumeric(page + "")) {
			page = 1;
		}

		video = videoService.findById(videoId);
		episodeList = videoEpisodeService.list(videoId, page, queryCount);
		total = videoEpisodeService.count(videoId);

		int remainCount = total % queryCount > 0 ? 1 : 0;
		pageCount = (total / queryCount) + remainCount;
		return "toList";
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(Integer queryCount) {
		this.queryCount = queryCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<VideoEpisode> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<VideoEpisode> episodeList) {
		this.episodeList = episodeList;
	}
}
