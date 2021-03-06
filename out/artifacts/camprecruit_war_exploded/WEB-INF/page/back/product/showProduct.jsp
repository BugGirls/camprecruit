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
			
			function addUploadFile(obj){
				if(index > 2){
					alert("您最多能添加四张图片");
				}else{	
					var parentEl = document.getElementById("aimageh" + index);
					index = index + 1;
					var htmlspan = document.createElement("span");
					htmlspan.setAttribute("id","aimageh" + index);
					var htmlinput = document.createElement("input");
					htmlinput.setAttribute("type","file");
					htmlinput.setAttribute("name","file"+index);
					var htmlbr = document.createElement("br");
					htmlspan.innerHTML = "上传图片：";
					htmlspan.appendChild(htmlinput);
					htmlspan.appendChild(htmlbr);
					insertAfter(htmlspan, parentEl);
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
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/TzzProduct/fileUpload">  
			<input type="hidden" id="addContent" name="addContent"/>
			<div style="float:left;"> 
				<div style="float:left;"> 		
		  		产品名称	&nbsp;<input type="text" name="name" id="aname" maxlength="60"/><br><br>
				简介	&nbsp;<input type="text" name="brief" id="abrief" maxlength="255"/><br><br>
				
				成本价&nbsp;<input type="text" name="costPrice" id="acostPrice" maxlength="255"/><br><br>
				金额	&nbsp;<input type="text" name="amount" id="aamount" maxlength="255"/><br><br>
				 
				<!--   类型	&nbsp;<span id="acategory"> </span>-->
				产品类型&nbsp;	：
					<span id="afamilyone"></span>&nbsp;&nbsp; 
					<span id="afamilytwo"></span>&nbsp; &nbsp;  
					<span id="afamilyId"> </span>
				
				
				<br/><br/>
				<span id="aimageh0">
				上传图片	&nbsp;	<input type='file' name='file' id='afile'/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
				</span>
				<input type="button" value="添加输入框" onclick = "addUploadFile(this)"/>
				
				</div>
				 <div style="float:left; padding-left: 20px;">
				型号	&nbsp;<input type="text" name="xinghao" id="axinghao" maxlength="255"/><br><br>
				规格	&nbsp;<input type="text" name="guige" id="aguige" maxlength="255"/><br><br>
				材质	&nbsp;<input type="text" name="caizhi" id="acaizhi" maxlength="255"/><br><br>
				品牌	&nbsp;<input type="text" name="pinpai" id="apinpai" maxlength="255"/><br><br>
				产地	&nbsp;<input type="text" name="chandi" id="achandi" maxlength="255"/><br><br>
				单位	&nbsp;<input type="text" name=danwei id="adanwei" maxlength="255"/><br><br> 
				热销	<select id="arexiao" name="rexiao" >
						<option value="0">普通</option>
						<option value="1">热销</option>
					</select><br><br> 
				 </div>
				 </div>
				 <div style="clear:both;"></div>
				 <div>
				 内容介绍：
				 <script id="acontent" name="contenttop" type="text/plain"> </script> <br><br>
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
		  	<form id="form1" method="post" enctype="multipart/form-data" action="${contextPath}/sys/TzzProduct/fileUpload">			
				<input type="hidden" name="id" id="fid"/><br><br>
				<input type="hidden" id="editContent" name="addContent"/>
				<div style="float:left">
				<div style="float:left;"> 	
				产品名称	&nbsp;<input type="text" name="name" id="fname" maxlength="60"/><br><br>
				简介	&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="brief" id="fbrief" maxlength="255"/><br><br>
				成本价&nbsp;&nbsp;&nbsp;<input type="text" name="costPrice" id="fcostPrice" maxlength="255"/><br><br>
				金额	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="amount" id="famount" maxlength="255"/><br><br>
				 
				<!--  类型	&nbsp;<span id="fcategory"> </span><br><br> -->
				产品类型&nbsp;	：
					<span id="ffamilyone"></span>&nbsp;&nbsp; 
					<span id="ffamilytwo"></span>&nbsp; &nbsp;  
					<span id="ffamilyId"> </span>
				<br/><br/>
				<input type="hidden" name="image" id="image">
				<span  id="fimage"> 
				</span> 
				<span  id="fimage1"> 
				</span>
				<span  id="fimage2"> 
				</span>
				<span  id="fimage3"> 
				</span>
				</div>
				<div style="float:left; padding-left: 100px;"> 	
				型号	&nbsp;<input type="text" name="xinghao" id="fxinghao" maxlength="255"/><br><br>
				规格	&nbsp;<input type="text" name="guige" id="fguige" maxlength="255"/><br><br>
				材质	&nbsp;<input type="text" name="caizhi" id="fcaizhi" maxlength="255"/><br><br>
				品牌	&nbsp;<input type="text" name="pinpai" id="fpinpai" maxlength="255"/><br><br>
				产地	&nbsp;<input type="text" name="chandi" id="fchandi" maxlength="255"/><br><br>
				单位	&nbsp;<input type="text" name=danwei id="fdanwei" maxlength="255"/><br><br> 
				热销	<select id="frexiao" name="rexiao" >
						<option value="0">普通</option>
						<option value="1">热销</option>
					</select><br><br> 
				</div>
				</div>
				<div style="clear:both;"></div>
				<div>
				内容简介：
				 <script id="fcontent" name="contenttop" type="text/plain"> </script> <br><br>
				 </div>
				<div style="clear: both;"></div>
				<input type="button" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="fclose" value="取消">
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
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                "${contextPath}/static/edit/ueditor.config.js",
		        		"${contextPath}/static/edit/ueditor.all.js",
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

        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/TzzProduct/getProductList",
        			datatype : "json",
        			height : 650,
        			colNames : ['', 'ID', '产品名称', '产品简介', '内容介绍', '成本价','金额', '类型', '图片','图片1','图片2','图片3','型号','规格','材质','品牌','产地','单位','点击浏览次数','销量','热销程度','产品类型1','产品类型1','产品类型ID'],
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
        				name : 'name',
        				index : 'name',
        				label : '产品名称',
        				width : 130,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'brief',
        				index : 'brief',
        				label : '简介',
        				width : 180,
        				editable : true,
        				editoptions : {size : "20", maxlength : "255"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'content',
        				index : 'content',
        				label : '内容介绍',
        				width : 200,
        				editable : true,
        				search : false,
        				editoptions : {size : "20"},
        				editrules : {required : true}
        			}, {
        				name : 'costPrice',
        				index : 'costPrice',
        				label : '成本价',
        				width : 80,
        				editable : true,
        				search : false
        			}, {
        				name : 'amount',
        				index : 'amount',
        				label : '金额',
        				width : 80,
        				editable : true,
        				search : false
        			},{
        				name : 'categoryName',
        				index : 'categoryName',
        				label : '类型',
        				width : 100,
        				editable : true,
        				edittype : "select",
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
        			},  {
        				name : 'image1',
        				index : 'image1',
        				label : '图片1', 
        				width : 250,
        				editable : true,
        				search:false,
        				formatter:alarmFormatter,
        				hidden : true 
        			},  {
        				name : 'image2',
        				index : 'image2',
        				label : '图片2', 
        				width : 250,
        				editable : true,
        				search:false,
        				formatter:alarmFormatter,
        				hidden : true 
        			},  {
        				name : 'image3',
        				index : 'image3',
        				label : '图片3', 
        				width : 250,
        				editable : true,
        				search:false,
        				formatter:alarmFormatter,
        				hidden : true 
        			}, {
        				name : 'xinghao',
        				index : 'xinghao',
        				label : '型号',
        				width : 110,
        				editable : true,
        				search : false
        					
        			}, {
        				name : 'guige',
        				index : 'guige',
        				label : '规格',
        				width : 110  ,
        				editable : true,
        				search : false			
        			}, {
        				name : 'caizhi',
        				index : 'caizhi',
        				label : '材质',
        				width : 110 ,
        				editable : true,
        				search : false 			
        			}, {
        				name : 'pinpai',
        				index : 'pinpai',
        				label : '品牌',
        				width : 110  ,
        				editable : true,
        				search : false			
        			}, {
        				name : 'chandi',
        				index : 'chandi',
        				label : '产地',
        				width : 110  ,
        				editable : true,
        				search : false			
        			}, {
        				name : 'danwei',
        				index : 'danwei',
        				label : '单位',
        				width : 110 ,
        				editable : true,
        				search : false 			
        			}, {
        				name : 'clickNum',
        				index : 'clickNum',
        				label : '点击浏览次数',
        				width : 100 ,
        				editable : false,
        				search : false 			
        			}, {
        				name : 'sales',
        				index : 'sales',
        				label : '销量',
        				width : 80  ,
        				editable : false,
        				search : false			
        			}, {
        				name : 'rexiaoName',
        				index : 'rexiao',
        				label : '热销',
        				width : 70  ,
        				editable : true,
        				edittype : "select",
        				editoptions : {value : "0:普通;1:热销"},
        				search : false		
        			}//name 简介brief 内容介绍content 成本价costPrice 金额amount 类型categoryName 
        			//图片image 型号xinghao 规格guige 材质caizhi 品牌pinpai 产地chandi 单位danwei 点击浏览次数clickNum 销量sales 热销rexiaoName
					,{
        				name : 'familyOne',
        				index : 'familyOne',
        				label : '产品类型1',
        				width : 80  ,
        				editable : false,
        				hidden:true,
        				search : false			
        			},{
        				name : 'familyTwo',
        				index : 'familyTwo',
        				label : '产品类型2',
        				width : 80  ,
        				editable : false,
        				hidden:true,
        				search : false			
        			},{
        				name : 'familyId',
        				index : 'familyId',
        				label : '产品类型ID',
        				width : 80  ,
        				editable : false,
        				hidden:true,
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
        			editurl : "${contextPath}/sys/TzzProduct/operateProduct"
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
        		/*
				$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/Tzzdic/getproSelectList",
			        data:{},
			   		dataType:"json", 
			       	success:function(data){   
			       		$("#fcategory").html(data.d); 
			       		$("#acategory").html(data.d);    
			       	} 
			    });
			    */
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
					var name=rowData.name;
					var brief=rowData.brief;
					var content=rowData.content;
					var costPrice=rowData.costPrice;
					var amount=rowData.amount;
					
					var image=rowData.image;
					var image1=rowData.image1;
					var image2=rowData.image2;
					var image3=rowData.image3;
					var xinghao=rowData.xinghao;
					var guige=rowData.guige;
					var caizhi=rowData.caizhi;
					var pinpai=rowData.pinpai;
					var chandi=rowData.chandi;
					var danwei=rowData.danwei;
					var clickNum=rowData.clickNum;
					var sales=rowData.sales;
					var rexiao=rowData.rexiaoName;
					
					var familyOne=rowData.familyOne;
			   		var familyTwo=rowData.familyTwo;
			   		var familyId=rowData.familyId;
					//name 简介brief 内容介绍content 成本价costPrice 金额amount 类型categoryName 
        			//图片image 型号xinghao 规格guige 材质caizhi 品牌pinpai 产地chandi 单位danwei 点击浏览次数clickNum 销量sales 热销rexiaoName
					
					//alert(15"--"+id+"--"+title+"--"+href+"--"+sort+"--"+image+"--"+carouselType+"--"+state+"--"+createTime );
					 
					
					$("#fid").val(id);
					$("#fname").val(name);
					$("#fbrief").val(brief);
					$("#fcostPrice").val(costPrice);
					$("#famount").val(amount);
					$("#fxinghao").val(xinghao);
					$("#fguige").val(guige); 
					$("#fcaizhi").val(caizhi); 
					$("#fpinpai").val(pinpai);
					$("#fchandi").val(chandi);
					$("#fdanwei").val(danwei);   
					$("#fimage").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+image + "<br/>" ); 
					$("#fimage1").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+image1 + "<br/>"); 
					$("#fimage2").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+image2 + "<br/>"); 
					$("#fimage3").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+image3 + "<br/>"); 
					 $.ajax({
	    			        type:"post", 
	    			        url:"${contextPath}/sys/Tzzdic/getjilian",
	    			        data:{"level":1,"did":0,"newfl":"new"},
	    			   		dataType:"html", 
	    			       	success:function(data){    
	    			       		$("#ffamilyone").html(data);
	    			       		$("#ffamilyone  select").val(familyOne);
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
					 
					 if(rexiao=="热销"){
						 rexiao=1;
					 }else{
						 rexiao=0;
					 }
					$("#frexiao").val(rexiao);
					 
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
					
					$("#fimage1").click(function(){
							$(this).html(""); 
							var htmlspan = document.createElement("span");
							var htmlinput = document.createElement("input");
							htmlinput.setAttribute("type","file");
							htmlinput.setAttribute("name","file1");
							var htmlbr = document.createElement("br");
							htmlspan.innerHTML = "上传图片：";
							htmlspan.appendChild(htmlinput);
							htmlspan.appendChild(htmlbr);
							$(this).append(htmlspan);
							$("#fimage1").unbind();			
					});
					$("#fimage2").click(function(){
							$(this).html(""); 
							var htmlspan = document.createElement("span");
							var htmlinput = document.createElement("input");
							htmlinput.setAttribute("type","file");
							htmlinput.setAttribute("name","file2");
							var htmlbr = document.createElement("br");
							htmlspan.innerHTML = "上传图片：";
							htmlspan.appendChild(htmlinput);
							htmlspan.appendChild(htmlbr);
							$(this).append(htmlspan);
							$("#fimage2").unbind();			
					});
					$("#fimage3").click(function(){
							$(this).html(""); 
							var htmlspan = document.createElement("span");
							var htmlinput = document.createElement("input");
							htmlinput.setAttribute("type","file");
							htmlinput.setAttribute("name","file3");
							var htmlbr = document.createElement("br");
							htmlspan.innerHTML = "上传图片：";
							htmlspan.appendChild(htmlinput);
							htmlspan.appendChild(htmlbr);
							$(this).append(htmlspan);
							$("#fimage3").unbind();			
					});
					
					var uee1 = UE.getEditor('fcontent', {
     				            autoHeight: false,
     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
     				            initialFrameHeight:445  //初始化编辑器高度,默认320
     				        }) ;
        				        
        		uee1.ready(function() { 
        			uee1.setContent(content);  
        		});
					
				});
				$("#addaaa").click(function(){
					$("#aname").val("");
					$("#abrief").val("");
					$("#acostPrice").val("");
					$("#aamount").val("");
					$("#axinghao").val("");
					$("#aguige").val(""); 
					$("#acaizhi").val(""); 
					$("#apinpai").val("");
					$("#achandi").val("");
					$("#adanwei").val("");   
					//$("#aimage").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+image); 
					
					$.ajax({
	    			        type:"post", 
	    			        url:"${contextPath}/sys/Tzzdic/getjilian",
	    			        data:{"level":1,"did":0,"newfl":"new"},
	    			   		dataType:"html", 
	    			       	success:function(data){    
	    			       		$("#afamilyone").html(data);    
	    			       	} 
	   			    });
	   			    	
					$("#arexiao").val("0");
					$("#aid").css("display","block"); 
					$("#hid").css("display","none"); 
					var uee1 = UE.getEditor('acontent', {
     				            autoHeight: false,
     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
     				            initialFrameHeight:445  //初始化编辑器高度,默认320
     				        }) ;
        				        
	        		uee1.ready(function() { 
	        			//alert(brief);
	        			uee1.setContent("");  
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
				$("#fimage1").click(function(){
					$(this).html(""); 
			
					
				});
						        
				$("#fimage2").click(function(){
					$(this).html(""); 
			
					
				});
				$("#fimage3").click(function(){
					$(this).html(""); 

					
				});
				//=================提交  
				$("input[name='up']").click(function(){  
		 			 $("#fimage").unbind();
		 			 $("#fimage1").unbind();
		 			 $("#fimage2").unbind();
		 			 $("#fimage3").unbind();
		 			 var content= UE.getEditor('fcontent').getContent();
		 			 $("#editContent").val(content);
					 $("#form1").submit();
				});
				$("input[name='aup']").click(function(){  
					 
					if($("#afile").val()==""){
						alert("请选择图片！");
						return false;
					} if($("#afamilyId select").val()==""){
						alert("请选择分类！");
						return false;
					}
					var acontent= UE.getEditor('acontent').getContent();
		 			 $("#addContent").val(acontent);
					 $("#form2").submit();
				});
				//======================关闭
				$("input[name='fclose']").click(function(){
					$("#fimage").unbind();
					$("#fimage1").unbind();
		 			$("#fimage2").unbind();
		 			$("#fimage3").unbind();
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
</script>
