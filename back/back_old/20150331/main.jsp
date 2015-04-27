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
<title>updating</title>
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
<link rel="stylesheet" href="./css/nar/index/bdsstyle.css"
	type="text/css">
</head>
<body>
	<div class="nmain">
		<div id="Head" class="Ncontainer">
			<div id="narutologo">
				<span class="red BOLD"><a href="http://www.narutom.com/">火影忍者中文网</a></span>
			</div>
			<div id="topbanner">
				<!-- <script type="text/javascript">
					hym.show(0);
				</script> -->
				<iframe scrolling="no" frameborder="0" width="600" height="60"
					src="http://www.narutom.com/v2/v/i/4399.html"></iframe>
			</div>
		</div>
		<div id="Narutonav" class="Ncontainer">
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
		<div id="Ntop" class="Ncontainer">
			<div class="Nleft"></div>
			<div id="narutolist1" class="listMenu" style="display: ''">
				<a href="http://www.narutom.com/news/" title="动漫新闻,最新的火影死神等动漫情报">动漫新闻</a>
				| <a href="http://www.narutom.com/comic/" title="火影忍者漫画">火影漫画</a> |
				<a href="http://www.narutom.com/video/" title="火影忍者动画">火影动画</a> | <a
					href="http://www.narutom.com/naruto-juchangban/" title="火影忍者剧场版">火影剧场版</a>
				| <a href="http://www.narutom.com/mv/" title="火影忍者MV">火影忍者MV</a> | <a
					href="http://www.narutom.com/juqingfenxi/" title="火影忍者剧情分析">火影剧情分析</a>
				| <a href="http://www.narutom.com/article/" title="火影忍者文章">火影文章</a>
				| <a href="http://www.narutom.com/pic/" title="火影忍者图库">火影图库</a> | <a
					href="http://www.narutom.com/wallpaper/" title="火影忍者壁纸">火影壁纸</a> |
				<a href="http://www.narutom.com/flash/" title="火影忍者Flash">火影Flash</a>
				| <a href="http://www.narutom.com/dengchang/" title="火影忍者资料">火影资料</a>
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
					href="http://www.narutom.com/cartoon/quanzhilieren/"
					title="全职猎人重制版" target="_blank">新全职猎人</a> | <a
					href="http://www.narutom.com/cartoon/seiya-omega/" title="圣斗士星矢Ω"
					target="_blank">圣斗士星矢Ω</a> | <a
					href="http://www.narutom.com/cartoon/yinhun/" title="银魂"
					target="_blank">银魂</a> | <a
					href="http://www.narutom.com/cartoon/anmei2/" title="我的妹妹哪有这么可爱第二季"
					target="_blank">我的妹妹哪有这么可爱第二季</a> | <a
					href="http://www.narutom.com/cartoon/xialan/"
					title="侠岚全集，动画片侠岚在线观看" target="_blank">侠岚</a> | <a
					href="http://www.narutom.com/cartoon/diancipaos/"
					title="某科学的超电磁炮S在线观看" target="_blank">某科学的超电磁炮S</a> | <a
					href="http://www.narutom.com/cartoon/xiaolirenchuan/" title="小李忍传"
					target="_blank">小李忍传</a> |<a href="http://www.narutom.com/cartoon/"
					title="更多动漫" target="_blank">更多动漫</a>
			</div>
		</div>
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
					<a href="/cartoon/" title="热闹动画片" target="_blank">热门动画片</a>
				</div>

				<s:iterator value="hotVideoListHolder" var="holder">
					<%-- <s:property value="name"/>|<s:property value="updateRemark"/>|<s:property value="iconUrl"/><p>
					 --%><ul id="indexcartoonList">
						<s:iterator value="holder">
							<li><a href="/s/naruto/" title="火影忍者全集" target="_blank"><img
										alt="${name}"
										src="${iconUrl}"></a><a
									href="http://www.narutom.com/video/27197.html"
									title="${name}" target="_blank"><font
										color="#FF0000"><s:property value="name"/></font></a></li>
						</s:iterator>
					</ul>
				</s:iterator>
				
				
				<ul id="indexcartoonList">
					<li><a href="/s/naruto/" title="火影忍者全集" target="_blank"><img
							alt="火影忍者"
							src="http://ui.narutom.com/images/cartoon/naruto_class.jpg"></a><a
						href="http://www.narutom.com/video/27197.html"
						title="火影忍者622集「逃走VS踪迹!」" target="_blank"><font
							color="#FF0000">火影忍者622集「逃</font></a></li>
					<li><a href="/onepiece/" title="海贼王" target="_blank"><img
							alt="海贼王" src="http://ui.narutom.com/images/cartoon/op_class.jpg"></a><a
						href="http://www.narutom.com/onepiece/video/27171.html"
						title="海贼王第683集「大地鸣动 破坏神巨大琵卡降临!」" target="_blank">海贼王第683集「大地</a></li>
					<li><a href="http://www.narutom.com/cartoon/quanzhilieren/"
						title="全职猎人重制版" target="_blank"><img alt="全职猎人重制版"
							src="http://ui.narutom.com/images/cartoon/lieren2011.jpg"></a><a
						href="http://www.narutom.com/cartoon/quanzhilieren/25683.html"
						title="全职猎人重制版第148集「至今为止×和×从今以后!」" target="_blank">全职猎人重制版第14</a></li>
					<li><a href="http://www.narutom.com/h/heizi2/"
						title="黑子的篮球第二季在线观看" target="_blank"><img alt="黑子的篮球第二季"
							src="http://ui.narutom.com/img/201306/heizi2.jpg"></a><a
						href="http://www.narutom.com/h/heizi2/23988.html"
						title="黑子的篮球第二季第25集 赢!(第二季完)" target="_blank">黑子的篮球第二季第</a></li>
					<li><a href="/cartoon/seiya-omega/" title="圣斗士星矢Ω"
						target="_blank"><img alt="圣斗士星矢Ω"
							src="http://ui.narutom.com/images/cartoon/seiya-omega.jpg"></a><a
						href="http://www.narutom.com/cartoon/seiya-omega/23990.html"
						title="圣斗士星矢Ω第97集「大结局」" target="_blank">圣斗士星矢Ω第97集</a></li>
					<li><a href="/cartoon/conan/" title="名侦探柯南" target="_blank"><img
							alt="名侦探柯南" src="http://ui.narutom.com/images/cartoon/kenan.jpg"></a><a
						href="http://www.narutom.com/cartoon/conan/27167.html"
						title="名侦探柯南第781集「麻烦的急救患者!」" target="_blank">名侦探柯南第781集「</a></li>
					<li><a href="http://www.narutom.com/l/lalalademaxiya/"
						title="啦啦啦德玛西亚" target="_blank"><img alt="啦啦啦德玛西亚"
							src="http://ui.narutom.com/images/cartoon/lalala.jpg"></a><a
						href="http://www.narutom.com/l/lalalademaxiya/20877.html"
						title="啦啦啦德玛西亚第30集 情义的威力" target="_blank">啦啦啦德玛西亚第30</a></li>
				</ul>
				<ul id="indexcartoonList">
					<li><a href="/s/naruto/" title="火影忍者全集" target="_blank"><img
							alt="火影忍者"
							src="http://ui.narutom.com/images/cartoon/naruto_class.jpg"></a><a
						href="http://www.narutom.com/video/27197.html"
						title="火影忍者622集「逃走VS踪迹!」" target="_blank"><font
							color="#FF0000">火影忍者622集「逃</font></a></li>
					<li><a href="/onepiece/" title="海贼王" target="_blank"><img
							alt="海贼王" src="http://ui.narutom.com/images/cartoon/op_class.jpg"></a><a
						href="http://www.narutom.com/onepiece/video/27171.html"
						title="海贼王第683集「大地鸣动 破坏神巨大琵卡降临!」" target="_blank">海贼王第683集「大地</a></li>
					<li><a href="http://www.narutom.com/cartoon/quanzhilieren/"
						title="全职猎人重制版" target="_blank"><img alt="全职猎人重制版"
							src="http://ui.narutom.com/images/cartoon/lieren2011.jpg"></a><a
						href="http://www.narutom.com/cartoon/quanzhilieren/25683.html"
						title="全职猎人重制版第148集「至今为止×和×从今以后!」" target="_blank">全职猎人重制版第14</a></li>
					<li><a href="http://www.narutom.com/h/heizi2/"
						title="黑子的篮球第二季在线观看" target="_blank"><img alt="黑子的篮球第二季"
							src="http://ui.narutom.com/img/201306/heizi2.jpg"></a><a
						href="http://www.narutom.com/h/heizi2/23988.html"
						title="黑子的篮球第二季第25集 赢!(第二季完)" target="_blank">黑子的篮球第二季第</a></li>
					<li><a href="/cartoon/seiya-omega/" title="圣斗士星矢Ω"
						target="_blank"><img alt="圣斗士星矢Ω"
							src="http://ui.narutom.com/images/cartoon/seiya-omega.jpg"></a><a
						href="http://www.narutom.com/cartoon/seiya-omega/23990.html"
						title="圣斗士星矢Ω第97集「大结局」" target="_blank">圣斗士星矢Ω第97集</a></li>
					<li><a href="/cartoon/conan/" title="名侦探柯南" target="_blank"><img
							alt="名侦探柯南" src="http://ui.narutom.com/images/cartoon/kenan.jpg"></a><a
						href="http://www.narutom.com/cartoon/conan/27167.html"
						title="名侦探柯南第781集「麻烦的急救患者!」" target="_blank">名侦探柯南第781集「</a></li>
					<li><a href="http://www.narutom.com/l/lalalademaxiya/"
						title="啦啦啦德玛西亚" target="_blank"><img alt="啦啦啦德玛西亚"
							src="http://ui.narutom.com/images/cartoon/lalala.jpg"></a><a
						href="http://www.narutom.com/l/lalalademaxiya/20877.html"
						title="啦啦啦德玛西亚第30集 情义的威力" target="_blank">啦啦啦德玛西亚第30</a></li>
				</ul>
				<ul id="indexcartoonList">
					<li><a href="/s/naruto/" title="火影忍者全集" target="_blank"><img
							alt="火影忍者"
							src="http://ui.narutom.com/images/cartoon/naruto_class.jpg"></a><a
						href="http://www.narutom.com/video/27197.html"
						title="火影忍者622集「逃走VS踪迹!」" target="_blank"><font
							color="#FF0000">火影忍者622集「逃</font></a></li>
					<li><a href="/onepiece/" title="海贼王" target="_blank"><img
							alt="海贼王" src="http://ui.narutom.com/images/cartoon/op_class.jpg"></a><a
						href="http://www.narutom.com/onepiece/video/27171.html"
						title="海贼王第683集「大地鸣动 破坏神巨大琵卡降临!」" target="_blank">海贼王第683集「大地</a></li>
					<li><a href="http://www.narutom.com/cartoon/quanzhilieren/"
						title="全职猎人重制版" target="_blank"><img alt="全职猎人重制版"
							src="http://ui.narutom.com/images/cartoon/lieren2011.jpg"></a><a
						href="http://www.narutom.com/cartoon/quanzhilieren/25683.html"
						title="全职猎人重制版第148集「至今为止×和×从今以后!」" target="_blank">全职猎人重制版第14</a></li>
					<li><a href="http://www.narutom.com/h/heizi2/"
						title="黑子的篮球第二季在线观看" target="_blank"><img alt="黑子的篮球第二季"
							src="http://ui.narutom.com/img/201306/heizi2.jpg"></a><a
						href="http://www.narutom.com/h/heizi2/23988.html"
						title="黑子的篮球第二季第25集 赢!(第二季完)" target="_blank">黑子的篮球第二季第</a></li>
					<li><a href="/cartoon/seiya-omega/" title="圣斗士星矢Ω"
						target="_blank"><img alt="圣斗士星矢Ω"
							src="http://ui.narutom.com/images/cartoon/seiya-omega.jpg"></a><a
						href="http://www.narutom.com/cartoon/seiya-omega/23990.html"
						title="圣斗士星矢Ω第97集「大结局」" target="_blank">圣斗士星矢Ω第97集</a></li>
					<li><a href="/cartoon/conan/" title="名侦探柯南" target="_blank"><img
							alt="名侦探柯南" src="http://ui.narutom.com/images/cartoon/kenan.jpg"></a><a
						href="http://www.narutom.com/cartoon/conan/27167.html"
						title="名侦探柯南第781集「麻烦的急救患者!」" target="_blank">名侦探柯南第781集「</a></li>
					<li><a href="http://www.narutom.com/l/lalalademaxiya/"
						title="啦啦啦德玛西亚" target="_blank"><img alt="啦啦啦德玛西亚"
							src="http://ui.narutom.com/images/cartoon/lalala.jpg"></a><a
						href="http://www.narutom.com/l/lalalademaxiya/20877.html"
						title="啦啦啦德玛西亚第30集 情义的威力" target="_blank">啦啦啦德玛西亚第30</a></li>
				</ul>
			</div>
			<div class="itabg">
				<script type="text/javascript">
					hym.show(3);
				</script>
				<iframe scrolling="no" frameborder="0" width="980" height="90"
					src="http://www.narutom.com/v2/v/i/key.html"></iframe>
			</div>
			<div class="index_downrank">
				<div class="ltitle">
					<span><a href="http://www.narutom.com/m/meishidefulu/"
						title="美食的俘虏全集">更多</a></span>美食的俘虏
				</div>
				<div id="video_list_holder"></div>
				<ul class="softolist">
					<li><a href="http://www.narutom.com/m/meishidefulu/23991.html"
						target="_blank" title="美食的俘虏第147集「最终话 阿虏和小松 踏上新旅程」">美食的俘虏第147集「最终话
							阿虏和</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23923.html"
						target="_blank" title="美食的俘虏第146集「传达过去吧 小松的呼唤 阿虏觉醒」">美食的俘虏第146集「传达过去吧
							小</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23780.html"
						target="_blank" title="美食的俘虏第145集「起死回生的一击 四大天王必杀技!」">美食的俘虏第145集「起死回生的一击</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23707.html"
						target="_blank" title="美食的俘虏第144集「终而复始 阿虏VS乔亚」">美食的俘虏第144集「终而复始
							阿虏</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23633.html"
						target="_blank" title="美食的俘虏第143集「惊愕 黑幕遮挡的乔亚的真面目」">美食的俘虏第143集「惊愕
							黑幕遮挡</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23557.html"
						target="_blank" title="美食的俘虏第142集「史上最强敌人 乔压出场」">美食的俘虏第142集「史上最强敌人
					</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23470.html"
						target="_blank" title="美食的俘虏第141集「阿虏反击 终极规则」">美食的俘虏第141集「阿虏反击
							终极</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23406.html"
						target="_blank" title="美食的俘虏第140集「逆袭 泽布拉开战」">美食的俘虏第140集「逆袭
							泽布拉开</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23341.html"
						target="_blank" title="美食的俘虏第139集「决战时刻 萨尼 最后一搏」">美食的俘虏第139集「决战时刻
							萨尼</a></li>
					<li><a href="http://www.narutom.com/m/meishidefulu/23256.html"
						target="_blank" title="美食的俘虏第138集「决斗 萨米VS多米洛特」">美食的俘虏第138集「决斗
							萨米VS多</a></li>
				</ul>
			</div>
			<div class="index_gamelist">
				<div class="ltitle">
					<span><a href="/onepiece/">更多</a></span>海贼王在线观看
				</div>
				<ul class="softblist">
					<li><span class="today">(03-01)</span><a
						href="http://www.narutom.com/onepiece/video/27171.html"
						target="_blank" title="海贼王第683集「大地鸣动 破坏神巨大琵卡降临!」">海贼王第683集「大地鸣动
							破坏神巨大琵卡降</a></li>
					<li><span class="today">(02-15)</span><a
						href="http://www.narutom.com/onepiece/video/27062.html"
						target="_blank" title="海贼王第682集「突破敌阵 路飞索隆开始反击!」">海贼王第682集「突破敌阵
							路飞索隆开始反击</a></li>
					<li><span class="today">(02-08)</span><a
						href="http://www.narutom.com/onepiece/video/27000.html"
						target="_blank" title="海贼王第681集「价值5亿的男人 被瞄准的乌索兰德!」">海贼王第681集「价值5亿的男人
							被瞄准的乌索</a></li>
					<li><span class="today">(02-01)</span><a
						href="http://www.narutom.com/onepiece/video/26936.html"
						target="_blank" title="海贼王第680集「恶魔的陷阱!德雷斯罗萨歼灭战!」">海贼王第680集「恶魔的陷阱!德雷斯罗萨歼灭</a></li>
					<li><span class="today">(01-25)</span><a
						href="http://www.narutom.com/onepiece/video/26874.html"
						target="_blank" title="海贼王第679集「飒爽登场!革命军总参谋长萨博?」">海贼王第679集「飒爽登场!革命军总参谋长萨</a></li>
					<li><span class="today">(01-18)</span><a
						href="http://www.narutom.com/onepiece/video/26805.html"
						target="_blank" title="海贼王第678集「火拳炸裂!烧烧果实的力量复活?」">海贼王第678集「火拳炸裂!烧烧果实的力量复</a></li>
					<li><span class="today">(01-11)</span><a
						href="http://www.narutom.com/onepiece/video/26666.html"
						target="_blank" title="海贼王第677集「传说复活!居鲁士全力一击?」">海贼王第677集「传说复活!居鲁士全力一击?」</a></li>
					<li><span class="today">(12-28)</span><a
						href="http://www.narutom.com/onepiece/video/26608.html"
						target="_blank" title="海贼王第676集「作战失败!英雄乌索兰度战死?」">海贼王第676集「作战失败!英雄乌索兰度战死</a></li>
					<li><span class="today">(12-21)</span><a
						href="http://www.narutom.com/onepiece/video/26548.html"
						target="_blank" title="海贼王第675集「命运般的邂逅 居鲁士与利库王!」">海贼王第675集「命运般的邂逅
							居鲁士与利库</a></li>
					<li><span class="today">(12-14)</span><a
						href="http://www.narutom.com/onepiece/video/26481.html"
						target="_blank" title="海贼王第674集「骗子乌索兰德逃跑中!」">海贼王第674集「骗子乌索兰德逃跑中!」</a></li>
				</ul>
			</div>
			<div class="index_gamelist">
				<div class="ltitle">
					<span><a href="http://www.narutom.com/cartoon/seiya-omega/"
						title="圣斗士星矢Ω全集，圣斗士星矢Ω在线观看">更多</a></span>圣斗士星矢Ω
				</div>
				<ul class="softblist">
					<li><span class="today">(03-30)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23990.html"
						target="_blank" title="圣斗士星矢Ω第97集「大结局」">圣斗士星矢Ω第97集「大结局」</a></li>
					<li><span class="today">(03-23)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23924.html"
						target="_blank" title="圣斗士星矢Ω第96集「最后的战斗 去吧!Ω圣斗士」">圣斗士星矢Ω第96集「最后的战斗
							去吧!Ω圣</a></li>
					<li><span class="today">(03-16)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23845.html"
						target="_blank" title="圣斗士星矢Ω第95集「超越神 星矢的小宇宙!」">圣斗士星矢Ω第95集「超越神
							星矢的小宇宙!</a></li>
					<li><span class="today">(03-09)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23779.html"
						target="_blank" title="圣斗士星矢Ω第94集「希望的战士 圣斗士的羁绊!」">圣斗士星矢Ω第94集「希望的战士
							圣斗士的羁</a></li>
					<li><span class="today">(03-02)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23705.html"
						target="_blank" title="圣斗士星矢Ω第93集「时之神 撒旦降临!」">圣斗士星矢Ω第93集「时之神
							撒旦降临!」</a></li>
					<li><span class="today">(02-23)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23634.html"
						target="_blank" title="圣斗士星矢Ω第92集「星矢的良心！从象回归！」">圣斗士星矢Ω第92集「星矢的良心！从象回归</a></li>
					<li><span class="today">(02-16)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23556.html"
						target="_blank" title="圣斗士星矢Ω第91集「雅典娜和帕拉斯 女神的决战！」">圣斗士星矢Ω第91集「雅典娜和帕拉斯
							女神的</a></li>
					<li><span class="today">(02-09)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23467.html"
						target="_blank" title="圣斗士星矢Ω第90集「金牛突进 到达、帕拉斯的所在!」">圣斗士星矢Ω第90集「金牛突进
							到达、帕拉斯</a></li>
					<li><span class="today">(02-02)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23407.html"
						target="_blank" title="圣斗士星矢Ω第89集「觉醒吧！究极的Ω！」">圣斗士星矢Ω第89集「觉醒吧！究极的Ω！」</a></li>
					<li><span class="today">(01-26)</span><a
						href="http://www.narutom.com/cartoon/seiya-omega/23342.html"
						target="_blank" title="圣斗士星矢Ω第88集「残留的意志 伟大圣斗士的教诲!」">圣斗士星矢Ω第88集「残留的意志
							伟大圣斗士</a></li>
				</ul>
			</div>
			<div id="naruto_desk" style="height: 175px;">
				<div class="ltitle">动画片大全</div>
				<div class="dmnew">
					<a href="http://www.narutom.com/g/gaodaORIGIN/" title="高达ORIGIN"
						target="_blank">高达ORIGIN</a><a
						href="http://www.narutom.com/y/youxiwangzhongzhiban/"
						title="游戏王重制版" target="_blank">游戏王重制版</a><a
						href="http://www.narutom.com/c/chaoshenxueyuandisanji/"
						title="超神学院第三季" target="_blank">超神学院第三季</a><a
						href="http://www.narutom.com/j/jidongzhanshigaodaTHE%20ORIGIN/"
						title="机动战士高达THE ORIGIN" target="_blank">机动战士高达THE ORIGIN</a><a
						href="http://www.narutom.com/c/chunjiedemaliya/" title="纯洁的玛利亚"
						target="_blank">纯洁的玛利亚</a><a
						href="http://www.narutom.com/l/longzhuZfuhuodeF/" title="龙珠Z复活的F"
						target="_blank">龙珠Z复活的F</a><a
						href="http://www.narutom.com/ss/siwangyouxing/" title="死亡游行"
						target="_blank">死亡游行</a><a
						href="http://www.narutom.com/c/huangwujindefafuna/"
						title="铳皇无尽的法夫纳" target="_blank">铳皇无尽的法夫纳</a><a
						href="http://www.narutom.com/i/ISUCA/" title="ISUCA"
						target="_blank">ISUCA</a><a
						href="http://www.narutom.com/x/xinmeimowangdeqiyuezhe/"
						title="新妹魔王的契约者" target="_blank">新妹魔王的契约者</a><a
						href="http://www.narutom.com/m/meinangaoxiaodiqiufangweibuLOVE/"
						title="美男高校地球防卫部LOVE" target="_blank">美男高校地球防卫部LOVE</a><a
						href="http://www.narutom.com/x/xuanzhuanshaonv/" title="旋转少女"
						target="_blank">旋转少女</a><a
						href="http://www.narutom.com/z/zhanguowushuang/" title="战国无双"
						target="_blank">战国无双</a><a
						href="http://www.narutom.com/y/yuanqishaonvyuanjieshendierji/"
						title="元气少女缘结神第二季" target="_blank">元气少女缘结神第二季</a><a
						href="http://www.narutom.com/f/FALCOMxueyuandierji/"
						title="Falcom学园第二季" target="_blank">Falcom学园第二季</a><a
						href="http://www.narutom.com/j/jueduishuangren/" title="绝对双刃"
						target="_blank">绝对双刃</a><a
						href="http://www.narutom.com/z/zhentangejushaonvfuermosidisiji/"
						title="侦探歌剧少女福尔摩斯第四季" target="_blank">侦探歌剧少女福尔摩斯第四季</a><a
						href="http://www.narutom.com/a/anshajiaoshi/" title="暗杀教室"
						target="_blank">暗杀教室</a><a
						href="http://www.narutom.com/a/Aldnoah%20Zerodierji/"
						title="Aldnoah Zero第二季" target="_blank">Aldnoah Zero第二季</a><a
						href="http://www.narutom.com/j/jianduiCollection/"
						title="舰队Collection" target="_blank">舰队Collection</a><a
						href="http://www.narutom.com/dd/dongjingshishiguidierji/"
						title="东京食尸鬼第二季" target="_blank">东京食尸鬼第二季</a><a
						href="http://www.narutom.com/ss/shengjianshidejinzhouyongchang/"
						title="圣剑使的禁咒咏唱" target="_blank">圣剑使的禁咒咏唱</a><a
						href="http://www.narutom.com/ss/shaonianhaolaiwudierji/"
						title="少年好莱坞第二季" target="_blank">少年好莱坞第二季</a><a
						href="http://www.narutom.com/w/wutouqishiyiwenludierji/"
						title="无头骑士异闻录第二季" target="_blank">无头骑士异闻录第二季</a><a
						href="http://www.narutom.com/m/menghuanxiyou/" title="梦幻西游"
						target="_blank">梦幻西游</a><a
						href="http://www.narutom.com/c/cangdefanaEXODUS/"
						title="苍穹的法芙娜EXODUS" target="_blank">苍穹的法芙娜EXODUS</a><a
						href="http://www.narutom.com/y/yaoguaimingdan/" title="妖怪名单"
						target="_blank">妖怪名单</a><a
						href="http://www.narutom.com/dd/doubizhelianmeng/" title="逗逼者联盟"
						target="_blank">逗逼者联盟</a><a
						href="http://www.narutom.com/c/chongshidisanji/" title="虫师第三季"
						target="_blank">虫师第三季</a><a
						href="http://www.narutom.com/w/woyaochengweishuangmawei/"
						title="我要成为双马尾" target="_blank">我要成为双马尾</a><a
						href="http://www.narutom.com/g/guaidaoxiaochou/" title="怪盗小丑"
						target="_blank">怪盗小丑</a><a
						href="http://www.narutom.com/z/zuanshiwangpaidierji/"
						title="钻石王牌第二季" target="_blank">钻石王牌第二季</a><a
						href="http://www.narutom.com/t/tiantidezhixu/" title="天体的秩序"
						target="_blank">天体的秩序</a><a
						href="http://www.narutom.com/q/qiyuanzui/" title="七原罪"
						target="_blank">七原罪</a><a
						href="http://www.narutom.com/j/jingjiechufazhe/" title="境界触发者"
						target="_blank">境界触发者</a><a
						href="http://www.narutom.com/m/mingyunzhiyezhongzhiban/"
						title="命运之夜重制版" target="_blank">命运之夜重制版</a><a
						href="http://www.narutom.com/x/xunzhaoyishideweilai/"
						title="寻找遗失的未来" target="_blank">寻找遗失的未来</a><a
						href="http://www.narutom.com/z/zaoanlianweidangaodian/"
						title="早安恋味蛋糕店" target="_blank">早安恋味蛋糕店</a><a
						href="http://www.narutom.com/m/moshukuaidou1412/" title="魔术快斗1412"
						target="_blank">魔术快斗1412</a><a
						href="http://www.narutom.com/x/xiaoduiyintian/" title="笑对阴天"
						target="_blank">笑对阴天</a><a
						href="http://www.narutom.com/g/gaodaGzhifuguoxingdong/"
						title="高达G之复国行动" target="_blank">高达G之复国行动</a><a
						href="http://www.narutom.com/ss/siyueshinidehuangyan/"
						title="四月是你的谎言" target="_blank">四月是你的谎言</a><a
						href="http://www.narutom.com/j/jishengshou/" title="寄生兽"
						target="_blank">寄生兽</a><a
						href="http://www.narutom.com/g/guanjijiayikadierji/"
						title="棺姬嘉依卡第二季" target="_blank">棺姬嘉依卡第二季</a><a
						href="http://www.narutom.com/dd/dianqijieshangdemanhuadian/"
						title="电器街上的漫画店" target="_blank">电器街上的漫画店</a><a
						href="http://www.narutom.com/dd/datushuguandemuyangren/"
						title="大图书馆的牧羊人" target="_blank">大图书馆的牧羊人</a><a
						href="http://www.narutom.com/b/baixiang/" title="白箱"
						target="_blank">白箱</a>
				</div>
			</div>
			<div id="num_index">&nbsp;&nbsp;合作伙伴 &amp; 友情链接
				&nbsp;&nbsp;&nbsp;(媒体合作 - veryer#gmail.com(#替换@) 欢迎PR≥5
				日访问量&gt;10万IP的网站交换链接)</div>
			<div id="friendlink">
				<div style="margin: 6px;">
					<a href="http://comic.qq.com/" target="_blank"><img
						src="http://www.narutom.com/images/logo/qq.gif" alt="腾讯动漫"
						border="0" width="88" height="31"></a> <a
						href="http://game.weibo.com/" target="_blank"><img
						src="http://www.narutom.com/images/logo/weiyouxi.gif" alt="微游戏"
						border="0" width="88" height="31"></a> <a
						href="http://www.dm5.com" target="_blank"><img
						src="http://www.narutom.com/images/logo/dm5logo.gif" alt="你的我的动漫屋"
						border="0" width="88" height="31"></a> <a
						href="http://hao.360.cn/dongmankatong.html" target="_blank"><img
						src="http://img.qihoo.com/images/2008/2010/dh.jpg" alt="360安全网址导航"
						border="0" width="88" height="31"></a> <a
						href="http://comic.zongheng.com/" target="_blank"><img
						src="http://dx2.narutom.com/images/logo/zhlogo.gif" alt="原创动漫"
						border="0" width="88" height="31"></a> <br> <a
						href="http://www.narutom.com" title="火影忍者" target="_blank"><strong>火影忍者</strong></a>
					<a href="http://www.hao123.com/cartoon.htm" title="hao123网址之家"
						target="_blank">hao123网址之家</a> <a href="http://www.mianbao99.com/"
						title="面包网" target="_blank">面包网</a> <a
						href="http://game.weibo.com/" title="动漫游戏" target="_blank">动漫游戏</a>
					<a href="http://comic.yesky.com/" title="天极动漫" target="_blank">天极动漫</a>
					<a href="http://www.iqiyi.com/dongman/" title="爱奇艺动漫"
						target="_blank">爱奇艺动漫</a> <a href="http://www.sasa123.com/"
						title="日本动漫" target="_blank">日本动漫</a> <a
						href="http://game.21cn.com/" title="单机游戏" target="_blank">单机游戏</a>
					<a href="http://www.repian.com/" title="热片网" target="_blank">热片网</a>
					<a href="http://www.mumayi.com/" title="木蚂蚁安卓游戏" target="_blank">木蚂蚁安卓游戏</a>
					<a href="http://www.4399dmw.com/" title="好看的动漫" target="_blank">好看的动漫</a>
					<a href="http://acg.178.com/" title="178动漫频道" target="_blank">178动漫频道</a>
					<a href="http://xyx.duowan.com/ " title="在线小游戏" target="_blank">在线小游戏</a>
					<a href="http://www.3dmgame.com" title="单机游戏" target="_blank">单机游戏</a>
					<a href="http://www.xialv.com/" title="侠侣周边游" target="_blank">侠侣周边游</a>
					<a href="http://pc.766.com/" title="单机游戏" target="_blank">单机游戏</a>
					<a href="http://www.9ku.com/" title="流行歌曲大全" target="_blank">流行歌曲大全</a>
					<a href="http://comic.letv.com/" title="乐视网动漫" target="_blank">乐视网动漫</a>
					<a href="http://www.manmankan.com" title="火影忍者漫画" target="_blank">火影忍者漫画</a>
					<a href="http://www.dm456.com/" title="日本动漫" target="_blank">日本动漫</a>
					<a href="http://dnf.tgbus.com" title="地下城与勇士" target="_blank">地下城与勇士</a>
					<a href="http://pc.tgbus.com/" title="单机游戏" target="_blank">单机游戏</a>
					<a href="http://www.juqingba.cn/" title="分集剧情" target="_blank">分集剧情</a>
					<a href="http://www.5068.com/" title="5068儿童网" target="_blank">5068儿童网</a>
					<a href="http://pc.duowan.com/ " title="单机游戏下载" target="_blank">单机游戏下载</a>
					<a href="http://www.07073.com/" title="07073游戏网" target="_blank">07073游戏网</a>
					<a href="http://www.yue365.com/" title="365音乐网" target="_blank">365音乐网</a>
					<a href="http://www.52tian.net/" title="动画片大全" target="_blank">动画片大全</a>
					<a href="http://www.xxdm.com/" title="动画片大全" target="_blank">动画片大全</a>
					<a href="http://www.imanhua.com" title="火影忍者" target="_blank">火影忍者</a>
					<a href="http://www.tuku.cc/" title="CC漫画图库" target="_blank">CC漫画图库</a>
					<a href="http://www.a67.com/" title="手机电影" target="_blank">手机电影</a>
					<a href="http://www.77vcd.com/" title="琪琪影院" target="_blank">琪琪影院</a>
					<a href="http://www.tianqi.com/" title="天气预报" target="_blank">天气预报</a>
					<a href="http://www.61baobao.com/" title="儿歌视频大全" target="_blank">儿歌视频大全</a>
					<a href="http://lol.tgbus.com/" title="英雄联盟" target="_blank">英雄联盟</a>
					<a href="http://www.zhuoku.com" title="桌面壁纸" target="_blank">桌面壁纸</a>
					<a href="http://www.youban.com/" title="儿歌视频大全" target="_blank">儿歌视频大全</a>
					<a href="http://www.91danji.com" title="91单机游戏" target="_blank">91单机游戏</a>
					<a href="http://www.yy8844.cn" title="音乐巴士" target="_blank">音乐巴士</a>
					<a href="http://www.7060.la/" title="手机电影下载" target="_blank">手机电影下载</a>
					<a href="http://www.yydm.com/" title="家庭教师" target="_blank">家庭教师</a>
					<a href="http://www.kanshu.com/" title="小说阅读网" target="_blank">小说阅读网</a>
					<a href="http://www.quanji.com/" title="电影下载" target="_blank">电影下载</a>
					<a href="http://www.37.com/" title="三七玩" target="_blank">三七玩</a> <a
						href="http://www.ik123.com/" title="dj舞曲" target="_blank">dj舞曲</a>
					<a href="http://www.rexuedongman.com/" title="火影忍者漫画"
						target="_blank">火影忍者漫画</a> <a href="http://www.9lala.com"
						title="九啦啦在线漫画" target="_blank">漫画</a> <a
						href="http://www.dj97.com" title="DJ" target="_blank">DJ</a> <a
						href="http://www.hahadm.com/" title="哈哈动漫网" target="_blank">哈哈动漫网</a>
					<a href="http://www.dodo8.com" title="火影忍者专题站-动动吧" target="_blank">火影忍者-动动吧</a>
					<a href="http://www.hltm.cc/" title="" target="_blank">红旅动漫</a> <a
						href="http://www.200dy.com/" title="200电影" target="_blank">200电影</a>
					<a href="http://www.8090kk.com/" title="百度影音" target="_blank">百度影音</a>
					<a href="http://www.vkeke.net/" title="百度影音" target="_blank">百度影音</a>
					<a href="http://www.enterdesk.com/" title="桌面壁纸" target="_blank">桌面壁纸</a>
					<a href="http://www.qqtu8.com/" title="搞笑图片" target="_blank">搞笑图片</a>
					<a href="http://www.dududm.com/" title="动画片大全" target="_blank">动画片大全</a>
					<a href="http://www.baidu.com/s?wd=%BB%F0%D3%B0%C8%CC%D5%DF"
						title="火影忍者" target="_blank">火影忍者</a>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<p>
			<a href="#">关于我们</a> -<a href="#">免责声明</a> - 链接合作 - 联系我们 - <a
				href="/sitemap/">网站地图</a>
		</p>
		<p>
			本站所有漫画均来自网友分享和上传，以便漫画爱好者研究漫画技巧和构图方式。若侵犯到您的权益，请立即联系我们(&#118;&#101;&#114;&#121;&#101;&#114;&#64;&#103;&#109;&#97;&#105;&#108;&#46;&#99;&#111;&#109;)删除。本站不负任何相关责任。<br />本站点所有微视频内容来源于土豆、优酷等,本站不参与也不提供任何视频的上传、下载,如有侵犯到您的版权,请联系我们,确认之后我们将立即删除.请支持正版,支持<a
				href="http://www.narutom.com" title="火影忍者中文网">火影忍者中文网</a>.<br>Copyright
			&reg; 2007-2013 <a href="http://www.narutom.com" title="火影忍者中文网"><font
				face="Verdana, Arial, Helvetica, sans-serif"><b>Narutom<font
						color="#CC0000">.Com</font></b></font></a>. All Rights Reserved .<a
				href="http://www.miibeian.gov.cn/" target="_blank">苏ICP备11016708号-5</a>
		</p>
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
            url:'/hehe9_ssm/video/videoAction!list',  
            type:'post',  
            data:"{}",  
            dataType:'json',  
            success:function (data) {  
                alert('aaaaaaa');
            }  
	});
});
*/

$(function() {
		$.ajax({
			url:'/hehe9_ssm/video/videoAction!list',  
            type:'post',  
            dataType:'json', 
			data:{
				'word':'word',
				'word':'wordcount'
			//此处不能加‘，’号
			},
			error:function(e) {
				alert("error occured!!!");
			},
			success:function(data) {
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
</script>
</html>