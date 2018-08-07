<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>

<!DOCTYPE html>
<html>
<head>
<script id="allmobilize" charset="utf-8" src="style/js/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" />
<!-- end 云适配 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>企业用户登录-营才网-最专业的营地服务平台</title>
<meta property="qc:admins" content="23635710066417756375" />
<meta content="营才网是3W旗下的营地人才领域垂直招聘网站,营地职业机会尽在营才网" name="description">
<meta content="营才,营才网,营才招聘,营才, 营才网 ,营地人才招聘,营才网招聘, 移动营地人才招聘, 垂直营地人才招聘, 微信招聘, 微博招聘, 营才官网, 营才百科,跳槽, 高薪职位, 营地人才圈子, IT招聘, 职场招聘, 猎头招聘,O2O招聘, LBS招聘, 社交招聘, 校园招聘, 校招,社会招聘,社招"
	name="keywords">

<meta name="baidu-site-verification" content="QIQ6KC1oZ6" />

<!-- <div class="web_root"  style="display:none">h</div> -->
<script type="text/javascript">
var ctx = "<%=requestPath%>";
console.log(ctx);
</script>
<link rel="Shortcut Icon" href="h/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="style/css/style.css" />

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>

<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script type="text/javascript" src="style/js/core.min.js"></script>

</head>

<body id="login_bg">
	<div class="login_wrapper">
		<div class="login_header">
			<a href="h/"><img src="style/images/logo_white.png" width="285"
				height="62" alt="营才招聘" /></a>
			<div id="cloud_s">
				<img src="style/images/cloud_s.png" width="81" height="52"
					alt="cloud" />
			</div>
			<div id="cloud_m">
				<img src="style/images/cloud_m.png" width="136" height="95"
					alt="cloud" />
			</div>
		</div>

		<input type="hidden" id="resubmitToken" value="" />
		<div class="login_box">
			<form id="loginForm">
				<input type="text" id="email" name="email" value="" tabindex="1" placeholder="请输入登录邮箱地址" /> 
					<input type="password" id="password" name="password" tabindex="2" placeholder="请输入密码" /> 
					<span class="error" style="display: none;" id="beError"></span> 
					<label class="fl" for="remember">
					<input type="checkbox" id="remember" value="" checked="checked" name="autoLogin" /> 记住我</label> 
					<a href="c_resetpwd_step1.jsp" class="fr" target="_blank">忘记密码？</a>

				<!--<input type="submit" id="submitLogin" value="登 &nbsp; &nbsp; 录" />-->
				<input type="submit" style="color: #fff;"  class="submitLogin" value="登 &nbsp; &nbsp; 录" />
				<input type="hidden" id="callback" name="callback" value="" /> 
				<input type="hidden" id="authType" name="authType" value="" /> 
				<input type="hidden" id="signature" name="signature" value="" /> 
				<input type="hidden" id="timestamp" name="timestamp" value="" />
			</form>
			<div class="login_right">
				<div>还没有营才帐号？</div>
				<a href="register.jsp" class="registor_now">立即注册</a>
			</div>
		</div>
		<div class="login_box_btm"></div>
	</div>

	<script type="text/javascript">
$(function(){
	//验证表单
	 	$("#loginForm").validate({
	 		/* onkeyup: false,
	    	focusCleanup:true, */
	        rules: {
	    	   	email: {
	    	    	required: true,
	    	    	email: true
	    	   	},
	    	   	password: {
	    	    	required: true
	    	   	}
	    	},
	    	messages: {
	    	   	email: {
	    	    	required: "请输入登录邮箱地址",
	    	    	email: "请输入有效的邮箱地址，如：vivi@lagou.com"
	    	   	},
	    	   	password: {
	    	    	required: "请输入密码"
	    	   	}
	    	},
	    	submitHandler:function(form){
	    		if($('#remember').prop("checked")){
	      			$('#remember').val(1);
	      		}else{
	      			$('#remember').val(null);
	      		}
	    		var email = $('#email').val();
	    		var password = $('#password').val();
	    		var remember = $('#remember').val();
	    		
	    		
	    		$(form).find(":submit").attr("disabled", true);
	            $.ajax({
	            	type:'POST',
	            	data:{email:email,password:password,autoLogin:remember},
	            	url:ctx+'/top/companyuser/login',
	            	dataType:'json'
	            }).done(function(result) {
					if(result.success){
						console.log(result)
						
						if(result.state==0){
							window.location.href = ctx+"/jsp/company_register.jsp?userid="+result.userid;
						}else if(result.state==3){
							window.location.href = ctx+"/jsp/company_register.jsp?userid="+result.userid+"&state="+result.state;
						}else{
							if(result.stage==0)
								window.location.href = ctx+"/jsp/company_wanshan1.jsp?userid="+result.userid;
							else
								window.location.href = ctx+"/jsp/company_center.jsp?userid="+result.userid;
						}
						
					}else{
						$('#beError').text(result.msg).show();
					}
					$(form).find(":submit").attr("disabled", false);
	            }); 
	        }  
		});
})
</script>
</body>
</html>
