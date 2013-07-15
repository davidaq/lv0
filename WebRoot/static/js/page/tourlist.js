scripts.tourlist = function(param, body) {
	$('h1 .username', body).attr('title', param);
	console.log(param);
	var fv = new FlowView(body);
	var page = 1;
	fv.load(function() {
		requestApi('tourLog-getTourLogBySomeoneId', {author : param, pageNow : page}, function(result) {
			if(result && result.length > 0) {
				for(k in result) {
					(function() {
						var item = result[k];
						var content = inflate($('.template', body), item);
						var element = fv.addBlock(content, item.abstract_.length > 50);
						$('.text', element).click(function() {
							document.location.hash = '#tourlog%' + item.tourLogId;
						});
					})();
				}
				parseUsernames(fv.fv);
				fv.show();
			}
			page++;
			parseUsernames(fv.fv);
			fv.show();
		});
	});
	parseUsernames(body);
	fv.show();
};
