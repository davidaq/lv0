scripts.plan = function(param, body) {
	var fv = new FlowView(body);
	fv.show();
	$('#publish_plan').click(function() {
		var send = {
			plan : {
				planHeadline : $('#plan_title', body).val(),
				planContent : $('#plan_content', body).val()
			},
			planitems : []
		};
		$('.plan-item', body).each(function() {
			if($('input:first', this).val()) {
				send.planitems.push({
					resName : $('input:first', this).val(),
					startdate : $('input:eq(1)', this).val().replace(/-/g, '') * 1,
					enddate : $('input:eq(2)', this).val().replace(/-/g, '') * 1
				});
			}
		});
		requestApi('plan-addPlan', send, function(result) {
			if(result == 'ok') {
				refresh();
			}
		});
	});
	var search_tip = false;
	$('.plan-item', body).each(function() {
		$('input:first', this).typeahead({
			source : function(input, process) {
				if(search_tip) {
					search_tip.abort();
					search_tip = false;
				}
				if(input.length > 1) {
					search_tip = requestApi('resort-searchResortByName', {searchText : input}, function(result) {
						var ret = [];
						for(k in result) {
							ret.push(result[k].resName);
						}
						process(ret);
					});
				}
			}
		});
		$('input:gt(0)', this).datepicker();
	});
};
