<%@page import="core.util.StringHelper"%>
<%@page import="com.jeefw.model.sys.CompanyInfo"%>
<%@page import="com.jeefw.model.sys.CompanyUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
CompanyUser companyUser = (CompanyUser)request.getSession().getAttribute("companyuser"); 
Integer userid = companyUser.getId();
CompanyInfo companyInfo = (CompanyInfo)request.getSession().getAttribute("companyinfo"); 
%>

<!DOCTYPE html>
<html>
<head>
<script id="allmobilize" charset="utf-8"
	src="<%=basePath%>style/js/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" />
<!-- end 云适配 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${companyuser.name }-营才网-最专业的营地服务平台</title>
<meta content="营才网是3W旗下的营地人才领域垂直招聘网站,营地职业机会尽在营才网" name="description">
<meta
	content="营才,营才网,营才招聘,营才, 营才网 ,营地人才招聘,营才网招聘, 移动营地人才招聘, 垂直营地人才招聘, 微信招聘, 微博招聘, 营才官网, 营才百科,跳槽, 高薪职位, 营地人才圈子, IT招聘, 职场招聘, 猎头招聘,O2O招聘, LBS招聘, 社交招聘, 校园招聘, 校招,社会招聘,社招"
	name="keywords">

<meta name="baidu-site-verification" content="QIQ6KC1oZ6" />

