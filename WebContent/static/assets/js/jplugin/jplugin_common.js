jplugin={};

///basic
function my_urlBase(){
	return location.protocol+"//"+location.host;
}

///ajax function
function my_ajax(url,setting){
	var para = {url:url,type:"post"};
	para.success = ajaxSuccess;
	para.error = ajaxError;
	para.data = setting.data==null? new Object():setting.data;
	
	para.beforeSend = ajaxbeforeSend;
	para.success = ajaxSuccess;
	para.error = ajaxError;
	if (setting.contentType!=null) 
		para.contentType=setting.contentType;
	if (setting.dataType!=null)
		para.dataType = setting.dataType;
	$.ajax(para);
	
	function ajaxbeforeSend(jqXHR,p){
		console.log("ajax call start. url="+p.url+" para="+p.data);
	}
	
	function ajaxSuccess(data,status,jaXHR){
		console.log("ajax call success. status="+status+" data type="+typeof(data));		
		console.log("ajax call result result:"+JSON.stringify(data));
		
		if (setting.success)	
			setting.success(data,status,jaXHR);	
		
		if (setting.successjson){
			if (!data.success) alert("调用服务返回异常,错误码："+data.code);
			else setting.successjson(data.content);
		}
	}
	function ajaxError(jqXHR,status,errorMsg){
		console.log("ajax call error. status="+status+" msg="+errorMsg);
		wa_notice("网络不给力");
		if (setting.error)
			setting.error(jqXHR,status,errorMsg);
	}
}
	

;(function($){
	$.fn.extend({
		render:function(obj){
			$.waRole.render(this,obj);
		},
		build:function(){
			return $.waRole.build(this);
		}
	});
})(jQuery);

$.waValid = $.waValid || {};
$.waValid.registry={};
$.waValid.extend=function(setting){
	var plg = {};
	setting.valid!=null && (plg.valid = setting.valid);
	setting.name!=null && ($.waValid.registry[setting.name]=plg);
}
$.waValid.valid=function(role,value){
	var plg = $.waValid.registry[role];
	if (plg) return plg.valid(value);
	else console.log("not find plg for valid:"+role);
}

$.waRole = $.waRole || {};
$.waRole.registry={};
$.waRole.extend=function(setting){
	var plg = {};
	setting.render!=null && (plg.render = setting.render);
	setting.build !=null && (plg.build = setting.build);
	setting.name!=null && ($.waRole.registry[setting.name]=plg);
}
$.waRole.render=function($div,value){
	var role = $div.attr("data-wa-role");
	if (!role) {
		console.log("can't find the role. "+$div[0].outerHTML);
		return;
	} 
	var plg = $.waRole.registry[role];
	if (plg) {
		($div.attr("data-wa-expand")=="true") && $.waRole.clear($div);
		plg.render($div,value);
		$div.attr("data-wa-expand","true");
	}
	else console.log("not find plg:"+role);
}
$.waRole.build=function($div){
	var role = $div.attr("data-wa-role");
	if (!role) {
		console.log("can't find the role");
		return;
	} 
	var plg = $.waRole.registry[role];
	if (plg) return plg.build($div);
	else console.log("not find plg:"+role);
}
$.waRole.clear=function($div){
	$div.attr("data-wa-expand","false");
	$div.empty();
}

