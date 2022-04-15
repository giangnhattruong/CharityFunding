<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav id="navbar" class="container-fluid">
<header
	class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-between py-3 px-5 mb-4 border-bottom">
<a href="<c:url value="/" />"
	class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
	<img src="<c:url value="/resources/assets/media/logo.png" />"
		alt="Logo" height="45px">
	<h3 class="display-5 text-success">TBS</h3>
</a>

<ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
	<li><a href="<c:url value="/explore" />" 
			class="nav-link px-2 link-secondary h5">Explore</a></li>
	<c:forEach var="navItem" items="${navItemMap.keySet()}">
		<li><a href="<c:url value="${navItemMap.get(navItem)}" />" 
			class="nav-link px-2 link-secondary h5">${navItem}</a></li>
	</c:forEach>
</ul>

<div class="col-md-3 text-end">
<c:if test="${email != null}">
<a href="<c:url value="/logout" />" 
	class="btn btn-outline-danger me-2 mb-3 mb-lg-0">Logout</a>
</c:if>

<c:if test="${email == null}">
<a href="<c:url value="/login" />" 
	class="btn btn-outline-success me-2 mb-3 mb-lg-0">Login</a>
<a href="<c:url value="/register" />" 
	class="btn btn-success mb-3 mb-lg-0">Sign-up</a>
</c:if>

<c:if test="${email != null && userRole > 0}">
<a href="<c:url value="/admin" />" 
	class="btn btn-secondary mb-3 mb-lg-0">Manage</a>
</c:if>

<c:if test="${email != null}">
<a href="<c:url value="/user" />" 
	class="btn btn-outline-secondary mb-3 mb-lg-0">Profile</a>
</c:if>
</div>
</header>
</nav>