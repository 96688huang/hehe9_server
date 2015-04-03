package cn.hehe9.service.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hehe9.persistent.dao.VideoSourceDao;
import cn.hehe9.persistent.entity.VideoSource;

@Service
public class VideoSourceService {
	@Resource
	private VideoSourceDao videoSourceDao;

	public List<VideoSource> list() {
		return videoSourceDao.list(1, Integer.MAX_VALUE);
	}
}
