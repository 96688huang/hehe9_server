<%@page import="cn.hehe9.common.utils.UrlEncodeUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div id="Head" class="Ncontainer">
	<div style="display: inline-block;">
		<a href="./" title="www.dmVCD.com" style="min-height: 60px; float: left; margin-top: 20px;"> 
		<img alt="www.dmvcd.com"
			src="./img/logo_brief.png">
		</a> 
		<a href="./" title="蒙其·d·路飞" style="float: right;"> 
		<img id="onepieceGif" alt=""
			src="./img/gif/as_1.gif" />
		</a>
	</div>
	<div id="searchBar">
		<form id="searchForm" action="" method="post" target="_blank">
			<input name="isSearchNameEncode" value="false" type="hidden">
			<input id="searchName" type="text" name="searchName">&nbsp;&nbsp;
			<input class="searchBtn" type="button" onclick="javascript:searchVideo();" value="找动漫">
			<input class="searchBtn" type="button" onclick="javascript:searchComic();" value="找漫画" style="background-color: #F00;">
		</form>
	</div>
	<div id="Dmvcdnav" class="Ncontainer">
		<ul>
			<li><a href="<%=basePath%>" title="<%=basePath%>" target="_blank">首 页</a></li>
		</ul>
	</div>
	<div id="Ntop" class="Ncontainer">
		<div class="Nleft"></div>
		<div class="listMenu">
			<span style="color: red; margin-left: 25px; font-weight: bold;">热门搜索：</span>&nbsp;&nbsp;
			<a href="<%=basePath%>search_videos/name/<%=UrlEncodeUtil.base64Encode("航海王")%>.html" target="_blank" title="海贼王">&nbsp;海贼王&nbsp;</a>&nbsp;
			<a href="<%=basePath%>search_videos/name/<%=UrlEncodeUtil.base64Encode("猪猪侠")%>.html" target="_blank" title="猪猪侠">&nbsp;猪猪侠&nbsp;</a>&nbsp;
			<a href="<%=basePath%>search_videos/name/<%=UrlEncodeUtil.base64Encode("尸兄")%>.html" target="_blank" title="尸兄">&nbsp;尸兄&nbsp;</a>&nbsp;
			<a href="<%=basePath%>search_videos/name/<%=UrlEncodeUtil.base64Encode("七龙珠")%>.html" target="_blank" title="七龙珠">&nbsp;七龙珠&nbsp;</a>&nbsp;
			<a href="<%=basePath%>search_videos/name/<%=UrlEncodeUtil.base64Encode("熊出没")%>.html" target="_blank" title="熊出末">&nbsp;熊出没&nbsp;</a>&nbsp;
			<a href="<%=basePath%>search_videos/name/<%=UrlEncodeUtil.base64Encode("灌篮高手")%>.html" target="_blank" title="灌篮高手">&nbsp;灌篮高手&nbsp;</a>&nbsp;
			<a href="<%=basePath%>search_videos/name/<%=UrlEncodeUtil.base64Encode("妖精的尾巴")%>.html" target="_blank" title="妖精的尾巴">&nbsp;妖精的尾巴&nbsp;</a>
		</div>
	</div>％
</div>
<!-- jumpForm -->
<form id="jumpForm" name="jumpForm" method="post" target="_blank">
</form>
