<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
<title>episode list</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!-- <link rel="stylesheet"
href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"> -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->

<!-- <link rel="stylesheet" href="./css/nar/bdsstyle.css"
	type="text/css">
<link rel="stylesheet" href="./css/nar/list/index_layout2.css" media="all"
	type="text/css">
<link rel="stylesheet" href="./css/nar/i.css"
	type="text/css">
<link rel="stylesheet" href="./css/nar/search.css" type="text/css"> -->

<link rel="stylesheet" href="./css/pick.css" type="text/css">
<link rel="stylesheet" href="./css/pick_global.css" type="text/css">

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
		<div align="center" style="width: 100%; margin: auto auto 5px auto;">
			<img alt="${video.name }" src="${video.posterBigUrl }">
		</div>
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
					<ul id="narutoList">
						<s:iterator value="episodeList" var="episode">
							<li><a href="./video/playAction!play?videoId=${video.id }&episodeId=${episode.id}&episodeNo=${episode.episodeNo}"
								title="${video.name}&nbsp;第${episode.episodeNo}集<s:if
											test="title != null">「${episode.title}」</s:if>"
								target="_blank"><img
									alt="${video.name}&nbsp;第${episode.episodeNo}集<s:if
											test="title != null">「${episode.title}」</s:if>"
									src="${episode.snapshotUrl}" /></a><a href=""
								title="${video.name}&nbsp;第${episode.episodeNo}集<s:if
											test="title != null">「${episode.title}」</s:if>"
								target="_blank">第${episode.episodeNo}集<s:if
										test="title != null">「${episode.title}」</s:if></a></li>
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

	<div class="wrap2 vtg3">
		<script type="text/javascript">
			hym.show(2);
		</script>
	</div>
	<script type='text/javascript' src='http://cbjs.baidu.com/js/m.js'></script>
	<script type="text/javascript">
		BAIDU_CLB_fillSlot("600153");
	</script>
	<!-- Baidu Button BEGIN -->
	<script type="text/javascript" id="bdshare_js"
		data="type=slide&amp;img=5&amp;uid=616638"></script>
	<script type="text/javascript" id="bdshell_js"></script>
	<script type="text/javascript">
		document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion="
				+ new Date().getHours();
	</script>
	<!-- Baidu Button END -->
	<script type="text/javascript" src="/js/foot.js"></script>
	<script type="text/javascript">
		BAIDU_CLB_fillSlot("600142");
	</script>
	<script type="text/javascript" src="/tj.js"></script>
</body>
</html>