 
/**
 *Base.js 提供统一的接口，与具体的功能无关
 */
/**
 * 全局变量对象
 */
var MECHAT = {};
/**
 * 定义命名空间的变量
 */
MECHAT.namespace = function(str) {
    var arr = str.split("."),
        o = MECHAT;
    for (i = (arr[0] == "MECHAT") ? 1 : 0; i < arr.length; i++) {
        o[arr[i]] = o[arr[i]] || {};
        o = o[arr[i]];
    }
};
/**
 * Dom相关
 */
MECHAT.namespace("Dom"); //定义命名空间
MECHAT.Dom.setOpacity = function(node, level) {
    if (document.all) {
        node.style.filter = 'alpha(opacity=' + level + ')';
    } else {
        node.style.opacity = level / 100;
    }
};
/**
 * 获取节点
 * node 节点对象(#id,.class,element) 字符类型
 * parent 节点的上层对象
 */
MECHAT.Dom.get = function(node, parent) {
    parent = parent || document;
    if (node.indexOf("#") > -1) { //Id类型
        node = node.replace("#", "");
        node = parent.getElementById(node);
    } else if (node.indexOf(".") > -1) { //class类型
        var tag = "*",
            arr = [];
        if (node.indexOf(".") != 0) {
            var sp = node.split(".");
            tag = sp[0];
            node = sp[1];
        } else {
            node = node.replace(".", "");
        }
        var els = parent.getElementsByTagName(tag);
        for (var i = 0, n = els.length; i < n; i++) {
            for (var j = 0, k = els[i].className.split(" "), l = k.length; j < l; j++) {
                if (k[j] == node) {
                    arr.push(els[i]);
                    break;
                }
            }
        }
        node = arr;
    } else { //节点名称
        node = parent.getElementsByTagName(node);
    }
    return node;
};
/**
 * 对象的隐藏与显示
 */
MECHAT.Dom.show = function(node) {
    node.style.display = "block";
};
MECHAT.Dom.hide = function(node) {
    node.style.display = "none";
};
/**
 * 动态加载JS
 */
MECHAT.Dom.loadScript = function(url) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = url;
    document.body.appendChild(script);
};
/**
 * 动态加载css
 */
MECHAT.Dom.loadStyle = function(css) {
    var style = document.createElement("style");
    style.type = "text/css";
    try {
        style.appendChild(document.createTextNode(css));
    } catch (ex) {
        style.styleSheet.cssText = css;
    }
    var head = document.getElementsByTagName("head")[0];
    head.appendChild(style);
};

/**
 * style样式设置
 * @Param el 节点对象
 * @param attr css属性
 * @param style 对应属性的样式
 */
MECHAT.Dom.css = function(el, attr, style) {
    el.style[attr] = style;
};
/**
 * 删除class
 */
MECHAT.Dom.rmClass = function(el, cls) {
    var c = el.getAttribute("class") || '';
    if (c) {
        c = c.replace(cls, '');
        el.setAttribute('class', c);
    }
};
MECHAT.Dom.addClass = function(el, cls) {
    var c = el.getAttribute("class") || '';
    c += " " + cls;
    el.setAttribute('class', c);
};
/**
 * Bower 浏览器相关
 */
MECHAT.namespace("Broswer"); //定义命名空间
MECHAT.Broswer = {
    IE6: "msie 6",
    IE7: "msie 7",
    IE8: "msie 8",
    IE9: "msie 9",
    IE10: "msie 10",
    IE11: "msie 11",
    Chrome: "chrome",
    Firefox: "firefox",
    Opera: "opera",
    Safari: "safari",
    Netscape: "netscape"
};
//获取浏览器类型
MECHAT.Broswer.get = function() {
    var name = "netscape";
    var ag = navigator.userAgent.toLowerCase();
    if (/(msie|firefox|opera|chrome|netscape)\D+(\d[\d.*])/.test(ag)) {
        var n = RegExp.$1;
        var v = parseInt(RegExp.$2);
        name = n;
        if (n == "msie") {
            name += " " + v;
        }
    } else if (/version\D+(\d[\d.]*).*safari/.test(ag)) {
        name = "safari";
    }
    return name;
};
MECHAT.Broswer.isIE = function() {
    return ("ActiveXObject" in window);
};


