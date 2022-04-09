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

// Function get and append data info to confirm modal body
const appendDataInfoToModal = function() {
	let count = 0;
	
	$(".table-row").each(function() {
		if ($(this).is(":checked")) {
			console.log($(this).attr("data-info"))
			const td1 = $("<td>").text(++count);
			const td2 = $("<td>").text($(this).attr("data-info"));
			const tableRow = $("<tr>").append(td1).append(td2);
			$(".modal-body table").append(tableRow);
		}
	})
}

// Function to remove data from modal body
const removeDataFromModal = function() {
	$(".modal-body table").empty();
}

// Show confirm delete model when user click on delete button
$("#deleteBtn").on("click", function(e) {
	e.preventDefault();
	appendDataInfoToModal();
	$(".confirm-delete-modal").show();
})

// Proceed submit form to delete when user click on confirm delete button
$("#modal-action-btn").on("click", function() {
	$("#deleteForm").submit();
	$(".confirm-delete-modal").hide();
})

// Hide model when user click on close/return button
$(".modal-close-btn").on("click", function() {
	$(".my-modal").hide();
	removeDataFromModal();
})
