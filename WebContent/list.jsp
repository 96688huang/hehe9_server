<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!-- <link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"> -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<%-- <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script> --%>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->

<script type="text/javascript" src="./js/jquery.min.js"></script>

<!-- <link rel="stylesheet" href="./css/nar/bdsstyle.css" type="text/css">
<link rel="stylesheet" href="./css/nar/search.css" type="text/css">
<link rel="stylesheet" href="./css/nar/list/index_layout2.css"
	media="all" type="text/css">
<link rel="stylesheet" href="./css/nar/i.css" media="all"
	type="text/css"> -->

<link rel="stylesheet" href="./css/pick.css" type="text/css">
<link rel="stylesheet" href="./css/pick_global.css" type="text/css">
</head>
<body>
	<div class="nmain">
		<jsp:include page="head.jsp" />
		<div class="wrap clearfix">
			<%-- <div id="login">
				<p>
					<span><a href="http://www.narutom.com/lianxu/"
						target="_blank">火影连续无插曲版</a> &nbsp; <a
						href="http://www.narutom.com/cartoon/narutocn/" target="_blank"><font
							color="#ff0000">火影忍者国语版</font></a> </span>火影情报：<a href="/news/27198.html"
						target="_blank">火影623集「决不放弃的强大毅力!」</a>(3月12日晚上放送! ) | 火影忍者漫画已完结
				</p>
			</div> --%>
			<div class="ita" id="itaTop">
				<script type="text/javascript">
					hym.show(1);
				</script>
				<iframe scrolling="no" frameborder="0" width="980" height="141"
					src="http://www.narutom.com/v2/v/i/1377.html?20150302"></iframe>
			</div>
			<div id="naruto_desk" style="width:100%; height: 870px; padding:5px; position:relative;">
				<div class="ltitle">${displayTitle}</div>
				<form id="queryForm" action="./video/videoListAction!list"
					method="post">
					<input name="searchName" type="hidden" value="${searchName }" />
					<s:iterator value="videoListHolder" var="videoList">
						<div style="display: inline-block;"> <!-- 强制一行 -->
						<ul id="indexcartoonList">
							<s:iterator value="videoList" status="videoListStatus">
								<li><a href="./video/episodeAction!list?videoId=${id }"
									title="${name} ${updateRemark}" target="_blank"><img
										alt="${name} ${updateRemark}" src="${iconUrl}"></a><a
									href="./video/episodeAction!list?videoId=${id }"
									title="${name} ${updateRemark}" target="_blank"><font
										color="#FF0000">${name}</font></a></li>
							</s:iterator>
						</ul>
						</div>
					</s:iterator>
				</form>
				<div class="rblank"></div>
				<jsp:include page="pagination.jsp" />
			</div>
		
			<%-- 			<div class="itabg">
				<script type="text/javascript">
					hym.show(3);
				</script>
				<iframe scrolling="no" frameborder="0" width="980" height="90"
					src="http://www.narutom.com/v2/v/i/key.html"></iframe>
			</div> --%>
			<!-- 友情链接 -->
			<jsp:include page="friend_link.jsp" />
		</div>
	</div>
	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>