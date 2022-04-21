<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- Google oauth -->
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id" content="${myClientID}">

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<div class="row g-0">
<div class="order-2 order-md-1 col-12 col-md-7 p-5 d-flex flex-column align-items-center">
<div class="text-center">
<img src="<c:url value="/resources/assets/media/logo.png" />" 
	alt="Logo" height="45px">
<h4 class="mt-1 mb-5 pb-1">TBS Charity Team</h4>
</div>

<form:form method="POST" id="loginForm"
	modelAttribute="user"
	action="login">
<input type="hidden" name="previousURL"/>
<p class="h5 mb-5 text-center">Please login to your account</p>
<p class="small text-danger">${message}</p>

<!-- Google sign in -->
<div class="g-signin2 mb-3" data-onsuccess="onSignIn"></div>

<div class="mb-4" 
	style="width: 24em">
<form:label cssClass="form-label" 
	path="email">Email</form:label>
<form:input path="email" cssClass="form-control" 
	placeholder="Email address"/>
</div>

<div class="mb-4" 
	style="width: 24em">
<form:label cssClass="form-label" 
	path="password">Password</form:label>
<form:password path="password" cssClass="form-control"
	placeholder="Password"/>
</div>

<div class="text-center d-flex justify-content-center align-items-center mb-3">
<button id="loginButton" 
	class="btn bg-sm-green-gradient me-3">Log in</button>
<a class="text-muted" 
	href="<c:url value="/login/forgot-password" />">Forgot password?</a>
</div>

<div class="d-flex align-items-center justify-content-center pb-4">
<p class="mb-0 me-2">You don't have an account?</p>
<a class="btn btn-outline-success"
	href="<c:url value="/register" />">Create new</a>
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