<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="search" class="container p-5 mb-5 border-bottom border-success">
<form id="search-campaigns" method="post" 
	action="<c:url value="/user/donation-history" />" >
<div class="row">
<div class="col-12 col-md mb-3">
<input class="form-control" type="text" name="campaignKeyword"
	placeholder="Campaign title/location..." 
	aria-label="Search for Campaign title or location"
	value="${campaignKeyword}">
</div>
<div class="col-12 col-md mb-3">
<input class="form-control me-3" type="text" name="transactionKeyword"
	placeholder="Bank's transaction code..." 
	aria-label="Search for bank's transaction code"
	value="${transactionKeyword}">
</div>
</div>

<div class="d-flex flex-wrap">
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="delivered"
	value="true" id="flexCheckDefault" ${delivered == "true"? "checked": ""}>
<label class="form-check-label" for="flexCheckDefault">Delivered</label>
</div>
<div class="form-check me-3 mb-3">
<input class="form-check-input" type="checkbox" name="notDelivered"
	value="true" id="flexCheckChecked" ${notDelivered == "true"? "checked": ""}>
<label class="form-check-label" for="flexCheckChecked">Not delivered</label>
</div>
<select class="form-select form-select-sm ms-auto" name="sort"
	style="width: 280px">
  <option ${sort == "date-desc"? "selected": ""}
	value="date-desc">Sort by descending date(default)</option>
  <option ${sort == "donation-desc"? "selected": ""}
	value="donation-desc">Sort by descending donation</option>
  <option ${sort == "transaction-code-desc"? "selected": ""}
	value="transaction-code-desc">Sort by descending transaction code</option>
  <option ${sort == "date-asc"? "selected": ""}
	value="date-asc">Sort by ascending date</option>
  <option ${sort == "donation-asc"? "selected": ""}
	value="donation-asc">Sort by ascending donation</option>
  <option ${sort == "transaction-code-asc"? "selected": ""}
	value="transaction-code-asc">Sort by ascending transaction code</option>
</select>
</div>

<div class="d-flex justify-content-center mt-3">
<button class="btn btn-success px-5">Search</button>
</div>
</form>
</div>