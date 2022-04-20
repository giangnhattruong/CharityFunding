<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<section id="mainCarousel" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img width="100vw" 
      	src="<c:url value="/resources/assets/media/carousel-img-1.jpg" />" 
      	class="carousel-img d-block w-100" alt="Hand holding heart">
      <div class="img-middle-caption d-none d-md-block">
        <h5>A helping hand for </br><span>A hoping heart</span></h5>
        <p>Your donation can save millions people around the world and 
        make the world a better place.</p>
      </div>
    </div>
    <div class="carousel-item">
      <img width="100vw" 
      	src="<c:url value="/resources/assets/media/carousel-img-2.jpg" />" 
      	class="carousel-img d-block w-100" alt="smiling kid">
      <div class="img-middle-caption d-none d-md-block">
        <h5>Help the poor children</h5>
        <p>Your donation is able to support underprivileged children and
        give them hope for a brighter future.</p>
      </div>
    </div>
    <div class="carousel-item">
      <img width="100vw" 
      	src="<c:url value="/resources/assets/media/carousel-img-3.jpg" />" 
      	class="carousel-img d-block w-100" alt="kid having fun together">
      <div class="img-middle-caption d-none d-md-block">
        <h5>Build new generation</h5>
        <p>Your donation can help many kids go to school and 
        make the world more beautiful.</p>
      </div>
    </div>
  </div>
  <button class="carousel-control-prev" type="button" 
  	data-bs-target="#mainCarousel" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" 
  	data-bs-target="#mainCarousel" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
  
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
<path fill="#fcfcfc" fill-opacity="1" 
	d="M0,96L120,128C240,160,480,224,720,256C960,288,1200,288,
		1320,288L1440,288L1440,320L1320,320C1200,320,960,320,
		720,320C480,320,240,320,120,320L0,320Z">
</path></svg>
</section>

<section id="aboutUs" class="row g-0">
<div class="col-12 col-md-6">
<h3>About us</h3>
<p>Charity funding is a non-profit organization founded since 2021. 
Our vision is to support people who are suffering from war, natural disasters, disease, poverty,... 
around the world, especially under-privileged children. With your help, we can spread the hope to many 
and make the world a better place.</p>
</div>

<c:url var="aboutImg" value='/resources/assets/media/aboutus.png' />
<div class="d-none d-md-block col offset-md-1 d-flex justify-content-center align-items-center"
	style="background: url('${aboutImg}') center/contain no-repeat;">
</div>
</section>

<section id="allCampaigns" 
	class="wave-border d-flex flex-column align-items-center">
<h3 class="text-white">Our campaigns</h3>

<div id="mainSearch" class="d-flex justify-content-center mb-5">
<button class="search-btn"><i class="bi bi-search me-3"></i>
<span>Search</span>
</button>
<button class="reload-btn"><i class="bi bi-arrow-clockwise"></i></button>
<c:import url="/WEB-INF/jsp/main/mainSearchCampaign.jsp" />
</div>

<c:if test="${campaignList.isEmpty()}">
<h5 class="display-3 text-center text-white">Nothing found.</h5>
</c:if>

<div class="campaign-grid">
<c:forEach varStatus="loopStatus"
	var="campaign" items="${campaignList}">
<div id="item${loopStatus.index + 1}"
	class="card border-0 overflow-hidden" style="width: 21rem; height: 100%;">
<div class="card-img-container overflow-hidden">
<c:url var="imgURL" value="${campaign.imgURL}" />
<c:if test="${imgURL == ''}">
<c:url var="imgURL" value="/resources/assets/media/carousel-img-2.jpg" />
</c:if>

<img src="${imgURL}" 
	class="card-img-top" alt="Charity campaign cover image">
