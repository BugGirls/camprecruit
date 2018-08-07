<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%
String path = request.getContextPath();
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
String email = request.getParameter("email");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script id="allmobilize" charset="utf-8" src="style/js/allmobilize.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>找回密码-营才网</title>
<link rel="stylesheet" type="text/css" href="style/css/style.css"/>

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>

<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script type="text/javascript" src="style/js/core.min.js"></script>
<script type="text/javascript" src="style/js/analytics.js"></script>
<script type="text/javascript" src="style/js/conv.js"></script>

<style>
	#pswForm input#email{width:296px;font-size:16px; color:#777; border:none;}
#pswForm input#verifycode{width:200px;font-size:16px; color:#777; border:none;}
</style>
</head>

<body id="login_bg">
	<div class="login_wrapper">
		<div class="login_header">
        	<a href="index.html"><img src="style/images/logo_white.png" width="285" height="62" alt="拉勾招聘" /></a>
            <div id="cloud_s"><img src="style/images/cloud_s.png" width="81" height="52" alt="cloud" /></div>
            <div id="cloud_m"><img src="style/images/cloud_m.png" width="136" height="95"  alt="cloud" /></div>
        </div>
        
    	<input type="hidden" id="resubmitToken" value="" />
     	<div class="find_psw" id="findPwd1">
            <form id="pswForm" >
            <span></span>
           		<input type="password" name="password" id="password" tabindex="1" value="" placeholder="请输入新密码" />
           		<span class="error" style="display:none;" id="pwdError"></span>
           		<input type="password" name="confirmpwd" id="confirmpwd" tabindex="1" value="" placeholder="请重新输入密码" />
           		 <span class="error"  style="display:none;"  id="confirmError"></span>
                 <input type="button" id="submitLogin" value="修改密码" />
            </form>
        </div>
    </div>
    
<script type="text/javascript">
    $(document).ready(function() {
    	$('#pswForm input[type="password"]').focus(function(){
    		$(this).siblings('.error').hide();
    	});
    	$("#submitLogin").click(function(){
	    	var password = $("#password").val();
			var confirmpwd = $("#confirmpwd").val();
		 	if(password==""){
				$('#pwdError').text("请输入密码").show();
			 	return false;
			}else if(password.length<6||password.length>16){
				$('#pwdError').text("请输入6-16位密码，字母区分大小写").show();
			 	return false;
			}else if(confirmpwd==""){
				$('#confirmError').text("请重新输入密码").show();
			 	return false;
			}else if(confirmpwd.length<6||confirmpwd.length>16){
				$('#confirmError').text("两次输入的密码不一致").show();
			 	return false;
			}else{
				$.ajax({
   				   type:"post",
   				   url:"<%=path %>/top/companyuser/resetPwd",
   				   dataType:"json", 
   				   data:{
   					   "email":'<%=email%>' ,
   					   "password":password 
   				   }
	   			}).done(function(result) {
	            	if(result.success){
	            		alert("密码修改成功，请重新登录");
	            		window.location.href = "<%=basePath%>clogin.jsp";			            		
	            	}else{
						$('#pwdError').text(result.msg).show();
	            	}
	            });
			}
    	});
    });
    </script>
</body>
</html>