<!-- <div class="web_root"  style="display:none">h</div> -->
<script type="text/javascript">
var ctx = "<%=requestPath%>";
console.log(ctx);
</script>
<link href="http://www.lagou.com/images/favicon.ico" rel="Shortcut Icon">
<link href="style/css/style.css" type="text/css" rel="stylesheet">
<link href="style/css/external.min.css" type="text/css" rel="stylesheet">
<link href="style/css/popup.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>style/js/jquery.1.10.1.min.js"></script>
<script src="<%=basePath%>style/js/jquery.lib.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>style/js/ajaxfileupload.js"></script>
<script src="<%=basePath%>style/js/additional-methods.js" type="text/javascript"></script>
<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>style/js/excanvas.js"></script>
<![endif]-->
<script src="<%=basePath%>style/js/ajaxCross.json" charset="UTF-8"></script>
</head>
<body>
<div id="body">
	<div id="header">
    	<div class="wrapper">
    		<a class="logo" href="index.html">
    			<img width="229" height="43" alt="营才招聘-专注互联网招聘" src="style/images/logo.png">
    		</a>
    		<ul id="navheader" class="reset">
    			<li><a href="index">首页</a></li>
    			<li class="current"><a href="companylist.html">公司</a></li>
    			<li><a target="_blank" href="">论坛</a></li>
    				    			<li>
	    				<a rel="nofollow" href="">简历管理</a>
	    					    			</li>
	    							    			<li><a rel="nofollow" href="position_create.jsp">发布职位</a></li>
	    		    		</ul>
        	        	<dl class="collapsible_menu">
            	<dt>
           			<span>${companyinfo.name }&nbsp;</span> 
            		<span class="red dn" id="noticeDot-1"></span>
            		<i></i>
            	</dt>
                                	<dd><a href="positions.html">我发布的职位</a></dd>
                	<dd><a href="positions.html">我收到的简历</a></dd>
                	<dd class="btm"><a href="myhome.html">我的公司主页</a></dd>
                	<dd><a href="jianli.html">我要找工作</a></dd>
                                                <dd><a href="accountBind.html">帐号设置</a></dd>
                                <dd class="logout"><a rel="nofollow" href="clogin.jsp">退出</a></dd>
            </dl>
                                </div>
    </div><!-- end #header -->
    <div id="container">
        <!-- <script src="style/js/swfobject_modified.js" type="text/javascript"></script> -->
        <div class="clearfix">
        				
            <div class="content_l">           
	                <div class="c_detail">
	                	<div style="background-color:#fff;" class="c_logo">
		                	<a title="上传公司LOGO" id="logoShow" class="inline cboxElement" href="#logoUploader">
		                				                			<img width="190" height="190" alt="公司logo" src="style/images/logo_default.png">
	                        		                        	
	                        	<span>更换公司图片<br>190px*190px 小于2M</span>
	                        </a>
		                </div>
		                
		                <!--  			                <div class="c_logo" style="background-color:#fff;">
			                	<div id="logoShow">
			                		<img src="style/images/logo_default.png" width="190" height="190" alt="公司logo" />
		                        	<span>更换公司图片<br />190px*190px 小于5M</span>
		                        </div>
		                        <input onchange="img_check(this,'http://www.lagou.com/cd/saveProfileLogo.json',25927,'logo');" type="file" id="logo" name="logo" title="支持jpg、jpeg、gif、png格式，文件小于5M" />
			                     
			                </div>
		                    <span class="error" id="logo_error" style="display:none;"></span>
						     -->
		                
	                    <div class="c_box companyName">
             		       <h2 title="公司名称">${companyinfo.name }</h2>
            			   <em class="unvalid"></em>
                           <span class="va dn" style="display: none;">营才未认证企业</span>
	                       <a target="_blank" class="applyC" href="http://www.lagou.com/c/auth.html">申请认证</a>
	                       <div class="clear"></div>
	                       <h1 title="${companyuser.name }" class="fullname">${companyuser.name }</h1>
	                        <form class="clear editDetail dn" id="editDetailForm" style="display: none;">
	                            <input type="text" placeholder="请输入公司简称" maxlength="15" value="${companyinfo.name }" name="companyShortName" id="companyShortName" class="valid">
	                            <input type="text" placeholder="一句话描述公司优势，核心价值，限50字" maxlength="50" value="${companyinfo.brief }" name="companyFeatures" id="companyFeatures" class="valid"><span for="companyFeatures" generated="true" class="error" style="display: none;">请输入5-50字的一句话介绍</span>
	                            <input type="hidden" value="${companyuser.id }" id="companyId" name="companyId">
	                            <input type="submit" value="保存" id="saveDetail" class="btn_small">
	                            <a id="cancelDetail" class="btn_cancel_s" >取消</a>
		                    </form>
	                        	                        
	                            
	                        <div class="clear oneword" style="display: block;"><img width="17" height="15" src="style/images/quote_l.png">&nbsp; <span>${companyinfo.brief }</span> &nbsp;<img width="17" height="15" src="style/images/quote_r.png"></div>
	                        <h3 class="dn">已选择标签</h3>
	                        <ul style="overflow:auto" id="hasLabels" class="reset clearfix">
	                        	<%if(StringHelper.isEmpty(companyInfo.getLabels())) {%>
	                        			                        	<li><span>年终分红</span></li>
		                        		                        	<li><span>五险一金</span></li>
		                        		                        	<li><span>弹性工作</span></li>
		                        		                        	<li><span>岗位晋升</span></li>
		                        <%}else{
		                        	String[]labels = companyInfo.getLabels().split(",");
		                        	for(String lable:labels){
		                        		%>
		                        		<li><span><%=lable %></span></li>
		                        <%	}
		                        	
		                        }
		                        %>		                        	
		                        
		                        
		                        	                            <li class="link">编辑</li>
	                        </ul>
	                        <div class="dn" id="addLabels">
	                        	<a id="changeLabels" class="change" href="javascript:void(0)">换一换</a>
	                        	<input type="hidden" value="1" id="labelPageNo">
	                            <input type="submit" value="贴上" class="fr" id="add_label">
                            	<input type="text" placeholder="添加自定义标签" name="label" id="label" class="label_form fr">	
	                            <div class="clear"></div>
	                            <ul class="reset clearfix"> </ul>
	                            <a id="saveLabels" class="btn_small" href="javascript:void(0)">保存</a>
	                            <a id="cancelLabels" class="btn_cancel_s" href="javascript:void(0)">取消</a>
	                        </div>
	                    </div>
	                    <a title="编辑基本信息" class="c_edit" id="editCompanyDetail" href="javascript:void(0);" style="display: block;"></a>
	                	<div class="clear"></div>
	                </div>
                
                	<div class="c_breakline"></div>
       
       				<div id="Profile">
			            				        	<div class="profile_wrap">
					             <!--无介绍 -->
									<dl class="c_section dn" style="display: none;">
					                	<dt>
					                    	<h2><em></em>公司介绍</h2>
					                    </dt>
					                    <dd>
					                    	<div class="addnew">
					                        	详细公司的发展历程、让求职者更加了解你!<br>
					                            <a id="addIntro" href="javascript:void(0)">+添加公司介绍</a>
					                        </div>
					                    </dd>
					                </dl>
					            <!--编辑介绍-->
					                <dl class="c_section newIntro dn" style="display: none;">
					                    <dt>
					                        <h2><em></em>公司介绍</h2>
					                    </dt>
					                    <dd>
						                    <form id="companyDesForm">
						                        <textarea placeholder="请分段详细描述公司简介、企业文化等" name="companyProfile" id="companyProfile" class="valid">该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎该方法嘎嘎</textarea>		                                        
						                        <div class="word_count fr">你还可以输入 <span>955</span> 字</div>
						                        <div class="clear"></div>
						                        <input type="submit" value="保存" id="submitProfile" class="btn_small">
						                        <a id="delProfile" class="btn_cancel_s" href="javascript:void(0)">取消</a>
						                    </form>
					                    </dd>
					                </dl>
					            
					            <!--有介绍-->
					               <dl class="c_section" style="display: block;">
					               		<dt>
					                   		<h2><em></em>公司介绍</h2>
					                   	</dt>
					                   	<dd>
					                   		<div class="c_intro">${companyinfo.profile }</div>
					                   		<a title="编辑公司介绍" id="editIntro" class="c_edit" href="javascript:void(0)"></a>
					                   	</dd>
					               	</dl>
				            </div>
				                 	
	     			</div><!-- end #Profile -->
      	
      	<!--[if IE 7]> <br /> <![endif]-->
	    
	        	 		        	<!--无招聘职位-->
						<dl id="noJobs" class="c_section">
		                	<dt>
		                    	<h2><em></em>招聘职位</h2>
		                    </dt>
		                    <dd>
		                    	<div class="addnew">
		                        	发布需要的人才信息，让伯乐和千里马尽快相遇……<br>
		                            <a href="position_create.jsp">+添加招聘职位</a>
		                        </div>
		                    </dd>
		                </dl>
	          	
	          	<input type="hidden" value="" name="hasNextPage" id="hasNextPage">
	          	<input type="hidden" value="" name="pageNo" id="pageNo">
	          	<input type="hidden" value="" name="pageSize" id="pageSize">
          		<div id="flag"></div>
            </div>	<!-- end .content_l -->
            
            <div class="content_r">
            	<div id="Tags">
	            	<div id="c_tags_show" class="c_tags solveWrap" style="display: block;">
							<table>
								<tbody>
									<tr>
										<td>地点</td>
										<td>${companyinfo.shengshi }</td>
									</tr>
									<tr>
										<td>领域</td>
										<td>${companyinfo.hangyevalue }</td>
									</tr>
									<tr>
										<td>规模</td>
										<td>${companyinfo.guimovalue }</td>
									</tr>
									<tr>
										<td>阶段</td>
										<td>${companyinfo.jieduanvalue }</td>
									</tr>
									<tr>
										<td>主页</td>
										<td><a target="_blank" href="${companyinfo.website }">${companyinfo.website }</a></td>
									</tr>
								</tbody>
							</table>
							<a id="editTags" class="c_edit" href="javascript:void(0)"></a>
	                </div>
	                <div id="c_tags_edit" class="c_tags editTags dn" style="display: none;">
		                <form id="tagForms">
		                    <table>
		                        <tbody><tr>
		                            <td>地点</td>
		                            <td>
		                            	<input type="text" placeholder="请输入地点" value="${companyinfo.shengshi }" name="shengshi" id="city" class="valid">	
		                            </td>
		                        </tr>
		                        <tr>
		                            <td>领域</td><!-- 支持多选 -->
		                            <td>
		                            	<input type="hidden" value="${companyinfo.hangye }" id="hangye" name="hangye" class="valid">
		                            	<input type="hidden" value="${companyinfo.hangyevalue }" id="hangyevalue" name="hangyevalue" class="valid">
		                            	<input type="button" class="select_tags" value="${companyinfo.hangyevalue }" id="select_ind" class="select_tags">
		                                <div id="box_ind" class="selectBox dn">
		                                    <ul class="reset" id="hangye_type">
		                                    </ul>
		                                </div>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td>规模</td>
		                            <td>
		                            	<input type="hidden" value="${companyinfo.guimo }" id="guimo" name="guimo" >
		                            	<input type="hidden" value="${companyinfo.guimovalue }" id="guimovalue" name="guimovalue" class="valid">
		                            	<input type="button" value="${companyinfo.guimovalue }" id="select_sca" class="select_tags">
		                                <div class="selectBox dn" id="box_sca" style="display: none;">
		                                    <ul class="reset" id="guimo_type">
		                                    </ul>
				                            				                                        		                                    </ul>
		                                </div>	
		                            </td>
		                        </tr>
		                        
		                        <tr>
		                            <td>阶段</td>
		                            <td>
		                            	<input type="hidden" value="${companyinfo.jieduan }" id="jieduan" name="jieduan" >
		                            	<input type="hidden" value="${companyinfo.jieduanvalue }" id="jieduanvalue" name="jieduanvalue" class="valid">
		                            	<input type="button" value="${companyinfo.jieduanvalue }" id="select_sjd" class="select_tags">
		                                <div class="selectBox dn" id="box_sjd" style="display: none;">
		                                    <ul class="reset" id="jieduan_type">
		                                    </ul>
				                            				                                        		                                    </ul>
		                                </div>	
		                            </td>
		                        </tr>
		                        
		                        <tr>
		                            <td>主页</td>
		                            <td>
                            			<input type="text" placeholder="请输入网址" value="${companyinfo.website }" name="website" id="website" class="valid">	
		                            </td>
		                        </tr>
		                    </tbody></table>
		                    <input type="hidden" id="comCity" value="上海">
		                    <input type="hidden" id="comInd" value="移动互联网">
		                    <input type="hidden" id="comSize" value="150-500人">
		                    <input type="hidden" id="comState" value="稳定期">
		                    <input type="hidden" id="comUrl" value="http://www.zmtpost.com">
		                    <input type="submit" value="保存" id="submitFeatures" class="btn_small">
		                    <a id="cancelFeatures" class="btn_cancel_s" href="javascript:void(0)">取消</a>
		                    <div class="clear"></div>
		            	</form>
	                </div>
       			</div><!-- end #Tags -->
	       	
	       <!--公司深度报道-->
            <div id="Reported">	
	            		            <!--无报道-->
		            <dl class="c_section c_reported">
		            	<dt>
		                	<h2><em></em>公司深度报道</h2>
                   			<a title="添加报道" class="c_add dn" href="javascript:void(0)"></a>
		                </dt>
		                <dd>
		                	<!-- 编辑报道 -->
                       		<ul class="reset dn" id="report_list"></ul>
		                	
		                	<!-- 无报道 -->
	                        <div class="addnew_right reported_info">
	                        	展示外界对公司的深度报道，<br>便于求职者了解公司！<br>
	                            <a class="report_edit" href="javascript:void(0)">+添加报道</a>
	                        </div>
	                        
		                	<ul class="newReport dn">
	                        	<li>
		                			<a style="display:none;" class="article" title="" target="_blank" ></a>
		                			<a title="编辑报道" class="c_edit dn" href="javascript:;"></a>
		                			<form class="reportForm">
		                				<input type="text" placeholder="请输入文章标题" value="" name="articleTitle">
		                				<input type="text" placeholder="请输入文章链接" value="" name="articleUrl">
		                				<input type="submit" value="保存" class="btn_small">
			                            <a class="btn_cancel_s report_cancel" href="javascript:;">取消</a>
			                            <input type="hidden" value="" class="article_id">
			                     	</form>
		                		</li>
	                        </ul>
				        </dd>
		            </dl><!-- end .c_reported -->
		                    </div><!-- end #Reported -->
	       	
        </div>
   	</div>

