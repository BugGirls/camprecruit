<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%@page import="com.jeefw.model.sys.IndividualUser"%>
<%
String path = request.getContextPath();
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
IndividualUser user = (IndividualUser)request.getSession().getAttribute("individualuser");
String userid = request.getParameter("userid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script id="allmobilize" charset="utf-8" src="style/js/allmobilize.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>完善基本信息-营才网</title>
<link rel="stylesheet" type="text/css" href="style/css/style.css"/>

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>
<link href="style/css/external.min.css" type="text/css" rel="stylesheet">
<link href="style/css/popup.css" type="text/css" rel="stylesheet">
<link href="city/css/pick-pcc.min.1.0.1.css" rel="stylesheet">

<script type="text/javascript" src="style/js/ajaxfileupload.js"></script>
<script src="style/js/additional-methods.js" type="text/javascript"></script>
<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script type="text/javascript" src="style/js/core.min.js"></script>
<script type="text/javascript" src="style/js/analytics.js"></script>
<!-- <script type="text/javascript" src="style/js/conv.js"></script> -->
<script type="text/javascript" src="js/validate_mine.js"></script>
<script type="text/javascript" src="city/js/pick-pcc.min.1.0.1.js"></script>
<script type="text/javascript">
var youdao_conv_id = 271546; 
var ctx = "<%=requestPath%>";
var userid = "<%=userid%>";
var frompage=1;
console.log(ctx);
</script> 
</head>

<body id="login_bg">
	<div class="login_wrapper" style="padding-top:50px;">
		<div class="login_header">
        	<a href="index.html"><img src="style/images/logo_white.png" width="285" height="62" alt="营才网" /></a>
            <div id="cloud_s"><img src="style/images/cloud_s.png" width="81" height="52" alt="cloud" /></div>
            <div id="cloud_m"><img src="style/images/cloud_m.png" width="136" height="95"  alt="cloud" /></div>
        </div>
        
    	<input type="hidden" id="resubmitToken" value="" />
    	<div class="profile_box" > 
    	<h2>基本信息</h2>
     	<div class="basicEdit" id="findPwd1">
            <form id="profileForm">
						  <table>
						    <tbody><tr>
						      <td valign="top">
						        <span class="redstar">*</span>
						      </td> 
						      <td>
						        <input type="text" placeholder="真实姓名" value="" name="name" id="name">
						      </td>
						      <td valign="top"></td> 
						      <td>
						          <ul class="profile_radio clearfix reset">
						            <li class="current">
						           	  	 男<em></em>
						              	<input type="radio" checked="checked" name="gender" value="1"> 
						            </li>
						            <li>
						            	  女<em></em>
						              	<input type="radio" name="gender" value="0"> 
						            </li>
						          </ul>  
						      </td>
						    </tr>
						    <tr>
						       <td valign="top"><span class="redstar">*</span></td> 
						      <td>
						        <input type="text" placeholder="年龄" value="" name="age" id="age" />
						        <span class="error" style="display:none;" id="ageError"></span>
						      </td>
						    </tr>
						    <tr>
						      <td valign="top">
						        <span class="redstar">*</span>
						      </td> 
						      <td>
						      	<input type="hidden" id="topDegree" value="" name="topDegree">
						        <input type="button" value="大专" id="select_topDegree" class="profile_select_190 profile_select_normal">
								<div class="boxUpDown boxUpDown_190 dn" id="box_topDegree" style="display: none;">
						        	<ul>
						        								        			<li id="dazhuan" value="dazhuanvalue">大专</li>
						        								        			<li>本科</li>
						        								        			<li>硕士</li>
						        								        			<li>博士</li>
						        								        			<li>其他</li>
						        								        	</ul>
						        </div>  
						      </td>
						      <td valign="top">
						        <span class="redstar">*</span>
						      </td> 
						      <td>
						          <input type="hidden" id="workyear" value="" name="workyear">
						          <input type="button" value="3年" id="select_workyear" class="profile_select_190 profile_select_normal">
								  <div class="boxUpDown boxUpDown_190 dn" id="box_workyear" style="display: none;">
						          	 <ul>
						        								        			<li>应届毕业生</li>
						        								        			<li>1年</li>
						        								        			<li>2年</li>
						        								        			<li>3年</li>
						        								        			<li>4年</li>
						        								        			<li>5年</li>
						        								        			<li>6年</li>
						        								        			<li>7年</li>
						        								        			<li>8年</li>
						        								        			<li>9年</li>
						        								        			<li>10年</li>
						        								        			<li>10年以上</li>
						        								        	 </ul>
						          </div>  
						      </td>
						    </tr>
						   	<tr>
						      <td valign="top">
						        <span class="redstar">*</span>
						      </td> 
						      <td colspan="3">
						          <input type="text" placeholder="接收面试通知的邮箱" value="" name="email" id="email">
						      </td>
						    </tr>
						   	<tr>
						      <td valign="top">
						        <span class="redstar">*</span>
						      </td> 
						      <!--
						      <td colspan="3">
						          <a type="text" placeholder="居住地" class="pick-area pick-area6" name="residence" id="residence"></a>
						          <span class="error" style="display:none;" id="residenceError"></span>
						      </td>
						      -->
						       
						      <td colspan="4">
						        <a href="javascript:void(0)" class="pick-area pick-area6" name=""></a>
						        <input type="hidden" value="" id="residence" name="residence"/>
						      </td>
						       
						    </tr>
						    <tr>
						      <td valign="top"> </td> 
						      <td colspan="3">
						          <input type="hidden" id="currentState" value="" name="currentState">
						          <input type="button" value="目前状态" id="select_currentState" class="profile_select_410 profile_select_normal">
								  <div class="boxUpDown boxUpDown_410 dn" id="box_currentState" style="display: none;">
						          	  <ul>
						        								        			<li>我目前已离职，可快速到岗</li>
						        								        			<li>我目前正在职，正考虑换个新环境</li>
						        								        			<li>我暂时不想找工作</li>
						        								        			<li>我是应届毕业生</li>
						        								        	  </ul>
						          </div>  
						      </td>
						    </tr>
						    <tr>
						      <td></td> 
						      <td colspan="3">
						          <input type="submit" value="保 存" class="btn_profile_save" id="savebaseinfo">
<!-- 						          <a class="btn_profile_cancel" href="javascript:;">取 消</a> -->
						      </td>
						    </tr>
						  </tbody></table>
						</form><!--end #profileForm-->
        </div>
        </div>
    </div>
    
    <script src="style/js/profile.min.js" type="text/javascript"></script>
    
<script type="text/javascript">
    $(document).ready(function() {
    	$('#profileForm input[type="text"]').focus(function(){
    		$(this).siblings('.error').hide();
    	});
    	var fd = new FormData();
//     	$('#savebaseinfo').click(function(){
    		
//     		var name = $('#name').val();
//     		var gender = $('input:radio[name="gender"]:checked').val();
//     		var age = $('#age').val();
//     		var topdegree = $('#topDegree').val();
//     		var workyear = $('#workyear').val();
//     		var phone = $('#tel').val();
//     		var email = $('#email').val();
//     		var currentstate = $('#currentState').val();
//     		var errnum = $('#profileForm').find('input[class="error"]').length;
//     		var residence = $('#residence').val();
//     		if(age==""){
//     			$('#ageError').text("请填写年龄").show();
//     			return false;
//     		}
// //     		if(residence==""){
// //     			$('#residenceError').text("请填写居住地").show();
// //     			return false;
// //     		}
<%--     		fd.append('userid', '<%=userid%>'); --%>
//     		fd.append('name', name);
//     		fd.append('gender', gender);
//     		fd.append('age', age);
//     		fd.append('phone', phone);
//     		fd.append('email', email);
//     		fd.append('topdegree', topdegree);
//     		fd.append('workyear', workyear);
//     		fd.append('currentstate', currentstate);
//     		fd.append('residence', residence);
    		
//     		 $.ajax({
<%--     		      url: '<%=path %>/top/individualuser/completeUserInfo', --%>
//     		      data: fd,
//     		      type: 'POST',
//     		      cache: false,
//     		      processData: false,
//     		      contentType: false,
//     		      dataType: 'json',
//     		      success: function(data) {
<%--     		    	  window.location.href = "resume.jsp?userid=<%=userid%>"; --%>
//     		      },
//     		      error: function() {
//     		      }
//     		 });
    		
//     	});


    });
    
    $(".pick-area6").pickArea({
        "format":"居住地省/市/县、区", //格式
        "width":"386",
        "height":"40",
        "borderColor":"#f7f7f7",//文本边框的色值
        "arrowColor":"#f3f3f",//下拉箭头颜色
        "listBdColor":"#f3f3f",//下拉框父元素ul的border色值
        "color":"#6f6f6f",//字体颜色
        "hoverColor":"#ff8c00",
        //"preSet":"山东省/临沂市/兰陵县",
        "getVal":function(){
            //console.log($(".pick-area-hidden").val())
            //console.log($(".pick-area-dom").val())
            var thisdom = $("."+$(".pick-area-dom").val());
            thisdom.next().val($(".pick-area-hidden").val());
        }
    });
    </script>
</body>
</html>
