<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div id="friendlink">
	<ul>
		<li><a href="http://www.qq.com/" target="_blank" title="http://www.qq.com/"><img
			src="./img/friend/qq_logo.png" alt="腾讯网"
			height="30"></a>
		</li>
		<li>	
		<a href="http://www.baidu.com/" target="_blank" title="http://www.baidu.com/"><img
			src="./img/friend/baidu_logo.gif" alt="百度搜索"
			height="30"></a>
		</li>
	</ul>
	<ul>
	</ul>
</div>
