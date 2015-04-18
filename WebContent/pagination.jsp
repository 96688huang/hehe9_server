<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pagenav">
	&nbsp;&nbsp;页码：<b>${pagination.page}/${pagination.pageCount }</b>&nbsp;每页&nbsp;<b>${pagination.queryCount }</b>&nbsp;总数&nbsp;<b>${pagination.total }</b>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="javascript:getListByPage('1');">首页</a>&nbsp;&nbsp;
	
	<c:if test="${pagination.page - 1} > 0">
		<a href="javascript:getListByPage('${pagination.page - 1}')">上一页</a>&nbsp;&nbsp;
	</c:if>
	<a href="javascript:getListByPage('${pagination.page + 1}')">下一页</a>&nbsp;&nbsp;<a
		href="javascript:getListByPage('${pagination.pageCount}')">尾页</a>&nbsp;&nbsp;
	&nbsp;&nbsp;转到:&nbsp;&nbsp;&nbsp;&nbsp; <select id="selectPage"
		name=select
		onchange="self.location.href=this.options[this.selectedIndex].value">
		<%-- <select id="select_page_id" name=select
		onchange="javascript:getListByPage();"> --%>
		<!-- 页码 -->
		<s:bean name="org.apache.struts2.util.Counter" var="counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="pagination.pageCount" />
			<s:iterator>
				<option id="${current-1 }"
					value="javascript:getListByPage('<s:property value="current-1" />');">
					第
					<s:property value="current-1" />&nbsp;页
				</option>
			</s:iterator>
		</s:bean>
	</select>
	<%-- 另一种实现方式					
 					<s:iterator>
					<option value="<s:property />"
					<s:if test="%{pagination.page==(current-1)}">selected="selected"</s:if>>
					<s:property /> 
					--%>
	<!-- end -->
</div>

<script type="text/javascript">
	$(function() {
		//还原选中页码 
		$("#${pagination.page}").attr("selected", true);
	});

	//$("#select_page_id option:checked").attr("selected", "");
	$(function() {
		$("#queryForm").append(
				"<input type='hidden' name='pagination.page' id='page_id' value='1'/>");
	});

	function getListByPage(page) {
		$("#page_id").attr("value", page);
		$("#queryForm").submit();
	}
</script>
