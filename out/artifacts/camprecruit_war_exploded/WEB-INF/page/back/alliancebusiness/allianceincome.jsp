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

.title{
    height: 40px;
    font-size: 16px;
}
.title span{
    color: red;
}
</style>
<div class="row col-xs-12">
	<div class="panel panel-default col-xs-6" style="height:500px;overflow-y: auto;">
	  <!-- Default panel contents -->
	  <div class="panel-heading">加盟商收益待结算统计</div>
	<form class="form-inline" role="form" >
	  <div class="form-group">
	  <div class="input-group">
	  	<span class="form-control">查询日期</span> 
	  </div>
	  <div class="input-group">
	    <div class="input-group-addon">从</div>
	    <input type="date" class="form-control" id="search_date_start_" placeholder="起始日期">
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="input-group">
	      <div class="input-group-addon">至</div>
	      <input class="form-control" type="date" id="search_date_end_" placeholder="截止日期" >
	    </div>
	  </div>
	  <input type="button" class="btn btn-default" id="search_btn_" value="查询"/>
	</form>
	  <!-- Table -->
	  <table class="table ">
        <thead>
          <tr>
            <th>加盟商</th>
            <th>收入</th>
            <th>支出</th>
            <th>结算</th>
          </tr>
        </thead>
        <tbody id="tab_content_" >
        </tbody>
      </table>
	</div>
	
	<div class="panel panel-default col-xs-6" style="height:500px;overflow-y: auto;">
	  <!-- Default panel contents -->
	  <div class="panel-heading">加盟商账户金额统计</div>
	
	  <!-- Table -->
	  <table class="table ">
        <thead>
          <tr>
            <th>加盟商</th>
            <th>账户金额(单位：元)</th>
          </tr>
        </thead>
        <tbody id="tab_alliance_content_" >
        </tbody>
      </table>
	</div>
	
	<div class="panel panel-default col-xs-6" style="height:500px;overflow-y: auto;">
	  <!-- Default panel contents -->
	  <div class="panel-heading">加盟商提现申请</div>
	  <!-- Table -->
	  <table class="table ">
        <thead>
          <tr>
            <th>加盟商</th>
            <th>账户金额</th>
            <th>提现金额</th>
            <th>申请时间</th>
            <th>审核</th>
          </tr>
        </thead>
        <tbody id="tab_withdraw_content_">
        </tbody>
      </table>
	</div>
	
</div>

<div id="hidediv" style="z-index:99;width:300px;padding:20px 20px; position:absolute;background-color:#fff; border:1px solid #dcd; display: none; top: 100px ;left:30% ; ">
	<p style="text-align: center; font-size: 20px;">驳回申请 </p>
	
	<label for="remark_">驳回原因：</label>
	<textarea id="remark_" class="autosize-transition form-control" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 52px;"></textarea>
	<br><br>
	<input type="hidden" id="withdraw_id" />
	
	<p style="text-align: center;"><button style="border:1px solid #aaa;margin-right:30px;background-color:#eee; " id="ok">确定</button><button  style="border:1px solid #aaa;margin-right:30px;background-color:#eee; "id="divclose">取消</button></p>
</div>
<script type="text/javascript">
	$("#divclose").click(function(){
		$("#remark_").val("");
		$("#withdraw_id").val("");
		$("#hidediv").hide(); 
	});
	$("#ok").click(function(){
		var id = $("#withdraw_id").val();
		var remark = $("#remark_").val();
		if(id==""){
			alert("请重试");
			return false;
		}else if(remark==""){
			alert("驳回理由不能为空！");
			return false;
		}else{
		$.ajax({
   			type : "post",
   			url : " ${contextPath}/sys/allianceincome/alliancewithdrawback",
   			data:{"id":id,"remark":remark},
   			dataType:"json",
   			success : function(data) {
   				//console.log(eval(data))
   				if(data.success){
   					alert(data.msg);
   					$("#remark_").val("");
   					$("#withdraw_id").val("");
   					$("#hidediv").hide(); 
   					loadallianceaccount();
   	        		loadalliancewithdraw();
   				}else{
   					alert(data.msg);
   				}
   			}
   		});
		}
	});
