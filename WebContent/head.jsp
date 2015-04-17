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
<div id="Head" class="Ncontainer">
	<div style="display: inline-block;">
		<%-- <span class="red BOLD"><a href="http://www.narutom.com/">火影忍者中文网</a></span> --%>
		<img alt="" src="./img/gif/as_1.gif" />
		<img style="margin-bottom: 20px;" alt="" src="./img/logo_brief.png" />
	</div>
	<!-- 	<div id="topbanner">
		<div class="container"> -->
	<div id="search" style="padding: 25px;">
		<form id="searchForm" action="./video/videoListAction!list"
			method="post">
			<input style="height: 30px; width: 170px; color:#000;" id="searchName" type="text" name="searchName">&nbsp;&nbsp;
			<input style="height: 30px; width: 70px; text-align: center; background-color: #0000FF; color: #FFF;" type="submit" value="搜&nbsp;索">
		</form>
	</div>
	<!-- 		</div>
	</div> -->
</div>
<div id="Narutonav" class="Ncontainer">
	<ul>
		<li><a href="./"><span>首 页</span></a></li>
	</ul>
</div>
<div id="Ntop" class="Ncontainer">
	<div class="Nleft"></div>
	<div id="narutolist1" class="listMenu">
		<span style="color: red; margin-left: 25px;">热门搜索：</span>&nbsp;&nbsp;
		<a href="javascript:onSearch('航海王')" title="海贼王">海贼王</a>&nbsp; <a
			href="javascript:onSearch('猪猪侠')" title="猪猪侠">猪猪侠</a>&nbsp;<a
			href="javascript:onSearch('尸兄')" title="尸兄">尸兄</a>&nbsp; <a
			href="javascript:onSearch('七龙珠')" title="七龙珠">七龙珠</a>&nbsp; <a
			href="javascript:onSearch('熊出没')" title="熊出末">熊出没</a>&nbsp; <a
			href="javascript:onSearch('灌篮高手')" title="灌篮高手">灌篮高手</a>&nbsp; <a
			href="javascript:onSearch('妖精的尾巴')" title="妖精的尾巴">妖精的尾巴</a>
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