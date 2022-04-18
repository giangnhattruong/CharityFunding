<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav id="navbar" class="navbar navbar-expand-lg navbar-light">
<div class="container-fluid d-lg-flex justify-content-lg-between">
<a id="logo" class="navbar-brand d-flex align-items-center" 
	href="<c:url value="/" />">
	<img src="<c:url value="/resources/assets/media/logo.png" />" 
		alt="Logo" height="45px">
	<h3 class="ms-1 mb-0">TBS</h3></a>
	
<button class="navbar-toggler" type="button" 
	data-bs-toggle="collapse" data-bs-target="#navbarToggler" 
	aria-controls="navbarToggler" aria-expanded="false" 
	aria-label="Toggle navigation">
<span class="navbar-toggler-icon"></span>
</button>

<div class="collapse navbar-collapse d-lg-flex justify-content-lg-between" 
	id="navbarToggler">
<div id="header-primary-items">
<ul class="navbar-nav me-auto mb-2 mb-lg-0">
  <li class="nav-item">
    <a class="nav-link header-primary-link" 
    	href="<c:url value="/explore" />">Explore</a>
  </li>
  
  <c:forEach var="navItem" items="${navItemMap.keySet()}">
  <li class="nav-item">
    <a class="nav-link header-primary-link" 
    	href="<c:url value="${navItemMap.get(navItem)}" />">${navItem}</a>
  </li>
  </c:forEach>
</ul>
</div>

<div id="header-secondary-items">
<ul class="navbar-nav me-auto mb-2 mb-lg-0">
  <c:if test="${email != null && userRole > 0 && currentPosition != 'admin pages'}">
  <li class="nav-item">
    <a class="nav-link" 
    	href="<c:url value="/admin" />">Admin dashboard</a>
  </li>
  </c:if>
  
  <c:if test="${email != null && currentPosition != 'user pages'}">
  <li class="nav-item">
    <a class="nav-link" 
    	href="<c:url value="/user" />">Profile</a>
  </li>
  </c:if>
  
  <c:if test="${email == null}">
  <li class="nav-item">
    <a class="nav-link" 
    	href="<c:url value="/register" />">Sign up</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" 
    	href="<c:url value="/login" />">Login</a>
  </li>
  </c:if>
  
  <c:if test="${email != null}">
  <li class="nav-item">
    <a class="nav-link" 
    	href="<c:url value="/logout" />">Logout</a>
  </li>
  </c:if>
</ul>
</div>
</div>
</div>
</nav>