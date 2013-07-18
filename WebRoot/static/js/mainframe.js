var scripts = {};
$(function () {
    var lang = getCookie('lang');
    if(!lang)
        lang = 'default';
    requestApi('user-getMyUserinfo', function(result) {
    	CFG.userinfo = result;
		requestApi('i18n-dictionary', {'lang':lang}, function(result) {
			CFG.dict = result;
		    request('main.html', function(result) {
		        $('body').html(result);
		        initUsername();
		        startHashRoute();
		        backgroundControl();
		        setLanguageList();
		        loadTabs();
		        initMenu();
		    });
		});
    	
    });
    setInterval(function() {
		requestApi('user-getMyUserinfo', function(result) {
			CFG.userinfo = result;
		});
    }, 30000);
    function initUsername() {
    	if(!CFG.userinfo) {
	    	$('#username, #messageBar').hide();
	    } else {
	    	$('#username span').html(CFG.userinfo.uname);
    		setInterval(fetchMessage, 5000);
    	}
    }
    function initMenu() {
		$('ul li').hover(function() {
			$(this).find('> ul.menu').show();
		}, function() {
			$(this).find('> ul.menu').hide();
		});
    }

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
            if(CFG.refresh || curHash != document.location.hash) {
                var url = document.location.hash.substr(1);
                var pos = url.indexOf('%');
                var param;
                if(pos > -1) {
                    param = url.substr(pos + 1);
                    url = url.substr(0, pos);
                }
                if(CFG.ann_allowed.indexOf(url) == -1 && !CFG.userinfo) {
                	document.location.hash = "login";
                	return;
                }
                if(loader)
                    loader.abort();
                if(scriptWaiter) {
                    clearInterval(scriptWaiter);
                    scriptWaiter = false;
                }
                function showPage(result) {
                    var display = function () {
                    	$('.modal').modal('hide');
                        var item = document.createElement('div');
                        item.className = 'content';
                        item.innerHTML = result;
                        if(CFG.refresh) {
                        	$('.foreground .body').html('');
                        }
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
	                    initForm(item);
	                    if($('h1:first', item)[0])
	                        document.title = $('h1:first', item).text() + " | " + bareTitle;
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
                    loader = request('page/' + url + '.html', function(result) {
                        pageCache[url] = result;
                        showPage(result);
                    });
                }
                curHash = document.location.hash;
            	CFG.refresh = false;
            }
        }, 100);
    }

    function loadTabs() {
        requestApi('home-tabs', function(result) {
            for(k in result) {
                var element = document.createElement('a');
                element.innerHTML = '<i class="icon-' + result[k].icon + ' white"></i> ' + result[k].title;
                element.className = 'link';
                element.href = '#' + result[k].url;
                $('.head').prepend(element);
            }
            if(result[0] && (document.location.hash == '' || document.location.hash == '#')) {
            	document.location.hash = result[0].url;
            }
        });
    }

    function setLanguageList() {
        requestApi('i18n-list', function(result) {
            for(k in result) {
                var element = document.createElement('li');
                element.innerHTML = result[k].title;
                (function() {
                	var name = result[k].name;
		            element.onclick = function() {
		            	setCookie('lang', name);
		            	document.location.reload();
		            };
                })();
                $('#languageList').prepend(element);
            }
        });
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
        requestApi('home-backgrounds', function(result) {
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
        });
    }
    var currentMessageId = 0;
    var messageCount = 0;
    var messageGotten = {};
    function fetchMessage() {
    	requestApi('Message-getMessage', {least : currentMessageId}, function(result) {
    		if(result && result.length) {
    			var len = 0;
				$('#messageBar .counter').html(messageCount);
				var aniTo = {
					opacity : 0.1
				};
				var aniFro = {
					opacity : 1
				};
				$('#messageBar .counter').animate(aniTo, 200).animate(aniFro, 200).animate(aniTo, 200).animate(aniFro, 200);
    			for(k in result) {
    				(function() {
    					var item = result[k];
    					if(messageGotten[item.messageId])
    						return;
    					len++;
						messageGotten[item.messageId] = 1;
    					var li = document.createElement('li');
    					li.innerHTML = '<b><span class="username" title="' + item.authorId + '"></span></b>: ';
    					var content = document.createElement('span');
    					content.innerHTML =  item.messContent;
    					content.onclick = function() {
							$('.modal#msgDlg').modal();
							$('.modal#msgDlg .touser').html('<span class="username" title="' + item.authorId + '"></span>');
							parseUsernames();
							$('.modal#msgDlg .btn-primary')[0].onclick = function() {
								requestApi('Message-sendMessage', {message:{
									messContent : $('.modal#msgDlg #msgtext').val(),
									targetId : item.authorId
								}}, function(result) {
									if(result == 'ok') {
										$('.modal#msgDlg #msgtext').val('');
										$('.modal#msgDlg').modal('hide');
									}
								});
							};
    					};
    					$(li).append(content);
    					var delBtn = document.createElement('button');
    					delBtn.className = 'close';
    					delBtn.innerHTML = '<i class="icon-remove white"></i>';
    					delBtn.onclick = function() {
    						requestApi('message-deleteMessageById', {messageId:item.messageId}, function(result) {
    							if(result == 'ok') {
    								messageCount--;
									$('#messageBar .counter').html(messageCount);
									$(li).slideUp(200, function() {
										$(this).remove();
									});
    							}
    						});
    					};
    					$(li).append(delBtn);
    					$('#messageBar .menu').append(li);
    				})();
    			}
    			messageCount += len;
    			parseUsernames();
    			currentMessageId = result[result.length - 1].messageId;
    		}
    	});
    }
});
var showFriendsFlag = false;
function showFriends() {
    if(!showFriendsFlag) {
        showFriendsFlag = true;
        $('.foot .extra').html(L('loading'));
        $('.foot .extra').stop().slideDown(100);
        requestApi('friends-list', function(result) {
            $('.foot .extra').hide();
            $('.foot .extra').html('');
            for(k in result) {
                var item = document.createElement('div');
                item.className = 'friend-list-item friend-list-item' + result[k].uid;
                
                if(result[k].online) {
                    item.className += ' online';
                } else {
                    item.className += ' offline';
                }
                if(!result[k].avatar)
                	result[k].avatar = 'static/images/picmi.png';
                item.innerHTML = '<img src="' + result[k].avatar + '"/> ' + result[k].name;

                (function() {
                	var info = result[k];
		            $(item).click(function() {
		            	show_userinfo(info.uid * 1);
		            });
	            })();
                
                $('.foot .extra').append(item);
            }
            $('.foot .extra').slideDown(100);
        }).error(function() {
            $('.foot .extra').stop().hide();
            showFriendsFlag = false;
        });
    } else {
        showFriendsFlag = false;
        $('.foot .extra').stop().slideUp(200);
    }
}