<!-------------------------------------弹窗lightbox  ----------------------------------------->
<div style="display:none;">
	<div style="width:650px;height:470px;" class="popup" id="logoUploader">
		<table width="100%">
			<tbody>
				<tr>
					<td align="center">
						<form>
							<a class="btn_addPic" href="javascript:void(0);"> <span>选择上传文件</span>
								<input type="file"
								onchange="file_check(this,'<%=requestPath%>/top/companyuser/logoUpload','resumeUpload')"
								class="filePrew" id="resumeUpload" name="newResume" size="1"
								title="支持jpg、png、gif格式文件，大小不超过2M,建议尺寸：190*190px" tabindex="1">
							</a>
						</form>
					</td>
				</tr>
				<tr>
					<td align="left">支持jpg、png、gif、bmp格式文件<br>文件大小需小于2M
					</td>
				</tr>
				<tr>
					<td align="center"><img width="55" height="16"
						alt="loading" style="visibility: hidden;" id="loadingImg"
						src="<%=basePath%>style/images/loading.gif"></td>
				</tr>
			</tbody>
		</table>
	</div><!-- #logoUploader -->
</div>
<!------------------------------------- end ----------------------------------------->

<script src="style/js/company.min.js" type="text/javascript"></script>
<script>
var avatar = {};
avatar.uploadComplate = function( data ){
	var result = eval('('+ data +')');
	if(result.success){
		jQuery('#logoShow img').attr("src",ctx+ '/'+result.content);
		jQuery.colorbox.close();
	}
};

