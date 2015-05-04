<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>${video.name },${video.name }漫画,${video.name }剧情分析 - 动漫VCD网</title>
<meta name="keywords" content="${video.name }王,${video.name }漫画,${video.name }-${video.name }专题 - 动漫VCD网">
<meta name="description" content="${video.name },${video.name }漫画全集连载,${video.name }专题 - 动漫VCD网">
<jsp:include page="common_head.jsp" />
</head>
<body>
	<div class="nmain">
	<jsp:include page="head.jsp" />
	<div class="wrap2 clearfix">
		<div id="nav">
			<p>
				<label>您的位置: <a
					href="./" class="classlinkclass">首页</a>&nbsp;>&nbsp;<a
					href="./video/episodeAction!list?videoId=${video.id }" class="classlinkclass">${video.name }</a>&nbsp;>&nbsp;
					<a class="classlinkclass">${video.name }&nbsp;在线观看</a>&nbsp;><!--empire.url--></label><span><script
						src="/style/nav.js"></script></span>
			</p>
		</div>
		<c:if test="${video.posterBigUrl != null && video.posterBigUrl != '' }">
			<div align="center" style="width: 100%; margin: auto auto 5px auto;">
				<img alt="${video.name }" src="${video.posterBigUrl }">
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
					<div class="ltitle">${video.name }</div>
						<div style="margin: 10px auto 10px auto; width: 100%; text-align: center;"><img alt="${video.name }" src="${video.iconUrl }"></div>
						<div style="width: 100%;" align="center">
						<div style="width: 80%; text-align: left;">
						${video.author }<p/>
						<div style="margin:10px auto 10px auto ;height: 1px; width:100%; overflow: hidden; background: url(./img/line1.png) repeat-x;"></div>
						<h1>剧情：</h1><p/>
						<div style="max-height: 200px; overflow: hidden;">${video.storyLine }</div><p/>
						<div style="margin:10px auto 10px auto ;height: 1px; width:100%; overflow: hidden; background: url(./img/line1.png) repeat-x;"></div>
						<c:if test="${video.playCountWeekly  != null}">
							${video.playCountWeekly }
							<p/>
						</c:if>
						<c:if test="${video.playCountTotal != null}">
							总播放量：${video.playCountTotal }
						</c:if>
						</div>
						</div>
				</div>
				<!-- <div class="blank"></div> -->
			</div>
			<div id="content2" style="position:relative; height: 800px;">
				<div class="listitem">
					<h1>${video.name}&nbsp;在线观看</h1>
					<form id="queryForm" action="./video/episodeAction!list?" method = "post">
					<input name="videoId" value="${video.id }" type="hidden"/>
					<ul id="dmvcdList">
						<s:iterator value="episodeList" var="episode">
							<li><a href="javascript:jumpTo('./video/playAction!play?videoId=${video.id }&episodeId=${episode.id}&episodeNo=${episode.episodeNo}');"
								title="${video.name}&nbsp;第${episode.episodeNo}集 <s:if test="title != null">「${episode.title}」</s:if>"
								target="_blank">
									<img alt="${video.name}&nbsp;第${episode.episodeNo}集<s:if test="title != null">「${episode.title}」</s:if>" src="${episode.snapshotUrl}" />
								</a>
								<span class="icon_bottom_tips" style="top: 84px;">
									第${episode.episodeNo}集&nbsp;
								</span>
											
								<a href="javascript:jumpTo('./video/playAction!play?videoId=${video.id }&episodeId=${episode.id}&episodeNo=${episode.episodeNo}');" 
								title="${video.name}&nbsp;第${episode.episodeNo}集<s:if test="title != null">「${episode.title}」</s:if>" 
								target="_blank">
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