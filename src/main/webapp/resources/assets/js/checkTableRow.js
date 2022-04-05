$("#selectOrDeselectAll").on("change", function() {
	let totalItems = Number.parseInt($("#totalItems").text());
	if ($(this).is(":checked")) {
		$(".table-row").prop("checked", true);
	} else {
		$(".table-row").prop("checked", false);
	}
})

$("#selectOrDeselectAll, .table-row").on("change", function() {
	let isRowChecked = false;
	
	$(".table-row").each(function() {
		if ($(this).is(":checked")) {
			isRowChecked = true;
		}
	})
	
	if (isRowChecked) {
		$("#deleteBtn").removeClass("d-none");
	} else {
		$("#deleteBtn").addClass("d-none");
	}
})