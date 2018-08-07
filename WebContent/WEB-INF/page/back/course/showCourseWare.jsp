<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
 <style>
 .axtable tr{ height: 40px}
 </style>
<div class="row">
	<div class="col-xs-12">
	<a href="javascript:history.go(-1);">返回上一页</a>
		<table id="grid-table"></table>
			<div style="position:absolute; ;top:530px;left:162px; padding-right: 100px;">
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
<!-- 添加  -->
 		<div style="display: none;position:absolute ;z-index:999;top:20%;left:30%;background-color: #fff;border: 1px solid #aaa;" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;">&nbsp;&nbsp;添加记录</div>
		  <div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/TzzCourse/fileUpload">  	 
				<div style="float:left;"> 
				<input type="hidden" name="cid" value="${cid }"/>
				<table class="axtable">
				
				<tr>	 
				<td>课件名称：</td><td><input type="text" name="name" id="aname" maxlength="64"/></td>
				</tr> 
				<tr>	
				<td>课程位置：</td><td><select name="title" id="atitle" >
					<option value="1">flv视频</option>
					<option value="2">swf视频</option>
				</select> </td>
				</tr> 
				 
				<tr>
				<td>视频时长：</td><td><input type="text" name="vediotime" id="avediotime" maxlength="10"/></td>
				</tr>
				<tr><td>视频地址：</td><td><input type="text" name="vedioHref" id="avedioHref" maxlength="255"/></td></tr> 
				<tr><td>开放用户类型：</td><td><span  id="aavailableRole" ></span></td></tr> 
				 <!--   //name   title 'vediotime','vedioHref' 'availableRole', -->
				</table>
				</div> 
				 <div style="clear: both;"></div>
				<input type="button"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" id="aup" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" id="aclose" value="取消">
			 </form>
			</div> 
		</div>
 <!--  修改 -->
		<div style="display: none;position:absolute ;z-index:999;top:20%;left:30%;background-color: #fff;border: 1px solid #aaa;" id="hid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;">&nbsp;&nbsp;编辑所选记录</div>
		  <div style="padding: 10px;">
		  	<form id="form1" method="post" enctype="multipart/form-data" action="${contextPath}/sys/TzzCourse/fileUpload">	 
				<input type="hidden" name="id" id="fid"/><br><br>
				<input type="hidden" name="cid" value="${cid }"/>
				<div style="float:left;"> 	
				<table class="axtable">
					<tr>	
				<td>课件名称：</td><td><input type="text" name="name" id="fname" maxlength="60"/></td>
				</tr> 
				<tr>	
				<td>课程位置：</td><td><select name="title" id="ftitle" >
					<option value="1">flv视频</option>
					<option value="2">swf视频</option>
				</select> </td>
				</tr> 
				
				<tr>
				<td>视频时长：</td><td><input type="text" name="vediotime" id="fvediotime" maxlength="60"/></td>
				</tr>
				<tr><td>视频地址：</td><td><input type="text" name="vedioHref" id="fvedioHref" maxlength="60"/></td></tr> 
				<tr><td>开放用户类型：</td><td><span id="favailableRole" ></span></td></tr> 
				 </table>    
				</div>
				<div style="clear: both;"></div>
				<input type="button" id="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" id="fclose" value="取消">
			 </form>
			 </div>
		</div>
		
		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
 
 <script type="text/javascript"> 
    $("#aid").draggable();
	$("#hid").draggable();

    </script>
