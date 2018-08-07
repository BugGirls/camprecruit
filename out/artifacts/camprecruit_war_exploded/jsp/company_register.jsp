<%@page import="core.util.StringHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
String path = request.getContextPath();
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
Integer userid = Integer.parseInt(request.getParameter("userid"));
Integer state=Integer.parseInt(StringHelper.null2String(request.getParameter("state"), "0"));
%>

<!DOCTYPE html>
<html>
<head>
<script id="allmobilize" charset="utf-8" src="style/js/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld"  />
<!-- end 云适配 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>企业信息登记-营才网-最专业的营地服务平台</title>
<meta property="qc:admins" content="23635710066417756375" />
<meta content="营才网是3W旗下的营地人才领域垂直招聘网站,营地职业机会尽在营才网" name="description">
<meta content="营才,营才网,营才招聘,营才, 营才网 ,营地人才招聘,营才网招聘, 移动营地人才招聘, 垂直营地人才招聘, 微信招聘, 微博招聘, 营才官网, 营才百科,跳槽, 高薪职位, 营地人才圈子, IT招聘, 职场招聘, 猎头招聘,O2O招聘, LBS招聘, 社交招聘, 校园招聘, 校招,社会招聘,社招" name="keywords">

<meta name="baidu-site-verification" content="QIQ6KC1oZ6" />

<!-- <div class="web_root"  style="display:none">h</div> -->
<script type="text/javascript">
var ctx = "<%=requestPath%>";
console.log(ctx);
</script>
<link rel="Shortcut Icon" href="h/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="style/css/style.css"/>
<style type="text/css">
#loginForm input[type="text"], #loginForm input[type="password"] {
    width: 300px;
}
#loginForm span {
    color:red;margin-left: 15px;
}
</style>

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>

<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script type="text/javascript" src="style/js/core.min.js"></script>

</head>

