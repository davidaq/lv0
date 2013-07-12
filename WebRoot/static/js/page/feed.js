scripts.feed = function(param, body) {
	var fv = new FlowView(body);
	var page = 1;
	fv.load(function() {
		requestApi('tourLog-getTourLog', {pageNow : page}, function(result) {
			if(result && result.length > 0) {
				for(k in result) {
					(function() {
						var item = result[k];
						var content = $('.template', body).html();
						for(i in item)
							content = content.replace(new RegExp('%' + i + '%','g'), item[i]);
						
						var element = fv.addBlock(content, item.abstract_.length > 50);
						$('.text', element).click(function() {
							document.location.hash = '#tourlog%' + item.tourLogId;
						});
						if(result[k].author != CFG.userinfo.uid) {
							$('.remove', element).remove();
						} else {
							$('.remove', element).click(function() {
								var me = this;
								requestApi('tourLog-deleteTourLogById', {tourLogId : item.tourLogId}, function() {
									$(me).closest('.block').fadeOut(200, function() {	
										$(this).show();
										$(this).css('visibility', 'hidden');
									});
								});
							});
						}
					})();
				}
				page++;
			}
			parseUsernames(fv.fv);
			fv.show();
		});
	});
	fv.show();
	KindEditor.create('textarea#post_content', {
		allowImageUpload : false,
		resizable : false,
		items : [
			'fontname', 'fontsize', '|', 'justifyleft', 'justifycenter', 'justifyright', '|',
			'forecolor', 'hilitecolor', 'bold', 'italic', 'underline','removeformat', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link']
	});
};
