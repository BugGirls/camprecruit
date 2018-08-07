<%@page import="core.util.StringHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
String jsPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String res=StringHelper.null2String(request.getParameter("res"));
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>冒险家</title>
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
 <style>
 .axtable tr{ height: 40px}
 </style>
 </head>
 <body>
<div class="row">
	<div class="col-xs-12">
		<script type="text/javascript">
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
		</script>
<!-- 添加  -->
 		<div style="top:20%;left:30%;background-color: #fff;border: 1px solid #aaa;" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;">&nbsp;&nbsp;导入Excel信息</div>
		  <div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/weixin/MxjWeixin/fileUpload">  	 
				<div style="float:left;"> 
				<table class="axtable">
				<tr><td>上传文档：</td><td><input type="file" name="filehref" id="ahref" maxlength="60"/></td></tr> 
				<tr><td>预约门店：</td>
					<td>
						<select name="storeid" id="storeid">
				 		</select>
				 	</td>
				</tr>
				</table>
				</div>
				 <div style="clear: both;"></div>
				<input type="button"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" id="aup" value="提交" onclick="onSubmit();"/>&nbsp;&nbsp;&nbsp;
			 </form>
			</div> 
		</div>
	</div>
</div>
<script type="text/javascript" src="${contextPath}/static/assets/js/jquery.js"></script>
<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
<script type="text/javascript">
	initres();
	function onSubmit(){
		document.getElementById('form2').submit();
	}
	function initres(){
		if('<%=res%>'=='ok'){
			alert("上传成功！");
		}else if('<%=res%>'=='error1'){
			alert("上传失败，有日期未正确填写！");
		}else if('<%=res%>'=='error2'){
			alert("上传失败，请检查格式是否正确！");
		}
		$.ajax({
			   type:"post",
			   url:"../weixin/MxjWeixin/getStoreAvailableSelectList",
			   success:function(data){ 
				   console.log(data)
				    $('#storeid').append(data);
		       }  
		    });
	}
</script>
</body>
</html>  		