scripts.feed = function(param, body) {
	var fv = new FlowView(body);
	var page = 1;
	fv.load(function() {
		requestApi('tourLog-getTourLog', {pageNow : page++}, function(result) {
			for(k in result) {
				var content = $('.template', body).html();
				for(i in result[k])
					content = content.replace(new RegExp('%' + i + '%','g'), result[k][i]);
				fv.addBlock(content);
			}
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
