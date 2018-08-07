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
  <div class="panel-heading">活动及参与人数统计</div>

  <!-- Table -->
  <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>活动名称</th>
            <th>报名人数</th>
            <th>已付款人数</th>
          </tr>
        </thead>
        <tbody id="tab_content_">
        </tbody>
      </table>
</div>

	<div class="col-xs-12">
		<div id="BarChart" class="col-xs-6" style="height:400px;"></div>
		<div id="PieChart" class="col-xs-6" style="height:400px;"></div>
	</div>

<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">会员参与次数统计</div>

  <!-- Table -->
  <table class="table" id="grid-table">
        <thead>
          <tr>
            <th>会员ID</th>
            <th>参与活动名</th>
            <th>参与次数</th>
          </tr>
        </thead>
        <tbody id="tab_user_content_">
        </tbody>
   </table>
   <div id="grid-pager"></div>
</div>
	<div class="col-xs-12">
		<div id="userBarChart" class="col-xs-6" style="height:400px;"></div>
		<div id="userPieChart" class="col-xs-6" style="height:400px;"></div>
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
        		var myPieChart = echarts.init(document.getElementById('PieChart'));
        		$.ajax({
        			type : "post",
        			url : " ${contextPath}/sys/TzzSalesPromotion/getPromotionParticipants",
        			success : function(data) {
						
        				var list = eval(data);
        				var x = new Array();
        				var y = new Array();
        				var y1 = new Array();
        				
        				var z = new Array();
        				var elemnts = "";
        				$.each(list, function(key, value) {
        					elemnts += "<tr><td>" + (key + 1) + "</td>" + "<td>"
        							+ value.promotionName + "</td>" + "<td>" + value.participants
        							+ "</td><td>" + value.payedNum
        							+ "</td></tr>";
        					x[key] = value.promotionName;
        					y[key] = value.participants;
        					y1[key] = value.payedNum;
        					z[key] = new promotionParticipants(value.participants, value.promotionName);
        				});
        				$("#tab_content_").append(elemnts);
        				var baroption = {
        					title : {
        						text : '活动参与人数统计'
        					},
        					tooltip : {},
        					legend : {
        						data : [ '报名人数' ,'已付款人数']
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
        						name : '报名人数',
        						type : 'bar',
        						itemStyle : {
        							normal : {
        								label : {
        									show : true,
        									position : 'top',
        									formatter : '{c}人'
        								}
        							}
        						},
        						data : y
        					} ,{
        						name : '已付款人数',
        						type : 'bar',
        						itemStyle : {
        							normal : {
        								label : {
        									show : true,
        									position : 'top',
        									formatter : '{c}人'
        								}
        							}
        						},
        						data : y1
        					} ]
        				};
        				var pieoption = {
        					title : {
        						text : '活动参与人数统计'
        					},
        					tooltip : {
        						trigger : 'item',
        						formatter : "{b} : {c}人 ({d}%)"
        					},
        					legend : {
        						orient : 'vertical',
        						left : 'left',
        						data : x,
        						show : false
        					},
        					series : [ {
        						name : '活动',
        						type : 'pie',
        						radius : '55%',
        						center : [ '50%', '60%' ],
        						data : z,
        						itemStyle : {
        							emphasis : {
        								shadowBlur : 10,
        								shadowOffsetX : 0,
        								shadowColor : 'rgba(0, 0, 0, 0.5)'
        							}
        						}
        					} ]
        				};
        				myBarChart.setOption(baroption);
        				myPieChart.setOption(pieoption);
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

        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/TzzSalesPromotion/participateTimesList",
        			datatype : "json",
        			height : 400,
        			colNames : ['微信ID', '活动名称','参与次数','手机号','宝贝姓名'],
        			colModel : [ {
        				name : 'openid',
        				index : 'openid',
        				label : '微信ID',
        				width : 300,
        				sorttype : "long",
        				search : false
        			},{
        				name : 'promotionname',
        				index : 'promotionname',
        				label : '活动名称',
        				width : 300,
        				sorttype : "long",
        				search : false
        			}, {
        				name : 'participateTimes',
        				index : 'participateTimes',
        				label : '参与次数',
        				//width : 180,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'phone',
        				index : 'phone',
        				label : '手机号',
        				//width : 180,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'babyName',
        				index : 'babyName',
        				label : '宝贝姓名',
        				//width : 180,
        				editable : true,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}],
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
        					//styleCheckbox(table);
        					//updateActionIcons(table);
        					//updatePagerIcons(table);
        					//enableTooltips(table);
        				}, 0);
        			}
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

        		function promotionParticipants(value, name) {
        			this.value = value;
        			this.name = name;
        		}
        	});
        });
</script>
