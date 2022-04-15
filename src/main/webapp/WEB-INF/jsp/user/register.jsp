<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="action" value="/register" />

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<div class="row g-0">
<div class="order-2 order-md-1 col-12 col-md-7 p-5 d-flex flex-column align-items-center">
<div class="text-center">
<img src="<c:url value="/resources/assets/media/logo.png" />" 
	alt="Logo" height="45px">
<h4 class="mt-1 mb-5 pb-1">TBS Charity Team</h4>
</div>

<form:form method="POST" modelAttribute="user"
	action="${action}">
<p class="h5 mb-5 text-center">Register your new account</p>

<p class="small text-danger offset-lg-1">${message}</p>
<div class="mb-4" 
	style="width: 24em">
<form:label cssClass="form-label" path="email">Email</form:label>
<span class="text-danger">*</span>
<form:input type="text" path="email"
	cssClass="form-control" placeholder="Email address"/>
</div>

<div class="mb-4" 
	style="width: 24em">
<form:label cssClass="form-label" path="fullname">Fullname</form:label>
<span class="text-danger">*</span>
<form:input type="text" path="fullname"
	cssClass="form-control" placeholder="Full-name"/>
</div>

<div class="mb-4" 
	style="width: 24em">
<form:label cssClass="form-label" path="phone">Phone</form:label>
<span class="text-danger">*</span>
<form:input type="text" path="phone"
	cssClass="form-control" placeholder="Phone number"/>
</div>

<div class="mb-4" 
	style="width: 24em">
<form:label cssClass="form-label" path="address">Address</form:label>
<form:input type="text" path="address"
	cssClass="form-control" placeholder="Your physical address"/>
</div>

<div class="text-center d-flex justify-content-center align-items-center mb-3">
<button class="btn bg-sm-green-gradient me-3">Log in</button>
<a class="text-muted" href="#!">Forgot password?</a>
</div>

<div class="d-flex align-items-center justify-content-center pb-4">
<p class="mb-0 me-2">Don't have an account?</p>
<button type="button" class="btn btn-outline-success">Create new</button>
</div>
</form:form>
</div>
              
<div class="order-1 order-md-2 col-12 col-md-5 d-flex align-items-center bg-green-gradient">
<div class="text-white px-3 py-4 p-md-5">
<h4 class="mb-4">Welcome to TBS non-profit organization!</h4>
<p class="small mb-0">We are an international charity organization founded in early 2021. 
Our mission is to support people around the world who suffered from natural disasters, war, 
famine, diseases,... especially orphans. With your help, we can spread the hope to many!</p>
</div>
</div>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>