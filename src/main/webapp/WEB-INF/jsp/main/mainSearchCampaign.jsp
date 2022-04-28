<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="action" value="/explore" />

<div class="my-modal search-modal" aria-hidden="true" 
	aria-labelledby="searchModal" tabindex="-1">
<div class="modal-dialog modal-dialog-centered">
<div class="modal-content">
<div class="modal-header border-0">
<h5>Search</h5>
<button type="button" class="btn-close modal-close-btn" 
	aria-label="Close"></button>
</div>
<div class="modal-body">
<form:form method="POST" modelAttribute="filter"
	action="${action}" id="submitForm">
      	
<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input checkbox-brink-red" 
	name="statuses" id="closedStatus" value="0" 
	${filter.getStatuses().contains('0') ? 'checked' : ''}/>
<label class="form-check-label label-brink-red" for="closedStatus">
	Closed</label>
</div>

<div class="form-check me-3 mb-3">
<input type="checkbox" class="form-check-input checkbox-brink-red" 
	name="statuses" id="openStatus" value="1" 
	${filter.getStatuses().contains('1') ? 'checked' : ''}/>
<label class="form-check-label label-brink-red" for="openStatus">
	Opening</label>
</div>
</div>
      	
<div class="row mb-3">
<div class="col">
<form:input path="keyword"
	type="text" cssClass="keyword-search" placeholder="Keyword" />
</div>
      
<div class="col">
<form:input path="location"
	type="text" cssClass="keyword-search" placeholder="Location" />
</div>
</div>
      
<form:select path="sortBy"
	cssClass="form-select main-search-sort-select">
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
      
<div>
</div>
<div class="modal-footer justify-content-center border-0">
<button id="submitButton"
	class="btn main-search-btn btn-brink-red-bold px-5">GO</button>
</div>
</form:form>
</div>
</div>
</div>
</div>