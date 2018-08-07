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
	<a href="javascript:history.go(-1);">返回活动套餐列表</a>
		<table id="grid-table"></table>
		<div style="position:absolute; ;top:727px;left:162px; padding-right: 100px;" id="operatebtn">
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
 		<div style="display: none;position:absolute ;z-index:999;top:0;left:0;background-color: #fff;border: 1px solid #aaa;width: 600px;"" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
		  	<input type="button"  style="float:left;" name="aup" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="aclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 新加记录 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div><div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/comboPrice/saveOrUpdateComboPrice">  
			<input type="hidden" id="addContent" name="addContent"/>
			<input type="hidden" id="aneedparticipant" name="needparticipant"/>
			<input type="hidden" name="pcid" value="${pcid }"/>
			<div style="float:left;"> 
		  		套餐价格名称	&nbsp;<input type="text" name="name" id="aname" maxlength="128"/>(<span style="color:red;">*必填项</span>)<br><br>
				套餐单位		&nbsp;<input type="text" name="unit" id="aunit" maxlength="32"/><br><br>
				价格	&nbsp;<input type="text" name="price" id="aprice" maxlength="10"/>(<span style="color:red;">*必填项</span>)<br><br>
				有效期	&nbsp;<input type="text" name="startdate" id="astartdate" class="date"/>
				至	&nbsp;<input type="text" name="enddate" id="aenddate" class="date"/>(<span style="color:red;">*必填项</span>)<br><br>
				是否可用	&nbsp;<input type="checkbox" name="disable" id="adisable" /><br><br>
				套餐价格类型	&nbsp;<input type="checkbox" name="pricetype" id="apricetype" />(勾选表示有效期内统一价，不勾选需要按日期设置价格)<br><br>
			 	每日限额	&nbsp;<input type="text" name="daylimit" id="adaylimit" />(<span style="color:red;">*必填项</span>)<br><br>
			 	套餐包含儿童数量	&nbsp;<input type="text" name="childnum" id="achildnum" /><br><br>
			 	套餐包含成人数量	&nbsp;<input type="text" name="adultnum" id="aadultnum" /><br><br>
				
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
		  	<form id="form1" method="post" enctype="multipart/form-data" action="${contextPath}/sys/comboPrice/saveOrUpdateComboPrice">			
				<input type="hidden" name="id" id="fid"/><br><br>
				<input type="hidden" id="fneedparticipant" name="needparticipant"/>
				<input type="hidden" name="pcid" value="${pcid }"/>
				<div style="float:left">
				套餐价格名称	&nbsp;<input type="text" name="name" id="fname" maxlength="128"/>(<span style="color:red;">*必填项</span>)<br><br>
				套餐单位		&nbsp;<input type="text" name="unit" id="funit" maxlength="32"/><br><br>
				价		格	&nbsp;&nbsp;&nbsp;<input type="text" name="price" id="fprice" maxlength="10"/>(<span style="color:red;">*必填项</span>)<br><br>
				有效期	&nbsp;<input type="text" name="startdate" id="fstartdate" class="date"/>
				至	&nbsp;<input type="text" name="enddate" id="fenddate" class="date"/>(<span style="color:red;">*必填项</span>)<br><br>
				是否可用	&nbsp;<input type="checkbox" value="1"  name="disable" id="fdisable" /><br><br>
				套餐价格类型	&nbsp;<input type="checkbox" value="1"  name="pricetype" id="fpricetype" />(勾选表示有效期内统一价，不勾选需要按日期设置价格)<br><br>
			 	每日限额	&nbsp;<input type="text" name="daylimit" id="fdaylimit" />(<span style="color:red;">*必填项</span>)<br><br>
			 	套餐包含儿童数量	&nbsp;<input type="text" name="childnum" id="fchildnum" /><br><br>
			 	套餐包含成人数量	&nbsp;<input type="text" name="adultnum" id="fadultnum" /><br><br>
			 	
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
						选择套餐所属活动
					</div>
				</div>
				<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
					<div id="modal-tip" class="red clearfix"></div>
					<input id="comboId" type="hidden" />
					<div class="widget-box widget-color-blue2">
						<div class="widget-body">
							<div class="widget-main padding-8">
								<ul id="promotionTree"></ul>
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


