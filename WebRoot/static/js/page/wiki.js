scripts.wiki = function(param, body) {
	var search_tip = false;
	$('#wiki_search', body).typeahead({
		source : function(input, process) {
			if(search_tip) {
				search_tip.abort();
				search_tip = false;
			}
			if(input.length > 2) {
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
		var input = $('#wiki_search').val();
		if(input.length > 2) {
			requestApi('resort-findResortByName', {resortName : input}, function(result) {
				if(result) {
					document.location.hash = 'wikipage%' + result.resortId;
				} else {
					$('#add_wiki_dlg h4', body).html("[" + input + "]");
					var data = { resort : {
						resName : input
					}};
					initForm($(body), data);
					$('#add_wiki_dlg', body).modal();
				}
			});
		}
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
						$(block).click(function() {
							document.location.hash = 'wikipage%' + item.resortId;
						});
						$('img', block).error(function() {
							$(this).attr('src', 'static/images/album_default.jpg');
						});
					})();
				}
				fv.show();
			}
		});
	});
	fv.show();
};
