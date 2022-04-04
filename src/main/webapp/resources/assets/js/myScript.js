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

$("#deleteForm input:checkbox").on("change", function() {
	if($("#deleteForm input:checkbox").is(":checked")) {
		$("#deleteBtn").removeClass("d-none");
	} else {
		$("#deleteBtn").addClass("d-none");
	}
})

$("#resetSearchButton").on("click", function() {
	location.reload();
})

$("#submitButton").on("click", function() {
	$("#submitForm").submit();
	$(this).addClass("disabled");
})

$("#resetFormButton").on("click", function(e) {
	$(".resetForm input").each(function() {
		$(this).val("");
	})
})
