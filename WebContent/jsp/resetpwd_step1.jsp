<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%
String path = request.getContextPath();
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
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
<!-- <script type="text/javascript" src="style/js/conv.js"></script> -->
<script type="text/javascript" src="js/validate_mine.js"></script>

<script type="text/javascript">
var youdao_conv_id = 271546; 
</script> 
<style>
	#pswForm input#phone{width:296px;font-size:16px; color:#777; border:none;}
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
           		<input type="text" name="phone" id="phone" tabindex="1" value="" placeholder="请输入注册时使用的手机号" />
           		 <span class="error" style="display:none;" id="phoneError"></span>
			     <input type="text" id="verifycode" name="verifycode" tabindex="3" placeholder="请输入验证码" /><a href="javascript:getSmsCode();" id="codebtn" style="color: #019875;margin-left: 5px;padding-left: 5px;    padding-right: 5px;">获取验证码</a>
	             <span class="error" id="codeError"></span>
                 <input type="button" id="submitLogin" value="找回密码" />
            </form>
        </div>
    </div>
    
<script type="text/javascript">
    $(document).ready(function() {
    	$('#pswForm input[type="text"]').focus(function(){
    		$(this).siblings('.error').hide();
    	});
    	$("#submitLogin").click(function(){
    		var phone = $("#phone").val();
			var code = $("#verifycode").val();
   			if(phone==""){
   				$('#phoneError').text("请输入手机号码").show();
   			 	return false;
   			}else if(!isMobile(phone)){
   				$('#phoneError').text("手机号格式错误").show();
   			 	return false;
   			}else if(code==""||code.length<4){
   				$('#codeError').text("请输入验证码").show();
   			 	return false;
   			}else if(!isvolidate){
   				$('#codeError').text("验证码输入错误").show();
   			 	return false;
   			}else{
   				$.ajax({
    				   type:"post",
    				   url:"<%=path %>/top/individualuser/checkResetPhone",
    				   dataType:"json", 
    				   data:{
    					   "phone":phone 
    				   }
    			}).done(function(result) {
			            	if(result.err==0){
			            		window.location.href = "resetpwd_step2.jsp?phone="+phone;			            		
			            	}else{
								$('#phoneError').text(result.msg).show();
			            	}
			    });
   			}
    		
    	});
    	
    	
    	$("#verifycode").keyup(function(){
			var code = $("#verifycode").val();
			if(code.length!=4){
				return;
			}
			
			$.ajax({
				   type:"post",
				   url:"<%=path %>/jsp/phonecheck",
				   dataType:"json", 
				   data:{
					   "phone":$("#phone").val() ,
					   "code":code
				   },
				   success:function(data){ 
					   console.log(data)
				  	   if(data.success){  
			                $('#codeError').text(data.msg).show();
			                isvolidate = true;
				  		}else{ 
				  			$('#codeError').text(data.msg).show();
			                isvolidate = false;
				  	  } 
			       }  
			});
		});
    	
    	//验证表单

	    	 $("#pswForm").validate({
	    	        rules: {
			    	   	email: {
			    	    	required: true,
			    	    	email: true
			    	   	}
			    	},
			    	messages: {
			    	   	email: {
			    	    	required: "请输入注册时使用的邮箱地址",
			    	    	email: "请输入有效的邮箱地址，如：vivi@lagou.com"
			    	   	}
			    	},
			    	submitHandler:function(form){ 
			    		$(form).find(":submit").attr("disabled", true);
			            //form.submit();
			    		$.ajax({
							dataType : "json",
							url : getContextPath() + "/sys/sysuser/retrievePassword",
							type : "post",
							data : {
								email : $('#email').val()
							},
							complete : function(xmlRequest) {
								var returninfo = eval("(" + xmlRequest.responseText + ")");
								if (returninfo.result == 1) {
									$("#retrieveTip").html("找回密码邮件已经发到你的邮箱");
								} else if (returninfo.result == -1) {
									$("#retrieveTip").html("密保邮箱不存在，请重新输入");
								} else if (returninfo.result == -2) {
									$("#retrieveTip").html("发送邮件失败，请重新发送");
								}
							}
						});
			        }  
	    	});
    	});
    
    function getSmsCode(){
		  if(!isMobile($("#phone").val())){
			  $('#beError').text("手机号格式错误").show();
			  return;
		  }
		  if( $('#codebtn').hasClass('unable')){
		    return;
		  }
		  
		  var i = 120;
		  $('#codeError').text("验证码已发送，请查收短信").show();
		    $('#codebtn').html("已发送").addClass('unable');
		    var inter = setInterval(function () {
		      $('#codebtn').html(--i + 's');
		      if (i == 0) {
		        $('#codebtn').html('重新获取').removeClass('unable');
		        clearInterval(inter);
		      }
		    }, 1000);
		  
		  $.ajax({
			   type:"post",
			   url:"<%=path %>/jsp/phonevolidate",
			   dataType:"json", 
			   data:{
				   "phone":$("#phone").val() 
			   },
			   success:function(data){ 
				   console.log("data: "+data)
			  	   if(data.success){  
			  		
			  		}else{ 
			  			
			  	    } 
		       }  
		});
	}
    </script>
</body>
</html>
