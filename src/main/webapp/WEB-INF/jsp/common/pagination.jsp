<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav aria-label="Table navigation" class="d-flex justify-content-center mt-3">
 <ul class="pagination">
   <li class="page-item">
   <a class="page-link" aria-label="Previous"
   	href="<c:url value="/admin/campaigns?page=${param.pageNumber - 1 <= 0 ? 
   															1 : 
   															param.pageNumber - 1}" />">
     <span aria-hidden="true">&laquo;</span>
   </a>
   </li>
   <c:forEach var="page" step="1" begin="1" end="${param.totalPages}">
   <li class="page-item ${param.pageNumber == page ? 'active' : ''}">
   	<a class="page-link" 
   	href="<c:url value="/admin/campaigns?page=${page}" />">
   	${page}</a></li>
   </c:forEach>
   <li class="page-item">
   <a class="page-link" aria-label="Next"
   	href="<c:url value="/admin/campaigns?page=${param.pageNumber + 1 > param.totalPages ? 
   														param.totalPages : 
   														param.pageNumber + 1}" />">
     <span aria-hidden="true">&raquo;</span>
   </a>
   </li>
 </ul>
</nav>