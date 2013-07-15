scripts.album = function(param, body) {
	var fv = new FlowView(body);
	fv.show();
	$('#createAlbumBtn').click(function() {
		var name = $(this).prev('input').val();
		requestApi('album-addAlbum', {albumName : name}, function(result) {
			if(result == 'ok') {
				refresh();
			}
		});
	});
};
