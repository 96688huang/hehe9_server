<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>to main</title>
<meta http-equiv="refresh" content="0;url=./video/videoAction!toMain">
<!-- <meta http-equiv="refresh" content="1;url=/hehe9_ssm/video/videoAction!list"> -->
</head>
<body>
<!-- 
<div align="center">
<img alt="loading" src="./img/Spinning airplane.gif">
</div> 
-->
</body>
</html>