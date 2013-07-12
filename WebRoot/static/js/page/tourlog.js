scripts.tourlog = function(param, body) {
	$('.good', body).addClass('good' + param);
	$('.good', body).click(function() {
		tour_log_like(param);
	});
	$('input[name="tourLogId"]').val(param);
	requestApi('tourLog-getATourLogById', {tourLogId : param}, function(result) {
		console.log(result);
		if(!result)
			document.location.hash = 'home';
		$('h1 .username', body).attr('title', result.author);
		$('.content', body).html(result.content);
		parseUsernames();
	});
};
