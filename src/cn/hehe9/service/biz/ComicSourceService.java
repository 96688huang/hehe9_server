package cn.hehe9.service.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hehe9.persistent.dao.ComicSourceDao;
import cn.hehe9.persistent.entity.ComicSource;

@Service
public class ComicSourceService {
	@Resource
	private ComicSourceDao comicSourceDao;

	public List<ComicSource> list() {
		return comicSourceDao.list(1, Integer.MAX_VALUE);
	}

	public ComicSource findByName(String name) {
		return comicSourceDao.findBy(name);
	}
}
