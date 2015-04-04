package cn.hehe9.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
public class VideoPlayAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(VideoPlayAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private VideoEpisodeService videoEpisodeService;

	//--- 请求参数 ---
	/** 视频id */
	private Integer videoId;

	/** 视频分集id */
	private Integer episodeId;

	/** 视频分集集数 */
	private Integer episodeNo;

	//--- 回复参数 ---
	/** 视频信息 */
	private Video video;

	/** 上一集采视频信息 */
	private VideoEpisode preEpisode;

	/** (当前播放的)视频分集信息 */
	private VideoEpisode episode;

	/** 下一级视频信息 */
	private VideoEpisode nextEpisode;

	/** 视频最近几个分集的数量 */
	private static final int NEAR_EPISODE_COUNT = 3;

	private static final String TO_PLAY = "toPlay";

	public String play() {
		if (videoId <= 0 || episodeId <= 0 || episodeNo <= 0) {
			return TO_PLAY;
		}

		video = videoService.findById(videoId);
		Integer[] episodeNoArr = new Integer[] { episodeNo - 1, episodeNo, episodeNo + 1 };
		List<VideoEpisode> episodeList = videoEpisodeService.list(videoId, 1, NEAR_EPISODE_COUNT, episodeNoArr);
		if (CollectionUtils.isEmpty(episodeList)) {
			return TO_PLAY;
		}

		if (episodeList.size() == NEAR_EPISODE_COUNT) {
			preEpisode = episodeList.get(0);
			episode = episodeList.get(1);
			nextEpisode = episodeList.get(2);
		} else if (episodeList.size() == 2) {
			episode = episodeList.get(0);
			nextEpisode = episodeList.get(1);
		} else {
			episode = episodeList.get(0);
		}
		return TO_PLAY;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public VideoEpisode getPreEpisode() {
		return preEpisode;
	}

	public void setPreEpisode(VideoEpisode preEpisode) {
		this.preEpisode = preEpisode;
	}

	public Integer getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(Integer episodeId) {
		this.episodeId = episodeId;
	}

	public Integer getEpisodeNo() {
		return episodeNo;
	}

	public void setEpisodeNo(Integer episodeNo) {
		this.episodeNo = episodeNo;
	}

	public VideoEpisode getNextEpisode() {
		return nextEpisode;
	}

	public void setNextEpisode(VideoEpisode nextEpisode) {
		this.nextEpisode = nextEpisode;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public VideoEpisode getEpisode() {
		return episode;
	}

	public void setEpisode(VideoEpisode episode) {
		this.episode = episode;
	}

}
