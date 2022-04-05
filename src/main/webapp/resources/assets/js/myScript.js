// Auto submit search form when user change sort order
$(".sort-form").on("change", function() {
	$(".search-form").submit();
})

// Reload page when user click on form reset button
$("#resetSearchButton").on("click", function() {
	location.reload();
})

// Disable submit button after user click on submit
$("#submitButton").on("click", function() {
	$("#submitForm").submit();
	$(this).addClass("disabled");
})

// Show confirm delete model when user click on delete button
$("#deleteBtn").on("click", function(e) {
	e.preventDefault();
	$(".confirm-delete-modal").show();
})

// Proceed submit form to delete when user click on confirm delete button
$(".modal-delete-btn").on("click", function() {
	$("#deleteForm").submit();
	$(".confirm-delete-modal").hide();
})

// Hide model when user click on close/return button
$(".modal-close-btn").on("click", function() {
	$(".my-modal").hide();
})
