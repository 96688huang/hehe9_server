package cn.hehe9.common.constants;

/**
 * 视频来源类型
 */
public enum ComicSourceName {
	TENCENT(1, "腾讯"), COCO(2, "可可漫画");

	private int id;
	private String name;

	private ComicSourceName(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
