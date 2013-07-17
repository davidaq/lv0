scripts.wiki = function(param, body) {
	var search_tip = false;
	$('#wiki_search', body).typeahead({
		source : function(input, process) {
			if(search_tip) {
				search_tip.abort();
				search_tip = false;
			}
			if(input.length > 3) {
				requestApi('resort-searchResortByName', {searchText : input}, function(result) {
					var ret = [];
					for(k in result) {
						ret.push(result[k].resName);
					}
					process(ret);
				});
			}
		}
	});
	$('#wiki_search_btn').click(function() {
		requestApi('resort-searchResortByName', {searchText : input}, function(result) {
			var ret = [];
			for(k in result) {
				ret.push(result[k].resName);
			}
			process(ret);
		});
	});
	var fv = new FlowView(body);
	var page = 1;
	fv.load(function() {
		requestApi('resort-list', {page : page}, function(result) {
			if(result && result.length > 0) {
				page++;
				for(k in result) {
					(function() {
						var item = result[k];
						var block = inflate($('.template', body), item);
						block = fv.addBlock(block);
						console.log(item);
					})();
				}
				fv.show();
			}
		});
	});
	fv.show();
};
