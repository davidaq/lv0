scripts.albumview = function(param, body) {
	param =  {albumId : param};
	$('.upload-new').hide();
	requestApi('album-getAlbumById', param, function(result) {
		$('h1 .username', body).attr('title', result.uid);
		parseUsernames();
		$('h1 .albumName', body).html(" :: " + result.mediaName);
		if(result.uid != CFG.userinfo.uid) {
			$('.upload-new').remove();
		} else {
			$('.upload-new').show();
			initForm(body, param);
		}
		var fv = new FlowView(body);
		fv.show();
		requestApi('album-getMediasByAlbumId', param, function(result) {
			for(k in result) {
				(function() {
					var item = result[k];
					var block = inflate($('.template', body), item);
					block = fv.addBlock(block);
					$('img', block).error(function() {
						if($(this).attr('src') != item.address)
							$(this).attr('src', item.address);
					}).click(function() {
						$('#media-detail img').attr('src', item.address);
						$('#media-detail .extra').html(item.mediaAbstract + '<br/><a href="' + item.address + '" target="displayImage">' + L('Full_image') + '</a>');
						$('#media-detail').modal();
					});
					$('button', block).click(function() {
						requestApi('album-deleteMedia', {"mediaId" : item.mediaContentId}, function(result) {
							if(result == 'ok')
								$(block).fadeOut(200, function() {
									$(this).show().css('visibility', 'hidden');
								})
						});
					});
				})();
			}
			fv.show();
		});
	});
};
