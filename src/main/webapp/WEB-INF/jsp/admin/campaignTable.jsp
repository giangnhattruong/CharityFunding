<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<section class="col-12 col-lg-9 px-5">
<h1 class="dashboard-title">Campaigns</h1>
<div class="d-flex flex-wrap justify-content-evenly my-3">
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-pin-map me-3"></i>
<span><fmt:formatNumber value="${numberOfCampaigns}"
         type="number"/> Campaigns</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-currency-dollar me-1"></i>
<span><fmt:formatNumber value="${donationSum}"
         type="number"/> Donations</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-people me-3"></i>
<span><fmt:formatNumber value="${supporterSum}"
         type="number"/> Supporters</span>
</div>
</div>

<c:choose>
<c:when test="${campaignList.size() == 0}">
<h3>No record found.</h3>
</c:when>
<c:otherwise>
<div class="d-flex justify-content-start pt-3">
<a class="btn btn-light me-3 mb-3 button-shadow"
	href="<c:url value="/admin/campaigns/new" />">
	<i class="bi bi-plus-circle"></i>
	Add new campaign</a>
<button id="deleteBtn" class="btn btn-outline-danger mb-3 d-none button-shadow">
	<i class="bi bi-trash"></i>
	Remove selected campaign(s)</button>
</div>

<div class="table-responsive dashboard-table p-1">
<form method="post" id="actionForm"
	action="<c:url value="/admin/campaigns/delete" />">
<table style="min-width: 778px" 
		class="table table-borderless table-fixed table-hover table-sm">
    <thead class="bottom-shadow">
      <tr>
        <th scope="col"></th>
        <th scope="col">
	        <input class="form-check-input" type="checkbox"
				id="selectOrDeselectAll">
				<span class="ms-1">All</span></th>
        <th scope="col">No</th>
        <th scope="col">Title</th>
        <th scope="col">Location</th>
        <!-- <th scope="col">Start</th> -->
        <th scope="col">End</th>
        <th scope="col">Target(USD)</th>
       <!--  <th scope="col">Donations(USD)</th>
        <th scope="col">Supporters</th>
        <th scope="col">Latest donation</th> -->
        <th scope="col">Status</th>
      </tr>
    </thead>
	<tbody>
	<c:forEach varStatus="loopStatus" 
		var="campaign" items="${campaignList}">
	  <tr id="item${loopStatus.index + 1}"
	  	class="${campaign.campaignStatus == 2 ? 'text-muted' : ''}">
	  
	  	<c:if test="${campaign.campaignStatus == 2}">
	    <td class="text-center">
		<span class="btn btn-outline-secondary disabled">Deleted</span>
	    <td class="text-center">
	    <input class="form-check-input table-row" type="checkbox" 
	    	name="campaignIDs" value="${campaign.campaignID}" disabled>
	    </td>
	    </c:if>
	  
	  	<c:if test="${campaign.campaignStatus != 2 }">
	    <td class="text-center"><a class="btn bg-sm-green-gradient"
	    	href="<c:url value="/admin/campaigns/update/${campaign.campaignID}" />">
	    	<i class="bi bi-pencil-square me-1"></i>Edit</a></td>
	    <td class="text-center">
	    <input class="form-check-input table-row" type="checkbox" 
	    	name="campaignIDs" data-info="${campaign.title}"
	    	value="${campaign.campaignID}">
	    </td>
	    </c:if>
	    
	    <td>${loopStatus.index + 1}</td>
	    <td>${campaign.title}</td>
	    <td>${campaign.location}</td>
	    <%-- <td>${campaign.startDate}</td> --%>
	    <td>${campaign.endDate}</td>
	    <td><fmt:formatNumber value="${campaign.targetAmount}"
         	type="number"/></td>
	  <%--   <td><fmt:formatNumber value="${campaign.totalDonations}"
         	type="number"/></td>
	    <td><fmt:formatNumber value="${campaign.totalSupporters}"
         	type="number"/></td>
	    <td>${campaign.latestDonationDate}</td> --%>
	    
	    <c:if test="${campaign.campaignStatus == 0}">
	    <c:set var="campaignStatus" value="Closed" />
	    </c:if>
	    <c:if test="${campaign.campaignStatus == 1}">
	    <c:set var="campaignStatus" value="Opening" />
	    </c:if>
	    <c:if test="${campaign.campaignStatus == 2}">
	    <c:set var="campaignStatus" value="Deleted" />
	    </c:if>
	    <td>${campaignStatus}</td>
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
</section>