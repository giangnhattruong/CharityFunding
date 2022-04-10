<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="search" class="container p-5 mb-5 button-shadow">
<form:form id="search-users" method="POST" 
	cssClass="search-form"
	modelAttribute="filter"
	action="users" >
<div class="row">
<div class="col-12 col-md mb-3">
<form:input cssClass="form-control me-3" type="text" path="keyword"
	placeholder="Search by email, fullname or phone number..." 
	aria-label="search for user email or fullname or phone"/>
</div>
</div>

<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<form:checkbox cssClass="form-check-input" path="statusActive"
	id="statusActive"/>
<form:label cssClass="form-check-label" path="statusActive">
	Active</form:label>
</div>
<div class="form-check me-3 mb-3">
<form:checkbox class="form-check-input" path="statusInactive"
	id="statusInactive"/>
<form:label cssClass="form-check-label" path="statusInactive">
	In-active</form:label>
</div>
<div class="form-check me-3 mb-3">
<form:checkbox class="form-check-input" path="roleAdmin"
	id="roleAdmin"/>
<form:label cssClass="form-check-label" path="roleAdmin">
	Admin</form:label>
</div>
<div class="form-check me-3 mb-3">
<form:checkbox class="form-check-input" path="roleUser"
	id="roleUser"/>
<form:label cssClass="form-check-label" path="roleUser">
	User</form:label>
</div>

<form:select class="form-select form-select-sm ms-auto sort-form" 
	path="sortBy" style="width: 300px">
  <form:option value="date-desc">
  	Sort by descending date joined(default)</form:option>
  <form:option value="email-desc">
  	Sort by descending email</form:option>
  <form:option value="total-donations-desc">
  	Sort by descending total donations</form:option>
  <form:option value="donation-times-desc">
  	Sort by descending donation times</form:option>
  <form:option value="latest-donation-date-desc">
  	Sort by descending latest donation date</form:option>
  <form:option value="date-asc">
  	Sort by ascending date joined</form:option>
  <form:option value="email-asc">
  	Sort by ascending email</form:option>
  <form:option value="total-donations-asc">
	Sort by ascending total donations</form:option>
  <form:option value="donation-times-asc">
  	Sort by ascending donation times</form:option>
  <form:option value="latest-donation-date-asc">
  	Sort by ascending latest donation date</form:option>
</form:select>
</div>

<div class="d-flex justify-content-center mt-3">
<input type="submit" class="btn btn-secondary me-3 px-5" 
	value="Search">
<span id="resetSearchButton" class="btn btn-outline-secondary me-3 px-5">Reset</span>
</div>
</form:form>
</div>