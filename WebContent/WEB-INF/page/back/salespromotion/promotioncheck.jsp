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
	<ul class="nav nav-tabs">
	  <li role="presentation" class="active" id="need_check_"><a href="javascript:void(0);">未审核</a></li>
	  <li role="presentation" id="already_approved_"><a href="javascript:void(0);">审核通过</a></li>
	  <li role="presentation" id="already_reject_"><a href="javascript:void(0);">审核不通过</a></li>
	</ul>
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
<div style="position:absolute; ;top:745px;left:162px; padding-right: 100px;">
			<span  id="editaaa" style="cursor: pointer;" > 
				<a title="查看所选记录" data-rel="tooltip"><i class=" ace-icon fa fa-search-plus grey"/></a>
			</span> &nbsp; 
		</div>
		<script reservationdate="text/javascript">
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
		
		<div style="display: none;position:absolute ; z-index:999; top:0;left:0;background-color: #fff;border: 1px solid #aaa;" id="hid" >
		   <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
			 <input type="button"  style="float:left;"name="fclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 编辑所选记录 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div><div style="padding: 10px;">
		  	<form id="ff_form" method="post" enctype="multipart/form-data" action="">			
				<input type="hidden" name="id" id="ffid"/>
				<input type="hidden" id="ffContent" name="addContent"/>
				<ul class="nav nav-tabs">
				  <li role="presentation" class="active" id="promotion_base_info_nav_f"><a href="javascript:void(0);">基本信息</a></li>
				  <li role="presentation" id="promotion_base_set_nav_f"><a href="javascript:void(0);">规则设置</a></li>
				</ul>
				<br/>
				<!-- fieldsets -->
				<fieldset id="promotion_base_info_set_f" >
					活动名称	&nbsp;<input type="text" name="name" id="fname" maxlength="60" disabled="disabled"/><br><br>
					有效期	&nbsp;<input type="text" name="begin_date" id="fbegin_date" disabled="disabled"/>
					至	&nbsp;<input type="text" name="end_date" id="fend_date" disabled="disabled"/><br><br>
					活动类型	&nbsp;<select name="promotionType" id="fpromotionType" disabled="disabled"> </select><input type="hidden" name="promotionTypeName" id="fpromotionTypeName" /><br><br>
					简介		&nbsp;<input type="text" name="brief" id="fbrief" maxlength="255" disabled="disabled"/><br><br>
					活动地点&nbsp;<input type="text" name="contact_address" id="fcontact_address"  disabled="disabled"/>地点坐标<input type="text" name="coordinate" id="fcoordinate" disabled="disabled"/><br><br>
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
					
					参与年龄	&nbsp;<input type="text" name="min_age" id="fmin_age" disabled="disabled"/>
					至	&nbsp;<input type="text" name="max_age" id="fmax_age" disabled="disabled"/><br><br>
					
					参与人数	&nbsp;<input type="text" name="participation" id="fparticipation" maxlength="10" disabled="disabled"/>
					每日限制人数	&nbsp;<input type="text" name="everyday_limit" id="feveryday_limit" maxlength="10" disabled="disabled"/>
					服务人员	&nbsp;<input type="text" name="service_staff" id="fservice_staff" maxlength="10" disabled="disabled"/><br><br>
					
					是否精品活动	&nbsp;<input type="checkbox" name="is_boutique" value="1" id="fis_boutique" disabled="disabled" role="checkbox" class="FormElement ace ace-switch ace-switch-5" /><span class="lbl"></span>&nbsp;&nbsp;周末活动	&nbsp;<input type="checkbox" disabled="disabled"  name="is_weekend" value="1" id="fis_weekend" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					<br><br>
					
					是否针对老用户	&nbsp;<input type="checkbox" name="old_member_aim" id="fold_member_aim" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					是否自动加群	&nbsp;<input type="checkbox" name="community_addible" id="fcommunity_addible" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					是否要选择出行人	&nbsp;<input type="checkbox" name="need_participant" value="1"  id="fneed_participant" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span><br><br>
					
					是否免费	&nbsp;<input type="checkbox" name="free_reservation" id="ffree_reservation" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					分享次数	&nbsp;<input type="text" name="shares_times" id="fshares_times"  disabled="disabled" maxlength="10"/><br><br>
					
					是否需支付	&nbsp;<input type="checkbox" name="pay_need" id="fpay_need" role="checkbox" disabled="disabled"  class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					支付金额	&nbsp;<input type="text" name="price" id="fprice" disabled="disabled"  maxlength="10"/><br><br>
					
					
					是否返券	&nbsp;<input type="checkbox" name="return_coupon" disabled="disabled"  id="freturn_coupon" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					返券金额	&nbsp;<input type="text" name="coupon" disabled="disabled"  id="fcoupon" /><br><br>
					
					是否赠送礼品	&nbsp;<input type="checkbox" name="has_gift" id="fhas_gift" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					礼品名称	&nbsp;<input type="text" name="gift_name" id="fgift_name" disabled="disabled" /><br><br>
					
					是否可用优惠券<input type="checkbox" name="can_use_coupon" id="fcan_use_coupon" disabled="disabled"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					优惠券有效期<input type="text" name="fixed_term" id="ffixed_term"  disabled="disabled"  maxlength="5"/>
					优惠券起用金额<input type="text" name="least_cost" id="fleast_cost"  disabled="disabled"  maxlength="5"/>
					优惠券抵用金额<input type="text" name="reduce_cost" id="freduce_cost"  disabled="disabled"  maxlength="5"/><br><br>
  				
  					是否可代理	&nbsp;<input type="checkbox" name="is_agent" id="fis_agent" disabled="disabled"  value="1"  role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					代理提成比（%）	<input type="text" name="percentage" id="fpercentage" disabled="disabled"  maxlength="10"/><br><br>
					
					<input type="hidden" name="is_publish" id="fis_publish" />
					<input type="hidden" name="status" id="fstatus" />
  				</fieldset>
				
				<div style="clear: both;"></div>
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="fclose" value="取消">
			 </form>
			 </div>
		</div>
		
		<script reservationdate="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
		<script type="text/javascript"src="${contextPath}/static/edit/ueditor.config.js"></script> 
		<script type="text/javascript"src="${contextPath}/static/edit/ueditor.all.js"></script> 
		
 
 <script reservationdate="text/javascript"> 
    $("#aid").draggable();
	$("#hid").draggable();

    </script>
