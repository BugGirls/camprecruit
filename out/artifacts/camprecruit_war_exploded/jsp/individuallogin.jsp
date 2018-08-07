<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%
String path = request.getContextPath();
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>登录</title>
<script type="text/javascript">
var ctx = "<%=requestPath%>";
console.log(ctx);
</script>
<link rel="Shortcut Icon" href="h/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="style/css/style.css"/>
<script id="allmobilize" charset="utf-8" src="style/js/allmobilize.min.js"></script>

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script type="text/javascript" src="style/js/core.min.js"></script>
<script type="text/javascript" src="js/validate_mine.js"></script>
<script type="text/javascript" src='js/util_base.js'></script>

<script type="text/javascript">
var youdao_conv_id = 271546; 
</script> 
<script type="text/javascript" src="style/js/conv.js"></script>
<style type="text/css">
	#loginForm input#phone{width:296px;font-size:16px; color:#777; border:none;}
</style>
</head>

<body id="login_bg">
	<div class="login_wrapper">
		<div class="login_header">
        	<a href="h/"><img src="style/images/logo_white.png" width="285" height="62" alt="拉勾招聘" /></a>
            <div id="cloud_s"><img src="style/images/cloud_s.png" width="81" height="52" alt="cloud" /></div>
            <div id="cloud_m"><img src="style/images/cloud_m.png" width="136" height="95"  alt="cloud" /></div>
        </div>
        
    	<input type="hidden" id="resubmitToken" value="" />		
		 <div class="login_box">
        	<form id="loginForm">
				<input type="text" id="phone" name="phone" value="" tabindex="1" placeholder="请输入手机号" />
				<span class="error" style="display:none;" id="beError"></span>
			  	<input type="password" id="password" name="password" tabindex="2" placeholder="请输入密码" />
			  	<span class="error" id="pwdError"></span>
			    <label class="fl" for="remember"><input type="checkbox" id="remember" value="" checked="checked" name="autoLogin" /> 记住我</label>
			    <a href="resetpwd_step1.jsp" class="fr" >忘记密码？</a>
			    
				<!--<input type="submit" id="submitLogin" value="登 &nbsp; &nbsp; 录" />-->
				<a style="color:#fff;" href="javascript: login();" class="submitLogin" title="登 &nbsp; &nbsp; 录"/>登 &nbsp; &nbsp; 录</a>

			    
			    <input type="hidden" id="callback" name="callback" value=""/>
                <input type="hidden" id="authType" name="authType" value=""/>
                <input type="hidden" id="signature" name="signature" value=""/>
                <input type="hidden" id="timestamp" name="timestamp" value=""/>
			</form>
			<div class="login_right">
				<div>还没有拉勾帐号？</div>
				<a  href="register.jsp"  class="registor_now">立即注册</a>
			    <div class="login_others">使用以下帐号直接登录:</div>
			    <a  href="h/ologin/auth/sina.html"  target="_blank" class="icon_wb" title="使用新浪微博帐号登录"></a>
			    <a  href="h/ologin/auth/qq.html"  class="icon_qq" target="_blank" title="使用腾讯QQ帐号登录"></a>
			</div>
        </div>
        <div class="login_box_btm"></div>
    </div>

<script type="text/javascript">
var userinfo = getcookie('userinfo');
var remebercookie = getcookie('isremember');
$(function(){
	$('#phone').focus(function(){
		$('#beError').hide();
	});
	$('#password').focus(function(){
		$('#pwdError').hide();
	});
	if(remebercookie!=null){
		if(remebercookie==1){
			$('#remember').attr("checked",true);
			if(userinfo.indexOf("_")>0){
				var userinfoarr = userinfo.split("_");
				if(userinfoarr.length>1){
					var userphone = userinfoarr[0];
					var userpass = userinfoarr[1];
					$('#phone').val(userphone);
					$('#password').val(userpass);
				}
			}
		}else{
			$('#remember').attr("checked",false);
		}
	}
})

function login(){
	var isremember = 0;
	if($("#remember").is(":checked")){
		isremember = 1;
	}
	var phone = $("#phone").val();
 	var password = $("#password").val();
		if(phone==""){
			$('#beError').text("请输入手机号码").show();
		 	return false;
		}else if(!isMobile(phone)){
			$('#beError').text("手机号格式错误").show();
		 	return false;
		}else if(password==""){
			$('#pwdError').text("请输入密码").show();
		 	return false;
		}else if(password.length<4||password.length>16){
			$('#pwdError').text("请输入6-16位密码，字母区分大小写").show();
		 	return false;
		}else{
			$.ajax({
				   type:"post",
				   url:"<%=path %>/top/individualuser/login",
				   dataType:"json", 
				   data:{
					   "phone":phone ,
					   "password":password ,
					   "isremember": isremember
				   },
				   success:function(data){
					   if(data.err==0){
		            		window.location.href = ctx+"/jsp/resume.jsp?userid="+data.userid;			            		
		            	}else if(data.err==1){
							$('#pwdError').text(data.msg).show();
		            	}
		            	else if(data.err==2){
							$('#beError').text(data.msg).show();
		            	}
				   }
			});
		}
}
</script>
</body>
</html>
