<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
<style>
.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
}
</style>
<div class="row">
	<div class="col-xs-12">
		<table id="grid-table"></table>
		<div id="hidediv" style="z-index:99;width:300px;height:150px;padding:20px 20px; position:absolute;background-color:#fff; border:1px solid #dcd; display: none; top: 100px ;left:50% ; ">
		<p style="text-align: center; font-size: 20px;">发布课程</p>
		<input id="hideid" style="display: none;"/>
		是否发布课程：<input type="checkbox" id="is_publish" value="1" role="checkbox" /><br><br>
		<p style="text-align: center;"><button style="border:1px solid #aaa;margin-right:30px;background-color:#eee; " id="ok">确定</button><button  style="border:1px solid #aaa;margin-right:30px;background-color:#eee; "id="divclose">取消</button></p>
	</div>
		<div style="position:absolute; ;top:709px;left:162px; padding-right: 100px;">
			<span  id="editaaa" style="cursor: pointer;" > 
				<a title="编辑所选记录" data-rel="tooltip"><i class=" fa fa-pencil blue fa-lg"/></a>
			</span> &nbsp; 
			<span id="addaaa" style="cursor: pointer;" >
				<a title="添加新记录" data-rel="tooltip" ><i class="ace-icon fa fa-plus-circle purple fa-lg"/></a>&nbsp;&nbsp;  
			</span>
		</div>
		<div id="grid-pager"></div>

		<script type="text/javascript">
			var index = 0;
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
			function insertAfter(newEl, targetEl)
			{
			      var parentEl = targetEl.parentNode;
			      if(parentEl.lastChild == targetEl)
			      {
			           parentEl.appendChild(newEl);
			      }else
			      {
			           parentEl.insertBefore(newEl,targetEl.nextSibling);
			      }  
			}
			
		</script>
		
<!-- 添加  -->
 		<div style="display: none;position:absolute ;z-index:999;top:0;left:0;background-color: #fff;border: 1px solid #aaa;" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
		  	<input type="button"  style="float:left;" name="aup" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="aclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 新加记录 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div><div style="padding: 10px;">
		  	<form id="aa_form" method="post" enctype="multipart/form-data" action="${contextPath}/sys/TzzSalesPromotion/fileUpload">  
				<ul class="nav nav-tabs">
				  <li role="presentation" class="active" id="promotion_base_info_nav_a"><a href="javascript:void(0);">基本信息</a></li>
				  <li role="presentation" id="promotion_base_set_nav_a"><a href="javascript:void(0);">规则设置</a></li>
				</ul>
				<br/>
				<!-- fieldsets -->
				<input type="hidden" name="id" id="aaid"/>
					<input type="hidden" id="aaContent" name="addContent"/>
  				<fieldset id="promotion_base_info_set_a" >
				  		课程名称	&nbsp;<input type="text" name="name" id="aname" maxlength="60"/>(<span style="color:red;">*必填项</span>)<br><br>
				  		有效期	&nbsp;<input type="text" name="begin_date" id="abegin_date" class="date"/>
						至	&nbsp;<input type="text" name="end_date" id="aend_date" class="date"/>(<span style="color:red;">*必填项</span>)<br><br>
						课程类型	&nbsp;<select name="promotionType" id="apromotionType"> </select><input type="hidden" name="promotionTypeName" id="apromotionTypeName" /><br><br>
						课程简介	&nbsp;<input type="text" name="brief" id="abrief" maxlength="255"/><br><br>
						课程地点     &nbsp;<input type="text" name="contact_address" id="acontact_address" /><a href="javascript: pickcoord(0);">选择地点坐标</a><input type="text" name="coordinate" id="acoordinate" /><br/><br>
						上传图片	&nbsp;<input type='file' name='file' id='afile'/><br/><br>
						<div style="clear:both;"></div>
						 <div>
						 课程详情
						 <script id="acontent" name="contenttop" type="text/plain"></script><br><br>
						 </div>
				</fieldset>
  				<fieldset id="promotion_set_info_set_a" style="display: none;">
						
						参与年龄	&nbsp;<input type="text" name="min_age" id="amin_age" />
						至	&nbsp;<input type="text" name="max_age" id="amax_age"/><br><br>
						
						参与人数	&nbsp;<input type="text" name="participation" id="aparticipation" maxlength="10"/>
						每日限制人数	&nbsp;<input type="text" name="everyday_limit" id="aeveryday_limit" maxlength="10"/>
						教练	&nbsp;<input type="text" name="service_staff" id="aservice_staff" maxlength="10"/><br><br>
						
						是否精品课程	&nbsp;<input type="checkbox" name="is_boutique" value="1" id="ais_boutique" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>&nbsp;&nbsp;周末课程	&nbsp;<input type="checkbox" name="is_weekend" value="1" id="ais_weekend" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						<br><br>
						
						是否针对老用户	&nbsp;<input type="checkbox" name="old_member_aim" id="aold_member_aim" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						是否要选择出行人	&nbsp;<input type="checkbox" name="need_participant"  id="aneed_participant" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						是否自动加群	&nbsp;<input type="checkbox" name="community_addible" id="acommunity_addible" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span><br><br>
						
						是否免费	&nbsp;<input type="checkbox" name="free_reservation" id="afree_reservation" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						分享次数	&nbsp;<input type="text" name="shares_times" id="ashares_times" maxlength="10"/><br><br>
						
						是否需支付	&nbsp;<input type="checkbox" name="pay_need" id="apay_need" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						支付金额	&nbsp;<input type="text" name="price" id="aprice" maxlength="10"/><br><br>
						
<!-- 						是否返券	&nbsp;<input type="checkbox" name="return_coupon" id="areturn_coupon" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span> -->
<!-- 						返券金额	&nbsp;<input type="text" name="coupon" id="acoupon"  maxlength="10"/><br><br> -->
						
						支付成功提醒	&nbsp;<input type="checkbox" name="is_remind" id="ais_remind" value="1"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						提醒人手机号	&nbsp;<input type="text" name="remind_phone" id="aremind_phone"  maxlength="11"/><br><br>
						
						是否赠送礼品	&nbsp;<input type="checkbox" name="has_gift" id="ahas_gift" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						礼品名称	&nbsp;<input type="text" name="gift_name" id="agift_name" /><br><br>
						
						是否可用优惠券<input type="checkbox" name="can_use_coupon" id="acan_use_coupon" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
<!-- 						优惠券有效期<input type="text" name="fixed_term" id="afixed_term"  maxlength="5"/> -->
<!-- 						优惠券起用金额<input type="text" name="least_cost" id="aleast_cost"  maxlength="5"/> -->
<!-- 						优惠券抵用金额<input type="text" name="reduce_cost" id="areduce_cost"  maxlength="5"/> -->
						<br><br>
						
						是否可代理	&nbsp;<input type="checkbox" name="is_agent" id="ais_agent" value="1"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
						代理提成比（%）	<input type="text" name="percentage" id="apercentage" maxlength="10"/>
						推荐人好处费（%）	<input type="text" name="personalpercentage" id="apersonalpercentage" maxlength="10"/><br><br>
						
						<input type="hidden" name="is_publish" id="ais_publish" value="0"/>
						<input type="hidden" name="status" id="astatus" value="0"/>
				 </fieldset>	
				 <div style="clear: both;"></div>
				<input type="button"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" name="aup" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="aclose" value="取消">
			 </form>
			</div> 
		</div>
 <!--  修改 -->
 
		<div style="display: none;position:absolute ; z-index:999; top:0;left:0;background-color: #fff;border: 1px solid #aaa;" id="hid" >
		   <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
		  	<input type="button"  style="float:left;" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="fclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 编辑所选记录 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div><div style="padding: 10px;">
		  	<form id="ff_form" method="post" enctype="multipart/form-data" action="${contextPath}/sys/TzzSalesPromotion/fileUpload">			
				<input type="hidden" name="id" id="ffid"/>
				<input type="hidden" id="ffContent" name="addContent"/>
				<ul class="nav nav-tabs">
				  <li role="presentation" class="active" id="promotion_base_info_nav_f"><a href="javascript:void(0);">基本信息</a></li>
				  <li role="presentation" id="promotion_base_set_nav_f"><a href="javascript:void(0);">规则设置</a></li>
				</ul>
				<br/>
				<!-- fieldsets -->
				<fieldset id="promotion_base_info_set_f" >
					课程名称	&nbsp;<input type="text" name="name" id="fname" maxlength="60"/>(<span style="color:red;">*必填项</span>)<br><br>
					有效期	&nbsp;<input type="text" name="begin_date" id="fbegin_date" class="date"/>
					至	&nbsp;<input type="text" name="end_date" id="fend_date" class="date"/>(<span style="color:red;">*必填项</span>)<br><br>
					课程类型	&nbsp;<select name="promotionType" id="fpromotionType"> </select><input type="hidden" name="promotionTypeName" id="fpromotionTypeName" /><br><br>
					简介		&nbsp;<input type="text" name="brief" id="fbrief" maxlength="255"/><br><br>
					课程地点&nbsp;<input type="text" name="contact_address" id="fcontact_address" /><a href="javascript: pickcoord(1);">选择地点坐标</a><input type="text" name="coordinate" id="fcoordinate" /><br><br>
					<input type="hidden" name="img" id="image">
					<span  id="fimage"> 
					</span> 
					<div style="clear:both;"></div>
					<div>
					内容简介：
					 <script id="fcontent" name="contenttop" type="text/plain"></script><br><br>
					 </div>
				</fieldset>
  				<fieldset id="promotion_set_info_set_f" style="display: none;">
					
					参与年龄	&nbsp;<input type="text" name="min_age" id="fmin_age" />
					至	&nbsp;<input type="text" name="max_age" id="fmax_age"/><br><br>
					
					参与人数	&nbsp;<input type="text" name="participation" id="fparticipation" maxlength="10"/>
					每日限制人数	&nbsp;<input type="text" name="everyday_limit" id="feveryday_limit" maxlength="10"/>
					教练	&nbsp;<input type="text" name="service_staff" id="fservice_staff" maxlength="10"/><br><br>
					
					是否精品课程	&nbsp;<input type="checkbox" name="is_boutique" value="1" id="fis_boutique" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>&nbsp;&nbsp;周末课程	&nbsp;<input type="checkbox" name="is_weekend" value="1" id="fis_weekend" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					<br><br>
					
					是否针对老用户	&nbsp;<input type="checkbox" name="old_member_aim" id="fold_member_aim" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					是否自动加群	&nbsp;<input type="checkbox" name="community_addible" id="fcommunity_addible" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					是否要选择出行人	&nbsp;<input type="checkbox" name="need_participant" value="1"  id="fneed_participant" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span><br><br>
					
					是否免费	&nbsp;<input type="checkbox" name="free_reservation" id="ffree_reservation" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					分享次数	&nbsp;<input type="text" name="shares_times" id="fshares_times" maxlength="10"/><br><br>
					
					是否需支付	&nbsp;<input type="checkbox" name="pay_need" id="fpay_need" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					支付金额	&nbsp;<input type="text" name="price" id="fprice" maxlength="10"/><br><br>
					
					
