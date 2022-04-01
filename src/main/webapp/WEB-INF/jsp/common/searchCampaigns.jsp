<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="search" class="container p-5 mb-5 border-bottom border-success">
<form id="search-campaigns" method="post" 
	action="<c:url value="${param.action}" />" >
<div class="row">
<div class="col-12 col-md mb-3">
<input class="form-control me-3" type="text" name="keyword"
	placeholder="Search  for campaign keyword..." 
	aria-label="search for campaign"
	value="${filter.keyword}">
</div>
<div class="col-12 col-md mb-3">
<input class="form-control" type="text" name="location"
	placeholder="Location" 
	aria-label="search for campaign location"
	value="${filter.location}">
</div>
</div>

<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="open"
	value="true" id="flexCheckDefault" ${filter.statusOn != null? "checked": ""}>
<label class="form-check-label" for="flexCheckDefault">Open</label>
</div>
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="closed"
	value="true" id="flexCheckChecked" ${filter.statusOff != null? "checked": ""}>
<label class="form-check-label" for="flexCheckChecked">Closed</label>
</div>
<select class="form-select form-select-sm ms-auto" name="sort"
	style="width: 300px">
  <option ${filter.sortBy == "date-desc"? "selected": ""}
	value="date-desc">Sort by descending date(default)</option>
  <option ${filter.sortBy == "target-desc"? "selected": ""}
	value="target-desc">Sort by descending donation target</option>
  <option ${filter.sortBy == "total-donations-desc"? "selected": ""}
	value="total-donations-desc">Sort by descending total donations</option>
  <option ${filter.sortBy == "total-supporters-desc"? "selected": ""}
	value="total-supporters-desc">Sort by descending total supporters</option>
  <option ${filter.sortBy == "latest-donation-date-desc"? "selected": ""}
	value="latest-donation-date-desc">Sort by descending latest donation date</option>
  <option ${filter.sortBy == "title-desc"? "selected": ""}
	value="title-desc">Sort by descending title</option>
  <option ${filter.sortBy == "date-asc"? "selected": ""}
	value="date-asc">Sort by ascending date</option>
  <option ${filter.sortBy == "target-asc"? "selected": ""}
	value="target-asc">Sort by ascending donation target</option>
  <option ${filter.sortBy == "total-donations-asc"? "selected": ""}
	value="total-donations-asc">Sort by ascending total donations</option>
  <option ${filter.sortBy == "total-supporters-asc"? "selected": ""}
	value="total-supporters-asc">Sort by ascending total supporters</option>
  <option ${filter.sortBy == "latest-donation-date-asc"? "selected": ""}
	value="latest-donation-date-asc">Sort by ascending latest donation date</option>
  <option ${filter.sortBy == "title-asc"? "selected": ""}
	value="title-asc">Sort by ascending title</option>
</select>
</div>

<div class="d-flex justify-content-center mt-3">
<button class="btn btn-success me-3 px-5">Search</button>
<button id="reset-campaign-search" class="btn btn-secondary me-3 px-5">Reset</button>
</div>
</form>
</div>