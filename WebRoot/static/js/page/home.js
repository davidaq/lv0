scripts.home = function(param, body) {
	var fv = new FlowView(body);
	requestApi('home-getTop10', function(result) {
		for(k in result) {
			(function() {
				var item = result[k][0];
				var content = $('.template', body).html();
				for(i in item)
					content = content.replace(new RegExp('%' + i + '%','g'), item[i]);
				var element = fv.addBlock(content, item.abstract_.length > 100);
				$('.text', element).click(function() {
					document.location.hash = '#tourlog%' + item.tourLogId;
				});
			})();
		}
		parseUsernames(fv.fv);
		fv.show();
	});
};