<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
			<!-- 门店选择对话框 -->
<div id="modal-table" class="modal fade" tabindex="-1" data-backdrop="static">
	<div class="modal-dialog">
		<form id="informationForm">
			<div class="modal-content">
				<div class="modal-header no-padding">
					<div class="table-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							<span class="white">&times;</span>
						</button>
						活动审核
					</div>
				</div>
				<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
					<div id="modal-tip" class="red clearfix"></div>
					<input id="promotionid" type="hidden" />
					<div class="widget-box widget-color-blue2">
						<div class="widget-body" id="refund_op_content_">
							<div class="widget-main padding-8" id="store_checkbox">
							<input type="hidden"  id="id" />
							通过	&nbsp;<input type="radio" name="status" value="1" checked="checked" id="status1" />
							不通过	&nbsp;<input type="radio" name="status" value="2" id="status2" /><br>
							审核意见	&nbsp;<textarea rows="5" cols="51" name="approval_opinion" id="approval_opinion"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer no-margin-top">
					<div class="text-center" id="refund_op_btn_">
						<button id="submitButton" type="submit" class="btn btn-app btn-success btn-xs" data-dismiss="modal" >
							<i class="ace-icon fa fa-floppy-o bigger-160"></i>
							确定
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
	
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script reservationdate="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
		                "${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js",
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                 null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		
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

        		var grid = jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/TzzSalesPromotion/getSalesPromotionListByStatus?status=0",
        			datatype : "json",
        			mtype:"POST",
        			height : 650,
        			colNames : [ 'ID','套餐管理', '活动类别','活动类别', '活动名称', '导语', '金额','加盟商','创建时间','活动状态','审核意见','操作'],
        			colModel : [ {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sorttype : "long",
        				search : false
        			}, {
        				name : 'id',
        				index : 'id',
        				label : '套餐管理',
        				width : 100,
        				formatter: function (value, grid, rows, state) 
        					{
        					 return "<a href=\"${contextPath}/sys/sysuser/home#page/promotioncombo?sid=" + value + "\" style=\"color:#9ab\"> 套餐管理 </a>";
        					  
        					 } ,
        				sorttype : "int",
        				search : false
        			}, {
        				name : 'promotionType',
        				index : 'promotionType',
        				label : '活动类别',
        				width : 80,
        				hidden : true,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'promotionTypeName',
        				index : 'promotionTypeName',
        				label : '活动类别',
        				width : 80,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'name',
        				index : 'name',
        				label : '活动名称',
        				width : 180,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'brief',
        				index : 'brief',
        				label : '导语',
        				width : 180,
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
        				name : 'allianceBusiness.name',
        				index : 'allianceBusiness.name',
        				label : '加盟商',
        				width : 110  ,
        				editable : true,
        				search : false	
        				
        			}, {
        				name : 'creattime',
        				index : 'creattime',
        				label : '创建时间',
        				width : 110 ,
        				editable : true,
        				search : false 	
        			}, {
        				name : 'status',
        				index : 'status',
        				label : '活动状态',
        				width : 80 ,
        				editable : true,
        				formatter : statusFormatter,
        				search : false 	
        			}, {
        				name : 'approval_opinion',
        				index : 'approval_opinion',
        				label : '审核意见',
        				width : 280 ,
        				editable : true,
        				search : false 	
        			}, {
        				name : 'id',
        				index : 'id',
        				label : '审核',
        				width : 80  ,
        				editable : false,
        				search : false,
        				sortable : false,
        				formatter : operatorFormatter
        			}
					],
        			//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
        			sortname : "id",
        			sortorder : "desc",
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
        			}
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
        		
       		console.log(grid);
       		
       		function statusFormatter(cellvalue, options, rowdata) {  
				if(cellvalue==1)
					return "审核通过";
				else if(cellvalue==2)
					return "审核不通过";
				else 
					return "未审核";
			}
       		
       		function operatorFormatter(cellvalue, options, cell) {
       			var template = "";
       			if(cell.status==0)
       			template = "<button data-toggle='modal' onclick='javascript:$(\"#modal-table\").modal(\"toggle\");$(\"#promotionid\").val(\"" + cell.id +"\");' class='btn btn-xs btn-warning'>审核</button>";
       			else if(cell.status==2)
       				template = "<button data-toggle='modal' onclick='javascript:$(\"#modal-table\").modal(\"toggle\");$(\"#promotionid\").val(\"" + cell.id +"\");' class='btn btn-xs btn-warning'>重新审核</button>";
       			return template;
       		}
       		
       		$('#need_check_').on('click', function() { 
       			$('#need_check_').addClass("active");
       			$('#already_approved_').removeClass("active");
       			$('#already_reject_').removeClass("active");
       			jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotionListByStatus?status=0"}).trigger("reloadGrid");
       			
			});
       		
       		$('#already_approved_').on('click', function() {
       			$('#need_check_').removeClass("active");
       			$('#already_reject_').removeClass("active");
       			$('#already_approved_').addClass("active");
       			jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotionListByStatus?status=1"}).trigger("reloadGrid");
			});
       		
       		$('#already_reject_').on('click', function() {
       			$('#need_check_').removeClass("active");
       			$('#already_approved_').removeClass("active");
       			$('#already_reject_').addClass("active");
       			jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotionListByStatus?status=2"}).trigger("reloadGrid");
			});
       		
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
       		
       		
       		$('#submitButton').on('click', function() { 
       			var status = $("input[name='status']:checked").val();
       			var approval_opinion = $("#approval_opinion").val();
				$.ajax({
					url : "${contextPath}/sys/TzzSalesPromotion/changePromotionStatus",
					data : {
						"promotionid" : $("#promotionid").val(),
						"approval_opinion" : approval_opinion,
						"status" : status
					},
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if(data.err=="0"){
							if(!$('#need_check_').hasClass("active")){
								$('#need_check_').addClass("active");
				       			$('#already_approved_').removeClass("active");
				       			$('#already_reject_').removeClass("active");
							}
							jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotionListByStatus?status=0"}).trigger("reloadGrid");
						}else{
							alert("审核异常");
						}
					}
				});
			});
       		
       		$("#editaaa").click(function(){
				 
				var id=$('#grid-table').jqGrid('getGridParam','selrow');
				if(id==null) {
					alert("请选择记录！");
					return;
				}
				var rowData = $('#grid-table').jqGrid('getRowData',id);
				
				
				//name 简介brief 内容介绍content 成本价costPrice 金额amount 类型categoryName 
    			//图片image 型号xinghao 规格guige 材质caizhi 品牌pinpai 产地chandi 单位danwei 点击浏览次数clickNum 销量sales 热销rexiaoName
				
				//var id=rowData.id;
				var name=rowData.name;
				var brief=rowData.brief;
				
				$.ajax({//classifycn
			        type:"post", 
			        url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotion",
			        data:{"id":id},
			   		dataType:"json", 
			       	success:function(data){ 
			       		if(data.success){
			       			var obj = data.obj;
			       			console.log(obj)
			       			var free_reservation=obj.free_reservation;
							var shares_times=obj.shares_times;
							var pay_need=obj.pay_need;
							var price=obj.price;
							var old_member_aim=obj.old_member_aim;
							var participation=obj.participation;
							var service_staff=obj.service_staff;
							var community_addible=obj.community_addible;
							var begin_date = new Date(obj.begin_date.time).Format("yyyy-MM-dd");
							var end_date = new Date(obj.end_date.time).Format("yyyy-MM-dd");
							var min_age = obj.min_age;
							var max_age = obj.max_age;
							var content = obj.content;
							var return_coupon = obj.return_coupon;
							var coupon = obj.coupon;
							var has_gift = obj.has_gift;
							var gift_name = obj.gift_name;
							var can_use_coupon = obj.can_use_coupon;
							var least_cost = obj.least_cost;
							var reduce_cost = obj.reduce_cost;
							var fixed_term = obj.fixed_term;
							var everyday_limit = obj.everyday_limit;
							var is_boutique = obj.is_boutique;
							var is_weekend = obj.is_weekend;
							var is_publish = obj.is_publish;
							console.log("is_publish:"+is_publish)
							var coordinate = obj.coordinate;
							var contact_address = obj.contact_address;
							var promotionType = obj.promotionType;
							var need_participant = obj.need_participant;
							var is_agent = obj.is_agent;
							var percentage = obj.percentage;
							var status = obj.status;
							$("#fpromotionType").val(promotionType);
							var fpromotionTypeName = $("#fpromotionType").find("option:selected").text();
							$("#fpromotionTypeName").val(fpromotionTypeName);
							
							
							uee1.ready(function() { 
			        			uee1.setContent(content);  
			        		});
							$("#fbegin_date").val(begin_date);
							$("#fend_date").val(end_date);
							$("#fmin_age").val(min_age);
							$("#fmax_age").val(max_age);
							$("#fis_publish").val(is_publish);
							$("#fstatus").val(status);
							
							if(free_reservation==1){
								$("#ffree_reservation").attr("checked","checked");
							}
							$("#fshares_times").val(shares_times);
							if(pay_need==1){
								$("#fpay_need").attr("checked","checked");
							}
							$("#fprice").val(price);
							if(old_member_aim==1){
								$("#fold_member_aim").attr("checked","checked");
							}
							$("#fparticipation").val(participation);
							if(community_addible==1){
								$("#fcommunity_addible").attr("checked","checked");
							}
							$("#fservice_staff").val(service_staff);
							
							if(return_coupon==1){
								$("#freturn_coupon").attr("checked","checked");
							}
							if(has_gift==1){
								$("#fhas_gift").attr("checked","checked");
							}
							if(can_use_coupon==1){
								$("#fcan_use_coupon").attr("checked","checked");
							}
							if(is_boutique==1){
								$("#fis_boutique").attr("checked","checked");
							}
							if(is_weekend==1){
								$("#fis_weekend").attr("checked","checked");
							}
							if(need_participant==1){
								$("#fneed_participant").attr("checked","checked");
							}
							if(is_agent==1){
								$("#fis_agent").attr("checked","checked");
							}
							$("#fpercentage").val(percentage);
							$("#fcoupon").val(coupon);
							$("#fgift_name").val(gift_name);
							$("#fleast_cost").val(least_cost);
							$("#freduce_cost").val(reduce_cost);
							$("#ffixed_term").val(fixed_term);
							$("#feveryday_limit").val(everyday_limit);
							$("#fcontact_address").val(contact_address);
							$("#fcoordinate").val(coordinate);

							var image=obj.img;
							if(""!=image){
								$("#image").val(image);
							}
							
							$("#hid").css("display","block"); 
							$("#aid").css("display","none"); 
							
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
			       		}   
			       	} 
			    });
				
				$("#ffid").val(id);
				$("#fname").val(name);
				$("#fbrief").val(brief);
				//UE.delEditor('fcontent');
				var uee1 = UE.getEditor('fcontent', {
 				            autoHeight: false,
 				            readonly: true,
 				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
 				            initialFrameHeight:445  //初始化编辑器高度,默认320
 				        }) ;
    		
			});
       		
       		$("input[name='fclose']").click(function(){
				$("#fimage").unbind();
				$("#hid").css("display","none");
			});
        //=====================================================
        	$('[data-rel=tooltip]').tooltip();
			$('[data-rel=popover]').popover({html:true});
				function booleanFormatter(cellvalue, options, rowdata) {  
					if(cellvalue==1)
						return "是";
					else
					return "否";
				}
				
				$.ajax({//classifycn
			        reservationdate:"post", 
			        url:"${contextPath}/sys/Tzzdic/getTypeSelectList",
			        data:{"parentId":"139"},
			   		dataType:"html", 
			       	success:function(data){ 
			       		$("#freservationdateId").append(data); 
			       		$("#areservationdateId").append(data);    
			       	} 
			    });
				$.ajax({//classifycn
			        reservationdate:"post", 
			        url:"${contextPath}/sys/Tzzdic/getTypeSelectList",
			        data:{"parentId":"143"},
			   		dataType:"html", 
			       	success:function(data){   
			       		$("#freservationtimeId").append(data); 
			       		$("#areservationtimeId").append(data);    
			       	} 
			    });
//===========================================jieshu ================================================
        		
        		
        		
        		$(window).triggerHandler('resize.jqGrid');// trigger window resize to make the grid get the correct size
        		
        		// enable search/filter toolbar
        		// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
        		// jQuery(grid_selector).filterToolbar({});
        		// switch element when editing inline
        		function aceSwitch(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[reservationdate=checkbox]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			}, 0);
        		}
        		
        		// enable datepicker
        		function pickDate(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[reservationdate=text]').datepicker({
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
					view : false,
					viewicon : 'ace-icon fa fa-search-plus grey'
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
			    	   form = form + "<input reservationdate='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open('', '');
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		
        		function style_edit_form(form) {
        			// enable datepicker on "birthday" field and switches for "stock" field
        			form.find('input[name=reservationdate]').datepicker({
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
        			 * .find('input.cbox[reservationdate=checkbox]').addClass('ace') .wrap('<label />').after('<span class="lbl align-top" />');
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
