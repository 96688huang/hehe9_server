package cn.hehe9.common.app;

/**
 * 缓存key枚举类
 * <br> 创建时间：2015年4月15日 下午4:24:04
 */
public enum CacheKeyEnum {

	/** index_page - 首页 */
	INDEX_PAGE("index_page", "首页"),

	SOURCE_IDS("source_ids", "来源id"),

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
