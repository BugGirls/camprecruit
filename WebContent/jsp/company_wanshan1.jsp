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
<title>企业信息完善-营才网-最专业的营地服务平台</title>
<meta property="qc:admins" content="23635710066417756375" />
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
<link href="<%=basePath%>style/css/style.css" type="text/css" rel="stylesheet">
<link href="<%=basePath%>style/css/external.min.css" type="text/css" rel="stylesheet">
<link href="<%=basePath%>style/css/popup.css" type="text/css" rel="stylesheet">
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
			<div class="wrapper" style="margin-top: 0px; ">
				<a class="logo" href="index.html"> <img width="229" height="43"
					alt="拉勾招聘-专注互联网招聘" src="<%=basePath%>style/images/logo.png">
				</a>
				<ul id="navheader" class="reset">
					<li><a href="index.html">首页</a></li>
					<li class="current"><a href="companylist.html">公司</a></li>
					<li><a target="_blank" href="">论坛</a></li>
					<li><a rel="nofollow" href="">简历管理</a></li>
					<li><a rel="nofollow" href="create.html">发布职位</a></li>
				</ul>
				<dl class="collapsible_menu">
					<dt>
						<span><%=companyUser.getName() %>&nbsp;</span> <span class="red dn" id="noticeDot-1"></span>
						<i></i>
					</dt>
					<dd>
						<a href="positions.html">我发布的职位</a>
					</dd>
					<dd>
						<a href="positions.html">我收到的简历</a>
					</dd>
					<dd class="btm">
						<a href="myhome.html">我的公司主页</a>
					</dd>
					<dd>
						<a href="jianli.html">我要找工作</a>
					</dd>
					<dd>
						<a href="accountBind.html">帐号设置</a>
					</dd>
					<dd class="logout">
						<a rel="nofollow" href="<%=basePath%>clogin.jsp">退出</a>
					</dd>
				</dl>
			</div>
		</div>
		<!-- end #header -->
		<div id="container">

			<div style="" id="stepTip">
				<a></a> <img width="803" height="59" src="<%=basePath%>style/images/img_tiponce.jpg">
			</div>
			<div class="content_mid">
				<dl class="c_section c_section_mid">
					<dt>
						<h2>
							<em></em>填写公司信息
						</h2>
					</dt>
					<dd>
						<form id="stepForm">
							<div class="c_text_1">基本信息为必填项，是求职者加速了解公司的窗口，认真填写吧！</div>
							<img width="668" height="56" class="c_steps" alt="第一步"
								src="<%=basePath%>style/images/step1.png">
							<input type="hidden" name="fullname" value="<%=companyUser.getName() %>">
							<h3>
								公司全称 <span><%=companyUser.getName() %></span>
							</h3>

							<h3>公司简称</h3>
							<!--非必填-->
							<input type="text" placeholder="请输入公司简称，如:营才网" value="${companyinfo.name}"
								name="name" id="name" class="valid">

							<h3>公司LOGO</h3>
							<!--非必填改必填-->
							<div class="c_logo c_logo_pos">
								<a title="上传公司LOGO" class="inline cboxElement"
									href="#logoUploader">
									<div id="logoNo">
										<span>上传公司LOGO</span> <br> 尺寸：190*190px <br> 大小：小于5M
									</div>
									<div class="dn" id="logoShow">
										<img width="190" height="190" alt="公司logo" src=""> <span>更换公司LOGO<br>190px*190px
											小于2M
										</span>
									</div>
								</a>
							</div>
							
							<h3>联系电话</h3>
							<input type="text" placeholder="请输入公司人事联系电话" value="${companyinfo.phone }" name="phone"
								id="phone">

							<h3>公司网址</h3>
							<input type="text" placeholder="请输入公司网址" value="${companyinfo.website }" name="website"
								id="website">

							<h3>所在城市</h3>
							<input type="text" placeholder="请输入工作城市，如：北京" name="shengshi" value="${companyinfo.shengshi }" id="shengshi">
							<input type="hidden"  name="sheng" value="${companyinfo.sheng }"id="sheng">
							<input type="hidden"  name="shi" value="${companyinfo.shi }"id="shi">

							<h3>行业领域</h3>
							<div>
								<input type="hidden" value="${companyinfo.hangyevalue }" name="hangyevalue"id="hangyevalue">
								<input type="hidden" value="${companyinfo.hangye }" name="hangye"id="hangye"> 
								<input type="button" value="请选择行业领域" id="select_industry" class="select">
								<div class="dn" id="box_industry" style="display: none;">
									<ul class="reset" id="hangye_type">
										<li>移动互联网</li>
										<li>电子商务</li>
										<li>社交</li>
										<li>企业服务</li>
										<li>O2O</li>
										<li>教育</li>
										<li>文化艺术</li>
										<li>游戏</li>
										<li>在线旅游</li>
										<li>金融互联网</li>
										<li>健康医疗</li>
										<li>生活服务</li>
										<li>硬件</li>
										<li>搜索</li>
										<li>安全</li>
										<li>运动体育</li>
										<li>云计算\大数据</li>
										<li>移动广告</li>
										<li>社会化营销</li>
										<li>视频多媒体</li>
										<li>媒体</li>
										<li>智能家居</li>
										<li>智能电视</li>
										<li>分类信息</li>
										<li>招聘</li>
									</ul>
								</div>
							</div>

							<h3>公司规模</h3>
							<div>
								<input type="hidden" value="${companyinfo.guimo }" name="guimo"  id="guimo"/>
								<input type="hidden" value="${companyinfo.guimovalue }" name="guimovalue" id="guimovalue"> 
								<input type="button" value="请选择公司规模" id="select_scale" class="select">
								<div class="dn" id="box_scale" style="display: none;">
									<ul class="reset" id="guimo_type">
									</ul>
								</div>
							</div>

							<h3>发展阶段</h3>
							<div>
								<input type="hidden" id="jieduanvalue" name="jieduanvalue"
									value="${companyinfo.jieduanvalue }">
									<input type="hidden" id="jieduan" name="jieduan"
									value="${companyinfo.jieduan }">
								<ul class="s_radio clearfix s_radio_ex" id="jieduan_type">
									<li>未融资</li>
									<li value="10">天使轮</li>
									<li>A轮</li>
									<li>B轮</li>
									<li>C轮</li>
									<li>D轮及以上</li>
									<li>上市公司</li>
								</ul>
							</div>

							<h3>一句话介绍</h3>
							<input type="text" placeholder="一句话概括公司亮点，如公司愿景、领导团队等，限50字"
								maxlength="50" name="brief" id="brief" value="${companyinfo.brief }"> 
							<span	style="display: none;" class="error" id="beError"></span> 
							 <input type="submit"
								value="保存" id="stepBtn" class="btn_big fr">
						</form>
					</dd>
				</dl>
			</div>

			<!-------------------------------------弹窗lightbox  ----------------------------------------->
			<div style="display: none;">
				<!--图片上传-->
				<div style="width: 650px; height: 470px;" class="popup"
					id="logoUploader">
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
				</div>
				<!-- #logoUploader -->
			</div>
			<!------------------------------------- end ----------------------------------------->
			<script src="<%=basePath%>style/js/step1.min.js" type="text/javascript"></script>
			<script>
