<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<title>动漫VCD-网站地图</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<style type="text/css">
body {
	background-color: #DDD;
	font: normal 80% "Trebuchet MS", "Helvetica", sans-serif;
	margin: 0;
	text-align: center;
}

#cont {
	margin: auto;
	width: 1000px;
	text-align: left;
}

a:link, a:visited {
	color: #0180AF;
	text-decoration: underline;
}

a:hover {
	color: #666;
}

h1 {
	background-color: #fff;
	padding: 20px;
	color: #00AEEF;
	text-align: left;
	font-size: 32px;
	margin: 0px;
}

h3 {
	font-size: 12px;
	background-color: #B8DCE9;
	margin: 0px;
	padding: 10px;
}

h3 a {
	float: right;
	font-weight: normal;
	display: block;
}

th {
	text-align: center;
	background-color: #00AEEF;
	color: #fff;
	padding: 4px;
	font-weight: normal;
	font-size: 12px;
}

td {
	font-size: 12px;
	padding: 3px;
	text-align: left;
}

tr {
	background: #fff
}

tr:nth-child(odd) {
	background: #f0f0f0
}

#footer {
	background-color: #B8DCE9;
	padding: 10px;
}

.pager, .pager a {
	background-color: #00AEEF;
	color: #fff;
	padding: 3px;
}

.lhead {
	background-color: #fff;
	padding: 3px;
	font-weight: bold;
	font-size: 16px;
	width: 100%;
}

.lpart {
	background-color: #f0f0f0;
	padding: 0px;
}

.lpage {
	font: normal 12px verdana;
}

.lcount {
	background-color: #00AEEF;
	color: #fff;
	padding: 2px;
	margin: 2px;
	font: bold 12px verdana;
}

a.aemphasis {
	color: #009;
	font-weight: bold;
}
</style>

</head>
<body>
	<div id="cont">
		<h1>动漫VCD 网站地图</h1>
		<h3>
			<a href="<%=basePath%>" title="动漫VCD网-海贼王动漫|海贼王漫画|海贼王在线观看">首页</a>
			最后更新: <b>${dateTime }</b>
		</h3>

		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr valign="top">
				<td class="lpart">
					<div class="lhead">
						<span class="lcount"><h3>最近更新:</h3></span>
					</div>
					<div class="lhead">
						<div class="lcount">动  漫</div>
					</div>

					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<s:iterator value="siteMapVideoList" var="videoVar">
							<tr><td class="lpage"><a href="${videoVar.url }" title="${videoVar.title } " target="_blank">${videoVar.title } </a></td></tr>
						</s:iterator>
					</table>
				</td>
			</tr>

			<tr valign="top">
				<td class="lpart" colspan="100">
					<div class="lhead">
						<div class="lcount">漫  画</div>
					</div>
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<s:iterator value="siteMapComicList" var="comicVar">
							<tr><td class="lpage"><a href="${comicVar.url }" title="${comicVar.title }" target="_blank">${comicVar.title }</a></td></tr>
						</s:iterator>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
