<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>动漫VCD网-动漫大全|海贼王动漫|海贼王漫画|海贼王在线观看</title>
<meta name="keywords" content="动漫VCD网-海贼王动漫|海贼王漫画|海贼王在线观看|动漫游戏下载">
<meta name="description" content="动漫VCD网是国内更新海贼王漫画、海贼王在线观看最快的、收录海贼王资源最全的网站。我们还提供了死神、火影的动漫连载以及动漫资讯和动漫游戏下载等.">
<jsp:include page="common_head.jsp" />
</head>
<body>
	<div class="nmain">
		<jsp:include page="head.jsp" />
		<div class="wrap clearfix">
			<div id="dmvcd_desk">
				<div class="ltitle">${displayTitle}</div>
				<form id="queryForm" action="./comic/comicListAction!list"
					method="post">
					<input name="searchName" type="hidden" value="${searchName }" />
					<s:iterator value="comicListHolder" var="comicList">
						<div style="display: inline-block; float: left;">
							<!-- 强制在一行, 靠左展示 -->
							<ul id="indexcartoonList">
								<s:iterator value="comicList" status="comicListStatus">
									<li><a href="javascript:jumpTo('./comic/comicEpisodeAction!list?comicId=${id }');"
										title="${name} ${updateRemark}">
										<img
											alt="${name} ${updateRemark}" src="${iconUrl}"></a> 
										<span
										class="icon_bottom_tips">&nbsp;<span
											style="float: left;">&nbsp;${sourceName }</span>&nbsp;
										<span>${updateRemark}&nbsp;</span></span>
										<a
										href="javascript:jumpTo('./comic/comicEpisodeAction!list?comicId=${id }');"
										title="${name} ${updateRemark}"><font
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