<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%@page import="com.jeefw.model.sys.CooperationColleges"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
String imgPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
CooperationColleges colleges = (CooperationColleges)request.getAttribute("cooperationColleges");
String typestr = "";
if(colleges.getType()==1)typestr="综合性";
else if(colleges.getType()==2)typestr="理工";
else if(colleges.getType()==3)typestr="体育";
else typestr ="";

String xingzhistr = "";
if(colleges.getXingzhi()==1)xingzhistr="本科";
else if(colleges.getXingzhi()==2)xingzhistr="专科";
else typestr ="";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>营才网 合作院校 <%=colleges.getName() %></title>
    <style type="text/css">
   #sales_promotion_list_ .weui_media_box {
	padding: 0;
}

.weui_tabbar_label {
	color: #959595;
	text-align: center;
	font-size: 14px;
	padding-top: 0px;
	padding-bottom: 10px;
}
.borderyellow {
	color: #FE7201;
	border: 1px solid;
	padding: 2px;
	margin-right: 10px;
}
.item_border{margin: 10px;/*border: 1px solid #d5d5d6;*/}
/*评价星级*/
.starul {
  /*padding-left: 15px;*/
  overflow: hidden;
 }
 .starul li {
  float: left;
  list-style: none;
  width: 27px;
  height: 27px;
  background: url(<%=imgPath %>/images/twostar.gif)
 }
 .starul li a {
  display: block;
  width: 100%;
  padding-top: 27px;
  overflow: hidden;
 }
 .starul li.light {
  background-position: 0 -29px;
 }
 
 .lbgreen {
  border-right-color: #8BC53F;
}
.badge {
  width: 0;
  height: 0;
  border-right-width: 60px;
  border-right-style: solid;
  border-bottom: 60px solid transparent;
  position: absolute;
  top: 0;
  right: 0;
}
i {
  display: inline-block;
  font-style: normal;
}
.tip {
  transform: rotate(45deg);
  position: absolute;
  top: 10px;
  right: 5px;
  color: #fff;
  font-size: 16px;
}
</style>
</head>
<body ontouchstart style="">
<jsp:include page="header.jsp" />
<div class="bodydiv" style="margin-top: 30px;">
	<div class=" bd" style="width:1024px; margin: 0 auto;">
	
		<div class="" style="background-size: cover; background-image: url(<%=basePath%>images/img_alliance_bg.jpg); background-repeat: no-repeat; background-position: center;">
	        <div class="weui_panel_bd" style=" padding: 5px 20px;">
	            <div class="weui_media_box weui_media_appmsg" style=" height: 120px;">
	                <div class="weui_media_hd"  style="width: 120px;height:120px;float:left;background-color: #fff;background: url(<%=imgPath%><%=colleges.getLogo()%>) center no-repeat;border-radius:60px;    background-size: contain;">
	                </div>
	                <div class="weui_media_bd" style="width: 320px;height: 100px;float:left;padding: 10px;">
	                    <h4 class="weui_media_title" style="color: #fff;font-size: 22px;padding:5px 5px 15px 5px;"><%=colleges.getName() %><span style="font-size: 8px; background-color: #b639d8; margin-left: 5px;"></span></h4>
	                    <p class="weui_media_desc"  style="font-size: 12px;color: #fff;padding-left: 5px;"> <%if(colleges.getIsniubi()==1) %> <span style="background: #f60; padding: 2px;">211工程院校</span> <%;if(colleges.getIshenniubi()==1) %> <span style="background: #f63f27; padding: 2px;">985工程院校</span><% ;%></p>
	                    <p class="weui_media_desc"  style="font-size: 12px;color: #fff;padding-left: 5px;"><span>类型：</span><%=typestr%> </p>
	                    <p class="weui_media_desc"  style="font-size: 12px;color: #fff;padding-left: 5px;"><span>性质：</span><%=xingzhistr%></p>
	                    <p class="weui_media_desc"  style="font-size: 12px;color: #fff;padding-left: 5px;"><span>校训：</span><%=colleges.getBrief()%></p>
	                </div>
	            </div>
	        </div>
	    </div>
	    
		<div class="weui_panel weui_panel_access">
	        <div class="weui_panel_bd">
	            <div class="weui_media_box weui_media_text">
	                <p class="weui_media_desc"><%=colleges.getContent()%></p>
	            </div>
	        </div>
	    </div>
	</div>
</div>
    
</body>
</html>