/************************
 * 简历上传功能 （文件）
 */
function file_check(obj,action_url,id)
{
	$('#loadingImg').css("visibility","visible");
	var obj = $('#' + id);
	var userId = <%=userid%>;
	
	this.AllowExt='.jpg,.png,.gif,.bmp';
	this.FileExt=obj.val().substr(obj.val().lastIndexOf(".")).toLowerCase();

	if(this.AllowExt != 0 && this.AllowExt.indexOf(this.FileExt)==-1)
	{
		if(id == 'reUploadResume1'){
			$('#setResume span.error').show();
		}else if(id == 'reUploadResume2'){
			$('#setResumeApply span.error').show();
		}else{
			errorTips("只支持jpg、png、gif、bmp格式文件，请重新上传");
			$('#loadingImg').css("visibility","hidden");
		}
	}else if(this.FileExt == ''){
	 	return false;
	}else{
		$.ajaxFileUpload ({
			type:'POST',
			url: action_url,
			secureuri:false,
			fileElementId:id,
			data:{userId:userId},
			dataType: 'text',
			success: function (jsonStr) {
				var json = eval('(' + jsonStr + ')');
				$('#loadingImg').css("visibility","hidden");
				if(json.success){
					$('#resumeSendForm .btn_profile_save').removeAttr('disabled').css('backgroundColor','#019875');
					$('#resumeSetForm .btn_profile_save').removeAttr('disabled').css('backgroundColor','#019875');
					jQuery('#logoShow img').attr("src",ctx+ json.msg);
					jQuery.colorbox.close();
					jQuery('#logoNo').hide();
					jQuery('#logoShow').show();
				}else{
					//issac 加判断 
					if(json.code==-1){
						$.colorbox({inline:true, href:$("div#fileResumeUpload"),title:"企业logo上传失败"});
					}else if(json.code==-2){
						$.colorbox({inline:true, href:$("div#fileResumeUploadSize"),title:"企业logo上传失败"});
					}else{
						errorTips("企业logo上传失败，请重新上传");
					}				
				} 
			},
			error:function(err){
				errorTips("企业logo上传失败，请重新上传");
			}
		})//end of ajax
		
	}
} 

