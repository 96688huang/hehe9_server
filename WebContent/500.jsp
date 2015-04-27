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
<title>500 页面无法访问</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./css/pick.css" type="text/css">
<link rel="stylesheet" href="./css/pick_global.css" type="text/css">

<link rel="stylesheet" href="./css/common.css" type="text/css">

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
}
</style>

<script type="text/javascript">
	function countDown(secs, surl) {
		var jumpTo = document.getElementById('jumpTo');
		jumpTo.innerHTML = secs;
		if (--secs > 0) {
			setTimeout("countDown(" + secs + ",'" + surl + "')", 1000);
		} else {
			location.href = surl;
		}
	}
</script>
</head>
<body>
	<div class="nmain" style="height: 700px;">
		<jsp:include page="head.jsp" />
		<div id="notfound">
			<div>
				<img alt="没有找到您访问的页面" src="./img/mine/500_img.jpg">
			</div>
			<br />
			<div style="line-height: 30px;">
				<h1>
					<font color="#FFCC22">很抱歉</font>, 页面访问不了 &nbsp;&nbsp;&nbsp;&nbsp; <font
						color="blue"><span id="jumpTo">10</span></font> 秒后自动跳转到 <a
						href="www.dmVCD.com"><font color="#F00;">动漫VCD</font>
						www.dmVCD.com</a>
					<script type="text/javascript">
						countDown(10, 'http://www.dmVCD.com/');
					</script>
				</h1>
			</div>
		</div>
	</div>
	<!-- 页脚 -->
	<jsp:include page="footer.jsp" />
</body>
</html>
