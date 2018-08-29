<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="utf-8" />
<<<<<<< HEAD
        <title>无人超市云服务平台后台管理</title>
=======
        <title>云尚互联后台管理平台</title>
>>>>>>> merge project
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="${contextPath}/static/assets/css/bootstrap.css" />
        <link rel="stylesheet" href="${contextPath}/static/assets/css/font-awesome.css" />
        <!-- text fonts -->
        <link rel="stylesheet" href="${contextPath}/static/assets/css/ace-fonts.css" />
        <link rel="stylesheet" href="${contextPath}/static/assets/css/activities-serverload.css" />
        <!-- ace styles -->
        <link rel="stylesheet" href="${contextPath}/static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
        <!--[if lte IE 9]>
			<link rel="stylesheet" href="${contextPath}/static/assets/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->
        <!--[if lte IE 9]>
		  <link rel="stylesheet" href="${contextPath}/static/assets/css/ace-ie.css" />
		<![endif]-->
        <!-- ace settings handler -->
        <script src="${contextPath}/static/assets/js/ace-extra.js"></script>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lte IE 8]>
		<script src="${contextPath}/static/assets/js/html5shiv.js"></script>
		<script src="${contextPath}/static/assets/js/respond.js"></script>
		<![endif]-->
    </head>
    <body class="no-skin">
        <!-- #section:basics/navbar.layout -->
        <div id="navbar" class="navbar navbar-default"  style="background: white url(${contextPath}/jsp/images/img_nav_bg.jpg) top center repeat; ">
            <script type="text/javascript">
				try {
					ace.settings.check('navbar', 'fixed')
				} catch (e) {
				}
			</script>
            <div class="navbar-container" id="navbar-container">
                <!-- #section:basics/sidebar.mobile.toggle -->
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                    <span class="sr-only">
                        Toggle sidebar
                    </span>
                    <span class="icon-bar">
                    </span>
                    <span class="icon-bar">
                    </span>
                    <span class="icon-bar">
                    </span>
                </button>
                <!-- /section:basics/sidebar.mobile.toggle -->
                <div class="navbar-header pull-left">
                    <!-- #section:basics/navbar.layout.brand -->
						<img class="" alt="Alex's Avatar" src="${contextPath}/jsp/images/logo_top.jpg" style="float: left; margin-top: 6px; max-width: 40px;"></img>
                    <a href="#" class="navbar-brand">
                        <small>
<<<<<<< HEAD
							无人超市云服务平台后台管理
=======
							云尚互联后台管理平台
