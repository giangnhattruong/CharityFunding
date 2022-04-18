<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en" class="h-100">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description"
	content="A place where kind-hearted people can gather and give away.">
<link rel="icon" href="<c:url value="/resources/assets/media/logo.png" />">
<title>Charity Campaigns Around The World</title>

<!-- Bootstrap 5.1.3 CSS -->
<link href="<c:url value="/resources/assets/css/bootstrap.min.css" />" rel="stylesheet">

<!-- Custom styles -->
<link href="<c:url value="/resources/assets/css/cover.css" />" rel="stylesheet">
</head>
<body class="d-flex h-100 text-center text-white">

<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
<header class="mb-auto">
<div>
<h3 class="float-md-start mb-0">
<img src="<c:url value="/resources/assets/media/logo.png" />" 
	alt="Logo" height="45px"></h3>
<nav class="nav nav-masthead justify-content-center float-md-end">
<a class="nav-link" href="<c:url value="/explore" />">Explore</a>

<c:if test="${email != null && userRole > 0 && currentPosition != 'admin pages'}">
<a class="nav-link" 
	href="<c:url value="/admin" />">Admin dashboard</a>
</c:if>

<c:if test="${email != null && currentPosition != 'user pages'}">
<a class="nav-link" 
	href="<c:url value="/user" />">Profile</a>
</c:if>

<c:if test="${email == null}">
<a class="nav-link" 
	href="<c:url value="/register" />">Sign up</a>
<a class="nav-link" 
	href="<c:url value="/login" />">Login</a>
</c:if>

<c:if test="${email != null}">
<a class="nav-link" 
	href="<c:url value="/logout" />">Logout</a>
</c:if>
</nav>
</div>
</header>

<main class="px-3">
<h1>Help To Give Hope</h1>
<figure class="text-center">
  <blockquote class="blockquote">
    <p>“We know only too well that what we are doing is nothing more than a drop in the ocean. But if the drop were not there, the ocean would be missing something."</p>
  </blockquote>
  <figcaption class="blockquote-footer">
    <cite title="Source Title">Mother Teresa</cite>
</figcaption>
</figure>
<p class="lead">
<a href="<c:url value="/explore" />"
			class="btn btn-lg btn-secondary fw-bold border-white bg-white">
			Learn more</a>
</p>
</main>

<footer class="mt-auto text-white-50">
<p>&#174;Copyright 2022</p>
</footer>
</div>

</body>
</html>
