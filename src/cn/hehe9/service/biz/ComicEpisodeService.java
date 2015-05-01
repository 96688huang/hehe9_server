package cn.hehe9.service.biz;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cn.hehe9.persistent.dao.ComicEpisodeDao;
import cn.hehe9.persistent.entity.ComicEpisode;

@Service
public class ComicEpisodeService {

	@Resource
	private ComicEpisodeDao comicEpisodeDao;

	public List<ComicEpisode> listByRange(Integer comicId, int minEpisodeNo, int maxEpisodeNo) {
		return comicEpisodeDao.findByRange(comicId, minEpisodeNo, maxEpisodeNo);
	}
	
	public List<ComicEpisode> list(Integer comicId, int page, int queryCount) {
		return comicEpisodeDao.findEpisodesBy(comicId, page, queryCount);
	}

	public List<ComicEpisode> list(Integer comicId, int page, int queryCount, Integer... episodeNos) {
		return comicEpisodeDao.findBy(comicId, page, queryCount, episodeNos);
	}

	public Integer count(Integer comicId) {
		return comicEpisodeDao.countBy(comicId);
	}

	public ComicEpisode findById(Integer episodeId) {
		return comicEpisodeDao.findById(episodeId);
	}

	public ComicEpisode getMaxEpisode(Integer comicId) {
		List<ComicEpisode> result = this.list(comicId, 1, 1);
		return CollectionUtils.isEmpty(result) ? null : result.get(0);
	}
}
