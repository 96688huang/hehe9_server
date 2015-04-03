package cn.hehe9.persistent.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.mapper.VideoMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class VideoDao {

	@Resource
	private VideoMapper videoMapper;

	public List<Video> list(int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findBy(params);
	}

	public List<Video> listBrief(int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findBriefBy(params);
	}

	/**
	 * 根据名称, 查询简要信息
	 * @param name
	 * @return
	 */
	public List<Video> searchBriefByName(String name) {
		return findBriefBy(name);
	}

	/**
	 * 根据条件, 查询简要信息
	 * @param name
	 * @return
	 */
	public List<Video> findBriefBy(String name) {
		return findBriefBy(name, 1, Integer.MAX_VALUE);
	}

	/**
	 * 根据条件, 查询简要信息
	 * @param name	名称
	 * @param page	查询页码
	 * @param count	查询数量
	 * @return
	 */
	public List<Video> findBriefBy(String name, int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", name);
		}

		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findBriefBy(params);
	}

	/**
	 * 根据条件, 查询视频信息
	 * @param name
	 * @return
	 */
	public List<Video> findBy(String name) {
		return findBriefBy(name, 1, Integer.MAX_VALUE);
	}

	/**
	 * 根据条件, 查询视频信息
	 * @param name	名称
	 * @param page	查询页码
	 * @param count	查询数量
	 * @return
	 */
	public List<Video> findBy(String name, int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", name);
		}

		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findBy(params);
	}

	public int save(Video video) {
		video.setCreateTime(new Date());
		return videoMapper.insertSelective(video);
	}

	public int udpate(Video video) {
		return videoMapper.updateByPrimaryKeySelective(video);
	}

}
