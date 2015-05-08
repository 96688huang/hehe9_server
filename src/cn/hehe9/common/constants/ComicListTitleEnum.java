package cn.hehe9.common.constants;

/**
 * 视频列表(页面)标题
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：huangquan@ucweb.com
 * <br> 创建时间：2015年4月6日 下午2:26:10
 * <br>==========================
 */
public enum ComicListTitleEnum {
	/** 热门动画片 */
	VIDEOS_HOT("热门漫画"),

	/** 动画片大全 */
	VIDEO_BOOK("漫画大全"),
	
	/** 动画片大全 */
	FIRST_CHAR_VIDEO(" 字母漫画"),

	/** 搜索结果 */
	SEARCH_RESULT("漫画搜索结果");

	private String title;

	private ComicListTitleEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
