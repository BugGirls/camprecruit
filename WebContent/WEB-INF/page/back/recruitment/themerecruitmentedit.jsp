<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<style>
.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
}
</style>
<div class="row">
	<div class="col-xs-12">

 <!--  修改 -->
 
		<div style="top:0;left:0;background-color: #fff;border: 1px solid #aaa;" >
		   <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;" id="editarea">
		  	<input type="button"  style="float:left;" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="closebtn"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 编辑所选记录
			</div><div style="padding: 10px;">
		  	<form id="ff_form" method="post" enctype="multipart/form-data" action="${contextPath}/sys/recruitment/fileUpload">			
				<input type="hidden" name="id" id="ffid"/>
				<input type="hidden" id="ffContent" name="addContent"/>
				<ul class="nav nav-tabs">
				  <li role="presentation" class="active" id="promotion_base_info_nav_f"><a href="javascript:void(0);">基本信息</a></li>
				  <li role="presentation" id="promotion_base_set_nav_f"><a href="javascript:void(0);">规则设置</a></li>
				</ul>
				<br/>
				<!-- fieldsets -->
				<fieldset id="promotion_base_info_set_f" >
					主题招聘名称	&nbsp;<input type="text" name="name" id="fname" maxlength="60"/>(<span style="color:red;">*必填项</span>)<br><br>
					有效期	&nbsp;<input type="text" name="begin_date" id="fbegin_date" class="date"/>
					至	&nbsp;<input type="text" name="end_date" id="fend_date" class="date"/>(<span style="color:red;">*必填项</span>)<br><br>
					主题招聘类型	&nbsp;<select name="themeid" id="fthemeid"> </select><input type="hidden" name="themename" id="fthemename" /><br><br>
					简介		&nbsp;<input type="text" name="brief" id="fbrief" maxlength="255"/><br><br>
					招聘地点&nbsp;<input type="text" name="contact_address" id="fcontact_address" /><a href="javascript: pickcoord(1);">选择地点坐标</a><input type="text" name="coordinate" id="fcoordinate" /><br><br>
					<input type="hidden" name="img" id="image">
					<span  id="fimage"> 
					</span> 
					<div style="clear:both;"></div>
					<div>
					内容简介：
					 <script id="fcontent" name="contenttop" type="text/plain"></script><br><br>
					 </div>
				</fieldset>
  				<fieldset id="promotion_set_info_set_f" style="display: none;">
					
					参与人数	&nbsp;<input type="text" name="participation" id="fparticipation" maxlength="10"/>
					每日限制人数	&nbsp;<input type="text" name="everyday_limit" id="feveryday_limit" maxlength="10"/><br><br>
					
					是否免费	&nbsp;<input type="checkbox" name="free_reservation" id="ffree_reservation" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					<br><br>
					
					是否需支付	&nbsp;<input type="checkbox" name="pay_need" id="fpay_need" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/><span class="lbl"></span>
					支付金额	&nbsp;<input type="text" name="price" id="fprice" maxlength="10"/><br><br>
					
					<input type="hidden" name="is_publish" id="fis_publish" />
					<input type="hidden" name="status" id="fstatus" />
  				</fieldset>
				
				<div style="clear: both;"></div>
			 </form>
				<input type="button" name="up" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="closebtn" value="取消">
			 </div>
		</div>

		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 

