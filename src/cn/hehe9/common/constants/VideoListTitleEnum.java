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
public enum VideoListTitleEnum {
	/** 热门动画片 */
	VIDEOS_HOT("热门动画片"),

	/** 动画片大全 */
	VIDEO_BOOK("动画片大全"),

	/** 搜索结果 */
	SEARCH_RESULT("搜索结果");

	private String title;

	private VideoListTitleEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
