package cn.hehe9.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.utils.DateUtil;
import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.common.utils.StringUtil;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.VideoEpisodeService;
import cn.hehe9.service.biz.VideoService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class VideoAction extends ActionSupport{

	private static final Logger logger = LoggerFactory
			.getLogger(VideoAction.class);

	@Resource
	private VideoService videoService;
	
	@Resource
	private VideoEpisodeService videoEpisodeService;
	
	private List<Video> hotVideoList;
	private List<List<Video>> hotVideoListHolder;
	
	private List<VideoEpisode> hotEpisodeList;
	private List<List<VideoEpisode>> hotEpisodeListHolder;

	public String list() {
		List<Video> list = videoService.listBrief(1, 21);
		System.err.println(list.get(0).toString());
		if (list == null || list.isEmpty()) {
			return null;
		}
		String videoList =  JacksonUtil.encodeQuietly(list);
		return "toMain";
	}

	public String toMain(){
		// 最热门的视频列表
		hotVideoList = videoService.listBrief(1, 21);
		hotVideoListHolder = new ArrayList<List<Video>>();
		int count = 0;
		for(;;){
			int nextCount = count+7 > hotVideoList.size() ? hotVideoList.size() : count+7;
			hotVideoListHolder.add(hotVideoList.subList(count, nextCount));
			count += 7;
			
			if(nextCount >= hotVideoList.size()){
				break;
			}
		}
		
		// 最热门的视频分集列表
		hotVideoList = videoService.listBrief(1, 3);
		hotEpisodeListHolder = new ArrayList<List<VideoEpisode>>();
		for(Video video : hotVideoList){
			hotEpisodeListHolder.add(videoEpisodeService.list(video.getId(), 1, 10));
		}
		return "toMain";
	}
	
	public List<Video> getHotVideoList() {
		return hotVideoList;
	}
	
	public void setHotVideoList(List<Video> hotVideoList) {
		this.hotVideoList = hotVideoList;
	}
	
	public List<List<Video>> getHotVideoListHolder() {
		return hotVideoListHolder;
	}
	
	public void setHotVideoListHolder(List<List<Video>> hotVideoListHolder) {
		this.hotVideoListHolder = hotVideoListHolder;
	}
	
	public List<VideoEpisode> getHotEpisodeList() {
		return hotEpisodeList;
	}
	
	public void setHotEpisodeList(List<VideoEpisode> hotEpisodeList) {
		this.hotEpisodeList = hotEpisodeList;
	}
	
	public List<List<VideoEpisode>> getHotEpisodeListHolder() {
		return hotEpisodeListHolder;
	}
	
	public void setHotEpisodeListHolder(
			List<List<VideoEpisode>> hotEpisodeListHolder) {
		this.hotEpisodeListHolder = hotEpisodeListHolder;
	}
	
	// List<List<Map<String,Object>>> jsonList = new
	// ArrayList<List<Map<String,Object>>>();
	// List<Map<String, Object>> itemList = new ArrayList<Map<String,Object>>();
	// for(int i = 0; i < list.size(); i ++)
	// {
	// if(i==7){ // 7个视频为一排
	// jsonList.add(itemList);
	// itemList = new ArrayList<Map<String,Object>>();
	// }
	//
	// Map<String,Object> map=new HashMap<String, Object>();
	// map.put("word", rs.getString("word"));
	// map.put("wordcount", rs.getInt("wordcount"));
	// list.add(map);
	// }
	// JSONArray jsarry = new JSONArray();
	// jsarry=JSONArray.fromObject(list);
	// result=jsarry.toString();
	// }
}
