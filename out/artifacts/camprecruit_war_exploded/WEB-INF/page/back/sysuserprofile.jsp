<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery.gritter.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/select2.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/bootstrap-editable.css" />

<!-- ajax layout which only needs content area -->

<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="clearfix">

			<div class="pull-left">

				<div class="btn-toolbar inline middle no-margin">
					<div data-toggle="buttons" class="btn-group no-margin">
						<label class="btn btn-sm btn-yellow active">
							<span class="bigger-110">基础信息</span>

							<input type="radio" value="1" />
						</label>

						<label class="btn btn-sm btn-yellow">
							<span class="bigger-110">修改信息</span>

							<input type="radio" value="3" />
						</label>
					</div>
				</div>
			</div>
		</div>

		<div class="hr dotted"></div>

		<div>
			<div id="user-profile-1" class="user-profile row">
				<div class="col-xs-12 col-sm-3 center">
					<div>
						<!-- #section:pages/profile.picture -->

						<!-- /section:pages/profile.picture -->
						<div class="space-4"></div>

					</div>

					<div class="space-6"></div>

				</div>

				<div class="col-xs-12 col-sm-9">
					   

					<div class="space-12"></div>

					<!-- #section:pages/profile.info -->
					<div class="profile-user-info profile-user-info-striped">
						<div class="profile-info-row">
							<div class="profile-info-name"> 姓名  </div>
							<div class="profile-info-value">
								<span class="editable" id="userName">${sysuser.userName}</span>
							</div>
						</div>
						<div class="profile-info-row">
							<div class="profile-info-name">性别 </div>
							<div class="profile-info-value">
								<span class="editable" id="sex">
									<c:set var="id" value="${sysuser.id}" />
									<c:set var="sex" value="${sysuser.sex}" />
									<c:choose>
										<c:when test="${sysuser.sex == 1}">男</c:when>
										<c:otherwise>女</c:otherwise>
									</c:choose>
								</span>
							</div>
						</div>
						
						<div class="profile-info-row">
							<div class="profile-info-name"> 邮箱  </div>
							<div class="profile-info-value">
								<span class="editable" id="email" title="邮箱不可以更改">${sysuser.email}</span>
							</div>
						</div>

						<div class="profile-info-row">
							<div class="profile-info-name"> 联系电话  </div>
							<div class="profile-info-value">
								<span class="editable" id="phone">${sysuser.phone}</span>
							</div>
						</div>
						
						<div class="profile-info-row">
							<div class="profile-info-name"> 生日  </div>
							<div class="profile-info-value">
								<span class="editable" id="birthday"><fmt:formatDate  value="${sysuser.birthday}" type="date" /></span>
							</div>
						</div>
					</div>

					<!-- /section:pages/profile.info -->
					<div class="space-20"></div>

				</div>
			</div>
		</div>

		<div class="hide">
			<div id="user-profile-3" class="user-profile row">
				<div class="col-sm-offset-1 col-sm-10">
				 
					<div class="space"></div>

					<form class="form-horizontal">
						<div class="tabbable">
							<ul class="nav nav-tabs padding-16">
								<li class="active">
									<a data-toggle="tab" href="#edit-basic">
										<i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
										基本信息
									</a>
								</li>

								<li>
									<a data-toggle="tab" href="#edit-password">
										<i class="blue ace-icon fa fa-key bigger-125"></i>
										修改密码
									</a>
								</li>
							</ul>

							<div class="tab-content profile-edit-tab-content">
								<div id="edit-basic" class="tab-pane in active">
									<h4 class="header blue bolder smaller">基本信息</h4>

									<div class="row">
										<div class="vspace-12-sm"></div>

										<div class="col-xs-12 col-sm-8">
											<div class="form-group">
												<label class="col-sm-4 control-label no-padding-right" for="form-field-userName">姓名</label>

												<div class="col-sm-8">
													<input class="col-xs-12 col-sm-10" type="text" id="form-field-userName" placeholder="姓名" value="${sysuser.userName}" />
												</div>
											</div>
											
											<div class="space-4"></div>
											
											<div class="form-group">
												<label class="col-sm-4 control-label no-padding-right">性别</label>
		
												<div class="col-sm-8">
													<label class="inline">
														<input name="sex" type="radio" class="ace" value="1" <c:choose><c:when test="${sysuser.sex == 1}">checked</c:when></c:choose> />
														<span class="lbl middle">男</span>
													</label>
													&nbsp; &nbsp; &nbsp;
													<label class="inline">
														<input name="sex" type="radio" class="ace" value="2" <c:choose><c:when test="${sysuser.sex == 2}">checked</c:when></c:choose> />
														<span class="lbl middle">女</span>
													</label>
												</div>
											</div>
											
											<div class="space-4"></div>
											
											<div class="form-group">
												<label class="col-sm-4 control-label no-padding-right" for="form-field-birthday">生日</label>

												<div class="col-sm-8">
													<input class="col-xs-12 col-sm-10" type="text" id="form-field-birthday" placeholder="生日" value="<fmt:formatDate  value="${sysuser.birthday}" type="date" />" readonly />
												</div>
											</div>											
										</div>
									</div>

									<div class="space"></div>
									<h4 class="header blue bolder smaller">联系方式</h4>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-email">邮箱</label>

										<div class="col-sm-9">
											<span class="input-icon input-icon-right" title="邮箱不可以更改">
												<input type="email" id="form-field-email" value="${sysuser.email}" readonly/>
												<i class="ace-icon fa fa-envelope"></i>
											</span>
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-phone">联系电话</label>

										<div class="col-sm-9">
											<span class="input-icon input-icon-right">
												<input type="text" id="form-field-phone" value="${sysuser.phone}" />
												<i class="ace-icon fa fa-phone fa-flip-horizontal"></i>
											</span>
										</div>
									</div>

									<div class="space"></div>

								</div>

								<div id="edit-password" class="tab-pane">
									<div class="space-10"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-pass1">新密码</label>

										<div class="col-sm-9">
											<input type="password" id="form-field-pass1" />
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-pass2">确认密码</label>

										<div class="col-sm-9">
											<input type="password" id="form-field-pass2" />
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button id="submitButton" class="btn btn-info" type="button">
									<i class="ace-icon fa fa-check bigger-110"></i>
									保存
								</button>
								&nbsp; &nbsp;
								<button class="btn" type="reset">
									<i class="ace-icon fa fa-undo bigger-110"></i>
									重置
								</button>
							</div>
						</div>
					</form>
				</div><!-- /.span -->
			</div><!-- /.user-profile -->
		</div>
 
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
  <script src="${contextPath}/static/assets/js/excanvas.js"></script>