MECHAT.Broswer.parseQuerystring = function(str) {
    if ('string' !== typeof str) {
        return {};
    }
    str = MECHAT.Lang.trim(str);
    if ('' === str) {
        return {};
    }

    var obj = {};
    try {
        var pairs = str.split('&');
        for (var i = 0; i < pairs.length; i++) {
            var parts = pairs[i].split('=');
            if (parts[0] && parts[1]) {
                obj[parts[0]] = decodeURIComponent(parts[1])
            }
        }
    } catch (ex) {

    }

    return obj;
};

MECHAT.Broswer.mc_getReferrer = function() {
    var ret = '';

    // first, lookup at url query string
    if (location.search.length > 1) {
        var query_str = location.search.replace('?', '');
        var query = this.parseQuerystring(query_str);
        if (query['_realref_']) {
            ret = query['_realref_'];
            return ret;
        }
    }

    // second, lookup at meta tag
    var metaRealRef = document.getElementById('_realref_');
    if (metaRealRef) {
        ret = metaRealRef.getAttribute('realref') || '';

        if (ret.length > 0) return ret;
    }
    // third, use js document.referrer
    return document.referrer;
};

/**
 * 验证是否是苹果机
 */
MECHAT.Broswer.isApple = function() {
    if ((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPad/i))) {
        if (document.cookie.indexOf("iphone_redirect=false") == -1) {
            return true;
        }
    }
    return false;
};
/**
 * 获取url中的主域名
 */
MECHAT.Broswer.getPriDomain = function(url) {
    var host = "null";
    if (typeof url == "undefined" || null == url) {
        url = window.location.href;
    }
    var regex = /.*\:\/\/([^\/]*).*/;
    var match = url.match(regex);
    if (typeof match != "undefined" && null != match)
        host = match[1];
    if (host.indexOf(".") > 0) {
        var arr = host.split(".");
        var len = arr.length;
        if (len > 2) {
            host = arr[len - 2] + "." + arr[len - 1];
        }
    }
    return host;
};
/**
 * Event相关
 */
MECHAT.namespace("Event"); //定义命名空间
MECHAT.Event.getEventTarget = function(e) {

};
MECHAT.Event.stopPropagation = function(e) {
    if (e.stopPropagation) {
        e.stopPropagation();
    } else {
        e.cancelBubble = true;
    }
};
MECHAT.Event.on = function(node, eventType, handler) {
    node = typeof node == "string" ? document.getElementById(node) : node;
    if (node.addEventListener) {
        node.addEventListener(eventType, handler, false);
    } else if (node.attachEvent) {
        node.attachEvent("on" + eventType, handler);
    } else {
        node["on" + eventType] = handler;
    }
};
MECHAT.Event.defautChnage = function(el, type, cb) {
    el.onfocus = function() {
        var val = el.defaultValue;
        var str = '';
        if (type == 'div') {
            str = el.innerHTML;
        } else if (type == 'input') {
            str = el.value;
        }
        str = MECHAT.Lang.trim(str);

        if (!val || val == str) {
            el.defaultValue = str;
            if (type == 'div') {
                el.innerHTML = '';
            } else if (type == 'input') {
                el.value = '';
            }
        }
        //设置文字的颜色
        el.setAttribute("style", "color:#333;");
        if (cb) {
            cb();
        }
    }
    el.onblur = function() {
        var str = '';
        if (type == 'div') {
            str = el.innerHTML;
        } else if (type == 'input') {
            str = el.value;
        }
        str = MECHAT.Lang.trim(str);
        if (str.length == 0 || str == '<br>') {
            if (type == 'div') {
                el.innerHTML = el.defaultValue;
                el.setAttribute("style", "color:#999;");

            } else if (type == 'input') {
                el.value = el.defaultValue;
                el.setAttribute("style", "color:#999;");

            }
        }

    }
};
/**
 * Lang相关
 */
MECHAT.namespace("Lang"); //定义命名空间

