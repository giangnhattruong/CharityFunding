<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Calculate page values for pagination -->
<fmt:formatNumber var="totalItems" 
	value="${campaignList.size()}" type="number" pattern="#" />
<fmt:formatNumber var="pageSize" 
	value="${6}" type="number" pattern="#" />

<!-- Add 0.4999 for ceiling the result -->
<fmt:formatNumber var="totalPages" 
	value="${totalItems / pageSize + 0.4999}" 
	type="number" pattern="#" />

<!-- Get current page number -->
<c:choose>
<c:when test="${param.page == null || param.page <= 0}">
<fmt:formatNumber var="pageNumber" 
	value="1" type="number" pattern="#" />
</c:when>
<c:when test="${param.page >= totalPages}">
<fmt:formatNumber var="pageNumber" 
	value="${totalPages}" type="number" pattern="#" />
</c:when>
<c:otherwise>
<fmt:formatNumber var="pageNumber" 
	value="${param.page}" type="number" pattern="#" />
</c:otherwise>
</c:choose>

<!-- Get index range of items -->
<fmt:formatNumber var="startIndex" 
	value="${(pageNumber - 1) * pageSize}" 
	type="number" pattern="#" />
<fmt:formatNumber var="endIndex" 
	value="${pageNumber * pageSize - 1}" 
	type="number" pattern="#" />

<!-- Table content -->
<div class="d-flex justify-content-start pt-3">
<a class="btn btn-success me-3 mb-3"
	href="<c:url value="/admin/campaigns/new" />">
	<i class="bi bi-plus-circle"></i>
	Add new campaign</a>
<button id="deleteBtn" class="btn btn-danger mb-3 d-none">
	<i class="bi bi-trash"></i>
	Remove selected campaign(s)</button>
</div>

<c:choose>
<c:when test="${campaignList.size() == 0}">
<h3>No record found.</h3>
</c:when>
<c:otherwise>
<div class="table-responsive">
<form method="post" id="deleteForm"
	action="<c:url value="/admin/campaigns/delete" />">
<table style="width: 1500px" class="table table-fixed table-hover table-sm">
    <thead>
      <tr>
        <th scope="col"></th>
        <th scope="col">Selected</th>
        <th scope="col">No</th>
        <th scope="col">Title</th>
        <th scope="col">Location</th>
        <th scope="col">Start</th>
        <th scope="col">End</th>
        <th scope="col">Target(USD)</th>
        <th scope="col">Donations(USD)</th>
        <th scope="col">Supporters</th>
        <th scope="col">Lastest donation</th>
        <th scope="col">Status</th>
      </tr>
    </thead>
	<tbody>
	
	<c:forEach varStatus="loopStatus" var="campaign" items="${campaignList}">
	<c:if test="${loopStatus.index >= startIndex &&
					loopStatus.index <= endIndex}">
	  <tr>
	    <td class="text-center">
	    <a  class="btn btn-outline-primary"
	    	href="<c:url value="/admin/campaigns/update?id=${campaign.campaignID}" />">
	    	<i class="bi bi-pencil-square"></i>
	    	Edit</a>
	    </td>
	    <td class="text-center">
	    <input class="form-check-input" type="checkbox" 
	    	name="campaignIDs" value="${campaign.campaignID}">
	    </td>
	    <td>${loopStatus.index + 1}</td>
	    <td>${campaign.title}</td>
	    <td>${campaign.location}</td>
	    <td>${campaign.startDate}</td>
	    <td>${campaign.endDate}</td>
	    <td>${campaign.targetAmount}</td>
	    <td>${campaign.totalDonations}</td>
	    <td>${campaign.totalSupporters}</td>
	    <td>${campaign.dateCreated}</td>
	    <td>${campaign.campaignStatus == 1 ? "Open" : "Closed"}</td>
	  </tr>
	</c:if>
	</c:forEach>
	</tbody>
</table>
</form>
</div>

<c:import url="/WEB-INF/jsp/common/pagination.jsp">
<c:param name="pageNumber">${pageNumber}</c:param>
<c:param name="totalPages">${totalPages}</c:param>
</c:import>
</c:otherwise>
</c:choose>
