<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<c:url var="campaignCoverImg" value="${campaign.imgURL == null ? 
						'/resources/assets/media/carousel-img-1.jpg' :
						campaign.imgURL}" />

<section id="campaignCoverImg" class="row g-0"
	style="background: url('${campaignCoverImg}') center/cover no-repeat;">
<h1 class="col col-lg-8">${campaign.title}</h1>
</section>

<section id="campaignContent" class="container py-5">
<div class="row">
<div id="description" class="col-12 order-2 col-lg-7 order-lg-1">
<p>${campaign.description}</p>
</div>

<div id="campaignDetails" class="col order-1 offset-lg-1 order-lg-2">
<h3>Details</h3>
<table>
<tr>
<td><strong>Status</strong></td>
<td><strong>: ${campaign.campaignStatus == true ? "OPEN" : "CLOSED"}</strong></td>
</tr>
<tr>
<td>Location</td>
<td>: ${campaign.location}</td>
</tr>
<tr>
<td>Target</td>
<td>: $<fmt:formatNumber value="${campaign.targetAmount}"
         type="number"/></td>
</tr>
<tr>
<td>Supporters</td>
<td>: <fmt:formatNumber value="${campaign.totalSupporters}"
         type="number"/> people</td>
</tr>
<tr>
<td>Donations</td>
<td>: $<fmt:formatNumber value="${campaign.totalDonations}"
         type="number"/></td>
</tr>
<tr>
<td>Start date</td>
<td>: ${campaign.startDate}</td>
</tr>
<tr>
<td>End date</td>
<td>: ${campaign.endDate}</td>
</tr>
</table>
</div>
</div>
</section>

<c:url var="donationImage" 
	value="/resources/assets/media/claudio-schwarz-helping-hand.jpg" />

<section id="donationSection"  class="row g-0">
<div class="col d-none d-lg-block"
	style="background: url('${donationImage}') 
		center/cover no-repeat;">
</div>
<div class="donation-content col-lg-7 p-5">
<h3 class="text-warning">${campaign.campaignStatus == true ? 
													"Donate" :
													"Campaign is closed."}</h3>

<c:if test="${email != null && campaign.campaignStatus == true}">
<c:if test="${validateMessage != ''}">
<p class="validate-message">${validateMessage}</p>
</c:if>

<c:url var="action" value="/campaign/${campaign.campaignID}" />
<form:form method="POST"
	modelAttribute="transaction"
	action="${action}">
<div class="mb-3">
<form:label path="fullname">Your full name</form:label>
<form:input cssClass="form-control" type="text" path="fullname"  
	placeholder="Enter your full name..." value="${user.fullname}"/>
</div>
<div class="mb-3">
<form:label path="amount">Donation amount</form:label>
<form:input cssClass="form-control" type="number" step="0.01" path="amount" 
	placeholder="$0.00 (must be in range $5 - $10,000)" />
</div>
<div class="mb-3">
<form:label path="bankNumber">Your bank number</form:label>
<form:input class="form-control" type="text" path="bankNumber"  
	placeholder="Enter your bank number" />
</div>
<div class="d-flex mt-4">
<button class="btn donate-btn btn-outline-warning py-2 px-5">Send</button>
</div>
</form:form>
</c:if>

<c:if test="${email == null && campaign.campaignStatus == true}">
<div class="d-flex flex-column align-items-center">
<h5>Please login first to donate.</h5>
<a class="btn btn-outline-warning py-2 px-5"
	href="<c:url value='/login' />">Login Now</a>
</div>
</c:if>

</div>
</section>

<c:if test="${message != null && message != ''}">
<c:import url="/WEB-INF/jsp/common/notifyModal.jsp"></c:import>
</c:if>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>