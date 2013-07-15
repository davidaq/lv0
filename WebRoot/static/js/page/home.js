scripts.home = function(param, body) {
	var fv = new FlowView(body);
	requestApi('home-getTop10', function(result) {
		for(k in result) {
			(function() {
				var item = result[k][0];
				var content = inflate($('.template', body), item);
				var element = fv.addBlock(content, item.abstract_.length > 50);
				$('.text', element).click(function() {
					document.location.hash = '#tourlog%' + item.tourLogId;
				});
			})();
		}
		parseUsernames(fv.fv);
		fv.show();
	});
};
