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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>play</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!-- <link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"> -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<%-- <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script> --%>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->

<%-- <script type="text/javascript" async=""
	src="http://static.duoshuo.com/embed.js" charset="UTF-8"> 
</script>
--%>

<%-- <script type="text/javascript" src="./js/jquery.min.js"></script>

<link rel="stylesheet" href="./css/nar/play/p.css" media="all"
	type="text/css">
<link rel="stylesheet" href="./css/nar/bdsstyle.css"
	type="text/css">


<!-- link -->
<link href="http://www.narutom.com/v3/css/p.css?20131009"
	type="text/css" rel="stylesheet">
<script type="text/javascript" async="" charset="utf-8"
	src="http://c.cnzz.com/core.php?web_id=30038114&amp;t=q">
	
</script>
<script type="text/javascript"
	src="http://img.twcczhu.com/js/rr/rich03.js?v3">
</script>

<link
	href="http://bdimg.share.baidu.com/static/css/bdsstyle.css?cdnversion=20131219"
	rel="stylesheet" type="text/css">

<link type="text/css" rel="stylesheet"
	href="http://static.duoshuo.com/styles/embed.default.css?5c5fd1ac.css">
<script
	src="http://bdimg.share.baidu.com/static/js/logger.js?cdnversion=396111">
	
</script>
<!-- end --> --%>

<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/menu2.js"></script>

<link rel="stylesheet" href="./css/pick.css" type="text/css">
<link rel="stylesheet" href="./css/pick_global.css" type="text/css">

<link rel="stylesheet" href="./css/common.css" type="text/css">

<script src="./css/nar/play/play.js"></script>

