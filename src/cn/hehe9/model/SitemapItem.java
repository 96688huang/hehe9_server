package cn.hehe9.model;

import java.io.Serializable;

public class SitemapItem implements Serializable{
	/**
	 * serialVersionUID
	 *
	 */
	private static final long serialVersionUID = -3447796865204351344L;
	
	private String url;
	private String title;

	public SitemapItem() {

	}

	public SitemapItem(String url, String title) {
		this.url = url;
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