MECHAT.Lang = {
    telRegFatory: function(reg) {
        return [
            [RegExp('^' + reg + '$', 'gm'), "<a target='_blank' href=\"tel:$1\">$1</a>"],
            [RegExp('^' + reg + '(\\D)', 'gm'), "<a target='_blank' href=\"tel:$1\">$1</a>$2"],
            [RegExp('(\\D)' + reg + '$', 'gm'), "$1<a target='_blank' href=\"tel:$2\">$2</a>"],
            [RegExp('(\\D)' + reg + '(\\D)', 'gm'), "$1<a target='_blank' href=\"tel:$2\">$2</a>$3"]
        ]
    },
    regUrl: [
        [/((ftp:\/\/)[^\u4E00-\u9FA5 ]+)/ig, "<a target='_blank' href=\"$1\">$1</a>"],
        [/((http:\/\/|https:\/\/)[^\u4E00-\u9FA5 ]+)/ig, "<a target='_blank' href=\"$1\">$1</a>"],
        [/((www\.)[^\u4E00-\u9FA5 ]+)/ig, "<a target='_blank' href=\"http://$1\">$1</a>"],

        [/([\.A-Za-z0-9-]+?\.((com)|(net)|(im)|(cn)|(me)|(org)|(edu)|(cc)|(biz)|(info)|(tv)|(co)|(so)|(tel)|(mobi))[/?#][^\u4E00-\u9FA5， ]+)/img, "<a target='_blank' href=\"http://$1\">$1</a>"],
        [/([\.A-Za-z0-9-]+?\.((com)|(net)|(im)|(cn)|(me)|(org)|(edu)|(cc)|(biz)|(info)|(tv)|(co)|(so)|(tel)|(mobi))[/?#])/img, "<a target='_blank' href=\"http://$1\">$1</a>"],
        [/([\.A-Za-z0-9-]+?\.((com)|(net)|(im)|(cn)|(me)|(org)|(edu)|(cc)|(biz)|(info)|(tv)|(co)|(so)|(tel)|(mobi)))([0-9\s\u4E00-\u9FA5，]+)/img, "<a target='_blank' href=\"http://$1\">$1</a>$18"],
        [/([\.A-Za-z0-9-]+?\.((com)|(net)|(im)|(cn)|(me)|(org)|(edu)|(cc)|(biz)|(info)|(tv)|(co)|(so)|(tel)|(mobi)))$/img, "<a target='_blank' href=\"http://$1\">$1</a>"]
    ],
    regTel: [],

    regEmail: [
        [/([A-Z0-9\._%+-]+@[A-Z0-9\.-]+\.[A-Z]{2,15})/img, '<a href="mailto:$1">$1</a>']
    ]
};

MECHAT.Lang.regTel = MECHAT.Lang.regTel.concat(MECHAT.Lang.telRegFatory('([1]\\d{10})'));
MECHAT.Lang.regTel = MECHAT.Lang.regTel.concat(MECHAT.Lang.telRegFatory('([48]00\\d?-?\\d{3,4}-?\\d{3,4})'));
MECHAT.Lang.regTel = MECHAT.Lang.regTel.concat(MECHAT.Lang.telRegFatory('(0\\d{2,3}-?\\d{7,8})'));
MECHAT.Lang.regTel = MECHAT.Lang.regTel.concat(MECHAT.Lang.telRegFatory('(\\d{8})'));


MECHAT.Lang.replace = function(text, regexp) {
    var ele = document.createElement('div');
    ele.innerHTML = text;
    for (var i in ele.childNodes) {
        var el = ele.childNodes[i];

        if (el.nodeType == 3) {
            var ele2 = document.createElement('em');
            ele2.appendChild(el.cloneNode(true));

            var oldHtml = ele2.innerHTML;
            ele2.innerHTML = ele2.innerHTML.replace(regexp[0], regexp[1]);
            if (ele2.innerHTML != oldHtml) {
                ele.replaceChild(ele2, el);
            }
        }
    }
    return ele.innerHTML.replace(/\<em\>/g, '').replace(/\<\/em\>/g, '');
};
MECHAT.Lang.replaceTel = function(text) {
    for (var i in this.regTel) {
        text = this.replace(text, this.regTel[i]);
    }
    return text;
};
MECHAT.Lang.replaceUrl = function(text) {
    for (var i in this.regUrl) {
        text = this.replace(text, this.regUrl[i]);
    }
    return text;
};
MECHAT.Lang.replaceEmail = function(text) {
    for (var i in this.regEmail) {
        text = this.replace(text, this.regEmail[i]);
    }
    return text;
};
MECHAT.Lang.urlTelTrans = function(text) {
    text = this.replaceEmail(text);
    text = this.replaceUrl(text);
    text = this.replaceTel(text);
    return text;
};

MECHAT.Lang.trim = function(s) {
    if (typeof s == 'undefined') {
        return '';
    }
    // s = s.replace(/\<div\>\<br\>\<\/div\>/g,"");
    // s = s.replace(/&nbsp;/g,"");
    s = s.replace(/^\s*/g, '');
    return s.replace(/\s*$/g, '');
};
MECHAT.Lang.isNumber = function(s) {
    return !isNaN(s);
};
MECHAT.Lang.isEmail = function(str) {

    // var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    var reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    return reg.test(str);
};
MECHAT.Lang.isQQ = function(str) {
    var result = str.match(/[1-9][0-9]{4,}/);
    if (result == null) return false;
    return true;
};
MECHAT.Lang.isImg = function(str) {
    var ext = str.substring(str.lastIndexOf('.') + 1, str.length);
    ext = ext.toLowerCase();
    if (ext != 'jpg' && ext != 'jpeg' && ext != "png" && ext != "gif" && ext != "bmp") {
        return false;
    }
    return true;
};
MECHAT.Lang.httpReplace = function(txt) {
    var regex = /(https?:\/\/)?(\w+\.?)+(\/[a-zA-Z0-9\?%=_\-\+\/]+)?/gi;
    return txt.replace(regex, function(match, capture) {
        if (capture) {
            return match
        } else {
            return 'http://' + match;
        }
    });
};

//换行的处理
MECHAT.Lang.html_enline = function(str) {
    if (typeof str != 'string') {
        return str;
    }
    var s = "";
    if (!str || str.length == 0) return "";
    s = str.replace(/\n/g, "<br>");
    return s;
};
/**
 * 获取后缀名
 */
MECHAT.Lang.getSuffix = function(str) {
    return str.substring(str.lastIndexOf('.') + 1, str.length);
};
//是否是座机
MECHAT.Lang.isTell = function(str) {
    // var result = str.match(/^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$/);
    // var result = str.match(/^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$/);
    var result = str.match(/\d{3}-\d{8}|\d{4}-\d{7}/);
    if (result == null) return false;
    return true;
};
MECHAT.Lang.isPhone = function(str) {
    var result = str.match(/1[3-8]+\d{9}/);
    if (result == null) return false;
    return true;
};
MECHAT.Lang.isMac = function() {
    if (navigator.userAgent.indexOf("Mac OS X") > 0) {
        return true;
    }
    return false;
};
MECHAT.Lang.html_encode = function(str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&/g, "&amp;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    s = s.replace(/\n/g, "<br>");
    return s;
};

//时间的格式化
Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1, //month 
        "d+": this.getDate(), //day 
        "h+": this.getHours(), //hour 
        "m+": this.getMinutes(), //minute 
        "s+": this.getSeconds(), //second 
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter 
        "S": this.getMilliseconds() //millisecond 
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
//时间的转换
MECHAT.Lang.fromNow = function(date) {
    var now = new Date;
    var now_d = now.getDate();
    var t;
    var format = function(n, unit) {
            return n + " " + unit;
        }
        // past / future
    var diff = date > now ? date - now : now - date;
    date = new Date(date);
    var y = date.getFullYear(); //获取完整的年份(4位,1970-????)
    var mon = date.getMonth() + 1; //获取当前月份(0-11,0代表1月)
    var d = date.getDate(); //获取当前日(1-31)
    var h = date.getHours(); //获取当前小时数(0-23)
    var m = date.getMinutes(); //获取当前分钟数(0-59)
    h = h < 10 ? ("0" + h) : h;
    m = m < 10 ? ("0" + m) : m;
    var d_s = now_d - d;
    if (d_s == 0) {
        return format("今天", h + ":" + m);
    } else if (d_s == 1) {
        return format("昨天", h + ":" + m);
    } else {
        return format(mon + "月" + d + "日", h + ":" + m);
    }
    return format(y + "年" + mon + "月" + d + "日", h + ":" + m);
};
//unicode的转换
MECHAT.Lang.uc2encode = function(str) {
    var t = escape(str);
    t = t.replace(/\%u/g, '\\u');
    return t;
};
/**
 * 将对象转为字符串
 */
MECHAT.Lang.objToStr = function(obj) {
    var str = '';
    if (typeof obj == "object") {
        for (var k in obj) {
            var v = obj[k];
            str += k + "[@]" + v + "[#]";
        }
    } else {
        str = obj;
    }
    return str;
};
/**
 * 按照自定义的规则，把字符串拆分成对象
 */
MECHAT.Lang.strToObj = function(str) {
    var obj = {};
    if (typeof str == "string" && str.indexOf("#") > 0) {
        str = str.split('[#]');
        for (var i = 0, len = str.length; i < len; i++) {
            var s = str[i];
            if (s.indexOf("[@]") > 0) {
                s = s.split("[@]");
                obj[s[0]] = s[1];
            }
        }
    } else {
        obj = str;
    }
    return obj;
};


/**
 * Obj 对象相关
 */
MECHAT.namespace("Obj"); //定义命名空间
//将origin的属性设置到obj上
MECHAT.Obj.setProperty = function(obj, origin) {
    for (var v in origin) {
        if (obj.hasOwnProperty(v) && origin[v]) {
            obj[v] = origin[v];
        }
    };
};



/*
 *Common.js 可覆用性的组件 2014-10-19合并过来
 */
/**
 * Cookie相关操作
 */
MECHAT.namespace("Cookie");
MECHAT.Cookie = {
    read: function(key, dc) {
        key = encodeURIComponent(key) + "=";
        var cookieStr = document.cookie,
            cookieValue = null;
        var start = cookieStr.indexOf(key),
            end = cookieStr.indexOf(";", start),
            end = end == -1 ? cookieStr.length : end;
        if (start > -1) {
            cookieValue = cookieStr.substring(start + key.length, end);
            if (!dc) {
                cookieValue = decodeURIComponent(cookieValue);
            }
        }
        return cookieValue;
    },
    readObj: function(key) {
        var val = this.read(key, true);
        var obj = {};
        if (val) {
            val = val.split('&');
            for (var i = 0, len = val.length; i < len; i++) {
                var v = val[i];
                v = v.split("=");
                obj[decodeURIComponent(v[0])] = decodeURIComponent(v[1]);
            };
        }
        return obj;
    },
    set: function(key, value, expires, path, domain, secure) {
        var cookieText = encodeURIComponent(key) + "=",
            subCookieParts = new Array();
        switch (typeof value) {
            case 'object':
                for (var subName in value) {
                    if (subName.length > 0 && value.hasOwnProperty(subName)) {
                        subCookieParts.push(encodeURIComponent(subName) + "=" +
                            encodeURIComponent(value[subName]));
                    }
                }
                if (subCookieParts.length > 0) {
                    cookieText += subCookieParts.join('&');
                }
                break;
            default:
                cookieText += encodeURIComponent(value);
                break;
        }
        if (!expires) {
            expires = new Date();
            expires.setTime(expires.getTime() + 90 * 24 * 3600 * 1000); //默认cookie存放时间
        }
        if (expires instanceof Date) {
            cookieText += "; expires=" + expires.toGMTString();
        }
        if (path) {
            cookieText += "; path=" + path;
        }
        if (domain) {
            cookieText += "; domain=" + domain;
        }
        if (secure) {
            cookieText += "; secure";
        }
        document.cookie = cookieText;
    }
};
/**
 * Ajax相关
 */
MECHAT.namespace("Ajax");
MECHAT.Ajax.createCORSRequest = function() {
    if (typeof XMLHttpRequest != "undefined") {
        return new XMLHttpRequest();
    } else if (typeof ActiveXObject != "undefined") {
        if (typeof arguments.callee.activeXString != "string") {
            var versions = ["MSXML2.XMLHttp.6.0", "MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp"],
                i, len;
            for (i = 0, len = versions.length; i < len; i++) {
                try {
                    new ActiveXObject(versions[i]);
                    arguments.callee.activeXString = versions[i];
                    break;
                } catch (ex) {}
            }
        }
        return new ActiveXObject(arguments.callee.activeXString);
    }
    return xhr;
};
MECHAT.Ajax.makeQuery = function(json) {
    json = json || {};
    var query_arr = [];
    json.__ = new Date().getTime();
    for (var key in json) {
        var val = json[key];
        query_arr.push(key + '=' + encodeURIComponent(val));
    }
    var query = query_arr.join('&').replace(/%20/g, '+');
    return query;
};
//param 参考包括success,error,timeout,timeoutBack
MECHAT.Ajax.post = function(url, data, param) {
    var xhr = this.createCORSRequest();
    var timeEr = null;
    try {
        if (param.timeout) {
            //超时断开访问
            timeEr = setTimeout(function() {
                param.timeoutBack(); //调用回调函数
                xhr.abort();
            }, param.timeout);
        }

        var sendStr = data;
        if (typeof data == 'object') {
            sendStr = this.makeQuery(data);
        }
        xhr.open('post', url, true);
        if (param.author) {
            xhr.setRequestHeader("Authorization", param.author);
        }
        var contet_type = param.type || "application/x-www-form-urlencoded; charset=UTF-8";
        xhr.setRequestHeader("Content-Type", contet_type);
        xhr.send(sendStr);

        if (typeof param.success != 'undefined') {
            //设置执行成功后的函数
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        if (timeEr) {
                            clearTimeout(timeEr);
                        }
                        param.success(xhr.responseText);
                    }
                }
            };
        }
    } catch (ex) {
        if (timeEr) {
            clearTimeout(timeEr);
        }
        if (param.timeoutBack) {
            param.timeoutBack();
            xhr.abort();
        }
    }
};

MECHAT.Ajax.get = function(url, data, param) {
    try {
        var xhr = this.createCORSRequest();
        url += "?" + this.makeQuery(data);
        xhr.open("get", url, false);
        if (typeof param.success != 'undefined') {
            //设置执行成功后的函数
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        param.success(xhr.responseText);
                    }
                }
            };
        }
        xhr.send(null);
    } catch (ex) {
        if (param.timeoutBack) {
            param.timeoutBack();
        }
    }

};

