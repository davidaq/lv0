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
					content = $(content).appendTo($('.wikipage', body));
					content.find('button.del').click(function() {
						requestApi('resort-deleteRessupplement', {resSupplementId : item.resSupplementId}, function(result) {
							if(result == 'ok') {
								content.slideUp(300, function() {
									$(this).remove();
								});
							}
						});
					});
					content.find('button.edit').click(function() {
						$('#wiki_editor').attr('action', 'Resort-editResspupplement');
						initForm(body, {ressupplement : {
							resortId : param,
							resSupplementId : item.resSupplementId,
							resHeadline : item.resHeadline,
							resContent : item.resContent
						}});
						if(editor)
							editor.html(item.resContent);
						$('#wiki_editor').modal();
					});
				})();
			}
		});
	});
	//$('#textarea#wiki_content')
	var editor = KindEditor.create('textarea#wiki_content', {
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
