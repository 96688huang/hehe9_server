<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<link rel="stylesheet" href="./css/dmvcd.css" type="text/css">
<link rel="stylesheet" href="./css/dmvcd_global.css" type="text/css">
</head>
<body>
	<div class="nmain">
		<jsp:include page="head.jsp" />
		<div class="wrap clearfix">
			<div id="dmvcd_desk">
				<div class="ltitle">${displayTitle}</div>
				<form id="queryForm" action="./video/videoListAction!list"
					method="post">
					<input name="searchName" type="hidden" value="${searchName }" />
					<s:iterator value="videoListHolder" var="videoList">
						<div style="display: inline-block; float: left;">
							<!-- 强制在一行, 靠左展示 -->
							<ul id="indexcartoonList">
								<s:iterator value="videoList" status="videoListStatus">
									<li><a href="javascript:jumpTo('./video/episodeAction!list?videoId=${id }');"
										title="${name} ${updateRemark}" target="_blank">
										<img
											alt="${name} ${updateRemark}" src="${iconUrl}"></a> 
										<span
										class="icon_bottom_tips">&nbsp;<span
											style="float: left;">&nbsp;${sourceName }</span>&nbsp;
										<span>${updateRemark}&nbsp;</span></span>
										<a
										href="javascript:jumpTo('./video/episodeAction!list?videoId=${id }');"
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

			<%-- <div class="itabg">
			</div> --%>
			<!-- 友情链接 -->
			<jsp:include page="friend_link.jsp" />
		</div>
	</div>
	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>