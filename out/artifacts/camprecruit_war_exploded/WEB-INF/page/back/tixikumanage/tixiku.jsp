<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
	<!-- 编辑器 -->
 
 <style>
 .axtable tr{ height: 40px}
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
		   $("#aid").hide();
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
		</script>
<!-- 添加  -->
            					
 		<div style="display:none;position: absolute ;z-index:999;top:30px;left:50%;margin-left:-570px;background-color: #fff;border: 1px solid #aaa;" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
		  	<input type="button"  style="float:left;" name="aup" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="aclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 新加记录 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div><div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/tixiku/fileUpload">  	 
				<input type="hidden" name="contenttop" id="acontenttop"/> 
				<input type="hidden" name="content" id="acontent"/> 
				<input type="hidden" name="contentbot" id="acontentbot"/> 
				<div style="float:left;"> 
				<table class="axtable">	 
				<tr><td>名称：</td><td><input type="text" name="name" id="aname" maxlength="60"/></td></tr> 
				<tr><td>标题：</td><td><input type="text" name="title" id="atitle" maxlength="200"style="width:978px;"/></td></tr> 
				<tr><td>内容简介1：</td><td> 
				 <script id="aeditortop" name="contenttop" type="text/plain"> </script> 
				</td></tr>
				<tr><td>内容简介2：</td><td> 
				 <script id="aeditor" name="content" type="text/plain"> </script> 
				</td></tr>
				<tr><td>内容简介3：</td><td> 
				 <script id="aeditorbot" name="contentbot" type="text/plain"> </script> 
				</td></tr>
				<input type="hidden" name="image" id="image">  
				<tr id="aimageh"><td>上传图片：</td><td><input type='file' name='file' id='afile'/> </td></tr> 
				<tr><td>大分类：</td><td><span id="aclassifyId"> </span></td></tr>
				<tr><td>体系分类	：</td><td>
					<span id="afamilyone"></span>&nbsp;&nbsp; 
					<span id="afamilytwo"></span>&nbsp; &nbsp;  
					<span id="afamilyId"> </span>
				</td></tr>
				<tr><td>开放用户类型：</td><td><span id="aavailableRole"></span></td></tr>
				<tr><td>推荐级别：</td><td><input type="text" name="tuijian" id="atuijian" maxlength="60"/></td></tr>
				</table>
				</div> 
				 <div style="clear: both;"></div>
				<input type="button"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" name="aup" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="aclose" value="取消">
			 </form>
			</div> 
		</div>
 <!--  修改 --> 
		<div style="display: none;position:absolute ;z-index:999; top:30px;left:50%;margin-left:-570px;background-color: #fff;border: 1px solid #aaa;" id="hid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
		  	<input type="button"  style="float:left;" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="fclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 编辑所选记录 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</div>
		  <div style="padding: 10px;">
		  	<form id="form1" method="post" enctype="multipart/form-data" action="${contextPath}/sys/tixiku/fileUpload">	 
				<input type="hidden" name="id" id="fid"/> 
				<input type="hidden" name="contenttop" id="fcontenttop"/>
				<input type="hidden" name="content" id="fcontent"/>
				<input type="hidden" name="contentbot" id="fcontentbot"/>
				<div style="float:left;">
				<table class="axtable">	
				<tr><td>名称	：</td><td><input type="text" name="name" id="fname" maxlength="60" /></td></tr>
				<tr><td>标题：</td><td><input type="text" name="title" id="ftitle" maxlength="200"style="width:978px;"/></td></tr> 
				<tr><td>内容简介1：</td><td> 
				 <script id="feditortop" name="contenttop" type="text/plain"> </script> 
				</td></tr>
				<tr><td>内容简介2：</td><td> 
				 <script id="feditor" name="content" type="text/plain"> </script> 
				</td></tr>
				<tr><td>内容简介3：</td><td> 
				 <script id="feditorbot" name="contentbot" type="text/plain"> </script> 
				</td></tr>
				<input type="hidden" name="image" id="image">
				<tr><td></td><td><span  id="fimage"></span> </td></tr> 
				<tr id="fimageh" style="display: none;"><td>上传图片：</td><td><input type='file' name='file' id='ffile'/> </td> </tr> 
				<tr><td>大分类：</td><td><span id="fclassifyId"> </span></td></tr>
				<tr><td>体系分类	：</td><td>
					<span id="ffamilyone"></span>&nbsp;&nbsp; 
					<span id="ffamilytwo"></span>&nbsp; &nbsp;  
					<span id="ffamilyId"> </span>
				</td></tr>
				<tr><td>开放用户类型：</td><td><span id="favailableRole"></span></td></tr> 
				<tr><td>推荐级别：</td><td><input type="text" name="tuijian" id="ftuijian" maxlength="60"/></td></tr>
				</table>
				</div> 
				<div style="clear: both;"></div>
				<input type="button" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="fclose" value="取消">
			 </form>
			 </div>
		</div>

