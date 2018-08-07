<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery.gritter.css" />

<link rel="stylesheet"
	href="${contextPath}/static/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet"
	href="${contextPath}/static/assets/css/chosen.css" />

<!-- ajax layout which only needs content area -->
<div class="page-header">
 
	<h1>
		站内消息
		<small> 
		</small>
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="row">
			<div class="col-xs-12">
				<!-- #section:pages/inbox -->
				<div class="tabbable">
					<ul id="inbox-tabs" class="inbox-tabs nav nav-tabs padding-16 tab-size-bigger tab-space-1">
						<!-- #section:pages/inbox.compose-btn -->
						<li class="li-new-mail pull-right">
							 
						</li><!-- /.li-new-mail -->

						<!-- /section:pages/inbox.compose-btn -->
						<li class="active">
							<a data-toggle="tab" href="#inbox" data-target="inbox">
								<i class="blue ace-icon fa fa-location-arrow bigger-130"></i>
								<span class="bigger-110">已发送</span>
							</a>
						</li>
 
						<li>
							<a data-toggle="tab" href="#write" data-target="write">
								<i class="green ace-icon fa fa-pencil bigger-130"></i>
								<span class="bigger-110"> 写信 </span>
							</a>
						</li>
					</ul>

					<div class="tab-content no-border no-padding">
						<div id="inbox" class="tab-pane in active">
							<div class="message-container">
								<!-- #section:pages/inbox.navbar -->
								<div id="id-message-list-navbar" class="message-navbar clearfix">
									<div class="message-bar">
										<div class="message-infobar" id="id-message-infobar">
											<span class="blue bigger-150">已发送${newsnum }封站内消息</span> 
										</div> 
										<div class="message-toolbar hide"> 
											<button id="delete" type="button" class="btn btn-xs btn-white btn-primary">
												<i class="ace-icon fa fa-trash-o bigger-125 orange"></i>
												<span class="bigger-110">删除</span>
											</button>
										</div>
									</div>

									<div>
										<div class="messagebar-item-left">
											<label class="inline middle">
												<input type="checkbox" id="id-toggle-all" class="ace" />
												<span class="lbl"></span>
											</label>

											&nbsp;
											<div class="inline position-relative">
												<a href="#" data-toggle="dropdown" class="dropdown-toggle">
													<i class="ace-icon fa fa-caret-down bigger-125 middle"></i>
												</a>

												<ul class="dropdown-menu dropdown-lighter dropdown-100">
													<li>
														<a id="id-select-message-all" href="#">全选</a>
													</li>
													<li class="divider"></li>
													<li>
														<a id="id-select-message-none" href="#">反选</a>
													</li>
 
												</ul>
											</div>
										</div>

										<!-- #section:pages/inbox.navbar-search 搜索-->
										<div class="nav-search minimized">
											 
												<span class="input-icon">
													<input type="text" autocomplete="off" id="search" class="input-small nav-search-input" placeholder="邮件全文检索 ..." />
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											 
										</div>

										<!-- /section:pages/inbox.navbar-search -->
									</div>
								</div>
 
								<div id="id-message-new-navbar" class="hide message-navbar clearfix">
									 <div class="message-bar">
										<div class="message-toolbar">
											<button type="button" class="btn btn-xs btn-white btn-primary"> 
												<span class="bigger-110">站内消息</span>
											</button>
 
										</div>
									</div>
									 

									<div>
										<div class="messagebar-item-left">
											<a href="#" class="btn-back-message-list">
												<i class="ace-icon fa fa-arrow-left bigger-110 middle blue"></i>
												<b class="middle bigger-110">返回</b>
											</a>
										</div>

										<div class="messagebar-item-right">
											<span class="inline btn-send-message">
												<button id="sendEmailButton" type="button" class="btn btn-sm btn-primary no-border btn-white btn-round">
													<span class="bigger-110">发送</span>

													<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
												</button>
											</span>
										</div>
									</div>
								</div>

								<!-- /section:pages/inbox.navbar -->
								<div class="message-list-container">
									<!-- #section:pages/inbox.message-list -->
									<div class="message-list" id="message-list">
									 								
										<c:forEach items="${nlist }" var="n" >
											 
											<!-- #section:pages/inbox.message-list.item id= message-list站内消息列表 -->
												<div class="message-item  message-unread">
													<input type="hidden" id="thisid" value="${n.id }">
													<label class="inline">
														<input type="checkbox" name="del" class="ace" />
														<span class="lbl"></span>
													</label>
		
												<i class="message-star ace-icon fa fa-star orange2"></i>
													<span class="sender" title="标题">${n.userame }</span>
													<span class="time" style="width:150px;">${fn:substring(n.createTime,0,19) }</span>
		
													<span class="summary">
														<span class="text" name="newsxq" style="width:500px ;"> 
															${fn:substring(n.title,0,50) }
														</span>
													</span>
												</div> 
										</c:forEach>  
									</div>

									<!-- /section:pages/inbox.message-list -->
								</div>

								<!-- #section:pages/inbox.message-footer分页 -->
								<div class="message-footer clearfix">
									<div class="pull-left"> 共 ${newsnum } 封 </div>

									<div class="pull-right">
										<div class="inline middle" id="dijiye"> ${pagenum }/${totalpage }页 </div>

										&nbsp; &nbsp;
										<ul class="pagination middle">
											<li class="abled">
												<a  style="cursor:pointer;" id="shouye">
													<i class="ace-icon fa fa-step-backward middle"></i>
												</a>
											</li>

											<li class="abled">
												<a  style="cursor:pointer;" id="shangyiye">
													<i class="ace-icon fa fa-caret-left bigger-140 middle"></i>
												</a>
											</li>

											<li>
												<span>
													<input value="${pagenum }" maxlength="4" type="text" />
												</span>
											</li>

											<li class="abled">
												<a  style="cursor:pointer;"  id="xiayiye">
													<i class="ace-icon fa fa-caret-right bigger-140 middle"></i>
												</a>
											</li>

											<li class="abled">
												<a style="cursor:pointer;" id="moye">
													<i class="ace-icon fa fa-step-forward middle"></i>
												</a>
											</li>
										</ul>
									</div>
								</div>

								<!-- /section:pages/inbox.message-footer -->
							</div>
						</div>
					</div><!-- /.tab-content -->
				</div><!-- /.tabbable --> 
				<!-- /section:pages/inbox -->
			</div><!-- /.col -->
		</div><!-- /.row -->

		<form id="id-message-form" class="hide form-horizontal message-form col-xs-12" method="POST">
			<!-- #section:pages/inbox.compose 发件输入位置-->
			<div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="form-field-recipient">收件人:</label>

					<div class="col-sm-6 col-xs-12">
						<div class="input-icon block col-xs-12 no-padding">
						<!-- 	<input type="text" class="col-xs-12" name="users" id="form-field-recipient" placeholder="请填写用户昵称，多个用英文逗号隔开，如果填写“0”则发给所有人" />
							<i class="ace-icon fa fa-user"></i>
							  -->
							<select class="chosen-select form-control" name="users" id="form-field-select-1">
									 <c:forEach items="${names }" var="n" >
									 <option value="${n}">${n }</option> 
									 </c:forEach>
							</select>
						
						
						</div>
					</div>
				</div>

				<div class="hr hr-18 dotted"></div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="form-field-subject">主题:</label>

					<div class="col-sm-6 col-xs-12">
						<div class="input-icon block col-xs-12 no-padding">
							<input maxlength="100" type="text" class="col-xs-12" name="title" id="form-field-subject" placeholder="主题" />
							<i class="ace-icon fa fa-comment-o"></i>
						</div>
					</div>
				</div> 
				<div class="hr hr-18 dotted"></div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">
						<span class="inline space-24 hidden-480"></span>
						正文:
					</label>

					<!-- #section:plugins/editor.wysiwyg -->
					<div class="col-sm-9">
					 <script id="content" name="contenttop" type="text/plain"> </script> 
						<!-- <div class="wysiwyg-editor"></div> -->
					</div>
					<!-- /section:plugins/editor.wysiwyg -->
				</div>

				
				<div class="space"></div>
				<div id="form-field-attachmentpaths" style="display: none;"></div>
			</div>
			<!-- /section:pages/inbox.compose -->
		</form>

		<div class="hide message-content" id="id-message-content">
			<!-- #section:pages/inbox.message-header消息详情 -->
			<div class="message-header clearfix">
				<div class="pull-left">
					<div class="space-4"></div>

					<i class="ace-icon fa fa-star orange2"></i>

					&nbsp;
					<a href="#" class="sender" id="sendtitle"></a>

					&nbsp;
					<i class="ace-icon fa fa-clock-o bigger-110 orange middle"></i>
					<span class=" grey" style="width:20px;overflow: hidden;" id="fasongtime"></span>
					<i class="ace-icon fa fa-user bigger-110 green middle"></i>
					 <span class="time grey">&nbsp;&nbsp;接收人：</span><span class="grey" id="jiushouuser"></span>
				</div> 
			</div>

			<!-- /section:pages/inbox.message-header -->
			<div class="hr hr-double"></div>

			<!-- #section:pages/inbox.message-body -->
			<div class="message-body">
			
			</div>

			<!-- /section:pages/inbox.message-body -->
			<div class="hr hr-double"></div>

			<!-- #section:pages/inbox.message-attachment -->
			<div class="message-attachment clearfix">
			 
			</div>

			<!-- /section:pages/inbox.message-attachment -->
		</div><!-- /.message-content -->

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<input type="hidden" value="${pagenum }" id="hidepagenum"/>

