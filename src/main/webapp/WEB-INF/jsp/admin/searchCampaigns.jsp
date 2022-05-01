<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="action" value="/admin/campaigns" />

<aside id="search" class="col-12 col-lg-3 p-5 px-lg-3">
<h3 class="mb-3">Search campaigns</h3>
<form:form id="search-campaigns" method="POST" 
	cssClass="search-form"
	modelAttribute="filter"
	action="${action}" >

<strong>Status</strong>
<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="closedStatus" value="0" 
	${filter.getStatuses().contains('0') ? 'checked' : ''}/>
<label class="form-check-label" for="closedStatus">
	Closed</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="openStatus" value="1" 
	${filter.getStatuses().contains('1') ? 'checked' : ''}/>
<label class="form-check-label" for="openStatus">
	Opening</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="deletedStatus" value="2" 
	${filter.getStatuses().contains('2') ? 'checked' : ''}/>
<label class="form-check-label" for="deletedStatus">
	Deleted</label>
</div>
</div>

<div class="row">
<div class="col col-lg-12 mb-3">
<form:input cssClass="form-control me-3" type="text" path="keyword"
	placeholder="Search  for campaign keyword..." 
	aria-label="search for campaign"/>
</div>
<div class="col col-lg-12 mb-3">
<form:input cssClass="form-control" type="text" path="location"
	placeholder="Location" 
	aria-label="search for campaign location"/>
</div>
</div>

<form:select cssClass="form-select form-select-sm ms-auto sort-form" 
	path="sortBy">
  <form:option value="date-desc">
	Sort by descending date(default)</form:option>
  <form:option value="title-desc">
	Sort by descending title</form:option>
  <form:option value="target-desc">
	Sort by descending donation target</form:option>
  <%-- <form:option value="total-donations-desc">
	Sort by descending total donations</form:option>
  <form:option value="total-supporters-desc">
	Sort by descending total supporters</form:option>
  <form:option value="latest-donation-date-desc">
	Sort by descending latest donation date</form:option> --%>
  <form:option value="date-asc">
	Sort by ascending date</form:option>
  <form:option value="title-asc">
	Sort by ascending title</form:option>
  <form:option value="target-asc">
	Sort by ascending donation target</form:option>
  <%-- <form:option value="total-donations-asc">
	Sort by ascending total donations</form:option>
  <form:option value="total-supporters-asc">
	Sort by ascending total supporters</form:option>
  <form:option value="latest-donation-date-asc">
	Sort by ascending latest donation date</form:option> --%>
</form:select>

<div class="d-flex justify-content-center mt-3">
<input type="submit" class="btn btn-brink-red-bold me-3 px-5" 
	value="Search">
<button class="reload-btn"><i class="bi bi-arrow-clockwise"></i></button>
</div>
</form:form>
</aside>