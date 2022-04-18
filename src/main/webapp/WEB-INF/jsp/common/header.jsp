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
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
	
<!-- My custom CSS styles -->
<link rel="stylesheet" 
	href="<c:url value="/resources/assets/css/styles.css"/>">
	
<!-- Bootstrap Icon -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">

<!-- Google font Kanix font-family: 'Cinzel', 'Kanit', sans-serif -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Cinzel&family=Kanit:wght@100;200;300;500&display=swap" rel="stylesheet">

<!-- Bootstrap 5.1.3 JS -->
<script defer
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
	
<!-- jQuery 3.6 -->
<script defer src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>

<!-- My custom JS script -->
<script defer 
	src="<c:url value="/resources/assets/js/myScript.js"/>"></script>
</head>
<body class="vh-100 d-flex flex-column justify-content-between">

<c:import url="/WEB-INF/jsp/common/navbar.jsp"></c:import>

<main id="main-body">