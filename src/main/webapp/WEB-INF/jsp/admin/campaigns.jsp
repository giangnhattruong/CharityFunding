<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="navItemMap">${navItemMap}</c:param>
</c:import>

<h1 class="display-5 text-center">Campaigns</h1>

<c:import url="/WEB-INF/jsp/common/searchCampaigns.jsp">
	<c:param name="action">campaigns</c:param>
</c:import>


<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>