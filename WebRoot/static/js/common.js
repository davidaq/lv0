var undefined;

function getCookie(c_name) {
	var c_value = document.cookie;
	var c_start = c_value.indexOf(" " + c_name + "=");
	if (c_start == -1)
		c_start = c_value.indexOf(c_name + "=");
	if (c_start == -1)
		c_value = null;
	else {
		c_start = c_value.indexOf("=", c_start) + 1;
		var c_end = c_value.indexOf(";", c_start);
		if (c_end == -1)
			c_end = c_value.length;
		c_value = unescape(c_value.substring(c_start,c_end));
	}
	return c_value;
}

function setCookie(c_name,value,exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	document.cookie = c_name + "=" + c_value;
}

function requestApi(api, send, action) {
    if(action) {
        send = {param : JSON.stringify(send)};
        return request(CFG.apiUrl(api), send, action, "JSON");
    } else {
        return request(CFG.apiUrl(api), send, "JSON");
    }
}
logoAnimateCounter = 0;
function logoAnimate() {
	if(logoAnimateCounter == 0)
		logoAnimate1();
	logoAnimateCounter++;
}
function logoAnimate1() {
	$('a.logo').stop().animate({opacity : 0.3}, 500, logoAnimate2);
}
function logoAnimate2() {
	$('a.logo').stop().animate({opacity : 1}, 500, logoAnimate1);
}
function logoAnimateStop() {
	logoAnimateCounter--;
	if(logoAnimateCounter == 0)
		$('a.logo').stop().animate({opacity : 1}, 500);
}
function request(url, send, action, type) {
    if(!type) {
        if(!action) {
            type = "html";
            action = send;
            send = {};
        } else {
            type = action;
            action = send;
            send = {};
        }
    }
    var method = 'GET';
    if(send) {
        method = 'POST';
        url += "?d=" + new Date().getTime();
    }
    logoAnimate();
    function RetryAjax(url, send, method, action) {
    	this.url = url; this.send = send; this.method = method; this.action = action;
    	this.retry = 5;
		ajax = this;
		this.intent();
    }
    RetryAjax.prototype.error = function(action) {
    	this.errorHandler = action;
    };
    RetryAjax.prototype.intent = function() {
    	var me = this;
		this.ajax = $.ajax({
			url : url,
			type : method,
			data : send,
			dataType : 'text',
			tryCount : 0,
			retryLimit : 20,
			success : function(result) {
				logoAnimateStop();
				if(action) {
				    var reg = /\%\{(.+?)\}/m;
				    while((m = reg.exec(result))) {
				        result = result.replace(m[0], L(m[1]));
				    }
				    if(type == "JSON") {
				    	if(result)
					    	result = JSON.parse(result);
				    }
				    action(result);
				}
			},
			error : function(e,m){
				if(me.retry-- > 0) {
					me.ajax = false;
					me.delay = setTimeout(function() {
						me.intent();
					}, 1000);
				} else {
					logoAnimateStop();
					if(this.errorHandler)
						this.errorHandler();
				}
			}
		});
    }
    RetryAjax.prototype.abort = function() {
    	if(this.ajax)
	    	this.ajax.abort();
	    if(this.delay)
	    	clearTimeout(this.delay);
    }
    return new RetryAjax(url, send, method, action);
}
function initForm(body, data) {
	var popedOver = [];
	$('form', body).each(function() {
		var form = $(this)[0];
		if(data) {
			$('input, textarea').each(function() {
				var name = $(this).attr('name');
				if(data[name]) {
					if($(this).attr('type') == 'checkbox') {
						$(this).attr('checked', true);
					} else
						$(this).val(data[name]);
				}
			});
		}
		if($(this).hasClass('inited'))
			return;
		if($(form).attr('enctype') == 'multipart/form-data') {
			$(form).attr('method', 'post');
			var fname = 'form' + new Date().getTime();
			var frame = '<iframe name="' + fname +'" src="about:blank"></iframe>';
			var isSubmit = false;
			$(frame).appendTo(body).attr('name', fname).css({
				position: 'fixed',
				top: 0,
				right: 0,
				width: '1px',
				height: '1px',
				boder: 'none'
			})[0].onload = function() {
				if(isSubmit)
					refresh();
			};
			setTimeout(function() {
				isSubmit = true;
			}, 1000);
			$(form).attr('target', fname);
		} else {
			form.onsubmit = function() {
				var rawData = $(form).serializeArray();
				var send = {};
				function access(set, path, value) {
					var tmp = set, ret;
					var key;
					path = path.split('.');
					for(key in path) {
						key = path[key];
						if(!tmp[key])
							tmp[key] = {};
						ret = tmp;
						tmp = tmp[key];
					}
					if(value !== undefined) {
						ret[key] = value;
					}
					return tmp;
				}
				for(k in rawData) {
					var key = rawData[k].name;
					if(key.substr(-2) == '[]') {
						key = key.substr(0, key.length - 2);
						if(!access(send, key))
							access(send, key, []);
						access(send, key).push(rawData[k].value);
					} else
						access(send, key, rawData[k].value);
				}
				console.log(send);
				for(k in popedOver) {
					popedOver[k].popover('destroy');
				}
				popedOver = [];
				$('input, textarea', form).css('border-color', '');
				requestApi(form.action, send, function(result) {
					if(result == 'ok') {
						if(form.target) {
							document.location.hash = form.target;
							if($(form).attr('refresh'))
								document.location.reload();
						} else {
							refresh();
						}
					} else {
						var x = $('input[name^="' + result + '"], textarea[name="' + result + '"]', form);
						x.css('border-color', '#F00');
						x.focus();
						if(x[0])
							x[0].select();
						setTimeout(function() {
							x.stop().popover('show');
						}, 200);
						popedOver.push(x);
					}
				});
				return false;
			}
		}
		$(this).addClass('inited');
	});
}
function logout() {
	requestApi('user-logout', function(result) {
		if(result == 'ok') {
			document.location.reload();
		}
	});
}
parsedUsernames = {};
parsedUseravatars = {};
function parseUsernames(element) {
	if(!element)
		element = document;
	var pendingUsername = {};
	$('.username', element).each(function() {
		if($(this).closest('.template')[0])
			return;
		var uid = $(this).attr('title');
		$(this).removeClass('username');
		$(this).attr('title','');
		if(!parsedUsernames[uid]) {
			if(!pendingUsername[uid])
				pendingUsername[uid] = [];
			pendingUsername[uid].push(this);
		} else {
			$(this).html(parsedUsernames[uid]);
		}
		$(this).css('cursor', 'pointer');
		$(this).click(function() {
			show_userinfo(uid * 1);
		});
	});
	for(k in pendingUsername) {
		(function() {
			var uid = k;
			requestApi('User-getUsernameByUid', {userId : uid}, function(result) {
				parsedUsernames[uid] = result;
				$(pendingUsername[uid]).html(result);
			});
		})();
	}
	var pendingAvatars = {};
	$('img.useravatar', element).each(function() {
		if($(this).closest('.template')[0])
			return;
		var uid = $(this).attr('title');
		$(this).removeClass('useravatar');
		$(this).attr('title','');
		if(!parsedUseravatars[uid]) {
			if(!pendingAvatars[uid])
				pendingAvatars[uid] = [];
			pendingAvatars[uid].push(this);
		} else if(parsedUseravatars[uid]) {
			$(this).attr('src', parsedUseravatars[uid]);
		}
		$(this).css('cursor', 'pointer');
		$(this).click(function() {
			show_userinfo(uid * 1);
		});
	});
	for(k in pendingAvatars) {
		(function() {
			var uid = k;
			requestApi('User-getUserPortraitByUid', {uid : uid}, function(result) {
				parsedUseravatars[uid] = result;
				if(result) {
					$(pendingAvatars[uid]).attr('src', result);
				}
			});
		})();
	}
}

function follow_user(uid) {
	requestApi('friends-setAttention', {attedUser : uid}, function(result) {
		if(result == 'ok') {
			msgbox(L('User_followed'));
		}
	});
}


function unfollow_user(uid) {
	requestApi('friends-deleteAttention', {uid : uid}, function(result) {
		if(result == 'ok') {
			msgbox(L('User_follow_removed'));
		}
	});
}

function tour_log_like(pid) {
	requestApi('tourLog-good', {tourLogId : pid}, function(result) {
		console.log(result);
		if(result == 'ok') {
			$('.good.good' + pid).html($('.good.good' + pid).html() * 1 + 1);
		}
	});
}

function refresh() {
	CFG.refresh = true;
}

function msgbox(message) {
	$('#alertDlg .modal-body').html(message);
	$('#alertDlg').modal();
}
function ask(message, action) {
	$('#confirmDlg .modal-body').html(message);
	$('#confirmDlg').modal();
	$('#confirmDlg #ok')[0].onclick = action;
}

function inflate(template, data) {
	var content = template.html();
	for(i in data)
		content = content.replace(new RegExp('%' + i + '%','g'), data[i]);
	return content;
}
