<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="contactBgImg" value="/resources/assets/media/steve-johnson-unsplash.jpg" />

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<section id="contactInfo" 
	class="container d-flex flex-wrap justify-content-evenly my-5">
<div class="contact-item d-flex flex-column align-items-center">
<i class="bi bi-geo-alt-fill"></i>
<p><strong>Address: </strong></p>
<p>123 Sample Street, NA, 983708</p>
</div>
<div class="contact-item d-flex flex-column align-items-center">
<i class="bi bi-telephone-fill"></i>
<p><strong>Phone: </strong></p>
<p>0938798685</p>
</div>
<div class="contact-item d-flex flex-column align-items-center">
<i class="bi bi-envelope-fill"></i>
<p><strong>Email: </strong></p>
<p>truonggnfx13372@funix.edu.vn</p>
</div>
</section>

<section id="contactForm" class="container my-5">
<div class="row g-0 button-shadow">
<div id="contactBgImg" class="card- d-none d-md-block col-md-5 col-lg-4"
	style="background: url('${contactBgImg}') center/cover no-repeat;">
</div>
<div class="col d-flex flex-column p-5">
<div class="text-center mb-3">
<h3>Contact Us</h3>
<p>If you have any suggestions, please leave your message here.</p>
</div>
<form method="POST" action="contact" class="needs-validation"
	id="submitForm">
<div class="mb-3">
<label for="fullname" class="form-label">Your full-name
<span class="text-danger">*</span>
</label>
<input type="text" class="form-control" name="fullname" 
	id="fullname" required>
<div class="invalid-feedback">
  Please enter your fullname.
</div>
</div>
<div class="mb-3">
<label for="email" class="form-label">Email
<span class="text-danger">*</span>
</label>
<input type="email" class="form-control" name="email" 
	id="email" required>
<div class="invalid-feedback">
  Please enter your email.
</div>
</div>
<div class="mb-3">
<label for="message" class="form-label">Message
<span class="text-danger">*</span>
</label>
<textarea type="text" class="form-control" name="message" id="message"
	style="min-height: 9rem" placeholder="Leave your message here..." required>
</textarea>
<div class="invalid-feedback">
  Please enter your message.
</div>
</div>
<div class="d-flex justify-content-center">
<button id="submitButton" 
	class="btn btn-brink-red-bold py-2 px-5">Submit</button>
</div>
</form>
</div>
</div>
</section>

<c:if test="${message != null && message != ''}">
<c:import url="/WEB-INF/jsp/common/notifyModal.jsp"></c:import>
</c:if>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>