</head>
<body>

	<%-- <div id="BAIDU_DUP_fp_wrapper"
		style="position: absolute; left: -1px; bottom: -1px; z-index: 0; width: 0px; height: 0px; overflow: hidden; visibility: hidden; display: none;">

		<iframe id="BAIDU_DUP_fp_iframe"
			src="http://pos.baidu.com/wh/o.htm?ltr=http%3A%2F%2Fwww.narutom.com%2Fonepiece%2F"
			style="width: 0px; height: 0px; visibility: hidden; display: none;">

		</iframe>
	</div>

	<script
		src="http://s.00oo00.com/stat.php?uid=25533&amp;wid=28775&amp;adstype=3&amp;visit_count=2&amp;isnew=0&amp;ismonitor=0&amp;url=http%3A%2F%2Fwww.narutom.com%2Fonepiece%2Fvideo%2F27171.html&amp;ref=http%3A%2F%2Fwww.narutom.com%2Fonepiece%2F">
		
	</script>
	<script
		src="http://rc.71sem.com/popup.php?q=MjU1MzN8Mjg3NzV8OTl8OTl8Miw1fHwxNDI1OTAwMTA4fDE5ODA0ZTRiODE1MDJiMDVmNGMwMTk3MTNiMjFhN2MxfDB8MTAwfDB8MHwwfDcxc2VtLmNvbQ%3D%3D&amp;logo=1">
		
	</script>
	<iframe frameborder="0" style="display: none;"> </iframe>
	<div id="bdshare_s" style="display: block;">
		<iframe id="bdsIfr"
			style="position: absolute; display: none; z-index: 9999;"
			frameborder="0"> </iframe>
		<div id="bdshare_l" style="display: none;">
			<div id="bdshare_l_c">
				<h6>分享到</h6>
				<ul>
					<li><a href="#" class="bds_mshare mshare"> 一键分享 </a></li>
					<li><a href="#" class="bds_qzone qqkj"> QQ空间 </a></li>
					<li><a href="#" class="bds_tsina xlwb"> 新浪微博 </a></li>
					<li><a href="#" class="bds_bdysc bdysc"> 百度云收藏 </a></li>
					<li><a href="#" class="bds_renren rrw"> 人人网 </a></li>
					<li><a href="#" class="bds_tqq txwb"> 腾讯微博 </a></li>
					<li><a href="#" class="bds_bdxc bdxc"> 百度相册 </a></li>
					<li><a href="#" class="bds_kaixin001 kxw"> 开心网 </a></li>
					<li><a href="#" class="bds_tqf txpy"> 腾讯朋友 </a></li>
					<li><a href="#" class="bds_tieba bdtb"> 百度贴吧 </a></li>
					<li><a href="#" class="bds_douban db"> 豆瓣网 </a></li>
					<li><a href="#" class="bds_tsohu shwb"> 搜狐微博 </a></li>
					<li><a href="#" class="bds_bdhome bdhome"> 百度新首页 </a></li>
					<li><a href="#" class="bds_sqq sqq"> QQ好友 </a></li>
					<li><a href="#" class="bds_thx thx"> 和讯微博 </a></li>
					<li><a href="#" class="bds_more"> 更多... </a></li>
				</ul>
				<p>
					<a href="#" class="goWebsite"> 百度分享 </a>
				</p>
			</div>
		</div>
	</div> --%>
	<jsp:include page="head.jsp" />

	<!-- 	<div class="wraps gt1" id="cg1">
		<script src="http://www.77u.com/page/s.php?s=50&amp;w=960&amp;h=60"></script>
		<iframe
			src="http://www.77u.com/page/?s=50&amp;loc=http%3A//www.narutom.com/onepiece/video/27171.html&amp;ref=http%3A//www.narutom.com/onepiece/&amp;zhv=926"
			width="960" height="60" frameborder="0" marginwidth="0"
			marginheight="0" vspace="0" hspace="0" allowtransparency="true"
			scrolling="no"> </iframe>
	</div> -->
	<div style="width: 980px; margin: 0 auto; height: 30px;">
		<!-- <div class="v-left l15"> -->
		<div
			style="display: inline; float: left; text-align: left; margin: auto auto auto 8px;">
			您的位置： <a href="./"> 首页 </a> &nbsp;&gt;&nbsp; <a href="">
				${video.name } </a> &nbsp;&gt;&nbsp; <strong>第${episode.episodeNo }集&nbsp;<s:if
					test="episode.title != null">「${episode.title}」</s:if>
			</strong>
		</div>
		<!-- <div class="v-right r15"> -->
		<div style="display: inline;">
			<span id="setwid"> <a href="javascript:void(0);"
				onclick="javascript:setWid(1);" id="witer"> 宽屏 </a>
			</span> | <a href="javascript:void(0);" id="lighter"
				style="background: url('./img/mine/light.png') no-repeat left top; padding-left: 20px; color: #ff0000; z-index: 10001; width: 30px;">
				关灯 </a> | <a href="#pl"> 我要评论 </a>
			<%-- 			<span id="setwid"> <a href="javascript:void(0);"
				onclick="javascript:setWid(1);" id="witer"> 宽屏 </a>
			</span> / <a href="javascript:void(0);" class="lighter" id="lighter"> 关灯
			</a> / <a href="#pl"> 我要评论 </a> --%>
		</div>
	</div>
	<div class="PlayBody">
		<div class="play">
			<div class="pl" id="vleft">
				<div class="top">
					<h1>
						${video.name }第${episode.episodeNo }集
						<s:if test="episode.title != null">「${episode.title}」</s:if>
					</h1>
				</div>
				<div class="player" id="player">
					<%--         <script type="text/javascript">
          plays();
        </script> --%>
					<!--         <iframe 
            src="http://www.narutom.com/v3/player/sohu.html?v=2263872"
            id="naruto_v"
            name="naruto_v"
            frameborder="0"
            width="100%"
            height="480"
            scrolling="no"
            class="vborder">

        </iframe> -->
					<embed src="${episode.fileUrl }" id="naruto_v" class="vborder"
						name="naruto_v" type="application/x-shockwave-flash"
						allowscriptaccess="always"
						flashvars="playMovie=true&amp;isAutoPlay=true&amp;auto=1&amp;autoPlay=true&amp;"
						allowfullscreen="true" wmode="opaque" width="100%" height="480"></embed>
				</div>
			</div>
			<%-- 
			<div class="pr v-right" id="vright">
				<div class="r250 mb10" id="cg2">
					<script
						src="http://www.77u.com/page/s.php?s=112&amp;w=250&amp;h=250">
						
					</script>
					<iframe
						src="http://www.77u.com/page/?s=112&amp;loc=http%3A//www.narutom.com/onepiece/video/27171.html&amp;ref=http%3A//www.narutom.com/onepiece/&amp;zhv=926"
						width="250" height="250" frameborder="0" marginwidth="0"
						marginheight="0" vspace="0" hspace="0" allowtransparency="true"
						scrolling="no"> </iframe>
				</div>
				<div class="r250" id="cg25">
					<script
						src="http://www.77u.com/page/s.php?s=137&amp;w=250&amp;h=250">
						
					</script>
					<iframe
						src="http://www.77u.com/page/?s=137&amp;loc=http%3A//www.narutom.com/onepiece/video/27171.html&amp;ref=http%3A//www.narutom.com/onepiece/&amp;zhv=926"
						width="250" height="250" frameborder="0" marginwidth="0"
						marginheight="0" vspace="0" hspace="0" allowtransparency="true"
						scrolling="no"> </iframe>
				</div> 
				--%>
		</div>
	</div>
	</div>
	<%-- 	<div class="wraps gt1" id="cg3">
		<script>
			hym.show(3);
		</script>
		<iframe scrolling="no" frameborder="0" width="980" height="90"
			src="http://img.88rpg.net/html/click/1553_2003.html"> </iframe>
	</div> --%>
	<div class="links wraps">
		<%-- 		<div class="v-left l15 share">
			<script type="text/javascript">
				_vshare();
			</script>
			<!-- Baidu Button BEGIN -->
			<div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare">
				<a class="bds_qzone" title="分享到QQ空间" href="#"> </a> <a
					class="bds_tsina" title="分享到新浪微博" href="#"> </a> <a class="bds_tqq"
					title="分享到腾讯微博" href="#"> </a> <a class="bds_renren" title="分享到人人网"
					href="#"> </a> <a class="bds_tqf" title="分享到腾讯朋友" href="#"> </a> <a
					class="bds_qq" title="分享到QQ收藏" href="#"> </a> <a class="bds_baidu"
					title="分享到百度搜藏" href="#"> </a> <a class="bds_douban" title="分享到豆瓣网"
					href="#"> </a> <a class="bds_copy" title="分享到复制网址" href="#"> </a> <span
					class="bds_more"> 更多 </span> <a class="shareCount" href="#"
					title="累计分享61次"> 61 </a>
			</div>
			<!-- Baidu Button END -->
			<script type="text/javascript" id="bdshare_js"
				data="type=tools&amp;uid=616638"
				src="http://bdimg.share.baidu.com/static/js/bds_s_v2.js?cdnversion=396084">
				
			</script>
		</div> --%>
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

	<%-- <script type="text/javascript" src="http://cbjs.baidu.com/js/m.js">
		
	</script>
	<script type="text/javascript"
		src="http://www.narutom.com/v2/js/yxf.js">
		
	</script>
	<script language="javascript"
		src="http://rwq.youle55.com/r/mr_1553_3945.js">
		
	</script>
	<style type="text/css">
