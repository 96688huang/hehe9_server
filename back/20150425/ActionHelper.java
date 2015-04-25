package cn.hehe9.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import cn.hehe9.common.constants.ComConstant;
import cn.hehe9.common.constants.VideoSourceName;
import cn.hehe9.persistent.entity.Video;
import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.service.biz.VideoEpisodeService;
import cn.hehe9.service.biz.VideoService;

/**
 * Action 助手类
 */
public class ActionHelper {

	/**
	 * 设置来源名称
	 */
	public static void setSourceName(List<Video> videoList) {
		for (Video video : videoList) {
			setSourceName(video);
		}
	}

	/**
	 * 设置来源名称
	 */
	public static void setSourceName(Video video) {
		int sourceId = video.getSourceId().intValue();
		if (sourceId == VideoSourceName.SOHU.getId()) {
			video.setSourceName(VideoSourceName.SOHU.getName());
		} else if (sourceId == VideoSourceName.YOUKU.getId()) {
			video.setSourceName(VideoSourceName.YOUKU.getName());
		}
	}

	public static List<Map<Video, List<VideoEpisode>>> initHotEpisodes(VideoEpisodeService videoEpisodeService,
			List<Video> hotVideoList, int mainHotVideoCountForEpisode, int mainHotVideoEpisodeCount) {
		hotVideoList = hotVideoList.subList(0, mainHotVideoCountForEpisode);
		List<Map<Video, List<VideoEpisode>>> hotEpisodeListHolder = new ArrayList<Map<Video, List<VideoEpisode>>>();
		for (Video video : hotVideoList) {
			List<VideoEpisode> episodeList = videoEpisodeService.list(video.getId(), 1, mainHotVideoEpisodeCount);
			Map<Video, List<VideoEpisode>> map = new HashMap<Video, List<VideoEpisode>>(1);
			map.put(video, episodeList);
			hotEpisodeListHolder.add(map);
		}
		return hotEpisodeListHolder;
	}

	public static List<Video> getHotVideos(VideoService videoService, int mainHotVideoCont) {
		return videoService.listBrief(1, mainHotVideoCont);
	}

	public static Set<Set<String>> initHotVideos(VideoService videoService, List<String> hotVideoList,
			int mainHotVideoCountPerLine) {
		Set<Set<String>> hotVideoListHolder = new LinkedHashSet<Set<String>>();
		int count = 0;
		for (;;) {
			int preNextCount = count + mainHotVideoCountPerLine;
			int nextCount = preNextCount > hotVideoList.size() ? hotVideoList.size() : preNextCount;
			hotVideoListHolder.add(new HashSet<String>(hotVideoList.subList(count, nextCount)));
			count = preNextCount;

			if (nextCount >= hotVideoList.size()) {
				break;
			}
		}
		return hotVideoListHolder;
	}

	public static void initLetterVideos(VideoService videoService, Map<String, Set<String>> letterMenuVideoMap,
			int countPerFirstChar) {
		List<Video> letterVideoNameList = videoService.listBriefGroupByFirstChar(countPerFirstChar);
		for (;;) {
			if (CollectionUtils.isEmpty(letterVideoNameList)) {
				break;
			}

			// 归类
			Video video = letterVideoNameList.get(0);
			Set<String> groupVideNames = letterMenuVideoMap.get(video.getFirstChar().toUpperCase());
			if (groupVideNames == null) { // 此处只判断null，不要判断是否empty，因为初始化容器时， 是empty。
				groupVideNames = letterMenuVideoMap.get(ComConstant.OTHER_CNS);
			}
			groupVideNames.add(video.getName());

			// 删除该元素
			letterVideoNameList.remove(0);
		}
	}

	//	public static List<List<Video>> initHotVideos(VideoService videoService, List<Video> hotVideoList,
	//			int mainHotVideoCountPerLine) {
	//		List<List<Video>> hotVideoListHolder = new ArrayList<List<Video>>();
	//		int count = 0;
	//		for (;;) {
	//			int preNextCount = count + mainHotVideoCountPerLine;
	//			int nextCount = preNextCount > hotVideoList.size() ? hotVideoList.size() : preNextCount;
	//			hotVideoListHolder.add(hotVideoList.subList(count, nextCount));
	//			count = preNextCount;
	//			
	//			if (nextCount >= hotVideoList.size()) {
	//				break;
	//			}
	//		}
	//		return hotVideoListHolder;
	//	}
	//	
	//	public static void initLetterVideos(VideoService videoService, Map<String, List<Video>> letterMenuVideoMap,
	//			int countPerFirstChar) {
	//		List<Video> letterVideoList = videoService.listBriefGroupByFirstChar(countPerFirstChar);
	//		for (;;) {
	//			if (CollectionUtils.isEmpty(letterVideoList)) {
	//				break;
	//			}
	//			
	//			// 归类
	//			Video video = letterVideoList.get(0);
	//			List<Video> groupVides = letterMenuVideoMap.get(video.getFirstChar().toUpperCase());
	//			if (groupVides == null) { // 此处只判断null，不要判断是否empty，因为初始化容器时， 是empty。
	//				groupVides = letterMenuVideoMap.get(ComConstant.OTHER_CNS);
	//			}
	//			groupVides.add(video);
	//			
	//			// 删除该元素
	//			letterVideoList.remove(0);
	//		}
	//	}
}
