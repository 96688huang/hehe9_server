<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>${comic.name }第${episode.episodeNo }讲「${episode.title}」高清在线阅读
	- 动漫VCD网</title>
<meta name="keywords"
	content="${comic.name }第${episode.episodeNo }讲「${episode.title}」动画片,动漫${comic.name }第${episode.episodeNo }讲「${episode.title}」高清在线阅读 - 动漫VCD网">
<meta name="description"
	content="${comic.name }第${episode.episodeNo }讲「${episode.title}」在线阅读,${comic.name }第${episode.episodeNo }讲「${episode.title}」 - 动漫VCD网">
<jsp:include page="common_head.jsp" />
</head>
<body>
	<jsp:include page="head.jsp" />
	<div
		style="width: 980px; margin: 0 auto; height: 30px; overflow: hidden;">
		<!-- <div class="v-left l15"> -->
<%-- 		<div
			style="display: inline; float: left; text-align: left; margin: auto auto auto 8px;">
			您的位置： <a href="<%=basePath%>"> 首页 </a> &nbsp;&gt;&nbsp; <a href="">
				${comic.name } </a> &nbsp;&gt;&nbsp; <strong style="">第${episode.episodeNo }讲&nbsp;
				<s:if test="episode.title != null">「${episode.title}」</s:if>
			</strong>
		</div> --%>
		<div id="nav">
			<p>
				<label>您的位置： <a href="<%=basePath%>"> 首页 </a> &nbsp;&gt;&nbsp; <a href="">
				${comic.name }&nbsp;&nbsp;<font color="#FF0000">[ ${comic.serializeStatus } ]</font></a> &nbsp;&gt;&nbsp; <strong style="">第${episode.episodeNo }讲&nbsp;
				<s:if test="episode.title != null">「${episode.title}」</s:if>
				</strong>
				</label>
			</p>
		</div>
	</div>
	<div style="width: 980px; margin: 0 auto; height: 3000px;">
		<iframe src="${episode.readPageUrl }" width="100%" height="100%"
		frameborder="0" scrolling="yes"></iframe>
	</div>
	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>