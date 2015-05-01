package cn.hehe9.service.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hehe9.persistent.dao.ComicDao;
import cn.hehe9.persistent.entity.Comic;

@Service
public class ComicService {
	@Resource
	private ComicDao comicDao;

	public List<Comic> listBrief(int page, int count) {
		return comicDao.listBrief(page, count);
	}

	public List<Comic> listExceptBigData(Integer sourceId, int page, int count) {
		return comicDao.listExceptBigData(sourceId, page, count);
	}

	public List<Comic> list(int page, int count) {
		return comicDao.list(page, count);
	}

	public List<Comic> listBriefGroupByFirstChar(int countPerFirstChar) {
		return comicDao.listBriefGroupByFirstChar(countPerFirstChar);
	}

	public List<Comic> findBriefByName(String firstChar, String name, int page, int queryCount) {
		return comicDao.findBriefBy(null, firstChar, name, page, queryCount);
	}

	public Comic findById(Integer comicId) {
		return comicDao.findById(comicId);
	}

	public Integer countBy(String firstChar, String name) {
		return comicDao.countBy(firstChar, name);
	}

	public Integer countBy() {
		return comicDao.countBy(null, null);
	}
}
