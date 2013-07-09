scripts.profile = function(param, body) {
	var map = {
		username : 'uname',
		email : 'umail'
	};
	$('input', body).each(function() {
		var name = $(this).attr('name');
		if(name && map[name]) {
			$(this).val(CFG.userinfo[map[name]]);
		}
	});
};
function change_avatar(item) {
	$(item).prev('img').attr('src', $(item).val());
}
