package cn.hehe9.common.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.hehe9.common.constants.ComConstant;

import com.twogotrade.common.utils.IniReader;
import com.twogotrade.common.utils.PathUtils;

public class AppConfig {
	private AppConfig() {

	}

	private static IniReader config = new IniReader(PathUtils.find("./app.ini"));

	private static String APP = SectionEnum.APP.val();
	private static String MEMCACHE = SectionEnum.MEMCACHE.val();

	/** 别名数组 */
	public static final String[] ALIAS_ARR = config.getStringArray(APP, "video_alias");
	/** 别名map */
	public static final Map<String, String> ALIAS_MAP = new HashMap<String, String>();
	/** 内容最大长度 */
	public static final int CONTENT_MAX_LENGTH = config.getInt(APP, "content_max_length");

	/** 是否启用 memcache */
	public static final Boolean MEMCACHE_ENABLE = config.getBoolean(MEMCACHE, "memcache_enable", false);
	public static final String MEMCACHE_HOST = config.getString(MEMCACHE, "memcache_host");
	public static final String MEMCACHE_USER = config.getString(MEMCACHE, "memcache_user");
	public static final String MEMCACHE_PWD = config.getString(MEMCACHE, "memcache_pwd");
	public static final Boolean MEMCACHE_USE_CONSISTENT_HASH = config.getBoolean(MEMCACHE,
			"memcache_use_consistent_hash", false);
	public static final List<String> MEMCACHE_HOST_LIST = new ArrayList<String>();
	
	public static final String INDEX_URL = config.getString(APP, "index_url"); 
			
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

	/**
	 * 如果别名存在, 则获取;
	 *
	 * @param origName	原始名称
	 * @return	如果别名存在, 则返回别名; 否则, 返回视频名称;
	 */
	public static String getAliasNameIfExist(String origName) {
		String alias = ALIAS_MAP.get(origName);
		return StringUtils.isBlank(alias) ? origName : alias;
	}
}
