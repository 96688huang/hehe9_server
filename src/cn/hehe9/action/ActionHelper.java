package cn.hehe9.action;

import java.util.List;

import cn.hehe9.common.constants.VideoSourceName;
import cn.hehe9.persistent.entity.Video;

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
}
