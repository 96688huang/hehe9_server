<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="<%=basePath%>/favicon.ico" mce_href="<%=basePath%>/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" mce_href="<%=basePath%>/favicon.ico" type="image/x-icon">
<link rel="Bookmark" href="<%=basePath%>/favicon.ico" mce_href="<%=basePath%>/favicon.ico" type="image/x-icon">

<link rel="stylesheet" href="./css/dmvcd_global.css" type="text/css">
<link rel="stylesheet" href="./css/dmvcd.css" type="text/css">
<link rel="stylesheet" href="./css/common.css" type="text/css">
<link rel="stylesheet" href="./css/search.css" type="text/css">
<link rel="stylesheet" href="./css/menu2.css" type="text/css">

<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/common.js"></script>
<script type="text/javascript" src="./js/play.js"></script>
<script type="text/javascript" src="./js/menu2.js"></script>