<!-- 					是否返券	&nbsp;<input type="checkbox" name="return_coupon" id="freturn_coupon" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span> -->
<!-- 					返券金额	&nbsp;<input type="text" name="coupon" id="fcoupon" /><br><br> -->
					
					支付成功提醒	&nbsp;<input type="checkbox" name="is_remind" id="fis_remind" value="1"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					提醒人手机号	&nbsp;<input type="text" name="remind_phone" id="fremind_phone"  maxlength="11"/><br><br>
					
					是否赠送礼品	&nbsp;<input type="checkbox" name="has_gift" id="fhas_gift" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					礼品名称	&nbsp;<input type="text" name="gift_name" id="fgift_name" /><br><br>
					
					是否可用优惠券<input type="checkbox" name="can_use_coupon" id="fcan_use_coupon" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
<!-- 					优惠券有效期<input type="text" name="fixed_term" id="ffixed_term"  maxlength="5"/> -->
<!-- 					优惠券起用金额<input type="text" name="least_cost" id="fleast_cost"  maxlength="5"/> -->
<!-- 					优惠券抵用金额<input type="text" name="reduce_cost" id="freduce_cost"  maxlength="5"/> -->
					<br><br>
  				
  					是否可代理	&nbsp;<input type="checkbox" name="is_agent" id="fis_agent" value="1"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					代理提成比（%）	<input type="text" name="percentage" id="fpercentage" maxlength="10"/>
					推荐人好处费（%）	<input type="text" name="personalpercentage" id="fpersonalpercentage" maxlength="10"/><br><br>
					
					<input type="hidden" name="is_publish" id="fis_publish" />
					<input type="hidden" name="status" id="fstatus" />
  				</fieldset>
				
				<div style="clear: both;"></div>
				<input type="button" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="fclose" value="取消">
			 </form>
			 </div>
		</div>
		
		
		<div style="display: none;position:absolute ; z-index:999; top:0;left:0;background-color: #fff;border: 1px solid #aaa;" id="vid" >
		   <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
			 <input type="button"  style="float:left;" name="vclose" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 查看所选记录(发布课程以后不可编辑) &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div><div style="padding: 10px;">
		  	<form id="vv_form" method="post" enctype="multipart/form-data" action="">			
				<input type="hidden" name="id" id="vvid"/>
				<input type="hidden" id="vvContent" name="addContent"/>
				<ul class="nav nav-tabs">
				  <li role="presentation" class="active" id="promotion_base_info_nav_v"><a href="javascript:void(0);">基本信息</a></li>
				  <li role="presentation" id="promotion_base_set_nav_v"><a href="javascript:void(0);">规则设置</a></li>
				</ul>
				<br/>
				<!-- fieldsets -->
				<fieldset id="promotion_base_info_set_v" >
					课程名称	&nbsp;<input type="text" name="name" id="vname" maxlength="60" disabled="disabled"/><br><br>
					有效期	&nbsp;<input type="text" name="begin_date" id="vbegin_date" disabled="disabled"/>
					至	&nbsp;<input type="text" name="end_date" id="vend_date" disabled="disabled"/><br><br>
					课程类型	&nbsp;<select name="promotionType" id="vpromotionType" disabled="disabled"> </select><input type="hidden" name="promotionTypeName" id="vpromotionTypeName" /><br><br>
					简介		&nbsp;<input type="text" name="brief" id="vbrief" maxlength="255" disabled="disabled"/><br><br>
					课程地点&nbsp;<input type="text" name="contact_address" id="vcontact_address"  disabled="disabled"/>地点坐标<input type="text" name="coordinate" id="vcoordinate" disabled="disabled"/><br><br>
					<input type="hidden" name="img" id="vimage">
					<span  id="vimage"> 
					</span> 
					<div style="clear:both;"></div>
					<div>
					内容简介：
					 <script id="vcontent" name="contenttop" type="text/plain"></script><br><br>
					 </div>
				</fieldset>
  				<fieldset id="promotion_set_info_set_v" style="display: none;">
					
					参与年龄	&nbsp;<input type="text" name="min_age" id="vmin_age" disabled="disabled"/>
					至	&nbsp;<input type="text" name="max_age" id="vmax_age" disabled="disabled"/><br><br>
					
					参与人数	&nbsp;<input type="text" name="participation" id="vparticipation" maxlength="10" disabled="disabled"/>
					每日限制人数	&nbsp;<input type="text" name="everyday_limit" id="veveryday_limit" maxlength="10" disabled="disabled"/>
					服务人员	&nbsp;<input type="text" name="service_staff" id="vservice_staff" maxlength="10" disabled="disabled"/><br><br>
					
					是否精品课程	&nbsp;<input type="checkbox" name="is_boutique" value="1" id="vis_boutique" disabled="disabled" role="checkbox" class="FormElement ace ace-switch ace-switch-5" /><span class="lbl"></span>&nbsp;&nbsp;周末课程	&nbsp;<input type="checkbox" disabled="disabled"  name="is_weekend" value="1" id="vis_weekend" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					<br><br>
					
					是否针对老用户	&nbsp;<input type="checkbox" name="old_member_aim" id="vold_member_aim" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					是否自动加群	&nbsp;<input type="checkbox" name="community_addible" id="vcommunity_addible" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					是否要选择出行人	&nbsp;<input type="checkbox" name="need_participant" value="1"  id="vneed_participant" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span><br><br>
					
					是否免费	&nbsp;<input type="checkbox" name="free_reservation" id="vfree_reservation" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					分享次数	&nbsp;<input type="text" name="shares_times" id="vshares_times"  disabled="disabled" maxlength="10"/><br><br>
					
					是否需支付	&nbsp;<input type="checkbox" name="pay_need" id="vpay_need" role="checkbox" disabled="disabled"  class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					支付金额	&nbsp;<input type="text" name="price" id="vprice" disabled="disabled"  maxlength="10"/><br><br>
					
					
<!-- 					是否返券	&nbsp;<input type="checkbox" name="return_coupon" disabled="disabled"  id="vreturn_coupon" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span> -->
<!-- 					返券金额	&nbsp;<input type="text" name="coupon" disabled="disabled"  id="vcoupon" /><br><br> -->
					
					支付成功提醒	&nbsp;<input type="checkbox" name="is_remind" id="vis_remind" value="1"  disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					提醒人手机号	&nbsp;<input type="text" name="remind_phone" id="vremind_phone"  disabled="disabled"  maxlength="10"/><br><br>
					
					是否赠送礼品	&nbsp;<input type="checkbox" name="has_gift" id="vhas_gift" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					礼品名称	&nbsp;<input type="text" name="gift_name" id="vgift_name" disabled="disabled" /><br><br>
					
					是否可用优惠券<input type="checkbox" name="can_use_coupon" id="vcan_use_coupon" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
