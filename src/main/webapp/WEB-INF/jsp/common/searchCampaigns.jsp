<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="search" class="container p-5 mb-5 border-bottom border-success">
<form id="search-campaigns" method="post" 
	action="<c:url value="${param.action}" />" >
<div class="row">
<div class="col-12 col-md mb-3">
<input class="form-control me-3" type="text" name="keyword"
	placeholder="Search  for campaign keyword..." aria-label="search for campaign">
</div>
<div class="col-12 col-md mb-3">
<input class="form-control" type="text" name="location"
	placeholder="Location" aria-label="search for campaign location">
</div>
</div>

<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="open"
	value="true" id="flexCheckDefault">
<label class="form-check-label" for="flexCheckDefault">Open</label>
</div>
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="close"
	value="true" id="flexCheckChecked" >
<label class="form-check-label" for="flexCheckChecked">Closed</label>
</div>
<select class="form-select form-select-sm ms-auto" name="sort"
	style="width: 280px">
  <option selected value="date-desc">Sort by descending date(default)</option>
  <option value="date-asc">Sort by ascending date</option>
  <option value="title-desc">Sort by descending title</option>
  <option value="title-asc">Sort by ascending title</option>
</select>
</div>

<div class="d-flex justify-content-center mt-3">
<button class="btn btn-success px-5">Search</button>
</div>
</form>
</div>