package cn.hehe9.persistent.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.persistent.mapper.VideoSourceMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class VideoSourceDao {

	@Resource
	private VideoSourceMapper videoSourceMapper;

	public List<VideoSource> list(int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoSourceMapper.findBy(params);
	}
}
