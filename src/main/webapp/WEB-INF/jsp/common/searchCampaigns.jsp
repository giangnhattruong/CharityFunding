<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="search" class="container p-5 mb-5 border-bottom border-success">
<form:form id="search-campaigns" method="POST" 
	cssClass="search-form"
	modelAttribute="filter"
	action="${param.action}" >
<div class="row">
<div class="col-12 col-md mb-3">
<form:input cssClass="form-control me-3" type="text" path="keyword"
	placeholder="Search  for campaign keyword..." 
	aria-label="search for campaign"/>
</div>
<div class="col-12 col-md mb-3">
<form:input cssClass="form-control" type="text" path="location"
	placeholder="Location" 
	aria-label="search for campaign location"/>
</div>
</div>

<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<form:checkbox cssClass="form-check-input" path="statusOpen"
	id="statusOpen"/>
<form:label cssClass="form-check-label" path="statusOpen">
	Open</form:label>
</div>
<div class="form-check me-3 mb-3">
<form:checkbox cssClass="form-check-input" path="statusClosed"
	id="statusClosed"/>
<form:label cssClass="form-check-label" path="statusClosed">
	Closed</form:label>
</div>
<form:select cssClass="form-select form-select-sm ms-auto sort-form" 
	path="sortBy" style="width: 300px">
  <form:option value="date-desc">
	Sort by descending date(default)</form:option>
  <form:option value="target-desc">
	Sort by descending donation target</form:option>
  <form:option value="total-donations-desc">
	Sort by descending total donations</form:option>
  <form:option value="total-supporters-desc">
	Sort by descending total supporters</form:option>
  <form:option value="latest-donation-date-desc">
	Sort by descending latest donation date</form:option>
  <form:option value="title-desc">
	Sort by descending title</form:option>
  <form:option value="date-asc">
	Sort by ascending date</form:option>
  <form:option value="target-asc">
	Sort by ascending donation target</form:option>
  <form:option value="total-donations-asc">
	Sort by ascending total donations</form:option>
  <form:option value="total-supporters-asc">
	Sort by ascending total supporters</form:option>
  <form:option value="latest-donation-date-asc">
	Sort by ascending latest donation date</form:option>
  <form:option value="title-asc">
	Sort by ascending title</form:option>
</form:select>
</div>

<div class="d-flex justify-content-center mt-3">
<input type="submit" class="btn btn-success me-3 px-5" 
	value="Search">
<span id="resetSearchButton" class="btn btn-secondary me-3 px-5">Reset</span>
</div>
</form:form>
</div>