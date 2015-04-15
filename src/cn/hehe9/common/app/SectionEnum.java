package cn.hehe9.common.app;

public enum SectionEnum {
	APP("app"), MEMCACHE("memcache");

	private String section;

	private SectionEnum(String section) {
		this.section = section;
	}

	public String val() {
		return section;
	}
}
