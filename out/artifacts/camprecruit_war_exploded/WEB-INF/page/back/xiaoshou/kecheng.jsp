<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet"
	href="${contextPath}/static/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet"
	href="${contextPath}/static/assets/css/chosen.css" />

<div class="page-header">
	<h1>购买课程</h1>
</div>
<!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" >

			<div class="col-xs-12 col-sm-4">
				<div class="widget-box" style="border: none;">

					<div class="space-6"></div>

					<!--  -->
					<div class="widget-body">
						<div class="widget-main">
							<div>
								<label for="form-field-select-1">选择客户</label>
								 <select class="chosen-select form-control" id="form-field-select-1">
									<option value=""></option>
									<c:forEach items="${ulist }" var="u">
										<option value="${u.id }">${u.nickname }</option>

									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<!--  -->
					<div class="hr hr-16 hr-dotted"></div>
					
					<!-- 选择课程 -->
					<div class="widget-main">
						<div>
							<div class="row">
								<div class="col-sm-6">
									<span class="bigger-110">选择课程</span>
								</div>
								<!-- /.span --> 
							</div> 
							<div class="space-2"></div> 
							<select  class="chosen-select form-control" id="form-field-select-4" >
								<option value=""></option>
								<c:forEach items="${list }" var="d">
									<option value="${d.id }">${d.name }</option>
								</c:forEach>
							</select> <br>
							<span class="bigger-110">购买年数：</span><input type="text" class="input-mini" id="spinner3" />
							<button type="button" class="btn btn-sm btn-success"id="tianjia">
								确定添加 
				  			</button> 
						</div>
					</div>
				</div>
				<div class="hr hr-16 hr-dotted"></div>
				<table width="140%" id="table">
					<tr> 
						<td width="25%">课程名称</td> 
						<td width="15%">年数</td> 
						<td width="15%">折扣</td>
						<td width="15%">维护费</td>
						<td width="15%">单价</td>
						<td width="15%">操作</td> 
					</tr>
					 
				</table>
				<div class="hr hr-16 hr-dotted"></div>
				<div class="widget-main"> 
					折扣率（如“9.5”，没有折扣填写“10”）： <input id="zhekou" type="text" style="height: 27px;width: 102px;" value="10"/><br><br>
					总价： <input id="zongjia" type="text" style="height: 27px;" />
				</div>
				<div class="hr hr-16 hr-dotted"></div> 
				
				<div class="widget-main"> 
					  
				  	 <button type="button" class="btn btn-sm btn-primary"id="submit">
							提交<i class='ace-icon fa fa-arrow-right icon-on-right bigger-110'></i>
				  	</button> 
				</div>
			</div>
		</form>

		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">
	var scripts = [null,"${contextPath}/static/assets/js/jquery-ui.custom.js",
	               "${contextPath}/static/assets/js/jquery.ui.touch-punch.js",
	               "${contextPath}/static/assets/js/chosen.jquery.js",
	               "${contextPath}/static/assets/js/fuelux/fuelux.spinner.js",
	               "${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
	               "${contextPath}/static/assets/js/date-time/bootstrap-timepicker.js",
	               "${contextPath}/static/assets/js/date-time/moment.js",
	               "${contextPath}/static/assets/js/date-time/daterangepicker.js",
	               "${contextPath}/static/assets/js/date-time/bootstrap-datetimepicker.js",
	               "${contextPath}/static/assets/js/bootstrap-colorpicker.js",
	               "${contextPath}/static/assets/js/jquery.knob.js", 
	               "${contextPath}/static/assets/js/bootbox.js",
	               "${contextPath}/static/assets/js/jquery.autosize.js",
	               "${contextPath}/static/assets/js/jquery.inputlimiter.1.3.1.js",
	               "${contextPath}/static/assets/js/jquery.maskedinput.js",
	               "${contextPath}/static/assets/js/bootstrap-tag.js", null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	  //inline scripts related to this page
		 jQuery(function($) {
			 $('#spinner3').ace_spinner({
				 value:1,
				 min:1,
				 max:100,
				 step:1,
				 on_sides: true,
				 icon_up:'ace-icon fa fa-plus smaller-75', 
				 icon_down:'ace-icon fa fa-minus smaller-75',
				 btn_up_class:'btn-success',
				 btn_down_class:'btn-danger'
			 });
	  
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
		
	 	$("#table").on("click","#del",function(){
	 		var dalhtml=$(this).parent().parent();
	 		var flag = confirm("确定删除！");
	 		if(flag){
	 			dalhtml.remove(); 
	 		} 
	 		 jisuan();
		});
		
	 
		$("#form-field-select-1").change(function(){ 
			if($("#form-field-select-1").val()==""){
		    	alert("请选择用户！");
		    	return;
		    } 
		});
		$("#form-field-select-4").change(function(){ 
			if($("#form-field-select-4").val()==""){
		    	alert("请选择课程！");
		    	return;
		    }
		}); 
		 
		$("#zhekou").change(function(){
			 $("#zongjia").val(""); 
			if($("#zhekou").val()==""){
				alert("折扣不能为空！");
				return;
			} 
			 if(!regk.test($("#zhekou").val())){ 
			        alert("折扣不能小于0，最多1位小数!");  
			        return ;
			    }else if($("#zhekou").val()>10){
			    	alert("折扣不能大于10");
			    	 return ;
			    }   
		});
		//=======================================================================================
		$("#tianjia").on("click",function(){
			
			var year=$("#spinner3").val();
			var name=$("#form-field-select-4").find("option:selected").text();
			var userid=$("#form-field-select-1").val();
			var cid=$("#form-field-select-4").val();   
			
			if(userid==""){
				 alert("请选择用户");  
			        return ;
			}
			if(year<0){
				alert("请选择年份");
				return;
			} 
			if(cid==""){
				alert("请选择课程");
				return;
			} 
			var newtd="";
			var tf=true;
	 		for(var i=0;i<$("#table tr").length;i++){
	 			var tdhtml= $("#table tr").eq(i).find("td");
	 			var pname = tdhtml.eq(0).html();
	 			//alert(pname+"=dsd="+name);
	 			
	 			if(name==pname){ 
	 				year=parseInt(year)+parseInt(tdhtml.eq(1).html()); 
	 				newtd=tdhtml;
	 				tf=false;
	 			}  	
	 		}
			 $.ajax({
				 async:"false",
  	  			 type:"post",
  	  			 url:"${contextPath}/sys/kecheng/addcourse",
   	          	 data:{"year":year,"cid":cid,"tf":tf  },
  	  			 dataType:"json", 
  	   		 	 success:function(data){ 
  	   		 	    var price=data.price;
  	   		 		var weihufei=data.whf;	
  	               	var zhekou=data.zhekou;
  	               	if(!tf){
  	               		newtd.eq(1).html(year);
  	          			newtd.eq(2).html(zhekou);
  	          			newtd.eq(3).html(weihufei);
  	          			newtd.eq(4).html(price);
  	               	}else{
  	               		$("#table").append("<tr>"+ 
  	  						"<td>"+name+"</td> "+
  	  						"<td>"+year+"</td> "+
  	  						"<td>"+zhekou+"</td> "+
  	  						"<td>"+weihufei+"</td> "+
  	  						"<td>"+price+"</td> "+
  	  						"<td> <i class='ace-icon fa fa-trash-o  icon-only' id='del'></i> </td>"+
  	  						"</tr>"); 
  	               	} 	 
  	 				$("#form-field-select-4").val("");	
  	 				$("#form_field_select_4_chosen").find("a").html("<span style='color:#999;'>Select an Option</span>");
  	 			 jisuan();
  	  			} 
          }); 
			
		});	
		//===========
		$("#zhekou").keyup(function(){
			var v=$("#zhekou").val();
			if(v.substr(v.length-1,v.length-1)==".")
				return;
			if($("#zhekou").val())
			jisuan();
		}); 
		
		$("#submit").click(function(){ 
			jisuan();
			bootbox.confirm("购买的课程一共"+ $("#zongjia").val()+"元,确定购买这些课程吗?", function(result) {
				if(!result) {
					 return;
				}else{
					var names="";
				    var years="";
				    var zhekous="";
				    var weihufeis=""; 
				    for(var i=1;i< $("#table tr").size();i++){ 
						names=names+ $("#table tr").eq(i).children('td').eq(0).text(); 
						names+=",";
						years=years+ $("#table tr").eq(i).children('td').eq(1).text(); 
						years+=","; 
						zhekous=zhekous+ $("#table tr").eq(i).children('td').eq(2).text(); 
						zhekous+=",";
						weihufeis=weihufeis+ $("#table tr").eq(i).children('td').eq(3).text(); 
						weihufeis+=",";
					}  
		               	 $.ajax({
		       	  			 type:"post",
		       	  			 url:"${contextPath}/sys/kecheng/saveAdminGoumai",
		       	  			 data:{"zhekous": zhekous, "names":names,"years":years,"weihufeis":weihufeis,"userid":$("#form-field-select-1").val() ,"allzhekou":$("#zhekou").val()},
		     				 dataType:"json", 
		       	   		 	 success:function(data){  
		       	                var me=data.a; 
		       	                if(me=="success"){
		       	                 	alert("购买成功！");
		       	              		self.location.reload();
		       	                } 
		       	  			} 
		               });
				}
			}); 
		});
		  
	});
	}); 
	 function jisuan(){
		//var reg = new RegExp("^[0-9]*$");
		var regk=new RegExp("^[0-9]+(.[0-9]{1})?$"); 
		if($("#form-field-select-1").val()==""){
	    	alert("请选择用户！");
	    	return;
	    }  
		if($("#zhekou").val()==""){
			alert("折扣不能为空！");
			return;
		}  
	    if(!regk.test($("#zhekou").val())){ 
	        alert("折扣不能小于0，最多1位小数!");  
	        return ;
	    }else if($("#zhekou").val()>10){
	    	alert("折扣不能大于10");
	    	 return ;
	    } 
	    var names="";
	    var years="";
	    var zhekous="";
	    var weihufeis=""; 
	    for(var i=1;i< $("#table tr").size();i++){ 
			names=names+ $("#table tr").eq(i).children('td').eq(0).text(); 
			names+=",";
			years=years+ $("#table tr").eq(i).children('td').eq(1).text(); 
			years+=","; 
			zhekous=zhekous+ $("#table tr").eq(i).children('td').eq(2).text(); 
			zhekous+=",";
			weihufeis=weihufeis+ $("#table tr").eq(i).children('td').eq(3).text(); 
			weihufeis+=",";
		}  
	    $.ajax({
  			 type:"post",
  			 url:"${contextPath}/sys/kecheng/jisuanzongjia",
	         data:{"zhekous": zhekous, "names":names,"years":years,"weihufeis":weihufeis,"userid":$("#form-field-select-1").val() ,"allzhekou":$("#zhekou").val()},
  			 dataType:"json", 
   		 	 success:function(data){  
                var me=data.zongjia; 
                $("#zongjia").val(me); 
              
  			}
		}); 
	    
	 }
</script>