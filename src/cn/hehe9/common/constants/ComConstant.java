package cn.hehe9.common.constants;

public class ComConstant {
	
	/** 字母列表 */
	public static final String[] LETTERS = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	/** 下划线 */
	public static final String UNDERSCORE = "_";
	
	public static final String LEFT_SLASH = "/";

	/** 中文名: 其他 */
	public static final String OTHER_CNS = "其他";
	
//	static{
//		// 初始化字母列表
//		for(int i = 0; i < 26; i++){
//			LETTERS[i] = String.valueOf((char)((char)'A' + i));
//		}
//	}
	
	/**
	 * 日志前缀
	 */
	public static class LogPrefix {
		public static final String COLLECT_VIDEOS = "[ collect videos ] ";
		public static final String SOHU_VIDEO = "[ sohu video ] ";
		public static final String SOHU_EPISODE = "[ sohu episode ] ";
	}
}
