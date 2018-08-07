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
			
			$("#fexpirytype").change(function(){ 
			    var selecttype =  $(this).val();
			    if(selecttype=="2"){
					 $('#fexpiredatetype').css("display","block");
					 $('#fexpiredaytype').css("display","none");
					 $('#fexpiryday').val("");
					 
				}else{
					 $('#fexpiredatetype').css("display","none");
					 $('#fexpiredaytype').css("display","block");
					 $('#fdatefrom').val("");
					 $('#fdateto').val("");
				}
			});
			$("#aexpirytype").change(function(){ 
			    var selecttype =  $(this).val();
			    if(selecttype=="2"){
					 $('#aexpiredatetype').css("display","block");
					 $('#aexpiredaytype').css("display","none");
					 $('#aexpiryday').val("");
				}else{
					 $('#aexpiredatetype').css("display","none");
					 $('#aexpiredaytype').css("display","block");
					 $('#adatefrom').val("");
					 $('#adateto').val("");
				}
			});
		</script>
		
<!-- 添加  -->
 		<div style="display: none;position:absolute ;z-index:999;top:0;left:0;background-color: #fff;border: 1px solid #aaa;width: 600px;"" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
		  	<input type="button"  style="float:left;" name="aup" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="aclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 新加记录 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div><div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/syscoupon/saveOrUpdateCoupon">  
			<div style="float:left;"> 
		  		券名称	&nbsp;<input type="text" name="couponname" id="acouponname" /><br><br>
		  		描述	&nbsp;<input type="text" name="desc" id="adesc" /><br><br>
		  		金额	&nbsp;<input type="text" name="couponfee" id="acouponfee" /><br><br>
		  		数量	&nbsp;<input type="text" name="quantity" id="aquantity" /><br><br>
		  		有效期设置方式 &nbsp; <select id="aexpirytype" ><option value="1">按天数</option><option value="2">按日期</option></select><br><br>
		  		<div id="aexpiredaytype" >
		  		有效期（天）	&nbsp;<input type="text" name="expiryday" id="aexpiryday" /><br><br></div>
		  		<div id="aexpiredatetype" style="display: none;">
		  		有效期起	&nbsp;<input type="text" name="datefrom" id="adatefrom"  class="date"/>
		  		有效期止	&nbsp;<input type="text" name="dateto" id="adateto"  class="date"/><br><br></div>
<!-- 				类型	&nbsp;&nbsp;&nbsp;<input type="text" name="type" id="atype" /><br><br> -->
			 	起用金额	&nbsp;<input type="text" name="leastuse" id="aleastuse" /><br><br>
				是否可用	&nbsp;<input type="checkbox" name="status" id="astatus" role="checkbox" value="1" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span><br><br>
				 领取优惠券链接 &nbsp;<input type="text" name="couponurl" id="acouponurl"  style="width: 500px;"/><a href="javascript: generateCouponUrl(0);">生成链接</a><br><br>
				 </div>
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
		  	<form id="form1" method="post" enctype="multipart/form-data" action="${contextPath}/sys/syscoupon/saveOrUpdateCoupon">			
				<input type="hidden" name="id" id="fid"/><br><br>
				<div style="float:left">
				券名称	&nbsp;<input type="text" name="couponname" id="fcouponname" /><br><br>
		  		描述	&nbsp;<input type="text" name="desc" id="fdesc" /><br><br>
		  		金额	&nbsp;<input type="text" name="couponfee" id="fcouponfee" /><br><br>
		  		数量	&nbsp;<input type="text" name="quantity" id="fquantity" /><br><br>
		  		有效期设置方式 &nbsp; <select id="fexpirytype"  ><option value="1">按天数</option><option value="2">按日期</option></select><br><br>
		  		<div id="fexpiredaytype" >
		  		有效期（天）	&nbsp;<input type="text" name="expiryday" id="fexpiryday" /><br><br></div>
		  		<div id="fexpiredatetype" style="display: none;">
		  		有效期起	&nbsp;<input type="text" name="datefrom" id="fdatefrom"  class="date" />
		  		有效期止	&nbsp;<input type="text" name="dateto" id="fdateto"  class="date"/><br><br></div>
