package cn.hehe9.service.biz;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cn.hehe9.persistent.dao.VideoEpisodeDao;
import cn.hehe9.persistent.entity.VideoEpisode;

@Service
public class VideoEpisodeService {

	@Resource
	private VideoEpisodeDao videoEpisodeDao;

	public List<VideoEpisode> listByRange(Integer videoId, int minEpisodeNo, int maxEpisodeNo) {
		return videoEpisodeDao.findByRange(videoId, minEpisodeNo, maxEpisodeNo);
	}

	public List<VideoEpisode> list(Integer videoId, int page, int queryCount) {
		return videoEpisodeDao.findEpisodesBy(videoId, page, queryCount);
	}

	public List<VideoEpisode> list(Integer videoId, int page, int queryCount, Integer... episodeNos) {
		return videoEpisodeDao.findBy(videoId, null, page, queryCount, episodeNos);
	}

	public Integer count(Integer videoId) {
		return videoEpisodeDao.countBy(videoId);
	}

	public VideoEpisode findById(Integer episodeId) {
		return videoEpisodeDao.findById(episodeId);
	}

	public VideoEpisode getMaxEpisode(Integer videoId) {
		List<VideoEpisode> result = this.list(videoId, 1, 1);
		return CollectionUtils.isEmpty(result) ? null : result.get(0);
	}
}