<!-- 					优惠券有效期<input type="text" name="fixed_term" id="vfixed_term"  disabled="disabled"  maxlength="5"/> -->
<!-- 					优惠券起用金额<input type="text" name="least_cost" id="vleast_cost"  disabled="disabled"  maxlength="5"/> -->
<!-- 					优惠券抵用金额<input type="text" name="reduce_cost" id="vreduce_cost"  disabled="disabled"  maxlength="5"/> -->
					<br><br>
  				
  					是否可代理	&nbsp;<input type="checkbox" name="is_agent" id="vis_agent" disabled="disabled"  value="1"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					代理提成比（%）	<input type="text" name="percentage" id="vpercentage" disabled="disabled"  maxlength="10"/>
					推荐人好处费（%）	<input type="text" name="personalpercentage" id="vpersonalpercentage" maxlength="10"/><br><br>
					
					<input type="hidden" name="is_publish" id="vis_publish" />
					<input type="hidden" name="status" id="vstatus" />
  				</fieldset>
				
				<div style="clear: both;"></div>
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="vclose" value="取消">
			 </form>
			 </div>
		</div>
		
		<div id="modal-table" class="modal fade" tabindex="-1" data-backdrop="static">
			<div class="modal-dialog">
				<form id="informationForm">
					<div class="modal-content">
						<div class="modal-header no-padding">
							<div class="table-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									<span class="white">&times;</span>
								</button>
								选择优惠券
							</div>
						</div>
						<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
							<div id="modal-tip" class="red clearfix"></div>
							<input id="promotionId" type="hidden" />
							<div class="widget-box widget-color-blue2">
								<div class="widget-body">
									<div class="widget-main padding-8">
										<ul id="couponTree"></ul>
									</div>
									
								</div>
							</div>
						</div>
						<div class="modal-footer no-margin-top">
							<div class="text-center">
								<button id="submitButton" type="submit" class="btn btn-app btn-success btn-xs">
									<i class="ace-icon fa fa-floppy-o bigger-160"></i>
									保存
								</button>
								<button class="btn btn-app btn-pink btn-xs" data-dismiss="modal">
									<i class="ace-icon fa fa-share bigger-160"></i>
									取消
								</button>
							</div>
						</div>
					</div><!-- /.modal-content -->
				</form>
			</div><!-- /.modal-dialog -->
		</div>
		
		<div id="modal-table2" class="modal fade" tabindex="-1" data-backdrop="static">
			<div class="modal-dialog">
				<form id="informationForm2">
					<div class="modal-content">
						<div class="modal-header no-padding">
							<div class="table-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									<span class="white">&times;</span>
								</button>
								审核不通过原因
							</div>
						</div>
						<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
							<div id="modal-tip2" class="red clearfix"></div>
							<input id="promotionId" type="hidden" />
							<div class="widget-box widget-color-blue2">
								<div class="widget-body">
									<div class="widget-main padding-8">
										<span id="rejectreason"> </span><br>
									</div>
									
								</div>
							</div>
						</div>
						<div class="modal-footer no-margin-top">
							<div class="text-center">
								<button class="btn btn-app btn-pink btn-xs" data-dismiss="modal">
									<i class="ace-icon fa fa-share bigger-160"></i>
									关闭
								</button>
							</div>
						</div>
					</div><!-- /.modal-content -->
				</form>
			</div><!-- /.modal-dialog -->
		</div>
		
		<div id="modal-table3" class="modal fade" tabindex="-1" data-backdrop="static">
			<div class="modal-dialog">
				<form id="qualityForm">
					<div class="modal-content">
						<div class="modal-header no-padding">
							<div class="table-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									<span class="white">&times;</span>
								</button>
								选择对应的素质项
							</div>
						</div>
						<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
							<div id="modal-tip" class="red clearfix"></div>
							<input id="courseid" type="hidden" />
							<div class="widget-box widget-color-blue2">
								<div class="widget-body">
									<div class="widget-main padding-8">
										<ul id="qualityTree"></ul>
									</div>
									
								</div>
							</div>
						</div>
						<div class="modal-footer no-margin-top">
							<div class="text-center">
								<button id="qualitysubmitButton" type="submit" class="btn btn-app btn-success btn-xs">
									<i class="ace-icon fa fa-floppy-o bigger-160"></i>
									保存
								</button>
								<button class="btn btn-app btn-pink btn-xs" data-dismiss="modal">
									<i class="ace-icon fa fa-share bigger-160"></i>
									取消
								</button>
							</div>
						</div>
					</div><!-- /.modal-content -->
				</form>
			</div><!-- /.modal-dialog -->
		</div>
		
		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
 
 <script type="text/javascript"> 
    $("#aid").draggable();
	$("#hid").draggable();
	function publicaction(id){
		if(confirm("确定要发布课程？")){
			$.ajax({
		    	   type:"post",
				   url:" ${contextPath}/sys/TzzSalesPromotion/publicSalesPromotion",
				   dataType:"json", 
				  	   data:{"ispublic":1 ,"id":id},
				  	   success:function(data){
				  		   if(data.err==0){
				  			   if(data.msg!=""&&data.msg!=null){
				  				 alert(data.msg); 
				  			   }else{
				  				 alert("修改成功"); 
				  				jQuery("#grid-table").trigger("reloadGrid");
				  			   }
//		 						$("#hidediv").hide(); 
//		 						$("#hideid").val("");
				  		   }else{
				  				alert("修改失败"); 
				  		   } 
				  	   }
				});
		}
		
		
// 		$("#hideid").val(id);
// 		$.ajax({//classifycn
// 	        type:"post", 
// 	        url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotion",
// 	        data:{"id":id},
// 	   		dataType:"json", 
// 	       	success:function(data){ 
// 	       		if(data.success){
// 	       			if(data.obj.is_publish==1){
// 	       				document.getElementById("is_publish").checked=true;
// 						//$("#is_publish").attr("checked","checked");
// 					}
// 	       			else{
// 						//document.getElementById("is_publish").checked=false;
// 						$("#is_publish").attr("checked",false);
// 					}
// 	       			$("#hidediv").show(); 
// 	       		}
	       		
// 	       	}
// 		});
		
	}
	
	function cancelpublish(id){
	 if(confirm("确定要取消课程发布？")){
		 $.ajax({
    	   type:"post",
		   url:" ${contextPath}/sys/TzzSalesPromotion/publicSalesPromotion",
		   dataType:"json", 
		  	   data:{"ispublic":0 ,"id":id},
		  	   success:function(data){
		  		   if(data.err==0){
		  			   if(data.msg!=""&&data.msg!=null){
		  				 alert(data.msg); 
		  			   }else{
		  				 alert("修改成功"); 
		  				jQuery("#grid-table").trigger("reloadGrid");
		  			   }
// 						$("#hidediv").hide(); 
// 						$("#hideid").val("");
		  		   }else{
		  				alert("修改失败"); 
		  		   } 
		  	   }
		});
	   }
	}
	
	function tocheck(id){
	 if(confirm("确定提交课程去审核？")){
		 $.ajax({
				url : "${contextPath}/sys/TzzSalesPromotion/changePromotionStatus",
				data : {
					"promotionid" : id,
					"status" : 0
				},
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					if(data.err=="0"){
						alert("提交成功");
						jQuery("#grid-table").trigger("reloadGrid");
					}
				}
			});
	   }
	}
	
	$("#divclose").click(function(){
		$("#hideid").val("");
		$("#hidediv").hide(); 
		
	});
	$("#ok").click(function(){
			$.ajax({
	      		   type:"post",
     		   url:" ${contextPath}/sys/TzzSalesPromotion/publicSalesPromotion",
     		   dataType:"json", 
    		  	   data:{"ispublic":document.getElementById("is_publish").checked?1:0 ,"id":$("#hideid").val()},
    		  	   success:function(data){
    		  		   if(data.err==0){
    		  			   if(data.msg!=""&&data.msg!=null){
    		  				 alert(data.msg); 
    		  			   }else{
    		  				 alert("修改成功"); 
    		  			   }
							$("#hidediv").hide(); 
							$("#hideid").val("");
    		  		   }else{
    		  				alert("修改失败"); 
    		  		   } 
    		  	   }
			});
	});
// 	$.ajax({
// 	   	   type:"post",
// 	     	url:" ${contextPath}/sys/TzzSalesPromotion/getStoreAvailableSelectList",
// 	   	   success:function(data){
// 	   				$("#store_checkbox").append(data); 
// 	   	   }
// 	   	});
    </script>
<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<script type="text/javascript"src="${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js"></script> 
<script type="text/javascript"src="${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script> 
<script type="text/javascript"src="${contextPath}/static/edit/ueditor.config.js"></script> 
<script type="text/javascript"src="${contextPath}/static/edit/ueditor.all.js"></script> 

