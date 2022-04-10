<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<div class="container p-5 button-shadow">
<h2 class="text-center mb-5">Update password</h2>
<p class="small text-danger col-lg-6 offset-lg-3">${error}</p>
<form id="submitForm" class="resetForm" 
	method="POST" action="update-password">

<div class="row mb-3">
<div class="col-md col-lg-4 offset-lg-4">
<div class="mb-3">
<label for="oldPassword" class="form-label lead">Old Password</label>
<input type="password" id="oldPassword" name="oldPassword" class="form-control">
</div>

<hr class="my-4">
<div class="mb-3">
<p class="small text-muted">Password must contain all below requirements:</p>
<ul>
	<li><span class="small text-muted">
		From 6-12 characters.</span></li>
	<li><span class="small text-muted">
		A minimum of 1 upper-case letter (A-Z).</span></li>
	<li><span class="small text-muted">
		A minimum of 1 lower-case letter (a-z).</span></li>
	<li><span class="small text-muted">
		A minimum of 1 special characters (@#$!%*?&).</span></li>
	<li><span class="small text-muted">
		A minimum of 1 digit (0-9).</span></li>
</ul>
</div>

<div class="mb-3">
<label for="newPassword" class="form-label lead">New password</label>
<input type="password" id="newPassword" name="newPassword" class="form-control">
</div>

<div class="mb-3">
<label for="confirmPassword" class="form-label lead">Confirm password</label>
<input type="password" id="confirmPassword" name="confirmPassword" class="form-control">
</div>
</div>
</div>

<div class="d-flex justify-content-center">
<input id="submitButton" class="btn btn-primary px-5 me-3" 
	type="submit" value="Update">
<a class="btn btn-secondary px-5"
	href="<c:url value="/user/update-profile" />">Return</a>
</div>
</form>
</div>

<c:if test="${message != null && message != ''}">
<c:import url="/WEB-INF/jsp/common/notifyModal.jsp"></c:import>
</c:if>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>