package cn.hehe9.common.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.hehe9.common.constants.ComConstant;

import com.twogotrade.common.utils.IniReader;
import com.twogotrade.common.utils.PathUtils;

public class AppConfig {
	private AppConfig() {

	}

	private static IniReader config = new IniReader(PathUtils.find("./app.ini"));
	public static final String[] aliasArr = config.getStringArray("app", "video_alias");
	/** 剧情最大长度 */
	public static final int CONTENT_MAX_LENGTH = config.getInt("app", "content_max_length");
	public static final Map<String, String> aliasMap = new HashMap<String, String>();

	// init
	static {
		// init alias map
		for (String alias : aliasArr) {
			for (String name : alias.split(ComConstant.LEFT_SLASH)) {
				aliasMap.put(name, alias);
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
		String alias = aliasMap.get(origName);
		return StringUtils.isBlank(alias) ? origName : alias;
	}
}
