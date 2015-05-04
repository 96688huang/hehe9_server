package cn.hehe9.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicEpisode;
import cn.hehe9.service.biz.ComicEpisodeService;
import cn.hehe9.service.biz.ComicService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ComicReadAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3207061567116252849L;

	private static final Logger logger = LoggerFactory.getLogger(ComicReadAction.class);

	@Resource
	private ComicService comicService;

	@Resource
	private ComicEpisodeService comicEpisodeService;

	// --- 请求参数 ---
	/** 漫画id */
	private Integer comicId;

	/** 漫画分集id */
	private Integer episodeId;

	/** 漫画分集集数 */
	private Integer episodeNo;

	// --- 回复参数 ---
	/** 漫画信息 */
	private Comic comic;

	/** 上一集采漫画信息 */
	private ComicEpisode preEpisode;

	/** (当前播放的)漫画分集信息 */
	private ComicEpisode episode;

	/** 下一级漫画信息 */
	private ComicEpisode nextEpisode;

	/** 漫画最近几个分集的数量 */
	private static final int NEAR_EPISODE_COUNT = 3;

	/** 最大分集与最小分集, 距离当前点播分集的上下间隔 */
	private static final int EPISODE_INTERVAL = 2;

	/** 播放页右边的分集列表 */
	private List<ComicEpisode> episodeList;

	private static final String MAIN_PAGE = PageUrlFlagEnum.MAIN_PAGE.getUrlFlag();
	private static final String READ_PAGE = PageUrlFlagEnum.READ_PAGE.getUrlFlag();
	private static final String READ_JUMP_PAGE = PageUrlFlagEnum.READ_JUMP_PAGE.getUrlFlag();

	public String read() {
		if (comicId <= 0 || episodeId <= 0 || episodeNo <= 0) {
			return MAIN_PAGE;
		}

		comic = comicService.findById(comicId);

		// 播放页右边的分集列表
		ComicEpisode maxEpisode = comicEpisodeService.getMaxEpisode(comicId);
		if (maxEpisode == null) {
			return READ_PAGE;
		}

		int maxEpisodeNo = maxEpisode.getEpisodeNo();
		int minEpisodeNo = episodeNo;
		if (episodeNo + EPISODE_INTERVAL <= maxEpisodeNo) {
			maxEpisodeNo = episodeNo + EPISODE_INTERVAL;
		}
		int tmp = maxEpisodeNo - (2 * EPISODE_INTERVAL) + 1;
		minEpisodeNo = tmp > 0 ? tmp : 0;

		this.episodeList = comicEpisodeService.listByRange(comicId, minEpisodeNo, maxEpisodeNo);
		if (CollectionUtils.isEmpty(this.episodeList)) {
			return READ_PAGE;
		}

		// 倒序(分集越小, 越排在前面)
		List<ComicEpisode> tmpList = new ArrayList<ComicEpisode>(this.episodeList.size());
		for (int i = this.episodeList.size() - 1; i >= 0; i--) {
			tmpList.add(this.episodeList.get(i));
		}
		this.episodeList.clear();
		this.episodeList = tmpList;

		// 上集/当前集/下集
		int episodeSize = this.episodeList.size();
		ComicEpisode firstEpisode = this.episodeList.get(0);
		ComicEpisode secondEpisode = episodeSize >= 2 ? this.episodeList.get(1) : null;
		ComicEpisode thirdEpisode = episodeSize >= 3 ? this.episodeList.get(2) : null;
		ComicEpisode forthEpisode = episodeSize >= 4 ? this.episodeList.get(3) : null;
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
		
		if(StringUtils.isBlank(episode.getPicUrls())){
			return READ_JUMP_PAGE;
		}else{
			return READ_PAGE;
		}
	}

	public Integer getComicId() {
		return comicId;
	}

	public void setComicId(Integer comicId) {
		this.comicId = comicId;
	}

	public ComicEpisode getPreEpisode() {
		return preEpisode;
	}

	public void setPreEpisode(ComicEpisode preEpisode) {
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

	public ComicEpisode getNextEpisode() {
		return nextEpisode;
	}

	public void setNextEpisode(ComicEpisode nextEpisode) {
		this.nextEpisode = nextEpisode;
	}

	public Comic getComic() {
		return comic;
	}

	public void setComic(Comic comic) {
		this.comic = comic;
	}

	public ComicEpisode getEpisode() {
		return episode;
	}

	public void setEpisode(ComicEpisode episode) {
		this.episode = episode;
	}

	public List<ComicEpisode> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<ComicEpisode> episodeList) {
		this.episodeList = episodeList;
	}
}
