<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="search" class="container p-5 mb-5 border-bottom border-success">
<form id="search-campaigns" method="post" 
	action="<c:url value="/admin/users" />" >
<div class="row">
<div class="col-12 col-md mb-3">
<input class="form-control me-3" type="text" name="keyword"
	placeholder="Search  for user email or fullname..." 
	aria-label="search for user email or fullname"
	value="${keyword}">
</div>
</div>

<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="active"
	value="true" id="flexCheckDefault" ${active == "true"? "checked": ""}>
<label class="form-check-label" for="flexCheckDefault">Active</label>
</div>
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="inactive"
	value="true" id="flexCheckChecked" ${inactive == "true"? "checked": ""}>
<label class="form-check-label" for="flexCheckChecked">Inactive</label>
</div>
<select class="form-select form-select-sm ms-auto" name="sort"
	style="width: 300px">
  <option ${sort == "date-desc"? "selected": ""} 
  	value="date-desc">Sort by descending date joined(default)</option>
  <option ${sort == "email-desc"? "selected": ""} 
  	value="email-desc">Sort by descending email</option>
  <option ${sort == "total-donations-desc"? "selected": ""} 
  	value="total-donations-desc">Sort by descending total donations</option>
  <option ${sort == "donation-date-desc"? "selected": ""} 
	value="donation-date-desc">Sort by descending donation date</option>
  <option ${sort == "date-asc"? "selected": ""} 
	value="date-asc">Sort by ascending date joined</option>
  <option ${sort == "email-asc"? "selected": ""} 
	value="email-asc">Sort by ascending email</option>
  <option ${sort == "total-donations-asc"? "selected": ""} 
	value="total-donations-asc">Sort by ascending total donations</option>
  <option ${sort == "donation-date-asc"? "selected": ""} 
	value="donation-date-asc">Sort by ascending donation date</option>
</select>
</div>

<div class="d-flex justify-content-center mt-3">
<button class="btn btn-success px-5">Search</button>
</div>
</form>
</div>