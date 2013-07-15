scripts.album = function(param, body) {
	var fv = new FlowView(body);
	var uId = param ? param : CFG.userinfo.uid;
	$('h1 .username', body).attr('title', uId);
	parseUsernames();
	if(uId != CFG.userinfo.uid) {
		$('#createAlbumBtn', body).parent().remove();
	}
	requestApi('album-getAlbumsByUserId', {uId : uId}, function(result) {
		for(k in result) {
			(function() {
				var item = result[k];
				if(!item.mediaCover) {
					item.mediaCover = 'static/images/album_default.jpg';
				}
				var content = inflate($('.template', body), item);
				var block = fv.addBlock(content);
				$('img', block).click(function() {
					document.location.hash = 'albumview%' + item.mediaId;
				});
				$('button', block).click(function() {
					var dlg = $('#albumEditDlg', body);
					dlg.modal();
					$('h3', dlg).html(L('Edit') + ' : ' + item.mediaName);
					initForm(dlg, item);
					$('#del', dlg)[0].onclick = function() {
						ask(L('Do_you_really_want_to_delete_this_item'), function() {
							$(dlg).modal('hide');
							requestApi('album-deleteAlbum', {albumId : item.mediaId}, refresh);
						});
					};
				});
			})();
		}
		fv.show();
	});
	$('#createAlbumBtn').click(function() {
		var name = $(this).prev('input').val();
		requestApi('album-addAlbum', {albumName : name}, function(result) {
			if(result == 'ok') {
				refresh();
			}
		});
	});
};
