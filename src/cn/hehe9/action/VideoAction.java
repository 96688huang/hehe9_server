package cn.hehe9.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class VideoAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(VideoAction.class);

	@Resource
	private VideoService videoService;

	@Resource
	private VideoEpisodeService videoEpisodeService;

	private List<Video> hotVideoList;
	private List<List<Video>> hotVideoListHolder;

	private List<VideoEpisode> hotEpisodeList;
	private List<Map<Video, List<VideoEpisode>>> hotEpisodeListHolder;

	private List<Video> menuVideoList;

	/** 首页热门视频数量 */
	private final int MAIN_HOT_VIDEOS_COUNT = 21;
	/** 首页每行热门视频的数量 */
	private final int MAIN_HOT_VIDEOS_COUNT_PER_LINE_ = 7;
	/** 首页热门视频数量(用于展示分集) */
	private final int MAIN_HOT_VIDEOS_COUNT_FOR_EPISODE = 4;
	/** 分集数量 */
	private final int MAIN_HOT_VIDEOS_ESPICODE_COUNT = 10;

	/** 动画片大全的视频数量 */
	private final int MAIN_MENU_VIDEOS_COUNT = 40;

	public String list() {
		List<Video> list = videoService.listBrief(1, MAIN_HOT_VIDEOS_COUNT);
		System.err.println(list.get(0).toString());
		if (list == null || list.isEmpty()) {
			return null;
		}
		String videoList = JacksonUtil.encodeQuietly(list);
		return "toMain";
	}

	public String toMain() {
		// TODO 查看缓存中是否存在首页

		// 最热门的视频列表
		hotVideoList = videoService.listBrief(1, MAIN_HOT_VIDEOS_COUNT);
		hotVideoListHolder = new ArrayList<List<Video>>();
		int count = 0;
		for (;;) {
			int preNextCount = count + MAIN_HOT_VIDEOS_COUNT_PER_LINE_;
			int nextCount = preNextCount > hotVideoList.size() ? hotVideoList.size() : preNextCount;
			hotVideoListHolder.add(hotVideoList.subList(count, nextCount));
			count = preNextCount;

			if (nextCount >= hotVideoList.size()) {
				break;
			}
		}

		// 最热门的视频分集列表
		hotVideoList = videoService.listBrief(1, MAIN_HOT_VIDEOS_COUNT_FOR_EPISODE);
		hotEpisodeListHolder = new ArrayList<Map<Video, List<VideoEpisode>>>();
		for (Video video : hotVideoList) {
			List<VideoEpisode> episodeList = videoEpisodeService.list(video.getId(), 1, MAIN_HOT_VIDEOS_ESPICODE_COUNT);
			Map<Video, List<VideoEpisode>> map = new HashMap<Video, List<VideoEpisode>>(1);
			map.put(video, episodeList);
			hotEpisodeListHolder.add(map);
		}

		// 动画片大全
		menuVideoList = videoService.listBrief(1, MAIN_MENU_VIDEOS_COUNT);

		// TODO 把首页加入缓存

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

	public List<Map<Video, List<VideoEpisode>>> getHotEpisodeListHolder() {
		return hotEpisodeListHolder;
	}

	public void setHotEpisodeListHolder(List<Map<Video, List<VideoEpisode>>> hotEpisodeListHolder) {
		this.hotEpisodeListHolder = hotEpisodeListHolder;
	}

	public List<Video> getMenuVideoList() {
		return menuVideoList;
	}

	public void setMenuVideoList(List<Video> menuVideoList) {
		this.menuVideoList = menuVideoList;
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
