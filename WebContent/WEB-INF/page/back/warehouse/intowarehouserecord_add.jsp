<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
<style>
	.ajax-loading-icon {
		display:none;
	}
</style>
<div class="row">
	<div class="col-xs-12">
		<div style="padding: 10px;">
<<<<<<< HEAD
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/productInfo/fileUpload">  
			<div> 
=======
			<div> 
			<form id="form1" method="get" action="#">  
>>>>>>> merge project
				<table class="table table-bordered">
					<tr>
						<td colspan="4" align="center">
							<span style="font-size:18px;font-family:微软雅黑;">${title }</span>
							<input type="hidden" value="${sysUser.allianceId }" name="allianceId" id="allianceId"/>
<<<<<<< HEAD
=======
							<input type="hidden" value="${title }" name="title" id="title"/>
>>>>>>> merge project
						</td>
					</tr>
					<tr>
						<td>入库单号</td>
<<<<<<< HEAD
						<td><input type="text" value="${no }" name="no" id="no" style="width:220px;" disabled="disabled"/></td>
						<td>操作人员</td>
						<td><input type="text" value="${sysUser.userName }" name="" id="" disabled="disabled"/></td>
					</tr>
					<tr>
						<td>种类总数</td>
						<td><input type="text" value="" name="" id="" disabled="disabled"/></td>
						<td>商品总数</td>
						<td><input type="text" value="" name="" id="" disabled="disabled"/></td>
					</tr>
					<tr>
						<td>进货总价</td>
						<td><input type="text" value="" name="" id="" disabled="disabled"/></td>
						<td>销售总价</td>
						<td><input type="text" value="" name="" id="" disabled="disabled"/></td>
					</tr>
					<tr>
						<td>供应商</td>
						<td><input type="text" value="" name="" id=""/></td>
						<td>备注</td>
						<td><input type="text" value="" name="" id=""/></td>
					</tr>
				</table>
=======
						<td><input type="text" value="${no }" name="no" id="no" style="width:220px;" readonly="readonly"/></td>
						<td>操作人员</td>
						<td><input type="text" value="${sysUser.userName }" name="creater" id="creater"  readonly="readonly"/>
							<input type="hidden" value="${sysUser.no }" name="createrNo" id="createrNo"/>
						</td>
					</tr>
					<tr>
						<td>种类总数</td>
						<td><input type="text" value="" name="intoTypeNum" id="intoTypeNum"  readonly="readonly"/></td>
						<td>商品总数</td>
						<td><input type="text" value="" name="intoNum" id="intoNum"  readonly="readonly"/></td>
					</tr>
					<tr>
						<td>建议总价</td>
						<td><input type="text" value="" name="intoTotalAmount" id="intoTotalAmount"  readonly="readonly"/></td>
						<td>进货总价</td>
						<td><input type="text" value="" name="saleTotalAmount" id="saleTotalAmount"  readonly="readonly"/></td>
					</tr>
					<tr>
						<td>供应商</td>
						<td><input type="text" value="" name="supplier" id="supplier"/></td>
						<td>备注</td>
						<td><input type="text" value="" name="content" id="content"/></td>
					</tr>
				</table>
				 </form>
>>>>>>> merge project
					<table class="table table-bordered" id="intoWarehouserecorddetail">
						<tr>
							<th>序号</th>
							<th>商品编码</th>
							<th>名称</th>
<<<<<<< HEAD
							<th>售价</th>
=======
							<th>进价</th>
>>>>>>> merge project
							<th>数量</th>
							<th>生产日期</th>
							<th>库存位置</th>
							<th>备注</th>
							<th>
	                			<a style="width:26px;height:26px;color:#737373;border:1px solid #ebebeb;border-radius:13px;" onclick="addDetailRow()">
	                				<span class="glyphicon glyphicon-plus"></span>
	                			</a>
	                		</th>
						</tr>
					</table>
				 </div>
				 <div style="clear:both;"></div>
				 <div>
				<br><br>
				 </div>
				 <div style="clear: both;"></div>
				<input type="button"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" name="aup" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="aclose" onclick="javascript:history.go(-1);" value="返回">
<<<<<<< HEAD
			 </form>
=======
>>>>>>> merge project
			</div> 
 
		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
		<script type="text/javascript"src="${contextPath}/static/assets/js/datepicker/WdatePicker.js"></script> 
<<<<<<< HEAD
=======
		<script type="text/javascript"src="${contextPath}/static/assets/js/jplugin/jplugin_common.js"></script> 
		<script type="text/javascript"src="${contextPath}/static/assets/js/jplugin/jplugin_3rd.js"></script>
>>>>>>> merge project
 </div></div>

