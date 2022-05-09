// Auto submit search form when user change sort order
$(".sort-form").on("change", function() {
	$(".search-form").submit();
})

// Reload page when user click on form reload button
$(".reload-btn").on("click", function() {
	window.history.forward(1);
	window.location.reload(true);
	return false;
})

//Validate empty input
const validateEmptyInput = function(form) {
	let isValidated = true;
	
	form.find(":required").each(function() {
		if ($(this).val() === '') {
			isValidated = false;
		}
	})
	
	return isValidated;
}

// Disable submit button after user click on submit
$("#submitButton").on("click", function() {
	if (validateEmptyInput($("#submitForm"))) {
		$("#submitForm").submit();
		$(this).addClass("disabled");
		$(".waiting-modal").show();
	}
})

// Append previous URL to login form
$("#loginButton").on("click", function(e) {
	e.preventDefault();
	$("[name=previousURL]").val(document.referrer);
	$("#loginForm").submit();
	$(this).addClass("disabled");
	$(".waiting-modal").show();
})

// Function get and append data info to confirm modal body
const appendDataInfoToModal = function() {
	let count = 0;
	
	$(".table-row").each(function() {
		if ($(this).is(":checked")) {
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

// Show confirm action model when user click on delete button.
$("#deleteBtn").on("click", function(e) {
	e.preventDefault();
	appendDataInfoToModal();
	$("#modalAction, #modalActionBtn").text("Delete");
	$(".confirm-action-modal").show();
})

// Show confirm action model when user click on reset passwords button.
$("#resetPasswordsBtn").on("click", function(e) {
	e.preventDefault();
	appendDataInfoToModal();
	$("#modalAction, #modalActionBtn").text("Reset passwords");
	$(".confirm-action-modal").show();
})

// Proceed submit form to delete when user click on confirm delete button.
$("#modal-action-btn").on("click", function() {
	if ($("#modalAction").text() === "Reset passwords") {
		let action = $("#actionForm").attr("action");
		let newAction = action.replace("delete", "reset-passwords");
		$("#actionForm").attr("action", newAction);
	}
	
	$("#actionForm").submit();
	$(".confirm-action-modal").hide();
	removeDataFromModal();
	$(".waiting-modal").show();
})

//Show bank info modal
$("#showBankInfo").on("click", function() {
	$("#bankInfoModal").show();
})

// Hide model when user click on close/return button
$(".modal-close-btn").on("click", function() {
	$(".my-modal").hide();
	removeDataFromModal();
})

// Show search modal button
$(".search-btn").on("click", function() {
	$(".search-modal").show();
})

// Google oauth function
function onSignIn(googleUser) {
         var profile = googleUser.getBasicProfile();
         console.log('ID: ' + profile.getId());
         console.log('Name: ' + profile.getName());
         console.log('Image URL: ' + profile.getImageUrl());
         console.log('Email: ' + profile.getEmail());
         console.log('id_token: ' + googleUser.getAuthResponse().id_token);

         var redirectUrl = location.href.endsWith("/") ?
         				location.href + "google-signin" :
         				location.href + "/google-signin";

         //using jquery to post data dynamically
         var form = $('<form action="' + redirectUrl + '" method="post">'
				+ '<input type="text" name="id_token" value="'
                + googleUser.getAuthResponse().id_token + '" />'
                + '<input type="text" name="previousURL" '
                + 'value="' + document.referrer + '"/>'
                + '</form>');
         $('body').append(form);
         form.submit();
         
         // Disconect user account.
         gapi.auth2.getAuthInstance().disconnect();
         
		$(".waiting-modal").show();
      }