</script>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/echarts-all.js",
		                 null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
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
        		$("#search_date_end_").val(new Date().Format("yyyy-MM-dd"));
        		$("#search_btn_").click(function(){
        			loadallianceincome();
        		});
        		
        		loadallianceincome();
        		loadallianceaccount();
        		loadalliancewithdraw();
        		
        	});
        });
        
        function loadallianceaccount(){
    		$.ajax({
    			type : "post",
    			url : " ${contextPath}/sys/allianceincome/getAllianceAccountCount",
    			success : function(data) {
    				var list = eval(data);
    				//var elemnts = "";
    				var elemnts = "";
    				$("#tab_alliance_content_").empty();
    				if(list.length==0){
    					elemnts += "<tr><td>无</td></tr>";
    				}else{
        				$.each(list, function(key, value) {
        					elemnts += "<tr><td>" + value.name + "</td><td>"
        							+ value.profit + "</td></tr>";
        				});
    				}
    				$("#tab_alliance_content_").append(elemnts);
    			}
    		});
		}
   		function loadallianceincome(){
    		$.ajax({
    			type : "post",
    			url : " ${contextPath}/sys/allianceincome/getAllianceIncomeSettleCount",
    			data:{"startdate":$("#search_date_start_").val(),"enddate":$("#search_date_end_").val()},
    			success : function(data) {
    				var list = eval(data);
    				console.log(list.length)
    				var elemnts = "";
    				$("#tab_content_").empty();
    				if(list.length==0){
    					elemnts += "<tr><td>无</td></tr>";
    				}else{
     				$.each(list, function(key, value) {
     					elemnts += "<tr><td>" + value.name + "</td><td>"
     							+ value.income + "</td><td>"+ value.expense + "</td><td><input type=\"button\"  value=\"结算\" onclick=\"settlealliance(" + value.allianceid
     							+ ")\"/></td></tr>";
     				});
    				}
    				$("#tab_content_").append(elemnts);
    			}
    		});
   		}
   		function settlealliance(allianceid){
   			$.ajax({
       			type : "post",
       			url : " ${contextPath}/sys/allianceincome/settleAllianceIncome",
       			data:{"startdate":$("#search_date_start_").val(),"enddate":$("#search_date_end_").val(),"allianceid":allianceid},
       			dataType:"json",
       			success : function(data) {
       				//console.log(eval(data))
       				if(data.success){
       					loadallianceincome();
       					loadallianceaccount();
       				}else{
       					alert(data.msg);
       				}
       			}
       		});
   		}
   		
   		function loadalliancewithdraw(){
    		$.ajax({
    			type : "post",
    			url : " ${contextPath}/sys/allianceincome/getAllianceWithdraw",
    			success : function(data) {
    				var list = eval(data);
    				var elemnts = "";
    				$("#tab_withdraw_content_").empty();
    				if(list.length==0){
    					elemnts += "<tr><td>无</td></tr>";
    				}else{
     				$.each(list, function(key, value) {
     					var b = value.allianceBusiness;
     					
     					elemnts += "<tr><td>" + b.name + "</td><td>"
     							+ b.profit + "</td><td>"+ value.withdrawal + "</td><td>"+ new Date(value.createtime).Format("yyyy-MM-dd hh:mm:ss") + "</td><td><input type=\"button\"  value=\"通过\" onclick=\"alliancewithdraw(" + value.id
     							+ ")\"/>&nbsp;<input type=\"button\"  value=\"驳回\" onclick=\"alliancewithdrawback(" + value.id
     							+ ")\"/></td></tr>";
     				});
    				}
    				$("#tab_withdraw_content_").append(elemnts);
    			}
    		});
   		}
   		
   		function alliancewithdraw(id){
   			$.ajax({
       			type : "post",
       			url : " ${contextPath}/sys/allianceincome/alliancewithdraw",
       			data:{"id":id},
       			dataType:"json",
       			success : function(data) {
       				//console.log(eval(data))
       				if(data.success){
       					loadallianceaccount();
       					loadalliancewithdraw();
       				}else{
       					alert(data.msg);
       				}
       			}
       		});
   		}
		function alliancewithdrawback(id){
			$("#withdraw_id").val(id);
			$("#hidediv").show();
   		}
</script>
