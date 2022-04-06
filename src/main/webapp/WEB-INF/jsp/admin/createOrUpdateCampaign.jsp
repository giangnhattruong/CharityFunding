<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<c:url var="action" value="${formAction}" />
<c:set var="startDate" value="${campaign.startDate.toString()}"></c:set>
<c:set var="endDate" value="${campaign.endDate.toString()}"></c:set>

<div class="py-5 text-center">
<h2>${formTitle} campaign</h2>
<p class="lead">Please fill all the required informations below to 
	${fn:toLowerCase(formTitle)} a campaign.</p>
</div>

<div class="container mb-5">
<p class="small text-danger offset-lg-1">${message}</p>
<form:form id="submitForm"
	cssClass="resetForm" 
	method="POST" modelAttribute="campaign"
	action="${action}" enctype="multipart/form-data">

<c:if test="${formTitle == 'Create'}">
<input type="reset" value="&#8634; CLEAR"
	class="btn btn-outline-secondary resetFormButton my-3 offset-lg-1">
</c:if>

<div class="row g-md-5 mb-3">
<div class="col-md col-lg-5 offset-lg-1">
<div class="mb-3">
<form:label path="title" cssClass="form-label lead">
	Title</form:label>
<span class="text-danger">*</span>
<form:input type="text" cssClass="form-control" 
	id="title" path="title"
	placeholder="Enter campaign title (below 60 characters)"/>
</div>

<div class="row g-3 mb-3">
<div class="col">
<form:label path="location" cssClass="form-label lead">
	Location</form:label>
<span class="text-danger">*</span>
<form:input type="text" cssClass="form-control" 
	id="location" path="location"
	placeholder="Enter region"/>
</div>

<div class="col">
<form:label path="targetAmount" cssClass="form-label lead">
	Target amount</form:label>
<span class="text-danger">*</span>
<form:input type="number" step="0.01" cssClass="form-control" 
	id="targetAmount" path="targetAmount"
	placeholder="Enter campaign target amount"/>
</div>
</div>

<div class="mb-3">
<form:label path="description" cssClass="form-label lead">
	Description</form:label>
<span class="text-danger">*</span>
<form:textarea style="height: 180px" cssClass="form-control"
	aria-label="Campaign description"
	placeholder="Enter campaign description" id="description"
	path="description"></form:textarea>
</div>

<hr class="my-4 d-md-none">
</div>

<div class="col-md col-lg-5 border-start-md">
<div class="mb-3">
<form:label path="startDate" cssClass="form-label lead">
	Start date</form:label> 
<span class="text-danger">*</span>
<form:input type="date" cssClass="form-select" 
	id="startDate" path="startDate"
	value="${startDate}"/>
</div>

<div class="mb-3">
<form:label path="endDate" cssClass="form-label lead">
	End date</form:label>
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
	${formTitle == "Create" ? "Upload" : "Replace"} cover image (below 5MB)
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
	id="campaignStatus"/>
 <form:label cssClass="form-check-label" path="campaignStatus">
 Campaign status</form:label>
</div>
</div>
</div>

<div class="d-flex justify-content-center">
<a id="returnBackButton" class="btn btn-secondary btn-lg px-5 me-3" 
href="<c:url value="/admin/campaigns" />">Return</a>
<input id="submitButton" class="btn btn-primary btn-lg px-5 me-3" 
	type="submit" value="Submit">
</div>

<form:hidden path="imgURL" value="${campaign.imgURL}"/>
</form:form>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>