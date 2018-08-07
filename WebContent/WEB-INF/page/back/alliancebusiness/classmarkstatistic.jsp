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
<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">班级学生素质得分统计</div>

  <!-- Table -->
  <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>班级名称</th>
            <th>最高分</th>
            <th>最低分</th>
            <th>平均分</th>
          </tr>
        </thead>
        <tbody id="tab_content_">
        </tbody>
      </table>
</div>

	<div class="col-xs-12">
		<div id="BarChart" style="height:400px;"></div>
<!-- 		<div id="LineChart" class="col-xs-6" style="height:400px;"></div> -->
	</div>

</div>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/echarts-all.js", "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		var myBarChart = echarts.init(document.getElementById('BarChart'));
        		//var myLineChart = echarts.init(document.getElementById('LineChart'));
        		$.ajax({
        			type : "post",
        			url : " ${contextPath}/sys/schoolclass/getStatisticsMarks",
        			success : function(data) {
        				var list = eval(data);
        				var x = new Array();
        				var y = new Array();
        				var y1 = new Array();
        				var y2 = new Array();
        				
        				var z = new Array();
        				var elemnts = "";
        				$.each(list, function(key, value) {
        					elemnts += "<tr><td>" + (key + 1) + "</td>" + "<td>"
        							+ value.classname + "</td>" + "<td>" + value.maxMark
        							+ "</td><td>" + value.minMark
        							+ "</td><td>" + value.avgMark
        							+ "</td></tr>";
        					x[key] = value.classname;
        					y[key] = value.maxMark;
        					y1[key] = value.minMark;
        					y2[key] = value.avgMark;
        					z[key] = new promotionParticipants(value.participants, value.promotionName);
        				});
        				$("#tab_content_").append(elemnts);
        				var baroption = {
        					title : {
        						text : '班级学生素质得分统计'
        					},
        					tooltip : {},
        					legend : {
        						data : [ '最高分' ,'最低分','平均分']
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
        						name : '最高分',
        						type : 'bar',
        						itemStyle : {
        							normal : {
        								label : {
        									show : true,
        									position : 'top',
        									formatter : '{c}分'
        								}
        							}
        						},
        						data : y
        					} ,{
        						name : '最低分',
        						type : 'bar',
        						itemStyle : {
        							normal : {
        								label : {
        									show : true,
        									position : 'top',
        									formatter : '{c}分'
        								}
        							}
        						},
        						data : y1
        					} ,{
        						name : '平均分',
        						type : 'line',
        						itemStyle : {
        							normal : {
        								label : {
        									show : true,
        									position : 'top',
        									formatter : '{c}分'
        								}
        							}
        						},
        						data : y2
        					} ]
        				};
        				
        				myBarChart.setOption(baroption);
        				//myPieChart.setOption(pieoption);
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
