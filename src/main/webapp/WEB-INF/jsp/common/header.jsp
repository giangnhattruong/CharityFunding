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
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
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

<c:import url="/WEB-INF/jsp/common/navbar.jsp"></c:import>

<main id="main-body">