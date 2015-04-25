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

	/**
	 * 查询视频列表: 仅查询简要的数据
	 */
	public List<Video> listBrief(int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findBriefBy(params);
	}

	/**
	 * 查询视频列表: 排除大数据量的字段
	 */
	public List<Video> listExceptBigData(Integer sourceId, int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		int offset = (page - 1) * count;
		params.put("sourceId", sourceId);
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findExceptBigDataBy(params);
	}

	/**
	 * 查询视频列表: 查询视频的所有的数据
	 */
	public List<Video> list(int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findBy(params);
	}

	/**
	 * 根据名称, 查询简要信息
	 * @param name
	 * @return
	 */
	public List<Video> searchBriefByName(Integer sourceId, String name) {
		return findBriefBy(sourceId, null, name);
	}

	/**
	 * 根据名称, 查询简要信息
	 * @param name
	 * @return
	 */
	public List<Video> searchBriefByName(String name) {
		return findBriefBy(null, null, name);
	}

	/**
	 * 根据条件, 查询简要信息
	 * @param name
	 * @return
	 */
	public List<Video> findBriefBy(Integer sourceId, String firstChar, String name) {
		return findBriefBy(sourceId, firstChar, name, 1, Integer.MAX_VALUE);
	}

	/**
	 * 根据条件, 查询简要信息
	 * @param name	名称
	 * @param page	查询页码
	 * @param count	查询数量
	 * @return
	 */
	public List<Video> findBriefBy(Integer sourceId, String firstChar, String name, int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceId", sourceId);
		
		if (StringUtils.isNotBlank(firstChar)) {
			params.put("firstChar", firstChar);
		}
		if (StringUtils.isNotBlank(name)) {
			params.put("name", name);
		}

		int offset = (page - 1) * count;
		params.put("offset", offset);
		params.put("count", count);
		return videoMapper.findBriefBy(params);
	}

	/**
	 * 根据条件, 查询视频信息
	 *
	 * @param videoId	视频id
	 * @return	视频信息
	 */
	public Video findById(Integer videoId) {
		return videoMapper.selectByPrimaryKey(videoId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int save(Video video) {
		video.setCreateTime(new Date());
		return videoMapper.insertSelective(video);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int udpate(Video video) {
		return videoMapper.updateByPrimaryKeySelective(video);
	}

	public Integer countBy(String firstChar, String name) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		if (StringUtils.isNotBlank(firstChar)) {
			params.put("firstChar", firstChar);
		}
		if (StringUtils.isNotBlank(name)) {
			params.put("name", name);
		}
		return videoMapper.countBy(params);
	}

	/**
	 * 按首字母分组查询记录
	 * @param countPerFirstChar	每组字母查询前多少条记录
	 * @return	视频列表
	 */
	public List<Video> listBriefGroupByFirstChar(int countPerFirstChar) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countPerFirstChar", countPerFirstChar);
		return videoMapper.listBriefGroupByFirstChar(params);
	}
}
