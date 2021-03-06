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
		<p style="text-align: center; font-size: 20px;">发布招聘会</p>
		<input id="hideid" style="display: none;"/>
		是否发布招聘会：<input type="checkbox" id="is_publish" value="1" role="checkbox" /><br><br>
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
		
		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 

 <script type="text/javascript"> 
    $("#aid").draggable();
	$("#hid").draggable();
	function publicaction(id){
		if(confirm("确定要发布招聘会？")){
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
		
	}
	
	function cancelpublish(id){
	 if(confirm("确定要取消招聘会发布？")){
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
	 if(confirm("确定提交招聘会去审核？")){
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
        			url : "${contextPath}/sys/recruitment/getRecruitmentList",
        			datatype : "json",
        			height : 650,
        			colNames : ['','招聘岗位','ID', '主题招聘类别', '主题招聘类别', '主题招聘名称', '简介','创建人','创建时间','状态','是否发布','操作'],
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
        				label : '招聘岗位',
        				width : 100,
        				formatter: function (value, grid, rows, state) 
        					{
         						return "<a href=\"${contextPath}/sys/sysuser/home#page/recruitcareer?rid=" + value + "&ispublish="+rows.is_publish+"\" style=\"color:#9ab\">招聘岗位 </a>";
        					  //return "<a href=''>招聘岗位</a>";
        					 } ,
        				sorttype : "int",
        				search : false
        			}, {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sorttype : "int",
        				search : false
        			}, {
        				name : 'themeid',
        				index : 'themeid',
        				label : '主题类别',
        				width : 80,
        				hidden : true,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'themename',
        				index : 'themename',
        				label : '主题类别',
        				width : 80,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'name',
        				index : 'name',
        				label : '主题招聘名称',
        				width : 240,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'brief',
        				index : 'brief',
        				label : '简介',
        				width : 300,
        				editable : true,
        				editoptions : {size : "20", maxlength : "255"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'creater',
        				index : 'creater',
        				label : '创建人',
        				width : 80  ,
        				editable : true,
        				search : false	
        				
        			}, {
        				name : 'createtime',
        				index : 'createtime',
        				label : '创建时间',
        				width : 150 ,
        				editable : true,
        				search : false 	
        			}, {
        				name : 'status',
        				index : 'status',
        				label : '状态',
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
//         				formatter: publishFormatter,
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
        			editurl : "${contextPath}/sys/recruitment/operateRecruitment"
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
				
				$("#editaaa").click(function(){
					var id=$('#grid-table').jqGrid('getGridParam','selrow');
					if(id==null) {
						alert("请选择记录！");
						return;
					}
				 	window.location.href="${contextPath}/sys/sysuser/home#page/themerecruitmentedit?spid="+id;
					
				});
				$("#addaaa").click(function(){
					window.location.href="${contextPath}/sys/sysuser/home#page/themerecruitmentedit?spid=0";
				});
				
				//======================关闭
				$("input[name='vclose']").click(function(){
					$("#vimage").unbind();
					$("#vid").css("display","none");
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