<!-- page specific plugin scripts -->
<script type="text/javascript">
// function autosave(i){
// 	console.log($("#"+i+"name").val());
// 	console.log($("#"+i+"begin_date").val());
// 	console.log($("#"+i+"end_date").val());
// 	if($("#"+i+"name").val()!=""&&$("#"+i+"name").val()!=null &&$("#"+i+"begin_date").val()!=""&&$("#"+i+"begin_date").val()!=null&&$("#"+i+"end_date").val()!=""&&$("#"+i+"end_date").val()!=null){
// 		console.log(new Date());
// 		$.ajax({
// 	                type: "POST",
// 	                url:"${contextPath}/sys/TzzSalesPromotion/autosave",
// 	                data:$('#'+i+i+'_form').serialize(),// 你的formid
// 	                error: function(request) {
// 	                    //alert("Connection error");
// 	                },
// 	                success: function(data) {
// 	                    console.log(data);
// 						$("#"+i+i+"id").val(data);
// 						console.log($("#"+i+i+"id"))
// 	                }
// 	            });
// 	}
// }
var poll;
		var scripts = [ null,
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                "${contextPath}/static/assets/js/fuelux/fuelux.tree.js",
		        		 "${contextPath}/static/assets/js/ace-extra.js",
		                 null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		
        		$('#promotion_base_info_nav_a').on('click', function() { 
           			$('#promotion_base_info_nav_a').addClass("active");
           			$('#promotion_base_set_nav_a').removeClass("active");
           			$('#promotion_base_info_set_a').show();
           			$('#promotion_set_info_set_a').hide();
    			});
           		
           		$('#promotion_base_set_nav_a').on('click', function() {
           			$('#promotion_base_info_nav_a').removeClass("active");
           			$('#promotion_base_set_nav_a').addClass("active");
           			$('#promotion_base_info_set_a').hide();
           			$('#promotion_set_info_set_a').show();
    			});
           		
           		$('#promotion_base_info_nav_f').on('click', function() { 
           			$('#promotion_base_info_nav_f').addClass("active");
           			$('#promotion_base_set_nav_f').removeClass("active");
           			$('#promotion_base_info_set_f').show();
           			$('#promotion_set_info_set_f').hide();
    			});
           		
           		$('#promotion_base_set_nav_f').on('click', function() {
           			$('#promotion_base_info_nav_f').removeClass("active");
           			$('#promotion_base_set_nav_f').addClass("active");
           			$('#promotion_base_info_set_f').hide();
           			$('#promotion_set_info_set_f').show();
    			});
           		$('#promotion_base_info_nav_v').on('click', function() { 
           			$('#promotion_base_info_nav_v').addClass("active");
           			$('#promotion_base_set_nav_v').removeClass("active");
           			$('#promotion_base_info_set_v').show();
           			$('#promotion_set_info_set_v').hide();
    			});
           		
           		$('#promotion_base_set_nav_v').on('click', function() {
           			$('#promotion_base_info_nav_v').removeClass("active");
           			$('#promotion_base_set_nav_v').addClass("active");
           			$('#promotion_base_info_set_v').hide();
           			$('#promotion_set_info_set_v').show();
    			});
        		$('.date').datepicker({
					format : 'yyyy-mm-dd',
					autoclose : true,
				    language: 'zh-CN'
				});
//            		$('#abegin_date').datepicker({
// 					format : 'yyyy-mm-dd',
// 					autoclose : true,
// 				    language: 'zh-CN'
// 				}).on('changeDate',function(ev){
// 					   alert("ev: "+ev.date);
// 					   });
        		
        		
        		$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/promotiontype/getSelectList",
			   		dataType:"html", 
			       	success:function(data){ 
			       		$("#fpromotionType").append(data); 
			       		$("#apromotionType").append(data);  
			       		
			       		$("#apromotionTypeName").val($("#apromotionType option").eq(0).text());
			       		$("#fpromotionTypeName").val($("#fpromotionType option").eq(0).text());
			       		
			       		$("#apromotionType").on('change', function() {
			       			var value = $("#apromotionType").find("option:selected").text();
			       			$("#apromotionTypeName").val(value);
			       		});
			       		$("#fpromotionType").on('change', function() {
			       			var value = $("#fpromotionType").find("option:selected").text();
			       			$("#fpromotionTypeName").val(value);
			       		});
			       	} 
			    });
        		
        		var grid_selector = "#grid-table";
        		var pager_selector = "#grid-pager";

        		// resize to fit page size
        		$(window).on('resize.jqGrid', function() {
        			$(grid_selector).jqGrid('setGridWidth', $(".page-content").width());
        		})
        		// resize on sidebar collapse/expand
        		var parent_column = $(grid_selector).closest('[class*="col-"]');
        		$(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed) {
        			if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
        				// setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        				setTimeout(function() {
        					$(grid_selector).jqGrid('setGridWidth', parent_column.width());
        				}, 0);
        			}
        		})

        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			autowidth: true,
        			url : "${contextPath}/sys/TzzSalesPromotion/getSalesPromotionList",
        			datatype : "json",
        			height : 650,
        			colNames : ['','套餐管理', 'ID', '课程类别','课程类别', '课程名称', '导语','金额','创建人','创建时间','课程状态','是否发布','操作','设置素质项'],
        			colModel : [ {
        				name : '',
        				index : '',
        				width : 50,
        				fixed : true,
        				sortable : false,
        				resize : false,
        				formatter : 'actions',
        				formatoptions : {
        					keys : true,
        					editbutton : false,//disable edit button
        					delOptions : {
        						recreateForm : true,
        						beforeShowForm : beforeDeleteCallback
        					}
        					//editformbutton:true, editOptions:{recreateForm:true, beforeShowForm:beforeEditCallback}
        				}
        			}, {
        				name : 'id',
        				index : 'id',
        				label : '套餐管理',
        				width : 80,
        				formatter: function (value, grid, rows, state) 
        					{
        					 return "<a href=\"${contextPath}/sys/sysuser/home#page/promotioncombo?sid=" + value + "&ispublish="+rows.is_publish+"\" style=\"color:#9ab\"> 套餐管理 </a>";
        					  
        					 } ,
        				sorttype : "int",
        				search : false
        			}, {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sorttype : "long",
        				search : false
        			}, {
        				name : 'promotionType',
        				index : 'promotionType',
        				label : '课程类别',
        				width : 80,
        				hidden : true,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'promotionTypeName',
        				index : 'promotionTypeName',
        				label : '课程类别',
        				width : 80,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'name',
        				index : 'name',
        				label : '课程名称',
        				width : 240,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'brief',
        				index : 'brief',
        				label : '导语',
        				width : 400,
        				editable : true,
        				editoptions : {size : "20", maxlength : "255"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'price',
        				index : 'price',
        				label : '金额',
        				width : 100,
        					search : false 
        			}, {
        				name : 'creater',
        				index : 'creater',
        				label : '创建人',
        				width : 80  ,
        				editable : true,
        				search : false	
        				
        			}, {
        				name : 'creattime',
        				index : 'creattime',
        				label : '创建时间',
        				width : 150 ,
        				editable : true,
        				search : false 	
        			}, {
        				name : 'status',
        				index : 'status',
        				label : '课程状态',
        				width : 120 ,
        				editable : true,
        				formatter : statusFormatter,
        				search : false 	
        			}, {
        				name : 'is_publish',
        				index : 'is_publish',
        				label : '是否发布',
        				width : 80 ,
        				editable : true,
        				formatter : booleanFormatter,
        				search : false 	
        			}, {
        				name : 'id',
        				index : 'id',
        				label : '操作',
        				width : 100 ,
        				editable : true,
        				formatter: publishFormatter,
//         				formatter: function (value, grid, rows, state) {
//        					 return "&nbsp;&nbsp;&nbsp;&nbsp;<button id= \"" + value + "\" style=\"background-color:#eee;border:1px solid #777; \" onclick='publicaction(" + value + ")'>发布课程</button>";} ,
        				search : false 	
        			}, {
    				name : 'id',
    				index : 'id',
    				label : '设置素质项',
    				width : 100  ,
    				editable : false,
    				search : false,
    				sortable : false,
    				formatter : qualitySet
    			}
//         			, {
//         				name : 'id',
//         				index : 'id',
//         				label : '设置返券',
//         				width : 110  ,
//         				editable : false,
//         				search : false,
//         				sortable : false,
//         				formatter : couponFormatter
//         			}
					],
        			//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
        			sortname : "id",
        			sortorder : "asc",
        			viewrecords : true,
        			rowNum : 10,
        			rowList : [ 10, 20, 30 ],
        			pager : pager_selector,
        			altRows : true,
        			shrinkToFit:false,  
        			autoScroll: true, 
        			//toppager : true,
        			multiselect : true,
        			//multikey : "ctrlKey",
        	        multiboxonly : true,
        			loadComplete : function() {
        				var table = this;
        				setTimeout(function(){
        					styleCheckbox(table);
        					updateActionIcons(table);
        					updatePagerIcons(table);
        					enableTooltips(table);
        				}, 0);
        			},
        			editurl : "${contextPath}/sys/TzzSalesPromotion/operateSalesPromotion"
        			//caption : "用户管理列表",
        			//autowidth : true,
        			/**
        			grouping : true, 
        			groupingView : { 
        				 groupField : ['name'],
        				 groupDataSorted : true,
        				 plusicon : 'fa fa-chevron-down bigger-110',
        				 minusicon : 'fa fa-chevron-up bigger-110'
        			},
        			*/
        		});
        //=====================================================
        	$('[data-rel=tooltip]').tooltip();
			$('[data-rel=popover]').popover({html:true});
        		//显示图片格式
				function alarmFormatter(cellvalue, options, rowdata) {  
					return '<img id="imagel" width="200px" height="100px" src="${contextPath}'+cellvalue+'" alt="' + cellvalue + '" />';
					
				}
				function booleanFormatter(cellvalue, options, rowdata) {  
					if(cellvalue==1)
						return "是";
					else
					return "否";
				}
				
				function statusFormatter(cellvalue, options, rowdata) {  
					if(cellvalue==1)
						return "审核通过";
					else if(cellvalue==2)
						return "审核不通过 <a href=\"javascript:viewreason('" + rowdata.approval_opinion +"');\">原因</a>";
					else if(cellvalue==0)
						return "待审核";
					else if(cellvalue==3)
						return "修改中 <button id= \"" + rowdata.id + "\" style=\"background-color:#eee;border:1px solid #777; \" onclick='tocheck(" + rowdata.id + ")'>去审核</button>";
				}
				
				function couponFormatter(cellvalue, options, cell) {
        			var template = "<button data-toggle='modal' onclick='javascript:$(\"#modal-table\").modal(\"toggle\");$(\"#promotionId\").val(\"" + cell.id +"\");' class='btn btn-xs btn-warning'><i class='ace-icon fa fa-flag bigger-120'></i></button>";
        			return template;
        		}
				
				function qualitySet(cellvalue, options, cell) {
        			var template = "<button data-toggle='modal' onclick='javascript:$(\"#modal-table3\").modal(\"toggle\");$(\"#courseid\").val(\"" + cell.id +"\");' class='btn btn-xs btn-warning'><i class='ace-icon fa fa-flag bigger-120'></i></button>";
        			return template;
        		}
				
				function publishFormatter(cellvalue, options, cell) {
					if(cell.status==1){
						if(cell.is_publish==1){
	        				return "&nbsp;&nbsp;&nbsp;&nbsp;<button id= \"" + cell.id + "\" style=\"background-color:#eee;border:1px solid #777; \" onclick='cancelpublish(" + cell.id + ")'>下架课程</button>";
						}else{
	        				return "&nbsp;&nbsp;&nbsp;&nbsp;<button id= \"" + cell.id + "\" style=\"background-color:#eee;border:1px solid #777; \" onclick='publicaction(" + cell.id + ")'>发布课程</button>";
						}
					}else{
						return "";
					}
        		}
				
				var treeflag = 0;
        		$("#modal-table").on('shown.bs.modal', function() {
        			var remoteDateSource = function(options, callback) {
        				var parent_id = null;
        				console.log("options"+options);
            			if (!('text' in options || 'type' in options)) {
            				parent_id = 0;// load first level data
            			} else if ('type' in options && options['type'] == 'folder') {// it has children
            				if ('additionalParameters' in options && 'children' in options.additionalParameters)
            					parent_id = options.additionalParameters['id'];
            			}
            			console.log("parent_id"+parent_id);
            			if (parent_id !== null) {
            				$.ajax({
            					url : "${contextPath}/sys/syscoupon/getSyscouponTreeList?rand=" + Math.random(1000),
            					type : 'POST',
            					data : {"promotionId" : $("#promotionId").val(),
            						"id" : parent_id
            						},
            					dataType : 'json',
            					complete : function(response) {
            						var returninfo = eval("(" + response.responseText + ")");
            						if (returninfo.status == "OK") {
            							callback({
            								data : returninfo.data
            							});
            						}
            					}
            				});
            			}
            		};
            		$('#couponTree').ace_tree({
            			dataSource : remoteDateSource,
            			multiSelect : false,
            			loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
            			'open-icon' : 'ace-icon tree-minus',
            			'close-icon' : 'ace-icon tree-plus',
            			'selectable' : true,
            			'selected-icon' : 'ace-icon fa fa-check',
            			'unselected-icon' : 'ace-icon fa fa-times',
            			cacheItems : false,
            			folderSelect : false
            		});
            		//$('#authorityTree').ace_tree('discloseAll');
        		    if(treeflag > 0){
        		    	$('#couponTree').tree('render');
        		    }
        		    treeflag++;
        		    $('#couponTree').one('loaded.fu.tree', function (event, data) {
        		    	$('#couponTree').data('ignore-disclosures-limit', true);
        		    	$('#couponTree').tree('discloseAll');
       		    	})
        		});
        		
        		$('#submitButton').on('click', function() {        			
        			var output = '';
					var items = $('#couponTree').tree('selectedItems');
					for(var i in items) if (items.hasOwnProperty(i)) {
						var item = items[i];
						output += item.additionalParameters['id'];
					}
					if(output == ''){
        				$.gritter.add({
    		                title: "优惠券信息",
    		                text: "请展开树勾选菜单",
    		                class_name: "gritter-info gritter-center"
    		            });  
	        			return;
					}
					$.ajax({
    					url : "${contextPath}/sys/syscoupon/asignPromotionCoupon",
    					data : {
    						promotionId : $("#promotionId").val(),
    						couponId : output
    					},
    					type : 'POST',
    					dataType : 'json',
    					complete : function(response) {
    						$("#modal-table").modal("toggle");
    					}
    				});
					//$('#authorityTree').tree('destroy');
				});
				
        		var qualitytreeflag = 0;
        		$("#modal-table3").on('shown.bs.modal', function() {
        			var remoteDateSource = function(options, callback) {
        				var parent_id = null;
        				console.log("options"+options);
            			if (!('text' in options || 'type' in options)) {
            				parent_id = 0;// load first level data
            			} else if ('type' in options && options['type'] == 'folder') {// it has children
            				if ('additionalParameters' in options && 'children' in options.additionalParameters)
            					parent_id = options.additionalParameters['id'];
            			}
            			console.log("parent_id"+parent_id);
            			if (parent_id !== null) {
            				$.ajax({
            					url : "${contextPath}/sys/coursequality/getQualityTreeList?rand=" + Math.random(1000),
            					type : 'POST',
            					data : {"courseid" : $("#courseid").val(),
            						"id" : parent_id
            						},
            					dataType : 'json',
            					complete : function(response) {
            						var returninfo = eval("(" + response.responseText + ")");
            						if (returninfo.status == "OK") {
            							callback({
            								data : returninfo.data
            							});
            						}
            					}
            				});
            			}
            		};
            		$('#qualityTree').ace_tree({
            			dataSource : remoteDateSource,
            			multiSelect : true,
            			loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
            			'open-icon' : 'ace-icon tree-minus',
            			'close-icon' : 'ace-icon tree-plus',
            			'selectable' : true,
            			'selected-icon' : 'ace-icon fa fa-check',
            			'unselected-icon' : 'ace-icon fa fa-times',
            			cacheItems : false,
            			folderSelect : false
            		});
            		//$('#authorityTree').ace_tree('discloseAll');
        		    if(qualitytreeflag > 0){
        		    	$('#qualityTree').tree('render');
        		    }
        		    qualitytreeflag++;
        		    $('#qualityTree').one('loaded.fu.tree', function (event, data) {
        		    	$('#qualityTree').data('ignore-disclosures-limit', true);
        		    	$('#qualityTree').tree('discloseAll');
       		    	})
        		});
        		
        		$('#qualitysubmitButton').on('click', function() {        			
        			var output = '';
					var items = $('#qualityTree').tree('selectedItems');
					for(var i in items) if (items.hasOwnProperty(i)) {
						var item = items[i];
						if(i>0){
							output += (","+item.additionalParameters['id']);
						}else{
							output += item.additionalParameters['id'];
						}
					}
					if(output == ''){
        				$.gritter.add({
    		                title: "素质项信息",
    		                text: "请展开树勾选菜单",
    		                class_name: "gritter-info gritter-center"
    		            });  
	        			return;
					}
					$.ajax({
    					url : "${contextPath}/sys/coursequality/saveCourseQuality",
    					data : {
    						courseid : $("#courseid").val(),
    						qualityids : output
    					},
    					type : 'POST',
    					dataType : 'json',
    					complete : function(response) {
    						$("#modal-table3").modal("toggle");
    					}
    				});
					//$('#authorityTree').tree('destroy');
				});
				
				$("#editaaa").click(function(){
					var id=$('#grid-table').jqGrid('getGridParam','selrow');
					if(id==null) {
						alert("请选择记录！");
						return;
					}
				 	window.location.href="${contextPath}/sys/sysuser/home#page/salespromotionedit?spid="+id;
// 					var rowData = $('#grid-table').jqGrid('getRowData',id);
					
					
// 					//name 简介brief 内容介绍content 成本价costPrice 金额amount 类型categoryName 
//         			//图片image 型号xinghao 规格guige 材质caizhi 品牌pinpai 产地chandi 单位danwei 点击浏览次数clickNum 销量sales 热销rexiaoName
					
// 					//var id=rowData.id;
// 					var name=rowData.name;
// 					var brief=rowData.brief;
					
// 					$.ajax({//classifycn
// 				        type:"post", 
// 				        url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotion",
// 				        data:{"id":id},
// 				   		dataType:"json", 
// 				       	success:function(data){ 
// 				       		if(data.success){
// 				       			var obj = data.obj;
// 				       			console.log(obj)
// 				       			var free_reservation=obj.free_reservation;
// 								var shares_times=obj.shares_times;
// 								var pay_need=obj.pay_need;
// 								var price=obj.price;
// 								var old_member_aim=obj.old_member_aim;
// 								var participation=obj.participation;
// 								var service_staff=obj.service_staff;
// 								var community_addible=obj.community_addible;
// 								var begin_date = new Date(obj.begin_date.time).Format("yyyy-MM-dd");
// 								var end_date = new Date(obj.end_date.time).Format("yyyy-MM-dd");
// 								var min_age = obj.min_age;
// 								var max_age = obj.max_age;
// 								var content = obj.content;
// 								var return_coupon = obj.return_coupon;
// 								var coupon = obj.coupon;
// 								var has_gift = obj.has_gift;
// 								var gift_name = obj.gift_name;
// 								var can_use_coupon = obj.can_use_coupon;
// 								var least_cost = obj.least_cost;
// 								var reduce_cost = obj.reduce_cost;
// 								var fixed_term = obj.fixed_term;
// 								var everyday_limit = obj.everyday_limit;
// 								var is_boutique = obj.is_boutique;
// 								var is_weekend = obj.is_weekend;
// 								var is_publish = obj.is_publish;
// 								console.log("is_publish:"+is_publish)
// 								var coordinate = obj.coordinate;
// 								var contact_address = obj.contact_address;
// 								var promotionType = obj.promotionType;
// 								var need_participant = obj.need_participant;
// 								var is_agent = obj.is_agent;
// 								var percentage = obj.percentage;
// 								var personalpercentage = obj.personalpercentage;
// 								var status = obj.status;
// 								var is_remind = obj.is_remind;
// 								var remind_phone = obj.remind_phone;
								
// 								//发布过的课程不能直接编辑
// 								if(is_publish==1){
// 									$("#vpromotionType").val(promotionType);
// 									var fpromotionTypeName = $("#vpromotionType").find("option:selected").text();
// 									$("#vpromotionTypeName").val(fpromotionTypeName);
									
// 									var uee2 = UE.getEditor('vcontent', {
// 		     				            autoHeight: false,
// 		     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
// 		     				            initialFrameHeight:445  //初始化编辑器高度,默认320
// 		     				        }) ;
// 									uee2.ready(function() { 
// 					        			uee2.setContent(content);  
// 					        		});
// 									$("#vbegin_date").val(begin_date);
// 									$("#vend_date").val(end_date);
// 									$("#vmin_age").val(min_age);
// 									$("#vmax_age").val(max_age);
// 									$("#vis_publish").val(is_publish);
// 									$("#vstatus").val(status);
									
// 									if(free_reservation==1){
// 										$("#vfree_reservation").attr("checked","checked");
// 									}
// 									$("#vshares_times").val(shares_times);
// 									if(pay_need==1){
// 										$("#vpay_need").attr("checked","checked");
// 									}
// 									$("#vprice").val(price);
// 									if(old_member_aim==1){
// 										$("#vold_member_aim").attr("checked","checked");
// 									}
// 									$("#vparticipation").val(participation);
// 									if(community_addible==1){
// 										$("#vcommunity_addible").attr("checked","checked");
// 									}
// 									$("#vservice_staff").val(service_staff);
									
// // 									if(return_coupon==1){
// // 										$("#vreturn_coupon").attr("checked","checked");
// // 									}
// 									if(has_gift==1){
// 										$("#vhas_gift").attr("checked","checked");
// 									}
// 									if(can_use_coupon==1){
// 										$("#vcan_use_coupon").attr("checked","checked");
// 									}
// 									if(is_boutique==1){
// 										$("#vis_boutique").attr("checked","checked");
// 									}
// 									if(is_weekend==1){
// 										$("#vis_weekend").attr("checked","checked");
// 									}
// 									if(need_participant==1){
// 										$("#vneed_participant").attr("checked","checked");
// 									}
// 									if(is_agent==1){
// 										$("#vis_agent").attr("checked","checked");
// 									}
// 									if(is_remind==1){
// 										$("#vis_remind").attr("checked","checked");
// 									}
// 									$("#vremind_phone").val(remind_phone);
// 									$("#vpercentage").val(percentage);
// 									$("#vpersonalpercentage").val(personalpercentage);
// // 									$("#vcoupon").val(coupon);
// 									$("#vgift_name").val(gift_name);
// // 									$("#vleast_cost").val(least_cost);
// // 									$("#vreduce_cost").val(reduce_cost);
// // 									$("#vfixed_term").val(fixed_term);
// 									$("#veveryday_limit").val(everyday_limit);
// 									$("#vcontact_address").val(contact_address);
// 									$("#vcoordinate").val(coordinate);

// 									var image=obj.img;
// 									console.log(image);
// 									if(""!=image){
// 										$("#image").val(image);
// 									}
									
// 									$("#vvid").val(id);
// 									$("#vname").val(name);
// 									$("#vbrief").val(brief);
									
// 									$("#vid").css("display","block"); 
// 									$("#aid").css("display","none"); 
// 									$("#fid").css("display","none"); 
// 								}else{
									
									
// 									$("#fpromotionType").val(promotionType);
// 									var fpromotionTypeName = $("#fpromotionType").find("option:selected").text();
// 									$("#fpromotionTypeName").val(fpromotionTypeName);
									
// 									var uee1 = UE.getEditor('fcontent', {
// 		     				            autoHeight: false,
// 		     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
// 		     				            initialFrameHeight:445  //初始化编辑器高度,默认320
// 		     				        }) ;
// 									uee1.ready(function() { 
// 					        			uee1.setContent(content);  
// 					        		});
// 									$("#fbegin_date").val(begin_date);
// 									$("#fend_date").val(end_date);
// 									$("#fmin_age").val(min_age);
// 									$("#fmax_age").val(max_age);
// 									$("#fis_publish").val(is_publish);
// 									$("#fstatus").val(status);
									
// 									if(free_reservation==1){
// 										$("#ffree_reservation").attr("checked","checked");
// 									}
// 									$("#fshares_times").val(shares_times);
// 									if(pay_need==1){
// 										$("#fpay_need").attr("checked","checked");
// 									}
// 									$("#fprice").val(price);
// 									if(old_member_aim==1){
// 										$("#fold_member_aim").attr("checked","checked");
// 									}
// 									$("#fparticipation").val(participation);
// 									if(community_addible==1){
// 										$("#fcommunity_addible").attr("checked","checked");
// 									}
// 									$("#fservice_staff").val(service_staff);
									
// // 									if(return_coupon==1){
// // 										$("#freturn_coupon").attr("checked","checked");
// // 									}
// 									if(has_gift==1){
// 										$("#fhas_gift").attr("checked","checked");
// 									}
// 									if(can_use_coupon==1){
// 										$("#fcan_use_coupon").attr("checked","checked");
// 									}
// 									if(is_boutique==1){
// 										$("#fis_boutique").attr("checked","checked");
// 									}
// 									if(is_weekend==1){
// 										$("#fis_weekend").attr("checked","checked");
// 									}
// 									if(need_participant==1){
// 										$("#fneed_participant").attr("checked","checked");
// 									}
// 									if(is_agent==1){
// 										$("#fis_agent").attr("checked","checked");
// 									}
// 									if(is_remind==1){
// 										$("#fis_remind").attr("checked","checked");
// 									}
// 									$("#fremind_phone").val(remind_phone);
// 									$("#fpercentage").val(percentage);
// 									$("#fpersonalpercentage").val(personalpercentage);
// // 									$("#fcoupon").val(coupon);
// 									$("#fgift_name").val(gift_name);
// // 									$("#fleast_cost").val(least_cost);
// // 									$("#freduce_cost").val(reduce_cost);
// // 									$("#ffixed_term").val(fixed_term);
// 									$("#feveryday_limit").val(everyday_limit);
// 									$("#fcontact_address").val(contact_address);
// 									$("#fcoordinate").val(coordinate);
	
// 									var image=obj.img;
// 									console.log(image);
// 									if(""!=image){
// 										$("#image").val(image);
// 										$("#fimage").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片<img id="imagel" width="200px" height="100px" src="${contextPath}'+image + '"/><br/>' ); 
// 									}else{
// 										$("#fimage").html("上传图片	&nbsp;	<input type='file' name='file' id='ffile'/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>" );
// 									}
									
// 									$("#hid").css("display","block"); 
// 									$("#aid").css("display","none"); 
									
// 									$("#fimage").click(function(){
// 											$(this).html(""); 
// 											var htmlspan = document.createElement("span");
// 											var htmlinput = document.createElement("input");
// 											htmlinput.setAttribute("type","file");
// 											htmlinput.setAttribute("name","file");
// 											var htmlbr = document.createElement("br");
// 											htmlspan.innerHTML = "上传图片：";
// 											htmlspan.appendChild(htmlinput);
// 											htmlspan.appendChild(htmlbr);
// 											$(this).append(htmlspan);
// 											$("#fimage").unbind();			
// 									});
									
// 					$("#ffid").val(id);
// 					$("#fname").val(name);
// 					$("#fbrief").val(brief);
					
        				        
        		
// 					poll = setInterval("$('#ffContent').val(UE.getEditor('fcontent').getContent());autosave('f')", 60000);
// 								}
// 				       		}   
// 				       	} 
// 				    });
					
				});
				$("#addaaa").click(function(){
					
					window.location.href="${contextPath}/sys/sysuser/home#page/salespromotionedit?spid=0";
// 					$("#aname").val("");
// 					$("#abrief").val("");
// 					$("#afree_reservation").attr("checked",false);
// 					$("#ashares_times").val("");
// 					$("#apay_need").attr("checked",false);
// 					$("#aprice").val(""); 
// 					$("#abegin_date").val(""); 
					
// 					$("#aend_date").val("");
// 					$("#amin_age").val("");
// 					$("#amax_age").val(""); 
// 					$("#aold_member_aim").attr("checked",false);
					
// 					$("#aparticipation").val("");
// 					$("#aservice_staff").val("");
// 					$("#acommunity_addible").attr("checked",false);
					
// // 					$("#areturn_coupon").attr("checked",false);
// 					$("#ahas_gift").attr("checked",false);
// // 					$("#acoupon").val("");
// 					$("#agift_name").val("");
// 					$("#acan_use_coupon").attr("checked",false);
// 					$("#ais_boutique").attr("checked",false);
// 					$("#ais_weekend").attr("checked",false);
// 					$("#aneed_participant").attr("checked",false);
// 					$("#ais_agent").attr("checked",false);
// 					$("#ais_remind").attr("checked",false);
// // 					$("#ais_publish").attr("checked",false);
// // 					$("#aleast_cost").val("");
// // 					$("#areduce_cost").val("");
// // 					$("#afixed_term").val("");
// 					$("#apercentage").val("");
// 					$("#apersonalpercentage").val("");
// 					$("#aeveryday_limit").val("");
// 					$("#aremind_phone").val("");
					
// 					$("#aid").css("display","block"); 
// 					$("#hid").css("display","none"); 
// 					//UE.delEditor('acontent');
// 					var uee1 = UE.getEditor('acontent', {
//      				            autoHeight: false,
//      				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
//      				            initialFrameHeight:445  //初始化编辑器高度,默认320
//      				        }) ;
        				        
// 	        		uee1.ready(function() { 
// 	        			//alert(brief);
// 	        			uee1.setContent("");  
// 	        		});
// 	        		poll = setInterval("$('#aaContent').val(UE.getEditor('acontent').getContent());autosave('a');", 60000);
				});
				