<![endif]-->
<script  type="text/javascript" src="${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js"></script>
<script  type="text/javascript" src="${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript">
	var scripts = [null,"${contextPath}/static/assets/js/jquery-ui.custom.js","${contextPath}/static/assets/js/jquery.ui.touch-punch.js","${contextPath}/static/assets/js/jquery.gritter.js","${contextPath}/static/assets/js/select2.js","${contextPath}/static/assets/js/x-editable/bootstrap-editable.js","${contextPath}/static/assets/js/x-editable/ace-editable.js", null]
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	    //inline scripts related to this page
	    jQuery(function($) {

	        //editables on first profile page
	        $.fn.editable.defaults.mode = 'inline';
	        $.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
	        $.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>' +
	            '<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';

	        //select2 editable 实现网页文本即时编辑
	        $('#userName').editable({
                type: 'text',
                pk: ${id},
                name: 'userName',
                url: "${contextPath}" + "/sys/sysuser/updateSysUserField"
            });
	        var sexs = [];
	        $.each({
	            "1": "男",
	            "2": "女"
	        }, function(k, v) {
	            sexs.push({
	                id: k,
	                text: v
	            });
	        });
	        $('#sex').editable({
	            type: 'select2',
	            pk: ${id},
	            value: ${sex},
	            //onblur:'ignore',
	            source: sexs,
	            select2: {
	                'width': 140
	            },
	            name: 'sex',
	            url: "${contextPath}" + "/sys/sysuser/updateSysUserField",
	            success: function(response, newValue) {
	            }
	        });
	        //text editable
	        /**
	        $('#email').editable({
                type: 'text',
                pk: ${id},
                name: 'email',
                url: "${contextPath}" + "/sys/sysuser/updateSysUserField"
            });
	        **/
	        $('#phone').editable({
                type: 'text',
                pk: ${id},
                name: 'phone',
                url: "${contextPath}" + "/sys/sysuser/updateSysUserField"
            });
	        $('#birthday').editable({
                type: 'adate',
                pk: ${id},
                name: 'birthday',
                url: "${contextPath}" + "/sys/sysuser/updateSysUserField",
    			date: {
    				format: 'yyyy-mm-dd',
    				viewformat: 'yyyy-mm-dd',
    				weekStart: 1,
    				language: 'zh-CN'
    			}
            });
	    	$('#form-field-birthday').datepicker({
	    	    format: 'yyyy-mm-dd',
	    	    language: 'zh-CN'
	    	});

	        /**
			//let's display edit mode by default?
			var blank_image = true;//somehow you determine if image is initially blank or not, or you just want to display file input at first
			if(blank_image) {
				$('#avatar').editable('show').on('hidden', function(e, reason) {
					if(reason == 'onblur') {
						$('#avatar').editable('show');
						return;
					}
					$('#avatar').off('hidden');
				})
			}
			*/

	        //right & left position
	        //show the user info on right or left depending on its position
	        $('#user-profile-2 .memberdiv').on('mouseenter touchstart', function() {
	            var $this = $(this);
	            var $parent = $this.closest('.tab-pane');
	            var off1 = $parent.offset();
	            var w1 = $parent.width();
	            var off2 = $this.offset();
	            var w2 = $this.width();
	            var place = 'left';
	            if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2)) place = 'right';
	            $this.find('.popover').removeClass('right left').addClass(place);
	        }).on('click', function(e) {
	            e.preventDefault();
	        });

	        ///////////////////////////////////////////
	        
			////////////////////
			$("a[href='#edit-password']").on('shown.bs.tab', function (e) {
			});
			$('#submitButton').on('click', function() {
				if($('#form-field-pass1').val() != '' && ($('#form-field-pass1').val().length < 6 || $('#form-field-pass1').val().length > 14)){
					$.gritter.add({
		                title: '系统信息',
		                text: '新密码长度至少为6个字符，至多为14个字符',
		                class_name: 'gritter-warning gritter-center'
		            });
				}else if($('#form-field-pass1').val() != '' && $('#form-field-pass1').val() != $('#form-field-pass2').val()){
					$.gritter.add({
		                title: '系统信息',
		                text: '新密码和确认密码不一样，请重新输入',
		                class_name: 'gritter-warning gritter-center'
		            });
				}else if($('#form-field-pass1').val() != '' && $('#form-field-pass1').val() == $('#form-field-pass2').val()){
					$.ajax({
						dataType : "json",
						url : "${contextPath}" + "/sys/sysuser/resetPassword",
						type : "post",
						data : {
							password : $('#form-field-pass1').val()
						},
						complete : function(xmlRequest) {
							$.gritter.add({
				                title: '系统信息',
				                text: '密码修改成功',
				                class_name: 'gritter-success gritter-center'
				            });
						}
					});
				}else{
					$.ajax({
						dataType : "json",
						url : "${contextPath}" + "/sys/sysuser/saveSysUserProfile",
						type : "post",
						data : {
							sex : $("input[name='sex']:checked").val(),
							email : $('#form-field-email').val(),
							userName : $('#form-field-userName').val(),
							phone : $('#form-field-phone').val(),
							birthday: $('#form-field-birthday').val()
						},
						complete : function(xmlRequest) {
							$.gritter.add({
				                title: '系统信息',
				                text: '基本信息修改成功',
				                class_name: 'gritter-success gritter-center'
				            });
						}
					});					
				}
			});

	        ////////////////////
	        //change profile
	        $('[data-toggle="buttons"] .btn').on('click', function(e) {
	            var target = $(this).find('input[type=radio]');
	            var which = parseInt(target.val());
	            $('.user-profile').parent().addClass('hide');
	            $('#user-profile-' + which).parent().removeClass('hide');
	        });

	        /////////////////////////////////////
	        $(document).one('ajaxloadstart.page', function(e) {
	            //in ajax mode, remove remaining elements before leaving page
	            try {
	                $('.editable').editable('destroy');
	            } catch (e) {}
	            $('[class*=select2]').remove();
	        });
	    });

	});
</script>