<script type="text/javascript" src= "${contextPath}/static/edit/ueditorconfig3.js"></script>
<script type="text/javascript" src= "${contextPath}/static/edit/ueditor.all.js"></script>
<script type="text/javascript" >
var ue = UE.getEditor("content", {
    autoHeight: false,
    initialFrameWidth:980  , //初始化编辑器宽度,默认1000
    initialFrameHeight:445  //初始化编辑器高度,默认320
}) ; 

</script>

<!-- page specific plugin scripts -->
<script type="text/javascript">
	var scripts = [null, 
	               "${contextPath}/static/assets/js/jquery.hotkeys.js",
	               "${contextPath}/static/assets/js/bootstrap-wysiwyg.js",
	               "${contextPath}/static/assets/js/jquery.gritter.js",
	               
	               "${contextPath}/static/assets/js/jquery-ui.custom.js",
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
	               "${contextPath}/static/assets/js/bootstrap-tag.js",
	               
	               
	               null];
	$(".page-content-area").ace_ajax("loadScripts", scripts, function() {
		 
	  	 //inline scripts related to this page
		 jQuery(function($){
			//handling tabs and loading/displaying relevant messages and forms
			//not needed if using the alternative view, as described in docs
			$("#inbox-tabs a[data-toggle='tab']").on("show.bs.tab", function (e) {
				var currentTab = $(e.target).data("target");
				if(currentTab == "write") {
					Inbox.show_form();
				}
				else if(currentTab == "inbox") {
					Inbox.show_list();
				}else if(currentTab == "sent"){
					Inbox.show_list();
				}
			});
		
			
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				//resize the chosen on window resize
		 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() { 
					$('.chosen-select').each(function() { 
						 var $this = $(this);
						 $this.next().css({'width': '600px'});
					});
				}).trigger('resize.chosen'); 
			}
			//basic initializations
			$(".message-list .message-item input[type=checkbox]").removeAttr("checked");
			$(".message-list").on("click", ".message-item input[type=checkbox]" , function() {
				$(this).closest(".message-item").toggleClass("selected");
				if(this.checked) Inbox.display_bar(1);//display action toolbar when a message is selected
				else {
					Inbox.display_bar($(".message-list input[type=checkbox]:checked").length);
					//determine number of selected messages and display/hide action toolbar accordingly
				}		
			});
		
			//check/uncheck all messages
			$("#id-toggle-all").removeAttr("checked").on("click", function(){
				if(this.checked) {
					Inbox.select_all();
				} else Inbox.select_none();
			});
			
			//select all
			$("#id-select-message-all").on("click", function(e) {
				e.preventDefault();
				Inbox.select_all();
			});
			
			//select none
			$("#id-select-message-none").on("click", function(e) {
				e.preventDefault();
				Inbox.select_none();
			}); 
			 
			//back to message list
			$(".btn-back-message-list").on("click", function(e) {
				e.preventDefault();
				$("#inbox-tabs a[href='#inbox']").tab("show");
			});
		
			//hide message list and display new message form
			/**
			$(".btn-new-mail").on("click", function(e){
				e.preventDefault();
				Inbox.show_form();
			});
			*/
		
			var Inbox = {
				//displays a toolbar according to the number of selected messages
				display_bar : function (count) {
					if(count == 0) {
						$("#id-toggle-all").removeAttr("checked");
						$("#id-message-list-navbar .message-toolbar").addClass("hide");
						$("#id-message-list-navbar .message-infobar").removeClass("hide");
					}
					else {
						$("#id-message-list-navbar .message-infobar").addClass("hide");
						$("#id-message-list-navbar .message-toolbar").removeClass("hide");
					}
				}
				,
				select_all : function() {
					var count = 0;
					$(".message-item input[type=checkbox]").each(function(){
						this.checked = true;
						$(this).closest(".message-item").addClass("selected");
						count++;
					});
					$("#id-toggle-all").get(0).checked = true;
					Inbox.display_bar(count);
				}
				,
				select_none : function() {
					$(".message-item input[type=checkbox]").removeAttr("checked").closest(".message-item").removeClass("selected");
					$("#id-toggle-all").get(0).checked = false;
					Inbox.display_bar(0);
				} 
			}
		
			//show message list (back from writing mail or reading a message)
			Inbox.show_list = function() {
				$(".message-navbar").addClass("hide");
				$("#id-message-list-navbar").removeClass("hide");
				$(".message-footer").addClass("hide");
				$(".message-footer:not(.message-footer-style2)").removeClass("hide");
				$(".message-list").removeClass("hide").next().addClass("hide");
				//hide the message item / new message window and go back to list
			}
		
			//show write mail form
			Inbox.show_form = function() {
				if($(".message-form").is(":visible")) return;
				if(!form_initialized) {
					initialize_form();
				}
				var message = $(".message-list");
				$(".message-container").append("<div class='message-loading-overlay'><i class='fa-spin ace-icon fa fa-spinner orange2 bigger-160'></i></div>");
				setTimeout(function() {
					message.next().addClass("hide");
					$(".message-container").find(".message-loading-overlay").remove();
					$(".message-list").addClass("hide");
					$(".message-footer").addClass("hide");
					$(".message-form").removeClass("hide").insertAfter(".message-list");
					$(".message-navbar").addClass("hide");
					$("#id-message-new-navbar").removeClass("hide");
					//reset form??
					//$(".message-form .wysiwyg-editor").empty();
					ue.setContent("");  
					$(".message-form .ace-file-input").closest(".file-input-container:not(:first-child)").remove();
					$(".message-form input[type=file]").ace_file_input("reset_input");
					$(".message-form").get(0).reset();
				},10);
			};
		
			var form_initialized = false;
			function initialize_form() {
				if(form_initialized) return;
				form_initialized = true;
				//intialize wysiwyg editor
				/* $(".message-form .wysiwyg-editor").ace_wysiwyg({
					toolbar:
					[

						"font",
						null,
						"fontSize",
						null,
						"foreColor",
     					null, 
     					{name:"insertunorderedlist", className:"btn-success"},
     					{name:"insertorderedlist", className:"btn-success"},
						"bold",
						"italic",
						"strikethrough",
						"underline",
						null,
						"justifyleft",
						"justifycenter",
						"justifyright",
						null,
						"createLink",
						"unlink",
						null,
						"undo",
						"redo"
					]
				}).prev().addClass("wysiwyg-style1"); */
		
				//file input
				$(".message-form input[type=file]").ace_file_input().closest(".ace-file-input").addClass("width-90 inline").wrap("<div class='form-group file-input-container'><div class='col-sm-7'></div></div>");
				
				/**
				//Add Attachment,the button to add a new file input
				$("#id-add-attachment").on("click", function(){
					$("#form-attachments-tip").html("");
					var file_input = $("<input type="file" name="attachment[]" />").appendTo("#form-attachments");
					file_input.ace_file_input();
					file_input.closest(".ace-file-input").addClass("width-90 inline").wrap("<div class="form-group file-input-container"><div class="col-sm-7"></div></div>").parent()
					.append("<div class="action-buttons pull-right col-xs-1"><a href="#" data-action="delete" class="middle"><i class="ace-icon fa fa-trash-o red bigger-130 middle"></i></a></div>").find("a[data-action=delete]").on("click", function(e){
						//the button that removes the newly inserted file input
						e.preventDefault();
						$(this).closest(".file-input-container").hide(300, function(){ $(this).remove() });
					});
				});
				**/
			}//initialize_form
			
			/**
			//turn the recipient field into a tag input field!
			var tag_input = $("#form-field-recipient");
			try { 
				tag_input.tag({placeholder:tag_input.attr("placeholder")});
			} catch(e) {}
			*/
			/**
			//and add form reset functionality
			$("#id-message-form").on("reset", function(){
				$(".message-form .message-body").empty();
				$(".message-form .ace-file-input:not(:first-child)").remove();
				$(".message-form input[type=file]").ace_file_input("reset_input_ui");
				var val = tag_input.data("value");
				tag_input.parent().find(".tag").remove();
				$(val.split(",")).each(function(k,v){
					tag_input.before("<span class="tag">"+v+"<button class="close" type="button">&times;</button></span>");
				});
			});
			*/
			
			
			//分页 ============================================================================================================== == 
 		 // message-list
				var state=1;
			$("#shouye").click(function(){ 
				if($("#hidepagenum").val()==1)
					return; 
				$.ajax({ 
					dataType : "json",
					url : "${contextPath}/sys/news/getnews",
					type : "post",
					data : {
						pagenum : 1,
						style : "0",//首页
						maxtiaoshu :10 
					},
					success : function(s) {
						
						var list =s.list;  
						var pagenum=s.pagenum;
						var totalpage=s.totalpage;  
					//	var newsnum=s.newsnum;
						$("#hidepagenum").val(pagenum);
						$("#dijiye").html(pagenum+"/"+totalpage+"页");
						var html=""; 
						 for(var i=0;i<list.length;i++){
						
							html+="<div class='message-item  message-unread'>";
							html+="<input type='hidden' id='thisid' value='"+list[i].id+"'>";
							html+="<label class='inline'>";
							html+="<input type='checkbox' class='ace' />";
							html+="<span class='lbl'></span>";
							html+="</label>";
							html+= "<i class='message-star ace-icon fa fa-star orange2'></i>";
							html+="<span class='sender' title='标题'>"+list[i].title+"</span>";
							html+="<span class='time' style='width:150px;'>"+timeStamp2String(list[i].createTime.time)+"</span>";
							html+="<span class='summary'>";	
							html+="<span class='text' name='newsxq' style='width:500px ;' >";
							html+=list[i].content;	//"${fn:substring(n.content,0,50) }"	
							html+="</span>"	;	
							html+="</span>";	
							html+="</div>";	 
						 }
						 $("#message-list").html(html);
					//	 setTimeout("parent.location.reload()",100);
					} 
				});
			});
			$("#shangyiye").click(function(){
				if($("#hidepagenum").val()==1)
					return;
				$.ajax({ 
					dataType : "json",
					url : "${contextPath}/sys/news/getnews",
					type : "post",
					data : {
						pagenum : $("#hidepagenum").val(),
						style : "-",//上一页
						maxtiaoshu :10 
					},
					success : function(s) {  
						var list =s.list;
						var pagenum=s.pagenum;
						var totalpage=s.totalpage;  
					//	var newsnum=s.newsnum;
						$("#hidepagenum").val(pagenum);
						$("#dijiye").html(pagenum+"/"+totalpage+"页");
						var html=""; 
						 for(var i=0;i<list.length;i++){
						
							html+="<div class='message-item  message-unread'>";
							html+="<input type='hidden' id='thisid' value='"+list[i].id+"'>";
							html+="<label class='inline'>";
							html+="<input type='checkbox' class='ace' />";
							html+="<span class='lbl'></span>";
							html+="</label>";
							html+= "<i class='message-star ace-icon fa fa-star orange2'></i>";
							html+="<span class='sender' title='标题'>"+list[i].title+"</span>";
							html+="<span class='time' style='width:150px;'>"+timeStamp2String(list[i].createTime.time)+"</span>";
							html+="<span class='summary'>";	
							html+="<span class='text' name='newsxq' style='width:500px ;' >";
							html+=list[i].content;	//"${fn:substring(n.content,0,50) }"	
							html+="</span>"	;	
							html+="</span>";	
							html+="</div>";	 
						 }
						 $("#message-list").html(html);
					//	 setTimeout("parent.location.reload()",100);
					} 
				});
			});
			$("#xiayiye").on("click",function(){ 
				if( $("#hidepagenum").val()==${totalpage}||state==0)
					return; 
				$.ajax({ 
					dataType : "json",
					url : "${contextPath}/sys/news/getnews",
					type : "post",
					data : {
						pagenum : $("#hidepagenum").val(),
						style : "+",//写一页
						maxtiaoshu :10 
					},
					success : function(s) {  
						var list =s.list;
						var pagenum=s.pagenum;
						var totalpage=s.totalpage;  
					//	var newsnum=s.newsnum;
						$("#hidepagenum").val(pagenum);
						$("#dijiye").html(pagenum+"/"+totalpage+"页");
						var html=""; 
						 for(var i=0;i<list.length;i++){
						 
							html+="<!-- #section:pages/inbox.message-list.item id= message-list站内消息列表 -->";
							html+="<div class='message-item  message-unread'>";
							html+="<input type='hidden' id='thisid' value='"+list[i].id+"'>";
							html+="<label class='inline'>";
							html+="<input type='checkbox' name='del' class='ace' />";
							html+="<span class='lbl'></span>";
							html+="</label>";
							html+= "<i class='message-star ace-icon fa fa-star orange2'></i>";
							html+="<span class='sender' title='标题'>"+list[i].title+"</span>";
							html+="<span class='time' style='width:150px;'>"+timeStamp2String(list[i].createTime.time)+"</span>";
							html+="<span class='summary'>";	
							html+="<span class='text' name='newsxq' style='width:500px ;' >";
							html+=list[i].content;	//"${fn:substring(n.content,0,50) }"	
							html+="</span>"	;	
							html+="</span>";	
							html+="</div>";	 
						 }
						 $("#message-list").html(html);
					//	 setTimeout("parent.location.reload()",100);
					} 
				});
			});
			$("#moye").on("click",function(){  
				if( $("#hidepagenum").val()==${totalpage}||state==0)
					return;  
				$.ajax({ 
					dataType : "json",
					url : "${contextPath}/sys/news/getnews",
					type : "post",
					data : {
						pagenum : 1,
						style : "1",//末页
						maxtiaoshu :10 
					},
					success : function(s) {   
						var list =s.list;
						var pagenum=s.pagenum;
						var totalpage=s.totalpage;  
					//	var newsnum=s.newsnum;
						$("#hidepagenum").val(pagenum);
						$("#dijiye").html(pagenum+"/"+totalpage+"页");
						var html=""; 
						 for(var i=0;i<list.length;i++){
						
							html+="<div class='message-item  message-unread'>";
							html+="<input type='hidden' id='thisid' value='"+list[i].id+"'>";
							html+="<label class='inline'>";
							html+="<input type='checkbox' class='ace' />";
							html+="<span class='lbl'></span>";
							html+="</label>";
							html+= "<i class='message-star ace-icon fa fa-star orange2'></i>";
							html+="<span class='sender' title='标题'>"+list[i].title+"</span>";
							html+="<span class='time' style='width:150px;'>"+timeStamp2String(list[i].createTime.time)+"</span>";
							html+="<span class='summary'>";	
							html+="<span class='text'  name='newsxq' style='width:500px ;' >";
							html+=list[i].content;	//"${fn:substring(n.content,0,50) }"	
							html+="</span>"	;	
							html+="</span>";	
							html+="</div>";	 
						 }
						 $("#message-list").html(html);
					//	 setTimeout("parent.location.reload()",100);
					} 
				});
			});
			function timeStamp2String(time){
			    var datetime = new Date();
			    datetime.setTime(time);
			    var year = datetime.getFullYear();
			    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
			    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
			    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
			    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
			    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
			    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
			}
			
			
			
			//发送邮件
			$("#sendEmailButton").click(function () {
			    if ($("#form-field-select-1").val() == "") {
					$.gritter.add({
		                title: "系统信息",
		                text: "请选择收件人",
		                class_name: "gritter-warning gritter-center"
		            });
			        return;
			    } else if ($("#form-field-subject").val() == "") {
					$.gritter.add({
		                title: "系统信息",
		                text: "请填写主题",
		                class_name: "gritter-warning gritter-center"
		            });
			        return;
			    } else {
			    	 
			    	$.ajax({
			    		
						dataType : "json",
						url : "${contextPath}/sys/news/sendmsg",
						type : "post",
						data : {
							users : $("#form-field-select-1").val(),
							title : $("#form-field-subject").val(),
							content : UE.getEditor('content').getContent() 
						},
						success : function(su) {
							var v = su.v;
							if(v=="发送失败，输入的有不存在用户！"){
								$.gritter.add({
					                title: "系统信息",
					                text: v,
					                class_name: "gritte  gritter-center"
					            });
								return;
							}
							$.gritter.add({
				                title: "系统信息",
				                text: v,
				                class_name: "gritter-success gritter-center"
				            });
							setTimeout("window.location.reload()",250);
						} 
					});
			    }
			});
			
			//display message right inside the message list//===========================================================================================
			//在消息下面显示消息内容
			 $(".message-list-container").on("click",".message-list .message-item .text", function(){
			//$("span[name='newsxq']").on("click", function(){ 
				var message = $(this).closest(".message-item"); 
				message.siblings().removeClass("message-inline-open").find(".message-content").remove();
				
				//if message is open, then close it
				if(message.hasClass("message-inline-open")) {
					message.removeClass("message-inline-open").find(".message-content").remove();
					return;
				}
				$(".message-container").append("<div class='message-loading-overlay'><i class='fa-spin ace-icon fa fa-spinner orange2 bigger-160'></i></div>");
			
					$(".message-container").find(".message-loading-overlay").remove();
					message.addClass("message-inline-open").append("<div class='message-content' />");
					var content = message.find(".message-content:last").html( $("#id-message-content").html() );
					//remove scrollbar elements
					content.find(".scroll-track").remove();
					content.find(".scroll-content").children().unwrap();
					content.find(".message-body").ace_scroll({
						size: 150,
						mouseWheelLock: true,
						styleClass: "scroll-visible"
					}); 
					$.ajax({ 
						async:false,
						dataType : "json",
						url : "${contextPath}/sys/news/showmsg",
						type : "post",
						data : {
							id : message.find("#thisid" ).val() 
						},
						success : function(data) {
							var d = data; 
							$("#fasongtime").html(d.createtime);
							$(".message-body").html(d.content);
							$("#sendtitle").html(d.title);
							$("#jiushouuser").html(d.user);
						} 
					});
			
				
			});
		
			//搜索 
			$("#search").keydown(function(e){
				
				if(e.keyCode==13){  
					if($("#search").val()==""){
						 <%request.setAttribute("totalpage", request.getAttribute("totalpage2"));%>  
						 $("#hidepagenum").val(2); 
						 $("#shouye").click();   
						 state=1;
						 return false;
					}
					 state=0;
						$.ajax({ 
							dataType : "json",
							url : "${contextPath}/sys/news/search",
							type : "post",
							data : {
								name :  $("#search").val()
							},
							success : function(s) { 
								if(s.sta==0){
									$("#message-list").html("没有相关信息");
								}
								else{ 
									var list =s.list;
									var pagenum=1;
									var totalpage=1;  
								//	var newsnum=s.newsnum;
									$("#hidepagenum").val(pagenum);
									$("#dijiye").html(pagenum+"/"+totalpage+"页");
									var html=""; 
									 for(var i=0;i<list.length;i++){
									
										html+="<div class='message-item  message-unread'>";
										html+="<input type='hidden' id='thisid' value='"+list[i].id+"'>";
										html+="<label class='inline'>";
										html+="<input type='checkbox' class='ace' />";
										html+="<span class='lbl'></span>";
										html+="</label>";
										html+= "<i class='message-star ace-icon fa fa-star orange2'></i>";
										html+="<span class='sender' title='标题'>"+list[i].title+"</span>";
										html+="<span class='time' style='width:150px;'>"+timeStamp2String(list[i].createTime.time)+"</span>";
										html+="<span class='summary'>";	
										html+="<span class='text' name='newsxq' style='width:500px ;' >";
										html+=list[i].content.substr(0.50);	//"${fn:substring(n.content,0,50) }"	
										html+="</span>"	;	
										html+="</span>";	
										html+="</div>";	 
									 }
									 $("#message-list").html(html);
								//	 setTimeout("parent.location.reload()",100);
								} 
							}
						});
						return false;
				} 
			}); 
			//删除消息
			$("#delete").on("click",function(){ 
				
		
				 var ids="";
				 var i=0;
				$("#message-list").find("input[name='del']:checkbox:checked").each(function(){ 
					ids+=$(this).parent().prev().val()+",";
					 i=i+1;
					});
				if(!confirm("确定删除所选的"+i+"条记录吗？"))
					return;
				$.ajax({
		    		
					dataType : "json",
					url : "${contextPath}/sys/news/delmsg",
					type : "post",
					data : {
						 ids:ids
					},
					success : function(su) {   
						alert(su.v);
						setTimeout("window.location.reload()",10);
					} 
				});
		 
			});  
		});
	
	});

</script>
 
