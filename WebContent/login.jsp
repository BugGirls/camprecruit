<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title> 无人超市云服务管理平台</title>

		<meta name="description" content="无人超市云服务平台后台管理|登录页面" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${contextPath}/static/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="${contextPath}/static/assets/css/font-awesome.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${contextPath}/static/assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${contextPath}/static/assets/css/ace.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${contextPath}/static/assets/css/ace-part2.css" />
		<![endif]-->
		<link rel="stylesheet" href="${contextPath}/static/assets/css/ace-rtl.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${contextPath}/static/assets/css/ace-ie.css" />
		<![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		<script src="${contextPath}/static/assets/js/html5shiv.js"></script>
		<script src="${contextPath}/static/assets/js/respond.js"></script>
		<![endif]-->
	</head> 
	<body class="login-layout light-login">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1" style=" margin-top: 15%;">
						<div class="login-container" style="margin-right: 10%;">
							 
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header  lighter bigger">
												无人超市云服务管理平台
											</h4>


											<form id="validationLoginForm" method="post" action="#" style="    margin-bottom: 22px;">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<!-- <input id="loginEmail" name="email" type="text" class="form-control" placeholder="邮箱" data-rel="tooltip" title="邮箱作为账号" data-placement="right" /> -->
															<input id="loginEmail" name="email" type="text" class="form-control" placeholder="邮箱" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="loginPassword" name="password" type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>
													
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<span id="loginTip" style="color:#A94442"></span>
														</span>
													</label>


													<div class="toolbar clearfix" style="background: #f5f5f5;border-top: 0px;">
														<label class="inline">
															<input id="rememberMe" name="rememberMe" type="checkbox" class="ace" />
															<span class="lbl">下次自动登录</span>
														</label>
														<a href="#" data-target="#forgot-box" class="width-40 pull-right btn btn-sm btn-primary forgot-password-link" style="background-color: #ccc !important; border-color: #fff !important;">
															<span class="bigger-110" style="color:#545454;">忘记密码?</span>
														</a>
													</div>

														<button id="loginButton" type="button" class="width-100 pull-right btn btn-sm btn-primary" style="background-color: #9ED04D !important; border-color: #fff !important;">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
												</fieldset>
											</form>

										</div><!-- /.widget-main -->

									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="ace-icon fa fa-key"></i>
												找回密码
											</h4>

											<div class="space-6"></div>
											<p>
												输入您的电子邮箱
											</p>

											<form id="validationRetrieveForm" method="post" action="#">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="retrieveEmail" name="email" type="email" class="form-control" placeholder="邮箱" data-rel="tooltip" title="邮箱作为用户名" data-placement="right" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>
													
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<span id="retrieveTip" style="color:#A94442"></span>
														</span>
													</label>

													<div class="clearfix">
														<button id="retrieveButton" type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="ace-icon fa fa-lightbulb-o"></i>
															<span class="bigger-110">发送</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												登录
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									 <!-- /.widget-body -->
								</div><!-- /.signup-box -->
							</div><!-- /.position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
			<div class="footer">
                <div class="footer-inner">
                    <!-- #section:basics/footer -->
                    <div class="footer-content-nobordertop">
                        <span class="bigger-120">
                            <span class="blue bolder">
                                                                                                 无人超市云服务管理平台 
                            </span>
                            &copy; 
                            2018-2019
                        </span>
                    </div>
                    <!-- /section:basics/footer -->
                </div>
            </div>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${contextPath}/static/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
 			window.jQuery || document.write("<script src='${contextPath}/static/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${contextPath}/static/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		
		<script type="text/javascript" src="${contextPath}/static/assets/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${contextPath}/static/assets/js/tooltip.js"></script>
		<script type="text/javascript" src="${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js"></script>
		<script type="text/javascript" src="${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
		<script type="text/javascript" src="${contextPath}/static/assets/js/custom/login.js"></script>

	</body>
</html>