>>>>>>> merge project
                        </small>
                    </a>
                    <!-- /section:basics/navbar.layout.brand -->
                    <!-- #section:basics/navbar.toggle -->
                    <!-- /section:basics/navbar.toggle -->
                </div>
                <!-- #section:basics/navbar.dropdown -->
                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">
 
                        <!-- #section:basics/navbar.user_menu -->
                        <li class="light-blue" style="    ">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="${contextPath}/static/assets/avatars/profile-pic.jpg" alt="加盟商" />
                                <span class="user-info">
                                    <small>
										欢迎您,
                                    </small>
									<c:out value="${sessionScope.SESSION_SYS_USER.userName}"/>
                                </span>
                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>
                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <!-- 
                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
										设置
                                    </a>
                                </li>
                                -->
                                <li>
                                    <a data-url="page/sysuserprofile" href="home#page/sysuserprofile">
                                        <i class="ace-icon fa fa-user"></i>
										个人资料
                                    </a>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <a href="${contextPath}/sys/sysuser/logout">
                                        <i class="ace-icon fa fa-power-off"></i>
										退出
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <!-- /section:basics/navbar.user_menu -->
                    </ul>
                </div>
                <!-- /section:basics/navbar.dropdown -->
            </div>
            <!-- /.navbar-container -->
        </div>
        <!-- /section:basics/navbar.layout -->
        <div class="main-container" id="main-container" >
            <script type="text/javascript">
				try {
					ace.settings.check('main-container', 'fixed')
				} catch (e) {
				}
			</script>
            <!-- #section:basics/sidebar -->
            <div id="sidebar" class="sidebar responsive">
                <script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'fixed')
					} catch (e) {
					}
				</script>
				<!-- 
                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>
                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>
                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>
                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>
                    </div>
                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success">
                        </span>
                        <span class="btn btn-info">
                        </span>
                        <span class="btn btn-warning">
                        </span>
                        <span class="btn btn-danger">
                        </span>
                    </div>
                </div>
                -->
                <!-- /.sidebar-shortcuts -->
                <ul class="nav nav-list">
                	<c:forEach var="authority" items="${authorityList}">
		                    <li class="">
		                        <a href="<c:out value='${authority.dataUrl}'/>" class="dropdown-toggle">
		                            <i class="<c:out value='${authority.menuClass}'/>"></i>
		                            <span class="menu-text"><c:out value="${authority.menuName}"/></span>
		                            <b class="arrow fa fa-angle-down"></b>
		                        </a>
		                        <b class="arrow"></b>
		                        <ul class="submenu">
		                        	<c:forEach var="subAuthorityList" items="${authority.subAuthorityList}">
			                        <li class="">
		                                <a data-url="<c:out value='${subAuthorityList.dataUrl}'/>" href="home#<c:out value='${subAuthorityList.dataUrl}'/>">
		                                    <i class="<c:out value='${subAuthorityList.menuClass}'/>"></i><c:out value="${subAuthorityList.menuName}"/>
		                                </a>
		                                <b class="arrow"></b>
		                            </li>
		                            </c:forEach>
		         				</ul>
		                    </li>
                    </c:forEach>
                </ul>
                <!-- /.nav-list -->
                <!-- #section:basics/sidebar.layout.minimize -->
                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
                <!-- /section:basics/sidebar.layout.minimize -->
                <script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'collapsed')
					} catch (e) {
					}
				</script>
            </div>
            <!-- /section:basics/sidebar -->
            <div class="main-content">
                <!-- #section:basics/content.breadcrumbs -->
                <div class="breadcrumbs" id="breadcrumbs">
                    <script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>
                    <ul class="breadcrumb">
                        <li>
                            <i class="ace-icon fa fa-home home-icon"></i>
                            <a href="${contextPath}/sys/sysuser/home">
								 首页
                            </a>
                        </li>
                    </ul>
                    <!-- /.breadcrumb -->
                    <!-- #section:basics/content.searchbox -->
                    <!-- 
                    <div class="nav-search" id="nav-search">
                        <form class="form-search">
                            <span class="input-icon">
                                <input type="text" placeholder="全文检索 ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
                                <i class="ace-icon fa fa-search nav-search-icon"></i>
                            </span>
                        </form>
                    </div>
                    -->
                    <!-- /.nav-search -->
                    <!-- /section:basics/content.searchbox -->
                </div>
                <!-- /section:basics/content.breadcrumbs -->
                <div class="page-content">
                    <div class="page-content-area" data-ajax-content="true">
                        <!-- ajax content goes here -->
                    </div>
                    <!-- /.page-content-area -->
                </div>
                <!-- /.page-content -->
            </div>
            <!-- /.main-content -->
            <div class="footer">
                <div class="footer-inner">
                    <!-- #section:basics/footer -->
                    <div class="footer-content" style="border-top: 0px;">
                        <span class="bigger-120">
                            <span class="blue bolder">
<<<<<<< HEAD
                                                                                                无人超市云服务平台后台管理
=======
                                                                                                云尚互联后台管理平台
>>>>>>> merge project
                            </span>
                            &copy; 
                            2018-2019
                        </span>
                    </div>
                    <!-- /section:basics/footer -->
                </div>
            </div>
            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div>
        <!-- /.main-container -->
        <!-- basic scripts -->
        <!--[if !IE]> -->
			<script type="text/javascript">
				window.jQuery || document.write("<script src='${contextPath}/static/assets/js/jquery.js'>" + "<"+"/script>");
			</script>
		<!-- <![endif]-->
        <!--[if IE]>
			<script type="text/javascript">
			 	window.jQuery || document.write("<script src='${contextPath}/static/assets/js/jquery1x.js'>"+"<"+"/script>");
			</script>
		<![endif]-->
        <script type="text/javascript">
			if ('ontouchstart' in document.documentElement)
				document.write("<script src='${contextPath}/static/assets/js/jquery.mobile.custom.js'>" + "<"+"/script>");
		</script>
        <script src="${contextPath}/static/assets/js/bootstrap.js"></script>
        <!-- ace scripts -->
        <script src="${contextPath}/static/assets/js/ace/elements.scroller.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.colorpicker.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.fileinput.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.typeahead.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.wysiwyg.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.spinner.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.treeview.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.wizard.js"></script>
        <script src="${contextPath}/static/assets/js/ace/elements.aside.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.ajax-content.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.touch-drag.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.sidebar.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.sidebar-scroll-1.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.submenu-hover.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.widget-box.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.settings.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.settings-rtl.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.settings-skin.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.widget-on-reload.js"></script>
        <script src="${contextPath}/static/assets/js/ace/ace.searchbox-autocomplete.js"></script>
    </body>
</html>
