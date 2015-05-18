package cn.hehe9.common.app;

/**
 * 缓存key枚举类
 * <br> 创建时间：2015年4月15日 下午4:24:04
 */
public enum CacheKeyEnum {

	/** index_page - 首页 */
	INDEX_PAGE("index_page", "首页"),
	
	SITEMAP_VIDEOS("sitemap_videos", "网站地图-视频部分"),
	
	SITEMAP_COMICS("sitemap_comics", "网站地图-漫画部分"),

	VIDEO_SOURCE_IDS("video_source_ids", "视频来源id"),
	
	COMIC_SOURCE_IDS("comic_source_ids", "漫画来源id"),

	SOURCE_COMICS("source_comics", "按来源分类的漫画列表"),
	
	SOURCE_VIDEOS("source_videos", "按来源分类的视频列表");

	private String key;
	private String desc;

	private CacheKeyEnum(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}
}
