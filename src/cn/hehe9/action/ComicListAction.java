package cn.hehe9.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.app.AppConfig;
import cn.hehe9.common.constants.ComicListTitleEnum;
import cn.hehe9.common.constants.PageUrlFlagEnum;
import cn.hehe9.common.constants.Pagination;
import cn.hehe9.common.utils.UrlEncodeUtil;
import cn.hehe9.persistent.entity.Comic;
import cn.hehe9.service.biz.CacheService;
import cn.hehe9.service.biz.ComicService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ComicListAction extends ActionSupport {

	/**
	 * serialVersionUID
	 *
	 */
	private static final long serialVersionUID = -2496736327947639225L;

	private static final Logger logger = LoggerFactory.getLogger(ComicListAction.class);

	@Resource
	private ComicService comicService;

	@Resource
	private CacheService cacheService;

	/** 列表页漫画的数量 */
	private final int VIDEOS_COUNT_PER_LINE = 7;

	//--- 请求参数 ----
	/** 漫画名称 */
	private String searchName;
	// 默认采用base64编码(如果是form表单提交， 则无需要采用base64编码)
	private Boolean isSearchNameEncode = true;
	/** 漫画名称首字母 */
	private String firstChar;

	//---- 回复参数
	/** 展示的标题 */
	private String displayTitle;

	/** 漫画列表容器 */
	private List<List<Comic>> comicListHolder = new ArrayList<List<Comic>>(VIDEOS_COUNT_PER_LINE);;

	/** 分页参数*/
	private Pagination pagination = new Pagination();

	private static final String COMIC_LIST_PAGE = PageUrlFlagEnum.COMIC_LIST_PAGE.getUrlFlag();

	public String list() throws Exception {
		try {
			searchName = isSearchNameEncode ? UrlEncodeUtil.base64Decode(searchName) : searchName;
			firstChar = UrlEncodeUtil.base64Decode(firstChar);

			if (StringUtils.isNotBlank(searchName)) {
				displayTitle = searchName + "  " + ComicListTitleEnum.SEARCH_RESULT.getTitle();
			} else if (StringUtils.isNotBlank(firstChar)) {
				displayTitle = firstChar.trim() + ComicListTitleEnum.FIRST_CHAR_VIDEO.getTitle();
			} else {
				displayTitle = ComicListTitleEnum.VIDEO_BOOK.getTitle();
			}

			// 先从缓存中取
			List<Comic> comicList = null;
			AtomicInteger total = new AtomicInteger(0);
			if (AppConfig.MEMCACHE_ENABLE) {
				List<Integer> sourceIdList = cacheService.getOrCreateVideoSourceIdsCache();
				comicList = pickComicsFromCache(total, sourceIdList);
			}

			// 如果缓存中没有, 则从DB中取
			if (CollectionUtils.isEmpty(comicList)) {
				comicList = pickComicsFromDb(total);
			}

			// 排版
			int count = 0;
			for (;;) {
				int preNextCount = count + VIDEOS_COUNT_PER_LINE;
				int nextCount = preNextCount > comicList.size() ? comicList.size() : preNextCount;
				comicListHolder.add(comicList.subList(count, nextCount));
				count = preNextCount;

				if (nextCount >= comicList.size()) {
					break;
				}
			}
			pagination.setTotal(total.get());
			return COMIC_LIST_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	private List<Comic> pickComicsFromDb(AtomicInteger total) {
		List<Comic> comicList;
		comicList = comicService.findBriefByName(firstChar, searchName, pagination.getPage(),
				pagination.getQueryCount());

		// 赋值漫画来源名称
		ActionHelper.setComicSourceName(comicList);

		total.set(comicService.countBy(firstChar, searchName));
		return comicList;
	}

	private List<Comic> pickComicsFromCache(AtomicInteger total, List<Integer> sourceIdList) {
		if (CollectionUtils.isEmpty(sourceIdList)) {
			return null;
		}

		List<Comic> queryComics = new ArrayList<Comic>(30);
		int fromIndex = (pagination.getPage() - 1) * pagination.getQueryCount();
		int toIndex = fromIndex + pagination.getQueryCount() + 1;

		List<Comic> allComics = cacheService.getOrCreateSourceComicsCache(sourceIdList);
		for (Comic comic : allComics) {
			if (StringUtils.isNotBlank(searchName) && comic.getName().contains(searchName)) {
				pickComics(total, queryComics, toIndex, comic);
			} else if (StringUtils.isNotBlank(firstChar) && comic.getFirstChar().equalsIgnoreCase(firstChar)) {
				pickComics(total, queryComics, toIndex, comic);
			} else if (StringUtils.isBlank(searchName) && StringUtils.isBlank(firstChar)) { // 无查询条件, 则查询所有
				pickComics(total, queryComics, toIndex, comic);
			}
		}

		if (total.get() == 0 || CollectionUtils.isEmpty(queryComics)) {
			return null;
		}

		// sub list by query page and count
		toIndex = Math.min(total.get(), toIndex); // 可能查询的数量大于总数
		toIndex = Math.min(toIndex, queryComics.size()); // 可能元素数量少于查询的数量
		fromIndex = Math.min(fromIndex, toIndex); // 预防非法篡改分页参数
		queryComics = queryComics.subList(fromIndex, toIndex);
		return queryComics;
	}

	/**
	 * 根据需求, 挑选漫画
	 *
	 * @param total			符合条件的漫画总数
	 * @param queryComics	(查询结果得到的)漫画列表
	 * @param toIndex		要查询到的下标
	 * @param comic			漫画信息
	 */
	private void pickComics(AtomicInteger total, List<Comic> queryComics, int toIndex, Comic comic) {
		if (total.get() < toIndex) {
			queryComics.add(comic);
		}
		total.incrementAndGet();
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Boolean getIsSearchNameEncode() {
		return isSearchNameEncode;
	}
	
	public void setIsSearchNameEncode(Boolean isSearchNameEncode) {
		this.isSearchNameEncode = isSearchNameEncode;
	}
	
	public String getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}

	public List<List<Comic>> getComicListHolder() {
		return comicListHolder;
	}

	public void setComicListHolder(List<List<Comic>> comicListHolder) {
		this.comicListHolder = comicListHolder;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
