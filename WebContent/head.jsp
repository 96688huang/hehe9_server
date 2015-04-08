<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div id="Head" class="Ncontainer">
	<div id="narutologo">
		<span class="red BOLD"><a href="http://www.narutom.com/">火影忍者中文网</a></span>
	</div>
	<div id="topbanner">
		<div class="container">
			<div id="search">
				<form id="searchForm" action="./video/videoListAction!list"
					method="post">
					<input id="searchName" type="text" name="searchName">&nbsp;&nbsp;
					<input class="button" type="submit" value="搜&nbsp;索">
				</form>

				<%-- 				   <s:form action="videoListAction!list" namespace="/video" method="post">
					    <s:textfield type="text" name="name"/>&nbsp;&nbsp;
					    <s:submit class="button" type="submit" value="搜&nbsp;索"/>
				    </s:form> --%>
			</div>
		</div>

		<!-- <script type="text/javascript">
					hym.show(0);
				</script> -->
		<!-- 原广告位 -->
		<!-- <iframe scrolling="no" frameborder="0" width="600" height="60"
					src="http://www.narutom.com/v2/v/i/4399.html"></iframe> -->
	</div>
</div>
<div id="Narutonav" class="Ncontainer">
	<ul>
		<li><a href="./"><span>首 页</span></a></li>
		<!-- <li><a href="http://www.narutom.com/comic/"
					onmouseover="showMenu(1)">火影忍者</a></li>
				<li><a href="http://www.narutom.com/bleach/"
					onmouseover="showMenu(2)" title="死神">死神</a></li>
				<li><a href="http://www.narutom.com/onepiece/"
					onmouseover="showMenu(3)" title="海贼王">海贼王</a></li>
				<li><a href="http://www.narutom.com/fairytail/"
					onmouseover="showMenu(5)" title="妖精的尾巴">妖精的尾巴</a></li>
				<li><a href="http://www.narutom.com/cartoon/"
					onmouseover="showMenu(4)">在线动漫</a></li>
				<li><a href="http://bbs.narutom.com/" target="_blank">火影论坛</a></li> -->
	</ul>
</div>
<div id="Ntop" class="Ncontainer">
	<div class="Nleft"></div>
	<div id="narutolist1" class="listMenu" style="display: ''">
		<span style="color:red; margin-left: 25px;">热门搜索：</span>&nbsp;&nbsp; <a href="javascript:onSearch('航海王')"
			title="海贼王">海贼王</a>&nbsp; <a href="javascript:onSearch('猪猪侠')"
			title="猪猪侠">猪猪侠</a>&nbsp;<a href="javascript:onSearch('尸兄')"
			title="尸兄">尸兄</a>&nbsp; <a href="javascript:onSearch('七龙珠')"
			title="七龙珠">七龙珠</a>&nbsp; <a href="javascript:onSearch('熊出没')"
			title="熊出末">熊出没</a>&nbsp; <a href="javascript:onSearch('灌篮高手')"
			title="灌篮高手">灌篮高手</a>&nbsp; <a href="javascript:onSearch('妖精的尾巴')"
			title="妖精的尾巴">妖精的尾巴</a>
	</div>

	<!-- 
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
			</div> -->
</div>
<script type="text/javascript">
	function onSearch(keyword) {
		if (keyword == null || keyword == '' || keyword == undefined) {
			return;
		}

		$("#searchName").attr("value", keyword);
		$("#searchForm").submit();
	}

	
</script>