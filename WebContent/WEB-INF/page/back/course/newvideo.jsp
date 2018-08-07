<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" /> 
<link rel="stylesheet" href="${contextPath}/static/assets/css/chosen.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/js/laypage/skin/laypage.css" />

<div class="row">
	<div class="col-xs-12">
		<div style="padding-left: 8px;height:36px;"><span style="float: left; line-height: 35px;font-size: 16px">选择上传时间：</span>
		   <input  class="laydate-icon" id="laydate" type="text" style=" height:35px;float:left; "  />
			 
			<div style="float: left; "><span style="float: left; line-height: 30px;font-size: 16px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关键字：</span>
						<input type="text" id="searchname" style="" /> </div>
			<button id="search" class="btn btn-white btn-primary"> 搜索 
			</button> 
			<div style="clear: all;"></div>
		</div>  
		<div class="col-xs-6 col-sm-3 pricing-box" style="width:40%; margin-top: 3px; ">
				<div class="widget-box widget-color-blue" >
					<div class="widget-header">
						<h5 class="widget-title bigger lighter">视频列表</h5>
					</div>  
					<div class="widget-body widget-main" style="height: 600px;position: relative; ">
						 
						<ul class="list-unstyled spaced2" id="ysp">  
							 
						</ul> 
						<hr /> 
						          <!--分页-->
         <div class="pages" style="position: absolute; bottom: 5px;" id="laypages"> </div>  
         
   
  
  <script src="${contextPath}/static/assets/js/laypage/laypage.js"></script> 
              
   
					</div>  
				</div>
		</div> 
		<div style="clear: both;"></div>
	  
		<script type="text/javascript">
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
		</script>
 
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null,  
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",  
				        "${contextPath}/static/assets/js/chosen.jquery.js",   
				        "${contextPath}/static/assets/js/bootbox.js",
				        "${contextPath}/static/assets/js/jquery-ui.custom.js",  
	               		"${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
	               		"${contextPath}/static/assets/js/date-time/bootstrap-timepicker.js", 
	              		"${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", 
	              		"${contextPath}/static/assets/js/laypage/laypage.js", 
	              		"${contextPath}/static/laydate/laydate.js",  
	              		null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) { 
        		//datepicker plugin
        		//link
        		$('.date-picker').datepicker({
        			autoclose: true,
        			todayHighlight: true
        		})
        		//show datepicker when clicking on the icon
        		.next().on(ace.click_event, function(){
        			$(this).prev().focus();
        		});
        	
        		//or change it into a date range picker
        		$('.input-daterange').datepicker({autoclose:true});
        	
        	 
        	 //==========================
        		
        		if(!ace.vars['touch']) {
        			$('.chosen-select').chosen({allow_single_deselect:true}); 
        			//resize the chosen on window resize
        	 
        			$(window)
        			.off('resize.chosen')
        			.on('resize.chosen', function() { 
        				$('.chosen-select').each(function() { 
        					 var $this = $(this);
        					 $this.next().css({'width': $this.parent().width()});
        				});
        			}).trigger('resize.chosen'); 
        		}
        		laydate({
        		    elem: '#laydate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        		    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        		});
        		 function demo(curr){
        	    	 var name=$("#searchname").val();
        			 var time=$("#laydate").val();
        			 var date=new Date().getTime(); 
        		    $.getJSON('${contextPath}/sys/test/search?date='+date, {
        		        page: curr || 1 ,
        		        time:time,
        		        name:name, 
        		    }, function(res){   
        		    	 //alert(res.content);
        		        $("#ysp").html(res.content); 
        		        laypage({
        		            cont: 'laypages', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
        		            pages: res.pages, //通过后台拿到的总页数
        		            curr: curr || 1, //当前页
        		            jump: function(obj, first){ //触发分页后的回调
        		                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
        		                    demo(obj.curr);
        		                }
        		            }
        		        });
        		    });
        		};
        		//运行
        		demo();
        	  
        		$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/test/test",  
			   		dataType:"json", 
			       	success:function(data){ 
			       		
			       		demo(); 
			       	} 
			    });
    			$("#ysp").on( "mouseover","li",function(){   
    				$(this).siblings().css("background-color","#fff");
    				$(this).css("background-color","#eee");
    			});
        		//删除
        		$("#ysp").on( "click","input[name='del']",function(){ 
        			var val=$(this).parent().find("span").html();
        			if(!confirm("确定删除\""+val+"\"吗！")){   
        				return;
        			}  
        			$.ajax({
    			        type:"post", 
    			        url:"${contextPath}/sys/test/del", 
    			        data:{"name":val },
    			   		dataType:"json", 
    			       	success:function(data){ 
    			       		demo(); 
    			       	} 
    			    });
        			
        			
        		});
        		//查询
        		$("#search").click(function(){
        			demo(1); 
        		});
        		  
        	});
        });
</script>
