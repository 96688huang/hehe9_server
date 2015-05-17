package cn.hehe9.common.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import cn.hehe9.common.constants.ComConstant;

import com.twogotrade.common.utils.IniReader;
import com.twogotrade.common.utils.PathUtils;

public class AppConfig {
	private AppConfig() {

	}

	private static String prefix = SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_LINUX ? "/"
			: "";
	private static String abstractUrl = PathUtils.find("/app.ini");
	private static IniReader config = new IniReader(
			StringUtils.isNotBlank(prefix) && !abstractUrl.startsWith("/") ? prefix + abstractUrl : abstractUrl);

	private static String APP = SectionEnum.APP.val();
	private static String MEMCACHE = SectionEnum.MEMCACHE.val();

	/** 别名数组 */
	public static final String[] ALIAS_ARR = config.getStringArray(APP, "video_alias");
	/** 别名map */
	public static final Map<String, String> ALIAS_MAP = new HashMap<String, String>();
	/** 内容最大长度 */
	public static final int STORYLINE_MAX_LENGTH = config.getInt(APP, "storyline_max_length");
	public static final int AUTHOR_MAX_LENGTH = config.getInt(APP, "author_max_length");
	public static final int TITLE_MAX_LENGTH = config.getInt(APP, "title_max_length");

	/** 是否启用 memcache */
	public static final Boolean MEMCACHE_ENABLE = config.getBoolean(MEMCACHE, "memcache_enable", false);
	public static final String MEMCACHE_HOST = config.getString(MEMCACHE, "memcache_host");
	public static final String MEMCACHE_USER = config.getString(MEMCACHE, "memcache_user");
	public static final String MEMCACHE_PWD = config.getString(MEMCACHE, "memcache_pwd");
	public static final Boolean MEMCACHE_USE_CONSISTENT_HASH = config.getBoolean(MEMCACHE,
			"memcache_use_consistent_hash", false);
	public static final List<String> MEMCACHE_HOST_LIST = new ArrayList<String>();

	public static final String INDEX_URL = config.getString(APP, "index_url");

	public static final int DEFAULT_RANK = 10000;
	public static final String SOHU_HOT_VIDEO_COLLECT_PAGE_URL = "http://tv.sohu.com/hotcomic/";
	public static final String TENCENT_HOT_COMIC_COLLECT_PAGE_URL = "http://ac.qq.com/Rank/comicRank/type/pgv";
	//	public static final String TENCENT_HOT_COMIC_ROOT_URL = "http://ac.qq.com/Rank/";

	public static final boolean RUN_HOT_JOBS_SINCE_START = config.getBoolean(APP, "run_hot_jobs_since_start");

	// init
	static {
		// init alias map
		for (String alias : ALIAS_ARR) {
			for (String name : alias.split(ComConstant.LEFT_SLASH)) {
				ALIAS_MAP.put(name, alias);
			}
		}

		// init memcache map
		for (int i = 1; i <= 100; i++) {
			String key = "memcache_" + i + "_host";
			if (config.containsKey(MEMCACHE, key)) {
				String MEMCACHE_I_HOST = config.getString(MEMCACHE, key);
				if (StringUtils.isNotBlank(MEMCACHE_I_HOST)) {
					MEMCACHE_HOST_LIST.add(MEMCACHE_I_HOST);
				}
			}
		}
	}
}