</div>
  <div class="card-body">
    <h5 class="card-title">${campaign.title}</h5>
    <table class="campaigns-summary">
    <tr>
    <td><strong>Status</strong></td>
    <td><strong>: ${campaign.campaignStatus == true ? "OPEN" : "CLOSED"}</strong></td>
    </tr>
    <tr>
    <td>Location</td>
    <td>: ${campaign.location}</td>
    </tr>
    <tr>
    <td>Donation target</td>
    <td>: $${campaign.targetAmount}</td>
    </tr>
    <tr>
    <td>Total supporters</td>
    <td>: ${campaign.totalSupporters} people</td>
    </tr>
    <tr>
    <td>Total donations</td>
    <td>: $${campaign.totalDonations}</td>
    </tr>
    </table>
    <a href="<c:url value="/campaign/${campaign.campaignID}" />" 
    	class="btn btn-spring-green">See more</a>
  </div>
</div>
</c:forEach>
</div>

<c:import url="/WEB-INF/jsp/common/pagination.jsp">
<c:param name="totalItems">${campaignList.size()}</c:param>
<c:param name="pageSize">6</c:param>
</c:import>

<svg class="wave-top" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
	<path fill="#fcfcfc" fill-opacity="1" 
		d="M0,160L120,154.7C240,149,480,139,720,144C960,149,1200,171,1320,181.3L1440,192L1440,0L1320,0C1200,0,960,0,720,0C480,0,240,0,120,0L0,0Z"></path></svg>
<svg class="wave-bottom" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
  <path fill="#fcfcfc" fill-opacity="1" 
  	d="M0,128L48,138.7C96,149,192,171,288,170.7C384,171,480,149,576,122.7C672,96,768,64,864,74.7C960,85,1056,139,1152,144C1248,149,1344,107,1392,85.3L1440,64L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path>
</svg>
</section>

<c:url var="splitSectionImg" value='/resources/assets/media/split-section-img.png' />
<section id="splitSectionImg" 
	class="wave-border d-flex justify-content-center d-none d-md-block"
	style="background: url('${splitSectionImg}') center/contain no-repeat;
		height: calc(100vw * 0.3);">
<svg class="wave-bottom" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
	<path fill="#fcfcfc" fill-opacity="1" 
		d="M0,320L40,320C80,320,160,320,240,309.3C320,299,400,277,480,256C560,235,640,213,720,186.7C800,160,880,128,960,117.3C1040,107,1120,117,1200,106.7C1280,96,1360,64,1400,48L1440,32L1440,320L1400,320C1360,320,1280,320,1200,320C1120,320,1040,320,960,320C880,320,800,320,720,320C640,320,560,320,480,320C400,320,320,320,240,320C160,320,80,320,40,320L0,320Z"></path></svg>
</section>

<section id="ourMissions">
<h3>Our main missions</h3>
<div class="d-flex flex-wrap justify-content-evenly">
<div class="mission d-flex flex-column align-items-center">
<i class="bi bi-book"></i>
<h5 class="text-center">Education</h5>
<p class="text-justify">Education is the backbone of a nation. 
But many children do not get a chance to go to school due to their poverty. 
So we are raising funds for a project called education for everyone and every child will go to school.</p>
</div>

<div class="mission d-flex flex-column align-items-center">
<i class="bi bi-heart-pulse"></i>
<h5 class="text-center">Medical</h5>
<p class="text-justify">People in Africa, Philistine, afghanistan, 
Livia and many other countries are not getting the proper medical treatment. 
So we have open a slogan and a project called “people will not suffer for medical treatment.</p>
</div>

<div class="mission d-flex flex-column align-items-center">
<i class="bi bi-droplet"></i>
<h5 class="text-center">Pure water</h5>
<p class="text-justify">Millions of people around the world live under a lower scale of poverty. 
They do not get pure, safe and sound drinking water. 
So we are trying to serve them pure health water for their better future.</p>
</div>
</div>
</section>

<section id="cta" class="d-flex flex-column align-items-center">
<h3>How can you help?</h3>
<p>By registering an account at Charity Funding and donate to our 
campaigns, you are sending life-changing gifts to many people who are in need.</p>
<div class="d-flex justify-content-center">
<a class="btn btn-outline-warning" href="#allCampaigns">Explore now</a>
</div>
</section>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>

<script defer src="<c:url value="/resources/assets/js/pagination.js"/>">
</script>