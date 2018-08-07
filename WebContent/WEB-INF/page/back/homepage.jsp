<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
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
.m_title{ margin: auto 25px; }
.btn.btn-app.btn-sm {width:18%;}
</style>
		<script type="text/javascript">
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
		</script>
<div>
	<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
	<div id="user-profile-1" class="user-profile row">
		<div class="space-12" style=" "></div>

		<div class="">
			<div class="center">
				<a class="btn btn-app btn-sm  no-hover" style="background: #09B3FF !important;" href="#page/allianceincomesub">
					<span class="line-height-1 bigger-170 " id="bprofit" > 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 账户金额 </span>
				</a>

				<a class="btn btn-app btn-sm  no-hover" style="background: #3CC2FF !important;"  href="#page/allianceincomesub">
					<span class="line-height-1 bigger-170" id="beincome_c"> 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 累计收入 </span>
				</a>

				<a class="btn btn-app btn-sm  no-hover" style="background: #09B3FF !important;"  href="#page/allianceincomesub">
					<span class="line-height-1 bigger-170" id="bfincome_c"> 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 待结算金额 </span>
				</a>
				
				<a class="btn btn-app btn-sm  no-hover" style="background: #3CC2FF !important;"  href="#page/newreservationalliance">
					<span class="line-height-1 bigger-170"  id="ballorder_c"> 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 累计订单 </span>
				</a>


			</div>

			<div class="space-12"></div>

			<div class="profile-user-info profile-user-info-striped" style="width:70%;">
				<div class="profile-info-row">
					<div class="profile-info-name"> 商户名称 </div>

					<div class="profile-info-value">
						<span class="editable editable-click" id="bname_s">XXXX</span>
					</div>
				</div>
				
				<div class="profile-info-row">
					<div class="profile-info-name"> 行业 </div>

					<div class="profile-info-value">
						<span class="editable editable-click" id="bindustry_s">XXXX</span>
					</div>
				</div>

				<div class="profile-info-row">
					<div class="profile-info-name"> 加盟类型 </div>

					<div class="profile-info-value">
						<span class="editable editable-click" id="btype_s">加盟</span>
					</div>
				</div>

				<div class="profile-info-row">
					<div class="profile-info-name"> 加盟时间 </div>

					<div class="profile-info-value">
						<span class="editable editable-click" id="bcreatetime_s">XXXX年XX月XX日</span>
					</div>
				</div>

<!-- 				<div class="profile-info-row"> -->
<!-- 					<div class="profile-info-name"> 微店铺 </div> -->

<!-- 					<div class="profile-info-value"> -->
<!-- 						<a class="editable editable-click" id="bshop_s">www.zhihuiyingdi.com/camprecruit/top/salespromotion/getAllianceBusiness?id=</a> -->
<!-- 						[<a class="editable editable-click" id="bshop_s_code">二维码</a>] -->
<!-- 					</div> -->
<!-- 				</div> -->
				
<!-- 				<div class="profile-info-row"> -->
<!-- 					<div class="profile-info-name"> 邀请注册 </div> -->

<!-- 					<div class="profile-info-value"> -->
<!-- 						<a class="editable editable-click" id="bregist_s">http://www.zhihuiyingdi.com/camprecruit/jsp/home/wx_login_h.jsp?allianceid=</a> -->
<!-- 						[<a class="editable editable-click" id="bregist_s_code">二维码</a>] -->
<!-- 					</div> -->
<!-- 				</div> -->
<!--  -->
			</div>

			<div class="space-20"></div>

			<div class="space-6"></div>

		</div>
	</div>
</div>

<div id="hidediv" style="z-index:99;width:330px;height:370px;padding:20px 20px; position:absolute;background-color:#fff; border:1px solid #dcd; display: none; top: 100px ;left:30% ; ">
	<img id="scan_decode" alt="" src="">
	
	<p style="text-align: center;"><button style="border:1px solid #aaa;margin-right:30px;background-color:#eee; " onclick="$('#hidediv').hide();$('#scan_decode').attr('src','');">关闭</button></p>