;(function($){
	$.fn.extend({
		
		bindList:function (iterid, jsonList, callback,debug) {
			if (debug){
				console.log("wa_bindListElems:json="+JSON.stringify(jsonList));
				console.log("wa_bindListElems:iterid="+iterid);
			}
			
			var ctxjq = this;
			if (debug) console.log("this="+this.prop("outerHTML"));
			
			var selector = '#' + iterid;
			for (var i = 0;; i++) {
				var jq = ctxjq.find(selector + "_" + i);
				if (jq.length > 0) {
					jq.remove();
				} else {
					break;
				}
			}
			var lastrow = ctxjq.find(selector);
			lastrow.hide();
			for (var i = 0; i < jsonList.length; i++) {
				var temp = lastrow.clone();
				
				temp.attr("id", iterid + "_" + i);
				temp.attr("_index",i+"");
				
				callback(temp, jsonList[i]);
				$(lastrow).after(temp.prop("outerHTML"));
				lastrow = lastrow.next();
				lastrow.show();
				//lastrow.attr("id", iterid + "_" + i);
				//lastrow.attr("_index",i+"");
			}
		},
		
		autoBuild:function(obj,prefix){
				if (obj==null) obj={};
				var jq = this.find("input,select");
				//alert(jq.size());
				for (var i = 0; i < jq.length; i++) {
					var cjq = jq.eq(i);
					var id = cjq.attr("id");
					
					var keyname=null;

					if (prefix != null && prefix!="") {
						if (id.substr(0, prefix.length) == prefix) {
							var tail = id.substring(prefix.length);
							if (tail != null && tail != "") {
								keyname = tail;
								//obj[tail] = cjq.val();
							}
						}
					} else {
						keyname = id;
						//obj[id] = cjq.val();
					}
					
					//如果没有匹配到，忽略该项
					if (keyname==null) continue;
					
					//为obj增加属性
					var inputtype = cjq.attr('type');
					if (inputtype=='radio'){
						var radioname = cjq.attr("name");
						var checkedRadio = this.find("input[type=radio][name="+radioname+"]:checked");
						if (checkedRadio.length>0){
							//如果存在选中的radio，则增加属性
							obj[keyname]=checkedRadio.val();	
						}
					}else{
						obj[keyname]=cjq.val();
					}
				}
				
				return obj;
		},
		validAndBuild:function(obj,prefix,valid){
			if (obj==null) obj={};
			if (valid==null) valid=true;
			
			var $ctx = this;
			var jq = this.find("input[type='text'],input[type='hidden'],input[type='password'],select,textarea,div[data-wa-role]");
			var success = true;
			var result = jq.each(function(){
				var val; 
				console.log("tagname="+this.tagName);
				if (this.tagName=="DIV"){
					if ($(this).is(':hidden')) return;//如果是隐藏状态，数据无效
					val = $.waRole.build($(this));
					console.log("build result:"+val);
				}else{
					val = $(this).val();
				}
				
				console.log("validate:"+val);
				//set the result value item
				var id = $(this).attr("id");
				if (!id) return;
				var keyname = getKeyName(prefix,id);
				if (keyname==null) {
					alert("key not found");
					return;
				}
				obj[keyname] = val;
				
				//valid
				if (val!=null && val!=""){
					var valRole = $(this).attr("data-wa-val");
					if (valRole){
						var validResult = $.waValid.valid(valRole,val);
						if (validResult==null) return;//success
						else {
							var label = getLabel(this);
							if (label!=null){
								alert(label+validResult);
							}else{
								alert(validResult);
							}
							success = false;
							return false;
						}
					}else return;//need not valid
				}else{
					var required = $(this).attr("data-wa-req");
					if (required == "true"){
						var label = getLabel(this);
						if (label!=null){
							alert(label+"不能为空");
						}else{
							alert("非空校验失败");
						}
						success = false;
						return false;
					}
				}
			});
			//校验成功，则返回 结果，否则返回null
			if (success) return obj;
			else return null;
			
			function getLabel(valueElem){
				var label;
				var labelid = $(valueElem).attr("data-label-id");
				if (labelid!=null){
					label=$ctx.find("#"+labelid);
				}else{
					label = $(valueElem).prev();
				}
				if (label==null || label.length==0)  return null;
				
				var txt = $(label).text();
				var posl = txt.indexOf("(");
				if (posl>0) return txt.substr(0,posl);
				else return txt;
			}
			
			function getKeyName(prefix,id){
				if (prefix != null && prefix!="") {
					if (id.substr(0, prefix.length) == prefix) {
						var tail = id.substring(prefix.length);
						if (tail != null && tail != "") {
							return tail;
						}
					}
				} else {
					return id;
				}
				return null;
			}
		},
		autoBind:function(json,prefix,spt_wa_role){
			//alert("autobind");
			if (prefix==null) prefix='';
			if (json==null) json={};
			
			for ( var key in json) {
				var jq = this.find('#'+prefix+key) ;
				
				if (jq.length > 0) {
					var tag = jq[0].tagName.toLowerCase();
					if (tag == "input"){
						var type = jq.attr("type");
						if (type=='radio')
							bindRadio(this,jq.attr("name"),json[key]);
						else
							jq.val(json[key]);
					}else if (tag=="select"){
						jq.val(json[key]);
					}else if (tag=="img"){
						bindImg(jq,json[key]);
					}else{
						if (spt_wa_role && (tag=="div" || tag=="span") && jq.attr("data-wa-role")!=null){
							$.waRole.render(jq,json[key]);
						}else{
							var temp=json[key];
							if (temp==null) temp="";
							jq.text(temp);	
						}
					}
				}
			}
			//没有expand的expand掉
			if (spt_wa_role)
				$("div[data-wa-role]").each(function(){
					var $this=$(this);
					($this.attr("data-wa-expand")!="true") && $.waRole.render($this,"");
				});
				
			function bindImg(jq,imgurl){
				if (imgurl.substr(0,1)=='/')
					jq.attr("src",wa_localUrlBase + imgurl);
				else
					jq.attr("src",imgurl);
			}
			
			function bindRadio(jq,name,val){
				//use name attr to find all 
				var checkRadio = jq.find("input[name="+name+"][value="+val+"]");
				if (checkRadio.length>0){
					checkRadio.attr("checked",true);
				}
				/*
				for (var idx=0;idx<radiolist.length;idx++){
					var radio = radiolist.eq(idx);
					if (radio.val()==val) {
						radio.attr("checked","true");
					}else{
						radio.removeAttr("checked");
					}
				}*/
			}
		}		
	});
})(jQuery);


