<%@page import="org.springframework.context.annotation.Import"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<style>
.div_reservated{height:100%;vertical-align: bottom;text-align: left;line-height: 100px;}
.div_reservated span{color:#2b7dbc;font-size: 26px;padding-left: 5px;}
.lastnum{background: white url(/weixin/jsp/images/icon_green.png) bottom left no-repeat;padding-left: 18px;}
.countnum{background: white url(/weixin/jsp/images/icon_yellow.png) bottom left no-repeat;padding-left: 18px;}
</style>
<div class="row">

	<div class="col-xs-12"  id="promotionlist">
	<!-- 
		<div class="col-xs-4" >
			<div class="panel panel-default">
			  <div class="panel-heading">小小世界</div>
			  <div class="panel-body">
			    <div class="col-xs-12" >
			    <div class="col-xs-7 div_reservated" >
			    	 已报名<span >125</span>
			    </div>
			    <div class="col-xs-5" >
				    <p class="lastnum" >剩余名额：<span style="color:#5cb85c;">125</span>人</p>
				    <p class="countnum">总名额：<span style="color:#f0ad4e;">125</span>人</p>
			    </div>
			    </div>
			    
			  </div>
			  	<div class="panel-footer ">
			  		<div class="progress">
						<div class="progress-bar progress-bar-info  progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
						    <span class="">报名进度 40% </span>
						  </div>
					  </div>
				</div>
			</div>
			
		</div>
		 -->
	</div>

</div>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		Date.prototype.Format = function (fmt) { //author: meizz 
        		    var o = {
        		        "M+": this.getMonth() + 1, //月份 
        		        "d+": this.getDate(), //日 
        		        "h+": this.getHours(), //小时 
        		        "m+": this.getMinutes(), //分 
        		        "s+": this.getSeconds(), //秒 
        		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        		        "S": this.getMilliseconds() //毫秒 
        		    };
        		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        		    for (var k in o)
        		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        		    return fmt;
        		}
        		
        		loading();
        		
        		
        		setInterval("loading();", 180000);
        	});
        });
        
        function loading(){
    		$.ajax({
    			type : "post",
    			url : " ${contextPath}/sys/alliancepromotion/getAgentPromotionStateList",
    			success : function(data) {
					console.log(data)
    				var list = eval(data);
					console.log(list)
    				var x = new Array();
    				var y = new Array();
    				var z = new Array();
    				var elements = '';
    				$("#promotionlist").empty();
    				if(list.length>0){
    				$.each(list, function(key, value) {
        				console.log(value);
    					x[key] = value.name;
    					y[key] = value.zongshu;
    					var v = parseInt(value.reservationnum / value.participation * 100);
    					console.log(v)
    					elements += '<div class="col-xs-4" ><div class="panel panel-default"><div class="panel-heading">'
									+ value.name +'</div><div class="panel-body"><div class="col-xs-12" ><div class="col-xs-7 div_reservated" > 已报名<span >'
    								+ value.reservationnum +'</span></div><div class="col-xs-5" ><p class="lastnum" >剩余名额：<span style="color:#5cb85c;">'
    								+ (value.participation-value.reservationnum) +'</span>人</p><p class="countnum">总名额：<span style="color:#f0ad4e;">'
    								+ value.participation +'</span>人</p></div></div></div><div class="panel-footer "><div class="progress"><div class="progress-bar progress-bar-info  progress-bar-striped" role="progressbar" aria-valuenow="'
    								+v+'" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;width: '+v+'%"><span> '+v+'% </span></div></div></div></div></div>';
    				});
    				}else{
    					elements = '无正在进行中的活动信息';
    				}
    				$("#promotionlist").append(elements);
    			}
    		});
		}
        
        	                    
        
</script>
