<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="action" value="/admin/users" />

<aside id="search" class="col-12 col-lg-3 p-5 px-lg-3">
<h3 class="mb-3">Search users</h3>
<form:form id="search-users" method="POST" 
	cssClass="search-form"
	modelAttribute="filter"
	action="${action}" >
	
<strong>Status</strong>
<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="inactiveStatus" value="0" 
	${filter.getStatuses().contains('0') ? 'checked' : ''}/>
<label class="form-check-label" for="inactiveStatus">
	In-active</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="activeStatus" value="1" 
	${filter.getStatuses().contains('1') ? 'checked' : ''}/>
<label class="form-check-label" for="activeStatus">
	Active</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="bannedStatus" value="2" 
	${filter.getStatuses().contains('2') ? 'checked' : ''}/>
<label class="form-check-label" for="bannedStatus">
	Banned</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="statuses"
	id="deletedStatus" value="3" 
	${filter.getStatuses().contains('3') ? 'checked' : ''}/>
<label class="form-check-label" for="deletedStatus">
	Deleted</label>
</div>
</div>

<strong>Role</strong>
<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="roles"
	id="userRole" value="0" 
	${filter.getRoles().contains('0') ? 'checked' : ''}/>
<label class="form-check-label" for="userRole">
	User</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input" name="roles"
	id="adminRole" value="1" 
	${filter.getRoles().contains('1') ? 'checked' : ''}/>
<label class="form-check-label" for="adminRole">
	Admin</label>
</div>
</div>

<div class="row">
<div class="col col-lg-12 mb-3">
<form:input cssClass="form-control me-3" type="text" path="keyword"
	placeholder="Email/fullname/phone..." 
	aria-label="Search for user email or fullname or phone"/>
</div>
</div>

<form:select class="form-select form-select-sm ms-auto sort-form" 
	path="sortBy">
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

<div class="d-flex justify-content-center mt-3">
<input type="submit" class="btn btn-brink-red-bold me-3 px-5" 
	value="Search">
<button class="reload-btn"><i class="bi bi-arrow-clockwise"></i></button>
</div>
</form:form>
</aside>