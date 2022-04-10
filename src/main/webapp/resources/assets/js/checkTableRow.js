// Check/uncheck all rows when user check/uncheck select all button
$("#selectOrDeselectAll").on("change", function() {
	let totalItems = Number.parseInt($("#totalItems").text());
	if ($(this).is(":checked")) {
		$(".table-row").prop("checked", true);
	} else {
		$(".table-row").prop("checked", false);
	}
})

// Only show the delete and reset passwords button when at least one of the row is checked
$("#selectOrDeselectAll, .table-row").on("change", function() {
	let isRowChecked = false;
	
	$(".table-row").each(function() {
		if ($(this).is(":checked")) {
			isRowChecked = true;
		}
	})
	
	if (isRowChecked) {
		$("#deleteBtn, #resetPasswordsBtn").removeClass("d-none");
	} else {
		$("#deleteBtn, #resetPasswordsBtn").addClass("d-none");
	}
})