///////////////////validate check

$.waValid.extend({name:"code",
	valid:function(s){
		var patrn=/^(?:\w+\_?)*\w+$/; 
		if (!patrn.exec(s)) return "编码格式不正确,只能包含字母数字和下划线"; 
		return null; 
	}});


$.waValid.extend({name:"email",
	valid:function(s){
		var patrn=/^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/; 
		if (!patrn.exec(s)) return "电子邮件格式不正确"; 
		return null; 
	}});
$.waValid.extend({name:"count",
	valid:function(s){
		var patrn = /^[0-9]*$/;
		if (!patrn.exec(s)) return "数量格式不正确"; 
		return null; 
	}});

$.waValid.extend({name:"currency",
	valid:function(s){
		var patrn=/(?!^0\d+|.*0$)^[0-9]{1,16}(\.[0-9]{1,4})?$|^0$/; 
		if (!patrn.exec(s)) return "金额格式不正确"; 
		return null; 
	}});
$.waValid.extend({name:"percent",
	valid:function(s){
		var patrn=/^[0-9]+(.[0-9]{1,3})?$/;
		if (!patrn.exec(s)) return "百分数格式不正确"; 
		return null; 
	}});

$.waValid.extend({name:"mphone",
	valid:function(s){
		//必须以数字开头，除数字外，可含有“-” 
		var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/; 
		if (!patrn.exec(s)) return "必须以数字开头，除数字外，可含有“-”"; 
		return null; 
	}});

$.waValid.extend({name:"phone",
	valid:function(s){
		//校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-” 
		var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/; 
		if (!patrn.exec(s)) return "可以“+”开头，除数字外，可含有“-”"; 
		return null;
	}});

$.waValid.extend({name:"zipcode",
	valid:function(val){
		//校验邮政编码
		var patrn=/^[0-9 ]{4,8}$/; 
		if (!patrn.exec(val)) return "邮政编码格式不正确"; 
		return null; 
	}});

$.waValid.extend({name:"personid",
	valid:function(f){
	    if(f.length != 18) {  
	        return "请输入中国公民的18位身份证号码, 您当前输入了" + f.length + "位号码" ;  
	    }  
	    // 2. 确保前17位每一位都是数字  
	    for(i = 0; i < f.length - 1; i++) {  
	        // 如何判断一个字母是数字  
	        if(isNaN( parseInt( f.charAt(i) ) )) {  
	            return "您输入的身份证号码前17位包含有字母";  
	        }  
	    }  
	      
	    // 3. 确保最后一位是数字或者X  
	    var lastIDNum = f.charAt(17);  
	    if( isNaN(parseInt( f.charAt(i) )) &&  lastIDNum.toLowerCase() != 'x') {  
	        return "您输入的身份证号码最后一位不是数字也不是x" ;  
	    }  
	    return null;  
	}});


//////////////pagenation/////////////////////////////////////////////////////////////////////////////////////////////////////


function wa_initPageBindings($e,goFunc){
	$e.off();
	$e.find("#pc_first").on("click",function(){goFunc(1)});
	$e.find("#pc_prev").on("click",function(){goFunc(wa_pageIndex($e)-1)});
	$e.find("#pc_next").on("click",function(){goFunc(parseInt(wa_pageIndex($e))+1)});
	$e.find("#pc_last").on("click",function(){goFunc(wa_lastPage($e))});
	goFunc(1);

	function wa_pageIndex($e){
		if ($e.find("#withTotal").css("display")!="none") return $e.find("#pc_currentPage").text();
		if ($e.find("#noTotal").css("display")!="none") return $e.find("#pc_onlyCurrPage").text();
		return 1;
	}
	function wa_lastPage($e){
		if ($e.find("#withTotal").css("display")!="none") return $e.find("#pc_totalPage").text();
		return 1;
	}
}

