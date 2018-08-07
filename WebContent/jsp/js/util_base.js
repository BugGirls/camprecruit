
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null; 
}
function addcookie(name,value,expireHours){  
	var cookieString=name+"="+escape(value)+"; path=/";  
	//判断是否设置过期时间  
	if(expireHours>0){  
		var date=new Date();  
		date.setTime(date.getTime+expireHours*3600*1000);  
		cookieString=cookieString+"; expire="+date.toGMTString();  
	}
	document.cookie=cookieString;  
}  

function getcookie(name){  
	var strcookie=document.cookie;  
	var arrcookie=strcookie.split("; ");  
	for(var i=0;i<arrcookie.length;i++){  
		var arr=arrcookie[i].split("=");  
		if(arr[0]==name)return decodeURIComponent(arr[1]); //增加对特殊字符的解析  
	}  
	return "";  
}  

function delCookie(name){//删除cookie  
	var exp = new Date();  
	exp.setTime(exp.getTime() - 1);  
	var cval=getcookie(name);  
	if(cval!=null) document.cookie= name + "="+cval+"; path=/;expires="+exp.toGMTString();  
} 
function isWeiXin(){
	var ua = window.navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i) == 'micromessenger'){
		return true;
	}else{
		return false;
	}
}

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