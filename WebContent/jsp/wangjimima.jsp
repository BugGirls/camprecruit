<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>冒险家</title>
<link href="css/zhuce.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.js"></script>
<script src="js/validate_mine.js"></script>
</head>

<body>
<!--注册-->
<div class="bigbox" style="height:560px;">
	<div class="top">
    	<div class="line1"></div>
        <div class="line2"></div>
        <div class="name1">忘记密码</div>
        <div class="name2">FORGET PASSWORD</div>
    </div>
    
    <div class="name3"><a href="index">冒险家</a>欢迎您</div>
     
    <input type="text" id="phone" name="phone" placeholder="手机号" class="phone-in" maxlength="11"/> 
    <input type="text" id="npwd" name="pwd" placeholder="新密码" class="phone-in" maxlength="18"/> 
    <input type="text" id="npwd2" name="pwd" placeholder="确认密码" class="phone-in" maxlength="18"/> 
    <div class="box5">
    	
    </div>
    <input type="text" id="yanzhengma" name="yanzheng" placeholder="验证码" maxlength="4" class="yanzheng-in"/>
    <div class="box1"><img id="ocode"  src="../VerifyCodeServlet"></div>
    <a  id="newcode" class="fanhui"><img style="border:none;cursor: pointer;" src="images/fanhui.jpg"/></a>
    <input type="text" name="dongtai" id="indongtaima" placeholder="动态码"  maxlength="6" class="dongtai-in"/>
    <div class="box6" style="top:420px;"><a style="cursor: pointer;  " id="dongtaima"  >获取动态码</a></div>
    <div class="anniu"><a style="cursor: pointer;" id="sub" class="liji">确定修改</a></div> 
    <div class="number">已有账号<a href="login.jsp" class="zhuce">立即登录</a></div>
    <div class="box3">
    	<div class="line3"></div>
        <a href="#" class="hezuo">合作方登录账号</a>
        <div class="line4"></div>
    </div>
    <div class="box4">
    	<a href="#" class="photo1"><img style="border:none;" src="images/qq.jpg"/></a>
        <a href="#" class="QQ">使用QQ号登录</a>
    </div>
</div>

<script src="js/jquery-1.11.1.min.js"></script>
<script src="layer/layer.js"></script>
<script type="text/javascript">
 
var i= 120; 
var c=1;

var initclear = null ;
$("#newcode").click(function(){  
	//$(".box1").html("<img id='ocode'  src='../VerifyCodeServlet'>"); 
	var time=new Date().getTime(); 
	$("#ocode").attr("src","../VerifyCodeServlet?t"+time);
});

 $("#dongtaima").click(function(){
	if($("#phone").val()==""){
		layer.alert("请输入手机号", {icon:0}) ; 
		return false;
	}else if(!isMobile($("#phone").val())){
		layer.alert("手机号不正确", {icon:0}); 
		return false;
	}else if($("#newpwd").val()==""||$("#newpwd").val().length<6){
		layer.alert("请输入新密码", {icon:0}) ; 
		return false;
	}else if($("#newpwd").val()!=$("#newpwd2").val()){
		layer.alert("两次输入不一样", {icon:0}) ; 
		return false;
	}else if($("#yanzhengma").val()==""){
		layer.alert("请输入验证码", {icon:0});  
		return false;
	}else { 
		if(c!=0){  
		$.ajax({
   		   type:"post",
 		   url:"yanzhengma",
 		   dataType:"json", 
		   data:{"yzm":$("#yanzhengma").val()},
		   success:function(data){ 
		  	   if(data.err=="a"){
		  		 layer.alert(data.msg, {icon:0});   
		  	   }else if(data.err==0){
		  			c=0;
		  			$.ajax({
		     		   type:"post",
		 	 		   url:"dongtaima",
		 	 		   dataType:"json", 
		 			   data:{"phone":$("#phone").val()},
		 			   success:function(data){ 
		 			  	   if(data.err!=0){   
		 			  		 layer.alert(data.err, {icon:0});    
		 			  		   return false;
		 			  		}else{   
		 			  			initclear = setInterval(jishi,1000); 
		 			  	  } 
		 		       }  
		  	   		});  
		  	  } 
	       }  
 	   	});
		}
	} 
 });
 function jishi(){ 
	 i=i-1;
	 if(i!=0){
		$("#dongtaima").html(i); 
		$("#dongtaima").attr("onclick",""); 
	 }else{ 
		 if (initclear != null){
			 clearInterval(initclear);
		 } 
		 c=1;
		 i=10; 
		 $("#dongtaima").html("请重新获取");
	 
	 } 
	 
 }
 
	$("#sub").click(function(){
		if($("#phone").val()==""){
	 		layer.alert("请输入手机号", {icon:0});  
			return false;
		}else if(!isMobile($("#phone").val())){
			layer.alert("手机号不正确", {icon:0});  
			return false;
		} else if($("#yanzhengma").val()==""){
			layer.alert("请输入验证码", {icon:0}); 
			return false;
		}else if($("#indongtaima").val()==""){
			layer.alert("请输入动态码", {icon:0});  
			return false;
		}else if($("#newpwd").val()==""||$("#newpwd").val().length<6){
			layer.alert("请输入新密码", {icon:0}) ; 
			return false;
		}else if($("#newpwd").val()!=$("#newpwd2").val()){
			layer.alert("两次输入不一样", {icon:0}) ; 
			return false;
		}else{
			//phone pwd yanzhengma indongtaima
			$.ajax({
	     		   type:"post",
	 	 		   url:"wangjimima",
	 	 		   dataType:"json", 
	 			   data:{"phone":$("#phone").val(),"pwd":("#newpwd").val(), "yanzhengma":$("#yanzhengma").val(),"indongtaima":$("#indongtaima").val()},
	 			   success:function(data){ 
	 			  	   if(data.err==0){  
				  		 layer.alert("修改成功，即将跳转登录界面", {icon:1}) ;
				  		 setTimeout('location.href="login.jsp"',3000);
	 					
	 			  		}else if(data.err==1){
		 			  		 c=1;
		 					 i=120; 
		 					 $("#dongtaima").html("请重新获取");
		 			  	}else{ 
	 			  			layer.alert(data.msg, {icon:0});    
	 			  			if(data.err==2||data.err==4){
	 			  				$("#ocode").attr("src","../VerifyCodeServlet?"+time);
	 			  			} 
	 			  		   return true;
	 			  	  } 
	 		       }  
	  	    });  
		}
 	}); 
</script>
</body>
</html>
