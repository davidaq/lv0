scripts.plan = function(param, body) {
	var fv = new FlowView(body);
	var page = 1;
	function toDate(num) {
		if(num == 0)
			return '/';
		return Math.ceil(num / 10000) + '-' + (Math.ceil(num / 100) % 100) + '-' + (num % 100);
	}
	fv.load(function() {
		requestApi('plan-getMyPlans', {page:page}, function(result) {
			if(result && result.length) {
				page++;
				for(k in result) {
					(function() {
						var item = result[k];
						var block = inflate($('.template.block', body), item.plan);
						block = fv.addBlock(block);
						for(i in item.items) {
							item.items[i].startdate = toDate(item.items[i].startdate);
							item.items[i].enddate = toDate(item.items[i].enddate);
							var planItem = inflate($('.template.planitem', body), item.items[i]);
							$('.plan-items', block).append(planItem);
						}
						$('.remove', block).click(function() {
							requestApi('plan-deletePlan', {planId:item.plan.planId}, function(result) {
								if(result == 'ok') {
									$(block).fadeOut(400, function() {
										$(this).show();
										$(this).css({visibility:'hidden'});
									});
								}
							});
						});
						$('.btn-primary', block).click(function() {
							document.location.hash = 'planmatch%' + item.plan.planId;
						});
					})();
				}
				parseUsernames();
				fv.show();
			}
		});
	});
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
