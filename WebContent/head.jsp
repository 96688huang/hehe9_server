<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<div id="Head" class="Ncontainer">
	<div style="display: inline-block;">
		<a href="./" title="www.dmVCD.com" style="min-height: 60px; float: left; margin-top: 20px;"> 
		<img alt="www.dmvcd.com"
			src="./img/logo_brief.png">
		</a> 
		<a href="./" title="蒙其·d·路飞" style="float: right;"> 
		<img id="onepieceGif" alt=""
			src="./img/gif/as_1.gif" />
		</a>
	</div>
	<div id="searchBar">
		<form id="searchForm" action="./video/videoListAction!list"
			method="post">
			<input id="searchName" type="text" name="searchName">&nbsp;&nbsp;
			<input id="searchBtn" type="submit" value="搜&nbsp;索">
		</form>
	</div>
	<div id="Dmvcdnav" class="Ncontainer">
		<ul>
			<li><a href="./" title="www.dmVCD.com" target="_blank">首 页</a></li>
		</ul>
	</div>
	<div id="Ntop" class="Ncontainer">
		<div class="Nleft"></div>
		<div class="listMenu">
			<span style="color: red; margin-left: 25px; font-weight: bold;">热门搜索：</span>&nbsp;&nbsp;
			<a href="javascript:onSearch('航海王')" title="海贼王">&nbsp;海贼王&nbsp;</a>&nbsp;
			<a href="javascript:onSearch('猪猪侠')" title="猪猪侠">&nbsp;猪猪侠&nbsp;</a>&nbsp;<a
				href="javascript:onSearch('尸兄')" title="尸兄">&nbsp;尸兄&nbsp;</a>&nbsp;
			<a href="javascript:onSearch('七龙珠')" title="七龙珠">&nbsp;七龙珠&nbsp;</a>&nbsp;
			<a href="javascript:onSearch('熊出没')" title="熊出末">&nbsp;熊出没&nbsp;</a>&nbsp;
			<a href="javascript:onSearch('灌篮高手')" title="灌篮高手">&nbsp;灌篮高手&nbsp;</a>&nbsp;
			<a href="javascript:onSearch('妖精的尾巴')" title="妖精的尾巴">&nbsp;妖精的尾巴&nbsp;</a>
		</div>
	</div>
</div>
<!-- jumpForm -->
<form id="jumpForm" method="post" target="_blank">
<!-- <input id="resetBtn" type="reset" name="reset" style="display: none;" /> -->
</form>