<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
		                "${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js",
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", null ];
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
        				}, 10);
        			}
        		})
        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/TzzCourseWare/getCourseWare?cid=${cid}",
        			datatype : "json",
        			height : 450,
        			colNames : ['', 'ID' , '课件名称', '视频位置', '视频时长',  '视频地址' ,'开放用户类型', '添加时间',''],
        			colModel : [ {
        				name : '', 
        				index : '',
        				width : 80,
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
        				name : 'name',
        				index : 'name',
        				label : '课件名字',
        				width : 140,
        				editable : true,
        				editoptions : {size : "20", maxlength : "256"},
        				searchoptions : {sopt : ['cn','eq']},
        				editrules : {required : true}
        			}, {
        				name : 'title',
        				index : 'title',
        				label : '位置',
        				width : 130,
        				editable : true,
        				edittype : "select",
        				editoptions : {"1":"flv视频","2":"swf视频"}  ,
        				
        			}, {
        				name : 'vediotime',
        				index : 'vediotime',
        				label : '视频时长',
        				width : 130,
        				editable : true,
        				editoptions : {size : "12", maxlength : "25"},
        				editrules : {required : true},
        				search : true
        			} , {
        				name : 'videohrefsub',
        				index : 'videohrefsub',
        				label : '视频地址',
        				width : 110,
        				editable : true,
        				sorttype :  {size : "12", maxlength : "256"},
        			   			
        			}, {
        				name : 'availableRolecn',
        				index : 'availableRolecn',
        				label : '开放用户类型（多个用逗号隔开）',
        				width : 110,
        				editable : true,
        				 sorttype :  {size : "12", maxlength : "256"},
        			 
        				search : false 		
        			},  {
        				name : 'createTime',
        				index : 'createTime',
        				label : '添加时间',
        				width : 110,
        				sorttype : 'date', 
        				search : false   , 		
        			},  {
        				name : 'availableRole',
        				index : 'availableRole', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
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
        				}, 10);
        			},
        			editurl : "${contextPath}/sys/TzzCourseWare/operateCourseWare?cid=${cid}"
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
        		//========================================================
        		$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/Tzzdic/getcoursecheckbox",
			        data:{},
			   		dataType:"html", 
			       	success:function(data){    
			       		$("#aavailableRole").html(data);    
			       	} 
			    });
        		$("#editaaa").click(function(){
        			$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/Tzzdic/getcoursecheckbox",
    			        data:{},
    			   		dataType:"html", 
    			       	success:function(data){   
    			       		$("#favailableRole").html(data);    
    			       	} 
    			    });
					$("#aid").css("display","none"); 
					var id=$('#grid-table').jqGrid('getGridParam','selrow');
					if(id==null) {
						alert("请选择记录！");
						return;
					}
					var rowData = $('#grid-table').jqGrid('getRowData',id);
					 //name   title 'vediotime','vedioHref' 'availableRole',
					
					var id= rowData.id; 
					var name = rowData.name; 
					var title = rowData.title;
					 
					var vedioHref= rowData.videohrefsub ; 
					var vediotime= rowData.vediotime ;  
					var availableRole= rowData.availableRole ;  
					
					$("#fid").val(id); 
					$("#fname").val(name);
					$("#fvedioHref").val(vedioHref);
					$("#fvediotime").val(vediotime); 
					$("#ftitle").val(title);   
					 
					for(var i=0;i<$("#favailableRole").find("input").length;i++){
						$("#aavailableRole").find("input").eq(i).prop("checked",false);
					}
					for(var i=0;i<$("#favailableRole").find("input").length;i++){
						//alert(availableRole.split(",").length);
						for(var j=0;j<availableRole.split(",").length;j++){ 
							if($("#favailableRole").find("input").eq(i).val()==availableRole.split(",")[j]){
								//alert(i+""+j);
								alert($("#favailableRole").find("input").eq(i));
								$("#favailableRole").find("input").eq(i).prop("checked",true);
								continue;
							} else{
							//	alert(i+""+j);
								$("#aavailableRole").find("input").eq(i).prop("checked",false);
							}
						}  
					} 
				 
					$("#hid").css("display","block");  
				});
        		
        		$("#addaaa").click(function(){ 
        			for(var i=0;i<$("#aavailableRole").find("input").length;i++){
						$("#aavailableRole").find("input").eq(i).prop("checked",false);
						 
					}
        			$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/Tzzdic/getcoursecheckbox",
    			        data:{},
    			   		dataType:"html", 
    			       	success:function(data){    
    			       		$("#aavailableRole").html(data);    
    			       	} 
    			    }); 
        			$("#aid").css("display","block"); 
					$("#hid").css("display","none");  
        			$("#fid").val(""); 
					$("#fname").val("");
					$("#fvedioHref").val("");
					$("#fvediotime").val(""); 
					$("#ftitle").val("");   
					 
				});
        		//============================提交===========================
        		$("#up").click(function(){ 
					if($("#fname").val()==""){
						 	alert("请输入名字！");
						 	return;
						}
					 
				 	if($("#fvediotime").val()==""){
					 alert("请输入时长！");
					 return;
					}
				 	if($("#fvedioHref").val()==""){
						 alert("请输入视频地址！");
						 return;
					} 
				 	$.ajax({
		                 url:"${contextPath}/sys/TzzCourseWare/submit",
		                 data:$("#form1").serialize(),
		                 type:"post",
		                 success:function(data){
		                	 location.reload();
		               	}
		            });    
				});
				
				
				$("#aup").click(function(){ 
					if($("#aname").val()==""){
					 	alert("请输入名字！");
					 	return;
					}
				 
			 	if($("#avediotime").val()==""){
				 alert("请输入时长！");
				 return;
				}
			 	if($("#avedioHref").val()==""){
					 alert("请输入视频地址！");
					 return;
				} 
				$.ajax({
	                 url:"${contextPath}/sys/TzzCourseWare/submit",
	                 data:$("#form2").serialize(),
	                 type:"post",
	                 success:function(data){ 
	                	 location.reload();
	               	}
	            });    
				});
				//======================关闭
				$("#fclose").click(function(){
					$("#hid").css("display","none"); 
				});
				$("#aclose").click(function(){ 
					$("#aid").css("display","none");
				});
        		
        		//========================================================
        		$(window).triggerHandler('resize.jqGrid');// trigger window resize to make the grid get the correct size
        		
        		// enable search/filter toolbar
        		// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
        		// jQuery(grid_selector).filterToolbar({});
        		// switch element when editing inline
        		function aceSwitch(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[type=checkbox]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			}, 10);
        		}
        		
        		// enable datepicker
        		function pickDate(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[type=text]').datepicker({
        					format : 'yyyy-mm-dd',
        					autoclose : true,
        				    language: 'zh-CN'
        				});
        			}, 10);
        			
        		}
        		
        		// navButtons
        		jQuery(grid_selector).jqGrid('navGrid', pager_selector, { // navbar options
        			edit : false,
        			editicon : 'ace-icon fa fa-pencil blue',
        			add : false,
        			addicon : 'ace-icon fa fa-plus-circle purple',
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
        			  width: 500,
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
        			 width: 500,
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
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
        			}
        		});
        		
        		// add custom button to export the data to excel
				jQuery(grid_selector).jqGrid('navButtonAdd', pager_selector,{
				   caption : "",
			       title : "导出Excel",
			       buttonicon : "ace-icon fa fa-file-excel-o green", 
			       onClickButton : function () { 
			    	   var keys = [], ii = 0, rows = "";
			    	   var ids = $(grid_selector).getDataIDs(); // Get All IDs
			    	   var row = $(grid_selector).getRowData(ids[0]); // Get First row to get the labels
			    	   
			    	   var label = $(grid_selector).jqGrid('getGridParam','colNames');
   			    	   for(var i = 1 ; i < label.length; i++){
			    	   	   rows = rows + label[i] + "\t"; // output each Column as tab delimited
			    	   }
			    	  
   			    	   for (var k in row) {
			    	   	   keys[ii++] = k; // capture col names
			    	   	 rows = rows + k + "\t";
			    	   }
			    	   
			    	   rows = rows + "\n"; // Output header with end of line
			    	   for (i = 0; i < ids.length; i++) {
			    	   	   row = $(grid_selector).getRowData(ids[i]); // get each row
			    	   	   for (j = 0; j < keys.length; j++)
			    	   		   rows = rows + row[keys[j]] + "\t"; // output each Row as tab delimited
			    	   	   rows = rows + "\n"; // output each row with end of line
			    	   } 
			    	   
			    	   rows = rows + "\n"; // end of line at the end
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/TzzCourseWare/operateCourseWare?cid=${cid}&oper=excel' method='post'>";
			    	   form = form + "<input type='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open('', '');
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		
        		function style_edit_form(form) {
        			// form.find('input[name=statusCn]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			// don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
        			// .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

        			// update buttons classes
        			form.find('input[name=createTime]').datepicker({
        				format : 'yyyy-mm-dd',
        				autoclose : true,
        			    language: 'zh-CN'
        			});
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
        			buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
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
        			alert("before edite call back!!!");
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
</script>
        		