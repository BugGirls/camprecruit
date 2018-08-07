<%@ page  pageEncoding="utf-8"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
String imgPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
%>
    <style type="text/css">
    </style>
		<!-- <link rel="stylesheet" type="text/css" href="<%=basePath %>/jsp/css/ie_patch.css" />  -->
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/style/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/style/css/external.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/style/css/popup.css"/>
    <!--[if gte IE 9]>
	<![endif]--> 

<div id="pre_header" style="min-width: 1024px;    height: 30px;    background: #019875;    border-top: 3px solid #019875;">
		<div style=" width: 1024px;    margin: 0 auto;    position: relative;    z-index: 90;">
			<ul class="navheader">
	           	<li class="nav-down cur"><i class="m-icon m-icon-location"></i><a href="<%=basePath %>login.html" rel="nofollow">所在城市</a></li> 
	           	<li class="nav-down cur"><i class="m-icon m-icon-home"></i><a href="<%=basePath %>register.html" rel="nofollow">首页</a></li>
            </ul>
			<ul class="loginTop">
	           	<li><a href="<%=basePath %>register.html" rel="nofollow">服务</a></li>
	           	<li><a href="<%=basePath %>login.html" rel="nofollow">登录</a></li> 
	           	<li style="background:#ff6600;"><a href="<%=basePath %>register.html" rel="nofollow">注册</a></li>
            </ul>
		</div>
		
	</div>
	<div id="header">
    	<div class="wrapper">
    		<a href="<%=basePath %>index.html" class="logo">
    			<img src="<%=basePath %>style/images/logo.png" width="229" height="43" alt="营才招聘-专注营地人才招聘" />
    		</a>
    		
    		
    		<div id="search_box">
				<form id="searchForm" name="searchForm" action="<%=basePath %>list.html" method="get">
			        <input type="text" id="search_input" name = "kd"  tabindex="1" value=""  placeholder="搜索职位、公司或地点"  />
			        <input type="hidden" name="spc" id="spcInput" value=""/>
			        <input type="hidden" name="pl" id="plInput" value=""/>
			        <input type="hidden" name="gj" id="gjInput" value=""/>
			        <input type="hidden" name="xl" id="xlInput" value=""/>
			        <input type="hidden" name="yx" id="yxInput" value=""/>
			        <input type="hidden" name="gx" id="gxInput" value="" />
			        <input type="hidden" name="st" id="stInput" value="" />
			        <input type="hidden" name="labelWords" id="labelWords" value="" />
			        <input type="hidden" name="lc" id="lc" value="" />
			        <input type="hidden" name="workAddress" id="workAddress" value=""/>
			                <input type="hidden" name="city" id="cityInput" value=""/>
			                <input type="submit" id="search_button" value="搜索" />
							
			    </form>
			    <div class="searchHot" style=" ">
			    	<span>热门搜索:</span><a>教练</a><a>培训师</a><a>营地导师</a><a>拓展培训师</a>
			    </div>
			</div>
			<div style=" float: right;">
				<image src="<%=basePath %>images/img_rencairuzhu.jpg" width="150px"/>
			</div>
			
			<script type="text/javascript" src="<%=imgPath %>style/js/search.min.js"></script>
        </div>
    </div><!-- end #header -->