<div id="modal-table1" class="modal fade" tabindex="-1" data-backdrop="static">
	<div class="modal-dialog">
		<form id="informationForm1">
			<div class="modal-content">
				<div class="modal-header no-padding">
					<div class="table-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							<span class="white">&times;</span>
						</button>
						设置指定日期价格
					</div>
				</div>
				<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
					<div id="modal-tip1" class="red clearfix"></div>
					<input id="combopriceId" type="hidden" />
					<div class="widget-box widget-color-blue2">
						<div class="widget-body">
							<div class="widget-main padding-8">
								<ul id="combodateTree"></ul>
							</div>
							<div>套餐价格：<input type="text" id="dateprice"/></div>
							<div>每日报名人数：<input type="text" id="datelimit"/></div>
						</div>
					</div>
				</div>
				<div class="modal-footer no-margin-top">
					<div class="text-center">
						<button id="submitButton1" type="submit" class="btn btn-app btn-success btn-xs">
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
        		if('${ispublish}'=='1'){
        			$("#operatebtn").css("display","none");
        		}
        		
        		
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
        			url : "${contextPath}/sys/comboPrice/getComboPriceList?pcid=${pcid}",
        			datatype : "json",
        			height : 650,
        			colNames : ['','按日期设置的价格', 'ID', '套餐价格名称', '单位', '价格','开始日期','结束日期','是否可用','大人数量','儿童数量','是否统一价','每日限额','团购设置','指定日期价格'],
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
        				label : '按日期设置的价格',
        				width : 120,
        				formatter: function (value, grid, rows, state) 
        					{
        					 return "<a href=\"${contextPath}/sys/sysuser/home#page/combodateprice?cid=" + value + "\" style=\"color:#9ab\"> 查看详情 </a>";
        					  
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
        				name : 'name',
        				index : 'name',
        				label : '套餐价格名称',
        				width : 180,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'unit',
        				index : 'unit',
        				label : '单位',
        				width : 100,
        				editable : true,
        				editoptions : {size : "20", maxlength : "255"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'price',
        				index : 'price',
        				label : '价格',
        				width : 100
        			}, {
        				name : 'startdate',
        				index : 'startdate',
        				label : '开始日期',
        				width : 100,
        				editable : true,
        					search : false 
        			}, {
        				name : 'enddate',
        				index : 'enddate',
        				label : '结束日期',
        				width : 100,
        				editable : true,
        					search : false 
        			}, {
        				name : 'disable',
        				index : 'disable',
        				label : '是否可用',
        				width : 100,
        				formatter:booleanFormatter,
        				editable : true,
        				edittype : "checkbox",
        					search : false 
        			}, {
        				name : 'adultnum',
        				index : 'adultnum',
        				label : '大人数量',
        				width : 100,
        				editable : true,
        					search : false 
        			}, {
        				name : 'childnum',
        				index : 'childnum',
        				label : '儿童数量',
        				width : 100,
        				editable : true,
        					search : false 
        			}, {
        				name : 'pricetype',
        				index : 'pricetype',
        				label : '是否统一价',
        				width : 100,
        				formatter:booleanFormatter,
        				editable : true,
        				edittype : "checkbox",
        					search : false 
        			}, {
        				name : 'daylimit',
        				index : 'daylimit',
        				label : '每日限额',
        				width : 100
        			}, {
        				name : 'id',
        				index : 'id',
        				label : '团购设置',
        				width : 110  ,
        				formatter: function (value, grid, rows, state) 
    					{
    					 return "<a href=\"${contextPath}/sys/sysuser/home#page/groupbuy?combopriceid="+value+"\" style=\"color:#9ab\"> 团购设置 </a>";
    					  
    					 } ,
    					 sorttype : "int",
    					 search : false
        			}, {
        				name : '',
        				index : '',
        				label : '指定日期价格',
        				width : 110  ,
        				editable : false,
        				search : false,
        				sortable : false,
        				formatter : datePriceFormatter
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
        			editurl : "${contextPath}/sys/comboPrice/operateComboPrice"
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
            					url : "${contextPath}/sys/comboPrice/getPromotionComboTreeList?rand=" + Math.random(1000),
            					type : 'POST',
            					data : {"comboPriceId" : $("#comboId").val(),
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
					$.ajax({
    					url : "${contextPath}/sys/comboPrice/isComboNeedParticipant",
    					data : {
    						"promotioncomboid" : '${pcid }'
    					},
    					type : 'POST',
    					dataType : 'json',
    					success : function(data) {
    						if(data.needParticipant!=null&&data.needParticipant!=""){
    							$("#fneedparticipant").val(data.needParticipant);
    						}
    					}
    				});
					
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
					var unit=rowData.unit;
					var price=rowData.price;
					var startdate=rowData.startdate;
					var enddate=rowData.enddate;
					var disable=rowData.disable;
					var pricetype=rowData.pricetype;
					var daylimit=rowData.daylimit;
					var childnum = rowData.childnum;
					var adultnum = rowData.adultnum;
					
					$("#fid").val(id);
					$("#fname").val(name);
					$("#funit").val(unit);
					$("#fprice").val(price);
					$("#fstartdate").val(startdate);
					$("#fenddate").val(enddate);
					$("#fdaylimit").val(daylimit);
					$("#fchildnum").val(childnum);
					$("#fadultnum").val(adultnum);
					if(disable=="是"){
						$("#fdisable").attr("checked","checked");
					}
					if(pricetype=="是"){
						$("#fpricetype").attr("checked","checked");
					}
					$("#hid").css("display","block"); 
					$("#aid").css("display","none"); 
				});
				$("#addaaa").click(function(){
					$.ajax({
    					url : "${contextPath}/sys/comboPrice/isComboNeedParticipant",
    					data : {
    						"promotioncomboid" : '${pcid }'
    					},
    					type : 'POST',
    					dataType : 'json',
    					success : function(data) {
    						if(data.needParticipant!=null&&data.needParticipant!=""){
    							$("#aneedparticipant").val(data.needParticipant);
    						}
    					}
    				});
					
					$("#aname").val("");
					$("#aunit").val("");
					$("#aprice").val("");
					$("#astartdate").val("");
					$("#aenddate").val("");
					$("#adaylimit").val("");
					$("#achildnum").val("");
					$("#aadultnum").val("");
					$("#adisable").attr("checked",false);
					$("#apricetype").attr("checked",false);
					$("#aid").css("display","block"); 
					$("#hid").css("display","none"); 
					
				});
				

//===========================================================================================	
		
				//=================提交  
				$("input[name='up']").click(function(){  
					if($("#fname").val()==null||$("#fname").val()==""){
						alert("套餐名称不可为空");
						return;
					}
					if($("#fprice").val()==null||$("#fprice").val()==""){
						alert("套餐价格不可为空");
						return;
					}
					if($("#fstartdate").val()==null||$("#fstartdate").val()==""){
						alert("请选择套餐有效期");
						return;
					}
					if($("#fenddate").val()==null||$("#fenddate").val()==""){
						alert("请选择套餐有效期");
						return;
					}
					if($("#fdaylimit").val()==null||$("#fdaylimit").val()==""){
						alert("每日限额不可为空");
						return;
					}
					
					var regz = new RegExp("^[0-9]*[1-9][0-9]*$");
					var regk=new RegExp("^[0-9]+(.[0-9]{1,2})?$");   
    			    if($("#fprice").val()!=""&&$("#fprice").val()!="0"&&!regk.test($("#fprice").val())){  
    			        alert("价格必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#fdaylimit").val()!=""&&$("#fdaylimit").val()!="0"&&!regz.test($("#fdaylimit").val())){  
    			        alert("每日限额必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fchildnum").val()!=""&&$("#fchildnum").val()!="0"&&!regz.test($("#fchildnum").val())){  
    			        alert("儿童数量必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#fadultnum").val()!=""&&$("#fadultnum").val()!="0"&&!regz.test($("#fadultnum").val())){  
    			        alert("成人数量必须是整数格式!");  
    			        return ;
    			    }
    			    
    			    var needParticipant = $("#fneedparticipant").val();
					var achildnum = $("#fchildnum").val();
					var aadultnum = $("#fadultnum").val();
					 if(needParticipant==1){  
						 if((achildnum==""&&aadultnum=="")){
	    			        alert("人员数量必须设置!");
	    			        return ;
						 }else if(achildnum==""&&aadultnum!="" &&parseInt(aadultnum)<1){
	    			        alert("人员数量必须设置!");
	    			        return ;
						 }else if(achildnum!=""&&aadultnum=="" &&parseInt(achildnum)<1){
							 alert("人员数量必须设置!");
							 return ;
						 }else if(achildnum!=""&&aadultnum!="" &&(parseInt(achildnum)+parseInt(aadultnum))<1){
							 alert("人员数量必须设置!");
							 return ;
						 }
	    			    }
					 $("#form1").submit();
				});
				$("input[name='aup']").click(function(){
					
					if($("#aname").val()==null||$("#aname").val()==""){
						alert("套餐名称不可为空");
						return;
					}
					if($("#aprice").val()==null||$("#aprice").val()==""){
						alert("套餐价格不可为空");
						return;
					}
					if($("#astartdate").val()==null||$("#astartdate").val()==""){
						alert("请选择套餐有效期");
						return;
					}
					if($("#aenddate").val()==null||$("#aenddate").val()==""){
						alert("请选择套餐有效期");
						return;
					}
					if($("#adaylimit").val()==null||$("#adaylimit").val()==""){
						alert("每日限额不可为空");
						return;
					}
					var regz = new RegExp("^[0-9]*[1-9][0-9]*$");
					var regk=new RegExp("^[0-9]+(.[0-9]{1,2})?$");   
    			    if($("#aprice").val()!=""&&$("#aprice").val()!="0"&&!regk.test($("#aprice").val())){  
    			        alert("价格必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#adaylimit").val()!=""&&$("#adaylimit").val()!="0"&&!regz.test($("#adaylimit").val())){  
    			        alert("每日限额必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#achildnum").val()!=""&&$("#achildnum").val()!="0"&&!regz.test($("#achildnum").val())){  
    			        alert("儿童数量必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#aadultnum").val()!=""&&$("#aadultnum").val()!="0"&&!regz.test($("#aadultnum").val())){  
    			        alert("成人数量必须是整数格式!");  
    			        return ;
    			    }
    			    var needParticipant = $("#aneedparticipant").val();
					var achildnum = $("#achildnum").val();
					var aadultnum = $("#aadultnum").val();
					 if(needParticipant==1){  
						 if((achildnum==""&&aadultnum=="")){
	    			        alert("人员数量必须设置!");
	    			        return ;
						 }else if(achildnum==""&&aadultnum!="" &&parseInt(aadultnum)<1){
	    			        alert("人员数量必须设置!");
	    			        return ;
						 }else if(achildnum!=""&&aadultnum=="" &&parseInt(achildnum)<1){
							 alert("人员数量必须设置!");
							 return ;
						 }else if(achildnum!=""&&aadultnum!="" &&(parseInt(achildnum)+parseInt(aadultnum))<1){
							 alert("人员数量必须设置!");
							 return ;
						 }
	    			    }
					
					 $("#form2").submit();
				});
				//======================关闭
				$("input[name='fclose']").click(function(){
					$("#fimage").unbind();
					$("#hid").css("display","none");
				});
				$("input[name='aclose']").click(function(){
					$("#aid").css("display","none");
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
</script>