function wa_renderPageInfo($e,pc,listSize){
		/*<div style="display: inline" id="withTotal">
			共
			<div style="display: inline" id="pc_totalCount">100</div>
			条，第
			<div style="display: inline" id="pc_currentPage">1</div>
			/
			<div style="display: inline" id="pc_totalPage">10</div>
			页
		</div>
		<div style="display: inline" id="noTotal">
			第
			<div style="display: inline" id="pc_onlyCurrPage">100</div>
			页
		</div>*/
	if (pc==null) {
		$e.find("#withTotal").css("display","none")
		$e.find("#noTotal").css("display","none");
		return;
	}
	if (pc.shdCount){
		$e.find("#withTotal").css("display","inline")
		$e.find("#noTotal").css("display","none");
		$e.find("#pc_totalCount").text(pc.count);
		var totalPage = Math.ceil(pc.count/pc.pageSize);
		$e.find("#pc_totalPage").text(totalPage);
		$e.find("#pc_currentPage").text(pc.pageIndex);
		
		if ( pc.pageIndex < totalPage){
			$e.find("#pc_last").show();
			$e.find("#pc_next").show();
		}else{
			$e.find("#pc_last").hide();
			$e.find("#pc_next").hide();
		}
		if (pc.pageIndex > 1){
			$e.find("#pc_prev").show();
			$e.find("#pc_first").show();
		}else{
			$e.find("#pc_prev").hide();
			$e.find("#pc_first").hide();
		}
	}else{
		$e.find("#withTotal").css("display","none")
		$e.find("#noTotal").css("display","inline");
		$e.find("#pc_onlyCurrPage").text(pc.pageIndex);
		
		if (listSize >= pc.pageSize){//如果查询条数和返回条数一样多，则有下一页，否则没有
			$e.find("#pc_last").show();
			$e.find("#pc_next").show();
		}else{
			$e.find("#pc_last").hide();
			$e.find("#pc_next").hide();
		}
		
		if (pc.pageIndex > 1){
			$e.find("#pc_prev").show();
			$e.find("#pc_first").show();
		}else{
			$e.find("#pc_prev").hide();
			$e.find("#pc_first").hide();
		}
	}
}

///////////param /////////////////////////////////////////////////////////////////////////////////////////

var paramethod="qs";


function wa_paraname(path){
	var s = path +"";
	var path="pa_";
	for (var i=0;i<s.length;i++){
		var c = s.charAt(i);
		if ((c>='a' && c<='z') || (c>='A' && c<='Z') || (c=='_')){
			path = path + c;
		}
		if (c=='.'){
			path = path + "_dt_";
		}
		if (c=='/'){
			path = path +'_';
		}
		if (c=='?'){
			break;
		}
	}
	return path;
}


//页面参数管理,可选参数pm
function wa_initpara(pm) {
	this.obj = new Object();
	
	this.paramMethod = pm;
	if (pm == null)	this.paramMethod = paramethod;
	
	if (this.paramMethod == 'qs'){
		this.obj = parseParaFromLocation(window.location.href);
	}else{
		var s = sessionStorage[wa_paraname(window.location.pathname)];
		if (s != null) {
			this.obj = JSON.parse(s);
		}
	}

	this.show = function() {
		alert(JSON.stringify(this.obj));
	}
}


//页面参数管理 ,pm 为可选参数
function wa_gopara(path,pm) {
	this.obj = new Object();
	this.pagepath = path;
	
	this.paramMethod = pm;
	if (pm == null)	this.paramMethod = paramethod;

	this.show = function() {
		alert(JSON.stringify(this.obj));
	}
	this.clear = function() {
		this.obj = new Object();
		return this;
	}
	this.go = function(){
		if (this.paramMethod == 'qs'){
			this.pagepath = addParamToLocation(this.pagepath,this.obj);
		}else{
			sessionStorage[wa_paraname(this.pagepath)] = JSON.stringify(this.obj);
		}
		location.href = this.pagepath;
	}
	
}
function parseParaFromLocation(loc){
	//alert("not impl");
	var pos = loc.indexOf('_WEB_PARA_=');
	if (pos<0) {
			//2016-2-15修改为从querystring直接获取
		    var obj={};
		    var reg=/\??(\w+)=(\w+)&?/g;
		    var result;
		    while((result = reg.exec(loc)) != null){
		        obj[result[1]]=result[2];
		    }
		    return obj;
	}
	
	var ret = loc.substring(pos + 11);
	var theAndPos = ret.indexOf('&');
	if (theAndPos>=0)
		ret = ret.substring(0,theAndPos); 
	
	return JSON.parse(utf8to16(base64decode(ret)));
}

function addParamToLocation(pagepath,obj){
	var ret =pagepath;
	//alert("not impl");
	if (pagepath.indexOf('?')>=0)
		ret += '&_WEB_PARA_='; 
	else
		ret += '?_WEB_PARA_=';
	ret+=  base64encode(utf16to8(JSON.stringify(obj)));
	return ret;
}

//dicts 维护
jplugin.dict = jplugin.dict || {};
jplugin.dict.dicts={};
jplugin.dict.render=function(dict,k){
	var m = jplugin.dict.dicts[dict];
	if (m==null) return k;
	else return m[k];
};
