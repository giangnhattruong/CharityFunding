//Show active pagination button
const showActive = function(currentPage) {
	$(".page-number").each(function() {
		$(this).removeClass("active");
		let pageNumber = Number.parseInt($(this).text());
		
		if (pageNumber === currentPage) {
			console.log(pageNumber);
			console.log(pageNumber == currentPage);
			$(this).addClass("active");
		}
	})
}

// Paginate page
const pagination = function() {
	let currentPage = Number.parseInt($("#currentPage").val());
	let totalItems = Number.parseInt($("#totalItems").text());
	let pageSize = Number.parseInt($("#pageSize").text());
    let startRowNumber = pageSize * (currentPage - 1) + 1;
    let endRowNumber = pageSize * currentPage;
    
    for (let i = 1; i <= totalItems; i++) {
		let rowID = `#row-${i}`;
		
		if (i >= startRowNumber && i <= endRowNumber) {
			$(rowID).removeClass("d-none");
		} else {
			$(rowID).addClass("d-none");
		}
	}
	
	showActive(currentPage);
}

// Initial pagination
pagination();

// Paginate on previous button click event
$("#pagePrevious").on("click", function() {
	let currentPage = Number.parseInt($("#currentPage").val());
    let pageNumber = Math.max(currentPage - 1, 1);
    $("#currentPage").val(pageNumber);
    pagination();
})

// Paginate on next button click event
$("#pageNext").on("click", function() {
	let totalPages = Number.parseInt($("#totalPages").text());
	let currentPage = Number.parseInt($("#currentPage").val());
    let pageNumber = Math.min(currentPage + 1, totalPages);
    $("#currentPage").val(pageNumber);
    pagination();
})

// Paginate on page number button click event
$(".page-number").on("click", function() {
    let pageNumber = Number.parseInt($(this).text());
    $("#currentPage").val(pageNumber);
    pagination();
})