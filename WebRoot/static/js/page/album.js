scripts.album = function(param, body) {
	var fv = new FlowView(body);
	fv.show();
	$('#createAlbumBtn').click(function() {
		var name = $(this).prev('input').val();
		
	});
};
