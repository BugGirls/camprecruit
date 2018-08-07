<%@page import="org.springframework.context.annotation.Import"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<div class="row">

	<div class="col-xs-12">
		<div id="PieChart" class="col-xs-12" style="height:400px;"></div>
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
        		
        		loading();
        		function loading(){
	        		var myPieChart = echarts.init(document.getElementById('PieChart'));
	        		$.ajax({
	        			type : "post",
	        			url : " ${contextPath}/sys/alliancepromotion/getAgentPromotionStateList",
	        			success : function(data) {
							console.log(data)
	        				var list = eval(data);
							console.log(list)
	        				var x = new Array();
	        				var y = new Array();
	        				var z = new Array();
	        				var labelTop = {
	        		        	    normal : {
	        		        	        label : {
	        		        	            show : true,
	        		        	            position : 'center',
	        		        	            formatter : '{b}',
	        		        	            textStyle: {
	        		        	                baseline : 'bottom'
	        		        	            }
	        		        	        },
	        		        	        labelLine : {
	        		        	            show : false
	        		        	        }
	        		        	    }
	        		        	};
	        		       	var labelFromatter = {
	        		       	    normal : {
	        		       	        label : {
	        		       	            formatter : function (params){
	        		       	                return parseFloat((100 - params.value).toFixed(2)) + '%'
	        		       	            },
	        		       	            textStyle: {
	        		       	                baseline : 'top'
	        		       	            }
	        		       	        }
	        		       	    },
	        		       	}
	        		       	var labelBottom = {
	        		       	    normal : {
	        		       	        color: '#ccc',
	        		       	        label : {
	        		       	            show : true,
	        		       	            position : 'center'
	        		       	        },
	        		       	        labelLine : {
	        		       	            show : false
	        		       	        }
	        		       	    },
	        		       	    emphasis: {
	        		       	        color: 'rgba(0,0,0,0)'
	        		       	    }
	        		       	};
	        		       	var radius = [40, 55];
	        				var sum = 0;
	        				var xt = 10;
	        				var yt = 30;
	        				var xf = 0;
	        				var yf = 10;
	        				
	        				$.each(list, function(key, value) {
		        				console.log(value.participation-value.reservationnum);
	        					x[key] = value.name;
	        					y[key] = value.zongshu;
	        					var v = parseFloat((value.reservationnum / value.participation * 100).toFixed(2));
	        					console.log(v)
	        					var datat = [
	     	               	                {name:'other', value:100-v, itemStyle : labelBottom},
	    	               	                {name:value.name, value:v,itemStyle : labelTop}
	    	               	            ];
	        					z[key] = new piedata([xt +'%', yt +'%'], labelFromatter,datat,xf +'%', yf +'%');
	        					sum += value.zongshu;
	        					if((key+1)%5==0){
		        					xt = 10;
		        					yt += 20;
		        					
		        					xf = 0;
		        					yf += 30;
						    	}else{
						    		xt += 20;
						    		xf += 20;
						    	}
	        				});
	        				
	        				
	        		       	option = {
	        		       	    legend: {
	        		       	        x : 'center',
	        		       	        y : 'center',
	        		       	        data:x
	        		       	    },
	        		       	    title : {
	        		       	        text: '活动进展',
	        		       	        subtext: '进行中',
	        		       	        x: 'center'
	        		       	    },
	        		       	    toolbox: {
	        		       	        show : true,
	        		       	        feature : {
	        		       	            dataView : {show: true, readOnly: false},
	        		       	            magicType : {
	        		       	                show: true, 
	        		       	                type: ['pie', 'funnel'],
	        		       	                option: {
	        		       	                    funnel: {
	        		       	                        width: '20%',
	        		       	                        height: '30%',
	        		       	                        itemStyle : {
	        		       	                            normal : {
	        		       	                                label : {
	        		       	                                    formatter : function (params){
	        		       	                                        return '未报名\n' + params.value + '%\n'
	        		       	                                    },
	        		       	                                    textStyle: {
	        		       	                                        baseline : 'middle'
	        		       	                                    }
	        		       	                                }
	        		       	                            },
	        		       	                        } 
	        		       	                    }
	        		       	                }
	        		       	            },
	        		       	            restore : {show: true},
	        		       	            saveAsImage : {show: true}
	        		       	        }
	        		       	    },
	        		       	    series : z
	        		       	};
	        				
	        				myPieChart.setOption(option);
	        			}
	        		});
	
        			
        		}

        		
        		function piedata(center, itemStyle,data,x,y) {
        			this.type = 'pie';
        			this.center = center;
        			this.x=x;
        			this.y=y;
        			this.radius = [40, 55];
        			this.itemStyle = itemStyle;
        			this.data = data;
        		}
        		
        		
        		
        	});
        });
        
        
        
        	                    
        
</script>