</script>
			<div class="clear"></div>
			<input type="hidden" value="af5b64a9520a4b7da6287ff3400dde11" id="resubmitToken">
	    	<a rel="nofollow" title="回到顶部" id="backtop" style="display: none;"></a>
	    </div><!-- end #container -->
	</div><!-- end #body -->
	<div id="footer">
		<div class="wrapper">
			<a rel="nofollow" target="_blank" href="about.html">联系我们</a>
		    <a target="_blank" href="http://www.lagou.com/af/zhaopin.html">互联网公司导航</a>
		    <a rel="nofollow" target="_blank" href="http://e.weibo.com/lagou720">营才微博</a>
		    <a rel="nofollow" href="javascript:void(0)" class="footer_qr">营才微信<i></i></a>
			<div class="copyright">&copy;2013-2014 Lagou <a href="http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action" target="_blank">京ICP备14023790号-2</a></div>
		</div>
	</div>

<script src="style/js/core.min.js" type="text/javascript"></script>
<script src="style/js/popup.min.js" type="text/javascript"></script>

<!--  -->


<div id="cboxOverlay" style="display: none;"></div>
<div id="colorbox" class="" role="dialog" tabindex="-1" style="display: none;"><div id="cboxWrapper"><div><div id="cboxTopLeft" style="float: left;"></div><div id="cboxTopCenter" style="float: left;"></div><div id="cboxTopRight" style="float: left;"></div></div><div style="clear: left;"><div id="cboxMiddleLeft" style="float: left;"></div><div id="cboxContent" style="float: left;"><div id="cboxTitle" style="float: left;"></div><div id="cboxCurrent" style="float: left;"></div><button type="button" id="cboxPrevious"></button><button type="button" id="cboxNext"></button><button id="cboxSlideshow"></button><div id="cboxLoadingOverlay" style="float: left;"></div><div id="cboxLoadingGraphic" style="float: left;"></div></div><div id="cboxMiddleRight" style="float: left;"></div></div><div style="clear: left;"><div id="cboxBottomLeft" style="float: left;"></div><div id="cboxBottomCenter" style="float: left;"></div><div id="cboxBottomRight" style="float: left;"></div></div></div>
	<div style="position: absolute; width: 9999px; visibility: hidden; display: none;"></div>
