<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="my-modal confirm-delete-modal" 
	aria-hidden="true" aria-labelledby="notifyModal" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content" style="height: 60vh; width: 60vw;">
      <div class="modal-header border-0">
      	<p class="text-center h3">Are you sure to ${param.action} these ${param.itemLabel}(s):</p>
        <button type="button" class="btn-close modal-close-btn" aria-label="Close"></button>
      </div>
      <div class="modal-body px-5 overflow-auto">
      <table class="table table-borderless"></table>
      </div>
      <div class="modal-footer border-0">
        <button class="btn modal-close-btn btn-secondary">Return</button>
        <button id="modal-action-btn" class="btn btn-danger">Delete</button>
      </div>
    </div>
  </div>
</div>