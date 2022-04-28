<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<section class="col-12 col-lg-9 px-5">
<h1 class="dashboard-title">Users</h1>
<div class="d-flex flex-wrap justify-content-evenly my-3">
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-people me-3"></i>
<span><fmt:formatNumber value="${numberOfUsers}"
         type="number"/> Users</span>
</div>
<div class="dashboard-summary-item button-shadow">
<i class="bi bi-currency-dollar me-1"></i>
<span><fmt:formatNumber value="${donationSum}"
         type="number"/> Donations</span>
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
        <th scope="col">Email</th>
        <th scope="col">Full-name</th>
        <th scope="col">Phone</th>
        <th scope="col">Role</th>
        <th scope="col">Status</th>
      </tr>
    </thead>
	<tbody>
	
	<c:forEach varStatus="loopStatus" var="user" items="${userList}">
	  <tr id="item${loopStatus.index + 1}"
	  	 class="${user.userStatus == 3 ? 'text-muted' : ''}">
	    <c:if test="${user.userStatus == 3}">
	    <td class="text-center">
	    <span class="btn btn-outline-secondary disabled">Deleted</span>
	    </td>
	    <td class="text-center">
	    <input class="form-check-input" type="checkbox" 
	    	name="userIDs" value="${user.userID}" disabled>
	    </td>
	    </c:if>
	    
	    <c:if test="${user.userRole > 0 && user.userStatus != 3}">
	    <td class="text-center">
	    <span class="btn btn-outline-info disabled">Admin</span>
	    </td>
	    <td class="text-center">
	    <input class="form-check-input" type="checkbox" 
	    	name="userIDs" value="${user.userID}" disabled>
	    </td>
	    </c:if>
	    
	    <c:if test="${user.userRole == 0 && user.userStatus != 3}">
	    <td class="text-center"><a class="btn bg-sm-green-gradient"
	    	href="<c:url value="/admin/users/update/${user.userID}" />">
	    	<i class="bi bi-pencil-square me-1"></i>Edit</a></td>
	    <td class="text-center">
	    <input class="form-check-input table-row" type="checkbox" 
	    	name="userIDs" 
	    	data-info="${user.email}${user.userStatus != 0 ? ' (in used)' : ''}"
	    	value="${user.userID}">
	    </td>
	    </c:if>
	    
	    <td>${loopStatus.index + 1}</td>
	    <td>${user.email}</td>
	    <td>${user.fullname}</td>
	    <td>${user.phone}</td>
	    <td>${user.userRole == 1 ? "Admin" : "User"}</td>
	    
	    <c:if test="${user.userStatus == 0}">
	    <c:set var="userStatus" value="In-active" />
	    </c:if>
	    <c:if test="${user.userStatus == 1}">
	    <c:set var="userStatus" value="Active" />
	    </c:if>
	    <c:if test="${user.userStatus == 2}">
	    <c:set var="userStatus" value="Banned" />
	    </c:if>
	    <c:if test="${user.userStatus == 3}">
	    <c:set var="userStatus" value="Deleted" />
	    </c:if>
	    <td>${userStatus}</td>
	    
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