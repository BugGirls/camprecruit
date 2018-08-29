<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
<style>

</style>
<style type="text/css">
.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
}
		.box{width:400px;height:300px;border:1px solid #000;border-radius:10px;background:#fff;box-shadow:1px 1px 20px #000;text-align:center;display:none;position:absolute;z-index:2;}
		.box h2{font-size:18px;font-weight:bold;line-height:60px;}
		.box p{line-height:100px;font-size:14px;}
		.yy{width:100%;height:100%;background:#000;position:absolute;left:0;top:0;opacity:0.3;z-index:1;display:none;}
		.box .cancel{position:absolute;right:15px;top:10px;text-decoration:none;width:15px;height:15px;border:1px solid #adadad;text-align:center;line-height:15px;border-radius:2px;font-size:15px;font-weight:lighter ;display:block;}
		.box .cancel:hover{border:1px solid #585858;}
		
		.box2{width:400px;height:300px;border:1px solid #000;border-radius:10px;background:#fff;box-shadow:1px 1px 20px #000;text-align:center;display:none;position:absolute;z-index:2;}
		.box2 h2{font-size:18px;font-weight:bold;line-height:60px;}
		.box2 p{line-height:100px;font-size:14px;}
		.yy2{width:100%;height:100%;background:#000;position:absolute;left:0;top:0;opacity:0.3;z-index:1;display:none;}
		.box2 .cancel2{position:absolute;right:15px;top:10px;text-decoration:none;width:15px;height:15px;border:1px solid #adadad;text-align:center;line-height:15px;border-radius:2px;font-size:15px;font-weight:lighter ;display:block;}
		.box2 .cancel2:hover{border:1px solid #585858;}
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
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/productInfo/fileUpload">  
			<div style="float:left;"> 
				<div style="float:left;">
				商品编码	&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="no" id="ano" maxlength="60"/><br><br>
		  		商品名称	&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="name" id="aname" maxlength="60"/><br><br>
				商品条形码&nbsp;<input type="text" name="barCode" id="abarCode" maxlength="255"/><br><br>
				品牌&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	&nbsp;<input type="text" name="brand" id="abrand" maxlength="255"/><br><br>
				
				建议零售价&nbsp;<input type="text" name="advicePrice" id="aadvicePrice" maxlength="255"/><br><br>
				分类&nbsp;&nbsp;&nbsp;&nbsp;	<select id="abigCategoryNo" name="bigCategoryNo" onchange="changebigCategoryNo()">
				<option value="">--请选择--</option>
					</select>
					<input type="hidden" name="bigCategoryName" id="abigCategoryName"/><br><br>
				类型&nbsp;&nbsp;&nbsp;&nbsp;	<select id="asmallCategoryNo" name="smallCategoryNo" >
				<option value="">--请选择--</option>
					</select>
					<input type="hidden" name="smallCategoryName" id="asmallCategoryName"/>
					<br><br> 
				
				<span id="aimageh0">
				上传图片	&nbsp;	<input type='file' name='file' id='afile'/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
				</span>
				<input type="button" value="添加输入框" onclick = "addUploadFile(this)"/>
				
				</div>
				 <div style="float:left; padding-left: 20px;">
				 <input type="hidden" name="productPropertyTempNo" id="productPropertyTempNo"/>
					<table class="table table-bordered" id="propertyColumn" style="width:600px">
						<tr>
							<th>属性名</th>
							<th>属性值</th>
							<th>
	                			<a style="width:26px;height:26px;color:#737373;border:1px solid #ebebeb;border-radius:13px;" onclick="addPropertyRow()">
	                				<span class="glyphicon glyphicon-plus"></span>
	                			</a>
	                		</th>
						</tr>
					</table>
				 </div>
				 </div>
				 <div style="clear:both;"></div>
				 <div>
				 <br><br>
				 内容介绍：
				 <input type="text" name="content" id="acontent" style="width:90%;" maxlength="500">
				<br><br>
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
		  	<form id="form1" method="post" enctype="multipart/form-data" action="${contextPath}/sys/productInfo/fileUpload">			
				<input type="hidden" name="id" id="fid"/><br><br>
				<div style="float:left">
				<div style="float:left;"> 
				商品编码	&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="no" id="fno" maxlength="60"/><br><br>
		  		商品名称	&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="name" id="fname" maxlength="60"/><br><br>
				商品条形码&nbsp;<input type="text" name="barCode" id="fbarCode" maxlength="255"/><br><br>
				品牌&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	&nbsp;<input type="text" name="brand" id="fbrand" maxlength="255"/><br><br>
				
				建议零售价&nbsp;<input type="text" name="advicePrice" id="fadvicePrice" maxlength="255"/><br><br>
				分类&nbsp;&nbsp;&nbsp;&nbsp;	<select id="fbigCategoryNo" name="bigCategoryNo" onchange="changebigCategoryNo2()">
				<option value="">--请选择--</option>
					</select>
					<input type="hidden" name="bigCategoryName" id="fbigCategoryName"/><br><br>
				类型&nbsp;&nbsp;&nbsp;&nbsp;	<select id="fsmallCategoryNo" name="smallCategoryNo" >
				<option value="">--请选择--</option>
					</select>
					<input type="hidden" name="smallCategoryName" id="fsmallCategoryName"/>
					<br><br> 
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
				<div style="float:left; padding-left: 20px;">
					<input type="hidden" name="productPropertyTempNo" id="fproductPropertyTempNo"/>
					<table class="table table-bordered" id="fpropertyColumn" style="width:600px">
						<tr>
							<th>属性名</th>
							<th>属性值</th>
							<th>
	                			<a style="width:26px;height:26px;color:#737373;border:1px solid #ebebeb;border-radius:13px;" onclick="addfPropertyRow()">
	                				<span class="glyphicon glyphicon-plus"></span>
	                			</a>
	                		</th>
						</tr>
					</table>
				</div>
				</div>
				<div style="clear:both;"></div>
				<div>
				<br><br>
				 内容介绍：
				 <input type="text" name="content" id="fcontent" style="width:90%;" maxlength="500">
				<br><br>
				 </div>
				<div style="clear: both;"></div>
				<input type="button" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="fclose" value="取消">
			 </form>
			 </div>
		</div>
		
		<div id="" class="box">
		<span class="cancel">X</span>
		<h2>条形码</h2>
		<img src="" alt="" cache="false" width="200px" height="140px" id="barcodeImg"/>
	</div>
	<div id="" class="yy">
	</div>
	
	<div id="" class="box2">
		<span class="cancel2">X</span>
		<h2>二维码</h2>
		<img src="" alt="" cache="false" width="200px" height="200px" id="qrcodeImg"/>
	</div>
	<div id="" class="yy2">
		
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
//分类
function changebigCategoryNo(){
	$.ajax({
        type:"post", 
        url:"${contextPath}/sys/productType/getTypeSelectList",
        data:{"parentTypeKey":$("#abigCategoryNo").val()},
   		dataType:"html", 
   		async: false,//非异步
       	success:function(data){    
       		$("#asmallCategoryNo").html(data);    
       	} 
    });  
}

//修改分类
function changebigCategoryNo2(){
	$.ajax({
        type:"post", 
        url:"${contextPath}/sys/productType/getTypeSelectList",
        data:{"parentTypeKey":$("#fbigCategoryNo").val()},
   		dataType:"html", 
   		async: false,//非异步
       	success:function(data){    
       		$("#fsmallCategoryNo").html(data);    
       	} 
    });  
}

//从后台获取类类型
function changepropertyParent(num){
	var  parentPropertykey = $("#propertyInfo"+num).find("#apropertyParent").val();
	$.ajax({
		url:"${contextPath}/sys/productPropertyTemp/getPropertyTempSelectList",
		type:"POST",
		data:{"parentPropertykey":parentPropertykey},
		async: false,//非异步
		dataType : "html",
		success:function(data){
			$("#propertyInfo"+num).find("#aproductPropertyTempNo").html(data);
		}
	})
}

//添加行
function addPropertyRow(){
	var table = document.getElementById("propertyColumn");
	//表格行数
	var tempNum = table.rows.length;
	var num = Number(tempNum);
	var td1 = "<td><select class='form-control select2' name='propertyParent' id='apropertyParent' style='width: 100%;' onchange='changepropertyParent("+num+")'><option value=''>--请选择--</option></select></td>";
	var td2 = "<td><select class='form-control select2' name='aproductPropertyTempNo' id='aproductPropertyTempNo' style='width: 100%;'><option value=''>--请选择--</option></select></td>";
	var td3 = "<td><a style='width:30px;height:30px;color:#737373;border:1px solid #ebebeb;border-radius:13px;' onclick='deletePropertyRow("+num+")'><span class='glyphicon glyphicon-trash'></span></a></td>";
	$("#propertyColumn tr:last").after("<tr id='propertyInfo"+num+"'>"+td1+""+td2+""+td3+"</tr>");
	
	//从后台获取大类
	$.ajax({
		url:"${contextPath}/sys/productPropertyTemp/getPartnetProductPropertyTemp",
		type:"POST",
		data:{},
		async: false,//非异步
		dataType : "html",
		success:function(data){
			$("#propertyInfo"+num).find("#apropertyParent").html(data);
		}
	})
	//从后台获取类类型
	changepropertyParent(num);
	
}


//删除行
function deletePropertyRow(tempNum){
	var table = document.getElementById("propertyColumn");
	//表格行数
	var rowNum = table.rows.length;
	if(tempNum < rowNum) {   //需要重新排序
		$("#propertyInfo"+tempNum).remove();
		for(i=(parseInt(tempNum)+1);i<rowNum;i++){
			//赋值给超链接的删除 参数  i-1
			$("#propertyInfo"+i).find("td:last").find("a").attr("onclick","deletePropertyRow("+(i-1)+")");
			$("#propertyInfo"+i).find("#apropertyParent").attr("onchange","changepropertyParent("+(i-1)+")");
			//赋值 每一行的id  i-1  
			$("#propertyInfo"+i).attr("id",("propertyInfo"+(i-1)));
		}
	}
}

//从后台获取类类型--修改
function changefpropertyParent(num){
	var  parentPropertykey = $("#fpropertyInfo"+num).find("#fpropertyParent").val();
	$.ajax({
		url:"${contextPath}/sys/productPropertyTemp/getPropertyTempSelectList",
		type:"POST",
		data:{"parentPropertykey":parentPropertykey},
		async: false,//非异步
		dataType : "html",
		success:function(data){
			$("#fpropertyInfo"+num).find("#fproductPropertyTempNo").html(data);
		}
	})
}

//添加行--修改
function addfPropertyRow(){
	var table = document.getElementById("fpropertyColumn");
	//表格行数
	var tempNum = table.rows.length;
	var num = Number(tempNum);
	var td1 = "<td><select class='form-control select2' name='propertyParent' id='fpropertyParent' style='width: 100%;' onchange='changefpropertyParent("+num+")'><option value=''>--请选择--</option></select></td>";
	var td2 = "<td><select class='form-control select2' name='fproductPropertyTempNo' id='fproductPropertyTempNo' style='width: 100%;'><option value=''>--请选择--</option></select></td>";
	var td3 = "<td><a style='width:30px;height:30px;color:#737373;border:1px solid #ebebeb;border-radius:13px;' onclick='deletefPropertyRow("+num+")'><span class='glyphicon glyphicon-trash'></span></a></td>";
	$("#fpropertyColumn tr:last").after("<tr id='fpropertyInfo"+num+"'>"+td1+""+td2+""+td3+"</tr>");
	
	//从后台获取大类
	$.ajax({
		url:"${contextPath}/sys/productPropertyTemp/getPartnetProductPropertyTemp",
		type:"POST",
		data:{},
		async: false,//非异步
		dataType : "html",
		success:function(data){
			$("#fpropertyInfo"+num).find("#fpropertyParent").html(data);
		}
	})
	//从后台获取类类型
	changefpropertyParent(num);
	
}


//删除行--修改
function deletefPropertyRow(tempNum){
	var table = document.getElementById("fpropertyColumn");
	//表格行数
	var rowNum = table.rows.length;
	if(tempNum < rowNum) {   //需要重新排序
		$("#fpropertyInfo"+tempNum).remove();
		for(i=(parseInt(tempNum)+1);i<rowNum;i++){
			//赋值给超链接的删除 参数  i-1
			$("#fpropertyInfo"+i).find("td:last").find("a").attr("onclick","deletefPropertyRow("+(i-1)+")");
			$("#fpropertyInfo"+i).find("#fpropertyParent").attr("onchange","changefpropertyParent("+(i-1)+")");
			//赋值 每一行的id  i-1  
			$("#fpropertyInfo"+i).attr("id",("fpropertyInfo"+(i-1)));
		}
	}
}

//商品条形码
function barcode(barcode){
	//加载条形码
	$.ajax({
		url:"${contextPath}/sys/productInfo/barcode",
		type:"POST",
		data:{"barcode":barcode},
		async: false,//非异步
		dataType : "text",
		success:function(data){
			$("#barcodeImg").attr("src","${basePath}uploadFiles/barcode/"+ barcode +".png");
		}
	})
	var left = ($(window).width()-400) / 2;
	var top = ($(window).height()-400) / 2;
	$(".box").show().css({left:left,top:top});
	$(".yy").css("opacity","0.3").show();
}

//二维码
function erweima(barcode){
	//加载条形码
	$.ajax({
		url:"${contextPath}/sys/productInfo/erweima",
		type:"POST",
		data:{"barcode":barcode},
		async: false,//非异步
		dataType : "text",
		success:function(data){
			$("#qrcodeImg").attr("src","${basePath}uploadFiles/twoDimensionCode/"+ barcode +".png");
		}
	})
	var left = ($(window).width()-400) / 2;
	var top = ($(window).height()-400) / 2;
	$(".box2").show().css({left:left,top:top});
	$(".yy2").css("opacity","0.3").show();
}

$(function(){
	$(".cancel").click(function(){
		$(".yy").hide();
		var left = ($(window).width()-400) / 2;
		var top = ($(window).height()-400) / 2;
		$(".box").show().animate({
			width:"-400px",
			height:"-300px",
			left:"-"+left+"px",
			top:"-"+top+"px"
		},500,function(){
			$(this).css({width:400,height:300}).hide();
		});
	});
	
	$(".cancel2").click(function(){
		$(".yy2").hide();
		var left = ($(window).width()-400) / 2;
		var top = ($(window).height()-400) / 2;
		$(".box2").show().animate({
			width:"-400px",
			height:"-300px",
			left:"-"+left+"px",
			top:"-"+top+"px"
		},500,function(){
			$(this).css({width:400,height:300}).hide();
		});
	});
});


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
        			url : "${contextPath}/sys/productInfo/getProductInfo",
        			datatype : "json",
        			height : 650,
<<<<<<< HEAD
        			colNames : ['编码', ' 商品名称', '信息', '品牌', '分类', '类型', '图片', '建议零售价（￥）'],
=======
        			colNames : ['编码', '商品名称', '信息', '品牌', '分类', '类型', '图片', '建议零售价（￥）'],
>>>>>>> merge project
        			colModel : [ {
        				name : 'no',
        				index : 'no',
        				label : '编码',
        				width : 120,
        				editable : false,
<<<<<<< HEAD
        				search : false 
=======
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
>>>>>>> merge project
        			}, {
        				name : 'name',
        				index : 'name',
        				label : '商品名称',
        				width : 150,
        				editable : false,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'barCode',
        				index : 'barCode',
        				label : '信息',
        				width : 120,
        				editable : false,
        				search : false,
        				sortable : false,
        				formatter : operatorFormatter
        			}, {
        				name : 'brand',
        				index : 'brand',
        				label : '品牌',
        				width : 130,
        				editable : false,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'bigCategoryName',
        				index : 'bigCategoryName',
        				label : '分类',
        				width : 120,
        				editable : false,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'smallCategoryName',
        				index : 'smallCategoryName',
        				label : '类型',
        				width : 120,
        				editable : false,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			},{
        				name : 'image',
        				index : 'image',
        				label : '图片', 
        				width : 220,
        				editable : true,
        				search:false,
        				formatter:alarmFormatter,
        				search : false 
        			}, {
        				name : 'advicePrice',
        				index : 'advicePrice',
        				label : '建议零售价',
        				width : 130,
        				editable : false,
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
        			editurl : "${contextPath}/sys/productInfo/operateProductInfo"
        			
        		});
        //=====================================================
        	
        	$('[data-rel=tooltip]').tooltip();
			$('[data-rel=popover]').popover({html:true});
			
			function operatorFormatter(cellvalue, options, cell) {
       			template = "<a onclick='erweima("+cellvalue+");'><img style='cursor:pointer;' width='50px' src='${contextPath}/static/images/erwei.png'  title='商品二维码'/></a>"
				+ "<a onclick='barcode("+cellvalue+");'><img style='cursor:pointer;' width='50px' src='${contextPath}/static/images/barcode.png'  title='商品条形码'/></a>";
       			return template;
       		}
			
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

					var no=rowData.no;
					var bigCategoryNo = '';
					var smallCategoryNo = '';
					var productPropertyTempNo = '';
					$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/productInfo/findByNo",
    			        data:{"no":no},
    			   		dataType:"json", 
    			   		async: false,//非异步
    			       	success:function(data){    
    			       		bigCategoryNo = data.bigCategoryNo;
    			       		smallCategoryNo = data.smallCategoryNo;
    			       		productPropertyTempNo = data.productPropertyTempNo;
    			       		$("#fid").val(data.id);
    						$("#fname").val(data.name);
    						$("#fno").val(no);
    						$("#fbarCode").val(data.barCode);
    						$("#fadvicePrice").val(data.advicePrice);
    						$("#fbigCategoryName").val(data.bigCategoryName); 
    						$("#fsmallCategoryName").val(data.smallCategoryName);
    						$("#fproductPropertyTempNo").val(productPropertyTempNo);
    						$("#fcontent").val(data.content);   
    						$("#fbrand").val(data.brand);  
    						$("#fimage").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+data.image + "<br/>" ); 
    						$("#fimage1").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+data.image1 + "<br/>"); 
    						$("#fimage2").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+data.image2 + "<br/>"); 
    						$("#fimage3").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+data.image3 + "<br/>"); 
    			       	} 
   			    	});
					//大类
					$.ajax({
        			        type:"post", 
        			        url:"${contextPath}/sys/productType/getPartnetProductType",
        			        data:{"bigCategoryNo":bigCategoryNo},
        			   		dataType:"html", 
        			   		async: false,//非异步
        			       	success:function(data){    
        			       		$("#fbigCategoryNo").html(data);  
        			       	} 
        			}); 

					//小类
					$.ajax({
				        type:"post", 
				        url:"${contextPath}/sys/productType/getProductTypeByTypeKey",
				        data:{"smallCategoryNo":smallCategoryNo,"bigCategoryNo":bigCategoryNo},
				   		dataType:"html", 
				   		async: false,//非异步
				       	success:function(data){   
				       		$("#fsmallCategoryNo").html(data);    
				       	} 
				    });  
					 
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
					
					//商品规格属性展示
					var strs= new Array(); //定义一数组  
					strs=productPropertyTempNo.split(","); //字符分割  
					for (i=0;i<strs.length-1 ;i++ ) { 
						var td1 = "<td><select class='form-control select2' name='propertyParent' id='fpropertyParent' style='width: 100%;' onchange='changefpropertyParent("+(i+1)+")'><option value=''>--请选择--</option></select></td>";
						var td2 = "<td><select class='form-control select2' name='fproductPropertyTempNo' id='fproductPropertyTempNo' style='width: 100%;'><option value=''>--请选择--</option></select></td>";
						var td3 = "<td><a style='width:30px;height:30px;color:#737373;border:1px solid #ebebeb;border-radius:13px;' onclick='deletefPropertyRow("+(i+1)+")'><span class='glyphicon glyphicon-trash'></span></a></td>";
						$("#fpropertyColumn tr:last").after("<tr id='fpropertyInfo"+(i+1)+"'>"+td1+""+td2+""+td3+"</tr>");
						//从后台获取大类
						$.ajax({
							url:"${contextPath}/sys/productPropertyTemp/getPartnetProductPropertyTemp",
							type:"POST",
							data:{"propertykey":strs[i]},
							async: false,//非异步
							dataType : "html",
							success:function(data){
								$("#fpropertyInfo"+(i+1)).find("#fpropertyParent").html(data);
							}
						})
						//从后台获取类类型
						$.ajax({
							url:"${contextPath}/sys/productPropertyTemp/getPropertyTempSelectList",
							type:"POST",
							data:{"propertykey":strs[i]},
							async: false,//非异步
							dataType : "html",
							success:function(data){
								$("#fpropertyInfo"+(i+1)).find("#fproductPropertyTempNo").html(data);
							}
						})
					}
					
				});
				
				//添加
				$("#addaaa").click(function(){
					$("#aname").val("");
					$("#ano").val("");
					$("#abarCode").val("");
					$("#aadvicePrice").val("");
					$("#abigCategoryNo").val("");
					$("#asmallCategoryNo").val(""); 
					$("#abigCategoryName").val(""); 
					$("#asmallCategoryName").val("");
					$("#acontent").val("");
					$("#abrand").val("");
					$("#aimage").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片"+image); 
					//大类
					$.ajax({
        			        type:"post", 
        			        url:"${contextPath}/sys/productType/getPartnetProductType",
        			        data:{},
        			   		dataType:"html", 
        			   		async: false,//非异步
        			       	success:function(data){    
        			       		$("#abigCategoryNo").html(data);  
        			       	} 
        			}); 
					changebigCategoryNo();
					$("#aid").css("display","block"); 
					$("#hid").css("display","none"); 
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
		 			if($("#fsmallCategoryNo select").val()==""){
						alert("请选择商品类型！");
						return false;
					}
					var bigCategoryNooption = $("#fbigCategoryNo option:selected");
					$("#fbigCategoryName").val(bigCategoryNooption.text());
					var smallCategoryNooption = $("#fsmallCategoryNo option:selected");
					$("#fsmallCategoryName").val(smallCategoryNooption.text());
					var typeStr = '';
					var table = document.getElementById("fpropertyColumn");
					//表格行数
					var rows = table.rows.length;
					for(var i = 1;i<rows;i++){
						typeStr += ($("#fpropertyInfo"+i).find("#fproductPropertyTempNo").val()) + ",";
					}
					$("#fproductPropertyTempNo").val(typeStr);
					 $("#form1").submit();
				});
				$("input[name='aup']").click(function(){  
					 
					if($("#afile").val()==""){
						alert("请选择图片！");
						return false;
					} if($("#asmallCategoryNo select").val()==""){
						alert("请选择商品类型！");
						return false;
					}
					var bigCategoryNooption = $("#abigCategoryNo option:selected");
					$("#abigCategoryName").val(bigCategoryNooption.text());
					var smallCategoryNooption = $("#asmallCategoryNo option:selected");
					$("#asmallCategoryName").val(smallCategoryNooption.text());
					var typeStr = '';
					var table = document.getElementById("propertyColumn");
					//表格行数
					var rows = table.rows.length;
					for(var i = 1;i<rows;i++){
						typeStr += ($("#propertyInfo"+i).find("#aproductPropertyTempNo").val()) + ",";
					}
					$("#productPropertyTempNo").val(typeStr);
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