MECHAT.Ajax.jsonp = function(url, data, callback, param) {
    if (param && param.timeout) {
        setTimeout(function() {
            param.timeoutBack();
        }, param.timeout);
    }

    var query = this.makeQuery(data) + '&callback=' + callback;
    var script = document.createElement("script");
    script.src = url + "?" + query;
    try {
        document.body.appendChild(script);
    } catch (e) {

    }
};

//文件的上传 
MECHAT.Ajax.fileUp = function() {

};
/**
 * Drag 拖拽相关
 */
MECHAT.namespace("Drag");
/**
 * Animation 动画相关
 */
MECHAT.namespace("Ani");
/**
 * 动画移动效果
 * @param element执行动画的对象
 * @param position 移动的位置、方向{left:120},{top:120}
 * @param speed 移动的速度1~100 默认为30
 * @param callback 回调函数
 */
MECHAT.Ani.move = function(element, position, speed, callback) {
    if (!element.effect) {
        element.effect = {};
        element.effect.move = 0;
    }
    clearInterval(element.effect.move);
    var speed = speed || 30;
    //因为element.offsetBottom 没有值，所以使用了固定值
    var start = {
        left: element.offsetLeft,
        bottom: position.b,
        top: element.offsetTop,
        right: position.r
    };
    var style = element.style;
    var parr = new Array(),
        p, len, bol;
    if (typeof(position.left) == 'number') {
        parr.push('left');
        p = 'left';
    }
    if (typeof(position.right) == 'number') {
        parr.push('right');
        p = 'right';
    }
    if (typeof(position.bottom) == 'number') {
        parr.push('bottom');
        p = 'bottom';
    }
    if (typeof(position.top) == 'number') {
        parr.push('top');
        p = 'top';
    }

    element.effect.move = setInterval(function() {
        for (var i = 0; i < parr.length; i++) {
            start[parr[i]] += (position[parr[i]] - start[parr[i]]) * speed / 100;
            style[parr[i]] = start[parr[i]] + 'px';
        }
        for (var i = 0; i < parr.length; i++) {
            if (Math.round(start[parr[i]]) == position[parr[i]]) {
                if (i != parr.length - 1) {
                    continue;
                }
            } else {
                break;
            }
            for (var i = 0; i < parr.length; i++) {
                style[parr[i]] = position[parr[i]] + 'px'
            };
            clearInterval(element.effect.move);
            if (callback) {
                callback();
            }
        }

    }, 20);
};
/**
 * Color 颜色相关
 */
MECHAT.namespace("Color");
MECHAT.Color = {
    blue: ['#067ab4', '#0c90c2'],
    gray: ['#616161', '#797979'],
    green: ['#69b40f', '#81c21d'],
    initial: ['#47c1a8', '#61cdb8'],
    red: ['#f23f3f', '#f45959'],
    rosered: ['#ea4c89', '#ee659d'],
    yellow: ['#ff8000', '#ff9500'],
    get: function(k) {
        k = k.toLowerCase();
        return this[k];
    }
};
