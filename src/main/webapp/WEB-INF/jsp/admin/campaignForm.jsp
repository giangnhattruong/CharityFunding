<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="startDate" value="${campaign.startDate.toString()}"></c:set>
<c:set var="endDate" value="${campaign.endDate.toString()}"></c:set>

<div class="py-5 text-center">
<h2>${formTitle} campaign</h2>
<p class="lead">Please fill all the informations below to 
	${fn:toLowerCase(formTitle)} a campaign.</p>
</div>

<c:url var="action" value="${formAction}" scope="page" />

<div class="container">
<p class="small text-danger offset-1">${message}</p>
<form:form id="submitForm"
	cssClass="needs-validation" 
	method="POST" modelAttribute="campaign"
	action="${action}" enctype="multipart/form-data">
<div class="row g-md-5 mb-3">
<div class="col-md-5 offset-md-1 col-lg-6">
<div class="mb-3">
<form:label path="title" cssClass="form-label">Title</form:label>
<span class="text-danger">*</span>
<form:input type="text" cssClass="form-control" 
	id="title" path="title"
	placeholder="Enter campaign title (below 60 characters)"/>
</div>

<div class="row g-3 mb-3">
<div class="col">
<form:label path="location" cssClass="form-label">Location</form:label>
<span class="text-danger">*</span>
<form:input type="text" cssClass="form-control" id="location" path="location"
	placeholder="Enter campaign region"/>
<div class="small text-danger d-none">
Please enter campaign region.</div>
</div>

<div class="col">
<form:label path="targetAmount" cssClass="form-label">Target amount</form:label>
<span class="text-danger">*</span>
<form:input type="number" step="0.01" cssClass="form-control" 
	id="targetAmount" path="targetAmount"
	placeholder="Enter campaign target amount"/>
</div>
</div>

<div class="mb-3">
<form:label path="description" cssClass="form-label">Description</form:label>
<span class="text-danger">*</span>
<form:textarea style="height: 180px" cssClass="form-control"
	aria-label="Campaign description"
	placeholder="Enter campaign description" id="description"
	path="description"></form:textarea>
</div>

<hr class="my-4 d-md-none">

</div>

<div class="col-md-5 col-lg-4">
<div class="mb-3">
<form:label path="startDate" cssClass="form-label">Start date</form:label> 
<span class="text-danger">*</span>
<form:input type="date" cssClass="form-select" 
	id="startDate" path="startDate"
	value="${startDate}"/>
</div>


<div class="mb-3">
<form:label path="endDate" cssClass="form-label">End date</form:label>
<span class="text-danger">*</span>
<form:input type="date" cssClass="form-select" 
	id="endDate" path="endDate"
	value="${endDate}"/>
</div>

<hr class="my-4">

<div class="mb-3">
${imgHTML}
</div>

<div class="mb-3">
<form:label path="file" cssClass="form-label">
	${formTitle == "Upload" ? "Upload" : "Replace"} cover image (below 5MB)
	</form:label>
<c:if test="${formTitle == 'Upload'}">
<span class="text-danger">*</span>
</c:if>
<form:input cssClass="form-control" type="file"
	id="file" path="file"/>
<div><form:errors path="file" cssclass="small text-danger" /></div>
</div>

<hr class="my-4">

<div class="form-check form-switch">
<form:checkbox path="campaignStatus" cssClass="form-check-input"
	id="campaignStatus" value="1"/>
 <form:label cssClass="form-check-label" path="campaignStatus">
 Campaign status</form:label>
</div>
</div>
</div>

<div class="d-flex justify-content-center">
<input id="submitButton" class="btn btn-primary btn-lg px-5" 
	type="submit" value="Submit">
</div>

<form:hidden path="imgURL" value="${campaign.imgURL}"/>
<form:hidden path="campaignID" value="${campaign.campaignID}"/>
</form:form>
</div>