#cs_couplet_left, #cs_couplet_right, #cs_right_bottom, #ft_couplet_left,
	#ft_couplet_right, #ft_right_bottom {
	z-index: 2147483647;
}
</style>
	<!-- <script type="text/javascript" src="http://www.narutom.com/js/foot.js"></script> -->
	<script type="text/javascript">
		BAIDU_CLB_fillSlot("600142");
	</script>
	<div id="BAIDU_DUP_wrapper_600142_0">
		<iframe id="baidu_clb_slot_iframe_600142_0" src="about:blank"
			onload="BAIDU_DUP_CLB_renderFrame('600142_0')" width="960" height="0"
			vspace="0" hspace="0" allowtransparency="true" scrolling="no"
			marginwidth="0" marginheight="0" frameborder="0"
			style="border: 0; vertical-align: bottom; margin: 0; display: block;">

		</iframe>
	</div>
	<script charset="utf-8"
		src="http://cb.baidu.com/ecom?di=600142&amp;dcb=BAIDU_DUP_define&amp;dtm=BAIDU_DUP2_SETJSONADSLOT&amp;dbv=2&amp;dci=0&amp;dri=0&amp;dis=0&amp;dai=1&amp;dds=&amp;drs=1&amp;dvi=1421289014&amp;ltu=http%3A%2F%2Fwww.narutom.com%2Fonepiece%2Fvideo%2F27171.html&amp;liu=&amp;ltr=http%3A%2F%2Fwww.narutom.com%2Fonepiece%2F&amp;lcr=&amp;ps=1061x0&amp;psr=1920x1080&amp;par=1920x1040&amp;pcs=1903x908&amp;pss=1903x1061&amp;pis=-1x-1&amp;cfv=15&amp;ccd=24&amp;chi=1&amp;cja=true&amp;cpl=31&amp;cmi=48&amp;cce=true&amp;col=zh-CN&amp;cec=GBK&amp;cdo=-1&amp;tsr=32&amp;tlm=1425800896&amp;tcn=1425900056&amp;tpr=1425900055981&amp;dpt=none&amp;coa=&amp;baidu_id=">
		
	</script>
	<script charset="utf-8"
		src="http://dup.baidustatic.com/painter/clb/fixed7o.js">
		
	</script>
	<script type="text/javascript" src="http://www.narutom.com/tj.js">
		
	</script>
	<script type="text/javascript">
		BAIDU_CLB_fillSlot("676368");
	</script>
	<div id="BAIDU_DUP_wrapper_676368_0"></div>
	<script charset="utf-8"
		src="http://cb.baidu.com/ecom?di=676368&amp;dcb=BAIDU_DUP_define&amp;dtm=BAIDU_DUP2_SETJSONADSLOT&amp;dbv=2&amp;dci=0&amp;dri=0&amp;dis=0&amp;dai=2&amp;dds=&amp;drs=1&amp;dvi=1421289014&amp;ltu=http%3A%2F%2Fwww.narutom.com%2Fonepiece%2Fvideo%2F27171.html&amp;liu=&amp;ltr=http%3A%2F%2Fwww.narutom.com%2Fonepiece%2F&amp;lcr=&amp;ps=1061x0&amp;psr=1920x1080&amp;par=1920x1040&amp;pcs=1903x908&amp;pss=1903x1061&amp;pis=-1x-1&amp;cfv=15&amp;ccd=24&amp;chi=1&amp;cja=true&amp;cpl=31&amp;cmi=48&amp;cce=true&amp;col=zh-CN&amp;cec=GBK&amp;cdo=-1&amp;tsr=316&amp;tlm=1425800896&amp;tcn=1425900056&amp;tpr=1425900055981&amp;dpt=none&amp;coa=&amp;baidu_id=">
		
	</script>
	<script src="http://rc.71sem.com/view.php?uid=25533">
		
	</script>
	<div id="cnzz_stat_icon_30038114"></div>
	<script src=" http://w.cnzz.com/c.php?id=30038114"
		type="text/javascript">
		
	</script>
	<div id="cs_right_bottom"
		style="position: fixed; bottom: 0px; right: 0px; overflow: hidden;">
		<div style="margin: 0px; padding: 0px; width: 270px; height: 200px;">
			<div style="position: absolute; z-index: 1;">
				<a
					href="http://c.x7ef.com/s/1/954/0.html?uid=301553&amp;ext=MmQgICAgICAgICAgY2NTUFFVVVNMUlBRVkxTWVRVTFFMU1RTUlJMUVROUVVSTlZUTldUTFJQUVVQU1BZUVlSUVRW"
					target="_blank" id="cs_click_816"> <img
					src="http://img.twcczhu.com/s/img/dot.gif" border="0" width="270"
					height="200" style="background-color: transparent;">

				</a>
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
					scale="exactfit" quality="high" wmode="transparent"
					is-video-flag="true">
			</object>
		</div>
		<div id="cs_rich_close" kc="0"
			dsturl="http://c.x7ef.com/s/1/954/0.html?uid=301553"
			eps="MmQgICAgICAgICAgY2NTUFFVVVNMUlBRVkxTWVRVTFFMU1RTUlJMUVROUVVSTlZUTldUTFJQUVVQU1BZUVlSUVRW"
			closetype="0"
			style="z-index: 1999999; top: 3px; left: 234px; width: 25px; height: 13px; position: absolute; display: block;">

			<a href="javascript:;" target="_self"> <img
				src="http://img.twcczhu.com/s/img/close.gif" ck="cs_rich_close_hide"
				border="0" style="width: 25px; height: 13px; cursor: pointer;">

			</a>
		</div>
	</div>
	<script
		src=" http://hm.baidu.com/h.js?ec1d3e383e58ca3346a6baf39b48040e"
		type="text/javascript">
		
	</script>

	<div id="cs_left_couplet"
		style="position: fixed; overflow: hidden; left: 103px; top: 131px; width: 120px; height: 290px;">

		<div style="margin: 0px; padding: 0px; width: 120px; height: 270px;">
			<div style="position: absolute; z-index: 1;">
				<a
					href="http://c.x7ef.com/s/1/754/0.html?uid=1316108&amp;ext=YzcgICAgICAgICAgZjVRU1FWUVBYTFJSWFZQTFZTUVNMUExTVFZYV0xUUk5RWVlOVVJOUlFXTFJQUVVQU1FQUlJRUFFT"
					target="_blank"> <img
					src="http://img.twcczhu.com/s/img/dot.gif" border="0" width="120"
					height="270" style="background-color: transparent;">

				</a>
			</div>
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
				codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,24,0"
				width="120" height="270" align="middle">

				<param name="movie"
					value="http://c.x7ef.com/b/1/754/efd9fkd.swf?uid=1316108">
				<param name="quality" value="high">
				<param name="wmode" value="transparent">
				<embed pluginspage="http://www.macromedia.com/go/getflashplayer"
					width="120" height="270" align="middle"
					type="application/x-shockwave-flash"
					src="http://c.x7ef.com/b/1/754/efd9fkd.swf?uid=1316108"
					quality="high" wmode="transparent">
			</object>
		</div>
		<div id="cs_rich_close_left" keepclose="false"
			style="position: fixed; background-color: rgb(221, 221, 221); width: 120px; height: 20px; margin-top: 2px;">

			<div style="float: right; width: 50px; height: 19px; border: 0px;">
				<img src="http://img.twcczhu.com/s/img/cb.gif"
					ck="cs_rich_close_left_hide" keepclose="false"
					style="width: 50px; height: 19px; cursor: pointer;">

			</div>
		</div>
	</div>
	<div id="cs_right_couplet"
		style="position: fixed; overflow: hidden; right: 103px; top: 131px; width: 120px; height: 290px;">

		<div style="margin: 0px; padding: 0px; width: 120px; height: 270px;">
			<div style="position: absolute; z-index: 1;">
				<a
					href="http://c.x7ef.com/s/1/755/0.html?uid=1316108&amp;ext=OWQgICAgICAgICAgZjFRU1FWUVBYTFJSWFZQTFZTUVNMUExTVFZYWUxUUk5RWVlOVVJOUlFXTFJQUVVQU1FQUlJRUFFT"
					target="_blank"> <img
					src="http://img.twcczhu.com/s/img/dot.gif" border="0" width="120"
					height="270" style="background-color: transparent;">

				</a>
			</div>
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
				codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,24,0"
				width="120" height="270" align="middle">

				<param name="movie"
					value="http://c.x7ef.com/b/1/755/efd9fkd.swf?uid=1316108">
				<param name="quality" value="high">
				<param name="wmode" value="transparent">
				<embed pluginspage="http://www.macromedia.com/go/getflashplayer"
					width="120" height="270" align="middle"
					type="application/x-shockwave-flash"
					src="http://c.x7ef.com/b/1/755/efd9fkd.swf?uid=1316108"
					quality="high" wmode="transparent">
			</object>
		</div>
		<div id="cs_rich_close_right" keepclose="false"
			style="position: fixed; background-color: rgb(221, 221, 221); width: 120px; height: 20px; margin-top: 2px;">

			<div style="float: right; width: 50px; height: 19px; border: 0px;">
				<img src="http://img.twcczhu.com/s/img/cb.gif"
					ck="cs_rich_close_right_hide" keepclose="false"
					style="width: 50px; height: 19px; cursor: pointer;">

			</div>
		</div>
	</div>
	<div class="counts" id="div_cg1"></div>
	<div class="counts" id="div_cg2"></div>
	<div class="counts" id="div_cg25"></div>
	<div id="light" title="双击关灯(Esc)"></div>
	<script type="text/javascript">
		document.getElementById("cg1").innerHTML = document
				.getElementById("div_cg1").innerHTML;
		document.getElementById("div_cg1").innerHTML = "";
		document.getElementById("cg2").innerHTML = document
				.getElementById("div_cg2").innerHTML;
		document.getElementById("div_cg2").innerHTML = "";
		document.getElementById("cg25").innerHTML = document
				.getElementById("div_cg25").innerHTML;
		document.getElementById("div_cg25").innerHTML = "";
		player.lighter.bind();
		//var duoshuoQuery = {
		//	short_name : "narutom"
		//};
		//(function() {
		//	var ds = document.createElement('script');
		//	ds.type = 'text/javascript';
		//	ds.async = true;
		//	ds.src = 'http://static.duoshuo.com/embed.js';
		//	ds.charset = 'UTF-8';
		//	(document.getElementsByTagName('head')[0] || document
		//			.getElementsByTagName('body')[0]).appendChild(ds);
		//})();
	</script> --%>
</body>
</html>