</div>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		$("#bshop_s_code").on('click', function() {
        			//alert($("#bshop_s").html());
        			$("#scan_decode").attr("src","http://qr.liantu.com/api.php?text="+$("#bshop_s").html());
        			$("#hidediv").show();
        		});
        		$("#bregist_s_code").on('click', function() {
        			//alert($("#bshop_s").html());
        			$("#scan_decode").attr("src","http://qr.liantu.com/api.php?text="+$("#bregist_s").html());
        			$("#hidediv").show();
        		});
        		
        		Date.prototype.Format = function(fmt)   
                { //author: meizz   
                  var o = {   
                    "M+" : this.getMonth()+1,                 //月份   
                    "d+" : this.getDate(),                    //日   
                    "h+" : this.getHours(),                   //小时   
                    "m+" : this.getMinutes(),                 //分   
                    "s+" : this.getSeconds(),                 //秒   
                    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
                    "S"  : this.getMilliseconds()             //毫秒   
                  };   
                  if(/(y+)/.test(fmt))   
                    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
                  for(var k in o)   
                    if(new RegExp("("+ k +")").test(fmt))   
                  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
                  return fmt;   
                };
        		function getAllianceIncomeCount(){
	        		$.ajax({
	        			type : "post",
	        			url : " ${contextPath}/sys/allianceincome/getAllianceIncomeCount",
	        			dataType:"json",
	        			success : function(data) {
	        				console.log(eval(data))
	       					//$("#eexpense").html(data.eexpense);
	       					//$("#eincome").html(data.eincome);
	       					//$("#fexpense").html(data.fexpense);
	       					//$("#fincome").html(data.fincome);
	       					
	       					$("#bfincome_c").html(new Number(data.fincome-data.fexpense).toFixed(2));
	       					$("#beincome_c").html(new Number(data.eincome-data.eexpense).toFixed(2));
	        			}
	        		});
        		}
        		getAllianceIncomeCount();
        		
        		function getAllianceBusiness(){
	        		$.ajax({
	        			type : "post",
	        			url : " ${contextPath}/sys/alliancebusiness/getAllianceBusiness",
	        			dataType:"json",
	        			success : function(data) {
	        				console.log(eval(data))
	        				if(data.success){
	        					var b = data.business;
		       					$("#blogo").attr("src","${contextPath}/"+b.logo);
		       					//$("#eincome").html(data.eincome);
		       					//$("#fexpense").html(data.fexpense);
		       					//$("#fincome").html(data.fincome);
		       					var types = b.typeValue;
		       					var industry = b.industryValue;
		       					//$("#bname").html(b.name);
		       					$("#bbrief").html(b.brief);
		       					$("#bprofit").html(b.profit);
		       					$("#bname_s").html(b.name);
		       					//$("#bphone_s").html(b.name);
		       					$("#btype_s").html(types);
		       					$("#bindustry_s").html(industry);
		       					$("#bcreatetime_s").html(new Date(b.createtime.time).Format("yyyy-MM-dd"));
// 		       					$("#bshop_s").html("http://www.zhihuiyingdi.com/weixin/top/salespromotion/getAllianceBusiness?id="+b.id);
// 		       					$("#bshop_s").attr("href","http://www.zhihuiyingdi.com/weixin/top/salespromotion/getAllianceBusiness?id="+b.id);
// 		       					$("#bregist_s").html("http://www.zhihuiyingdi.com/weixin/jsp/home/wx_login_h.jsp?allianceid="+b.id);
// 		       					$("#bregist_s").attr("href","http://www.zhihuiyingdi.com/weixin/jsp/home/wx_login_h.jsp?allianceid="+b.id);
	        				}
	        			}
	        		});
        		}
        		getAllianceBusiness();
        		
        		function getAllianceOrderCount(){
	        		$.ajax({
	        			type : "post",
	        			url : " ${contextPath}/sys/newTzzReservation/getAllianceOrderCount",
	        			dataType:"json",
	        			success : function(data) {
	        				console.log(eval(data))
	       					$("#ballorder_c").html(data.all);
	       					$("#brefoundorder_c").html(data.refound);
	       					//$("#fexpense").html(data.fexpense);
	       					//$("#fincome").html(data.fincome);
	       					
	       					//$("#bfincome_c").html(data.fincome-data.fexpense);
	       					//$("#beincome_c").html(data.eincome-data.eexpense);
	        			}
	        		});
        		}
        		getAllianceOrderCount();
        	});
        });
</script>
