<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="action" value="/admin/donation-history" />

<aside id="search" class="col-12 col-lg-3 p-5 px-lg-3">
<h3 class="mb-3">Search history</h3>
<form:form id="search-history" method="POST" 
	cssClass="search-form"
	modelAttribute="filter"
	action="${action}" >
	
<strong>Status</strong>
<div class="search-check d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="notVerifiedStatus" value="0" 
	${filter.getStatuses().contains('0') ? 'checked' : ''}/>
<label class="form-check-label" for="notVerifiedStatus">
	Not verified</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="verifiedStatus" value="1" 
	${filter.getStatuses().contains('1') ? 'checked' : ''}/>
<label class="form-check-label" for="verifiedStatus">
	Verified</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="failedStatus" value="2" 
	${filter.getStatuses().contains('2') ? 'checked' : ''}/>
<label class="form-check-label" for="failedStatus">
	Failed</label>
</div>
</div>

<div class="row">
<div class="col col-lg-12 mb-3">
<form:input cssClass="form-control me-3" type="text" path="userKeyword"
	placeholder="Email/fullname..." 
	aria-label="Search for user email or fullname"/>
</div>
<div class="col col-lg-12 mb-3">
<form:input cssClass="form-control" type="text" path="campaignKeyword"
	placeholder="Campaign keyword..." 
	aria-label="Search for campaign title or location"/>
</div>
<div class="col col-lg-12 mb-3">
<form:input cssClass="form-control me-3" type="text" path="transactionKeyword"
	placeholder="Transaction code..." 
	aria-label="Search for bank's transaction code"/>
</div>
</div>

<form:select cssClass="form-select form-select-sm ms-auto sort-form" 
	path="sortBy">
  <form:option value="date-desc">
  	Sort by descending date(default)</form:option>
  <form:option value="email-desc">
  	Sort by descending email</form:option>
  <form:option value="title-desc">
  	Sort by descending title</form:option>
  <form:option value="donation-desc">
  	Sort by descending donation</form:option>
  <form:option value="transaction-code-desc">	
  	Sort by descending transaction code</form:option>
  <form:option value="date-asc">
  	Sort by ascending date</form:option>
  <form:option value="email-asc">
  	Sort by ascending email</form:option>
  <form:option value="title-asc">
  	Sort by ascending title</form:option>
  <form:option value="donation-asc">
  	Sort by ascending donation</form:option>
  <form:option value="transaction-code-asc">
  	Sort by ascending transaction code</form:option>
</form:select>

<div class="d-flex justify-content-center mt-3">
<input type="submit" class="btn btn-brink-red-bold me-3 px-5" 
	value="Search">
<button class="reload-btn"><i class="bi bi-arrow-clockwise"></i></button>
</div>
</form:form>
</aside>