<script>
//添加行
function addDetailRow(){
	var table = document.getElementById("intoWarehouserecorddetail");
	//表格行数
	var tempNum = table.rows.length;
	var num = Number(tempNum);
<<<<<<< HEAD
	var td1 = "<td>"+num+"<input type='hidden' id='seqence' name='seqence' value="+num+"/></td>";
	var td2 = "<td><input type='text' name='' style='width:120px;'/></td>";
	var td3 = "<td><input type='text' name=''/></td>";
	var td4 = "<td><input type='text' name='' id='' style='width:70px;'/></td>";
	var td5 = "<td><input type='text' name='' id='' style='width:70px;'/></td>";
	var td6 = "<td><input type='text' name='' id='productionDate' style='width:100px;'/></td>";
	var td7 = "<td><input type='text' name=''/></td>";
	var td8 = "<td><input type='text' name=''/></td>";
=======
	var td1 = "<td>"+num+"<input type='hidden' id='seqence' name='seqence' value="+num+"></td>";
	var td2 = "<td><input type='text' name='productNo' id='productNo' onchange='changeProductNo("+num+")' style='width:120px;'/></td>";
	var td3 = "<td><input type='text' name='productName' id='productName' readonly='readonly'/><input type='hidden' name='productBarCode' id='productBarCode'/></td>";
	var td4 = "<td><input type='text' name='salePrice' id='salePrice' onchange='changePrice()' style='width:70px;'/><input type='hidden' name='advicePrice' id='advicePrice'/></td>";
	var td5 = "<td><input type='text' name='num' id='num' onchange='changeNum()' style='width:70px;'/></td>";
	var td6 = "<td><input type='text' name='productionDate' id='productionDate' style='width:100px;'/></td>";
	var td7 = "<td><input type='text' name='storageLocation' id='storageLocation'/></td>";
	var td8 = "<td><input type='text' name='content' id='content'/></td>";
>>>>>>> merge project
	var td9 = "<td><a style='width:30px;height:30px;color:#737373;border:1px solid #ebebeb;border-radius:13px;' onclick='deleteDetailRow("+num+")'><span class='glyphicon glyphicon-trash'></span></a></td>";
	$("#intoWarehouserecorddetail tr:last").after("<tr id='detail"+num+"'>"+td1+""+td2+""+td3+""+td4+""+td5+""+td6+""+td7+""+td8+""+td9+"</tr>");
	//设置时间弹出框
	$("#detail"+num).find("#productionDate").attr("onclick","WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM-dd'})")
}

//删除行
function deleteDetailRow(tempNum){
	var table = document.getElementById("intoWarehouserecorddetail");
	//表格行数
	var rowNum = table.rows.length;
	if(tempNum < rowNum) {   //需要重新排序
		$("#detail"+tempNum).remove();
		for(i=(parseInt(tempNum)+1);i<rowNum;i++){
			//循环将 i-- 赋值给第一行排序序号
			$("#detail"+i).find("td:first").html(i-1);
			$("#detail"+i).find("#seqence").val(i-1);
			//赋值给超链接的删除 参数  i-1
			$("#detail"+i).find("td:last").find("a").attr("onclick","deleteDetailRow("+(i-1)+")");
			//赋值 每一行的id  i-1  
			$("#detail"+i).attr("id",("detail"+(i-1)));
		}
	}
<<<<<<< HEAD
}
=======
	changgeTypeNum(tempNum-1);
	changeNum();
	changePrice();
}

function changeProductNo(num){
	var productNo = $("#detail"+num).find("#productNo").val();
	$.ajax({
        type:"post", 
        url:"${contextPath}/sys/productInfo/findByNo",
        data:{"no":productNo},
   		dataType:"json", 
   		async: false,//非异步
       	success:function(data){   
       		if(null != data && '' != data){
       			$("#detail"+num).find("#productName").val(data.name);
       			$("#detail"+num).find("#salePrice").val(data.advicePrice);
       			$("#detail"+num).find("#advicePrice").val(data.advicePrice);
       			$("#detail"+num).find("#productBarCode").val(data.barCode);
       			changePrice();
       			changgeTypeNum(num);
       		}
       	},
		error:function(){
			alert("商品不存在！请补充商品");
			$("#detail"+num).find("#productNo").val("");
			$("#detail"+num).find("#productName").val("");
			$("#detail"+num).find("#salePrice").val("");
			$("#detail"+num).find("#num").val("");
			$("#detail"+num).find("#productionDate").val("");
			$("#detail"+num).find("#storageLocation").val("");
			$("#detail"+num).find("#content").val("");
			$("#detail"+num).find("#productBarCode").val("");
			$("#detail"+num).find("#advicePrice").val("");
		}
	}); 
}
//商品总类数目处理
function changgeTypeNum(num){
	$("#intoTypeNum").val(num);
}

//商品总数处理
function changeNum(){
	var table = document.getElementById("intoWarehouserecorddetail");
	var tempNum = table.rows.length;
	var rowNum = Number(tempNum);
	var intoNum = 0;
	for(var i = 1;i<rowNum;i++){
		intoNum += Number($("#detail"+i).find("#num").val());
	}
	$("#intoNum").val(intoNum);
}

//总价处理
function changePrice(){
	var table = document.getElementById("intoWarehouserecorddetail");
	var tempNum = table.rows.length;
	var rowNum = Number(tempNum);
	var saleTotalAmount = 0.0;
	var intoTotalAmount = 0.0;
	for(var i = 1;i<rowNum;i++){
		saleTotalAmount += parseFloat($("#detail"+i).find("#salePrice").val());
		intoTotalAmount += parseFloat($("#detail"+i).find("#advicePrice").val());
	}
	$("#intoTotalAmount").val(intoTotalAmount);
	$("#saleTotalAmount").val(saleTotalAmount);
}


$("input[name='aup']").click(function(){  
	var detailArray = new Array();
	var table = document.getElementById("intoWarehouserecorddetail");
	var rows = table.rows.length;
	for(var i = 1;i<rows;i++){
		detailArray.push($("#detail"+i).validAndBuild());
	}
	$.ajax({
	url:"${contextPath}/sys/intoWarehouse/saveIntoWarehouse",
	type:"POST",
	data:{"detail":JSON.stringify(detailArray), "intoWarehouseRecord":JSON.stringify(($("#form1").validAndBuild()))},
	async: false,//非异步
	dataType : "json",
	success:function(data){
		location.href = "${contextPath}/sys/sysuser/home";
		location.href = "${contextPath}/sys/sysuser/home#page/intoWarehouseRecord";
	},
	error:function(){
		location.href = "${contextPath}/sys/sysuser/home";
		location.href = "${contextPath}/sys/sysuser/home#page/intoWarehouseRecord";
	}
})
	
});
>>>>>>> merge project
 
</script>