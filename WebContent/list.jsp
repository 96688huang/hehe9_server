<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>list</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!-- <link rel="stylesheet"
href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"> -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->

<link rel="stylesheet" href="./css/nar/list/index2.css" media="all"
	type="text/css">
<link rel="stylesheet" href="./css/nar/index/bdsstyle.css"
	type="text/css">
</head>
<body>
	<div id="Head" class="Ncontainer2">
		<div id="narutologo">
			<span class="red BOLD"><a href="http://www.narutom.com/">火影忍者中文网</a></span>
		</div>
		<div id="topbanner">
			<script type="text/javascript">
				hym.show(0);
			</script>
		</div>
	</div>
	<div id="Narutonav" class="Ncontainer2">
		<ul>
			<li><a href="http://www.narutom.com/"><span>首 页</span></a></li>
			<li><a href="http://www.narutom.com/comic/"
				onmouseover="showMenu(1)">火影忍者</a></li>
			<li><a href="http://www.narutom.com/bleach/"
				onmouseover="showMenu(2)" title="死神">死神</a></li>
			<li><a href="http://www.narutom.com/onepiece/"
				onmouseover="showMenu(3)" title="海贼王">海贼王</a></li>
			<li><a href="http://www.narutom.com/fairytail/"
				onmouseover="showMenu(5)" title="妖精的尾巴">妖精的尾巴</a></li>
			<li><a href="http://www.narutom.com/cartoon/"
				onmouseover="showMenu(4)">在线动漫</a></li>
			<li><a href="http://bbs.narutom.com/" target="_blank">火影论坛</a></li>
		</ul>
	</div>
	<div id="Ntop2" class="Ncontainer2">
		<div class="Nleft"></div>
		<div id="narutolist1" class="listMenu" style="display: ''">
			<a href="http://www.narutom.com/news/" title="动漫新闻,最新的火影死神等动漫情报">动漫新闻</a>
			| <a href="http://www.narutom.com/comic/" title="火影忍者漫画">火影漫画</a> | <a
				href="http://www.narutom.com/video/" title="火影忍者动画">火影动画</a> | <a
				href="http://www.narutom.com/naruto-juchangban/" title="火影忍者剧场版">火影剧场版</a>
			| <a href="http://www.narutom.com/mv/" title="火影忍者MV">火影忍者MV</a> | <a
				href="http://www.narutom.com/juqingfenxi/" title="火影忍者剧情分析">火影剧情分析</a>
			| <a href="http://www.narutom.com/article/" title="火影忍者文章">火影文章</a> |
			<a href="http://www.narutom.com/pic/" title="火影忍者图库">火影图库</a> | <a
				href="http://www.narutom.com/wallpaper/" title="火影忍者壁纸">火影壁纸</a> | <a
				href="http://www.narutom.com/flash/" title="火影忍者Flash">火影Flash</a> |
			<a href="http://www.narutom.com/dengchang/" title="火影忍者资料">火影资料</a>
		</div>
		<div id="narutolist2" class="listMenu" style="display: none;">
			<a href="http://www.narutom.com/bleach/manhua/" title="死神漫画">死神漫画</a>
			| <a href="http://www.narutom.com/bleach/video/" title="死神在线观看">死神在线观看</a>
			| <a href="http://www.narutom.com/juchangban/bleach/" title="死神剧场版">死神剧场版</a>
		</div>
		<div id="narutolist3" class="listMenu" style="display: none;">
			<a href="http://www.narutom.com/onepiece/manhua/" title="海贼王漫画">海贼王漫画</a>
			| <a href="http://www.narutom.com/onepiece/video/" title="海贼王在线观看">海贼王在线观看</a>
			| <a href="http://www.narutom.com/juchangban/onepiece/"
				title="海贼王剧场版">海贼王剧场版</a>
		</div>
		<div id="narutolist5" class="listMenu" style="display: none;">
			<a href="http://www.narutom.com/fairytail/manhua/" title="妖精的尾巴漫画">妖精的尾巴漫画</a>
			| <a href="http://www.narutom.com/fairytail/v/" title="妖精的尾巴在线观看">妖精的尾巴在线观看</a>
		</div>
		<div id="narutolist4" class="listMenu" style="display: none;">
			<a href="http://www.narutom.com/cartoon/conan/" title="名侦探柯南"
				target="_blank">名侦探柯南</a> | <a
				href="http://www.narutom.com/cartoon/quanzhilieren/" title="全职猎人重制版"
				target="_blank">新全职猎人</a> | <a
				href="http://www.narutom.com/cartoon/seiya-omega/" title="圣斗士星矢Ω"
				target="_blank">圣斗士星矢Ω</a> | <a
				href="http://www.narutom.com/cartoon/yinhun/" title="银魂"
				target="_blank">银魂</a> | <a
				href="http://www.narutom.com/cartoon/anmei2/" title="我的妹妹哪有这么可爱第二季"
				target="_blank">我的妹妹哪有这么可爱第二季</a> | <a
				href="http://www.narutom.com/cartoon/xialan/" title="侠岚全集，动画片侠岚在线观看"
				target="_blank">侠岚</a> | <a
				href="http://www.narutom.com/cartoon/diancipaos/"
				title="某科学的超电磁炮S在线观看" target="_blank">某科学的超电磁炮S</a> | <a
				href="http://www.narutom.com/cartoon/xiaolirenchuan/" title="小李忍传"
				target="_blank">小李忍传</a> | <a href="http://www.narutom.com/cartoon/"
				title="更多动漫" target="_blank">更多动漫</a>
		</div>
	</div>
	<div class="wrap2 clearfix">
		<div id="nav">
			<p>
				<label>您的位置: <!--empire.url--> <a
					href="http://www.narutom.com/" class="classlinkclass">首页</a>&nbsp;>&nbsp;<a
					href="http://www.narutom.com/onepiece/" class="classlinkclass">海贼王</a>&nbsp;>&nbsp;<a
					href="http://www.narutom.com/onepiece/video/"
					class="classlinkclass">海贼王在线观看</a>&nbsp;><!--empire.url--></label><span><script
						src="/style/nav.js"></script></span>
			</p>
		</div>
		<div class="wrap2 vtg3">
			<script type="text/javascript">
				hym.show(1);
			</script>
		</div>
		<div id="container2" class="clearfix">
			<div id="content2">
				<div class="listitem">
					<h1>${video.name}在线观看</h1>
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
				</div>
				<div class="rblank"></div>
				<div class="pagenav">
					分页：页次：<b>${page}/${pageCount }</b>&nbsp;每页<b>${queryCount }</b>&nbsp;总数<b>${total }</b>&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="./video/episodeAction!list?videoId=${video.id}&page=1">首页</a>&nbsp;&nbsp;<a
						href="./video/episodeAction!list?videoId=${video.id}&page=${page -1}">上一页</a>&nbsp;&nbsp;<a
						href="./video/episodeAction!list?videoId=${video.id}&page=${page +1}">下一页</a>&nbsp;&nbsp;<a
						href="./video/episodeAction!list?videoId=${video.id}&page=${pageCount}">尾页</a>&nbsp;&nbsp;
						&nbsp;&nbsp;转到:&nbsp;&nbsp;&nbsp;&nbsp; 
					<select name=select
						onchange="self.location.href=this.options[this.selectedIndex].value">
						<!-- 页码 -->
						<s:bean name="org.apache.struts2.util.Counter" var="counter">
							<s:param name="first" value="1" />
							<s:param name="last" value="pageCount" />
							<s:iterator>
								<option id="${current-1 }"
									value="./video/episodeAction!list?videoId=${video.id}&page=<s:property value="current-1" />">
									第<s:property value="current-1" />页
								</option>
							</s:iterator>
						</s:bean>
					</select>
					<!-- 还原选中页码 -->
					<script type="text/javascript">
						$("#${page}").attr("selected", true);
					</script>

					<%-- 另一种实现方式					
 					<s:iterator>
					<option value="<s:property />"
					<s:if test="%{page==(current-1)}">selected="selected"</s:if>>
					<s:property /> 
					--%>
					<!-- end -->
				</div>
			</div>
			<div id="sidebar">
				<div id="menunav" class="clearfix">
					<div class="ltitle">本站海贼微信公众号</div>
					<ul id="comiczt">
						<div style="width: 180px; padding-left: 10px;">
							<img src="/v3/wxop.jpg" /><BR>打开微信扫一扫，<br>关注本站公众号，手机微信直接看动画漫画！
						</div>
					</ul>
				</div>
				<div class="blank"></div>

				<div class="sidebarad">
					<div class="ltitle">更新</div>
					<ul class="softolist">
						<li><a href="http://www.narutom.com/comic/26113.html"
							target="_blank" title="火影忍者最终话「漩涡鸣人!」">火影忍者最终话「漩涡鸣人!」</a></li>
						<li><a href="http://www.narutom.com/video/27197.html"
							target="_blank" title="火影忍者622集「逃走VS踪迹!」"><font
								color="#FF0000">火影忍者622集「逃走VS踪迹!」</font></a></li>
						<li><a href="http://www.narutom.com/news/27204.html"
							target="_blank" title="动画「伪恋:」4月10日首播 新角色公开声优沼仓爱美加盟">动画「伪恋:」4月10日首播
								新角</a></li>
						<li><a href="http://www.narutom.com/juqingfenxi/27055.html"
							target="_blank" title="FD分析组|非主流分析 - 查克拉 ">FD分析组|非主流分析 - 查克拉</a></li>
						<li><a
							href="http://www.narutom.com/onepiece/video/27171.html"
							target="_blank" title="海贼王第683集「大地鸣动 破坏神巨大琵卡降临!」">海贼王第683集「大地鸣动
								破坏</a></li>
						<li><a href="http://www.narutom.com/bleach/video/14765.html"
							target="_blank" title="死神第366集「变化的历史!不变的心灵!!」[完结]"><font
								color="#5C44BB">死神第366集「变化的历史!不变</font></a></li>
						<li><a href="http://www.narutom.com/tongren/27036.html"
							target="_blank" title="UZUMAKI NARUTO 画页全集!">UZUMAKI NARUTO
								画页全集!</a></li>
						<li><a href="http://www.narutom.com/tongren/27030.html"
							target="_blank" title="火影忍者佐助写轮眼传第3话!">火影忍者佐助写轮眼传第3话!</a></li>
						<li><a href="http://www.narutom.com/tongren/27029.html"
							target="_blank" title="火影忍者佐助写轮眼传第2话!">火影忍者佐助写轮眼传第2话!</a></li>
					</ul>
				</div>
				<div class="blank"></div>
				<div id="list_recommendation" class="clearfix">
					<div class="ltitle">热门</div>
					<ul class="softolist">
						<li><a href="http://www.narutom.com/tongren/8343.html"
							target="_blank" title="火影忍者外传-黄色闪光">火影忍者外传-黄色闪光</a></li>
						<li><a
							href="http://www.narutom.com/article/huoyingrenzhexiaozuzhi.html"
							target="_blank" title="火影忍者晓组织全资料">火影忍者晓组织全资料</a></li>
						<li><a href="http://www.narutom.com/tongren/8334.html"
							target="_blank" title="四代封印九尾(同人漫画)">四代封印九尾(同人漫画)</a></li>
						<li><a href="http://www.narutom.com/tongren/8340.html"
							target="_blank" title="火影忍者同人：大蛇丸vs鼬">火影忍者同人：大蛇丸vs鼬</a></li>
						<li><a href="http://www.narutom.com/video/8470.html"
							target="_blank" title="火影忍者298-299集「阿斯玛战死！绝望和恸哭」">火影忍者298-299集「阿斯玛战死</a></li>
						<li><a href="http://www.narutom.com/video/8417.html"
							target="_blank" title="火影忍者295集「老僧的祈祷」">火影忍者295集「老僧的祈祷」</a></li>
						<li><a
							href="http://www.narutom.com/article/dashewan-qingren.html"
							target="_blank" title="大蛇丸殿的十大绯闻情人">大蛇丸殿的十大绯闻情人</a></li>
						<li><a href="http://www.narutom.com/video/8455.html"
							target="_blank" title="火影忍者296-297集「晓VS阿斯玛班决战特别篇」">火影忍者296-297集「晓VS阿斯玛</a></li>
						<li><a
							href="http://www.narutom.com/pic/naruto_pic/huoyingsige-xiao.html"
							target="_blank" title="火影四格：晓的搞笑生活">火影四格：晓的搞笑生活</a></li>
						<li><a href="http://www.narutom.com/video/8353.html"
							target="_blank" title="火影忍者292集「来自忍者的威胁」">火影忍者292集「来自忍者的威胁</a></li>
					</ul>
				</div>

			</div>
		</div>
		<div class="line"></div>
	</div>
	<!-- 页脚 -->
	<jsp:include page="footer.html" />

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