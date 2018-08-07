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
<div class="bigbox">
	<div class="top">
    	<div class="line1"></div>
        <div class="line2"></div>
        <div class="name1">用户注册</div>
        <div class="name2">USER REGISTRATION</div>
    </div> 
    <div class="name3"><a href="index" class="underline">冒险家</a>感谢您的注册</div>
    
    <input type="text" id="phone" name="phone" placeholder="手机号" class="phone-in" maxlength="11"/>
    <input type="password" id="pwd" name="password" placeholder="输入6~18位密码" maxlength="18" class="password-in"/>
    <div class="box5">
    	
    </div>
    <input type="text" id="yanzhengma" name="yanzheng" placeholder="验证码" maxlength="4" class="yanzheng-in"/>
    <div class="box1"><img id="ocode"  src="../VerifyCodeServlet"></div>
    <a  id="newcode" class="fanhui"><img style="border:none;" src="images/fanhui.jpg"/></a>
    <input type="text" name="dongtai" id="indongtaima" placeholder="动态码"  maxlength="6" class="dongtai-in"/>
    <div class="box6"><a style="cursor: pointer;" id="dongtaima"  >获取动态码</a></div>
    <div class="box2"><input type="checkbox" id="check" checked="checked" class="fuxuan"/><span class="mianfei"><a id="xieyi"style="cursor: pointer;">冒险家网站用户协议</a></span></div>
    <div class="anniu"><a style="cursor: pointer;" id="zhuce" class="liji">立即注册</a></div>
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
$("#xieyi").mouseover(function(){
	 $(this).css("color","red");
});
$("#xieyi").mouseout(function(){
	 $(this).css("color","#9a9a9a");
});
var i= 120; 
var c=1;

var initclear = null ;
$("#newcode").click(function(){  
	//$(".box1").html("<img id='ocode'  src='../VerifyCodeServlet'>"); 
	var time=new Date().getTime(); 
	$("#ocode").attr("src","../VerifyCodeServlet?"+time);
});

