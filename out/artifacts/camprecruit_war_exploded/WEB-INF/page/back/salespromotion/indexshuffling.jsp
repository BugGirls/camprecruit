<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />  

<div class="row">
	<div class="col-xs-12">
		<table id="grid-table">
		
		
		</table>
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
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
		</script>

		<!-- PAGE CONTENT ENDS > -->
			<!-- 添加修改 -->
		 <div style="display: none;position:absolute ;top:20%;left:30%;background-color: #fff;border: 1px solid #aaa;" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;">&nbsp;&nbsp;添加记录</div>
		  <div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/shuffing/fileUpload"> 
		  		<input type="hidden" name="style" value="0"/>
		  		标题	&nbsp;<input type="text" name="title" id="atitle" maxlength="255"/><br><br>
				链接	&nbsp;<input type="text" name="href" id="ahref" maxlength="255"/><br>
				示例：http://www.zhihuiyingdi.com/weixin/top/salespromotion/salesPromotionDetail?id=139或者#<br>
				排序	&nbsp;<input type="text" name="sort" id="asort" maxlength="2"/> <br><br> 
				<span id="aimageh">
				上传图片	&nbsp;	<input type='file' name='file' id='afile'/> 
				</span> <br> 
				状态 	&nbsp;<select id="astate" name="state" >
						<option value="0">不显示</option>
						<option value="1">显示</option>
					</select>
				<br><br> 
				
				<input type="button"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" id="aup" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" id="aclose" value="取消">
			 </form>
			</div> 
		</div>
		<div style="display: none;position:absolute ;top:20%;left:30%;background-color: #fff;border: 1px solid #aaa;" id="hid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;">&nbsp;&nbsp;编辑所选记录</div>
		  <div style="padding: 10px;">
		  	<form id="form1" method="post" enctype="multipart/form-data" action="${contextPath}/sys/shuffing/fileUpload">			
				<input type="hidden" name="id" id="fid"/><br><br>
				标题	&nbsp;<input type="text" name="title" id="ftitle"/><br><br>
				链接	&nbsp;<input type="text" name="href" id="fhref"/><br>
				示例：http://www.zhihuiyingdi.com/weixin/top/salespromotion/salesPromotionDetail?id=139或者#<br>
				排序	&nbsp;<input type="text" name="sort" id="fsort"/> <br><br>
				<input type="hidden" name="image" id="image">
				<span  id="fimage"> 
				
				</span> 
				 <span id="fimageh" style="display: none;">
				上传图片	&nbsp;	<input type='file' name='file' id='file'/> 
				</span> <br> <br> 
				状态	&nbsp; <select id="fstate" name="state" >
						<option value="0">不显示</option>
						<option value="1">显示</option>
					</select><br><br> 
				<input type="button" id="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" id="fclose" value="取消">
			 </form>
			 </div>
		</div>
		<!-- 添加修改 -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">
	var scripts = [
			null, 
			"${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
			"${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
			"${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
			"${contextPath}/static/assets/js/ajaxfileupload.js", 
			"${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js",
			null ];
	
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {
						// inline scripts related to this page
					jQuery(function($) { 
					 
							var grid_selector = "#grid-table";
							var pager_selector = "#grid-pager";

							// resize to fit page size
							$(window).on('resize.jqGrid',function() {
										$(grid_selector).jqGrid('setGridWidth',
												$(".page-content").width());
									});
							// resize on sidebar collapse/expand
							var parent_column = $(grid_selector).closest(
									'[class*="col-"]');
							$(document).on('settings.ace.jqGrid',function(ev, event_name, collapsed) {
												if (event_name === 'sidebar_collapsed'|| event_name === 'main_container_fixed') {
													// setTimeout is for webkit only to give time for DOM changes and then redraw!!!
													setTimeout(
															function() {
																$(grid_selector).jqGrid('setGridWidth',parent_column.width());
															}, 0);
												}
											});

							jQuery(grid_selector).jqGrid({
												subGrid : false,
												url : "${contextPath}/sys/shuffing/getTzzIndexShuffing",
												datatype : "json",
												height : 650,
												colNames : [ '', 'ID','图片', '标题', '链接地址', '排序', '是否显示', '创建时间' ],
												colModel : [
												{
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
												},{
													name : 'id',
													index : 'id',
													label : 'ID',
													width : 60,
													sorttype : "int",
													search : false
												},{
													name:'image',
													index:'image',
													label : '图片',
													width:150,
													search:false,
													formatter:alarmFormatter,
													editable:true 
												},{
													name : 'title',
													index : 'title',
													label : '活动标题',
													width : 160,
													edittype : 'textarea',
													editable : true,
													editoptions : {	size : "20",maxlength : "255"},
													searchoptions : {sopt : [ 'cn' ]},
													editrules : {required : true}
												},{
													name : 'href',
													index : 'href',
													label : '链接地址',
													width : 120,
													editable : true,
													editoptions : {
														size : "20",
														maxlength : "255"
													},
													search : false, 
												},{
													name : 'sort',
													index : 'sort',
													label : '排序',
													width : 60,
													editable : true,
													editoptions : {
														size : "20",
														maxlength : "1"
													},
													search : false,
													editrules : {
														number : true
													}
												},{
													name : 'statecn',
													index : 'state',
													label : '是否显示',
													width : 60,
													editable : true,
													edittype : "checkbox",
													editoptions : {
														value : "显示:不显示"
													},
													unformat : aceSwitch,
													search : false
												},
												{
													name : 'createTime',
													index : 'createTime',
													label : '创建日期',
													width : 90,
													search : false,
													sorttype : 'date'
												} ],
												//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
												sortname : "id",
												sortorder : "asc",
												viewrecords : true,
												rowNum : 10,
												rowList : [ 10, 20, 30 ],
												pager : pager_selector,
												altRows : true,
												//toppager : true,
												multiselect : true,
												//multikey : "ctrlKey",
												multiboxonly : true,
												loadComplete : function() {
													var table = this;
													setTimeout(
															function() {
																styleCheckbox(table);
																updateActionIcons(table);
																updatePagerIcons(table);
																enableTooltips(table);
															}, 0);
												},
												editurl : "${contextPath}/sys/shuffing/operateTzzIndexShuffing"
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
				//============================================================================			
							$('[data-rel=tooltip]').tooltip();
						 	$('[data-rel=popover]').popover({html:true});
							//显示图片格式
							function alarmFormatter(cellvalue, options, rowdata) {  
								return '<img id="imagel" width="200px" height="100px" src="${contextPath}'+cellvalue+'" alt="' + cellvalue + '" />';
							}
							$("#editaaa").click(function(){
							 
								var id=$('#grid-table').jqGrid('getGridParam','selrow');
								if(id==null) {
									alert("请选择记录！");
									return;
								}
								var rowData = $('#grid-table').jqGrid('getRowData',id);
								
								var id=rowData.id;
								var title=rowData.title;
								var href =rowData.href;
								var sort=rowData.sort;
								var image=rowData.image;
								var state=rowData.statecn;
								var createTime=rowData.createTime;
								//alert("--"+id+"--"+title+"--"+href+"--"+sort+"--"+image+"--"+carouselType+"--"+state+"--"+createTime );
								$("#fid").val(id);
								$("#ftitle").val(title);
								$("#fhref").val(href);
								$("#fsort").val(sort);
								$("#fimage").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+image);
								 if(state=="显示"){
									 state=1;
								 }else{
									 state=0;
								 }
								$("#fstate").val(state);
								$("#fcreateTime").val(createTime);
								$("#hid").css("display","block"); 
								$("#aid").css("display","none"); 
							});
							$("#addaaa").click(function(){ 
								$("#atitle").val("");
								$("#ahref").val("");
								$("#asort").val(""); 
								$("#astate").val(1); 
								$("#aid").css("display","block"); 
								$("#hid").css("display","none"); 
							});
 
		//===========================================================================================	
							$("#fimage").click(function(){
								$(this).html(""); 
								$("#fimageh").css("display","block");
								
							});
							//=================提交  
							$("#up").click(function(){  
					 			if($("#fsort").val()==""|| isNaN($("#fsort").val())){
					 				alert("排序必须为数字！");
					 				return false;
					 			}else{
					 				 $("#form1").submit();
					 			}
							});
							$("#aup").click(function(){   
								 
								if($("#asort").val()==""||isNaN($("#asort").val())){
					 				alert("排序必须为数字！");
					 				return false;
					 				
					 			} else if($("#afile").val()==""){
									alert("请选择图片！");
									return false;
								}else{
									 $("#form2").submit();
								}
							});
							//======================关闭
							$("#fclose").click(function(){
								$("#hid").css("display","none");
							});
							$("#aclose").click(function(){
								$("#aid").css("display","none");
							});
		//===========================================================================================
			
			
			
							$(window).triggerHandler('resize.jqGrid');// trigger window resize to make the grid get the correct size

							// enable search/filter toolbar
							// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
							// jQuery(grid_selector).filterToolbar({});
							// switch element when editing inline
							function aceSwitch(cellvalue, options, cell) {
								setTimeout(
										function() {
											$(cell).find('input[type=checkbox]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
										}, 0);
							}

							// navButtons
							jQuery(grid_selector).jqGrid( 'navGrid', pager_selector,
											{ // navbar options
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
											},{
												 
											},
											{
											 
											},
											{
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
											},
											{
												// search form
												recreateForm : true,
												afterShowSearch : function(e) {
													var form = $(e[0]);
													form.closest(
																	'.ui-jqdialog')
															.find(
																	'.ui-jqdialog-title')
															.wrap(
																	'<div class="widget-header" />');
													style_search_form(form);
												},
												afterRedraw : function() {
													style_search_filters($(this));
												},
												multipleSearch : true
											/**
											 * multipleGroup:true, showQuery: true
											 */
											},
											{
												// view record form
												recreateForm : true,
												beforeShowForm : function(e) {
													var form = $(e[0]);
													form
															.closest(
																	'.ui-jqdialog')
															.find(
																	'.ui-jqdialog-title')
															.wrap(
																	'<div class="widget-header" />');
												}
											});

							// add custom button to export the data to excel
							jQuery(grid_selector)
									.jqGrid('navButtonAdd',pager_selector,{
												caption : "",
												title : "导出Excel",
												buttonicon : "ace-icon fa fa-file-excel-o green",
												onClickButton : function() {
													var keys = [], ii = 0, rows = "";
													var ids = $(grid_selector)
															.getDataIDs(); // Get All IDs
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
										    	   	   for (j = 0 ; j < keys.length; j++)
										    	   		   rows = rows + row[keys[j]] + "\t"; // output each Row as tab delimited
										    	   	   rows = rows + "\n"; // output each row with end of line
										    	   }
													rows = rows + "\n"; // end of line at the end
													var form = "<form name='csvexportform' action='${contextPath}/sys/shuffing/operateTzzIndexShuffing?oper=excel' method='post'>";
													form = form
															+ "<input type='hidden' name='csvBuffer' value='"
															+ encodeURIComponent(rows)
															+ "'>";
													form = form
															+ "</form><script>document.csvexportform.submit();</sc" + "ript>";
													OpenWindow = window.open(
															'', '');
													OpenWindow.document
															.write(form);
													OpenWindow.document.close();
												}
											});

							function style_edit_form(form) {
								form.find('input[name=statecn]').addClass(
										'ace ace-switch ace-switch-5').after(
										'<span class="lbl"></span>');
								// don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
								// .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

								// update buttons classes
								var buttons = form.next().find(
										'.EditButton .fm-button');
								buttons.addClass('btn btn-sm').find(
										'[class*="-icon"]').hide();// ui-icon, s-icon
								buttons.eq(0).addClass('btn-primary').prepend(
										'<i class="ace-icon fa fa-check"></i>');
								buttons.eq(1).prepend(
										'<i class="ace-icon fa fa-times"></i>');

								buttons = form.next().find('.navButton a');
								buttons.find('.ui-icon').hide();
								buttons
										.eq(0)
										.append(
												'<i class="ace-icon fa fa-chevron-left"></i>');
								buttons
										.eq(1)
										.append(
												'<i class="ace-icon fa fa-chevron-right"></i>');
							}

							function style_delete_form(form) {
								var buttons = form.next().find(
										'.EditButton .fm-button');
								buttons.addClass(
										'btn btn-sm btn-white btn-round').find(
										'[class*="-icon"]').hide();// ui-icon, s-icon
								buttons
										.eq(0)
										.addClass('btn-danger')
										.prepend(
												'<i class="ace-icon fa fa-trash-o"></i>');
								buttons.eq(1).addClass('btn-default').prepend(
										'<i class="ace-icon fa fa-times"></i>');
							}

							function style_search_filters(form) {
								form.find('.delete-rule').val('X');
								form.find('.add-rule').addClass(
										'btn btn-xs btn-primary');
								form.find('.add-group').addClass(
										'btn btn-xs btn-success');
								form.find('.delete-group').addClass(
										'btn btn-xs btn-danger');
							}
							function style_search_form(form) {
								var dialog = form.closest('.ui-jqdialog');
								var buttons = dialog.find('.EditTable');
								buttons.find('.EditButton a[id*="_reset"]')
										.addClass('btn btn-sm btn-info').find(
												'.ui-icon').attr('class',
												'ace-icon fa fa-retweet');
								buttons.find('.EditButton a[id*="_query"]')
										.addClass('btn btn-sm btn-inverse')
										.find('.ui-icon').attr('class',
												'ace-icon fa fa-comment-o');
								buttons.find('.EditButton a[id*="_search"]')
										.addClass('btn btn-sm btn-purple')
										.find('.ui-icon').attr('class',
												'ace-icon fa fa-search');
							}

							function beforeDeleteCallback(e) {
								var form = $(e[0]);
								if (form.data('styled'))
									return false;
								form.closest('.ui-jqdialog').find(
										'.ui-jqdialog-titlebar').wrapInner(
										'<div class="widget-header" />');
								style_delete_form(form);
								form.data('styled', true);
							}

							function beforeEditCallback(e) {
								var form = $(e[0]);
								form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
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
													var $class = $.trim(icon.attr('class').replace('ui-icon',''));

													if ($class in replacement)
														icon.attr('class','ui-icon '+ replacement[$class]);
												});
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
	
	
	 
	
</script>

