
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
    var method = $.get;
    if(send)
        method = $.post;
    return method(url, send, function(result) {
        if(action) {
            var reg = /\%\{(.+?)\}/m;
            while((m = reg.exec(result))) {
                result = result.replace(m[0], L(m[1]));
            }
            action(result);
        }
    }, type).error(function(e,m){console.log(url + " : " + m);});
}