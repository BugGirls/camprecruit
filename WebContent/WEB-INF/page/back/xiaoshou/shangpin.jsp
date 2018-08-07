<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet"
	href="${contextPath}/static/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet"
	href="${contextPath}/static/assets/css/chosen.css" />

<div class="page-header">
	<h1>购买产品</h1>
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
								 <select class="chosen-select  form-control" id="form-field-select-1">
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
					<div class="widget-main">
						<div>
							<div class="row">
								<div class="col-sm-6">
									<span class="bigger-110">选择商品</span>
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
							<input type="text" class="input-mini" id="spinner3" />
							<button type="button" class="btn btn-sm btn-success"id="tianjia">
							确定添加 
				  			</button> 
						</div>
					</div>
				</div>
				<div class="hr hr-16 hr-dotted"></div>
				<table width="100%" id="table">
					<tr> 
						<td width="33%">商品</td> 
						<td width="33%">件数</td> 
						<td width="33%">操作</td>
					</tr>
					 
				</table>
				<div class="hr hr-16 hr-dotted"></div>
				<div class="widget-main">
					是否付款：是<input type="radio" id="d2" name="ra" value="1" checked="checked"/>
							   否<input type="radio" id="d2" name="ra" value="0" /><br><br> 
					折扣率（如“9.5”，没有折扣填写“10”）： <input id="zhekou" type="text" style="height: 27px;width: 102px;" value="10"/><br><br>
					总价： <input id="zongjia" type="text" style="height: 27px;"/>
				</div>
				 <div class="hr hr-16 hr-dotted"></div> 
				 <div style="margin-left: 15px;">
					 <span>收货人姓名</span>：<input type="text" id="orderusername" maxlength="7" style="height: 27px;"/><br><br>
					 <span style="letter-spacing:13px;">手机</span>号：<input type="text" id="orderuserphone" maxlength="11" style="height: 27px;"/><br><br>
					 <span style="letter-spacing:38px;">邮</span>编：<input type="text" id="orderuserpostcode" maxlength="6" style="height: 27px;"/><br><br>
					 <span style="letter-spacing:4px;">收货地</span>址：<input type="text" id="orderuseraddr"  style="height: 27px;width:430px;"/><br><br>
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


