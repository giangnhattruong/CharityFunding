<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Add 0.4999 for ceiling the result -->
<fmt:formatNumber var="totalItems" value="${param.totalItems}" />
<fmt:formatNumber var="pageSize" value="${param.pageSize}" />
<fmt:formatNumber var="totalPages" 
	value="${totalItems / pageSize + 0.4999}" 
	type="number" pattern="#" />
	
<nav aria-label="Table navigation" class="d-flex justify-content-center mt-3">
<p id="totalItems" class="d-none">${totalItems}</p>
<p id="pageSize" class="d-none">${pageSize}</p>
<p id="totalPages" class="d-none">${totalPages}</p>
<input type="hidden" id="currentPage" class="d-none" value="1">
<ul class="pagination">
	<li class="page-item"><span id="pagePrevious"
		class="page-link" aria-label="Previous" 
	    aria-hidden="true">&laquo;</span></li>
	<c:forEach var="page" step="1" begin="1" end="${totalPages}">
	<li class="page-item page-number"><span class="page-link">${page}</span></li>
	</c:forEach>
	<li class="page-item"><span id="pageNext"
		class="page-link" aria-label="Next" 
	    aria-hidden="true">&raquo;</span></li>
</ul>
</nav>