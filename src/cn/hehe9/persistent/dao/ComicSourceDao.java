package cn.hehe9.persistent.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hehe9.persistent.entity.ComicSource;
import cn.hehe9.persistent.mapper.ComicSourceMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ComicSourceDao {

	@Resource
	private ComicSourceMapper comicSourceMapper;

	public List<ComicSource> list(int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return comicSourceMapper.findBy(params);
	}

	public ComicSource findBy(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("offset", 0);
		params.put("count", 1);
		List<ComicSource> result = comicSourceMapper.findBy(params);
		return CollectionUtils.isEmpty(result) ? null : result.get(0);
	}
}
