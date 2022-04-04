<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<h1 class="display-5 text-center">Users</h1>

<c:import url="/WEB-INF/jsp/admin/searchUsers.jsp"></c:import>

<c:import url="/WEB-INF/jsp/admin/userTable.jsp"></c:import>

<c:import url="/WEB-INF/jsp/common/confirmDeleteModal.jsp"></c:import>

<c:if test="${message != null && message != ''}">
<c:import url="/WEB-INF/jsp/common/notifyModal.jsp"></c:import>
</c:if>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>