scripts.planmatch = function(param, body) {
	var fv = new FlowView(body);
	function toDate(num) {
		if(num == 0)
			return '/';
		return Math.ceil(num / 10000) + '-' + (Math.ceil(num / 100) % 100) + '-' + (num % 100);
	}
	requestApi('plan-findPartner', {planId:param}, function(result) {
		if(result && result.length > 1) {
			for(k in result) {
				(function() {
					var item = result[k];
					var block = inflate($('.template.block', body), item.plan);
					block = fv.addBlock(block);
					if(k == 0) {
						$(block).css({
							border : '1px solid #333',
							backgroundColor : '#ADF'
						});
					}
					for(i in item.items) {
						item.items[i].startdate = toDate(item.items[i].startdate);
						item.items[i].enddate = toDate(item.items[i].enddate);
						var planItem = inflate($('.template.planitem', body), item.items[i]);
						$('.plan-items', block).append(planItem);
					}
				})();
			}
			parseUsernames();
			fv.show();
		} else {
			msgbox(L("Can_not_find_any_result"), function() {
				window.history.back();
			});
		}
	});
};
