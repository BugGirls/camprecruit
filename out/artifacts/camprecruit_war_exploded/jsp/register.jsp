<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
<link rel="alternate" media="handheld"  />
<!-- end 云适配 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>注册-营才网-最专业的营地服务平台</title>
<meta property="qc:admins" content="23635710066417756375" />
<meta content="营才网是3W旗下的营地人才领域垂直招聘网站,营地职业机会尽在营才网" name="description">
<meta content="营才,营才网,营才招聘,拉钩, 拉钩网 ,营地人才招聘,营才网招聘, 移动营地人才招聘, 垂直营地人才招聘, 微信招聘, 微博招聘, 营才官网, 营才百科,跳槽, 高薪职位, 营地人才圈子, IT招聘, 职场招聘, 猎头招聘,O2O招聘, LBS招聘, 社交招聘, 校园招聘, 校招,社会招聘,社招" name="keywords">

<meta name="baidu-site-verification" content="QIQ6KC1oZ6" />
<style type="text/css">
#loginForm input#phone{width:296px;font-size:16px; color:#777; border:none;}
#loginForm input#verifycode{width:200px;font-size:16px; color:#777; border:none;}
#loginForm input#individualpwd{width:296px;font-size:16px; color:#777; border:none;}
#individualLogin:hover {
    background: #00694e;
}
#individualLogin {
    width: 155px;
    height: 46px;
    font-size: 16px;
    line-height: 46px;
    clear: both;
    float: left;
    background: #019875;
    color: #fff;
    border: none;
    text-align: center;
}
</style>
<!-- <div class="web_root"  style="display:none">h</div> -->
<script type="text/javascript">
var ctx = "<%=requestPath%>";
console.log(ctx);
</script>
<link rel="Shortcut Icon" href="h/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="style/css/style.css"/>

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>

<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script type="text/javascript" src="style/js/core.min.js"></script>
<script type="text/javascript" src="js/validate_mine.js"></script>


<script type="text/javascript">
var youdao_conv_id = 271546; 
</script> 
<script type="text/javascript" src="style/js/conv.js"></script>
</head>

