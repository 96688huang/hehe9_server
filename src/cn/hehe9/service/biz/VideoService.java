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

	public Video findById(Integer videoId) {
		return videoDao.findById(videoId);
	}

	public List<Video> findBriefByName(String name, int page, int queryCount) {
		return videoDao.findBriefBy(name, page, queryCount);
	}

	public Integer countBy(String name) {
		return videoDao.countBy(name);
	}

	public Integer countBy() {
		return videoDao.countBy(null);
	}
}
