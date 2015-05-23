package cn.hehe9.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.hehe9.common.constants.ComicSourceName;
import cn.hehe9.common.constants.VideoSourceName;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.Video;

/**
 * Action 助手类
 */
public class ActionHelper {

	/**
	 * 设置来源名称
	 */
	public static void setComicSourceName(List<Comic> comicList) {
		for (Comic comic : comicList) {
			setComicSourceName(comic);
		}
	}

	/**
	 * 设置来源名称
	 */
	public static void setComicSourceName(Comic comic) {
		int sourceId = comic.getSourceId().intValue();
		if (sourceId == ComicSourceName.TENCENT.getId()) {
			comic.setSourceName(ComicSourceName.TENCENT.getName());
		} else if (sourceId == ComicSourceName.COCO.getId()) {
			comic.setSourceName(ComicSourceName.COCO.getName());
		}
	}

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

	public static String cleanText(String text) {
		if (StringUtils.isNotBlank(text)) {
			text = text.replace("%", "");
			text = text.replace("<", "");
			text = text.replace(">", "");
			text = text.replace("&", "");
			int length = text.length();
			if (length > 20) {
				length = 20;
			}
			text = text.substring(0, length);
		}
		return text;
	}
}