function isChinese(obj) {    
  if (!(/[\u4e00-\u9fa5]+/).test(obj))    
  {    
      return false;    
  }    
  return true;    
}   
function isMobile(str) {    
    if((/^(13[0-9]|15[012356789]|17[5678]|18[0-9]|14[57])[0-9]{8}$/).test(str))    
      {    
          return true;    
      }    
    return false;    
} 
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
	 	$("#table").on( "click","i[name='del']",function(){
	 		 var del=$(this).parent().parent();
	 		 if(confirm("确定删除吗！")){
	 			del.remove(); 
	 		 } 
		});
		
	 	$("#tianjia").click(function(){
	 		if($("#form-field-select-1").val()==""){
		    	alert("请选择用户！");
		    	return;
		    } 
	 		if($("#form-field-select-4").val()==""){
		    	alert("请选择商品！");
		    	return;
		    }  
	 		var pro=$("#form-field-select-4").find("option:selected").text();
	 		var num=$("#spinner3").val(); 
	 		//alert($("#table tr"));
	 		var tf=true;
	 		for(var i=0;i<$("#table tr").length;i++){
	 			var tdhtml= $("#table tr").eq(i).find("td");
	 			var pname = tdhtml.eq(0).html();
	 			if(pro==pname){ 
	 				tdhtml.eq(1).html(parseInt(tdhtml.eq(1).html())+parseInt(num)); 
	 				tf=false;
	 			}  	
	 		}
	 		 if(tf){
	 			$("#table").append("<tr>"+ 
						"<td>"+pro+"</td> "+
						"<td>"+num+"</td> "+
						"<td> <i class='ace-icon fa fa-trash-o  icon-only' name='del'></i> </td>"+
						"</tr>"); 
	 		 } 
	 		 $("#spinner3").val(1);
	 		$("#form-field-select-4").val("");	
	 		$("#form_field_select_4_chosen").find("a").html("<span style='color:#999;'>Select an Option</span>");
		
	 		jisuan();
	 	});  
	   
		$("#zhekou").change(function(){
			var v=$("#zhekou").val();
			if(v.substr(v.length-1,v.length-1)==".")
				return;
			if($("#zhekou").val())
			jisuan();
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
	
		 
		$("#submit").click(function(){
			if($("#zongjia").val()=="" ){
				alert("请计算总金额！");
				return false;
			}
			if($("#zongjia").val()==0 ){
				alert("请重新选择！");
				return false;
			}
			if($("#orderusername").val()==""){
				alert("请输入收货人姓名！");
				return false;
			} 
			if(!isChinese($("#orderusername").val())){
				alert("请输入中文收货人姓名！");
				return false;
			} 
			if(!isMobile($("#orderuserphone").val())){
				alert("请输入正确手机号！");
				return false;
			} 
			if($("#orderuserpostcode").val()==""){
				alert("请输入邮编！");
				return false;
			}if(isNaN($("#orderuserpostcode").val())||$("#orderuserpostcode").val().length!=6){
				alert("请输入正确邮编号！ ");
				return false;
			}
			if($("#orderuseraddr").val()==""){
				alert("请输入收货地址！");
				return false;
			}
			bootbox.confirm("确定购买这些商品吗?", function(result) {
				if(!result) {
					 return false;
				}else{ 
					var dd=$("#d2").val();
					var zhekou= $("#zhekou").val();
				 	var names=""; 
				    var nums="";
				    var userid=$("#form-field-select-1").val();
					for(var i=1;i< $("#table tr").size();i++){ 
						names=names+ $("#table tr").eq(i).children('td').eq(0).text(); 
						names+=",";
						nums=nums+ $("#table tr").eq(i).children('td').eq(1).text(); 
						nums+=","; 
					}
					 $.ajax({
			  			 type:"post",
			  			 url:"${contextPath}//sys/shangpin/saveAdminOrder",
			  			 //orderusername orderuseraddr  orderuserpostcode
		 	          	 data:{"names":names,
		 	          		 	"nums":nums,
		 	          		 	"zhekou":zhekou,
		 	          		 	"dd":dd,
		 	          		 	"userid":userid ,
		 	          		 	"orderuserphone":$("#orderuserphone").val(),
		 	          		 	"orderusername":$("#orderusername").val(),
		 	          		 	"orderuseraddr":$("#orderuseraddr").val(),
		 	          		 	"orderuserpostcode":$("#orderuserpostcode").val()
		 	          		 	},
			  			 dataType:"json", 
			   		 	 success:function(data){   
			                if(data.a=="success"){ 
			      	   		  alert("购买成功！"); 
			       	   	 	}
			  			}
					});  
				}
			}); 
		});
		  
	});
	});
	//计算总金额
	 function  jisuan(){ 
		var regk=new RegExp("^[0-9]+(.[0-9]{1})?$");  
		if($("#zhekou").val()==""){
			$("#zongjia").val("");
			alert("折扣不能为空！");
			return;
		}  
	    if(!regk.test($("#zhekou").val())){ 
	    	$("#zongjia").val("");
	        alert("折扣不能小于0，最多1位小数!");  
	        return ;
	    }else if($("#zhekou").val()>10){ 
	    	$("#zongjia").val("");
	    	alert("折扣不能大于10"); 
	    	return ;
	    } 
	    var zhekou= $("#zhekou").val();
	    
	    var names=""; 
	    var nums="";
		for(var i=1;i< $("#table tr").size();i++){ 
			names=names+ $("#table tr").eq(i).children('td').eq(0).text(); 
			names+=",";
			nums=nums+ $("#table tr").eq(i).children('td').eq(1).text(); 
			nums+=","; 
		}
	    $.ajax({
 			 type:"post",
 			 url:"${contextPath}/sys/shangpin/getprice",
          	 data:{"names":names,"nums":nums,"zhekou":zhekou },
 			 dataType:"json", 
  		 	 success:function(data){   
               if(data.a=="1"){ 
     	   		  alert("折扣太低！");
     	   		return;
      	   		}else{
      	   			$("#zongjia").val(data.a);
      	   		 	
      	   	 	}
 			}
		});  
	}  
</script>