<!-- 				类型	&nbsp;&nbsp;&nbsp;<input type="text" name="type" id="ftype" /><br><br> -->
			 	起用金额	&nbsp;<input type="text" name="leastuse" id="fleastuse" /><br><br>
			 	是否可用	&nbsp;<input type="checkbox" name="status" id="fstatus" value="1" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span><br><br>
			 	领取优惠券链接 &nbsp;<input type="text" name="couponurl" id="fcouponurl"  style="width: 500px;"/><a href="javascript: generateCouponUrl(1);">生成链接</a><br><br>
				</div>
				<div style="clear: both;"></div>
				<input type="button" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="fclose" value="取消">
			 </form>
			 </div>
		</div>
		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
 
<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

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
					<input id="couponId" type="hidden" />
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

<script type="text/javascript"src="${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js"></script> 
<script type="text/javascript"src="${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script> 

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, 
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                "${contextPath}/static/assets/js/fuelux/fuelux.tree.js",
		                 null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		$(".date").datepicker({
					format : 'yyyy-mm-dd',
					autoclose : true,
				    language: 'zh-CN'
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
        			url : "${contextPath}/sys/syscoupon/getSysCouponList",
        			datatype : "json",
        			height : 650,
        			colNames : ['', 'ID', '券名称','描述','金额','有效期(天)','有效期起','有效期止','起用金额','是否可用','数量','已领取数量',''],
        			colModel : [ {
        				name : '',
        				index : '',
        				width : 60,
        				fixed : true,
        				sortable : false,
        				resize : false,
        				formatter : 'actions',
        				formatoptions : {
        					keys : true,
        					editbutton : false,//disable delete button
        					delOptions : {
        						recreateForm : true,
        						beforeShowForm : beforeDeleteCallback
        					}
        					//editformbutton:true, editOptions:{recreateForm:true, beforeShowForm:beforeEditCallback}
        				}
        			}, {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sorttype : "long",
        				search : false
        			}, {
        				name : 'couponname',
        				index : 'couponname',
        				label : '券名称',
        				width : 200
        			}, {
        				name : 'description',
        				index : 'description',
        				label : '描述',
        				width : 200
        			}, {
        				name : 'couponfee',
        				index : 'couponfee',
        				label : '金额',
        				width : 100
        			}, {
        				name : 'expiryday',
        				index : 'expiryday',
        				label : '有效期',
        				width : 100,
        				search : false
        			}, {
        				name : 'datefrom',
        				index : 'datefrom',
        				label : '有效期起',
        				width : 100,
        				formatter : timeformat,
        				search : false
        			}, {
        				name : 'dateto',
        				index : 'dateto',
        				label : '有效期止',
        				width : 100,
        				formatter : timeformat,
        				search : false
        			}, {
        				name : 'leastuse',
        				index : 'leastuse',
        				label : '起用金额',
        				width : 100,
        				search : false
        			}, {
        				name : 'status',
        				index : 'status',
        				label : '是否可用',
        				width : 100,
        				formatter : booleanFormatter,
        				search : false
        			}, {
        				name : 'quantity',
        				index : 'quantity',
        				label : '数量',
        				width : 100,
        				search : false
        			}, {
        				name : 'dispatchnum',
        				index : 'dispatchnum',
        				label : '已领取数量',
        				width : 100,
        				search : false
        			}, {
        				name : 'couponurl',
        				index : 'couponurl',
        				label : '',
        				width : 100,
        				hidden : true,
        				search : false
        			}
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
        			editurl : "${contextPath}/sys/syscoupon/operateCoupon"
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
				
				function promotionFormatter(cellvalue, options, cell) {
        			var template = "<button data-toggle='modal' onclick='javascript:$(\"#modal-table\").modal(\"toggle\");$(\"#comboId\").val(\"" + cell.id +"\");' class='btn btn-xs btn-warning'><i class='ace-icon fa fa-flag bigger-120'></i></button>";
        			return template;
        		}
				
				var treeflag = 0;
        		$("#modal-table").on('shown.bs.modal', function() {
        			var remoteDateSource = function(options, callback) {
        				var parent_id = null;
            			if (!('text' in options || 'type' in options)) {
            				parent_id = 0;// load first level data
            			} else if ('type' in options && options['type'] == 'folder') {// it has children
            				if ('additionalParameters' in options && 'children' in options.additionalParameters)
            					parent_id = options.additionalParameters['id'];
            			}
            			if (parent_id !== null) {
            				$.ajax({
            					url : "${contextPath}/sys/syscoupon/getSyscouponTreeList?rand=" + Math.random(1000),
            					type : 'POST',
            					data : {"couponId" : $("#comboId").val(),
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
            		$('#promotionTree').ace_tree({
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
        		    	$('#promotionTree').tree('render');
        		    }
        		    treeflag++;
        		    $('#promotionTree').one('loaded.fu.tree', function (event, data) {
        		    	$('#promotionTree').data('ignore-disclosures-limit', true);
        		    	$('#promotionTree').tree('discloseAll');
       		    	})
        		});
        		
        		$('#submitButton').on('click', function() {        			
        			var output = '';
					var items = $('#promotionTree').tree('selectedItems');
					for(var i in items) if (items.hasOwnProperty(i)) {
						var item = items[i];
						output += item.additionalParameters['id'];
					}
					alert($("#comboId").val());
					if(output == ''){
        				$.gritter.add({
    		                title: "活动信息",
    		                text: "请展开树勾选菜单",
    		                class_name: "gritter-info gritter-center"
    		            });  
	        			return;
					}
					$.ajax({
    					url : "${contextPath}/sys/comboPrice/comboPriceAsign",
    					data : {
    						comboPriceId : $("#comboId").val(),
    						promotionComboId : output
    					},
    					type : 'POST',
    					dataType : 'json',
    					complete : function(response) {
    						$("#modal-table").modal("toggle");
    					}
    				});
					//$('#authorityTree').tree('destroy');
				});
        		
        		
        		
        		function datePriceFormatter(cellvalue, options, cell) {
        			var template = "<button data-toggle='modal' onclick='javascript:$(\"#modal-table1\").modal(\"toggle\");$(\"#combopriceId\").val(\"" + cell.id +"\");' class='btn btn-xs btn-warning'><i class='ace-icon fa fa-flag bigger-120'></i></button>";
        			return template;
        		}
				
				var treeflag = 0;
        		$("#modal-table1").on('shown.bs.modal', function() {
        			var remoteDateSource = function(options, callback) {
        				var parent_id = null;
            			if (!('text' in options || 'type' in options)) {
            				parent_id = 0;// load first level data
            			} else if ('type' in options && options['type'] == 'folder') {// it has children
            				if ('additionalParameters' in options && 'children' in options.additionalParameters)
            					parent_id = options.additionalParameters['id'];
            			}
            			if (parent_id !== null) {
            				$.ajax({
            					url : "${contextPath}/sys/comboPrice/getDefaultDateTreeList?rand=" + Math.random(1000),
            					type : 'POST',
            					data : {"comboPriceId" : $("#combopriceId").val(),
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
            		$('#combodateTree').ace_tree({
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
        		    if(treeflag > 0){
        		    	$('#combodateTree').tree('render');
        		    }
        		    treeflag++;
        		    $('#combodateTree').one('loaded.fu.tree', function (event, data) {
        		    	$('#combodateTree').data('ignore-disclosures-limit', true);
        		    	$('#combodateTree').tree('discloseAll');
       		    	})
        		});
        		
        		$('#submitButton1').on('click', function() {        			
        			var output = '';
        			var dateprice= $("#dateprice").val();
        			var datelimit =$("#datelimit").val();
        			if(dateprice==null||dateprice==""){
        				alert("请填写价格！");
        				return;
        			}
        			if(datelimit==null||datelimit==""){
        				alert("请填写每日报名人数！");
        				return;
        			}
					var items = $('#combodateTree').tree('selectedItems');
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
    		                title: "日期选择",
    		                text: "请展开树勾选日期",
    		                class_name: "gritter-info gritter-center"
    		            });  
        				alert("请选择日期！");
	        			return;
					}
					$.ajax({
    					url : "${contextPath}/sys/comboPrice/comboDatePriceAsign",
    					data : {
    						comboPriceId : $("#combopriceId").val(),
    						dateprice : $("#dateprice").val(),
    						datelimit : $("#datelimit").val(),
    						datelist : output
    					},
    					type : 'POST',
    					dataType : 'json',
    					complete : function(response) {
    						$("#modal-table1").modal("toggle");
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
					var rowData = $('#grid-table').jqGrid('getRowData',id);
					
					
					//name 简介brief 内容介绍content 成本价costPrice 金额amount 类型categoryName 
        			//图片image 型号xinghao 规格guige 材质caizhi 品牌pinpai 产地chandi 单位danwei 点击浏览次数clickNum 销量sales 热销rexiaoName
					
					var id=rowData.id;
					var couponname=rowData.couponname;
					var desc=rowData.description;
					var couponfee=rowData.couponfee;
					var expiryday=rowData.expiryday;
					var datefrom=rowData.datefrom;
					var dateto=rowData.dateto;
					var leastuse=rowData.leastuse;
					var type=rowData.type;
					var status=rowData.status;
					var couponurl = rowData.couponurl;
					var quantity = rowData.quantity;
					console.log("status:"+status);
					$("#fid").val(id);
					$("#fcouponname").val(couponname);
					$("#fdesc").val(desc);
					$("#fcouponfee").val(couponfee);
					$("#fexpiryday").val(expiryday);
					$("#fdatefrom").val(datefrom);
					$("#fdateto").val(dateto);
// 					$("#ftype").val(type);
					$("#fleastuse").val(leastuse);
					$("#fcouponurl").val(couponurl);
					$("#fquantity").val(quantity);
					if(status==1){
						$("#fstatus").attr("checked","checked");
					}
					$("#hid").css("display","block"); 
					$("#aid").css("display","none"); 
				});
				$("#addaaa").click(function(){
					$("#acouponname").val("");
					$("#adesc").val("");
					$("#acouponfee").val("");
					$("#aexpiryday").val("");
					$("#adatefrom").val("");
					$("#adateto").val("");
					//$("#atype").val("");
					$("#aleastuse").val("");
					$("#acouponurl").val("");
					$("#aquantity").val("");
					$("#astatus").attr("checked",false);
					$("#aid").css("display","block"); 
					$("#hid").css("display","none"); 
					
				});
				

//===========================================================================================	
		
				//=================提交  
				$("input[name='up']").click(function(){  
					var regz = new RegExp("^[0-9]*[1-9][0-9]*$");
					//var regk=new RegExp("^[0-9]+(.[0-9]{2})?$");   
    			    if($("#fcouponfee").val()!=""&&$("#fcouponfee").val()!="0"&&!regz.test($("#fcouponfee").val())){  
    			        alert("金额必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fquantity").val()!=""&&$("#fquantity").val()!="0"&&!regz.test($("#fquantity").val())){  
    			        alert("数量必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fexpiryday").val()!=""&&$("#fexpiryday").val()!="0"&&!regz.test($("#fexpiryday").val())){  
    			        alert("有效期必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fleastuse").val()!=""&&$("#fleastuse").val()!="0"&&!regz.test($("#fleastuse").val())){  
    			        alert("起用金额必须是整数格式!");  
    			        return ;
    			    }
					 $("#form1").submit();
				});
				$("input[name='aup']").click(function(){  
					var regz = new RegExp("^[0-9]*[1-9][0-9]*$");
					//var regk=new RegExp("^[0-9]+(.[0-9]{2})?$");   
    			    if($("#acouponfee").val()!=""&&$("#acouponfee").val()!="0"&&!regz.test($("#acouponfee").val())){  
    			        alert("金额必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#aquantity").val()!=""&&$("#aquantity").val()!="0"&&!regz.test($("#aquantity").val())){  
    			        alert("数量必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#aexpiryday").val()!=""&&$("#aexpiryday").val()!="0"&&!regz.test($("#aexpiryday").val())){  
    			        alert("有效期必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#aleastuse").val()!=""&&$("#aleastuse").val()!="0"&&!regz.test($("#aleastuse").val())){  
    			        alert("起用金额必须是整数格式!");  
    			        return ;
    			    }
					 $("#form2").submit();
				});
				//======================关闭
				$("input[name='fclose']").click(function(){
					$("#hid").css("display","none");
				});
				$("input[name='aclose']").click(function(){
					$("#aid").css("display","none");
				});
				
				function timeformat(cellvalue, options, cell) {
					if(cellvalue==""||cellvalue==null){
						return "";
					}else{
		       			return new Date(cellvalue).Format("yyyy-MM-dd");
					}
	       		}
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
        			form.find('input[name=birthday]').datepicker({
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
        
        function generateCouponUrl(obj){
        	$.ajax({
				url : "${contextPath}/sys/syscoupon/generateCouponUrl",
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					if(data.couponurl!=null&&data.couponurl!=""){
						if(obj==0){
							$("#acouponurl").val(data.couponurl);
						}else if(obj==1){
							$("#fcouponurl").val(data.couponurl);
						}
					}
				}
			});
        }
</script>
