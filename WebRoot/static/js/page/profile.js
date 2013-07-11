scripts.profile = function(param, body) {
	var map = {
		username : 'uname',
		email : 'umail',
		phone : 'uphone',
		label : 'ulabel',
		sketch : 'usketch',
		portrait : 'uportrait'
	};
	$('input,textarea', body).each(function() {
		var name = $(this).attr('name');
		if(name && map[name]) {
			$(this).val(CFG.userinfo[map[name]]);
		}
		if(name == 'portrait') {
			change_avatar(this);
		}
	});
};
function change_avatar(item) {
	$(item).prev('img').attr('src', $(item).val());
}
