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

	public List<Video> listBrief(int page, int count) {
		return videoDao.listBrief(page, count);
	}

	public List<Video> listExceptBigData(int page, int count) {
		return videoDao.listExceptBigData(page, count);
	}

	public List<Video> list(int page, int count) {
		return videoDao.list(page, count);
	}

	public List<Video> listBriefGroupByFirstChar(int countPerFirstChar) {
		return videoDao.listBriefGroupByFirstChar(countPerFirstChar);
	}

	public List<Video> findBriefByName(String firstChar, String name, int page, int queryCount) {
		return videoDao.findBriefBy(firstChar, name, page, queryCount);
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
}
