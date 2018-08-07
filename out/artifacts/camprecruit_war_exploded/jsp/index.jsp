<%@page import="com.jeefw.model.sys.CompanyUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
String requestPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
CompanyUser companyUser = (CompanyUser)request.getSession().getAttribute("companyuser"); 
%>
<!DOCTYPE HTML>
<html xmlns:wb="http://open.weibo.com/wb">
<head>
<script id="allmobilize" charset="utf-8" src="style/js/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld"  />
<!-- end 云适配 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>营才网-最专业的营地人才招聘平台</title>
<meta property="qc:admins" content="23635710066417756375" />
<meta content="营才网是3W旗下的营地人才领域垂直招聘网站,营地职业机会尽在营才网" name="description">
<meta content="营才,营才网,营才招聘,拉钩, 拉钩网 ,营地人才招聘,营才网招聘, 移动营地人才招聘, 垂直营地人才招聘, 微信招聘, 微博招聘, 营才官网, 营才百科,跳槽, 高薪职位, 营地人才圈子, IT招聘, 职场招聘, 猎头招聘,O2O招聘, LBS招聘, 社交招聘, 校园招聘, 校招,社会招聘,社招" name="keywords">


<link rel="Shortcut Icon" href="h/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="style/css/common.css"/>
<link rel="stylesheet" type="text/css" href="style/css/external.min.css"/>
<link rel="stylesheet" type="text/css" href="style/css/popup.css"/>

<!--导航下边-->
<link rel="stylesheet" href="css/index_sync0_libs_6f5905d.css"/>
<link type="text/css" rel="stylesheet" href="css/swiper-3.4.1.min.css"/>

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script src="style/js/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" src="style/js/additional-methods.js"></script>

<script src="js/swiper-3.4.1.jquery.min.js"></script>
<!--[if lte IE 8]>
    <script type="text/javascript" src="style/js/excanvas.js"></script>
<![endif]-->
</head>
<body>
<div id="body">
	<div id="pre_header" style="min-width: 1024px;    height: 30px;    background: #019875;    border-top: 3px solid #019875;">
		<div style=" width: 1024px;    margin: 0 auto;    position: relative;    z-index: 90;">
			<ul class="navheader">
	           	<!-- <li class="nav-down cur"><i class="m-icon m-icon-location"></i><a href="login.html" rel="nofollow">所在城市</a></li>  -->
	           	<li class="nav-down cur"><i class="m-icon m-icon-home"></i><a href="index" rel="nofollow">营才网</a></li>
	           	<li class="nav-down cur" style="font-size: 12px; font-weight: normal;"><i style="margin: auto 3px;font-size: 18px;">|</i><a href="<%if(null==companyUser){ %>clogin.jsp<%}else{%>company_center.jsp<%}%>" rel="nofollow">进入企业版</a></li>
            </ul>
			<ul class="loginTop">
	           	<li><a href="login.jsp" rel="nofollow">登录</a></li> 
	           	<li style="background:#ff6600;"><a href="register.jsp" rel="nofollow">注册</a></li>
            </ul>
		</div>
		
	</div>
	<div id="header">
    	<div class="wrapper">
    		<a href="index.html" class="logo">
    			<img src="style/images/logo.png" width="229" height="43" alt="营才招聘-专注营地人才招聘" />
    		</a>
    		
    		
    		<div id="search_box">
				<form id="searchForm" name="searchForm" action="list.html" method="get">
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
			    <div class="searchHot" style="">
			    	<span>热门搜索:</span><a>教练</a><a>培训师</a><a>营地导师</a><a>拓展培训师</a>
			    </div>
			</div>
			<div style=" float: right;">
				<image src="images/img_rencairuzhu.jpg" width="150px"/>
			</div>
			
			<script type="text/javascript" src="style/js/search.min.js"></script>
        </div>
    </div><!-- end #header -->
    <style>
			.talent-nav {
    height: 44px;
    border-bottom: 2px solid #0d9572;
    background-color: #FFF;
    line-height: 42px;
    font-size: 14px;
    font-weight: 700;
        margin: 30px auto 0;
}
	.m-warp {
    position: relative;
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 1024px;
    height: auto;
}
.talent-nav ul {
    width: 100%;
    list-style: none;
}
.talent-nav li {
    float: left;
    padding: 0 20px;
    font-size: 18px;
    color: #999;
}
.talent-nav-first {
    width: 190px;
    background-color: #0d9572;
    color: #FFF !important;
    margin-right: 12px;
    padding-left: 25px;
    height: 45px;
}
			</style>
   	<div class="talent-nav">
	    <div class="m-warp">
	        <ul class="clearfix">
	            <li class="talent-nav-first">职位分类</li>
	            <li class="current"><a href="index.html">首页</a></li>
    			<li ><a href="companylist.html" >公司</a></li>
    			<li ><a href="h/toForum.html" target="_blank">论坛</a></li>
    			<li ><a href="jianli.html" rel="nofollow">我的简历</a></li>
    			<li ><a href="create.html" rel="nofollow">发布职位</a></li>
	        </ul>
	    </div>
	</div>
	
