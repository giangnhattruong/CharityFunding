<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section class="col-12 col-lg-9 px-5">
<h1 class="dashboard-title">Users</h1>
<div class="d-flex flex-wrap justify-content-evenly my-3">
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-gift me-3"></i>
<span>121 Campaigns</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-gift me-3"></i>
<span>121 Campaigns</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-gift me-3"></i>
<span>121 Campaigns</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-gift me-3"></i>
<span>121 Campaigns</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-gift me-3"></i>
<span>121 Campaigns</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-gift me-3"></i>
<span>121 Campaigns</span>
</div>
</div>

<div class="d-flex justify-content-start pt-3">
<a class="btn btn-light me-3 mb-3 button-shadow"
	href="<c:url value="/admin/users/new" />">
	<i class="bi bi-plus-circle"></i>
	Add new user</a>
<button id="deleteBtn" class="btn btn-outline-danger me-3 mb-3 d-none button-shadow">
	<i class="bi bi-trash"></i>
	Remove selected user(s)</button>
<button id="resetPasswordsBtn" class="btn btn-outline-info mb-3 d-none button-shadow">
	<i class="bi bi-arrow-clockwise"></i>
	Reset passwords</button>
</div>

<c:choose>
<c:when test="${userList.size() == 0}">
<h3>No record found.</h3>
</c:when>
<c:otherwise>
<div class="table-responsive dashboard-table p-1">
<form method="post" id="actionForm"
	action="<c:url value="/admin/users/delete" />">
<table style="width: 1500px" 
		class="table table-borderless table-fixed table-hover table-sm">
    <thead class="bottom-shadow">
      <tr>
        <th scope="col"></th>
        <th scope="col">
	        <input class="form-check-input" type="checkbox"
				id="selectOrDeselectAll">
				<span class="ms-1">Select all</span></th>
        <th scope="col">No</th>
        <th scope="col">Email</th>
        <th scope="col">Full-name</th>
        <th scope="col">Address</th>
        <th scope="col">Phone</th>
        <th scope="col">Donations(USD)</th>
        <th scope="col">Donation times</th>
        <th scope="col">Latest donation</th>
        <th scope="col">Date joined</th>
        <th scope="col">Role</th>
        <th scope="col">Status</th>
      </tr>
    </thead>
	<tbody>
	
	<c:forEach varStatus="loopStatus" var="user" items="${userList}">
	  <tr id="item${loopStatus.index + 1}">
	    <c:if test="${user.userRole == 0}">
	    <td class="text-center"><a class="btn btn-outline-secondary"
	    	href="<c:url value="/admin/users/update/${user.userID}" />">
	    	<i class="bi bi-pencil-square"></i>Edit</a></td>
	    <td class="text-center">
	    <input class="form-check-input table-row" type="checkbox" 
	    	name="userIDs" data-info="${user.email}"
	    	value="${user.userID}">
	    </td>
	    </c:if>
	    <c:if test="${user.userRole > 0}">
	    <td class="text-center">
	    <span class="btn btn-outline-info disabled">Admin</span>
	    </td>
	    <td class="text-center">
	    <input class="form-check-input" type="checkbox" 
	    	name="userIDs" value="${user.userID}" disabled>
	    </td>
	    </c:if>
	    <td>${loopStatus.index + 1}</td>
	    <td>${user.email}</td>
	    <td>${user.fullname}</td>
	    <td>${user.address}</td>
	    <td>${user.phone}</td>
	    <td>${user.totalDonations}</td>
	    <td>${user.donationTimes}</td>
	    <td>${user.latestDonationDate}</td>
	    <td>${user.dateCreated}</td>
	    <td>${user.userRole == 1 ? "Admin" : "User"}</td>
	    <td>${user.userStatus == true ? "Active" : "In-active"}</td>
	  </tr>
	</c:forEach>
	</tbody>
</table>
</form>
</div>

<c:import url="/WEB-INF/jsp/common/pagination.jsp">
<c:param name="totalItems">${userList.size()}</c:param>
<c:param name="pageSize">6</c:param>
</c:import>
</c:otherwise>
</c:choose>

<script defer src="<c:url value="/resources/assets/js/checkTableRow.js"/>">
</script>
<script defer src="<c:url value="/resources/assets/js/pagination.js"/>">
</script>
</section>