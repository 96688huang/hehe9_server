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
<title>main</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!-- <link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"> -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->

<link rel="stylesheet" href="./css/nar/index/i.css" media="all"
	type="text/css">
<link rel="stylesheet" href="./css/nar/bdsstyle.css" type="text/css">
<link rel="stylesheet" href="./css/nar/search.css" type="text/css">

<script type="text/javascript" src="./js/menu2.js"></script>
<link rel="stylesheet" href="./css/common.css" type="text/css">
<link rel="stylesheet" href="./css/menu2.css" type="text/css">

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
			<div id="naruto_desk" style="height: 630px;">
				<div class="ltitle">
					<a href="./video/videoListAction!listHot" title="热门动画片"
						target="_blank">热门动画片</a>
				</div>

				<s:iterator value="hotVideoListHolder" var="videoList">
					<ul id="indexcartoonList">
						<s:iterator value="videoList">
							<li><a href="./video/episodeAction!list?videoId=${id }"
								title="${name} ${updateRemark}" target="_blank"><img
									alt="${name} ${updateRemark}" src="${iconUrl}"></a><a
								href="./video/episodeAction!list?videoId=${id }"
								title="${name} ${updateRemark}" target="_blank"><font
									color="#FF0000">${name}</font></a></li>
						</s:iterator>
					</ul>
				</s:iterator>
			</div>
			<div class="itabg">
				<script type="text/javascript">
					hym.show(3);
				</script>
				<iframe scrolling="no" frameborder="0" width="980" height="90"
					src="http://www.narutom.com/v2/v/i/key.html"></iframe>
			</div>

			<s:iterator value="hotEpisodeListHolder" var="episodeMap">
				<s:iterator value="episodeMap" id="map">
					<div class="index_downrank">
						<div class="ltitle">
							<span><a
								href="./video/episodeAction!list?videoId=${map.key.id}"
								title="${map.key.name}">更多</a></span>${map.key.name}
						</div>
						<div id="video_list_holder"></div>
						<ul class="softolist">
							<s:iterator value="#map.value" var="episode">
								<li><a
									href="./video/episodeAction!list?videoId=${map.key.id }"
									target="_blank"
									title="${map.key.name}&nbsp;第${episode.episodeNo}集<s:if
											test="title != null">「${episode.title}」</s:if>">${map.key.name}&nbsp;第${episode.episodeNo}集<s:if
											test="title != null">「${episode.title}」</s:if></a></li>
							</s:iterator>
						</ul>
					</div>
				</s:iterator>
			</s:iterator>

			<div id="naruto_desk" style="height: 200px;">

				<!-- 字母下拉菜单 -->
				<div class="menu2_menu_container">
					<div class="menu2_nav" id="letterMainNav">
						<ul class="menu2_list">
							<li><a href="#" class="menu2_name">A</a></li>
							<li><a href="#" class="menu2_name">B</a></li>
							<li><a href="#" class="menu2_name">C</a></li>
							<li><a href="#" class="menu2_name">D</a></li>
							<li><a href="#" class="menu2_name">E</a></li>
							<li><a href="#" class="menu2_name">F</a></li>
							<li><a href="#" class="menu2_name">G</a></li>
							<li><a href="#" class="menu2_name">H</a></li>
							<li><a href="#" class="menu2_name">I</a></li>
							<li><a href="#" class="menu2_name">J</a></li>
							<li><a href="#" class="menu2_name">K</a></li>
							<li><a href="#" class="menu2_name">L</a></li>
							<li><a href="#" class="menu2_name">M</a></li>
							<li><a href="#" class="menu2_name">N</a></li>
							<li><a href="#" class="menu2_name">O</a></li>
							<li><a href="#" class="menu2_name">P</a></li>
							<li><a href="#" class="menu2_name">Q</a></li>
							<li><a href="#" class="menu2_name">R</a></li>
							<li><a href="#" class="menu2_name">S</a></li>
							<li><a href="#" class="menu2_name">T</a></li>
							<li><a href="#" class="menu2_name">U</a></li>
							<li><a href="#" class="menu2_name">V</a></li>
							<li><a href="#" class="menu2_name">W</a></li>
							<li><a href="#" class="menu2_name">X</a></li>
							<li><a href="#" class="menu2_name">Y</a></li>
							<li><a href="#" class="menu2_name">Z</a></li>
						</ul>	
							
				<div class="menu2_hover_cont wlyx">
				<div class="menu2_nav_cont">
					<div class="menu2_nav_li">
						<div class="menu2_nav_li_l">
						热门动画
						</div>
						<div class="menu2_nav_li_r">
							<a href="#">问道</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
							<a href="#">问道</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
						</div>
					</div>
					<div class="menu2_nav_li">
						<div class="menu2_nav_li_l">
						高清漫画
						</div>
						<div class="menu2_nav_li_r">
							<a href="#">问道</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
							<a href="#">问道</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
						</div>
					</div>
				</div>
			</div>
			
			<div class="menu2_hover_cont wlyx">
				<div class="menu2_nav_cont">
					<div class="menu2_nav_li">
						<div class="menu2_nav_li_l">
						热门动画
						</div>
						<div class="menu2_nav_li_r">
							<a href="#">问道22</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
							<a href="#">问道22</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
						</div>
					</div>
					<div class="menu2_nav_li">
						<div class="menu2_nav_li_l">
						高清漫画
						</div>
						<div class="menu2_nav_li_r">
							<a href="#">问道22</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
							<a href="#">问道22</a>┊<a href="#">DOTA2</a>┊<a href="#">魔兽世界</a>┊<a href="#">梦幻西游</a>┊<a href="#">九阴真经</a>┊<a href="#">颓废之心</a>┊<a href="#">英雄联盟</a>┊<a href="#">天龙八部</a>┊<a href="#">龙之谷</a>┊<a href="#">星辰变</a><br />    
						</div>
					</div>
				</div>
			</div>
					</div>
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
				<!-- 友情链接 -->
				<jsp:include page="friend_link.html" />
			</div>
		</div>
		<!-- 页脚 -->
		<jsp:include page="footer.html" />

		<script type="text/javascript">
			document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion="
					+ Math.ceil(new Date() / 3600000)
		</script>
		<script type="text/javascript" src="/v2/js/yxf.js"></script>
		<script language="javascript"
			src="http://rwq.youle55.com/r/mr_1553_3945.js"></script>
		<style>
