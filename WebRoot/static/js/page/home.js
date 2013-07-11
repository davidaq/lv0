scripts.home = function(param, body) {
	var fv = new FlowView(body);
	fv.show();
	requestApi('home-getTop10', function(result) {
		for(k in result) {
			(function() {
				var item = result[k][0];
				var content = $('.template', body).html();
				for(i in item)
					content = content.replace(new RegExp('%' + i + '%','g'), item[i]);
				var element = fv.addBlock(content, item.abstract_.length > 100);
				$('.text', element).click(function() {
					$('#detailDlg .modal-body').html(item.content);
					$('#detailDlg').modal();
				});
				if(result[k].author != CFG.userinfo.uid) {
					$('.remove', element).remove();
				} else {
					$('.remove', element).click(function() {
						var me = this;
						requestApi('', function() {
							$(me).closest('.block').fadeOut(200, function() {	
								$(this).show();
								$(this).css('visibility', 'hidden');
							});
						});
					});
				}
			})();
		}
	});
};
