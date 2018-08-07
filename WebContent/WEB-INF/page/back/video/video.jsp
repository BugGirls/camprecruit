<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>  

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
<div >

    <div >
        <!-- /.row -->
        <div >
            <div  > 
               <input id="a" value="12"/>
                    </div>
                </div>
            </div>
       </div>
<!-- page specific plugin scripts -->
<script type="text/javascript"> 
	var scripts = [ null, "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js", "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", null ];

	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	//	var jsonobj=$.ajax({url:"${contextPath}/sys/video/getTest",async:false});
		$.ajax({
   			 type:"post",
   			 url:"${contextPath}/sys/video/getTest",
//           data:{"text":textNode,"pid":node.data.id},
   			 dataType:"json", 
    		 success:function(data){  
                 var me=data[0].name;  
                 alert(me);
                 for(i in data){  
                    var me1=data[i];   
                    $("#a").val(me1.name);
                 }   
   			}
		}); 
		 
		//alert(jsonobj);
		//$("#a").val(jsonobj.book);
	});
</script>
 
 
 