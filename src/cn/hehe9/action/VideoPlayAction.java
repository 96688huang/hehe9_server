package cn.hehe9.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.VideoEpisodeService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class VideoPlayAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3207061567116252849L;

	private static final Logger logger = LoggerFactory.getLogger(VideoPlayAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private VideoEpisodeService videoEpisodeService;

	// --- 请求参数 ---
	/** 视频id */
	private Integer videoId;

	/** 视频分集id */
	private Integer episodeId;

	/** 视频分集集数 */
	private Integer episodeNo;

	// --- 回复参数 ---
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

	/** 最大分集与最小分集, 距离当前点播分集的上下间隔 */
	private static final int EPISODE_INTERVAL = 2;

	/** 播放页右边的分集列表 */
	private List<VideoEpisode> episodeList;

	private static final String MAIN_PAGE = PageUrlFlagEnum.MAIN_PAGE.getUrlFlag();
	private static final String PLAY_PAGE = PageUrlFlagEnum.PLAY_PAGE.getUrlFlag();

	public String play() {
		try {
			if (videoId <= 0 || episodeId <= 0 || episodeNo <= 0) {
				return MAIN_PAGE;
			}

			video = videoService.findById(videoId);
			// Integer[] episodeNoArr = new Integer[] { episodeNo - 1, episodeNo,
			// episodeNo + 1 };
			// List<VideoEpisode> episodeListTmp = videoEpisodeService.list(videoId,
			// 1, NEAR_EPISODE_COUNT, episodeNoArr);
			// if (CollectionUtils.isEmpty(episodeListTmp)) {
			// return PLAY_PAGE;
			// }
			//
			// if (episodeListTmp.size() == NEAR_EPISODE_COUNT) {
			// nextEpisode = episodeListTmp.get(0); // 按分集倒序
			// episode = episodeListTmp.get(1);
			// preEpisode = episodeListTmp.get(2);
			// } else if (episodeListTmp.size() == 2) {
			// episode = episodeListTmp.get(0);
			// preEpisode = episodeListTmp.get(1);
			// } else if (episodeListTmp.size() == 1) {
			// episode = episodeListTmp.get(0);
			// }

			// 播放页右边的分集列表
			VideoEpisode maxEpisode = videoEpisodeService.getMaxEpisode(videoId);
			if (maxEpisode == null) {
				return PLAY_PAGE;
			}

			int maxEpisodeNo = maxEpisode.getEpisodeNo();
			int minEpisodeNo = episodeNo;
			if (episodeNo + EPISODE_INTERVAL <= maxEpisodeNo) {
				maxEpisodeNo = episodeNo + EPISODE_INTERVAL;
			}
			int tmp = maxEpisodeNo - (2 * EPISODE_INTERVAL) + 1;
			minEpisodeNo = tmp > 0 ? tmp : 0;

			//		else if (episodeNo == maxEpisodeNo) {
			//			int tmp = episodeNo - (2 * EPISODE_INTERVAL) + 1;
			//			minEpisodeNo = tmp > 0 ? tmp : 0;
			//		} else {
			//			int tmp = maxEpisodeNo - (2 * EPISODE_INTERVAL) + 1;
			//			minEpisodeNo = tmp > 0 ? tmp : 0;
			//		}

			this.episodeList = videoEpisodeService.listByRange(videoId, minEpisodeNo, maxEpisodeNo);
			if (CollectionUtils.isEmpty(this.episodeList)) {
				return PLAY_PAGE;
			}

			// 倒序(分集越小, 越排在前面)
			List<VideoEpisode> tmpList = new ArrayList<VideoEpisode>(this.episodeList.size());
			for (int i = this.episodeList.size() - 1; i >= 0; i--) {
				tmpList.add(this.episodeList.get(i));
			}
			this.episodeList.clear();
			this.episodeList = tmpList;

			// 上集/当前集/下集
			int episodeSize = this.episodeList.size();
			VideoEpisode firstEpisode = this.episodeList.get(0);
			VideoEpisode secondEpisode = episodeSize >= 2 ? this.episodeList.get(1) : null;
			VideoEpisode thirdEpisode = episodeSize >= 3 ? this.episodeList.get(2) : null;
			VideoEpisode forthEpisode = episodeSize >= 4 ? this.episodeList.get(3) : null;
			if (episodeNo.equals(firstEpisode == null ? "" : firstEpisode.getEpisodeNo())) {
				episode = firstEpisode;
				nextEpisode = secondEpisode;
			} else if (episodeNo.equals(secondEpisode == null ? "" : secondEpisode.getEpisodeNo())) {
				preEpisode = firstEpisode;
				episode = secondEpisode;
				nextEpisode = thirdEpisode;
			} else if (episodeNo.equals(thirdEpisode == null ? "" : thirdEpisode.getEpisodeNo())) {
				preEpisode = secondEpisode;
				episode = thirdEpisode;
				nextEpisode = forthEpisode;
			} else if (episodeNo.equals(forthEpisode == null ? "" : forthEpisode.getEpisodeNo())) {
				preEpisode = thirdEpisode;
				episode = forthEpisode;
			}
			return PLAY_PAGE;
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

	public List<VideoEpisode> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<VideoEpisode> episodeList) {
		this.episodeList = episodeList;
	}
}
