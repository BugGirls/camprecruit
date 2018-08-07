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


<p style="font-size: 22px;">&nbsp;&nbsp;&nbsp;&nbsp;欢迎登陆无人超市云服务平台</p><br><br>
<div>
	<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
	<div id="user-profile-1" class="user-profile row">
		<div class="col-xs-12 col-sm-3 center">
			<div>
				<span class="profile-picture">
					<img id="blogo" class="editable img-responsive editable-click editable-empty" alt="Alex's Avatar" src="${contextPath}/static/assets/avatars/profile-pic.jpg"></img>
				</span>

				<div class="space-4"></div>

				<div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
					<div class="inline position-relative">
						<a href="#" class="user-title-label dropdown-toggle" data-toggle="dropdown">
							<i class="icon-circle light-green middle"></i>
							&nbsp;
							<span class="white" id="bname">XXX</span>
						</a>
					</div>
				</div>
			</div>

			<div class="space-6"></div>

			<div class="profile-contact-info">
				<div class="profile-contact-links align-left">
						<span id="bbrief" style="background: transparent none !important; color: #0088cc !important;    text-shadow: none !important;    padding: 4px 12px !important;line-height: 20px !important;"></span>

				</div>

				<div class="space-6"></div>

				<div class="profile-social-links center">
					<a href="#" class="tooltip-info" title="" data-original-title="Visit my Facebook">
						<i class="middle icon-facebook-sign icon-2x blue"></i>
					</a>

					<a href="#" class="tooltip-info" title="" data-original-title="Visit my Twitter">
						<i class="middle icon-twitter-sign icon-2x light-blue"></i>
					</a>

					<a href="#" class="tooltip-error" title="" data-original-title="Visit my Pinterest">
						<i class="middle icon-pinterest-sign icon-2x red"></i>
					</a>
				</div>
			</div>

			<div class="hr hr12 dotted"></div>
<!-- 
			<div class="clearfix">
				<div class="grid2">
					<span class="bigger-175 blue">25</span>

					<br>
					Followers
				</div>

				<div class="grid2">
					<span class="bigger-175 blue">12</span>

					<br>
					Following
				</div>
			</div>

			<div class="hr hr16 dotted"></div>
			
			 -->
		</div>

		<div class="col-xs-12 col-sm-9">
			<div class="center">
				<a class="btn btn-app btn-sm btn-light no-hover" href="#page/allianceincomesub">
					<span class="line-height-1 bigger-170 blue" id="bprofit" > 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 账户金额 </span>
				</a>

				<a class="btn btn-app btn-sm btn-yellow no-hover"  href="#page/allianceincomesub">
					<span class="line-height-1 bigger-170" id="beincome_c"> 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 累计收入 </span>
				</a>

				<a class="btn btn-app btn-sm btn-pink no-hover"  href="#page/allianceincomesub">
					<span class="line-height-1 bigger-170" id="bfincome_c"> 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 待结算金额 </span>
				</a>
				
				<a class="btn btn-app btn-sm btn-primary no-hover"  href="#page/newreservationalliance">
					<span class="line-height-1 bigger-170"  id="ballorder_c"> 0 </span>

					<br>
					<span class="line-height-1 smaller-90"> 累计订单 </span>
				</a>

				
			</div>

			<div class="space-12"></div>

			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<div class="profile-info-name"> 商户名称 </div>

					<div class="profile-info-value">
						<span class="editable editable-click" id="bname_s">XXXX</span>
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

				<div class="profile-info-row">
					<div class="profile-info-name"> 微店铺 </div>

					<div class="profile-info-value">
						<a class="editable editable-click" id="bshop_s">www.zhihuiyingdi.com/weixin/top/salespromotion/getAllianceBusiness?id=</a>
					</div>
				</div>
				
				<div class="profile-info-row">
					<div class="profile-info-name"> 注册链接 </div>

					<div class="profile-info-value">
						<a class="editable editable-click" id="bregist_s">http://www.zhihuiyingdi.com/weixin/jsp/home/wx_login_h.jsp?allianceid=</a>
					</div>
				</div>
<!--  -->
			</div>

			<div class="space-20"></div>

			<div class="space-6"></div>

		</div>
	</div>
</div>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
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
		       					var types = ""
		       					if(b.type==1)
		       						types= "自营";
								else if(b.type==2)
									types= "加盟";
		       					$("#bname").html(b.name);
		       					$("#bbrief").html(b.brief);
		       					$("#bprofit").html(b.profit);
		       					$("#bname_s").html(b.name);
		       					//$("#bphone_s").html(b.name);
		       					$("#btype_s").html(types);
		       					$("#bcreatetime_s").html(new Date(b.createtime.time).Format("yyyy-MM-dd hh:mm:ss"));
		       					$("#bshop_s").html("http://www.zhihuiyingdi.com/weixin/top/salespromotion/getAllianceBusiness?id="+b.id);
		       					$("#bshop_s").attr("href","http://www.zhihuiyingdi.com/weixin/top/salespromotion/getAllianceBusiness?id="+b.id);
		       					$("#bregist_s").html("http://www.zhihuiyingdi.com/weixin/jsp/home/wx_login_h.jsp?allianceid="+b.id);
		       					$("#bregist_s").attr("href","http://www.zhihuiyingdi.com/weixin/jsp/home/wx_login_h.jsp?allianceid="+b.id);
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