<body id="login_bg">
	<div class="login_wrapper" style="width:1024px;padding-top:50px;">
		<div class="login_header">
        	<a href="h/"><img src="style/images/logo_white.png" width="285" height="62" alt="营才招聘" /></a>
            <div id="cloud_s"><img src="style/images/cloud_s.png" width="81" height="52" alt="cloud" /></div>
            <div id="cloud_m"><img src="style/images/cloud_m.png" width="136" height="95"  alt="cloud" /></div>
        </div>
        
		<div class="login_box">
        	<form id="loginForm" style="height: 450px;"  method="post" enctype="multipart/form-data" action="<%=requestPath %>/top/companyuser/fileUpload">
        		<input type="text" id="company_name" name="name" tabindex="1" placeholder="请输入企业名称" /><span style="color:red;">*</span>
                <span class="error" style="display:none;" id="beError"></span>
                <input type="text" id="faren" name="faren" tabindex="2" placeholder="请输入法人姓名" /><span >*</span>
            	<input type="text" id="daima" name="daima" tabindex="3" placeholder="请输入统一社会信用代码" /><span >*</span>
            	<a  href="javascript:$('#afile').click();"  class="registor_now">上传营业执照	&nbsp;</a><span >*</span>
            	<span id="aimageh0">
				<input type='file' name='file' id='afile' style="display:none;"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
				</span>
                <input type="hidden" id="image" name="image"/>
            </form>
            <div class="login_right">
            	<div>营业执照</div>
				<!-- <span id="fileshow"></span> -->
				<div style="height:300px;text-align: center;">
            		<img id="fileshowdiv"  style="width:200px;height:300px;display: none; border: 1px solid #cbcbcb;" />
				</div>
				<div style="width:550px;float: right;margin-top: 50px;">
            		<input type="submit" id="submitLogin" value="提&nbsp;交 &nbsp;审&nbsp;核 &nbsp; " style="float:right;"/>
				</div>
            </div>
        </div>
        <div class="login_box_btm"></div>
    </div>
    
    <script type="text/javascript">
    
    $(document).ready(function(e) {
    	
    	<%if(null!=state&&state==3){%>
    	$.ajax({
   		   	   type:"get",
   		     	url:" <%=requestPath %>/top/companyuser/getCompanyShenheDetailInfo?id=<%=userid%>",
   		  		dataType:"json", 
   		     	success:function(dataT){
   		   			console.log(dataT); 
   		  			if(dataT.success){
   		   				var data = dataT.obj;
   		   				console.log(data); 
   		   				
		   				$("#company_name").val(data.name);
		   				$("#faren").val(data.faren);
		  				$("#daima").val(data.daima);
						$("#image").val(data.image);
						$("#fileshowdiv").attr("src","<%=requestPath %>"+data.image);
						$("#fileshowdiv").show();
					    var stageStr = "";
	   					if(data.stage==0)stageStr="待审核";
	         			else if(data.stage==1)stageStr="通过审核";
	         			else if(data.stage==2)stageStr="未通过审核";
		  				
						var element = '<br/><p>申请时间:&nbsp;'+new Date(data.createTime.time).Format("yyyy-MM-dd hh:mm:ss")+'</p>';
						element += '<p>审核状态:&nbsp;'+stageStr+'</p><p>审核时间:&nbsp;'+data.shenheTime+'</p>';
						element += '<p>审核意见:&nbsp;'+data.shenheyijian+'</p>';
						$("#loginForm").append(element);
   		  			}else{
   		  				alert("未查询到审核详情！");
   		  			}
   		   	   }
   		   	});
    	<%}%>
    	$("#submitLogin").click(function(){
    		$("#loginForm").submit();
    	});
    	
    	$('#afile').change(function(){
    		var imgpath = $('#afile').val();
    		var $file = $(this);  
            var fileObj = $file[0];  
      
            var windowURL = window.URL || window.webkitURL;  
            var dataURL;  
            var $img = $("#fileshowdiv");  
      
            if (fileObj && fileObj.files && fileObj.files[0]) {  
                dataURL = windowURL.createObjectURL(fileObj.files[0]);  
                $img.attr('src', dataURL);  
                $img.show();
                if($("span [for='afile']"))
                $("span [for='afile']").remove();
            } else {  
                dataURL = $file.val();  
                var imgObj = document.getElementById("fileshowdiv");  
                imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
                imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;  
      
            }
            $("#image").val(dataURL);
    		
    	});
    	
    	//验证表单
	    	 $("#loginForm").validate({
	    	        rules: {
	    	        	name:{
	    	        		required: true
	    	        	},
	    	        	faren: {
			    	    	required: true
			    	   	},
			    	   	daima: {
			    	    	required: true,
			    	    	rangelength: [6,16]
			    	   	},
			    	   	image:{required:true}
			    	},
			    	messages: {
			    		name:{
	    	        		required:"请输入企业名称"
	    	        	},
	    	        	faren: {
			    	    	required: "请输入企业法人姓名"
			    	   	},
			    	   	daima: {
			    	    	required: "请输入统一社会信用代码",
			    	    	rangelength: "请输入6-16位统一社会信用代码"
			    	   	},
			    	   	image: {
			    	    	required: "请上传营业执照"
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
			    	}
	    	});
    });
    
    Date.prototype.Format = function(fmt)   
    { //author: meizz   
      var o = {   
        "M+" : this.getMonth()+1,                 //月份   
        "d+" : this.getDate(),                    //日   
        "h+" : this.getHours(),                   //小时   
        "m+" : this.getMinutes(),                 //分   
        "s+" : this.getSeconds(),                 //秒   
        "q+" : Math.floor((this.getMonth()+3)/3), //季度   
        "S"  : this.getMilliseconds()             //毫秒   
      };   
      if(/(y+)/.test(fmt))   
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
      for(var k in o)   
        if(new RegExp("("+ k +")").test(fmt))   
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
      return fmt;   
    };
    </script>
</body>
</html>
