<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid rounded box-inset-shadow">
<c:choose>
<c:when test="${historyList.size() == 0}">
<h3>No record found.</h3>
</c:when>
<c:otherwise>
<div class="table-responsive">
<table style="width: 1200px" 
		class="table table-borderless table-fixed table-hover table-sm">
    <thead class="bottom-shadow">
      <tr>
        <th scope="col">No</th>
        <th scope="col">Donated to campaign</th>
        <th scope="col">Campaign location</th>
        <th scope="col">Donation(USD)</th>
        <th scope="col">Donation date</th>
        <th scope="col">Transaction code</th>
        <th scope="col">Status</th>
      </tr>
    </thead>
	<tbody>
	<c:forEach varStatus="loopStatus" var="history" items="${historyList}">
	  <tr id="item${loopStatus.index + 1}">
	    <td>${loopStatus.index + 1}</td>
	    <td>${history.title}</td>
	    <td>${history.location}</td>
	    <td>${history.donation}</td>
	    <td>${history.donationDate}</td>
	    <td>${history.transactionCode}</td>
	    <td>${history.donationStatus == true ? "Verified" : "Not Verified"}</td>
	  </tr>
	</c:forEach>
	</tbody>
</table>
</div>

<c:import url="/WEB-INF/jsp/common/pagination.jsp">
<c:param name="totalItems">${historyList.size()}</c:param>
<c:param name="pageSize">6</c:param>
</c:import>
</c:otherwise>
</c:choose>

<script defer src="<c:url value="/resources/assets/js/pagination.js"/>">
</script>
</div>