<body id="login_bg">
	<div class="login_wrapper">
		<div class="login_header">
        	<a href="h/"><img src="style/images/logo_white.png" width="285" height="62" alt="拉勾招聘" /></a>
            <div id="cloud_s"><img src="style/images/cloud_s.png" width="81" height="52" alt="cloud" /></div>
            <div id="cloud_m"><img src="style/images/cloud_m.png" width="136" height="95"  alt="cloud" /></div>
        </div>
        
		<div class="login_box" style="background:#eafffd url(style/css/img/img_zhuce_bg.png) bottom right no-repeat;">
        	<form id="loginForm">
        		<ul class="register_radio clearfix">
		            <li>
		            	找工作
		              	<input type="radio" value="0" name="type"  class="current" checked="checked"/> <em></em>
		            </li>
		            <li>
		           	           招人
		              	<input type="radio" value="1" name="type" /> 
		            </li>
		        </ul> 
		        <div id="individuallogin">
		        	<input type="text" id="phone" name="phone" tabindex="1" placeholder="请输入手机号" />
			        <span class="error" style="display:none;" id="phoneError"></span>
			        <span id="verify"><input type="text" id="verifycode" name="verifycode" tabindex="3" placeholder="请输入验证码" /><a href="javascript:getSmsCode();" id="codebtn" style="margin-left: 5px;padding-left: 5px;    padding-right: 5px;">获取验证码</a></span>
	                <span class="error" id="codeError"></span>
	                <input type="password" id="individualpwd" name="individualpwd" tabindex="4" placeholder="请输入密码" />
	                <span class="error" id="pwdError"></span>
	            	<label class="fl registerJianJu" for="checkbox">
	            		<input type="checkbox" id="checkbox" name="checkbox" checked  class="checkbox valid" />我已阅读并同意<a href="h/privacy.html" target="_blank">《营才网用户协议》</a>
	           		</label>
	                <input type="button" id="individualLogin" value="注 &nbsp; &nbsp; 册" />
		        </div>
		        
		        <div id="companylogin" style="display: none;">
            	<input type="text" id="email" name="email" tabindex="1" placeholder="请输入常用邮箱地址" />
                <span class="error" style="display:none;" id="beError"></span>
                <input type="password" id="password" name="password" tabindex="2" placeholder="请输入密码" />
            	<label class="fl registerJianJu" for="checkbox">
            		<input type="checkbox" id="checkbox" name="checkbox" checked  class="checkbox valid" />我已阅读并同意<a href="h/privacy.html" target="_blank">《营才网用户协议》</a>
           		</label>
                <input type="submit" id="submitLogin" value="注 &nbsp; &nbsp; 册" />
                
                <input type="hidden" id="callback" name="callback" value=""/>
                <input type="hidden" id="authType" name="authType" value=""/>
                <input type="hidden" id="signature" name="signature" value=""/>
                <input type="hidden" id="timestamp" name="timestamp" value=""/>
                </div>
            </form>
            <div class="login_right">
            	<div>已有帐号</div>
            	<a  href="javascript: tologin();"  class="registor_now">直接登录</a>
            </div>
        </div>
        <div class="login_box_btm"></div>       
    </div>
    
    <script type="text/javascript">
    var type = 0;
    var isvolidate = false;
    $(document).ready(function(e) {
    	$('.register_radio li input').click(function(e){
    		type = $(this).val();
    		$(this).parent('li').addClass('current').append('<em></em>').siblings().removeClass('current').find('em').remove();
    		if(type==0){
    			$('#companylogin').hide();
    			$('#individuallogin').show();
    		}else{
    			$('#companylogin').show();
    			$('#individuallogin').hide();
    		}
    	});
    	
    	$('#email').focus(function(){
    		$('#beError').hide();
    	});
    	$('#phone').focus(function(){
    		$('#beError').hide();
    	});
    	$('#verifycode').focus(function(){
    		$('#codeError').hide();
    	});
    	
    			//验证表单
   	    	 $("#loginForm").validate({
   	    	        rules: {
   	    	        	type:{
   	    	        		required: true
   	    	        	},
   			    	   	email: {
   			    	    	required: true,
   			    	    	email: true
   			    	   	},
   			    	   	password: {
   			    	    	required: true,
   			    	    	rangelength: [6,16]
   			    	   	},
   			    	   	checkbox:{required:true}
   			    	},
   			    	messages: {
   			    		type:{
   	    	        		required:"请选择使用目的"
   	    	        	},
   			    	 	email: {
   			    	    	required: "请输入常用邮箱地址",
   			    	    	email: "请输入有效的邮箱地址，如：vivi@lagou.com"
   			    	   	},
   			    	   	password: {
   			    	    	required: "请输入密码",
   			    	    	rangelength: "请输入6-16位密码，字母区分大小写"
   			    	   	},
   			    	   	checkbox: {
   			    	    	required: "请接受用户协议"
   			    	   	}
   			    	},
   			    	errorPlacement:function(label, element){/* 
   			    		if(element.attr("type") == "radio"){
   			    			label.insertAfter($(element).parents('ul')).css('marginTop','-20px');
   			    		}else if(element.attr("type") == "checkbox"){
   			    			label.inserresult.contenttAfter($(element).parent()).css('clear','left');
   			    		}else{
   			    			label.insertAfter(element);
   			    		} */			    		
   			    		/*modify nancy*/
   			    		if(element.attr("type") == "radio"){
   			    			label.insertAfter($(element).parents('ul')).css('marginTop','-20px');
   			    		}else if(element.attr("type") == "checkbox"){
   			    			label.insertAfter($(element).parent()).css('clear','left');
   			    		}else{
   			    			label.insertAfter(element);
   			    		};	
   			    	},
   			    	submitHandler:function(form){
   			    		var type =$('input[type="radio"]:checked',form).val();
   			    		var email =$('#email').val();
   			    		var password =$('#password').val();
   			    		
   			    		var callback = $('#callback').val();
   			    		var authType = $('#authType').val();
   			    		var signature = $('#signature').val();
   			    		var timestamp = $('#timestamp').val();
   			    		
   			    		$(form).find(":submit").attr("disabled", true);

			    		console.log(type)
				            $.ajax({
				            	type:'POST',
				            	data: {email:email,password:password,type:type, callback:callback, authType:authType, signature:signature, timestamp:timestamp},
				            	url:ctx+'/top/companyuser/zhuce',
				            	dataType:'json'
				            }).done(function(result) {
				            	if(result.success){
				            		window.location.href = ctx+"/jsp/company_register.jsp?userid="+result.userid;			            		
   			    				            		
   				            	}else{
   									$('#beError').text(result.msg).show();
   				            	}
   				            	$(form).find(":submit").attr("disabled", false);			           		
   				            });
   			    		
   			        }  
   	    	});
    	
    	$("#individualLogin").click(function(){
	    	var phone = $("#phone").val();
			var individualpwd = $("#individualpwd").val();
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
				}else if(individualpwd==""){
					$('#pwdError').text("请输入密码").show();
				 	return false;
				}else if(individualpwd.length<6||individualpwd.length>16){
					$('#pwdError').text("请输入6-16位密码，字母区分大小写").show();
				 	return false;
				}else if(!$("#checkbox").is(":checked")){
					$('#pwdError').text("请接受用户协议").show();
				 	return false;
				}else{
					$.ajax({
	    				   type:"post",
	    				   url:"<%=path %>/top/individualuser/zhuce",
	    				   dataType:"json", 
	    				   data:{
	    					   "phone":phone ,
	    					   "password":individualpwd
	    				   }
	    			}).done(function(result) {
				            	if(result.err==0){
				            		window.location.href = ctx+"/jsp/completebaseinfo.jsp?userid="+result.userid;			            		
				            	}else{
									$('#beError').text(result.msg).show();
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
	    	 
	    	 function tologin(){
	    		 if(type == 0){
	    			 window.location.href="login.jsp";
	    		 }else{
	    			 window.location.href="clogin.jsp";
	    		 }
	    	 }
    </script>
</body>
</html>
