<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>动漫VCD网-海贼王动漫|海贼王漫画|海贼王在线观看</title>
<meta name="keywords" content="动漫VCD网-海贼王动漫|海贼王漫画|海贼王在线观看|动漫在线">
<meta name="description"
	content="动漫VCD网为广大动漫爱好者提供最新最火热的动漫视频和漫画连载, 包括海贼王, 火影忍者, 死神等热门动漫和漫画, 保证观看速度最快, 画面最清晰.">
<jsp:include page="common_head.jsp" />
</head>
<body>
	<div class="nmain">
		<jsp:include page="head.jsp" />
		<div class="wrap clearfix">
			<%-- 
			<div class="ita" id="itaTop">
			</div>
			--%>
			<div id="dmvcd_desk">
				<div class="ltitle">
					热门动漫 <a href="javascript:jumpTo('./video/videoListAction!list');">
						<img class="more" src="./img/jian/more.jpg">
					</a>
				</div>

				<s:iterator value="hotVideoListHolder" var="videoListVar">
					<ul id="indexcartoonList">
						<s:iterator value="videoListVar">
							<li><a
								href="javascript:jumpTo('./video/episodeAction!list?videoId=${id }');"
								title="${name} ${updateRemark}" target="_blank"><img
									alt="${name} ${updateRemark}" src="${iconUrl}"></a><a
								href="javascript:jumpTo('./video/episodeAction!list?videoId=${id }');"
								title="${name} ${updateRemark}" target="_blank"><font
									color="#FF0000">${name}</font></a></li>
						</s:iterator>
					</ul>
				</s:iterator>
			</div>
			
			<s:iterator value="hotEpisodeListHolder" var="episodeMap">
				<s:iterator value="episodeMap" id="map">
					<div class="index_downrank">
						<div class="ltitle">
							${map.key.name} <a
								href="javascript:jumpTo('./video/episodeAction!list?videoId=${map.key.id}');"
								title="${map.key.name}"><img class="more"
								src="./img/jian/more.jpg"></a>
						</div>
						<div id="video_list_holder"></div>
						<ul class="softolist">
							<s:iterator value="#map.value" var="episode">
								<li><a
									href="javascript:jumpTo('./video/episodeAction!list?videoId=${map.key.id }');"
									target="_blank"
									title="${map.key.name}&nbsp;第${episode.episodeNo}集<s:if
											test="title != null">「${episode.title}」</s:if>">${map.key.name}&nbsp;第${episode.episodeNo}集<s:if
											test="title != null">「${episode.title}」</s:if></a></li>
							</s:iterator>
						</ul>
					</div>
				</s:iterator>
			</s:iterator>
			
			<div id="dmvcd_desk">
				<div class="ltitle">
					热门漫画 <a href="javascript:jumpTo('./video/comicListAction!list');">
						<img class="more" src="./img/jian/more.jpg">
					</a>
				</div>

				<s:iterator value="hotComicListHolder" var="comicListVar">
					<ul id="indexcartoonList">
						<s:iterator value="comicListVar">
							<li><a
								href="javascript:jumpTo('./video/episodeAction!list?videoId=${id }');"
								title="${name} ${updateRemark}" target="_blank"><img
									alt="${name} ${updateRemark}" src="${iconUrl}"></a><a
								href="javascript:jumpTo('./video/episodeAction!list?videoId=${id }');"
								title="${name} ${updateRemark}" target="_blank"><font
									color="#FF0000">${name}</font></a></li>
						</s:iterator>
					</ul>
				</s:iterator>
			</div>
			<%-- 
			<div class="itabg">
			</div>
			--%>

			<!-- 字母下拉菜单 -->
			<div id="dmvcd_desk">
				<div class="menu2_menu_container">
					<div class="menu2_nav" id="letterMainNav">
						<ul class="menu2_list">
							<s:iterator value="letterMenuVideoMap" var="map">
								<li><span class="menu2_name">${map.key}</span></li>
							</s:iterator>
						</ul>

						<s:iterator value="letterMenuVideoMap" var="map"
							status="mapStatus">
							<s:if test="#mapStatus.first">
								<div class="menu2_hover_cont wlyx" style="display: block;">
							</s:if>
							<s:else>
								<div class="menu2_hover_cont wlyx">
							</s:else>
							<div class="menu2_nav_cont">
								<div class="menu2_nav_li">
									<table style="height: 100%; width: 100%;">
										<tr>
											<td width="10px">
												<div class="menu2_nav_li_l">视&nbsp;&nbsp;频</div>
											</td>
											<td width="5px">&nbsp;</td>
											<td>
												<div class="menu2_nav_li_r">
													<s:iterator value="#map.value" var="videoNameVar"
														status="videoStatus">
														<div style="display: inline-block;">
															<a
																href="javascript:jumpTo('./video/videoListAction!list?searchName=${videoNameVar }');">${videoNameVar }</a>┊
														</div>
													</s:iterator>
													<div style="display: inline-block;">
														<s:if test="#map.value.size > 0">
															<a
																href="javascript:jumpTo('./video/videoListAction!list?firstChar=${map.key }');"><img
																class="more" src="./img/jian/more.jpg"></a>
														</s:if>
														<s:else>
															<a style="color: gray; font-weight: bold;">无</a>
														</s:else>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td width="10px">
												<div class="menu2_nav_li_l">漫&nbsp;&nbsp;画</div>
											</td>
											<td width="5px">&nbsp;</td>
											<td>
												<div class="menu2_nav_li_r">
													<s:set name="comicList" value="letterMenuComicMap[#map.key ]" />
													<s:iterator value="comicList" var="comicNameVar"
														status="videoStatus">
														<div style="display: inline-block;">
															<a
																href="javascript:jumpTo('./video/comicListAction!list?searchName=${comicNameVar }');">${comicNameVar }</a>┊
														</div>
													</s:iterator>
													<div style="display: inline-block;">
														<s:if test="#comicList.size > 0">
															<a
																href="javascript:jumpTo('./video/comicListAction!list?firstChar=${map.key }');"><img
																class="more" src="./img/jian/more.jpg"></a>
														</s:if>
														<s:else>
															<a style="color: gray; font-weight: bold;">无</a>
														</s:else>
													</div>
												</div>
											</td>
										</tr>
									</table>
								</div>
								<%-- <div class="menu2_nav_li">
									<div class="menu2_nav_li_l">高清漫画</div>
									<div class="menu2_nav_li_r">
										<s:iterator value="#map.value" var="video"
											status="videoStatus">
											<div style="display: inline-block;">
												<a href="#">${video.name }</a>┊
											</div>
										</s:iterator>
										<div style="display: inline-block;">
											<a href="#" style="color: red;">更多...</a>
										</div>
									</div>
								</div> --%>
							</div>
					</div>
					</s:iterator>
				</div>
				<%--
				<div class="ltitle">
					动画片大全<span><a href="./video/videoListAction!list" title="">更多</a></span>
				</div>
				<div class="dmnew">
					<s:iterator value="menuVideoList" var="menuVideo">
						<a href="" title="${menuVideo.name}" target="_blank">${menuVideo.name}&nbsp;&nbsp;&nbsp;&nbsp;|</a>
					</s:iterator>
					<br /> <a href="" title="" target="_blank" style="color: red;">更多=></a>
				</div>
			 --%>
			</div>
		</div>
		<div class="line"></div>
		<!-- 友情链接 -->
		<jsp:include page="friend_link.jsp" />
	</div>
	</div>
	<!-- 注: 因标签判断的缘故, 需要多一个div结束符 -->
	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>