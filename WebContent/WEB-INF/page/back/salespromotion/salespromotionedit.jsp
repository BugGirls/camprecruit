<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<style>
.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
}
</style>
<div class="row">
	<div class="col-xs-12">

 <!--  修改 -->
 
		<div style="top:0;left:0;background-color: #fff;border: 1px solid #aaa;" >
		   <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;" id="editarea">
		  	<input type="button"  style="float:left;" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="closebtn"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 编辑所选记录
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
					教练	&nbsp;<select name="service_staff" id="fservice_staff"> </select><input type="hidden" name="service_staff_name" id="fservice_staff_name" /><br><br>
					
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
			 </form>
				<input type="button" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="closebtn" value="取消">
			 </div>
		</div>

		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 

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
function autosave(i){
	console.log($("#"+i+"name").val());
	console.log($("#"+i+"begin_date").val());
	console.log($("#"+i+"end_date").val());
	if($("#"+i+"name").val()!=""&&$("#"+i+"name").val()!=null &&$("#"+i+"begin_date").val()!=""&&$("#"+i+"begin_date").val()!=null&&$("#"+i+"end_date").val()!=""&&$("#"+i+"end_date").val()!=null){
		console.log(new Date());
		$.ajax({
	                type: "POST",
	                url:"${contextPath}/sys/TzzSalesPromotion/autosave",
	                data:$('#'+i+i+'_form').serialize(),// 你的formid
	                error: function(request) {
	                    //alert("Connection error");
	                },
	                success: function(data) {
	                    console.log(data);
						$("#"+i+i+"id").val(data);
						console.log($("#"+i+i+"id"))
	                }
	            });
	}
}
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
        		var spid = '${spid}';
        		if(spid!=null&&spid>0){
					$.ajax({//classifycn
				        type:"post", 
				        url:"${contextPath}/sys/TzzSalesPromotion/getSalesPromotion",
				        data:{"id":spid},
				   		dataType:"json", 
				       	success:function(data){ 
				       		if(data.success){
				       			var obj = data.obj;
				       			console.log(obj)
				       			var brief=obj.brief;
				       			var name=obj.name;
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
								var personalpercentage = obj.personalpercentage;
								var status = obj.status;
								var is_remind = obj.is_remind;
								var remind_phone = obj.remind_phone;
							
								//发布过的课程不能直接编辑
								if(is_publish==1){
									$("input[name='up']").css("display","none");
									//$("input:not(:button,:hidden)").prop("disabled", "disabled");
									$('form').find('input, select').attr("disabled", "disabled");
									$("#editarea").append("（先下架课程才能编辑）");
									
									$("#fpromotionType").val(promotionType);
									var fpromotionTypeName = $("#fpromotionType").find("option:selected").text();
									$("#fpromotionTypeName").val(fpromotionTypeName);
								
									var uee2 = UE.getEditor('fcontent', {
		     				            autoHeight: false,
		     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
		     				            initialFrameHeight:445  //初始化编辑器高度,默认320
		     				        }) ;
									uee2.ready(function() { 
					        			uee2.setContent(content);  
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
								
// 									if(return_coupon==1){
// 										$("#vreturn_coupon").attr("checked","checked");
// 									}
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
									if(is_remind==1){
										$("#fis_remind").attr("checked","checked");
									}
									$("#fremind_phone").val(remind_phone);
									$("#fpercentage").val(percentage);
									$("#fpersonalpercentage").val(personalpercentage);
									$("#fgift_name").val(gift_name);
									$("#feveryday_limit").val(everyday_limit);
									$("#fcontact_address").val(contact_address);
									$("#fcoordinate").val(coordinate);

									var image=obj.img;
									console.log(image);
									if(""!=image){
										$("#image").val(image);
										$("#fimage").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>图片<img id="imagel" width="200px" height="100px" src="${contextPath}'+image + '"/><br/>' );
									}
								
									$("#ffid").val(spid);
									$("#fname").val(name);
									$("#fbrief").val(brief);
								
								}else{
								
								
									$("#fpromotionType").val(promotionType);
									var fpromotionTypeName = $("#fpromotionType").find("option:selected").text();
									$("#fpromotionTypeName").val(fpromotionTypeName);
								
									var uee1 = UE.getEditor('fcontent', {
		     				            autoHeight: false,
		     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
		     				            initialFrameHeight:445  //初始化编辑器高度,默认320
		     				        }) ;
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
									if(is_remind==1){
										$("#fis_remind").attr("checked","checked");
									}
									$("#fremind_phone").val(remind_phone);
									$("#fpercentage").val(percentage);
									$("#fpersonalpercentage").val(personalpercentage);
									$("#fgift_name").val(gift_name);
									$("#feveryday_limit").val(everyday_limit);
									$("#fcontact_address").val(contact_address);
									$("#fcoordinate").val(coordinate);

									var image=obj.img;
									console.log(image);
									if(""!=image){
										$("#image").val(image);
										$("#fimage").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片<img id="imagel" width="200px" height="100px" src="${contextPath}'+image + '"/><br/>' ); 
									}else{
										$("#fimage").html("上传图片	&nbsp;	<input type='file' name='file' id='ffile'/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>" );
									}
								
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
								
									$("#ffid").val(spid);
									$("#fname").val(name);
									$("#fbrief").val(brief);
								
									poll = setInterval("$('#ffContent').val(UE.getEditor('fcontent').getContent());autosave('f')", 60000);
								}
				       		}   
				       	} 
				    });
        		}else{
        			$("#fimage").html("上传图片	&nbsp;	<input type='file' name='file' id='ffile'/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>" );
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
            		var uee1 = UE.getEditor('fcontent', {
			            autoHeight: false,
			            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
			            initialFrameHeight:445  //初始化编辑器高度,默认320
			        }) ;
					uee1.ready(function() { 
	    			});
					poll = setInterval("$('#ffContent').val(UE.getEditor('fcontent').getContent());autosave('f')", 60000);
        		}

           		
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
           		
        		$('.date').datepicker({
					format : 'yyyy-mm-dd',
					autoclose : true,
				    language: 'zh-CN'
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
        		
        		$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/promotiontype/getCoachList",
			   		dataType:"html", 
			       	success:function(data){ 
			       		$("#fservice_staff").append(data); 
			       		
			       		$("#fservice_staff_name").val($("#fservice_staff option").eq(0).text());
			       		
			       		$("#fservice_staff").on('change', function() {
			       			var value = $("#fservice_staff").find("option:selected").text();
			       			$("#fservice_staff_name").val(value);
			       		});
			       	} 
			    });
        		
//         		var uee1 = UE.getEditor('fcontent', {
// 			            autoHeight: false,
// 			            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
// 			            initialFrameHeight:445  //初始化编辑器高度,默认320
// 			        }) ;
// 				uee1.ready(function() { 
//         			uee1.setContent(content);  
//         		});

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
				
				//======================关闭
				$("input[name='closebtn']").click(function(){
					$("#fimage").unbind();
					window.location.href="${contextPath}/sys/sysuser/home#page/salespromotion";
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
        			//$(grid_selector).jqGrid('GridUnload');
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
     
</script>
