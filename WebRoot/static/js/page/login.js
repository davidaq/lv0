scripts['login'] = function(param, body) {
	var image = document.createElement('img');
	function loadSpam() {
		image.src = 'spam-code.action?' + new Date().getTime();
	}
	image.onclick = loadSpam;
	loadSpam();
	$('#spamcode').html(image);
}
