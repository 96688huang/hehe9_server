package cn.hehe9.common.constants;

/**
 * 视频来源类型
 */
public enum ComicSourceName {
	SOHU(1, "搜狐"), YOUKU(2, "优酷");

	private int id;
	private String name;

	private ComicSourceName(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId(){
		return id;
	}
	
	public String getName() {
		return name;
	}
}
