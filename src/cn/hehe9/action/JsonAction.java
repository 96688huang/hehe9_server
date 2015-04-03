package cn.hehe9.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class JsonAction extends ActionSupport implements ServletRequestAware{
    private static final long serialVersionUID = 1L;
    
    private HttpServletRequest request;
    private String result; //用于返回json 的数据结果，必须有getter and setter方法

    public void setServletRequest(HttpServletRequest arg0) {
        this.request = arg0;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    
    /**
     * 处理ajax请求
     * @return success
     */
    public String excuteAjax(){
//        try {
//            //获取数据,单个获取数据
//            String word= request.getParameter("word");
//            int wordcount= Integer.parseInt(request.getParameter("wordcount")); 
//            //将数据存储在map里，再转换成json类型数据，也可以自己手动构造json类型数据
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("word", word);
//            map.put("wordcount",wordcount);     
//            JSONObject json = JSONObject.fromObject(map);//将map对象转换成json类型数据
//            result = json.toString();//给result赋值，传递给页面
//　　　　　　
//　
//　　　　　　　//手动构造json数据类型
//　　　　　　　result = {\'word\':\'"+word+"\'};{\'wordcount\':\'"+wordcount+"\'}" 
//
//
//　　　 　    //查询结果集list数据
//            RowSet rs = twImpl.queryAllBook(dbId);
//            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//            while(rs != null && rs.next())
//            {
//               Map<String,Object> map=new HashMap<String, Object>();
//               map.put("word", rs.getString("word"));
//               map.put("wordcount", rs.getInt("wordcount"));
//               list.add(map);
//           }
//            JSONArray jsarry = new JSONArray();
//            jsarry=JSONArray.fromObject(list);  
//            result=jsarry.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "success";
    }   
}