<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./css/search.css" type="text/css">

<div id="Head" class="Ncontainer">
	<div
		style="display: inline-block; background: url('./img/gif/as_1.gif') no-repeat;">
		<img style="float: right; margin: 15px auto 10px 200px;" alt=""
			src="./img/logo_brief.png" />
	</div>
	<div id="searchBar">
		<form id="searchForm" action="./video/videoListAction!list"
			method="post">
			<input id="searchName" type="text" name="searchName">&nbsp;&nbsp;
			<input id="searchBtn" type="submit" value="搜&nbsp;索">
		</form>
	</div>
</div>

<div id="Narutonav" class="Ncontainer">
	<ul>
		<li><a href="./">首 页</a></li>
	</ul>
</div>
<div id="Ntop" class="Ncontainer">
	<div class="Nleft"></div>
	<div class="listMenu">
		<span style="color: red; margin-left: 25px; font-weight: bold;">热门搜索：</span>&nbsp;&nbsp;
		<a href="javascript:onSearch('航海王')" title="海贼王">&nbsp;海贼王&nbsp;</a>&nbsp;
		<a href="javascript:onSearch('猪猪侠')" title="猪猪侠">&nbsp;猪猪侠&nbsp;</a>&nbsp;<a
			href="javascript:onSearch('尸兄')" title="尸兄">&nbsp;尸兄&nbsp;</a>&nbsp;
		<a href="javascript:onSearch('七龙珠')" title="七龙珠">&nbsp;七龙珠&nbsp;</a>&nbsp;
		<a href="javascript:onSearch('熊出没')" title="熊出末">&nbsp;熊出没&nbsp;</a>&nbsp;
		<a href="javascript:onSearch('灌篮高手')" title="灌篮高手">&nbsp;灌篮高手&nbsp;</a>&nbsp;
		<a href="javascript:onSearch('妖精的尾巴')" title="妖精的尾巴">&nbsp;妖精的尾巴&nbsp;</a>
	</div>
</div>

<script type="text/javascript">
	function onSearch(keyword) {
		if (keyword == null || keyword == '' || keyword == undefined) {
			return;
		}

		$("#searchName").attr("value", keyword);
		$("#searchForm").submit();
	}

	
</script>