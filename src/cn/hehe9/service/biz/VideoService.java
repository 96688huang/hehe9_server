package cn.hehe9.service.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hehe9.persistent.dao.VideoDao;
import cn.hehe9.persistent.entity.Video;

@Service
public class VideoService {
	@Resource
	private VideoDao videoDao;

	public int save(Video video){
		return videoDao.save(video);
	}
	
	public List<Video> listBrief(int page, int count) {
		return videoDao.listBrief(page, count);
	}

	public List<Video> listExceptBigData(Integer sourceId, int page, int count) {
		return videoDao.listExceptBigData(sourceId, page, count);
	}

	public List<Video> list(int page, int count) {
		return videoDao.list(page, count);
	}

	public List<Video> listBriefGroupByFirstChar(int countPerFirstChar) {
		return videoDao.listBriefGroupByFirstChar(countPerFirstChar);
	}

	public List<Video> findBriefByName(String firstChar, String name, int page, int queryCount) {
		return videoDao.findBriefBy(null, firstChar, name, null, page, queryCount);
	}

	public Video findById(Integer videoId) {
		return videoDao.findById(videoId);
	}

	public Integer countBy(String firstChar, String name) {
		return videoDao.countBy(firstChar, name);
	}

	public Integer countBy() {
		return videoDao.countBy(null, null);
	}

	public List<Video> findBriefByListPageUrl(Integer sourceId, String listPageUrl) {
		return videoDao.findBriefByListPageUrl(sourceId, listPageUrl);
	}

	public int updateRank(Integer sourceId, int rank) {
		return videoDao.updateRank(sourceId, rank);
	}

	public int update(Video video) {
		return videoDao.udpate(video);
	}
}
