package cn.hehe9.common.constants;

/**
 * 视频来源类型
 */
public enum VideoSourceName {
	SOHU("搜狐"), YOUKU("优酷");

	private String name;

	private VideoSourceName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
