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
}
