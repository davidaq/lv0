scripts.tourlog = function(param, body) {
	$('.good', body).addClass('good' + param);
	$('.good', body).closest('.btn').click(function() {
		tour_log_like(param);
	});
	$('input[name="tourLogId"]').val(param);
	requestApi('tourLog-getGoodByTourLogId', {tourLogId : param}, function(result) {
		$('.good', body).html(result);
	});
	requestApi('tourLog-getATourLogById', {tourLogId : param}, function(result) {
		if(!result)
			document.location.hash = 'home';
		$('h1 .username', body).attr('title', result.author);
		$('.content', body).html(result.content);
		parseUsernames();
		requestApi('tourLog-getCommentByTourLogId', {tourLogId : param}, function(result) {
			console.log(result);
			if(result) {
				for(k in result) {
					var comment = document.createElement('div');
					comment.className = "item";
					comment.innerHTML = '<img class="useravatar" src="static/images/picmi.png" style="width: 30px; height: 30px" title="' + result[k].uid + '"/>'
						+ ' <b class="username" title="' + result[k].uid + '"></b>: '
						+ result[k].comContent;
						;
					$('#comment-list').append(comment);
				}
			}
			parseUsernames();
		});
	});
};