function show_userinfo(user) {
	function display(info) {
		if(!info.uportrait)
			info.uportrait = 'static/images/picmi.png';
		$('#userinfo h1').html('<img src="' + info.uportrait + '" style="max-width:100px; max-height: 100px"/> ' + info.uname);
		$('#userinfo .phone').html(info.uphone);
		$('#userinfo .intro').val(info.usketch);
		$('#userinfo .tourlist').attr('href', '#tourlist%' + info.uid);
		$('#userinfo .album').attr('href', '#album%' + info.uid);
		$('#userinfo button.sendmsg')[0].onclick = function() {
			$('#userinfo').modal('hide');
			$('.modal#msgDlg').modal();
			$('.modal#msgDlg .touser').html(info.uname);
			$('.modal#msgDlg .btn-primary')[0].onclick = function() {
				requestApi('Message-sendMessage', {message:{
					messContent : $('.modal#msgDlg #msgtext').val(),
					targetId : info.uid
				}}, function(result) {
					if(result == 'ok') {
						$('.modal#msgDlg #msgtext').val('');
						$('.modal#msgDlg').modal('hide');
					}
				});
			};
		};
		$('#userinfo .follow')[0].onclick = function() {
			follow_user(info.uid);
		};
		$('#userinfo .unfollow')[0].onclick = function() {
			unfollow_user(info.uid);
		};
		$('#userinfo').modal();
	}
	if(typeof user == 'number') {
		requestApi('user-getUserinfoByUid', {userId : user}, function(result) {
			display(result);
		});
	} else {
		display(user);
	}
}
