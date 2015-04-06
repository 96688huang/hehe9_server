package cn.hehe9.common.constants;

/**
 * 页面url(用于struts跳转)
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：huangquan@ucweb.com
 * <br> 创建时间：2015年4月6日 下午3:36:49
 * <br>==========================
 */
public enum PageUrlFlagEnum {
	/** 首页 */
	MAIN_PAGE("toMain"),

	/** 视频列表页面 */
	LIST_PAGE("toList"),

	/** 分集列表页面 */
	EPISODE_LIST_PAGE("toEpisodeList"),

	/** 播放页面 */
	PLAY_PAGE("toPlay");

	private String urlFlag;

	private PageUrlFlagEnum(String urlFlag) {
		this.urlFlag = urlFlag;
	}

	public String getUrlFlag() {
		return urlFlag;
	}
}
