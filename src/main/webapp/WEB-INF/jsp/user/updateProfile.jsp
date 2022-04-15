<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<div class="container p-5 button-shadow">
<div class="d-flex flex-column justify-content-center align-items-center mb-3">
<h2 class="text-center mb-5">Your profile</h2>
<img width="300px"
	src="<c:url value="/resources/assets/media/kissclipart-user-profile.png" />">
</div>
<p class="small text-danger col-lg-8 offset-lg-2">${error}</p>
<form:form id="submitForm"
	cssClass="resetForm" 
	method="POST" modelAttribute="user"
	action="update-profile">

<div class="row g-md-5 mb-3">
<div class="col-md col-lg-4 offset-lg-2">
<div class="mb-3">
<form:label path="email" class="form-label lead">
	Email</form:label>
<input type="text" name="email" 
	value="${user.email}"
	class="form-control-plaintext" readonly>
</div>

<div class="mb-3">
<label class="form-label lead">Password</label>
<input type="password" name="showPassword" 
	value="forShowingOnly"
	class="form-control-plaintext" readonly/>
<a href="<c:url value="/user/update-password" />">Change password</a>
</div>

<hr class="my-4 d-md-none">
</div>

<div class="col-md col-lg-4 border-start-md">
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

<div class="mb-3">
<form:label path="address" cssClass="form-label lead">
	Address</form:label>
<form:input type="text" cssClass="form-control" 
	id="address" path="address"
	placeholder="Enter your address"/>
</div>
</div>
</div>

<div class="d-flex justify-content-center">
<input id="submitButton" class="btn btn-primary px-5 me-3" 
	type="submit" value="Update">
<span id="resetSearchButton" class="btn btn-outline-secondary me-3 px-5">Reset</span>
</div>
</form:form>
</div>

<c:if test="${message != null && message != ''}">
<c:import url="/WEB-INF/jsp/common/notifyModal.jsp"></c:import>
</c:if>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>