//===========================================================================================	
		
				$("#fimage").click(function(){
					$(this).html(""); 
					var htmlspan = document.createElement("span");
					var htmlinput = document.createElement("input");
					htmlinput.setAttribute("type","file");
					htmlinput.setAttribute("name","file");
					var htmlbr = document.createElement("br");
					htmlspan.innerHTML = "上传图片：";
					htmlspan.appendChild(htmlinput);
					htmlspan.appendChild(htmlbr);
					$(this).append(htmlspan);
					$("#fimage").unbind();		
				});
				//=================提交  
				$("input[name='up']").click(function(){  
		 			 $("#fimage").unbind();
		 			var regz = new RegExp("^[0-9]*[1-9][0-9]*$");
					var regk=new RegExp("^[0-9]+(.[0-9]{1,2})?$");   
    			    if($("#fprice").val()!=""&&$("#fprice").val()!="0"&&!regk.test($("#fprice").val())){  
    			        alert("支付金额必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#fmin_age").val()!=""&&$("#fmin_age").val()!="0"&&!regz.test($("#fmin_age").val())){  
    			        alert("参与年龄必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fmax_age").val()!=""&&$("#fmax_age").val()!="0"&&!regz.test($("#fmax_age").val())){  
    			        alert("参与年龄必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fparticipation").val()!=""&&$("#fparticipation").val()!="0"&&!regz.test($("#fparticipation").val())){  
    			        alert("参与人数必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#feveryday_limit").val()!=""&&$("#feveryday_limit").val()!="0"&&!regz.test($("#feveryday_limit").val())){  
    			        alert("每日限额人数必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fshares_times").val()!=""&&$("#fshares_times").val()!="0"&&!regz.test($("#fshares_times").val())){  
    			        alert("分享次数必须是整数格式!");  
    			        return ;
    			    }
//     			    if($("#fcoupon").val()!=""&&!regk.test($("#fcoupon").val())){  
//     			        alert("返券金额必须是两位小数数字格式!");  
//     			        return ;
//     			    }
//     			    if($("#ffixed_term").val()!=""&&$("#ffixed_term").val()!="0"&&!regz.test($("#ffixed_term").val())){  
//     			        alert("优惠券有效期必须是整数格式!");  
//     			        return ;
//     			    }
//     			    if($("#fleast_cost").val()!=""&&!regk.test($("#fleast_cost").val())){  
//     			        alert("起用金额必须是两位小数数字格式!");  
//     			        return ;
//     			    }
//     			    if($("#freduce_cost").val()!=""&&!regk.test($("#freduce_cost").val())){  
//     			        alert("抵用金额必须是两位小数数字格式!");  
//     			        return ;
//     			    }
    			    if($("#fpercentage").val()!=""&&$("#fpercentage").val()!="0"&&!regk.test($("#fpercentage").val())){  
    			        alert("提成比必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#fpersonalpercentage").val()!=""&&$("#fpersonalpercentage").val()!="0"&&!regk.test($("#fpersonalpercentage").val())){  
    			        alert("推荐人好处费必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#fremind_phone").val()!=""&&!/^(13[0-9]|15[012356789]|17[5678]|18[0-9]|14[57])[0-9]{8}$/.test($("#fremind_phone").val()))    
    			      {    
    			    	alert("提醒人手机号格式不正确!");  
    			          return ;    
    			      }    
		 			 var content= UE.getEditor('fcontent').getContent();
		 			 $("#ffContent").val(content);
		 			if($("#fbegin_date").val()==null||$("#fbegin_date").val()==""||$("#fend_date").val()==null||$("#fend_date").val()==""){
// 		 				if($('#promotion_base_info_nav_f').hasClass("active")){
// 		 					$('#promotion_base_set_nav_f').addClass("active");
// 		           			$('#promotion_base_info_nav_f').removeClass("active");
// // 		           			$('#promotion_base_set_nav_f').show();
// // 		           			$('#promotion_base_info_nav_f').hide();
		           			
// 		           			$('#promotion_set_info_set_f').addClass("active");
// 		           			$('#promotion_base_info_set_f').removeClass("active");
// 		           			$('#promotion_set_info_set_f').show();
// 		           			$('#promotion_base_info_set_f').hide();
// 			 				$("#fbegin_date").focus();
// 		 				}
		 				 alert("课程有效期必须填写");
		 				 return;
		 			 }
		 			
		 			if($("#fbegin_date").val()!=""&&!$("#fbegin_date").val().match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)&&!$("#fbegin_date").val().match(/^((?:19|20)\d\d)\/(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/)) { 
		 				alert("日期格式不正确，正确格式例如'2009-03-09'"); 
		 				return;
		 			}
		 			if($("#fend_date").val()!=""&&!$("#fend_date").val().match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)&&!$("#fend_date").val().match(/^((?:19|20)\d\d)\/(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/)) { 
		 				alert("日期格式不正确，正确格式例如'2009-03-09'"); 
		 				return;
		 			}
					 $("#ff_form").submit();
					 window.clearInterval(poll);
				});
				$("input[name='aup']").click(function(){  
					 
					var acontent= UE.getEditor('acontent').getContent();
					if($("#aname").val()==""||$("#aname").val()==null){
						alert("课程名称必须填写！");
						return;
					}
					var regz = new RegExp("^[0-9]*[1-9][0-9]*$");
					var regk=new RegExp("^[0-9]+(.[0-9]{1,2})?$");   
    			    if($("#aprice").val()!=""&&$("#aprice").val()!="0"&&!regk.test($("#aprice").val())){  
    			        alert("支付金额必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#amin_age").val()!=""&&$("#amin_age").val()!="0"&&!regz.test($("#amin_age").val())){  
    			        alert("参与年龄必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#amax_age").val()!=""&&$("#amax_age").val()!="0"&&!regz.test($("#amax_age").val())){  
    			        alert("参与年龄必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#aparticipation").val()!=""&&$("#aparticipation").val()!="0"&&!regz.test($("#aparticipation").val())){  
    			        alert("参与人数必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#aeveryday_limit").val()!=""&&$("#aeveryday_limit").val()!="0"&&!regz.test($("#aeveryday_limit").val())){  
    			        alert("每日限额人数必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#ashares_times").val()!=""&&$("#ashares_times").val()!="0"&&!regz.test($("#ashares_times").val())){  
    			        alert("分享次数必须是整数格式!");  
    			        return ;
    			    }
//     			    if($("#acoupon").val()!=""&&!regk.test($("#acoupon").val())){  
//     			        alert("返券金额必须是两位小数数字格式!");  
//     			        return ;
//     			    }
//     			    if($("#afixed_term").val()!=""&&$("#afixed_term").val()!="0"&&!regz.test($("#afixed_term").val())){  
//     			        alert("优惠券有效期必须是整数格式!");  
//     			        return ;
//     			    }
//     			    if($("#aleast_cost").val()!=""&&!regk.test($("#aleast_cost").val())){  
//     			        alert("起用金额必须是两位小数数字格式!");  
//     			        return ;
//     			    }
//     			    if($("#areduce_cost").val()!=""&&!regk.test($("#areduce_cost").val())){  
//     			        alert("抵用金额必须是两位小数数字格式!");  
//     			        return ;
//     			    }
    			    if($("#apercentage").val()!=""&&$("#apercentage").val()!="0"&&!regk.test($("#apercentage").val())){  
    			        alert("提成比必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#apersonalpercentage").val()!=""&&$("#apersonalpercentage").val()!="0"&&!regk.test($("#apersonalpercentage").val())){  
    			        alert("推荐人好处费必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#aremind_phone").val()!=""&&!/^(13[0-9]|15[012356789]|17[5678]|18[0-9]|14[57])[0-9]{8}$/.test($("#aremind_phone").val()))    
  			      {    
  			    	alert("提醒人手机号格式不正确!");  
  			          return ;    
  			      }    
		 			 $("#aaContent").val(acontent);
		 			 if($("#abegin_date").val()==null||$("#abegin_date").val()==""||$("#aend_date").val()==null||$("#aend_date").val()==""){
		 				 alert("课程有效期必须填写");
		 				 return;
		 			 }
		 			if($("#abegin_date").val()!=""&&!$("#abegin_date").val().match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)&&!$("#abegin_date").val().match(/^((?:19|20)\d\d)\/(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/)) { 
		 				alert("日期格式不正确，正确格式例如'2009-03-09'"); 
		 				return;
		 			}
		 			if($("#aend_date").val()!=""&&!$("#aend_date").val().match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)&&!$("#aend_date").val().match(/^((?:19|20)\d\d)\/(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/)) { 
		 				alert("日期格式不正确，正确格式例如'2009-03-09'"); 
		 				return;
		 			}
					 $("#aa_form").submit();
					 window.clearInterval(poll);
				});
				//======================关闭
				$("input[name='vclose']").click(function(){
					$("#vimage").unbind();
					$("#vid").css("display","none");
				});
				$("input[name='fclose']").click(function(){
					$("#fimage").unbind();
					$("#hid").css("display","none");
					 window.clearInterval(poll);
				});
				$("input[name='aclose']").click(function(){
					//$("#abegin_date").remove();
					$("#aid").css("display","none");
					 window.clearInterval(poll);
				});
