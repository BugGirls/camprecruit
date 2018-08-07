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
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
		</script>
<!-- 添加  -->
 		<div style="display: none;position:absolute ;z-index:999;top:20%;left:30%;background-color: #fff;border: 1px solid #aaa;" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;">&nbsp;&nbsp;添加记录</div>
		  <div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/TzzCourse/fileUpload">  	 
				<div style="float:left;"> 
				<table class="axtable">
				<tr>	
				<td>名称：</td><td><input type="text" name="name" id="aname" maxlength="60"/></td>
				</tr> 
				<tr>	
				<td>标题：</td><td><input type="text" name="title" id="atitle" maxlength="60"/></td>
				</tr> 
				<tr>
				 <input type="hidden" name="image" id="image">  
				  	<br>
				<td>
				
				 上传图片：</td><td> <input type='file' name='file' id='afile'/> 
				
				 </td>
				 </tr>
				<tr>
				<td>价格：</td><td><input type="text" name="price" id="aprice" maxlength="60"/></td>
				</tr>
				<tr><td>维护费：</td><td><input type="text" name="weihufei" id="aweihufei" maxlength="60"/></td></tr> 
				<tr><td>上传文档：</td><td><input type="file" name="href" id="ahref" maxlength="60"/></td></tr> 
				<tr><td>适宜人数：</td>
					<td>
						<select name="people" id="apeople">
					 		<option value="5">个人类</option>
					 		<option value="6">两人合作类</option>
					 		<option value="7">3-5人</option>
					 		<option value="8">5-10人</option>
					 		<option value="9">10-20人</option> 
					 		<option value="10">20人以上</option> 
				 		</select>
				 	</td>
				</tr>
				<tr><td>项目器材：</td><td><textarea rows="4" cols="25"  name="beijing" id="abeijing" maxlength="255"></textarea></td></tr>
				
				</table>
				</div>
				<div style="float:left; padding-left: 20px;"> 
				<table class="axtable">
				<tr><td>安全把控：</td><td><textarea rows="4" cols="25" name="neirong" id="aneirong" maxlength="255"></textarea></td></tr>
				<tr><td>推荐等级：</td><td><input type="text" name="tuijian" id="atuijian" maxlength="2"/></td></tr>
				 
				<tr><td>课程分类	：</td><td>
					<span id="afamilyone"></span>&nbsp;&nbsp; 
					<span id="afamilytwo"></span>&nbsp; &nbsp;  
					<span id="afamilyId"> </span>
				</td></tr>
				 <tr><td>课程类型 ：</td><td><span id="aclassifyId"> </span></td></tr>
				<tr><td>开放用户类型：</td><td><span id="aavailableRole"></span></td></tr>
				<tr><td>班级	：</td><td><span id="adifficultyId"> </span></td></tr>
				<tr><td>制作人 ：</td><td><span id="asysuserName"> </span></td></tr>
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
				<div style="float:left;"> 	
				<table class="axtable">
				<tr><td>名称：</td><td><input type="text" name="name" id="fname" maxlength="60"/></td></tr>
				<tr><td>标题：</td><td><input type="text" name="title" id="ftitle" maxlength="60"/></td></tr> 
				<tr id="fimage" >  </tr>
				
				<tr id="fimageh" style="display: none;"> 
					<td>上传图片：</td><td><input type='file' name='file' id='ffile'/></td>
				</tr>
				<tr><td>原文档：</td><td id="wendangyulan"><a href=""  ></a> </td></tr> 
				<tr><td>上传文档：</td><td><input type="file" name="href" id="fhref" maxlength="60"/></td></tr> 
				
				<tr><td>价格：</td><td><input type="text" name="price" id="fprice" maxlength="60"/></td></tr>
				<tr><td>维护费：</td><td><input type="text" name="weihufei" id="fweihufei" maxlength="60"/></td></tr>
				<tr><td>安全把控：</td><td><textarea rows="4" cols="25" name="neirong" id="fneirong" maxlength="255"></textarea></td></tr>
				 </table>
				</div>
				 <div style="float:left; padding-left: 20px;"> 
				 <table class="axtable">
				<tr><td>适宜人数：</td>
					<td>
				 	<select name="people" id="fpeople">
				 		<option value="5">个人类</option>
				 		<option value="6">两人合作类</option>
				 		<option value="7">3-5人</option>
				 		<option value="8">5-10人</option>
				 		<option value="9">10-20人</option> 
				 		<option value="10">20人以上</option> 
				 	</select> 
					</td>
				</tr>	
				<tr><td>项目器材：</td><td><textarea rows="4" cols="25"name="beijing" id="fbeijing" maxlength="255"></textarea></td></tr>
				<tr><td>推荐等级：</td><td><input type="text" name="tuijian" id="ftuijian" maxlength="2"/></td></tr>
				<tr><td>分类：</td><td>
					<span id="ffamilyone"></span>&nbsp;&nbsp; 
					<span id="ffamilytwo"></span>&nbsp; &nbsp;  
					<span id="ffamilyId"> </span>
				</td></tr>
				<tr><td>课程类型：</td><td><span id="fclassifyId"> </span></td></tr>
				<tr><td>开放用户类型（多个用逗号隔开）：</td><td><span id="favailableRole" ></span></td></tr>
				<tr><td>班级：</td><td><span id="fdifficultyId"> </span></td></tr>
				<tr><td>制作人：</td><td><span id="fsysuserName"> </span></td></tr>
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
        			url : "${contextPath}/sys/TzzCourse/getCourse",
        			datatype : "json",
        			height : 650, 
        			colNames : ['','添加课件', 'ID', '课程名称','图片','文档', '课程标题', '课程价格','维护费', '项目器材','安全把控', '适宜人数','适宜人数', '推荐级别', '点击次数', '课程类别', '班级', '课程类型','开放用户类型（多个用逗号隔开）','作者名字','添加时间','','','','','',''],
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
        				label : '添加课件',
        				width : 60,
        				formatter: function (value, grid, rows, state) 
        					{
        					 return "<a href=\"${contextPath}/sys/sysuser/home#page/showCourseWare?cid=" + value + "\" style=\"color:#9ab\"> 查看详情 </a>" +
        					 "<br/><br/><a href=\"${contextPath}/sys/sysuser/home#page/coursezhekou?cid=" + value + "\" style=\"color:#9ab\">&nbsp;&nbsp;&nbsp;折&nbsp;&nbsp;扣 </a>" ;
        					  
        					 } ,
        				sorttype : "int",
        				search : false
        			},  {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 80,
        				sorttype : "int",
        				search : false
        			}, {
        				name : 'name',
        				index : 'name',
        				label : '课程名称',
        				width : 280,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true},
        				search : false
        			}, {
        				name : 'image',
        				index : 'image',
        				label : '图片', 
        				width : 250,
        				editable : true,
        				search:false,
        				formatter:alarmFormatter,
        				search : false 
        			}, {
        				name : 'href',
        				index : 'href',
        				label : '文档',
        				width : 300,
        				editable : true, 
        				editoptions : {size : "20", },
        				search : false
        			}, {
        				name : 'title',
        				index : 'title',
        				label : '课程标题',
        				width : 300,
        				editable : true,
        				searchoptions : {sopt : ['cn']},
        				editoptions : {size : "20", title : "标题长度不对"},
        				 
        			}, {
        				name : 'price',
        				index : 'price',
        				label : '课程价格',
        				width : 100,
        				editable : true,
        				sorttype : "float",
        				editoptions : {size : "12", maxlength : "25"},
        				searchoptions : {sopt : ['eq']},
        				editrules : {number : true},
        				search : false
        			}, {
        				name : 'weihufei',
        				index : 'weihufei',
        				label : 'weihufei',
        				width : 100,
        				editable : true,
        				sorttype : "float",
        				editoptions : {size : "12", maxlength : "20"}, 
        				editrules : {number : true},
        				search : false
        			} , {
        				name : 'beijing',
        				index : 'beijing',
        				label : '项目器材',
        				width : 280,
        				editable : true,
        				sorttype :  {size : "12", maxlength : "255"},
        				search : false    			
        			}  , {
        				name : 'neirong',
        				index : 'neirong',
        				label : '安全把控',
        				width : 280,
        				editable : true,
        				sorttype :  {size : "12", maxlength : "255"},
        				search : false    			
        			}  , {
        				name : 'peoplecn',
        				index : 'peoplecn',
        				label : '适宜人数',
        				width : 280,
        				editable : true,
        				searchoptions : {sopt : ['cn']},
        				sorttype :  {size : "12", maxlength : "255"},
        				search : true   			
        			}  , {
        				name : 'people',
        				index : 'people',
        				label : '适用人群',
        				hidden : true  ,
        				search : false  ,
        				editable : false    		
        			} , {
        				name : 'tuijian',
        				index : 'tuijian',
        				label : '推荐级别',
        				width : 50,
        				editable : true,
        				sorttype :  {size : "2", maxlength : "2"},
        				search : false	 			
        			} , {
        				name : 'clickNum',
        				index : 'clickNum',
        				label : '点击次数',
        				width : 100,
        				sorttype : "int",
        				editable : true,
        				search : false	
        			} , {
        				name : 'familyIdcn',
        				index : 'familyId',
        				label : '课程类别对应的字典',
        				width : 100,
        				editable : true,
        				edittype : "select",
        				searchoptions : {sopt : ['cn']},
        				editoptions : {
        					dataUrl : "${contextPath}/sys/TzzCourse/getDictSelectList"
        				} ,
        				search : false	 
        			} , {
        				name : 'difficultyIdcn',
        				index : 'difficultyId',
        				label : '班级对应的字典',
        				width : 100,
        				editable : true,
        				editoptions : {
        					dataUrl : "${contextPath}/sys/Tzzdic/getDifficultSelectList"
        				},
        				edittype : "select",
        				searchoptions : {sopt : ['cn']} ,
        				search : false	 
        			} , {
        				name : 'classifyIdcn',
        				index : 'classifyId',
        				label : '大类',
        				width : 100,
        				editable : true,
        				editoptions : {
        				  dataUrl : "${contextPath}/sys/Tzzdic/getviptypeSelectList"
        				},
        				edittype : "select" ,
        				search : false	 
        			}, {
        				name : 'availableRolecn',
        				index : 'availableRolecn',
        				label : '开放用户类型 ',
        				width : 200,
        				editable : true,
        				sorttype : {size : "12", maxlength : "30"},
        				search : false  
        			}, {
        				name : 'sysuserName',
        				index : 'sysuserName',
        				label : '作者名字',
        				width : 100,
        				editable : true,
        				edittype : "select",
        				editoptions : {
        					dataUrl : "${contextPath}/sys/TzzCourse/getSysUserList"
        				}  ,
        				searchoptions : {sopt : ['cn']}			
        			}, {
        				name : 'createTime',
        				index : 'createTime',
        				label : '添加时间',
        				width : 150,
        				sorttype : 'date',
        				unformat : pickDate,
        				search : false   ,
        				editable : false    			
        			}, {
        				name : 'difficultyId',
        				index : 'difficultyId', 
        				hidden : true  ,
        				search : false  ,
        				editable : false    			
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
        				name : 'availableRole',
        				index : 'availableRole', 
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
        			}],
        	
        		
        		//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
        			sortname : "id",
        			sortorder : "asc",
        			shrinkToFit:false,  
        			autoScroll: true, 
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
        				}, 0);
        			},
        			editurl : "${contextPath}/sys/TzzCourse/operateCourse"
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
    				//${contextPath}/sys/Tzzdic/getviptypeSelectList"
    				//多选按钮
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
    				$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/TzzCourse/getDictSelectList",
    			        data:{},
    			   		dataType:"html", 
    			       	success:function(data){   
    			       		$("#ffamilyId").html(data); 
    			       		$("#afamilyId").html(data);    
    			       	} 
    			    });
    				$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/TzzCourse/getSysUserList",
    			        data:{},
    			   		dataType:"html", 
    			       	success:function(data){   
    			       		$("#fsysuserName").html(data); 
    			       		$("#asysuserName").html(data);    
    			       	} 
    			    });
    				
    				
    				$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/Tzzdic/getDifficultSelectList",
    			        data:{},
    			   		dataType:"html", 
    			       	success:function(data){   
    			       		$("#fdifficultyId").html(data); 
    			       		$("#adifficultyId").html(data);    
    			       	} 
    			    }); 
    				$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/Tzzdic/getviptypeSelectList",
    			        data:{},
    			   		dataType:"html", 
    			       	success:function(data){   
    			       		$("#fclassifyId").html(data); 
    			       		$("#aclassifyId").html(data);    
    			       	} 
    			    });
    				$("#editaaa").click(function(){
    					$("#aid").css("display","none"); 
    					var id=$('#grid-table').jqGrid('getGridParam','selrow');
    					if(id==null) {
    						alert("请选择记录！");
    						return;
    					}
    					var rowData = $('#grid-table').jqGrid('getRowData',id);
    					//name 简介brief 内容介绍content 成本价costPrice 金额amount 类型categoryName 
            			//图片image 型号xinghao 规格guige 材质caizhi 品牌pinpai 产地chandi 单位danwei 点击浏览次数clickNum 销量sales 热销rexiaoName
    					
    					
    					var id= rowData.id; 
						var name = rowData.name; 
						var title = rowData.title;
						var price = rowData.price;
						var href = rowData.href;
						var weihufei=rowData.weihufei; 
						var people =rowData.people; 
						var beijing =rowData.beijing; 
						var neirong =rowData.neirong; 
						var tuijian = rowData.tuijian ;
						var image= rowData.image ;
						var familyId = rowData.familyId ;
						var classifyId = rowData.classifyId ;
						var difficultyId=rowData.difficultyId ;
						var sysuserName  = rowData.sysuserName ;
			   			var availableRole=rowData.availableRole;
			   			//alert("ava"+availableRole);
			   			var familyOne=rowData.familyOne;
			   			var familyTwo=rowData.familyTwo;
						
			   			
			   			$("#wendangyulan").html("<a target='_blank' href='${contextPath}/sys/pdfview/getpdf?href="+href+"'>"+href+"</a>");
			   			
    					$("#fid").val(id);
    					$("#fneirong").val(neirong); 
    					$("#fbeijing").val(beijing);
    					$("#fname").val(name);
    					$("#ftitle").val(title);
    					$("#fprice").val(price); 
    					$("#fweihufei").val(weihufei);  
    					$("#fpeople").val(people); 
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
    					 
    					$("#ftuijian").val(tuijian);  
    					$("#fclassifyId select").val(classifyId);
    					$("#fdifficultyId  select").val(difficultyId);
    					$("#fsysuserName select").val(sysuserName); 
    					
    					
    					 //=============================级联=============================
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
                		//=============================级联结束=============================
    					$("#fimage").html("<td></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br><span style='cursor: pointer;'>"+image+"</span></td>"); 
    					 
    					 
    					$("#hid").css("display","block");  
    				});
    				$("#addaaa").click(function(){  
    					for(var i=0;i<$("#aavailableRole").find("input").length;i++){
    						$("#aavailableRole").find("input").eq(i).removeAttr("checked");
    						 
    					}
    					$("#hid").css("display","none");  
    					$("#aname").val("");
    					$("#abeijing").val("");
    					$("#aneirong").val("");
    					$("#atitle").val("");
    					$("#aprice").val(""); 
    					$("#aweihufei").val("");  
    					$("#apeople").val(""); 
    					//$("#aavailableRole").val("");   
    					$("#atuijian").val("");
    					$("#afamilyId").val("");
    					$("#aclassifyId").val("");
    					$("#adifficultyId").val("");
    					$("#asysuserName").val("");   
    					$("#aid").css("display","block"); 
    					$("#hid").css("display","none");  
    					$("#afamilytwo").html("<select><option>请选择分类</option></select>"); 
    					$("#afamilyId").html("<select><option>请选择分类</option></select>");  
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
    					$(this).html("<td></td><td></td>"); 
    					$("#fimageh").css("display","");
    					
    				});
    				//=================提交  
    				$("#up").click(function(){ 
    					if($("#fname").val()==""){
   						 	alert("请输入名字！");
   						 	return;
   						}  
    				 	if($("#ffamilytwo select").val()=="请选择分类"){
    						 alert("请选择详细分类！");
    						 return;
    					 } 
    				 	var regk=new RegExp("^(([0-9]+[\.]?[0-9]{1,2})|[0-9])$");   
        			    if(!regk.test($("#fprice").val())){  
        			        alert("请输入价格!");  
        			        return ;
        			    }
        			    if(!regk.test($("#fweihufei").val())){  
        			        alert("请输入维护费!");  
        			        return ;
        			    }
    				 	if($("#ftuijian").val()==""){
    					 alert("请输入推荐级别！");
						 return;
    					}
    				 	if($("#ffamilyId select").val()=="请选择分类"){
    						$("#ffamilyId select").val("");
    					} 
    					 $("#form1").submit();
    				});
    				
    				
    				$("#aup").click(function(){ 
    					if($("#aname").val()==""){
    						 alert("请输入名字！");
    						 return;
    					}
    					var regk=new RegExp("^[0-9]+(.[0-9]{2})?$");   
        			    if(!regk.test($("#aprice").val())){  
        			        alert("请输入价格!");  
        			        return ;
        			    }
        			    if(!regk.test($("#aweihufei").val())){  
        			        alert("请输入维护费!");  
        			        return ;
        			    }
        			    
    					 if($("#afamilytwo select").val()=="请选择分类"){
    						 alert("请选择详细分类！");
    						 return;
    					 } 
    					 if($("#atuijian").val()==""){
        					 alert("请输入推荐级别！");
    						 return;
        					}
    					if($("#afile").val()==""){
    						alert("请选择图片！");
    						return;
    					} 
    					if($("#afamilyId select").val()=="请选择分类"){
    						$("#afamilyId select").val("");
    					} 
    					 $("#form2").submit();
    				});
    				//======================关闭
    				$("#fclose").click(function(){
    					$("#hid").css("display","none");
    					$("#fimageh").css("display","none");
    				});
    				$("#aclose").click(function(){
    					$("#fimageh").css("display","none");
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
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
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
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
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
			    	   //var label = $(grid_selector).jqGrid('getGridParam','colNames');
			    	   
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
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/dict/operateDict?oper=excel' method='post'>";
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
        		