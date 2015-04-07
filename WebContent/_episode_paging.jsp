<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="pagenav">
	分页：页次：<b>${paging.page}/${paging.pageCount }</b>&nbsp;每页<b>${paging.queryCount }</b>&nbsp;总数<b>${paging.total }</b>&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="./video/episodeAction!list?videoId=${video.id}&paging.page=1">首页</a>&nbsp;&nbsp;<a
		href="./video/episodeAction!list?videoId=${video.id}&paging.page=${paging.page -1}">上一页</a>&nbsp;&nbsp;<a
		href="./video/episodeAction!list?videoId=${video.id}&paging.page=${paging.page +1}">下一页</a>&nbsp;&nbsp;<a
		href="./video/episodeAction!list?videoId=${video.id}&paging.page=${paging.pageCount}">尾页</a>&nbsp;&nbsp;
	&nbsp;&nbsp;转到:&nbsp;&nbsp;&nbsp;&nbsp; <select name=select
		onchange="self.location.href=this.options[this.selectedIndex].value">
		<!-- 页码 -->
		<s:bean name="org.apache.struts2.util.Counter" var="counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="paging.pageCount" />
			<s:iterator>
				<option id="${current-1 }"
					value="./video/episodeAction!list?videoId=${video.id}&paging.page=<s:property value="current-1" />">
					第<s:property value="current-1" />页
				</option>
			</s:iterator>
		</s:bean>
	</select>
	<!-- 还原选中页码 -->
	<script type="text/javascript">
		$("#${paging.page}").attr("selected", true);
	</script>

	<%-- 另一种实现方式					
 					<s:iterator>
					<option value="<s:property />"
					<s:if test="%{paging.page==(current-1)}">selected="selected"</s:if>>
					<s:property /> 
					--%>
	<!-- end -->
</div>