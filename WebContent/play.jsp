<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<title>${video.name }第${episode.episodeNo }集「${episode.title}」高清在线观看 - 动漫VCD网</title>
<meta name="keywords" content="${video.name }第${episode.episodeNo }集「${episode.title}」动画片,动漫${video.name }第${episode.episodeNo }集「${episode.title}」高清在线观看 - 动漫VCD网">
<meta name="description" content="${video.name }第${episode.episodeNo }集「${episode.title}」在线观看,${video.name }第${episode.episodeNo }集「${episode.title}」 - 动漫VCD网">

<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/menu2.js"></script>

<link rel="stylesheet" href="./css/dmvcd.css" type="text/css">
<link rel="stylesheet" href="./css/dmvcd_global.css" type="text/css">

<link rel="stylesheet" href="./css/common.css" type="text/css">

<script src="./js/play.js"></script>

</head>
<body>
	<jsp:include page="head.jsp" />
	<div style="width: 980px; margin: 0 auto; height: 30px; overflow: hidden;">
		<!-- <div class="v-left l15"> -->
		<div
			style="display: inline; float: left; text-align: left; margin: auto auto auto 8px;">
			您的位置： <a href="./"> 首页 </a> &nbsp;&gt;&nbsp; <a href="">
				${video.name } </a> &nbsp;&gt;&nbsp; 
				<strong style="">第${episode.episodeNo }集&nbsp;
				<s:if test="episode.title != null">「${episode.title}」</s:if>
			</strong>
		</div>
		<!-- <div class="v-right r15"> -->
		<div style="display: inline;">
			<span style="color:#FF0000; font-weight:blod; font-size:14px; margin: auto 20px auto auto;">观看不了请
				<a href="javascript:location.reload();">刷新一下</a>
			</span>
			<span id="playPanel"> <a href="javascript:void(0);"
				onclick="javascript:changePlayPanel(1);" id="witer"> 宽屏 </a>
			</span>
		</div>
	</div>
	<div class="PlayBody">
		<div class="play">
			<table>
				<tr>
					<td style="width: 720px; height: 513px; vertical-align: top;">
						<div class="pl" id="vleft">
							<br />
							<div class="top">
								<h1>
									${video.name }第${episode.episodeNo }集
									<s:if test="episode.title != null">「${episode.title}」</s:if>
								</h1>
							</div>
							<div class="player" id="player">
								<embed src="${episode.fileUrl }" id="dmvcd_v" class="vborder"
									name="dmvcd_v" type="application/x-shockwave-flash"
									allowscriptaccess="always"
									flashvars="playMovie=true&amp;isAutoPlay=true&amp;auto=1&amp;autoPlay=true&amp;"
									allowfullscreen="true" wmode="opaque" width="100%" height="480"></embed>
							</div>
						</div>
					</td>
					<td>&nbsp;</td>
					<td style="width: 250px; height: 580px; vertical-align: top;">
						<div class="pr" id="vright">
							<ul id="dmvcdList">
								<s:iterator value="episodeList" var="episodeItem">
									<li class="font_12" style="width: 250px; text-align: left;">
									<a href="javascript:jumpTo('./video/playAction!play?videoId=${video.id }&episodeId=${episodeItem.id}&episodeNo=${episodeItem.episodeNo}');"
										title="${video.name}&nbsp;第${episodeItem.episodeNo}集<s:if test="title != null">「${episodeItem.title}」</s:if>"
										target="_blank">
										<img alt="${video.name}&nbsp;第${episodeItem.episodeNo}集<s:if test="title != null">「${episodeItem.title}」</s:if>"
											src="${episodeItem.snapshotUrl}" />
									</a> 
									<!--  -->
									<span class="icon_bottom_tips" style="width:132px; top:84px; <s:if test="episodeNo == episode.episodeNo">color:#FFD306;</s:if><s:else>color:#FFF;</s:else>">
										第${episodeItem.episodeNo}集&nbsp;
									</span>
									<a href="javascript:jumpTo('./video/playAction!play?videoId=${video.id }&episodeId=${episodeItem.id}&episodeNo=${episodeItem.episodeNo}');"
										style="<s:if test="episodeNo == episode.episodeNo">color:#FFD306;</s:if>" 
										class="dmvcdTitle"
										title="${video.name}&nbsp;第${episodeItem.episodeNo}集<s:if test="title != null">「${episodeItem.title}」</s:if>"
										target="_blank"><s:if test="title != null">「${episodeItem.title}」</s:if>
									</a>
									</li>
								</s:iterator>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<%-- 	<div class="wraps gt1" id="cg3">
	</div> --%>
	<div class="links wraps">
		<div style="margin: 8px; font-size: 14px;">
			<ul>
				<li><b>上一集： </b> <s:if test="preEpisode != null">
						<a href="">${video.name }第${preEpisode.episodeNo }集 <s:if
								test="preEpisode.title != null">「${preEpisode.title}」</s:if></a>
					</s:if> <s:else>无</s:else></li>
				<li><b>下一集：</b> <s:if test="nextEpisode != null">
						<a href="">${video.name }第${nextEpisode.episodeNo }集 <s:if
								test="nextEpisode.title != null">「${nextEpisode.title}」</s:if></a>
					</s:if> <s:else>无</s:else></li>
			</ul>
		</div>
		<div>
			<div class="line"></div>
			<h1 align="left">
				<b>剧情：</b>
			</h1>
			<br />
			<p style="font-size: 14px;">
				<s:if test="episode.title == null">${video.name }第${episode.episodeNo }集</s:if>
				<s:else>
				「${episode.title}」
				</s:else>
			</p>
		</div>
	</div>

	<!-- TODO 多说评论 -->

	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>