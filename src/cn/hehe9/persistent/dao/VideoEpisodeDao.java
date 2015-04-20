package cn.hehe9.persistent.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hehe9.common.utils.ListUtil;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.persistent.mapper.VideoEpisodeMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class VideoEpisodeDao {

	@Resource
	private VideoEpisodeMapper videoEpisodeMapper;

	public int save(VideoEpisode ve) {
		ve.setCreateTime(new Date());
		return videoEpisodeMapper.insertSelective(ve);
	}

	public VideoEpisode findByVideoIdEpisodeNo(Integer videoId, Integer episodeNo) {
		List<VideoEpisode> episodes = findBy(videoId, 1, 1, episodeNo);
		return CollectionUtils.isEmpty(episodes) ? null : episodes.get(0);
	}

	public List<VideoEpisode> findEpisodesBy(Integer videoId, int page, int queryCount) {
		return findBy(videoId, page, queryCount);
	}

	/**
	 * 根据条件, 查询视频信息列表
	 * @param videoId		视频id
	 * @param page			查询页码
	 * @param count			查询数量
	 * @param episodeNos	集数(可多个)
	 * @return	视频列表
	 */
	public List<VideoEpisode> findBy(Integer videoId, int page, int queryCount, Integer... episodeNos) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("videoId", videoId);
		params.put("episodeNos", ListUtil.asList(episodeNos));
		params.put("offset", (page - 1) * queryCount);
		params.put("count", queryCount);
		return videoEpisodeMapper.findBy(params);
	}

	public int udpate(VideoEpisode ve) {
		return videoEpisodeMapper.updateByPrimaryKeySelective(ve);
	}

	public Integer countBy(Integer videoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("videoId", videoId);
		return videoEpisodeMapper.countBy(params);
	}

	public VideoEpisode findById(Integer episodeId) {
		return videoEpisodeMapper.selectByPrimaryKey(episodeId);
	}

	public List<VideoEpisode> findByRange(Integer videoId, int minEpisodeNo, int maxEpisodeNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("videoId", videoId);
		params.put("minEpisodeNo", minEpisodeNo);
		params.put("maxEpisodeNo", maxEpisodeNo);
		return videoEpisodeMapper.findByRange(params);
	}
}