#cs_couplet_left, #cs_couplet_right, #cs_right_bottom, #ft_couplet_left,
	#ft_couplet_right, #ft_right_bottom {
	z-index: 2147483647;
}
</style>
		<!--<script type="text/javascript">BAIDU_CLB_fillSlot("675989");</script>-->
		<script type="text/javascript">
			BAIDU_CLB_fillSlot("1045136");
		</script>
		<div id="BAIDU_DUP_wrapper_1045136_0"></div>
		<script charset="utf-8"
			src="http://cb.baidu.com/ecom?di=1045136&amp;dcb=BAIDU_DUP_define&amp;dtm=BAIDU_DUP2_SETJSONADSLOT&amp;dbv=2&amp;dci=0&amp;dri=0&amp;dis=0&amp;dai=3&amp;dds=&amp;drs=1&amp;dvi=1421289014&amp;ltu=http%3A%2F%2Fwww.narutom.com%2F&amp;liu=&amp;ltr=&amp;lcr=&amp;ps=2486x0&amp;psr=1366x768&amp;par=1366x728&amp;pcs=1335x579&amp;pss=1335x2486&amp;pis=-1x-1&amp;cfv=11&amp;ccd=32&amp;chi=1&amp;cja=true&amp;cpl=30&amp;cmi=90&amp;cce=true&amp;col=zh-CN&amp;cec=GBK&amp;cdo=-1&amp;tsr=3603&amp;tlm=1425683925&amp;tcn=1425717704&amp;tpr=1425717700730&amp;dpt=none&amp;coa=&amp;baidu_id="></script>
		<style>