<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<script type="text/javascript"src="${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js"></script> 
<script type="text/javascript"src="${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script> 
<script type="text/javascript"src="${contextPath}/static/edit/ueditor.config.js"></script>
<script type="text/javascript"src="${contextPath}/static/edit/ueditor.all.js"></script>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null,
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                "${contextPath}/static/assets/js/fuelux/fuelux.tree.js",
		        		 "${contextPath}/static/assets/js/ace-extra.js",
		                 null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		var spid = '${spid}';
        		if(spid!=null&&spid>0){
					$.ajax({//classifycn
				        type:"post", 
				        url:"${contextPath}/sys/recruitment/getRecruitment",
				        data:{"id":spid},
				   		dataType:"json", 
				       	success:function(data){ 
				       		if(data.success){
				       			var obj = data.obj;
				       			console.log(obj)
				       			var brief=obj.brief;
				       			var name=obj.name;
				       			var free_reservation=obj.free_reservation;
								var pay_need=obj.pay_need;
								var price=obj.price;
								var participation=obj.participation;
								var begin_date = new Date(obj.begin_date.time).Format("yyyy-MM-dd");
								var end_date = new Date(obj.end_date.time).Format("yyyy-MM-dd");
								var content = obj.content;
								var everyday_limit = obj.everyday_limit;
								var is_weekend = obj.is_weekend;
								var is_publish = obj.is_publish;
								console.log("is_publish:"+is_publish);
								var coordinate = obj.coordinate;
								var contact_address = obj.contact_address;
								var themeid = obj.themeid;
								var themename = obj.themename;
								var status = obj.status;
							
								//发布过的课程不能直接编辑
								if(is_publish==1){
									$("input[name='up']").css("display","none");
									//$("input:not(:button,:hidden)").prop("disabled", "disabled");
									$('form').find('input, select').attr("disabled", "disabled");
									$("#editarea").append("（先下架课程才能编辑）");
									
									$("#fthemeid").val(themeid);
									var fthemename = $("#fthemeid").find("option:selected").text();
									$("#fthemename").val(fthemename);
								
									var uee2 = UE.getEditor('fcontent', {
		     				            autoHeight: false,
		     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
		     				            initialFrameHeight:445  //初始化编辑器高度,默认320
		     				        }) ;
									uee2.ready(function() { 
					        			uee2.setContent(content);  
					        		});
									$("#fbegin_date").val(begin_date);
									$("#fend_date").val(end_date);
									$("#fis_publish").val(is_publish);
									$("#fstatus").val(status);
								
									if(free_reservation==1){
										$("#ffree_reservation").attr("checked","checked");
									}
									if(pay_need==1){
										$("#fpay_need").attr("checked","checked");
									}
									$("#fprice").val(price);
									$("#fparticipation").val(participation);
									if(is_weekend==1){
										$("#fis_weekend").attr("checked","checked");
									}
									$("#feveryday_limit").val(everyday_limit);
									$("#fcontact_address").val(contact_address);
									$("#fcoordinate").val(coordinate);

									var image=obj.img;
									console.log(image);
									if(""!=image){
										$("#image").val(image);
										$("#fimage").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>图片<img id="imagel" width="200px" height="100px" src="${contextPath}'+image + '"/><br/>' );
									}
								
									$("#ffid").val(spid);
									$("#fname").val(name);
									$("#fbrief").val(brief);
								
								}else{
								
								
									$("#fthemeid").val(themeid);
									var fthemename = $("#fthemeid").find("option:selected").text();
									$("#fthemename").val(fthemename);
								
									var uee1 = UE.getEditor('fcontent', {
		     				            autoHeight: false,
		     				            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
		     				            initialFrameHeight:445  //初始化编辑器高度,默认320
		     				        }) ;
									uee1.ready(function() { 
					        			uee1.setContent(content);  
					        		});
									$("#fbegin_date").val(begin_date);
									$("#fend_date").val(end_date);
									$("#fis_publish").val(is_publish);
									$("#fstatus").val(status);
								
									if(free_reservation==1){
										$("#ffree_reservation").attr("checked","checked");
									}
									if(pay_need==1){
										$("#fpay_need").attr("checked","checked");
									}
									$("#fprice").val(price);
									$("#fparticipation").val(participation);

									if(is_weekend==1){
										$("#fis_weekend").attr("checked","checked");
									}
									$("#feveryday_limit").val(everyday_limit);
									$("#fcontact_address").val(contact_address);
									$("#fcoordinate").val(coordinate);

									var image=obj.img;
									console.log(image);
									if(""!=image){
										$("#image").val(image);
										$("#fimage").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片可修改图片<br>图片<img id="imagel" width="200px" height="100px" src="${contextPath}'+image + '"/><br/>' ); 
									}else{
										$("#fimage").html("上传图片	&nbsp;	<input type='file' name='file' id='ffile'/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>" );
									}
								
									$("#fimage").click(function(){
											$(this).html(""); 
											var htmlspan = document.createElement("span");
											var htmlinput = document.createElement("input");
											htmlinput.setAttribute("type","file");
											htmlinput.setAttribute("name","file");
											var htmlbr = document.createElement("br");
											htmlspan.innerHTML = "上传图片：";
											htmlspan.appendChild(htmlinput);
											htmlspan.appendChild(htmlbr);
											$(this).append(htmlspan);
											$("#fimage").unbind();			
									});
								
									$("#ffid").val(spid);
									$("#fname").val(name);
									$("#fbrief").val(brief);
								
								}
				       		}   
				       	} 
				    });
        		}else{
        			$("#fimage").html("上传图片	&nbsp;	<input type='file' name='file' id='ffile'/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>" );
        			$("#fimage").click(function(){
						$(this).html(""); 
						var htmlspan = document.createElement("span");
						var htmlinput = document.createElement("input");
						htmlinput.setAttribute("type","file");
						htmlinput.setAttribute("name","file");
						var htmlbr = document.createElement("br");
						htmlspan.innerHTML = "上传图片：";
						htmlspan.appendChild(htmlinput);
						htmlspan.appendChild(htmlbr);
						$(this).append(htmlspan);
						$("#fimage").unbind();			
				});
            		var uee1 = UE.getEditor('fcontent', {
			            autoHeight: false,
			            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
			            initialFrameHeight:445  //初始化编辑器高度,默认320
			        }) ;
					uee1.ready(function() { 
	    			});
        		}

           		
           		$('#promotion_base_info_nav_f').on('click', function() { 
           			$('#promotion_base_info_nav_f').addClass("active");
           			$('#promotion_base_set_nav_f').removeClass("active");
           			$('#promotion_base_info_set_f').show();
           			$('#promotion_set_info_set_f').hide();
    			});
           		
           		$('#promotion_base_set_nav_f').on('click', function() {
           			$('#promotion_base_info_nav_f').removeClass("active");
           			$('#promotion_base_set_nav_f').addClass("active");
           			$('#promotion_base_info_set_f').hide();
           			$('#promotion_set_info_set_f').show();
    			});
           		
        		$('.date').datepicker({
					format : 'yyyy-mm-dd',
					autoclose : true,
				    language: 'zh-CN'
				});

        		$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/recruitmenttheme/getSelectList",
			   		dataType:"html", 
			       	success:function(data){ 
			       		$("#fthemeid").append(data); 
			       		
			       		$("#fthemename").val($("#fthemeid option").eq(0).text());
			       		
			       		$("#fthemeid").on('change', function() {
			       			var value = $("#fthemeid").find("option:selected").text();
			       			$("#fthemename").val(value);
			       		});
			       	} 
			    });
        		
