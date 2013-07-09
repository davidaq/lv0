
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
    if(send)
        method = 'POST';
    function logoAnimate1() {
    	$('a.logo').stop().animate({opacity : 0.3}, 500, logoAnimate2);
    }
    function logoAnimate2() {
    	$('a.logo').stop().animate({opacity : 1}, 500, logoAnimate1);
    }
    function logoAnimateStop() {
    	$('a.logo').stop().animate({opacity : 1}, 500);
    }
    logoAnimate1();
    return $.ajax({
    	url : url,
    	type : method,
    	data : send,
    	dataType : 'text',
    	tryCount : 0,
    	retryLimit : 5,
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
    		logoAnimateStop();
    	}
    });
}
function initForm(body) {
	var popedOver = [];
	$('form', body).each(function() {
		var form = $(this)[0];
		form.onsubmit = function() {
			var rawData = $(form).serializeArray();
			var send = {};
			for(k in rawData) {
				var key = rawData[k].name;
				if(key.substr(-2) == '[]') {
					key = key.substr(0, key.length - 2);
					if(!send[key])
						send[key] = [];
					send[key].push(rawData[k].value);
				} else
					send[key] = rawData[k].value;
			}
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
						document.location.reload();
					}
				} else {
					console.log(result);
					var x = $('input[name^="' + result + '"], textarea[name="' + result + '"]', form);
					x.css('border-color', '#F00');
					x.focus();
					x[0].select();
					setTimeout(function() {
						x.stop().popover('show');
					}, 200);
					popedOver.push(x);
				}
			});
			return false;
		}
	});
}
function logout() {
	requestApi('user-logout', function(result) {
		if(result == 'ok') {
			document.location.reload();
		}
	});
}