$("#phone").blur(function(){
	if($("#phone").val()==""||!isMobile($("#phone").val())){
		$("#phone").css({"border":"1px solid red",
			"box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-moz-box-shadow":"0 0 5px rgba(231, 26, 41, 1)"});
	}else{
		$("#phone").css({"border":"1px solid #00a52d",
			"box-shadow":"0 0 5px rgba(93, 1196, 121, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(93, 196, 121, 1)",
			"-moz-box-shadow":"0 0 5px rgba(93, 196, 121, 1)"});
	} 
		
}); 
$("#pwd").blur(function(){
	if($("#pwd").val()==""||$("#pwd").val().length<6){
		$("#pwd").css({"border":"1px solid red",
			"box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-moz-box-shadow":"0 0 5px rgba(231, 26, 41, 1)"});
	}else{
		$("#pwd").css({"border":"1px solid #00a52d",
			"box-shadow":"0 0 5px rgba(93, 1196, 121, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(93, 196, 121, 1)",
			"-moz-box-shadow":"0 0 5px rgba(93, 196, 121, 1)"});
	} 
		
});
$("#yanzhengma").blur(function(){
	if($("#yanzhengma").val()==""||$("#yanzhengma").val().length<4){
		$("#yanzhengma").css({"border":"1px solid red",
			"box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-moz-box-shadow":"0 0 5px rgba(231, 26, 41, 1)"});
	}else{
		$("#yanzhengma").css({"border":"1px solid #00a52d",
			"box-shadow":"0 0 5px rgba(93, 1196, 121, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(93, 196, 121, 1)",
			"-moz-box-shadow":"0 0 5px rgba(93, 196, 121, 1)"});
	} 
		
});
$("#indongtaima").blur(function(){
	if($("#indongtaima").val()==""||$("#indongtaima").val().length<6){
		$("#indongtaima").css({"border":"1px solid red",
			"box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-moz-box-shadow":"0 0 5px rgba(231, 26, 41, 1)"});
	}else{
		$("#indongtaima").css({"border":"1px solid #00a52d",
			"box-shadow":"0 0 5px rgba(93, 1196, 121, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(93, 196, 121, 1)",
			"-moz-box-shadow":"0 0 5px rgba(93, 196, 121, 1)"});
	}  	
});


 $("#dongtaima").click(function(){
	if($("#phone").val()==""){
		layer.alert("请输入手机号", {icon:0}) ;  
		return false;
	}else if(!isMobile($("#phone").val())){
		layer.alert("手机号不正确", {icon:0}); 
		return false;
	}else if($("#pwd").val()==""||$("#pwd").val().length<6){
		layer.alert("请输入密码", {icon:0}); 
		return false;
	}else if($("#yanzhengma").val()==""){
		layer.alert("请输入验证码", {icon:0});  
		$("#yanzhengma").css({"border":"1px solid red",
			"box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(231, 26, 41, 1)",
			"-moz-box-shadow":"0 0 5px rgba(231, 26, 41, 1)"});
		return false;
	}else {
		$("#yanzhengma").css({"border":"1px solid #00a52d",
			"box-shadow":"0 0 5px rgba(93, 1196, 121, 1)",
			"-webkit-box-shadow":"0 0 5px rgba(93, 196, 121, 1)",
			"-moz-box-shadow":"0 0 5px rgba(93, 196, 121, 1)"});
		if(c!=0){ 
		 
			$.ajax({
	    		   type:"post",
		 		   url:"yanzhengma",
		 		   dataType:"json", 
				   data:{"yzm":$("#yanzhengma").val()},
				   success:function(data){ 
				  	   if(data.err==1){ 
				  		 layer.alert("验证码错误", {icon:0});   
				  		   return false;
				  		}else{
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
		 i=120; 
		 $("#dongtaima").html("请重新获取");
	 
	 } 
	 
 }
 
 
	$("#zhuce").click(function(){
		if(!$("#check").is(":checked")){
			layer.alert("请同意冒险家网站用户协议", {icon:0});   
			return false; 
	 	}else if($("#phone").val()==""){
	 		layer.alert("请输入手机号", {icon:0});  
			return false;
		}else if(!isMobile($("#phone").val())){
			layer.alert("手机号不正确", {icon:0});  
			return false;
		}else if($("#pwd").val()==""||$("#pwd").val().length<6){
			layer.alert("请输入密码", {icon:0});  
			return false;
		}else if($("#yanzhengma").val()==""){
			layer.alert("请输入验证码", {icon:0}); 
			return false;
		}else if($("#indongtaima").val()==""){
			layer.alert("请输入动态码", {icon:0});  
			return false;
		}else{
			//phone pwd yanzhengma indongtaima
			$.ajax({
	     		   type:"post",
	 	 		   url:"zhuce",
	 	 		   dataType:"json", 
	 			   data:{"phone":$("#phone").val(),"pwd":$("#pwd").val(),"yanzhengma":$("#yanzhengma").val(),"indongtaima":$("#indongtaima").val()},
	 			   success:function(data){ 
	 			  	   if(data.err==0){   
	 			  		layer.alert("注册成功", {icon:1}); 
	 			  		window.location.href="index";
	 			  	 	return;
	 			  		}else if(data.err==1||data.err==4){
	 			  		 c=1;
	 					 i=120; 
	 					 clearInterval(initclear); 
	 					 $("#dongtaima").html("请重新获取");
	 					layer.alert(data.msg, {icon:0});   
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
	 
	$("#xieyi").click(function(){
		$.ajax({
  		  	 type:"post",
	 		 url:"../top/jianjie/getxy",
	 		 dataType:"json", 
			 success:function(data){  
				 layer.open({
					    type: 1,
					    title:"冒险家网站用户协议",
					    skin: 'layui-layer-rim', //加上边框
					    area: ['720px', '740px'], //宽高
					    content: data.xieyi.content
					});
		     }  
	    });   
	});
	
</script>
</body>
</html>
