<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<c:url var="action" value="${formAction}" />

<div class="py-5 text-center">
<h2>${formTitle} user</h2>
<p class="lead">Please fill all the required informations below to 
	${fn:toLowerCase(formTitle)} a user.</p>
</div>

<div class="container mb-5">
<p class="small text-danger offset-lg-1">${message}</p>
<form:form id="submitForm"
	cssClass="resetForm" 
	method="POST" modelAttribute="user"
	action="${action}" enctype="multipart/form-data">

<c:if test="${formTitle == 'Create'}">
<input type="reset" value="&#8634; CLEAR"
	class="btn btn-outline-secondary resetFormButton my-3 offset-lg-1">
</c:if>

<div class="row g-md-5 mb-3">
<div class="col-md col-lg-5 offset-lg-1">

<div class="mb-3">
<form:label path="email" cssClass="form-label lead">
	Email</form:label>
<c:if test="${formTitle == 'Create'}">
<span class="text-danger">*</span>
<form:input type="text" id="email" path="email" 
	placeholder="Enter your email address"
	cssClass="form-control"/>
</c:if>

<c:if test="${formTitle == 'Update'}">
<input type="text" id="email" name="email" 
	value="${user.email}"
	class="form-control-plaintext" readonly/>
</c:if>
</div>

<div class="mb-3">
<form:label path="fullname" cssClass="form-label lead">
	Full-name</form:label>
<span class="text-danger">*</span>
<form:input type="text" cssClass="form-control" 
	id="fullname" path="fullname"
	placeholder="Enter your fullname"/>
</div>

<div class="mb-3">
<form:label path="phone" cssClass="form-label lead">
	Phone number</form:label>
<span class="text-danger">*</span>
<form:input type="text" cssClass="form-control" 
	id="phone" path="phone"
	placeholder="Enter your phone number"/>
</div>

<%-- <div class="mb-3">
<form:label path="password" cssClass="form-label lead">
	Password</form:label>
<span class="text-danger">*</span>
<form:password cssClass="form-control" 
	showPassword="${formTitle == 'Update' ? 'true' : 'false'}"
	id="password" path="password" 
	placeholder="Enter your password"/>
</div>

<div class="mb-3">
<form:label path="confirmPassword" cssClass="form-label lead">
	Confirm password</form:label>
<span class="text-danger">*</span>
<form:password cssClass="form-control" 
	showPassword="${formTitle == 'Update' ? 'true' : 'false'}"
	id="confirmPassword" path="confirmPassword"
	placeholder="Enter your password"/>
</div>

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
</div> --%>

<hr class="my-4 d-md-none">
</div>

<div class="col-md col-lg-5 border-start-md">
<%-- <c:if test="${formTitle == 'Create'}">
<div class="mb-3">
<label class="form-label lead">
	Password</label>
<input type="password" class="form-control" 
	name="showOnly" value="${user.password}" readonly>
</div>
</c:if> --%>

<div class="mb-3">
<form:label path="address" cssClass="form-label lead">
	Address</form:label>
<form:input type="text" cssClass="form-control" 
	id="address" path="address"
	placeholder="Enter your address"/>
</div>

<hr class="my-4">

<div class="mb-3">
<form:label path="userRole" cssClass="form-label small">
	User role</form:label>
<form:select class="form-select form-select-sm ms-auto" 
		path="userRole" title="User role">
	<form:option value="0">User</form:option>
	<form:option value="1">Admin</form:option>
</form:select>
</div>

<div class="form-check form-switch">
<form:checkbox path="userStatus" cssClass="form-check-input"
	id="userStatus"/>
 <form:label cssClass="form-check-label small" path="userStatus">
 User status</form:label>
</div>
</div>
</div>

<div class="d-flex justify-content-center">
<a id="returnBackButton" class="btn btn-secondary btn-lg px-5 me-3" 
href="<c:url value="/admin/users" />">Return</a>
<input id="submitButton" class="btn btn-primary btn-lg px-5 me-3" 
	type="submit" value="Submit">
</div>
</form:form>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>