</div>
<script type="text/javascript">
	$(document).ready(function($){
		<%if(!StringHelper.isEmpty(companyUser.getImage())){%>
		jQuery('#logoShow img').attr("src",ctx+ '/<%=companyUser.getImage()%>');
		<%}%>
		
		/**行业类别 */
		$.ajax({
			   type:"post",
	 		   url:ctx + "/top/dict/getTypeSelectList",
	 		   data:{"parentDictkey":"HY"},
	 		   dataType:"json", 
			   success:function(data){ 
				    //console.log(data)
				    if(data.records>0){
				    	$("#hangye_type").empty();
				    	$.each( data.rows, function(i, n){
					    	var value = n;
					    	var elements = '<li value="'+value.dictKey+'">'+value.dictValue+'</li>';
					    	$("#hangye_type").append(elements);
			    		});
				    }
		       }  
		});
		
		/**公司规模 */
		$.ajax({
			   type:"post",
	 		   url:ctx + "/top/dict/getTypeSelectList",
	 		   data:{"parentDictkey":"GSGM"},
	 		   dataType:"json", 
			   success:function(data){ 
				    //console.log(data)
				    if(data.records>0){
				    	$("#guimo_type").empty();
				    	$.each( data.rows, function(i, n){
					    	var value = n;
					    	var elements = '<li value="'+value.dictKey+'">'+value.dictValue+'</li>';
					    	$("#guimo_type").append(elements);
			    		});
				    }
		       }  
		});
		
		/**公司发展阶段 */
		$.ajax({
			   type:"post",
	 		   url:ctx + "/top/dict/getTypeSelectList",
	 		   data:{"parentDictkey":"FZJD"},
	 		   dataType:"json", 
			   success:function(data){ 
				    //console.log(data)
				    if(data.records>0){
				    	$("#jieduan_type").empty();
				    	$.each( data.rows, function(i, n){
					    	var value = n;
					    	var elements = '<li value="'+value.dictKey+'">'+value.dictValue+'</li>';
					    	$("#jieduan_type").append(elements);
			    		});
				    }
		       }  
		});
	
	/**公司发展阶段 */
	$.ajax({
		   type:"post",
 		   url:ctx + "/top/companyuser/getNewsList",
 		   dataType:"json", 
		   success:function(data){ 
			    //console.log(data)
			    if(data.records>0){
			    	$("#report_list").empty();
			    	
			    	$.each( data.rows, function(i, n){
				    	var value = n;
				    	
				    	var elements = '<li><a style="display: block;" class="article" title="'+value.title+'" target="_blank" href="http://'+value.url+'">'+value.title+'</a><a title="编辑报道" class="c_edit dn" href="javascript:;" style="display: inline;"></a><form class="reportForm dn"><input type="text" placeholder="请输入文章标题" value="" name="articleTitle" class="valid"><input type="text" placeholder="请输入文章链接" value="" name="articleUrl" class="valid"><input type="submit" value="保存" class="btn_small"><a class="btn_cancel_s report_delete" href="javascript:;">删除</a><input type="hidden" value="'+value.id+'" class="article_id"></form></li>';
				    	
				    	//var elements = '<li value="'+value.dictKey+'">'+value.dictValue+'</li>';
				    	$("#report_list").append(elements);
		    		});
			    	$("#report_list").removeClass("dn");
			    	$(".c_add").removeClass("dn");
			    	$(".addnew_right").addClass("dn");
			    	
			    }
	       }  
	});
});
	
	
	</script>

</body>
</html>