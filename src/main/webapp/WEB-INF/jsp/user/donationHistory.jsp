<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp"></c:import>

<div class="box-inset-shadow dashboard py-5 mx-3">
<h1 class="display-5 text-center glow mb-5">Donation History</h1>
<c:import url="/WEB-INF/jsp/user/searchUserDonationHistory.jsp"></c:import>
<c:import url="/WEB-INF/jsp/user/userDonationHistoryTable.jsp"></c:import>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"></c:import>