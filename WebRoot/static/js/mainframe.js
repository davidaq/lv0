var scripts = {};
$(function () {
	startHashRoute();
	backgroundControl();
	loadTabs();

	function startHashRoute() {
		var curHash;
		var loader;
		var scriptWaiter;
		var bareTitle = document.title;
		var switching;
		var pageCache = {};
		setInterval(function() {
			if(switching)
				return;
			if(document.location.hash == '#index' || document.location.hash == '' || document.location.hash == '#')
				document.location.hash = '#home';
			if(curHash != document.location.hash) {
				var url = document.location.hash.substr(1);
				var pos = url.indexOf('%');
				var param;
				if(pos > -1) {
					param = url.substr(pos + 1);
					url = url.substr(0, pos);
				}
				if(loader)
					loader.abort();
				if(scriptWaiter) {
					clearInterval(scriptWaiter);
					scriptWaiter = false;
				}
				function showPage(result) {
					var display = function () {
						var item = document.createElement('div');
						item.className = 'content';
						item.innerHTML = result;
						$('.foreground .body').append(item);
						if($('.foreground .body > .content')[1]) {
							switching = true;
							$('.foreground .body .content:first').fadeOut(300, function() {
								$(this).remove();
								switching = false;
								loader = false;
							});
							$(item).fadeOut(0).fadeIn(300);
						}
						scripts[url](param, item);
						if($('h1:first', item)[0])
							document.title = bareTitle + " | " + $('h1:first', item).text();
						else
							document.title = bareTitle;
					}
					if(!scripts[url]) {
						scripts[url] = true;
						var script = document.createElement('script');
						script.src = 'static/js/page/' + url + '.js';
						script.type = 'text/javascript';
						$('head').append(script);
						scriptWaiter = setInterval(function() {
							if(typeof scripts[url] == 'function') {
								clearInterval(scriptWaiter);
								scriptWaiter = false;
								display();
							}
						}, 100);
					} else if(typeof scripts[url] == 'function') {
						display();
					}
				}
				if(pageCache[url])
					showPage(pageCache[url]);
				else {
					loader = $.get(url + '.html', function(result) {
						pageCache[url] = result;
						showPage(result);
					}, 'html');
				}
				curHash = document.location.hash;
			}
		}, 100);
	}

	function loadTabs() {
		$.get(CFG.apiUrl('home-tabs'), function(result) {
			for(k in result) {
				var element = document.createElement('a');
				element.innerHTML = '<i class="icon-' + result[k].icon + ' white"></i> ' + result[k].title;
				element.className = 'link';
				element.href = '#' + result[k].url;
				$('.head').prepend(element);
			}
		}, "JSON");
	}

	function backgroundControl() {
		var backgrounds = [];
		var currentBackground;
		var switchingBackground;
		function applyBackground(id, noani) {
			if(!switchingBackground && backgrounds[id]) {
				var loader = document.createElement('img');
				loader.src = backgrounds[id].url;
				currentBackground = id;
				setCookie('background', id, 100);
				if(!backgrounds[id].loaded) {
					loader.onload = function() {
						backgrounds[id].loaded = true;
						applyBackground(currentBackground, noani);
					}
				} else if(noani) {
					$('.background div').css('background-image', 'url(' + backgrounds[id].url + ')');
				} else {
					$('.background').css('background-image', $('.background div').css('background-image'));
					$('.background div').fadeOut(0);
					$('.background div').css('background-image', 'url(' + backgrounds[id].url + ')');
					switchingBackground = true;
					$('.background div').fadeIn(400, function() {
						switchingBackground = false;
					});
				}
			}
		}
		$.get(CFG.apiUrl('home-backgrounds'), function(result) {
			backgrounds = result;
			var bg = getCookie('background');
			if(!bg)
				bg = 0;
			applyBackground(bg * 1, true);
			$('#backgrounds-list').html('');
			for(k in backgrounds) {
				var item = document.createElement('li');
				item.innerHTML = backgrounds[k].title;
				(function() {
					var id = k;
					item.onclick = function() {
						applyBackground(id);
					};
				})();
				$('#backgrounds-list').append(item);
			}
		}, "JSON");
	}
});
var showFriendsFlag = false;
function showFriends() {
	if(!showFriendsFlag) {
		showFriendsFlag = true;
		$('.foot .extra').html('正在加载。。。');
		$('.foot .extra').stop().slideDown(100);
		$.get(CFG.apiUrl('friends-list'), function(result) {
			$('.foot .extra').hide();
			$('.foot .extra').html('');
			for(k in result) {
				var item = document.createElement('div');
				item.className = 'friend-list-item';
				var flagIcon;
				if(result[k].relation == 'friend') {
					flagIcon = 'friend';
				} else {
					flagIcon = 'watch';
				}
				if(result[k].online) {
					flagIcon += '-online';
				} else {
					flagIcon += '-offline';
				}
				item.innerHTML = '<img src="' + result[k].avatar + '"/> ' + result[k].name + ' <img src="static/images/' + flagIcon + '.png"/> ';
				console.log(result[k]);
				$('.foot .extra').append(item);
			}
			$('.foot .extra').slideDown(100);
		}, 'JSON').error(function(e,m) {
			$('.foot .extra').stop().hide();
			showFriendsFlag = false;
		});
	} else {
		showFriendsFlag = false;
		$('.foot .extra').stop().slideUp(200);
	}
}