//===========================================jieshu ================================================
        		
        		
        		
        		$(window).triggerHandler('resize.jqGrid');// trigger window resize to make the grid get the correct size
        		
        		// enable search/filter toolbar
        		// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
        		// jQuery(grid_selector).filterToolbar({});
        		// switch element when editing inline
        		function aceSwitch(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[type=checkbox]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			}, 0);
        		}
        		
        		// enable datepicker
        		function pickDate(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[type=text]').datepicker({
        					format : 'yyyy-mm-dd',
        					autoclose : true,
        				    language: 'zh-CN'
        				});
        			}, 0);
        		}
        		
        		// navButtons
        		jQuery(grid_selector).jqGrid('navGrid', pager_selector, { // navbar options
        			edit : false,
					editicon : '',
					add : false,
					addicon : '', 
					del : true,
					delicon : 'ace-icon fa fa-trash-o red',
					search : true,
					searchicon : 'ace-icon fa fa-search orange',
					refresh : true,
					refreshicon : 'ace-icon fa fa-refresh blue',
					view : true,
					viewicon : 'ace-icon fa fa-search-plus grey'
        		}, {
        			// edit record form
        			// closeAfterEdit: true,
        			// width: 700,
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        				style_edit_form(form);
        			},
    				errorTextFormat: function (response) {
    					var result = eval('('+response.responseText+')');
    				    return result.message;
    				}
        		}, {
        			// new record form
        			// width: 700,
        			closeAfterAdd : true,
        			recreateForm : true,
        			viewPagerButtons : false,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        				style_edit_form(form);
        			},
    				errorTextFormat: function (response) {
    					var result = eval('('+response.responseText+')');
    				    return result.message;
    				}
        		}, {
        			// delete record form
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				if (form.data('styled'))
        					return false;
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
        				style_delete_form(form);
        				form.data('styled', true);
        			},
        			onClick : function(e) {
        				// alert(1);
        			}
        		}, {
        			// search form
        			recreateForm : true,
        			afterShowSearch : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
        				style_search_form(form);
        			},
        			afterRedraw : function() {
        				style_search_filters($(this));
        			},
        			multipleSearch : true 
	        		/**
	        		 * multipleGroup:true, showQuery: true
	        		 */
        		}, {
        			// view record form
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
        			}
        		})
        		
        		// add custom button to export the data to excel
				jQuery(grid_selector).jqGrid('navButtonAdd', pager_selector,{
				   caption : "",
			       title : "导出Excel",
			       buttonicon : "ace-icon fa fa-file-excel-o green", 
			       onClickButton : function () { 
			    	   var keys = [], ii = 0, rows = "";
			    	   var ids = $(grid_selector).getDataIDs(); // Get All IDs
			    	   var label = $(grid_selector).jqGrid('getGridParam','colNames');
   			    	   for(var i = 1 ; i < label.length; i++){
			    	   	   rows = rows + label[i] + "\t"; // output each Column as tab delimited
			    	   }
			    	   var row = $(grid_selector).getRowData(ids[0]); // Get First row to get the labels
			    	   
   			    	   for (var k in row) {
			    	   	   keys[ii++] = k; // capture col names
			    	   }
			    	   
			    	   rows = rows + "\n"; // Output header with end of line
			    	   for (i = 0; i < ids.length; i++) {
			    	   	   row = $(grid_selector).getRowData(ids[i]); // get each row
			    	   	   for (j = 0; j < keys.length; j++)
			    	   		   rows = rows + row[keys[j]] + "\t"; // output each Row as tab delimited
			    	   	   rows = rows + "\n"; // output each row with end of line
			    	   }
			    	   rows = rows + "\n"; // end of line at the end
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/sysuser/operateSysUser?oper=excel' method='post'>";
			    	   form = form + "<input type='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open('', '');
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		
        		function style_edit_form(form) {
        			// enable datepicker on "birthday" field and switches for "stock" field
        			form.find('input[name=begin_date]').datepicker({
        				format : 'yyyy-mm-dd',
        				autoclose : true,
        			    language: 'zh-CN'
        			})

        			form.find('input[name=statusCn]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			// don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
        			// .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

        			// update buttons classes
        			var buttons = form.next().find('.EditButton .fm-button');
        			buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();// ui-icon, s-icon
        			buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
        			buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

        			buttons = form.next().find('.navButton a');
        			buttons.find('.ui-icon').hide();
        			buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
        			buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
        		}

        		function style_delete_form(form) {
        			var buttons = form.next().find('.EditButton .fm-button');
        			buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();// ui-icon, s-icon
        			buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
        			buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>');
        		}

        		function style_search_filters(form) {
        			form.find('.delete-rule').val('X');
        			form.find('.add-rule').addClass('btn btn-xs btn-primary');
        			form.find('.add-group').addClass('btn btn-xs btn-success');
        			form.find('.delete-group').addClass('btn btn-xs btn-danger');
        		}
        		function style_search_form(form) {
        			var dialog = form.closest('.ui-jqdialog');
        			var buttons = dialog.find('.EditTable')
        			buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
        			buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
        			buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
        		}

        		function beforeDeleteCallback(e) {
        			var form = $(e[0]);
        			if (form.data('styled'))
        				return false;
        			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        			style_delete_form(form);
        			form.data('styled', true);
        		}

        		function beforeEditCallback(e) {
        			var form = $(e[0]);
        			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        			style_edit_form(form);
        		}

        		// it causes some flicker when reloading or navigating grid
        		// it may be possible to have some custom formatter to do this as the grid is being created to prevent this
        		// or go back to default browser checkbox styles for the grid
        		function styleCheckbox(table) {
        			/**
        			 * $(table).find('input:checkbox').addClass('ace') .wrap('<label />') .after('<span class="lbl align-top" />') $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
        			 * .find('input.cbox[type=checkbox]').addClass('ace') .wrap('<label />').after('<span class="lbl align-top" />');
        			 */
        		}

        		// unlike navButtons icons, action icons in rows seem to be hard-coded
        		// you can change them like this in here if you want
        		function updateActionIcons(table) {
        			/**
        			 * var replacement = { 'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue', 'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red', 'ui-icon-disk' : 'ace-icon fa fa-check green', 'ui-icon-cancel' :
        			 * 'ace-icon fa fa-times red' }; $(table).find('.ui-pg-div span.ui-icon').each(function(){ var icon = $(this); var $class = $.trim(icon.attr('class').replace('ui-icon', '')); if($class in replacement)
        			 * icon.attr('class', 'ui-icon '+replacement[$class]); })
        			 */
        		}

        		// replace icons with FontAwesome icons like above
        		function updatePagerIcons(table) {
        			var replacement = {
        				'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
        				'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
        				'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
        				'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
        			};
        			$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function() {
        				var icon = $(this);
        				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        				if ($class in replacement)
        					icon.attr('class', 'ui-icon ' + replacement[$class]);
        			})
        		}

        		function enableTooltips(table) {
        			$('.navtable .ui-pg-button').tooltip({
        				container : 'body'
        			});
        			$(table).find('.ui-pg-div').tooltip({
        				container : 'body'
        			});
        		}

        		// var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

        		$(document).one('ajaxloadstart.page', function(e) {
        			$(grid_selector).jqGrid('GridUnload');
        			$('.ui-jqdialog').remove();
        		});
        		
        	});
        });
        Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
        
        function pickcoord(code){
        	window.open("${contextPath}/jsp/coordchange.html?code="+code);
        	
        } 
        
        function viewreason(reason){
        	$("#modal-table2").modal("toggle");
        	$("#rejectreason").html(reason);
        }
        
</script>
