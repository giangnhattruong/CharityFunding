<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<table style="width: 1500px" 
		class="table table-fixed table-hover table-sm">
    <thead>
      <tr>
        <th scope="col"></th>
        <th scope="col">
	        <input class="form-check-input" type="checkbox"
				id="selectOrDeselectAll">
				<span class="ms-1">Select all</span></th>
        <th scope="col">No</th>
        <th scope="col">Title</th>
        <th scope="col">Location</th>
        <th scope="col">Start</th>
        <th scope="col">End</th>
        <th scope="col">Target(USD)</th>
        <th scope="col">Donations(USD)</th>
        <th scope="col">Supporters</th>
        <th scope="col">Latest donation</th>
        <th scope="col">Status</th>
      </tr>
    </thead>
	<tbody>
	
	<c:forEach varStatus="loopStatus" 
		var="campaign" items="${campaignList}">
	  <tr id="row-${loopStatus.index + 1}">
	    <td class="text-center"><a class="btn btn-outline-primary"
	    	href="<c:url value="/admin/campaigns/update/${campaign.campaignID}" />">
	    	<i class="bi bi-pencil-square"></i>Edit</a></td>
	    <td class="text-center">
	    <input class="form-check-input table-row" type="checkbox" 
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
	    <td>${campaign.latestDonationDate}</td>
	    <td>${campaign.campaignStatus == true ? "Open" : "Closed"}</td>
	  </tr>
	</c:forEach>
	</tbody>
</table>
</form>
</div>

<c:import url="/WEB-INF/jsp/common/pagination.jsp">
<c:param name="totalItems">${campaignList.size()}</c:param>
<c:param name="pageSize">6</c:param>
</c:import>
</c:otherwise>
</c:choose>

<script defer src="<c:url value="/resources/assets/js/checkTableRow.js"/>">
</script>
<script defer src="<c:url value="/resources/assets/js/pagination.js"/>">
</script>