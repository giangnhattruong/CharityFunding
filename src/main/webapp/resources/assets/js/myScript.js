// Form search functions
$(".sort-form").on("change", function() {
	$(".search-form").submit();
})

$("#resetSearchButton").on("click", function() {
	location.reload();
})

$("#submitButton").on("click", function() {
	$("#submitForm").submit();
	$(this).addClass("disabled");
})

// Form reset function
$("#resetFormButton").on("click", function(e) {
	$(".resetForm input").each(function() {
		$(this).val("");
	})
})

// Modal function
$("#deleteBtn").on("click", function(e) {
	e.preventDefault();
	$(".confirm-delete-modal").show();
})

$(".modal-delete-btn").on("click", function() {
	$("#deleteForm").submit();
	$(".confirm-delete-modal").hide();
})

$(".modal-close-btn").on("click", function() {
	$(".my-modal").hide();
})
