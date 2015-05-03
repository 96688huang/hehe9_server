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
<title>404 页面不存在</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="common_head.jsp" />

<style type="text/css">
body {
	TEXT-ALIGN: center;
}

#notfound {
	MARGIN-RIGHT: auto;
	MARGIN-LEFT: auto;
	background: #FFF;
	vertical-align: middle;
	text-align: left;
	display: inline-block;
	MARGIN-RIGHT: auto;
}
</style>
</head>
<body>
	<div class="nmain" style="height: 580px;">
		<jsp:include page="head.jsp" />
		<div id="notfound">
			<div style="float: left;">
				<img alt="没有找到您访问的页面" src="./img/mine/404_img.jpg">
			</div>
			<div
				style="margin-top: 180px; margin-left: 20px; line-height: 30px; display: inline-block;">
				<h1>
					<font color="#FFCC22">很抱歉</font>, 没有找到您访问的页面
				</h1>
				<h3>有可能我们的攻城狮和程序猿正在升级或维护系统</h3>
				<div>&nbsp;</div>
				<hr>
				<div>&nbsp;</div>
				<h3>
					<strong>您可以尝试一下:</strong>
				</h3>

				<ul>
					<li><h3>
							<strong> <a href="javascript:history.go(-1);">&lt;&lt;
									返回 上一页</a>
							</strong>
						</h3></li>
					<li><h3>
							<strong><a href="./">&lt;&lt; 返回 首页</a> </strong>
						</h3></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>
