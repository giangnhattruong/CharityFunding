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
<form:form id="submitForm" cssClass="resetForm" 
	method="POST" modelAttribute="user"
	action="${action}">

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

<hr class="my-4 d-md-none">
</div>

<div class="col-md col-lg-5 border-start-md">
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

<c:if test="${formTitle == 'Update' && user.userStatus != 0}">
<div class="form-check form-switch">
<input type="checkbox" name="activate" class="form-check-input"
	id="activate" value="1"
	${user.userStatus == 1 ? "checked" : ""}/>
 <label class="form-check-label small" for="activate">
 Activate</label>
</div>
</c:if>

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