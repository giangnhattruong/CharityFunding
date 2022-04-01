<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<h1 class="display-5 text-center">Campaigns</h1>

<c:import url="/WEB-INF/jsp/common/searchCampaigns.jsp">
	<c:param name="action">campaigns</c:param>
</c:import>

<c:import url="/WEB-INF/jsp/admin/campaignTable.jsp"></c:import>

<c:import url="/WEB-INF/jsp/common/confirmDeleteModal.jsp"></c:import>

<c:if test="${message != null && message != ''}">
<c:import url="/WEB-INF/jsp/common/notifyModal.jsp"></c:import>
</c:if>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>