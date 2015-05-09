package cn.hehe9.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.common.constants.Pagination;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.persistent.entity.ComicEpisode;
import cn.hehe9.service.biz.ComicEpisodeService;
import cn.hehe9.service.biz.ComicService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ComicEpisodeAction extends ActionSupport {

	/**
	 * serialVersionUID
	 *
	 */
	private static final long serialVersionUID = 760585573175136203L;

	private static final Logger logger = LoggerFactory.getLogger(ComicEpisodeAction.class);

	@Resource
	private ComicService comicService;

	@Resource
	private ComicEpisodeService comicEpisodeService;

	/** 漫画id */
	private Integer comicId;

	/** 漫画信息 */
	private Comic comic;

	//	/** 页码 */
	//	private Integer page = 1;
	//
	//	/** 查询分集的数量 */
	//	private Integer queryCount = 20;
	//
	//	/** 页的数量 */
	//	private Integer pageCount;
	//
	//	/** 分集总数 */
	//	private Integer total;

	private Pagination pagination;

	/** 分集列表 */
	private List<ComicEpisode> episodeList;

	private static final String COMIC_EPISODE_LIST_PAGE = PageUrlFlagEnum.COMIC_EPISODE_LIST_PAGE.getUrlFlag();

	public String list() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
			}

			comic = comicService.findById(comicId);
			episodeList = comicEpisodeService.list(comicId, pagination.getPage(), pagination.getPageCount());
			pagination.setTotal(comicEpisodeService.count(comicId));
			return COMIC_EPISODE_LIST_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public Integer getComicId() {
		return comicId;
	}

	public void setComicId(Integer comicId) {
		this.comicId = comicId;
	}

	public Comic getComic() {
		return comic;
	}

	public void setComic(Comic comic) {
		this.comic = comic;
	}

	//	public Integer getPage() {
	//		return page;
	//	}
	//
	//	public void setPage(Integer page) {
	//		this.page = page;
	//	}
	//
	//	public Integer getQueryCount() {
	//		return queryCount;
	//	}
	//
	//	public void setQueryCount(Integer queryCount) {
	//		this.queryCount = queryCount;
	//	}
	//
	//	public Integer getPageCount() {
	//		return pageCount;
	//	}
	//
	//	public void setPageCount(Integer pageCount) {
	//		this.pageCount = pageCount;
	//	}
	//
	//	public Integer getTotal() {
	//		return total;
	//	}
	//
	//	public void setTotal(Integer total) {
	//		this.total = total;
	//	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<ComicEpisode> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<ComicEpisode> episodeList) {
		this.episodeList = episodeList;
	}
}