<!--导航下边部分-->
  
    <div class="cf" style="">
      <div class=" " style="    width: 100%;       float: left;">
        <div id="index_img" class="swiper-container banner-box" style="cursor: -webkit-grab;"> 
          <ul id="shuffing_list_" class="swiper-wrapper">
           
          </ul>
          <div class="swiper-pagination" id="index_img_page" style="margin-bottom: 50px;"></div>
        </div>
      </div>
      
      
      
      <div class="lesson-classfiy-nav" >
      <ul style="overflow: hidden; border-bottom-width: 0px; height: 350px;" id="careertypetree">
			<li>
        		<div  style="cursor: auto;"> <a cgid="1" href="top/courseindex/searchCourse?family=2">营地</a> 
	              <div class="lesson-list-show" style="min-height: 410px; width: 401px;">
	                <div style="width: 400px;">
	                	<dl> 
	                    	<dt><a href="top/courseindex/searchCourse?family=2 " cgid="2">营地 </a></dt>
	                    	
	                    	<dd class="cf">
	                    			<a href="top/courseindex/searchCourse?family=21" cgid="21">教练</a>
	                    			<a href="top/courseindex/searchCourse?family=22" cgid="22">培训师</a>
	                    			<a href="top/courseindex/searchCourse?family=23" cgid="23">导师</a>
	                    			<a href="top/courseindex/searchCourse?family=24" cgid="24">助教</a>
	                    	</dd>
	                 	</dl>
	                </div>
	             </div>
	            </div> 
	        </li> 
	        <li>
        		<div  style="cursor: auto;"> <a cgid="1" href="top/courseindex/searchCourse?family=3">营地</a> 
	              <div class="lesson-list-show" style="min-height: 410px; width: 401px;">
	                <div style="width: 400px;">
	                	<dl> 
	                    	<dt><a href="top/courseindex/searchCourse?family=3 " cgid="3">营地 </a></dt>
	                    	
	                    	<dd class="cf">
	                    			<a href="top/courseindex/searchCourse?family=31" cgid="31">教练</a>
	                    			<a href="top/courseindex/searchCourse?family=32" cgid="32">培训师</a>
	                    			<a href="top/courseindex/searchCourse?family=33" cgid="33">导师</a>
	                    			<a href="top/courseindex/searchCourse?family=34" cgid="34">助教</a>
	                    	</dd>
	                 	</dl>
	                </div>
	             </div>
	            </div> 
	        </li> 
      	</ul>
      </div> 
    </div>
    <script src="js/nav.js"></script>
    <script src="js/common_sync0_libs_5d57f5b.js" class="lazyload" charset="utf-8"></script> 
 	<script type="text/javascript">LazyLoad= function (e){function t(t,n){var s,r=e.createElement(t);for(s in n)n.hasOwnProperty(s)&&r.setAttribute(s,n[s]);return r}function n(e){var t,n,s=i[e];s&&(t=s.callback,n=s.urls,n.shift(),u=0,n.length||(t&&t.call(s.context,s.obj),i[e]=null,f[e].length&&r(e)))}function s(){var t=navigator.userAgent;o={async:e.createElement("script").async===!0},(o.webkit=/AppleWebKit\//.test(t))||(o.ie=/MSIE|Trident/.test(t))||(o.opera=/Opera/.test(t))||(o.gecko=/Gecko\//.test(t))||(o.unknown=!0)}function r(r,u,h,g,d){var p,y,m,b,k,v,j=function(){n(r)},w="css"===r,E=[];if(o||s(),u)if(u="string"==typeof u?[u]:u.concat(),w||o.async||o.gecko||o.opera)f[r].push({urls:u,callback:h,obj:g,context:d});else for(p=0,y=u.length;y>p;++p)f[r].push({urls:[u[p]],callback:p===y-1?h:null,obj:g,context:d});if(!i[r]&&(b=i[r]=f[r].shift())){for(l||(l=e.head||e.getElementsByTagName("head")[0]),k=b.urls,p=0,y=k.length;y>p;++p){if(v=k[p],w?m=o.gecko?t("style"):t("link",{href:v,rel:"stylesheet"}):(m=t("script",{src:v}),m.async=!1),m.className="lazyload",m.setAttribute("charset","utf-8"),o.ie&&!w&&"onreadystatechange"in m&&!("draggable"in m))m.onreadystatechange=function(){/loaded|complete/.test(m.readyState)&&(m.onreadystatechange=null,j())};else if(w&&(o.gecko||o.webkit))if(o.webkit){var T;if(b.urls[p]=m.href,T=c()){p--,y=k.length;continue}}else m.innerHTML='@import "'+v+'";',a(m);else m.onload=m.onerror=j;E.push(m)}var A=document.createDocumentFragment();for(p=0,y=E.length;y>p;++p)A.appendChild(E[p]);var x;return"css"===r?x=l:"js"===r&&(x=document.getElementById("pages-container")||l),x.appendChild(A),E}}function a(e){var t;try{t=!!e.sheet.cssRules}catch(s){return u+=1,void(200>u?setTimeout(function(){a(e)},50):t&&n("css"))}n("css")}function c(){var e,t=i.css,s=!1;if(t){for(e=h.length;--e>=0;)if(h[e].href===t.urls[0]){s=!0,n("css");break}u+=1,t&&(200>u?setTimeout(c,50):n("css"))}return s}var o,l,i={},u=0,f={css:[],js:[]},h=e.styleSheets;return{css:function(e,t,n,s){r("css",e,t,n,s)},js:function(e,t,n,s){r("js",e,t,n,s)}}}(this.document);</script> 
 
	<script type="text/javascript">
	     var _ready = false;_list = [];_when = function(cb) {_ready?cb():_list.push(cb);};LazyLoad.js(['js/index_sync0_libs_b145800.js'], function () {!function() {}(); 
	!function() {
	   	 require("index:widget/leftnav/leftnav.js").init();   
	}();
	
	
	_ready=true;var _item; while((_item=_list.shift())){_item();}});
	</script>
	<!--导航下边部分 结束-->

    <div id="container">
        <div class="content">	
			<!-- 名企职位 -->
			<div class="" >
		        <div class="heada" style="width: 100%;">
				    <span class="bt">
				    	<a href="jsp/neighbouring_promotion_list.jsp" target="_blank" title="名企职位">
				      	 名企职位</a>
				    </span>
				    <span class="En_bt">Enterprise</span>
				    <a href="jsp/neighbouring_promotion_list.jsp" class="more" target="_blank" title="更多>>">更多&gt;&gt;</a>
			    </div>
			    <ul id="" class="da-thumbs">
	        		<li >
	                    <a href="h/c/1650.html" target="_blank">
	                        <img src="style/images/img_qiye_11.gif" width="113" height="113" alt="拓展者" />
	                        <div class="hot_info">
	                        	<h2 title="拓展者">拓展者</h2>
	                            <em></em>
	                            <p title="拓展者">
	                            	为提高国民素质而努力
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	<li >
	                    <a href="h/c/9725.html" target="_blank">
	                        <img src="style/images/img_qiye_12.png" width="113" height="113" alt="智慧营地" />
	                        <div class="hot_info">
	                        	<h2 title="智慧营地">智慧营地</h2>
	                            <em></em>
	                            <p title="让培训更有效">
	                            	让培训更有效
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	<li >
	                    <a href="h/c/1914.html" target="_blank">
	                        <img src="style/images/img_qiye_13.png" width="113" height="113" alt="冒险家" />
	                        <div class="hot_info">
	                        	<h2 title="冒险家">冒险家</h2>
	                            <em></em>
	                            <p title="让孩子在体验中成长">
	                            	让孩子在体验中成长
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	<li >
	                    <a href="h/c/6630.html" target="_blank">
	                        <img src="style/images/img_qiye_14.png" width="113" height="113" alt="河南众心一加一" />
	                        <div class="hot_info">
	                        	<h2 title="河南众心一加一">河南众心一加一</h2>
	                            <em></em>
	                            <p title="为打造完美团队而努力">
	                            	为打造完美团队而努力
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	<li >
	                    <a href="h/c/2700.html" target="_blank">
	                        <img src="style/images/img_qiye_16.jpg" width="113" height="113" alt="郑州立体心" />
	                        <div class="hot_info">
	                        	<h2 title="郑州立体心">郑州立体心</h2>
	                            <em></em>
	                            <p title="没有围墙的课堂">
	                            	没有围墙的课堂
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	<li  class="last" >
	                    <a href="h/c/1335.html" target="_blank">
	                        <img src="style/images/c0052c69ef4546c3b7d08366d0744974.jpg" width="113" height="113" alt="堆糖网" />
	                        <div class="hot_info">
	                        	<h2 title="堆糖网">堆糖网</h2>
	                            <em></em>
	                            <p title="分享收集生活中的美好，遇见世界上的另外一个你">
	                            	分享收集生活中的美好，遇见世界上的另外一个你
	                            </p>
	                        </div>
	                    </a>
	                </li>
                </ul>
		    </div>
			
        	<!-- 高校联盟 -->
			<div class="" >
		        <div class="heada" style="width: 100%;">
				    <span class="bt">
				    	<a href="jsp/neighbouring_promotion_list.jsp" target="_blank" title="高校联盟">
				      	 高校联盟</a>
				    </span>
				    <span class="En_bt">Universities</span>
				    <a href="jsp/neighbouring_promotion_list.jsp" class="more" target="_blank" title="更多>>">更多&gt;&gt;</a>
			    </div>
			    <ul id="cooperation_colleges_" class="da-thumbs">
	        		        		<li >
	                    <a href="h/c/1650.html" target="_blank">
	                        <img src="style/images/img_school_2422.png" width="113" height="113" alt="安徽工程大学" />
	                        <div class="hot_info">
	                            <p title="安徽工程大学">
	                            	安徽工程大学
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	        		<li >
	                    <a href="h/c/9725.html" target="_blank">
	                        <img src="style/images/img_school_455.png" width="113" height="113" alt="西安体育学院" />
	                        <div class="hot_info">
	                            <p title="西安体育学院">
	                            	西安体育学院
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	        		<li >
	                    <a href="h/c/1914.html" target="_blank">
	                        <img src="style/images/img_school_54.png" width="113" height="113" alt="北京体育大学" />
	                        <div class="hot_info">
	                            <p title="北京体育大学">
	                            	北京体育大学
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	        		<li >
	                    <a href="h/c/6630.html" target="_blank">
	                        <img src="style/images/img_school_40.png" width="113" height="113" alt="北京航空航天大学" />
	                        <div class="hot_info">
	                            <p title="北京航空航天大学">
	                            	北京航空航天大学
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	        		<li >
	                    <a href="h/c/2700.html" target="_blank">
	                        <img src="style/images/img_school_84.png" width="113" height="113" alt="郑州大学" />
	                        <div class="hot_info">
	                            <p title="郑州大学">
	                            	郑州大学
	                            </p>
	                        </div>
	                    </a>
	                </li>
                	        		<li  class="last" >
	                    <a href="h/c/1335.html" target="_blank">
	                        <img src="style/images/img_school_655.png" width="113" height="113" alt="复旦大学" />
	                        <div class="hot_info">
	                            <p title="复旦大学">
	                            	复旦大学
	                            </p>
	                        </div>
	                    </a>
	                </li>
                </ul>
		    </div>
            
            <ul class="reset hotabbing">
     	            		<li class="current">热门职位</li>
     	            	<li>最新职位</li>
            </ul>
            <div id="hotList">
	            <ul class="hot_pos reset">
	            		            		            				            		<li class="clearfix">
		            																		            					                	<div class="hot_pos_l">
			                    	<div class="mb10">
			                        	<a href="h/jobs/147822.html" target="_blank">运营总监</a> 
			                            &nbsp;
			                            <span class="c9">[北京]</span>
			                            			                        </div>
			                        <span><em class="c7">月薪： </em>15k-20k</span>
			                        <span><em class="c7">经验：</em> 3-5年</span>
			                        <span><em class="c7">最低学历： </em>本科</span>
			                        <br />
			                        <span><em class="c7">职位诱惑：</em>发展前景</span>
			                        <br />
				                    <span>1天前发布</span>
			                        <!-- <a  class="wb">分享到微博</a> -->
			                    </div>
			                	<div class="hot_pos_r">
			                    	<div class="mb10 recompany"><a href="h/c/399.html" target="_blank">节操精选</a></div>
			                        <span><em class="c7">领域：</em> 移动互联网</span>
			                        			                        <span><em class="c7">创始人：</em>陈桦</span>
			                        			                        <br />
			                        <span><em class="c7">阶段：</em> 初创型(天使轮)</span>
			                        <span><em class="c7">规模：</em>少于15人</span>
			                        <ul class="companyTags reset">
			                        				                        					                        			<li>移动互联网</li>
			                        					                        				                        					                        			<li>五险一金</li>
			                        					                        				                        					                        			<li>扁平管理</li>
			                        					                        				                        </ul>
			                    </div>
			                    			                </li>
	                		                	            				            		<li class="odd clearfix">
		            																		            					                	<div class="hot_pos_l">
			                    	<div class="mb10">
			                        	<a href="h/jobs/147974.html" target="_blank">售前工程师（运维经验优先）</a> 
			                            &nbsp;
			                            <span class="c9">[北京]</span>
			                            			                        </div>
			                        <span><em class="c7">月薪： </em>6k-12k</span>
			                        <span><em class="c7">经验：</em> 3-5年</span>
			                        <span><em class="c7">最低学历： </em>大专</span>
			                        <br />
			                        <span><em class="c7">职位诱惑：</em>五险一金+商业保险+带薪年假+奖金等</span>
			                        <br />
				                    <span>1天前发布</span>
			                        <!-- <a  class="wb">分享到微博</a> -->
			                    </div>
			                	<div class="hot_pos_r">
			                    	<div class="mb10 recompany"><a href="h/c/5232.html" target="_blank">监控宝</a></div>
			                        <span><em class="c7">领域：</em> 云计算\大数据</span>
			                        			                        <br />
			                        <span><em class="c7">阶段：</em> 成长型(A轮)</span>
			                        <span><em class="c7">规模：</em>50-150人</span>
			                        <ul class="companyTags reset">
			                        				                        					                        			<li>五险一金</li>
			                        					                        				                        					                        			<li>福利好</li>
			                        					                        				                        					                        			<li>商业险</li>
			                        					                        				                        </ul>
			                    </div>
			                    			                </li>
	                		                	            				            		<li class="clearfix">
		            																		            					                	<div class="hot_pos_l">
			                    	<div class="mb10">
			                        	<a href="h/jobs/148680.html" target="_blank">市场推广</a> 
			                            &nbsp;
			                            <span class="c9">[上海]</span>
			                            			                        </div>
			                        <span><em class="c7">月薪： </em>7k-12k</span>
			                        <span><em class="c7">经验：</em> 1-3年</span>
			                        <span><em class="c7">最低学历： </em>大专</span>
			                        <br />
			                        <span><em class="c7">职位诱惑：</em>年度16薪 市场营销发展方向</span>
			                        <br />
				                    <span>1天前发布</span>
			                        <!-- <a  class="wb">分享到微博</a> -->
			                    </div>
			                	<div class="hot_pos_r">
			                    	<div class="mb10 recompany"><a href="h/c/1235.html" target="_blank">在路上</a></div>
			                        <span><em class="c7">领域：</em> 移动互联网,在线旅游</span>
			                        			                        <span><em class="c7">创始人：</em>黄天赐</span>
			                        			                        <br />
			                        <span><em class="c7">阶段：</em> 成熟型(C轮)</span>
			                        <span><em class="c7">规模：</em>50-150人</span>
			                        <ul class="companyTags reset">
			                        				                        					                        			<li>弹性工作</li>
			                        					                        				                        					                        			<li>领导好</li>
			                        					                        				                        					                        			<li>移动互联网</li>
			                        					                        				                        </ul>
			                    </div>
			                    			                </li>
	                		                	                
	                	                <a href="list.html" class="btn fr" target="_blank">查看更多</a>
	                	            </ul>
	            <ul class="hot_pos hot_posHotPosition reset" style="display:none;">
	            		            		            				            		<li class="clearfix">
		            				            					                	<div class="hot_pos_l">
			                    	<div class="mb10">
			                        	<a href="h/jobs/149389.html" target="_blank">高级PHP研发工程师</a> 
			                            &nbsp;
			                            <span class="c9">[南京]</span>
			                            			                        </div>
			                        <span><em class="c7">月薪： </em>12k-24k</span>
			                        <span><em class="c7">经验：</em>3-5年</span>
			                        <span><em class="c7">最低学历：</em> 本科</span>
			                        <br />
			                        <span><em class="c7">职位诱惑：</em>IPO了的互联网创业公司，潜力无限！</span>
			                        <br />
				                    <span>15:11发布</span>
			                        <!-- <a  class="wb">分享到微博</a> -->
			                    </div>
			                	<div class="hot_pos_r">
			                    	<div class="mb10"><a href="h/c/8250.html" target="_blank">途牛旅游网</a></div>
			                        <span><em class="c7">领域：</em> 电子商务,在线旅游</span>
			                        			                        <span><em class="c7">创始人：</em>于敦德</span>
			                        			                        <br />
			                        <span> <em class="c7">阶段： </em>上市公司</span>
			                        <span> <em class="c7">规模：</em>500-2000人</span>
			                        <ul class="companyTags reset">
			                        				                        					                        			<li>绩效奖金</li>
			                        					                        				                        					                        			<li>股票期权</li>
			                        					                        				                        					                        			<li>五险一金</li>
			                        					                        				                        </ul>
			                    </div>
			                </li>
	                	                	            				            		<li class="odd clearfix">
		            				            					                	<div class="hot_pos_l">
			                    	<div class="mb10">
			                        	<a href="h/jobs/149388.html" target="_blank">高级搜索研发工程师</a> 
			                            &nbsp;
			                            <span class="c9">[南京]</span>
			                            			                        </div>
			                        <span><em class="c7">月薪： </em>15k-30k</span>
			                        <span><em class="c7">经验：</em>3-5年</span>
			                        <span><em class="c7">最低学历：</em> 本科</span>
			                        <br />
			                        <span><em class="c7">职位诱惑：</em>IPO了的互联网创业公司，潜力无限！</span>
			                        <br />
				                    <span>15:09发布</span>
			                        <!-- <a  class="wb">分享到微博</a> -->
			                    </div>
			                	<div class="hot_pos_r">
			                    	<div class="mb10"><a href="h/c/8250.html" target="_blank">途牛旅游网</a></div>
			                        <span><em class="c7">领域：</em> 电子商务,在线旅游</span>
			                        			                        <span><em class="c7">创始人：</em>于敦德</span>
			                        			                        <br />
			                        <span> <em class="c7">阶段： </em>上市公司</span>
			                        <span> <em class="c7">规模：</em>500-2000人</span>
			                        <ul class="companyTags reset">
			                        				                        					                        			<li>绩效奖金</li>
			                        					                        				                        					                        			<li>股票期权</li>
			                        					                        				                        					                        			<li>五险一金</li>
			                        					                        				                        </ul>
			                    </div>
			                </li>
	                	                	            				            		<li class="clearfix">
		            				            					                	<div class="hot_pos_l">
			                    	<div class="mb10">
			                        	<a href="h/jobs/149385.html" target="_blank">高级数据工程师（爬虫、采集、分析）</a> 
			                            &nbsp;
			                            <span class="c9">[南京]</span>
			                            			                        </div>
			                        <span><em class="c7">月薪： </em>15k-30k</span>
			                        <span><em class="c7">经验：</em>3-5年</span>
			                        <span><em class="c7">最低学历：</em> 本科</span>
			                        <br />
			                        <span><em class="c7">职位诱惑：</em>IPO了的互联网创业公司，潜力无限！</span>
			                        <br />
				                    <span>15:08发布</span>
			                        <!-- <a  class="wb">分享到微博</a> -->
			                    </div>
			                	<div class="hot_pos_r">
			                    	<div class="mb10"><a href="h/c/8250.html" target="_blank">途牛旅游网</a></div>
			                        <span><em class="c7">领域：</em> 电子商务,在线旅游</span>
			                        			                        <span><em class="c7">创始人：</em>于敦德</span>
			                        			                        <br />
			                        <span> <em class="c7">阶段： </em>上市公司</span>
			                        <span> <em class="c7">规模：</em>500-2000人</span>
			                        <ul class="companyTags reset">
			                        				                        					                        			<li>绩效奖金</li>
			                        					                        				                        					                        			<li>股票期权</li>
			                        					                        				                        					                        			<li>五险一金</li>
			                        					                        				                        </ul>
			                    </div>
			                </li>
	                	                	            				            		<li class="odd clearfix">
		            				            					                	<div class="hot_pos_l">
			                    	<div class="mb10">
			                        	<a href="h/jobs/149380.html" target="_blank">高级JAVA研发工程师/架构师</a> 
			                            &nbsp;
			                            <span class="c9">[南京]</span>
			                            			                        </div>
			                        <span><em class="c7">月薪： </em>15k-30k</span>
			                        <span><em class="c7">经验：</em>3-5年</span>
			                        <span><em class="c7">最低学历：</em> 本科</span>
			                        <br />
			                        <span><em class="c7">职位诱惑：</em>IPO了的互联网创业公司，潜力无限！</span>
			                        <br />
				                    <span>15:06发布</span>
			                        <!-- <a  class="wb">分享到微博</a> -->
			                    </div>
			                	<div class="hot_pos_r">
			                    	<div class="mb10"><a href="h/c/8250.html" target="_blank">途牛旅游网</a></div>
			                        <span><em class="c7">领域：</em> 电子商务,在线旅游</span>
			                        			                        <span><em class="c7">创始人：</em>于敦德</span>
			                        			                        <br />
			                        <span> <em class="c7">阶段： </em>上市公司</span>
			                        <span> <em class="c7">规模：</em>500-2000人</span>
			                        <ul class="companyTags reset">
			                        				                        					                        			<li>绩效奖金</li>
			                        					                        				                        					                        			<li>股票期权</li>
			                        					                        				                        					                        			<li>五险一金</li>
			                        					                        				                        </ul>
			                    </div>
			                </li>
	                	                	                <a href="list.html?city=%E5%85%A8%E5%9B%BD" class="btn fr" target="_blank">查看更多</a>
	            </ul>
            </div>
            
            <div class="clear"></div>
			<div id="linkbox">
			    <dl>
			        <dt>友情链接</dt>
			        <dd id="association_">
			        		<a href="http://www.zhuqu.com/" target="_blank">住趣家居网</a> <span>|</span>
			        		<a href="http://www.woshipm.com/" target="_blank">人人都是产品经理</a> <span>|</span>
			        		<a href="http://zaodula.com/" target="_blank">互联网er的早读课</a> <span>|</span>
			          		<a href="http://www.sykong.com/" target="_blank">手游那点事</a> 
			          		
			          		<a href="http://www.zhubajie.com/" target="_blank" >创意服务外包</a><span>|</span>
			          		<a href="http://www.thinkphp.cn/" target="_blank" >thinkphp</a><span>|</span>
			          		<a href="http://www.chuangxinpai.com/" target="_blank" >创新派</a><span>|</span>

			          		<a href="http://w3cshare.com/" target="_blank" >W3Cshare</a><span>|</span>
			          		<a href="http://www.518lunwen.cn/" target="_blank" >论文发表网</a><span>|</span>
			          		<a href="http://www.199it.com" target="_blank" >199it</a><span>|</span>

			          		<a href="http://www.shichangbu.com" target="_blank" >市场部网</a><span>|</span>
			          		<a href="h/af/flink.html" target="_blank" class="more">更多</a>
			        </dd>
			    </dl>
			</div>
        </div>	
 	    <input type="hidden" value="" name="userid" id="userid" />
 		<!-- <div id="qrSide"><a></a></div> -->
<!--  -->

<!-- <div id="loginToolBar">
	<div>
		<em></em>
		<img src="style/images/footbar_logo.png" width="138" height="45" />
		<span class="companycount"></span>
		<span class="positioncount"></span>
		<a href="#loginPop" class="bar_login inline" title="登录"><i></i></a>
		<div class="right">
			<a href="register.html?from=index_footerbar" onclick="_hmt.push(['_trackEvent', 'button', 'click', 'register'])" class="bar_register" id="bar_register" target="_blank"><i></i></a>
		</div>
		<input type="hidden" id="cc" value="16002" />
		<input type="hidden" id="cp" value="96531" />
	</div>
</div>
 -->
<!-------------------------------------弹窗lightbox  ----------------------------------------->
<div style="display:none;">
	<!-- 登录框 -->
	<div id="loginPop" class="popup" style="height:240px;">
       	<form id="loginForm">
			<input type="text" id="email" name="email" tabindex="1" placeholder="请输入登录邮箱地址" />
			<input type="password" id="password" name="password" tabindex="2" placeholder="请输入密码" />
			<span class="error" style="display:none;" id="beError"></span>
		    <label class="fl" for="remember"><input type="checkbox" id="remember" value="" checked="checked" name="autoLogin" /> 记住我</label>
		    <a href="h/reset.html" class="fr">忘记密码？</a>
		    <input type="submit" id="submitLogin" value="登 &nbsp; &nbsp; 录" />
		</form>
		<div class="login_right">
			<div>还没有营才帐号？</div>
			<a href="register.html" class="registor_now">立即注册</a>
		    <div class="login_others">使用以下帐号直接登录:</div>
		    <a href="h/ologin/auth/sina.html" target="_blank" id="icon_wb" class="icon_wb" title="使用新浪微博帐号登录"></a>
		    <a href="h/ologin/auth/qq.html" class="icon_qq" id="icon_qq" target="_blank" title="使用腾讯QQ帐号登录" ></a>
		</div>
    </div><!--/#loginPop-->
</div>
<!------------------------------------- end ----------------------------------------->
<script type="text/javascript" src="style/js/Chart.min.js"></script>
<script type="text/javascript" src="style/js/home.min.js"></script>
<script type="text/javascript" src="style/js/count.js"></script>
			<div class="clear"></div>
			<input type="hidden" id="resubmitToken" value="" />
	    	<a id="backtop" title="回到顶部" rel="nofollow"></a>
	    </div><!-- end #container -->
	</div><!-- end #body -->
	<div id="footer">
		<div class="wrapper">
			<a href="h/about.html" target="_blank" rel="nofollow">联系我们</a>
		    <a href="h/af/zhaopin.html" target="_blank">营地人才公司导航</a>
		    <a href="http://e.weibo.com/lagou720" target="_blank" rel="nofollow">营才微博</a>
		    <a class="footer_qr" href="javascript:void(0)" rel="nofollow">营才微信<i></i></a>
			<div class="copyright">&copy;2013-2014 Lagou <a target="_blank" href="http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action">京ICP备14023790号-2</a></div>
		</div>
	</div>

<script type="text/javascript" src="style/js/core.min.js"></script>
<script type="text/javascript" src="style/js/popup.min.js"></script>

<!-- <script src="style/js/wb.js" type="text/javascript" charset="utf-8"></script>
 -->
<script type="text/javascript">  
$(document).ready(function($){
	/**轮播图 */
	$.ajax({
		   type:"post",
 		   url:"getIndexShuffingList",
 		   dataType:"json", 
		   success:function(data){ 
			    //console.log(data)
			    if(data.records>0){
			    	$("#shuffing_list_").empty();
			    	$.each( data.rows, function(i, n){
				    	var value = n;
				    	console.log(value.href)
				    	//var promotionNameT = value.promotionName.length>18?value.promotionName.substring(0,18)+"...":value.promotionName;
				    	var elements = '<li onclick="window.location.href=\''+value.href+'\'"  class="swiper-slide" style="background: url(http://www.zhihuiyingdi.com/weixin'+value.image+') no-repeat center; background-size: cover;"></li>';
				    	$("#shuffing_list_").append(elements);
		    		});
			    }
			    //$('.focus').width(window.screen.width);
			    var widthT = $("#index_img").width();
			    var heightT = widthT*40/75;
			    
			    //$('.index-banner').height(heightT);
			    $('#shuffing_list_ img').height(heightT);
			    //$('.focus ul').height(heightT);
				//$('.focus ul li').width(window.screen.width);
				//$('.focus ul li').height(heightT);
				//console.log($('.focus ul li').height())
			    //$.focus("#index_img");
			    var mySwipertop = new Swiper ('#index_img', {
					direction: 'horizontal',  
			        loop: true,  
			        autoplay: 2000,
			        startSlide:-100,
			        pagination: '#index_img_page',
			        paginationClickable: true
				});
	       }  
	});
	/**合作院校 */
	$.ajax({
		   type:"post",
 		   url:"getCooperationCollegesList",
 		   dataType:"json", 
		   success:function(data){ 
			    //console.log(data)
			    if(data.records>0){
			    	$("#cooperation_colleges_").empty();
			    	$.each( data.rows, function(i, n){
				    	var value = n;
				    	console.log(value)
				    	var elements = '<li ><a href="<%=requestPath%>top/cooperationcolleges/getdetail?id='+value.id+'" target="_blank"><img src="<%=requestPath%>'+value.logo+'" width="113" height="113" alt="'+value.name+'" /> <div class="hot_info"><p title="'+value.name+'">'+value.name+'</p></div></a></li>';
				    	$("#cooperation_colleges_").append(elements);
		    		});
			    }
	       }  
	});
	/**协会链接 */
	$.ajax({
		   type:"post",
 		   url:"getAssociationList",
 		   dataType:"json", 
		   success:function(data){ 
			    //console.log(data)
			    if(data.records>0){
			    	$("#association_").empty();
			    	$.each( data.rows, function(i, n){
				    	var value = n;
				    	console.log(value.href)
				    	var elements = '<a href="http://'+value.href+'" target="_blank">'+value.name+'</a> <span>|</span>';
				    	$("#association_").append(elements);
		    		});
			    }
	       }  
	});
	
	/**加载职位类别 */
	$.ajax({
		   type:"post",
 		   url:"<%=requestPath %>top/careertype/getcareertype",
 		   dataType:"json", 
		   success:function(data){ 
			    console.log("careertypes========="+data);
			    if(data.careertypes!=null && data.careertypes.length>0){
			    	$("#careertypetree").empty();
			    	$.each( data.careertypes, function(i, n){
				    	var value = n;
				    	var subtypes = value.childcareers;
				    	var elements = "";
				    	
				    	elements += '<li>';
				    	elements += '<div  style="cursor: auto;"> <a cgid="1" href="top/courseindex/searchCourse?family=3">'+value.name+'</a> ';
				    	elements += '			              <div class="lesson-list-show" style="min-height: 410px; width: 401px;">';
				    	elements += '<div style="width: 400px;">';
				    	elements += '<dl> ';
				    	elements += '<dt><a href="top/courseindex/searchCourse?family=3 " cgid="3">'+value.name+' </a></dt>';
				    	elements += '<dd class="cf">';
				    	if(subtypes!=null && subtypes.length>0){
				    		$.each(subtypes, function(j, vals){
						    	elements += '<a href="top/courseindex/searchCourse?family=31" cgid="31">'+vals.name+'</a>';
				    		});
				    	}
			            elements += '</dd>';
			            elements += '</dl>';
			            elements += '</div>';
			            elements += '</div>';
			            elements += '</div> ';
			            elements += '</li> ';
				    	
				    	$("#careertypetree").append(elements);
		    		});
			    }
	       }  
	});
	
});
</script>
</body>
</html>	