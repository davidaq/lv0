scripts.wikipage = function(param, body) {
	requestApi('resort-getResortById', {resortId : param}, function(resort) {
		$('.wiki_title', body).html(resort.resName);
		var thumb = document.createElement('img');
		thumb.className = 'thumb';
		thumb.src = resort.resPicture;
		thumb.onerror = function() {
			thumb.src = 'static/images/album_default.jpg';
		};
		var content = '<p><b>景点名称：</b>' + resort.resName +
						'</p><p><b>景点地址：</b>' + resort.resAddress +
						'</p><p><b>景点标签：</b>' + resort.resLabel;
		$('.basic', body).html(content).prepend(thumb);
		requestApi('resort-getRessupplementsByResortId', {resortId : param}, function(result) {
			for(k in result) {
				(function() {
					var item = result[k];
					var content = inflate($('.template', body), item);
					$('.wikipage', body).append(content);
				})();
			}
		});
	});
	//$('#textarea#wiki_content')
	KindEditor.create('textarea#wiki_content', {
		width : '100%',
		allowImageUpload : false,
		resizable : false,
		items : [
			'fontname', 'fontsize', '|', 'justifyleft', 'justifycenter', 'justifyright', '|',
			'forecolor', 'hilitecolor', 'bold', 'italic', 'underline','removeformat', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link']
	});
	$('#add_supplement').click(function() {
		$('#wiki_editor').attr('action', 'Resort-addRessupplement');
		initForm(body, {ressupplement : {resortId : param}});
		$('#wiki_editor').modal();
	});
};
