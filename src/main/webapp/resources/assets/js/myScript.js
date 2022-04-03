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

$("#reset-campaign-search").on("click", function() {
	$("#search-campaigns input").each(function() {
		$(this).val("");
	})
	$("[name=open]").prop("checked", true);
	$("[name=closed]").prop("checked", true);
	$("[name=sort]").val("date-desc");
	$("#search-campaigns").submit();
})

$("#submitButton").on("click", function() {
	$("#submitForm").submit();
	$(this).addClass("disabled");
})

/*$(".needs-validation").on("submit", function(e) {
	e.preventDefault();
	let isAllValidated = true;
	
	$(".needs-validation input, .needs-validation textarea")
		.not("input[type=checkbox]")
		.each(function() {
	    if (!$(this).val()) {
			isAllValidated = false;
	        $(this).next().removeClass("d-none");
	    } else {
			$(this).next().addClass("d-none");
		}
	})
	    
    if (isAllValidated) {
		$(".needs-validation").submit();
	}
})*/