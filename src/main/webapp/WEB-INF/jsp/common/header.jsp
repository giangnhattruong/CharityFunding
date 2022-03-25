<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" 
	content="A place where kind-hearted people can gather and give away.">
<link rel="icon"
	href="<c:url value="/resources/assets/media/logo.png" />">
<title>Charity Campaigns Around The World</title>

<!-- Bootstrap 5.1.3 CSS -->
<link 
	href="<c:url value="/resources/assets/css/bootstrap.min.css"/>" 
	rel="stylesheet">
	
<!-- My custom CSS styles -->
<link rel="stylesheet" 
	href="<c:url value="/resources/assets/css/styles.css"/>">
	
<!-- Bootstrap Icon -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">

<!-- Bootstrap 5.1.3 JS -->
<script defer 
	src="<c:url value="/resources/assets/js/boostrap.min.js"/>"></script>
	
<!-- jQuery 3.6 -->
<script defer 
	src="<c:url value="/resources/assets/js/jquery-3.6.0.min.js"/>"></script>

<!-- My custom JS script -->
<script defer 
	src="<c:url value="/resources/assets/js/myScript.js"/>"></script>
</head>
<body class="vh-100 d-flex flex-column justify-content-between">

<nav id="navbar" class="container-fluid">
<header
	class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 px-5 mb-4 border-bottom">
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
<a href="<c:url value="/login" />" class="btn btn-outline-success me-2">Login</a>
<a href="<c:url value="/register" />" class="btn btn-success">Sign-up</a>
</div>
</header>
</nav>

<main id="main-body" class="p-5">