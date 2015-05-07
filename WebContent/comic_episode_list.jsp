<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>${comic.name },${comic.name }漫画,${comic.name }剧情分析 - 动漫VCD网</title>
<meta name="keywords" content="${comic.name }王,${comic.name }漫画,${comic.name }-${comic.name }专题 - 动漫VCD网">
<meta name="description" content="${comic.name },${comic.name }漫画全集连载,${comic.name }专题 - 动漫VCD网">
<jsp:include page="common_head.jsp" />
</head>
<body>
	<div class="nmain">
	<jsp:include page="head.jsp" />
	<div class="wrap2 clearfix">
		<div id="nav">
			<p>
				<label>您的位置: <a
					href="<%=basePath %>" class="classlinkclass">首页</a>&nbsp;>&nbsp;<a
					href="<%=basePath %>list_comic_episodes/cid/${comic.id }.html" class="classlinkclass">${comic.name }</a>&nbsp;>&nbsp;
					<a class="classlinkclass">${comic.name }&nbsp;在线阅读</a>&nbsp;><!--empire.url--></label><span><script
						src="/style/nav.js"></script></span>
			</p>
		</div>
		<c:if test="${comic.posterBigUrl != null && comic.posterBigUrl != '' }">
			<div align="center" style="width: 100%; margin: auto auto 5px auto;">
				<img alt="${comic.name }" src="${comic.posterBigUrl }">
			</div>
		</c:if>
		<%-- <div class="wrap2 vtg3">
			<script type="text/javascript">
				hym.show(1);
			</script>
		</div> --%>
		<div id="container2" class="clearfix">
		<div id="sidebar" style="float: right;">
				<div class="sidebarad">
					<div class="ltitle">${comic.name }</div>
						<div style="margin: 10px auto 10px auto; width: 100%; text-align: center;"><img alt="${comic.name }" src="${comic.iconUrl }"></div>
						<div style="width: 100%;" align="center">
						<div style="width: 80%; text-align: left;">
						${comic.author }<p/>
						<div style="margin:10px auto 10px auto ;height: 1px; width:100%; overflow: hidden; background: url(./img/line1.png) repeat-x;"></div>
						<h1>剧情：</h1><p/>
						<div style="max-height: 200px; overflow: hidden;">${comic.storyLine }</div><p/>
						<div style="margin:10px auto 10px auto ;height: 1px; width:100%; overflow: hidden; background: url(./img/line1.png) repeat-x;"></div>
						<c:if test="${comic.readCountWeekly  != null}">
							${comic.readCountWeekly }
							<p/>
						</c:if>
						<c:if test="${comic.readCountTotal != null}">
							总阅读量：${comic.readCountTotal }
						</c:if>
						</div>
						</div>
				</div>
				<!-- <div class="blank"></div> -->
			</div>
			<div id="content2" style="position:relative; height: 800px;">
				<div class="listitem">
					<h1>${comic.name}&nbsp;&nbsp;<font color="#FF0000">[ ${comic.serializeStatus } ]</font></h1>
					<form id="queryForm" action="<%=basePath %>list_comic_episodes.html" method = "post">
					<input name="comicId" value="${comic.id }" type="hidden"/>
					<ul id="comicEpisodeList">
						<s:iterator value="episodeList" var="episode">
							<li>
								<a href="<%=basePath %>read_comic/cid/${comic.id }/eid/${episode.id}/eno/${episode.episodeNo}.html" 
								target="_blank"
								title="${comic.name}&nbsp;第${episode.episodeNo}集<s:if test="title != null">「${episode.title}」</s:if>" 
								>
									<s:if test="title != null">「${episode.title}」</s:if>
								</a>
							</li>
						</s:iterator>
					</ul>
					</form>
				</div>
				<div class="rblank"></div>
				<jsp:include page="pagination.jsp"	/>
			</div>
		</div>
		</div>
		<div class="line"></div>
		<!-- 友情链接 -->
		<jsp:include page="friend_link.jsp" />
	</div>
	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>