//         		var uee1 = UE.getEditor('fcontent', {
// 			            autoHeight: false,
// 			            initialFrameWidth:980  , //初始化编辑器宽度,默认1000
// 			            initialFrameHeight:445  //初始化编辑器高度,默认320
// 			        }) ;
// 				uee1.ready(function() { 
//         			uee1.setContent(content);  
//         		});

//===========================================================================================	
		
				$("#fimage").click(function(){
					$(this).html(""); 
					var htmlspan = document.createElement("span");
					var htmlinput = document.createElement("input");
					htmlinput.setAttribute("type","file");
					htmlinput.setAttribute("name","file");
					var htmlbr = document.createElement("br");
					htmlspan.innerHTML = "上传图片：";
					htmlspan.appendChild(htmlinput);
					htmlspan.appendChild(htmlbr);
					$(this).append(htmlspan);
					$("#fimage").unbind();		
				});
				//=================提交  
				$("input[name='up']").click(function(){  
		 			 $("#fimage").unbind();
		 			var regz = new RegExp("^[0-9]*[1-9][0-9]*$");
					var regk=new RegExp("^[0-9]+(.[0-9]{1,2})?$");   
    			    if($("#fprice").val()!=""&&$("#fprice").val()!="0"&&!regk.test($("#fprice").val())){  
    			        alert("支付金额必须是两位小数数字格式!");  
    			        return ;
    			    }
    			    if($("#fparticipation").val()!=""&&$("#fparticipation").val()!="0"&&!regz.test($("#fparticipation").val())){  
    			        alert("参与人数必须是整数格式!");  
    			        return ;
    			    }
    			    if($("#feveryday_limit").val()!=""&&$("#feveryday_limit").val()!="0"&&!regz.test($("#feveryday_limit").val())){  
    			        alert("每日限额人数必须是整数格式!");  
    			        return ;
    			    }
		 			 var content= UE.getEditor('fcontent').getContent();
		 			 $("#ffContent").val(content);
		 			if($("#fbegin_date").val()==null||$("#fbegin_date").val()==""||$("#fend_date").val()==null||$("#fend_date").val()==""){
		 				 alert("课程有效期必须填写");
		 				 return;
		 			 }
		 			
		 			if($("#fbegin_date").val()!=""&&!$("#fbegin_date").val().match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)&&!$("#fbegin_date").val().match(/^((?:19|20)\d\d)\/(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/)) { 
		 				alert("日期格式不正确，正确格式例如'2009-03-09'"); 
		 				return;
		 			}
		 			if($("#fend_date").val()!=""&&!$("#fend_date").val().match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)&&!$("#fend_date").val().match(/^((?:19|20)\d\d)\/(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/)) { 
		 				alert("日期格式不正确，正确格式例如'2009-03-09'"); 
		 				return;
		 			}
					 $("#ff_form").submit();
				});
				
				//======================关闭
				$("input[name='closebtn']").click(function(){
					$("#fimage").unbind();
					window.location.href="${contextPath}/sys/sysuser/home#page/themerecruitment";
				});