<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row --> 
<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
 
 <script type="text/javascript"> 
 $("#aid").draggable();
 $("#hid").draggable();
</script>
<!-- page specific plugin scripts -->
  
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                //"${contextPath}/static/dist/js/jquery-ui.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                "${contextPath}/static/assets/js/jquery-ui.custom.js",
		        		"${contextPath}/static/assets/js/jquery.ui.touch-punch.js",
		        		"${contextPath}/static/assets/js/markdown/markdown.js",
		        		"${contextPath}/static/assets/js/markdown/bootstrap-markdown.js",
		        		"${contextPath}/static/assets/js/jquery.hotkeys.js", 
		        		"${contextPath}/static/assets/js/bootstrap-wysiwyg.js",
		        		"${contextPath}/static/assets/js/bootbox.js",
		        		"${contextPath}/static/assets/js/jquery.gritter.js",
		        		"${contextPath}/static/edit/ueditor.config.js",
		        		"${contextPath}/static/edit/ueditor.all.js",
		        		"${contextPath}/static/edit/lang/zh-cn/zh-cn.js",

		        		null ]; 
		
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		
        		var grid_selector = "#grid-table"; 
        		var pager_selector = "#grid-pager";

        		// resize to fit page size
        		$(window).on('resize.jqGrid', function() {
        			$(grid_selector).jqGrid('setGridWidth', $(".page-content").width());
        		});
        		// resize on sidebar collapse/expand
        		var parent_column = $(grid_selector).closest('[class*="col-"]');
        		$(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed) {
        			if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
        				// setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        				setTimeout(function() {
        					$(grid_selector).jqGrid('setGridWidth', parent_column.width());
        				}, 0);
        			}
        		});
        	
        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/tixiku/getTzzTixiku",
        			datatype : "json",
        			height :  650, 
        			colNames : ['','操作', 'ID', '课程体系名字','标题', '图片','大分类', '体系分类','开放用户类型(多个用英文逗号隔开)','推荐级别','点击次数','创建时间','','','','','','','',''],
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
        					editbutton : false,//delbutton : false,//disable delete button
        					delOptions : {
        						recreateForm : true,
        						beforeShowForm : beforeDeleteCallback
        					}
        					//editformbutton:true, editOptions:{recreateForm:true, beforeShowForm:beforeEditCallback}
        				}
        			}, {
        				name : 'id',
        				index : 'id',
        				label : '操作',
        				width : 50,
        				formatter: function (value, grid, rows, state) 
        					{
        					 return "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"${contextPath}/sys/sysuser/home#page/tixikucourse?txkdid=" + value + "\" style=\"color:#9ab\">课程 </a><br/><br/>"+
        							"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"${contextPath}/sys/sysuser/home#page/tixikucp?txkid=" + value + "\" style=\"color:#9ab\">教具 </a>";} ,
        				sorttype : "int",
        				search : false
        			},  {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sorttype : "int",
        				search : false
        			}, {
        				name : 'name',
        				index : 'name',
        				label : '课程体系名称',
        				width : 130,
        				editable : true,
        				editoptions : {size : "20", maxlength : "50"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'title',
        				index : 'title',
        				label : '标题',
        				width : 200,
        				editable : true,
        				search:false 
        			}, {
        				name : 'image',
        				index : 'image',
        				label : '图片',
        				width : 170,
        				editable : true, 
        				formatter:alarmFormatter,
        				search : false 
        			}, {
        				name : 'classifycn',
        				index : 'classifyid',
        				label : '大分类', 
        				width : 100,
        				editable : true,
        				edittype : "select", 
        				editoptions : {
        					dataUrl : "${contextPath}/sys/Tzzdic/getviptypeSelectList"///
        				},
        				search : false 
        			}, {
        				name : 'familycn',//'id','name''title''image','classifycn''familycn'
        				index : 'familyid',
        				label : '体系分类',
        				width : 100,
        				editable : true,
        				edittype : "select", 
        				editoptions : {
        					dataUrl : "${contextPath}/sys/Tzzdic/gettypeSelectList"///
        				},
        				search : false		
        			}, {
        				name : 'availableRolecn',
        				index : 'availableRolecn',
        				label : '开放用户类型',
        				width : 150,
        				editable : true,
        				editoptions : {size : "20", maxlength : "30"},  
        				search : false      		
        			}, {
        				name : 'tuijian',
        				index : 'tuijian',
        				label : '推荐级别',
        				width : 80,
        				editable : true,
        				editoptions : {size : "20", maxlength : "30"}, 
        				editrules : {number : true},
        				search : false       		
        			}, {
        				name : 'clickNum',
        				index : 'clickNum',
        				label : '点击次数',
        				width : 80, 
        				editoptions : {size : "20", maxlength : "30"},   
        				search : false       		
        			}, {
        				name : 'createTime',
        				index : 'createTime',
        				label : '创建时间',
        				width : 100,  
        				sorttype : "date",
        				search : false
        			}, { 
        				name : 'classifyId',
        				index : 'classifyId', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'familyId',
        				index : 'familyId', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'familyTwo',
        				index : 'familyTwo', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'familyOne',
        				index : 'familyOne', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'content',
        				index : 'content', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'availableRole',
        				index : 'availableRole', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'contenttop',
        				index : 'contenttop', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'contentbot',
        				index : 'contentbot', 
        				hidden : true  ,
        				search : false   ,
        				editable : false    			
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
        				setTimeout(function(){
        					styleCheckbox(table);
        					updateActionIcons(table);
        					updatePagerIcons(table);
        					enableTooltips(table);
        				}, 50);
        			},
        			editurl : "${contextPath}/sys/tixiku/operateTzzTixiku"
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
        		
        		$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/Tzzdic/getcoursecheckbox",
			        data:{},
			   		dataType:"html", 
			       	success:function(data){   
			       		$("#favailableRole").html(data); 
			       		$("#aavailableRole").html(data);    
			       	} 
			    });
                //=====================================================
                    	$('[data-rel=tooltip]').tooltip();
            			$('[data-rel=popover]').popover({html:true});
                    		//显示图片格式
            				function alarmFormatter(cellvalue, options, rowdata) {  
            					return '<img id="imagel" width="100px" height="100px" src="${contextPath}'+cellvalue+'" alt="' + cellvalue + '" />';
            				}
            				 
            				function showErrorAlert(reason, detail) {
			        			var msg = "";
			        			if (reason === "unsupported-file-type") {
			        				msg = "Unsupported format " + detail;
			        			} else {
			        				// console.log("error uploading file", reason, detail);
			        			}
			        			$("<div class='alert'> <button type='button' class='close' data-dismiss='alert'>&times;</button>" + "<strong>File upload error</strong> " + msg + " </div>").prependTo("#alerts");
			        		}
            			 
            				$.ajax({//classifycn
            			        type:"post", 
            			        url:"${contextPath}/sys/Tzzdic/getviptypeSelectList",
            			     
            			   		dataType:"html", 
            			       	success:function(data){   
            			       		$("#fclassifyId").html(data); 
            			       		$("#aclassifyId").html(data);    
            			       	} 
            			    });
            				$.ajax({//familyId 
            			        type:"post", 
            			        url:"${contextPath}/sys/Tzzdic/gettixikuSelectList",
            			        data:{},
            			   		dataType:"html", 
            			       	success:function(data){   
            			       		$("#ffamilyId").html(data); 
            			       		$("#afamilyId").html(data);    
            			       	} 
            			    });
            				 
            				$("#editaaa").click(function(){
            				 
            					var id=$('#grid-table').jqGrid('getGridParam','selrow');
            					if(id==null) {
            						alert("请选择记录！");
            						return;
            					}
            					var rowData = $('#grid-table').jqGrid('getRowData',id);
            					 
            					//'id','name''title''image','classifycn''familycn'
            					//'availableRolecn''tuijian''clickNum''createTime',
            					var id= rowData.id; 
        						var name = rowData.name; 
        						var title = rowData.title; 
        						var content = rowData.content; 
        						var contenttop = rowData.contenttop; 
        						var contentbot = rowData.contentbot; 
        						
        						var image=rowData.image;
        						var classifyId =rowData.classifyId;
        						
        						var familyId =rowData.familyId;
        						var availableRole = rowData.availableRole ;
        						var tuijian = rowData. tuijian;
        						var clickNum = rowData.clickNum ;
        						var familyOne=rowData.familyOne;
        			   			var familyTwo=rowData.familyTwo;
        			   			
            					$("#fid").val(id);
            					$("#fname").val(name);  
            					$("#ftitle").val(title);  
            					
            					$("#fclassifyId  select").val(classifyId);
            					$("#ffamilyId  select").val(familyId);  
            					$("#ftuijian").val(tuijian); 
            				 	$("#fclickNum").val(clickNum);  
            				 	for(var k=0;k<$("#favailableRole").find("input").length;k++){
            						$("#favailableRole").find("input").eq(k).prop("checked",false);
            						 
            					}
            					for(var i=0;i<$("#favailableRole").find("input").length;i++){
            						//alert(availableRole.split(",").length);
            						for(var j=0;j<availableRole.split(",").length;j++){ 
            							if($("#favailableRole").find("input").eq(i).val()==availableRole.split(",")[j]){
            							//	alert(i+"t"+j);
            								//alert($("#favailableRole").find("input").eq(i));
        	    							$("#favailableRole").find("input").eq(i).prop("checked",true);
         
        	    							break;
            							}else{
            							//	alert(i+"f"+j);
            								$("#favailableRole").find("input").eq(i).prop("checked",false);
            							} 
            						}  
            					}
            				 	$.ajax({
                			        type:"post", 
                			        url:"${contextPath}/sys/Tzzdic/getjilian",
                			        data:{"level":1,"did":0},
                			   		dataType:"html", 
                			       	success:function(data){    
                			       		$("#ffamilyone").html(data); 
                			       		$("#ffamilyone select").val(familyOne); 
                			       	} 
                			    });
                        		$.ajax({
                			        type:"post", 
                			        url:"${contextPath}/sys/Tzzdic/getjilian",
                			        data:{"level":2,"did":familyOne},
                			   		dataType:"html", 
                			       	success:function(data){  
                			       		$("#ffamilytwo").html(data);  
                                		$("#ffamilytwo select").val(familyTwo);
                			       	} 
                			    });
                        		$.ajax({
                			        type:"post", 
                			        url:"${contextPath}/sys/Tzzdic/getjilian",
                			        data:{"level":3,"did":familyTwo},
                			   		dataType:"html", 
                			       	success:function(data){    
                			       		$("#ffamilyId").html(data);  
                			       		$("#ffamilyId select").val(familyId);
                			       	} 
                			    }); 
                        		
                        		$("#ffamilyone").on("change", "select",function(){
                    				// alert($("#afamilyone select").val());
                    					if($("#ffamilyone select").val()=="请选择分类"){ 
                    						$("#ffamilytwo").html("<select><option>请选择分类</option></select>"); 
                        					$("#ffamilyId").html("<select><option>请选择分类</option></select>");
                        					return;
                    					}
                    					$.ajax({
                        			        type:"post", 
                        			        url:"${contextPath}/sys/Tzzdic/getjilian",
                        			        data:{"level":2,"did":$("#ffamilyone select").val()},
                        			   		dataType:"html", 
                        			       	success:function(data){    
                        			       		$("#ffamilytwo").html(data); 
                        			       		$("#ffamilyId").html("<select><option>请选择分类</option></select>"); 
                        			       	} 
                        			    });  
                    				});
                        		$("#ffamilytwo").on("change", "select",function(){
                					//alert($("#afamilyone select").val()); 
                					if($("#ffamilytwo select").val()=="请选择分类"){
                						$("#ffamilyId").html("<select><option>请选择分类</option></select>");
                						return;
                					}
                					$.ajax({
                    			        type:"post", 
                    			        url:"${contextPath}/sys/Tzzdic/getjilian",
                    			        data:{"level":3,"did":$("#ffamilytwo select").val()},
                    			   		dataType:"html", 
                    			       	success:function(data){    
                    			       		$("#ffamilyId").html(data);    
                    			       	} 
                    			    });
                				});
            					$("#fimage").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br><span style='cursor: pointer;'>"+image+"</span>"); 
            					  
            					$("#hid").css("display","block");  
            					var uee1 = UE.getEditor('feditortop', {
        				            autoHeight: false,
        				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
        				            initialFrameHeight:445  //初始化编辑器高度,默认320
        				        }) ;
            					uee1.ready(function() { 
            						uee1.setContent(contenttop);  
            					}); 
            					var uee2 = UE.getEditor('feditor', {
        				            autoHeight: false,
        				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
        				            initialFrameHeight:445  //初始化编辑器高度,默认320
        				        }) ;
            					uee2.ready(function() { 
            						uee2.setContent(content);  
            					}); 
            					var uee3 = UE.getEditor('feditorbot', {
        				            autoHeight: false,
        				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
        				            initialFrameHeight:445  //初始化编辑器高度,默认320
        				        }) ;
            					uee3.ready(function() { 
            						uee3.setContent(contentbot);  
            					}); 
            					$("#aid").css("display","none"); 
            				});
            				$("#addaaa").click(function(){ 
            					for(var i=0;i<$("#aavailableRole").find("input").length;i++){
            						$("#aavailableRole").find("input").eq(i).removeAttr("checked"); 
            					}
            					$("#aname").val(""); 
            					$("#atitle").val(""); 
            					$("#acontent").val("");
            					$("#aclassifyId").val("");
            					$("#afamilyId").val(""); 
            					$("#atuijian").val(""); 
            				 	$("#aclickNum").val("");   
            					$("#aid").css("display","block");  
            					$("#hid").css("display","none"); 
            					$("#afamilytwo").html("<select><option>请选择分类</option></select>"); 
            					$("#afamilyId").html("<select><option>请选择分类</option></select>");  
            					var uea1 = UE.getEditor('aeditortop', {
        				            autoHeight: false,
        				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
        				            initialFrameHeight:445  //初始化编辑器高度,默认320
        				        });
            					var uea2 = UE.getEditor('aeditor', {
        				            autoHeight: false,
        				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
        				            initialFrameHeight:445  //初始化编辑器高度,默认320
        				        });
            					var uea3 = UE.getEditor('aeditorbot', {
        				            autoHeight: false,
        				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
        				            initialFrameHeight:445  //初始化编辑器高度,默认320
        				        });
            					$.ajax({
        	    			        type:"post", 
        	    			        url:"${contextPath}/sys/Tzzdic/getjilian",
        	    			        data:{"level":1,"did":0,"newfl":"new"},
        	    			   		dataType:"html", 
        	    			       	success:function(data){    
        	    			       		$("#afamilyone").html(data);    
        	    			       	} 
            			    	});
            				});
            				$("#afamilyone").on("change", "select",function(){
                				// alert($("#afamilyone select").val());
                					if($("#afamilyone select").val()=="请选择分类"){ 
                						$("#afamilytwo").html("<select><option>请选择分类</option></select>"); 
                    					$("#afamilyId").html("<select><option>请选择分类</option></select>");
                    					return;
                					}
                					$.ajax({
                    			        type:"post", 
                    			        url:"${contextPath}/sys/Tzzdic/getjilian",
                    			        data:{"level":2,"did":$("#afamilyone select").val()},
                    			   		dataType:"html", 
                    			       	success:function(data){    
                    			       		$("#afamilytwo").html(data); 
                    			       		$("#afamilyId").html("<select><option>请选择分类</option></select>"); 
                    			       	} 
                    			    });  
                				});
                				$("#afamilytwo").on("change", "select",function(){
                					//alert($("#afamilyone select").val()); 
                					if($("#afamilytwo select").val()=="请选择分类"){
                						$("#afamilyId").html("<select><option>请选择分类</option></select>");
                						return;
                					}
                					$.ajax({
                    			        type:"post", 
                    			        url:"${contextPath}/sys/Tzzdic/getjilian",
                    			        data:{"level":3,"did":$("#afamilytwo select").val()},
                    			   		dataType:"html", 
                    			       	success:function(data){    
                    			       		$("#afamilyId").html(data);    
                    			       	} 
                    			    });
                				});
            //===========================================================================================	
            				$("#fimage").click(function(){
            					$(this).html(""); 
            					$("#fimageh").css("display","block");
            					
            				});
            				//=================提交  
            				$("[name='up']").click(function(){  
            					
            					var contenttop= UE.getEditor('feditortop').getContent();
            					var content= UE.getEditor('feditor').getContent();
            					var contentbot= UE.getEditor('feditorbot').getContent();
            					$("#fcontenttop").val(contenttop);
            					$("#fcontent").val(content);
            					$("#fcontentbot").val(contentbot);
            					 $("#form1").submit();
            				});
            				$("[name='aup']").click(function(){  
            				
            					var contenttop=  UE.getEditor('aeditortop').getContent();
            					var content=  UE.getEditor('aeditor').getContent();
            					var contentbot=  UE.getEditor('aeditorbot').getContent();
            					$("#acontenttop").val(contenttop);
            					$("#acontent").val(content);
            					$("#acontentbot").val(contentbot);
            					if($("#afile").val()==""){
            						alert("请选择图片！");
            						return;
            					} 
            					 $("#form2").submit();
            				});
            				//======================关闭
            				$("[name='fclose']").click(function(){
            					$("#hid").css("display","none");
            				});
            				$("[name='aclose']").click(function(){
            					$("#aid").css("display","none");
            				});
            //===========================================================================================
                		
        		
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
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
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
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
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
			    	   var label = $(grid_selector).jqGrid('getGridParam','colNames');
   			    	   for(var i = 2 ; i < label.length; i++){
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
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/tixiku/operateTzzTixiku?oper=excel' method='post'>";
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
        			var buttons = form.next().find('.EditButton .fm-button');
        			buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();// ui-icon, s-icon
        			buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
        			buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>');

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
        			var buttons = dialog.find('.EditTable');
        			buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
        			buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
        			buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
        		}

        		function beforeDeleteCallback(e) {
        			var form = $(e[0]);
        			if (form.data('styled'))
        				return false;
        			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
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
        				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        				if ($class in replacement)
        					icon.attr('class', 'ui-icon ' + replacement[$class]);
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