var avatar = {};
avatar.uploadComplate = function( data ){
	var result = eval('('+ data +')');
	if(result.success){
		jQuery('#logoShow img').attr("src",ctx+ '/'+result.content);
		jQuery.colorbox.close();
		jQuery('#logoNo').hide();
		jQuery('#logoShow').show();
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
			<input type="hidden" value="13ae35fedd9e45cdb66fb712318d7369"
				id="resubmitToken"> <a rel="nofollow" title="回到顶部"
				id="backtop" style="display: none;"></a>
		</div>
		<!-- end #container -->
	</div>
	<!-- end #body -->
	<div id="footer">
		<div class="wrapper">
			<a rel="nofollow" target="_blank" href="about.html">联系我们</a> <a
				target="_blank" href="http://www.lagou.com/af/zhaopin.html">互联网公司导航</a>
			<a rel="nofollow" target="_blank" href="http://e.weibo.com/lagou720">拉勾微博</a>
			<a rel="nofollow" href="javascript:void(0)" class="footer_qr">拉勾微信<i></i></a>
			<div class="copyright">
				&copy;2013-2014 Lagou <a
					href="http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action"
					target="_blank">京ICP备14023790号-2</a>
			</div>
		</div>
	</div>

	<script src="<%=basePath%>style/js/core.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>style/js/popup.min.js" type="text/javascript"></script>

	<!--  -->


	<div id="cboxOverlay" style="display: none;"></div>
	<div id="colorbox" class="" role="dialog" tabindex="-1" style="display: none;">
		<div id="cboxWrapper">
			<div>
				<div id="cboxTopLeft" style="float: left;"></div>
				<div id="cboxTopCenter" style="float: left;"></div>
				<div id="cboxTopRight" style="float: left;"></div>
			</div>
			<div style="clear: left;">
				<div id="cboxMiddleLeft" style="float: left;"></div>
				<div id="cboxContent" style="float: left;">
					<div id="cboxTitle" style="float: left;"></div>
					<div id="cboxCurrent" style="float: left;"></div>
					<button type="button" id="cboxPrevious"></button>
					<button type="button" id="cboxNext"></button>
					<button id="cboxSlideshow"></button>
					<div id="cboxLoadingOverlay" style="float: left;"></div>
					<div id="cboxLoadingGraphic" style="float: left;"></div>
				</div>
				<div id="cboxMiddleRight" style="float: left;"></div>
			</div>
			<div style="clear: left;">
				<div id="cboxBottomLeft" style="float: left;"></div>
				<div id="cboxBottomCenter" style="float: left;"></div>
				<div id="cboxBottomRight" style="float: left;"></div>
			</div>
		</div>
		<div style="position: absolute; width: 9999px; visibility: hidden; display: none;"></div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function($){
		var leave = false;
	
		<%if(!StringHelper.isEmpty(companyUser.getImage())){%>
		jQuery('#logoShow img').attr("src",ctx+ '/<%=companyUser.getImage()%>');
		jQuery('#logoNo').hide();
		jQuery('#logoShow').show();
		<%}%>
		
		<%if(!StringHelper.isEmpty(companyInfo.getHangyevalue())){%>
		jQuery('#select_industry').val('<%=companyInfo.getHangyevalue()%>');
		<%}%>
		<%if(!StringHelper.isEmpty(companyInfo.getGuimovalue())){%>
		jQuery('#select_scale').val('<%=companyInfo.getGuimovalue()%>');
		<%}%>
		
		window.onbeforeunload=function(){
			
			return leave?void 0:"内容还未保存，确认离开该页面吗？ "
		}
		
		
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
		
		/**发展阶段 */
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
				    $("#jieduan_type li").click(function() {
						var b = $(this).text();
						$("#jieduanvalue").val(b),
						$("#jieduan").val($(this).attr("value")),
						$(this).addClass("current").append("<em></em>").siblings().removeClass("current").find("em").remove(),
						$("#stepForm").validate().element("#jieduanvalue")
					})
					<%if(!StringHelper.isEmpty(companyInfo.getJieduan())){%>
					jQuery('#jieduan_type li[value="<%=companyInfo.getJieduan()%>"]').click();
					<%}%>
		       }  
		});
		
		$("#stepForm").validate({
			onkeyup: true,
			focusCleanup: false,
			rules: {
				name: {
					required: true,
					specialchar: false,
					maxlenStr: 100
				},
				phone: {
					required: true,
					maxlenStr: 13
				},
				website: {
					required: true,
					maxlength: 120
				},
				shengshi: {
					required: true,
					maxlenStr: 20
				},
				hangye:{required: true},
				guimo:{required: true},
				jieduan:{required: true}
			},
			messages: {
				name: {
					required: "请输入有效的公司简称",
					specialchar: "请输入有效的公司简称",
					maxlenStr: "请输入100字以内的公司简称"
				},
				phone: {
					required: "请输入有效的公司人事联系电话",
					maxlenStr: "请输入13字以内的公司人事联系电话"
				},
				website: {
					required: "请输入公司网址，如：www.baidu.com",
					maxlength: "请输入120字符以内的网址"
				},
				shengshi: {
					required: "请输入公司所在城市，如：北京",
					maxlenStr: "请输入有效的公司所在城市"
				},
				hangye:{required: "请选择公司所属行业"},
				guimo:{required: "请选择公司规模"},
				jieduan:{required: "请选择公司发展阶段"}
			},
			errorPlacement: function(a, b) {
				leave = false;
				"hidden" == b.attr("type") ? a.insertAfter($(b).parent()) : a.insertAfter(b)
			},
			submitHandler: function(b) {
				leave = false;
				var c, d, e, f, g, h, i, j, k, l, m, n, o;
				a = false,
				c = $("#companyId").val(),
				d = $("#companyName").val(),
				e = $("#name").val(),
				f = $("#logoShow img").attr("src"),
				g = $("#website").val(),
				"http://" != g.substring(0, 7) && (g = "http://" + g),
				h = $("#city").val(),
				i = $("#select_industry_hidden").val(),
				j = $("#select_scale_hidden").val(),
				k = $("#s_radio_hidden").val(),
				
				o = $("#temptation").val(),
				$(b).find(":submit").attr("disabled", false),
				$.ajax({
					type: "POST",
					data: $(b).serialize(),
					url: ctx + "/top/companyuser/saveCompanyInfo",
					dataType:"json"
				}).done(function(data) {
					//data = eval(data);
					console.log(data.success);
					if(data.success){
						window.location.href = ctx + "/jsp/company_center.jsp";
						leave = true;
					}else{
						leave = false;
						$("#beError").text(data.msg).show();
					}
					$(b).find(":submit").attr("disabled", true);
				})
			}
		})
	});
	
	
	</script>
</body>
</html>