scripts.feed = function(param, body) {
	var fv = new FlowView(body);
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
