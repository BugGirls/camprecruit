// JavaScript Document
$(document).ready(function() {
    var n = location.href;
	if(n.indexOf("jsp/index") >=0||n.indexOf("jsp/login")>0||n.indexOf("jsp/logout")>0){
		$(".link01 a").addClass("gre");
	}
	if(n.indexOf("courseindex") >=0||n.indexOf("top/course") >=0){
		$(".link02 .main_menu_title").addClass("gre");
	}
	if(n.indexOf("top/safetynews/") >=0){
		$(".link03 .main_menu_title").addClass("gre");
	}
	if(n.indexOf("tixikuxq") >=0){
		$(".link04 .main_menu_title").addClass("gre");
	}
	if(n.indexOf("successfulCase") >=0){
		$(".link05 a").addClass("gre");
	}
	if(n.indexOf("message") >=0){
		$(".link06 a").addClass("gre");
	}
	
	$(".main_menu").hover(
		function () {
		    $(this).children(".sub_menu").show();
		},
		function () {
			$(this).children(".sub_menu").hide();
		}
	);
	$(".weixin").hover(
		function () {
		    $(".ewm").show();
		},
		function () {
			$(".ewm").hide();
		}
	);
	$(".sina").hover(
		function () {
		    $(".ewm1").show();
		},
		function () {
			$(".ewm1").hide();
		}
	);
	
});
