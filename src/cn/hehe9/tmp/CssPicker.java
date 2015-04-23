package cn.hehe9.tmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class CssPicker {

	/**
	 * 抽取除了id和class的css代码
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		toOneLine();
		pickCssByIdAndClass();
		pickCssExceptIdAndClass();
	}

	
	private static void pickCssExceptIdAndClass() throws IOException, FileNotFoundException {
		String cssDir = "E:/Mine/My_Projects/Git/hehe9_server/WebContent/css/nar_upt/";
		String destCss = "E:/Mine/My_Projects/Git/hehe9_server/WebContent/css/nar_pick/nar_pick_except_id_class.css";
		PrintWriter writer = new PrintWriter(new FileWriter(destCss));
		File cssFileDir = new File(cssDir);
		Set<String> cssSet = new TreeSet<String>();
		for(File cssFile : cssFileDir.listFiles()){
			BufferedReader reader = new BufferedReader(new FileReader(cssDir + cssFile.getName()));
			String line = null;
			while((line = reader.readLine()) != null){
				line = line.trim();
				if(!line.startsWith(".") && !line.startsWith("#")){
					cssSet.add(line);
				}
			}
			reader.close();
		}
		
		for(String css : cssSet){
			writer.println(css);
		}
		writer.flush();
		writer.close();
		System.err.println("done");
	}

	/**
	 * 第二,三步
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void pickCssByIdAndClass() throws FileNotFoundException, IOException {
		String pageDir = "E:/Mine/My_Projects/Git/hehe9_server/WebContent_upt/";
		
		String cssDir = "E:/Mine/My_Projects/Git/hehe9_server/WebContent/css/nar_upt/";
		String destCss = "E:/Mine/My_Projects/Git/hehe9_server/WebContent/css/nar_pick/nar_pick.css";

		// 第二步: 获取所有页面的所有id和class
		Set<String> idList = new TreeSet<String>();
		Set<String> classList = new TreeSet<String>();
		File dir = new File(pageDir);
		for (File page : dir.listFiles()) {
			String pageName = page.getName();
			if (!pageName.endsWith(".html") && !pageName.endsWith(".jsp")) {
				continue;
			}

			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(pageDir + page.getName()));
			try {
				while ((line = reader.readLine()) != null) {
					if (line.contains(" id=")) {
						int idIndex = line.indexOf(" id=");
						String subLine = line.substring(idIndex + 4, line.length());
						boolean doubleSign = subLine.startsWith("\"");
						boolean singleSign = subLine.startsWith("'");
						
						if (doubleSign || singleSign) {
							String sign = doubleSign ? "\"" : "'";
							subLine = subLine.replaceFirst(sign, "");
							int firstSignIndex = subLine.indexOf(sign);
							String id = subLine.substring(0, firstSignIndex);
							idList.add(id);
						} else {
							System.err.println("GET ID ERROR -->" + line);
							System.exit(-1);
						}
					}

					if (line.contains(" class=")) {
						int idIndex = line.indexOf(" class=");
						String subLine = line.substring(idIndex + 7, line.length());
						boolean doubleSign = subLine.startsWith("\"");
						boolean singleSign = subLine.startsWith("'");
						
						if (doubleSign || singleSign) {
							String sign = doubleSign ? "\"" : "'";
							subLine = subLine.replaceFirst(sign, "");
							int firstSignIndex = subLine.indexOf(sign);
							String id = subLine.substring(0, firstSignIndex);
							classList.add(id);
						} else {
							System.err.println("GET ID ERROR -->" + line);
							System.exit(-1);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(line);
				e.printStackTrace();
				System.exit(-1);
			} finally {
				reader.close();
			}

			// display
			System.out.println(idList);
			System.out.println(classList);
		}
		
		// 第三步: 分别查询各CSS文件中的id和class, 并抽取css代码到指定文件;
		Set<String> cssSet = new TreeSet<String>();
		PrintWriter writer = new PrintWriter(new FileOutputStream(destCss));
		File cssFileDir = new File(cssDir);
		for (File css : cssFileDir.listFiles()) {
			BufferedReader reader = new BufferedReader(new FileReader(css));
			String cssLine = null;
			while ((cssLine = reader.readLine()) != null) {
				cssLine = cssLine.trim();

				for(String id : idList){
					boolean containsId = cssLine.contains("#" + id);
					if(containsId){
						cssSet.add(cssLine);
					}
				}
				
				for(String className : classList){
					boolean containsClass = cssLine.contains("." + className);
					if(containsClass){
						cssSet.add(cssLine);
					}
				}
			}
			reader.close();
		}
		System.out.println("cssSet.size()-->" + cssSet.size());
		for(String css : cssSet){
			writer.println(css);
		}
		writer.flush();
		writer.close();
		System.err.println("done");
	}

	/**
	 * 第一步: 格式化css代码, 使完整的块变为一行;
	 */
	private static void toOneLine() throws FileNotFoundException, IOException {
		String cssDir = "E:/Mine/My_Projects/Git/hehe9_server/WebContent/css/nar_orig/";
		String cssDestDir = "E:/Mine/My_Projects/Git/hehe9_server/WebContent/css/nar_upt/";

		File dir = new File(cssDir);
		for (File css : dir.listFiles()) {
			BufferedReader reader = new BufferedReader(new FileReader(css));
			PrintWriter writer = new PrintWriter(new FileOutputStream(cssDestDir + css.getName()));
			String line = null;
			StringBuffer buf = new StringBuffer(500);
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				buf.append(line);
				if (line.endsWith("}")) {
					writer.println(buf.toString());
					buf.delete(0, buf.length());
				}
			}
			writer.flush();
			reader.close();
			writer.close();
		}
		System.err.println("done");
	}
}
