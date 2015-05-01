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
import cn.hehe9.persistent.entity.ComicEpisode;
import cn.hehe9.persistent.mapper.ComicEpisodeMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ComicEpisodeDao {

	@Resource
	private ComicEpisodeMapper comicEpisodeMapper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int save(ComicEpisode ve) {
		ve.setCreateTime(new Date());
		return comicEpisodeMapper.insertSelective(ve);
	}

	public ComicEpisode findByComicIdEpisodeNo(Integer comicId, Integer episodeNo) {
		List<ComicEpisode> episodes = findBy(comicId, 1, 1, episodeNo);
		return CollectionUtils.isEmpty(episodes) ? null : episodes.get(0);
	}

	public List<ComicEpisode> findEpisodesBy(Integer comicId, int page, int queryCount) {
		return findBy(comicId, page, queryCount);
	}

	/**
	 * 根据条件, 查询视频信息列表
	 * @param comicId		视频id
	 * @param page			查询页码
	 * @param count			查询数量
	 * @param episodeNos	集数(可多个)
	 * @return	视频列表
	 */
	public List<ComicEpisode> findBy(Integer comicId, int page, int queryCount, Integer... episodeNos) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comicId", comicId);
		params.put("episodeNos", ListUtil.asList(episodeNos));
		params.put("offset", (page - 1) * queryCount);
		params.put("count", queryCount);
		return comicEpisodeMapper.findBy(params);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int udpate(ComicEpisode ve) {
		return comicEpisodeMapper.updateByPrimaryKeySelective(ve);
	}

	public Integer countBy(Integer comicId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comicId", comicId);
		return comicEpisodeMapper.countBy(params);
	}

	public ComicEpisode findById(Integer episodeId) {
		return comicEpisodeMapper.selectByPrimaryKey(episodeId);
	}

	public List<ComicEpisode> findByRange(Integer comicId, int minEpisodeNo, int maxEpisodeNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comicId", comicId);
		params.put("minEpisodeNo", minEpisodeNo);
		params.put("maxEpisodeNo", maxEpisodeNo);
		return comicEpisodeMapper.findByRange(params);
	}
}
