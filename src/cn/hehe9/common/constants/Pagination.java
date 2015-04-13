package cn.hehe9.common.constants;

/**
 * 分页参数
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：huangquan@ucweb.com
 * <br> 创建时间：2015年4月6日 下午2:41:41
 * <br>==========================
 */
public class Pagination {
	/** 页码 */
	private int page = 1;
	/** 查询数量 */
	private int queryCount = 21;
	/** 每页的数量 */
	private int pageCount = 21;
	/** 总数 */
	private int total;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		
		// calc page count
		int remainCount = total % queryCount > 0 ? 1 : 0;
		pageCount = (total / queryCount) + remainCount;
	}

}
