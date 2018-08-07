<%@page import="org.springframework.context.annotation.Import"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<div class="row">
<div class="panel panel-default">
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
  <!-- Default panel contents -->
  <div class="panel-heading">门店吸粉量排行榜</div>

  <!-- Table -->
  <table class="table">
        <thead>
          <tr>
            <th>排名</th>
            <th>店名</th>
            <th>粉丝总数</th>
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
  <div class="panel-heading">门店会员粉丝排行榜</div>

  <!-- Table -->
  <table class="table">
        <thead>
          <tr>
            <th>排名</th>
            <th>店名</th>
            <th>粉丝总数</th>
          </tr>
        </thead>
        <tbody id="tab_user_content_">
        </tbody>
      </table>
</div>
	<div class="col-xs-12">
		<div id="userBarChart" class="col-xs-6" style="height:400px;"></div>
		<div id="userPieChart" class="col-xs-6" style="height:400px;"></div>
	</div>
</div>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/echarts-all.js", null ];
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
        			loading();
        		});
        		
        		loading();
        		function loading(){
	        		var myBarChart = echarts.init(document.getElementById('BarChart'));
	        		var myPieChart = echarts.init(document.getElementById('PieChart'));
	        		var userBarChart = echarts.init(document.getElementById('userBarChart'));
	        		var userPieChart = echarts.init(document.getElementById('userPieChart'));
	        		$.ajax({
	        			type : "post",
	        			url : " ${contextPath}/sys/storefans/getStoreFansRank",
	        			data:{"startdate":$("#search_date_start_").val(),"enddate":$("#search_date_end_").val()},
	        			success : function(data) {
	
	        				var list = eval(data);
	        				var x = new Array();
	        				var y = new Array();
	        				var z = new Array();
	        				var sum = 0;
	        				var elemnts = "";
	        				$("#tab_content_").empty();
	        				$.each(list, function(key, value) {
	        					elemnts += "<tr><td>" + (key + 1) + "</td>" + "<td>"
	        							+ value.storename + "</td>" + "<td>" + value.zongshu
	        							+ "</td></tr>";
	        					x[key] = value.storename;
	        					y[key] = value.zongshu;
	        					z[key] = new storerank(value.zongshu, value.storename);
	        					sum += value.zongshu;
	        				});
	        				elemnts += "<tr><td>总计</td>" + "<td></td>" + "<td>" + sum
	        							+ "</td></tr>";
	        				$("#tab_content_").append(elemnts);
	        				var baroption = {
	        					title : {
	        						text : '门店吸粉量排行榜'
	        					},
	        					tooltip : {},
	        					legend : {
	        						data : [ '粉丝量' ]
	        					},
	
	        					calculable : true,
	        					grid : {
	        						borderWidth : 0,
	        						y : 80,
	        						y2 : 60
	        					},
	        					xAxis : [ {
	        						type : 'category',
	        						show : false,
	        						data : x
	        					} ],
	        					yAxis : [ {
	        						type : 'value',
	        						show : false
	        					} ],
	        					series : [ {
	        						name : '粉丝量',
	        						type : 'bar',
	        						itemStyle : {
	        							normal : {
	        								color : function(params) {
	        									// build a color map as your need.
	        									var colorList = [ '#C1232B', '#B5C334',
	        											'#FCCE10', '#E87C25', '#27727B',
	        											'#FE8463', '#9BCA63', '#FAD860',
	        											'#F3A43B', '#60C0DD', '#D7504B',
	        											'#C6E579', '#F4E001', '#F0805A',
	        											'#26C0C0' ];
	        									return colorList[params.dataIndex]
	        								},
	        								label : {
	        									show : true,
	        									position : 'top',
	        									formatter : '{b}\n{c}人'
	        								}
	        							}
	        						},
	        						data : y
	        					} ]
	        				};
	        				var pieoption = {
	        					title : {
	        						text : '门店吸粉量排行榜'
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
	        						name : '门店',
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
	
	        		$.ajax({
	        			type : "post",
	        			url : " ${contextPath}/sys/storefans/getStoreUserFansRank",
	        			data:{"startdate":$("#search_date_start_").val(),"enddate":$("#search_date_end_").val()},
	        			success : function(data) {
	        				var list = eval(data);
	        				var x = new Array();
	        				var y = new Array();
	        				var z = new Array();
	        				var sum = 0;
	        				var elemnts = "";
	        				$("#tab_user_content_").empty();
	        				$.each(list, function(key, value) {
	        					elemnts += "<tr><td>" + (key + 1) + "</td>" + "<td>"
	        							+ value.storename + "</td>" + "<td>" + value.zongshu
	        							+ "</td></tr>";
	        					x[key] = value.storename;
	        					y[key] = value.zongshu;
	        					z[key] = new storerank(value.zongshu, value.storename);
	        					sum += value.zongshu;
	        				});
	        				elemnts += "<tr><td>总计</td>" + "<td></td>" + "<td>" + sum
							+ "</td></tr>";
	        				$("#tab_user_content_").append(elemnts);
	        				var baroption = {
	        						title : {
	        							text : '门店会员粉丝排行榜'
	        						},
	        						tooltip : {},
	        						legend : {
	        							data : [ '会员人数' ]
	        						},
	
	        						calculable : true,
	        						grid : {
	        							borderWidth : 0,
	        							y : 80,
	        							y2 : 60
	        						},
	        						xAxis : [ {
	        							type : 'category',
	        							show : false,
	        							data : x
	        						} ],
	        						yAxis : [ {
	        							type : 'value',
	        							show : false
	        						} ],
	        						series : [ {
	        							name : '会员人数',
	        							type : 'bar',
	        							itemStyle : {
	        								normal : {
	        									color : function(params) {
	        										// build a color map as your need.
	        										var colorList = [ '#C1232B', '#B5C334',
	        												'#FCCE10', '#E87C25', '#27727B',
	        												'#FE8463', '#9BCA63', '#FAD860',
	        												'#F3A43B', '#60C0DD', '#D7504B',
	        												'#C6E579', '#F4E001', '#F0805A',
	        												'#26C0C0' ];
	        										return colorList[params.dataIndex]
	        									},
	        									label : {
	        										show : true,
	        										position : 'top',
	        										formatter : '{b}\n{c}人'
	        									}
	        								}
	        							},
	        							data : y
	        						} ]
	        				};
	        				var pieoption = {
	        					title : {
	        						text : '门店会员粉丝排行榜'
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
	        						name : '门店',
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
	        				userBarChart.setOption(baroption);
	        				userPieChart.setOption(pieoption);
	        			}
	        		});
        			
        		}

        		function storerank(value, name) {
        			this.value = value;
        			this.name = name;
        		}
        		
        	});
        });
</script>
