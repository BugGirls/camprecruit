<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
<style>
.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
}
</style>
<div class="row">

	<div class="col-xs-12">
		<div id="BarChart" class="col-xs-6" style="height:400px;"></div>
		<div id="PieChart" class="col-xs-6" style="height:400px;"></div>
	</div>

<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">员工提成统计</div>

  <!-- Table -->
  <table class="table" id="grid-table">
        <thead>
          <tr>
            <th>员工ID</th>
            <th>员工姓名</th>
            <th>提成</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="tab_user_content_">
        </tbody>
   </table>
   <div id="grid-pager"></div>
</div>
	<div class="col-xs-12">
		<div id="userBarChart" class="col-xs-6" style="height:400px;"></div>
	</div>
</div>
<script type="text/javascript"src="${contextPath}/static/assets/js/config.js"></script> 

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/echarts-all.js", "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		var myBarChart = echarts.init(document.getElementById('BarChart'));
        		$.ajax({
        			type : "post",
        			url : " ${contextPath}/sys/allianceincome/getAllianceStaffIncomeStatistics",
        			dataType:"json", 
        			success : function(data) {
        				var list = data.rows;
        				console.log(data.rows);
        				var x = new Array();
        				var y = new Array();
        				var y1 = new Array();
        				
        				var z = new Array();
        				var elemnts = "";
        				$.each(list, function(key, value) {
        					elemnts += "<tr><td>" + value.userid + "</td>" + "<td>"
        							+ value.username + "</td>" + "<td>" + value.income
        							+ "</td><td><a href='${contextPath}/sys/sysuser/home#page/alliancestaffincome?staffid="+value.userid+"'>查看详情</a></td></tr>" ;
        							//console.log(elemnts);
        					x[key] = value.username;
        					y[key] = value.income;
        					//y1[key] = value.payedNum;
        					z[key] = new promotionParticipants(value.income, value.userid);
        				});
        				$("#tab_user_content_").append(elemnts);
        				var baroption = {
        					title : {
        						text : '员工提成统计'
        					},
        					tooltip : {},
        					legend : {
        						data : [ '提成' ]
        					},

        					calculable : true,
        					grid : {
        						borderWidth : 0,
        						y : 80,
        						y2 : 60
        					},
        					xAxis : [ {
        						type : 'category',
        						show : true,
        						data : x
        					} ],
        					yAxis : [ {
        						type : 'value',
        						show : true
        					} ],
        					series : [ {
        						name : '提成',
        						type : 'bar',
        						itemStyle : {
        							normal : {
        								label : {
        									show : true,
        									position : 'top',
        									formatter : '{c}元'
        								}
        							}
        						},
        						data : y
        					}]
        				};
        				
        				myBarChart.setOption(baroption);
        				//var ecConfig = require('echarts/config');  
//         				myBarChart.on('click', function(param){  
//         	            	var selected = param.username;
//         	           });
        			}
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


        		function promotionParticipants(value, name) {
        			this.value = value;
        			this.name = name;
        		}
        	});
        });
</script>