//===========================================jieshu ================================================

        		$(window).triggerHandler('resize.jqGrid');// trigger window resize to make the grid get the correct size
        		
        		// enable search/filter toolbar
        		// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
        		// jQuery(grid_selector).filterToolbar({});
        		// switch element when editing inline
        		function aceSwitch(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[type=checkbox]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			}, 0);
        		}
        		
        		// enable datepicker
        		function pickDate(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[type=text]').datepicker({
        					format : 'yyyy-mm-dd',
        					autoclose : true,
        				    language: 'zh-CN'
        				});
        			}, 0);
        		}

        		function style_edit_form(form) {
        			// enable datepicker on "birthday" field and switches for "stock" field
        			form.find('input[name=begin_date]').datepicker({
        				format : 'yyyy-mm-dd',
        				autoclose : true,
        			    language: 'zh-CN'
        			})

        			form.find('input[name=statusCn]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			// don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
        			// .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

        			// update buttons classes
        			var buttons = form.next().find('.EditButton .fm-button');
        			buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();// ui-icon, s-icon
        			buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
        			buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

        			buttons = form.next().find('.navButton a');
        			buttons.find('.ui-icon').hide();
        			buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
        			buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
        		}

        		function style_delete_form(form) {
        			var buttons = form.next().find('.EditButton .fm-button');
        			buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();// ui-icon, s-icon
        			buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
        			buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>');
        		}

        		function style_search_filters(form) {
        			form.find('.delete-rule').val('X');
        			form.find('.add-rule').addClass('btn btn-xs btn-primary');
        			form.find('.add-group').addClass('btn btn-xs btn-success');
        			form.find('.delete-group').addClass('btn btn-xs btn-danger');
        		}
        		function style_search_form(form) {
        			var dialog = form.closest('.ui-jqdialog');
        			var buttons = dialog.find('.EditTable')
        			buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
        			buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
        			buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
        		}

        		function beforeDeleteCallback(e) {
        			var form = $(e[0]);
        			if (form.data('styled'))
        				return false;
        			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        			style_delete_form(form);
        			form.data('styled', true);
        		}

        		function beforeEditCallback(e) {
        			var form = $(e[0]);
        			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        			style_edit_form(form);
        		}

        		// it causes some flicker when reloading or navigating grid
        		// it may be possible to have some custom formatter to do this as the grid is being created to prevent this
        		// or go back to default browser checkbox styles for the grid
        		function styleCheckbox(table) {
        			/**
        			 * $(table).find('input:checkbox').addClass('ace') .wrap('<label />') .after('<span class="lbl align-top" />') $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
        			 * .find('input.cbox[type=checkbox]').addClass('ace') .wrap('<label />').after('<span class="lbl align-top" />');
        			 */
        		}

        		// unlike navButtons icons, action icons in rows seem to be hard-coded
        		// you can change them like this in here if you want
        		function updateActionIcons(table) {
        			/**
        			 * var replacement = { 'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue', 'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red', 'ui-icon-disk' : 'ace-icon fa fa-check green', 'ui-icon-cancel' :
        			 * 'ace-icon fa fa-times red' }; $(table).find('.ui-pg-div span.ui-icon').each(function(){ var icon = $(this); var $class = $.trim(icon.attr('class').replace('ui-icon', '')); if($class in replacement)
        			 * icon.attr('class', 'ui-icon '+replacement[$class]); })
        			 */
        		}

        		// replace icons with FontAwesome icons like above
        		function updatePagerIcons(table) {
        			var replacement = {
        				'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
        				'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
        				'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
        				'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
        			};
        			$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function() {
        				var icon = $(this);
        				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        				if ($class in replacement)
        					icon.attr('class', 'ui-icon ' + replacement[$class]);
        			})
        		}

        		function enableTooltips(table) {
        			$('.navtable .ui-pg-button').tooltip({
        				container : 'body'
        			});
        			$(table).find('.ui-pg-div').tooltip({
        				container : 'body'
        			});
        		}

        		// var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

        		$(document).one('ajaxloadstart.page', function(e) {
        			//$(grid_selector).jqGrid('GridUnload');
        			$('.ui-jqdialog').remove();
        		});
        		
        	});
        });
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
     
</script>