.body-bg-left, .body-bg-right {
	display: block;
	position: absolute;
	width: 50%;
	z-index: 0;
}
/* 左  */
.body-bg-left {
	background:
		url("http://ubmcmm.baidustatic.com/media/v1/0f000ZsA20mNjBQXTanrkf.jpg")
		no-repeat scroll right 0px transparent;
	left: -500px;
	height: 580px;
	top: 0px;
}
/* 右  */
.body-bg-right {
	background:
		url("http://ubmcmm.baidustatic.com/media/v1/0f000nUXYBrYtRyEo9bcCf.jpg")
		no-repeat scroll 500px 0px transparent;
	right: 0px;
	height: 580px;
	top: 0px;
}
</style>
		<div id="beitou" style="display: block;">
			<!-- 左 -->
			<a href="http://g.2ksm.com/s/1/999/3938.html?uid=503134"
				target="_blank" class="body-bg-left"></a>
			<!-- 右 -->
			<a href="http://g.2ksm.com/s/1/999/3938.html?uid=503134"
				target="_blank" class="body-bg-right"></a>
		</div>
		<!-- beitou -->
		<div class="counts">
			<script type="text/javascript" src="/tj.js"></script>
			<div id="cnzz_stat_icon_30038114"></div>
			<script src=" http://w.cnzz.com/c.php?id=30038114"
				type="text/javascript"></script>
			<script
				src=" http://hm.baidu.com/h.js?ec1d3e383e58ca3346a6baf39b48040e"
				type="text/javascript"></script>
		</div>
		<div id="cs_right_bottom"
			style="position: fixed; bottom: 0px; right: 0px; overflow: hidden;">
			<div style="margin: 0px; padding: 0px; width: 270px; height: 200px;">
				<div style="position: absolute; z-index: 1;">
					<a
						href="http://c.x7ef.com/s/1/954/0.html?uid=301553&amp;ext=ZWIgICAgICAgICAgMWVTUFFVVVNMUlBRVkxTWVRVTFFMU1RTUlJMUVROUVRVTldWTlNMUlBRVVBTUFdRVlNUUFU%3D"
						target="_blank" id="cs_click_746"><img
						src="http://img.twcczhu.com/s/img/dot.gif" border="0" width="270"
						height="200" style="background-color: transparent;"></a>
				</div>
				<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
					codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,24,0"
					width="270" height="200" align="middle">
					<param name="movie"
						value="http://c.x7ef.com/b/1/954/fkd9fdd.swf?uid=301553">
					<param name="quality" value="high">
					<param name="wmode" value="transparent">
					<param name="scale" value="exactfit">
					<embed pluginspage="http://www.macromedia.com/go/getflashplayer"
						width="270" height="200" align="middle"
						type="application/x-shockwave-flash"
						src="http://c.x7ef.com/b/1/954/fkd9fdd.swf?uid=301553"
						scale="exactfit" quality="high" wmode="transparent">
				</object>
			</div>
			<div id="cs_rich_close" kc="0"
				dsturl="http://c.x7ef.com/s/1/954/0.html?uid=301553"
				eps="ZWIgICAgICAgICAgMWVTUFFVVVNMUlBRVkxTWVRVTFFMU1RTUlJMUVROUVRVTldWTlNMUlBRVVBTUFdRVlNUUFU%3D"
				closetype="0"
				style="z-index: 1999999; top: 3px; left: 234px; width: 25px; height: 13px; position: absolute; display: block;">
				<a href="javascript:;" target="_self"><img
					src="http://img.twcczhu.com/s/img/close.gif"
					ck="cs_rich_close_hide" border="0"
					style="width: 25px; height: 13px; cursor: pointer;"></a>
			</div>
</body>
<script type="text/javascript">
	/*
	 $(function(){
	 $.ajax({  
	 url:'./video/videoAction!list',  
	 type:'post',  
	 data:"{}",  
	 dataType:'json',  
	 success:function (data) {  
	 alert('aaaaaaa');
	 }  
	 });
	 });
	 */

	/*
	$(function() {
	$.ajax({
		url : './video/videoAction!list',
		type : 'post',
		dataType : 'json',
		data : {
			'word' : 'word',
			'word' : 'wordcount'
		//此处不能加‘，’号
		},
		error : function(e) {
			alert("error occured!!!");
		},
		success : function(data) {
			alert("succ");
			$("#video_list_holder").text("aaaaaaaaaaaaaaaaaaaaa");

			//var test = eval("(" + data + ")");
			//非循环的结果获得
			//var word = eval("(" + data + ")").word;
			//var wrodcount = eval("(" + data + ")").wordcount;
			//循环结果集
			//$.each(test, function(i) {
			//	desc += '<li>' + test[i].word + '(' + test[i].wordcount
			//			+ '%)</li>';
			//});
		}
	});
	});
	 */
</script>
</html>