<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
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
</style>
<div class="row">
	<div class="col-xs-12 title" >
		<div class="col-xs-2" id="bname"></div>
	</div>
	<div class="col-xs-12 " >
		<span class="m_title" >账户金额</span>
		<button class="btn btn-success"><span id="bprofit">0.00</span>&nbsp;元</button>
		<a class="btn " onclick="$('#hidediv').show();">提现</a>
		<a class="btn " id="detail_show">明细</a>
		<span class="m_title" >已结算</span>
		<button class="btn btn-success"><i class=" align-top bigger-100">收入：</i><span id="eincome">0.00</span>&nbsp;元</button>
		<button class="btn btn-danger"><i class=" align-top bigger-100">支出：</i><span id="eexpense">0.00</span>&nbsp;元</button>
		<span class="m_title" >待结算</span>
		<button class="btn btn-warning"><i class=" align-top bigger-100">收入：</i><span id="fincome">0.00</span>&nbsp;元</button>
		<button class="btn btn-pink"><i class=" align-top bigger-100">支出：</i><span id="fexpense">0.00</span>&nbsp;元</button>
	</div>
	<div class="col-xs-12">
		<table id="grid-table"></table>
		<div id="grid-pager"></div>

		<script type="text/javascript">
			var index = 0;
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
			function insertAfter(newEl, targetEl)
			{
			      var parentEl = targetEl.parentNode;
			      if(parentEl.lastChild == targetEl)
			      {
			           parentEl.appendChild(newEl);
			      }else
			      {
			           parentEl.insertBefore(newEl,targetEl.nextSibling);
			      }  
			}
			
		</script>
		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
 
	</div><!-- /.col -->
</div><!-- /.row -->


<div id="hidediv" style="z-index:99;width:600px;height:600px;padding:20px 20px; position:absolute;background-color:#fff; border:1px solid #dcd; display: none; top: 10px ;left:15% ; ">
	<p style="text-align: center; font-size: 20px;">提现申请 <button class="btn" style="float: right;" id="divclose"><i class=" align-top bigger-100">关闭</i></button></p>
	<ul class="nav nav-tabs">
	  <li role="presentation" class="active" id="request_in_"><a href="javascript:void(0);">发起申请</a></li>
	  <li role="presentation" id="request_list_"><a href="javascript:void(0);">申请记录</a></li>
	</ul>
	<div id="new_form" >
	  <div class="col-xs-12">
	  <br><br>
		账户金额：<span id="oprofit">0.00</span>&nbsp;元 <br><br>
		提现金额：<input type="text" id="withdraw" maxlength="26"/> <br><br>
	  </div>
	  
	  <div class="clearfix form-actions">
			<div class="col-md-offset-3 col-md-9">
				<button class="btn btn-info" type="button"  id="ok">
					<i class="icon-ok bigger-110"></i>
					确定
				</button>
	
				&nbsp; &nbsp; &nbsp;
				<button class="btn" type="reset" id="resetbtn">
					<i class="icon-undo bigger-110"></i>
					取消
				</button>
			</div>
		</div>
	  
	</div>
	<div id="tab_list" style="display: none;">
	<!-- Table -->
	  <table class="table ">
        <thead>
          <tr>
            <th>申请时间</th>
            <th>审核状态</th>
            <th>提现金额</th>
            <th>审核时间</th>
            <th>备注</th>
          </tr>
        </thead>
        <tbody id="tab_withdrawal_content_">
        </tbody>
      </table><br><br>
	</div>
</div>
<script type="text/javascript">
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
	$("#divclose").click(function(){
		$("#withdraw").val("");
		$("#hidediv").hide(); 
	});
	
	$("#resetbtn").click(function(){
		$("#withdraw").val("");
	});
	
	$('#request_in_').on('click', function() { 
		$('#request_in_').addClass("active");
		$('#request_list_').removeClass("active");
		$('#new_form').show();
		$('#tab_list').hide();
	});
		
	$('#request_list_').on('click', function() {
		$('#request_in_').removeClass("active");
		$('#request_list_').addClass("active");
		$('#new_form').hide();
		$('#tab_list').show();
		$.ajax({
			type : "post",
			url : " ${contextPath}/sys/allianceincome/getAllianceWithdrawal",
			success : function(data) {
				var list = eval(data);
				var elemnts = "";
				$("#tab_withdrawal_content_").empty();
				if(list.length==0){
					elemnts += "<tr><td>无</td></tr>";
				}else{
	 				$.each(list, function(key, value) {
	 					var status = "审核中";
	 					if(value.status==1){
	 						status = "已通过";
	 					}else if(value.status==2){
	 						status = "已驳回";
	 					}
	 					var passtime = "";
	 					if(value.passtime){
	 						passtime = new Date(value.passtime).Format("yyyy-MM-dd hh:mm:ss");
	 					}
	 					var remark = "";
	 					if(value.remark){
	 						remark = value.remark;
	 					}
	 					console.log(passtime,remark)
	 					elemnts += "<tr><td>" + new Date(value.createtime).Format("yyyy-MM-dd hh:mm:ss")  + "</td><td>"
	 							+ status + "</td><td>"+ value.withdrawal + "</td><td>"+ passtime + "</td><td>"+ remark + "</td></tr>";
	 				});
				}
				$("#tab_withdrawal_content_").append(elemnts);
			}
		});
		
	});
	
	$("#ok").click(function(){
		var withdraw = $("#withdraw").val();
		var profit = $("#oprofit").html();
		if($("#withdraw").val()==""){
			alert("提现金额不能为空");
			return false;
		}else if(profit-withdraw<0){
			alert("账户余额不足");
			return false;
		}else{
			$.ajax({
     		   type:"post",
      		   url:" ${contextPath}/sys/allianceincome/withdraw",
      		   dataType:"json", 
   		  	   data:{"withdrawal":withdraw},
   		  	   success:function(data){
   		  		   if(data.success){
    		  			alert(data.msg); 
					//$("#hidediv").hide(); 
					$("#request_list_").trigger("click");
					$("#withdraw").val("");
   		  		   }else{
   		  				alert(data.msg); 
   		  		   } 
   		  	   }
			});
		}   
	});
</script>

<div id="hide_detail_div" style="z-index:99;padding:20px 20px; position:absolute;background-color:#fff; border:1px solid #dcd; display: none; top: 100px ;left:30% ; ">
	<p style="text-align: center; font-size: 20px;">账户明细 </p>
	<!-- Table -->
	  <table class="table ">
        <thead>
          <tr>
            <th>时间</th>
            <th>收入金额</th>
            <th>支出金额</th>
            <th>交易前账户金额</th>
            <th>交易后账户金额</th>
          </tr>
        </thead>
        <tbody id="tab_content_">
        </tbody>
      </table><br><br>
	<p style="text-align: center;"><button  style="border:1px solid #aaa;margin-right:30px;background-color:#eee; "id="detaildivclose">关闭</button></p>
</div>
<script type="text/javascript">
	$("#detaildivclose").click(function(){
		$("#withdraw").val("");
		$("#hide_detail_div").hide(); 
	});
	$("#detail_show").click(function(){
		$("#hide_detail_div").show(); 
		$.ajax({
			type : "post",
			url : " ${contextPath}/sys/allianceincome/getAllianceIncomeSettle",
			success : function(data) {
				var list = eval(data);
				var elemnts = "";
				$("#tab_content_").empty();
				if(list.length==0){
					elemnts += "<tr><td>无</td></tr>";
				}else{
 				$.each(list, function(key, value) {
 					elemnts += "<tr><td>" + value.createtime + "</td><td>"
 							+ value.income + "</td><td>"+ value.expense + "</td><td>"+ value.oldprofit + "</td><td>"+ value.newprofit + "</td></tr>";
 				});
				}
				$("#tab_content_").append(elemnts);
			}
		});
		
	});
</script>

<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
		                "${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js",
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                 null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		
        		function getAllianceIncomeCount(){
	        		$.ajax({
	        			type : "post",
	        			url : " ${contextPath}/sys/allianceincome/getAllianceIncomeCount",
	        			dataType:"json",
	        			success : function(data) {
	       					$("#eexpense").html(data.eexpense);
	       					$("#eincome").html(data.eincome);
	       					$("#fexpense").html(data.fexpense);
	       					$("#fincome").html(data.fincome);
	       					$("#bname").html(data.bname);
	       					$("#bprofit").html(data.bprofit);
	       					$("#oprofit").html(data.bprofit);
	        			}
	        		});
        		}
        		getAllianceIncomeCount();
        		
        		var grid_selector = "#grid-table";
        		var pager_selector = "#grid-pager";

        		// resize to fit page size
        		$(window).on('resize.jqGrid', function() {
        			$(grid_selector).jqGrid('setGridWidth', $(".page-content").width());
        		})
        		// resize on sidebar collapse/expand
        		var parent_column = $(grid_selector).closest('[class*="col-"]');
        		$(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed) {
        			if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
        				// setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        				setTimeout(function() {
        					$(grid_selector).jqGrid('setGridWidth', parent_column.width());
        				}, 0);
        			}
        		})

        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/allianceincome/getAllianceIncomeList",
        			datatype : "json",
        			height : 650,
        			colNames : [ 'ID', '加盟商名称', '订单号','活动名称','备注','收入金额','支出金额', '是否结算','是否结算', '创建时间','结算时间'],
        			colModel : [ {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sorttype : "long",
        				hidden : true,
        				search : false
        			}, {
        				name : 'allianceBusiness.name',
        				index : 'allianceid',
        				label : '加盟商名称',
        				width : 180,
        				editable : false,
        				search : true,
        				stype:'select',
        				searchoptions:{
        					dataUrl : "${contextPath}/sys/alliancebusiness/getAllianceSelectList"
        				}
        			}, {
        				name : 'orderid',
        				index : 'orderid',
        				label : '订单号',
        				width : 180,
        				editable : false,
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'promotionName',
        				index : 'orderid',
        				label : '活动名称',
        				width : 180,
        				editable : false,
        				searchoptions : false
        			}, {
        				name : 'remark',
        				index : 'remark',
        				label : '备注',
        				width : 180,
        				editable : false,
        				search : false
        			}, {
        				name : 'income',
        				index : 'income',
        				label : '收入金额',
        				width : 100,
        				editable : false,
        				search : false
        			}, {
        				name : 'expense',
        				index : 'expense',
        				label : '支出金额',
        				width : 100,
        				editable : false,
        				search : false
        			},{
        				name : 'is_closed',
        				index : 'is_closed',
        				label : '是否结算',
        				width : 100,
        				formatter:booleanFormatter,
        				editable : false,
        				search : true,
        				stype:'select',
        				searchoptions:{
        					sopt:["eq"],
        					value : "0:否;1:是"
        				} 
        			},{
        				name : 'is_closed',
        				index : 'is_closed',
        				label : '是否结算',
        				width : 0,
        				hidden : true,
        				search : false 
        			}, {
        				name : 'createtime',
        				index : 'createtime',
        				label : '创建时间',
        				width : 150 ,
        				editable : false,
        				search : false 			
        			}, {
        				name : 'closetime',
        				index : 'closetime',
        				label : '结算时间',
        				width : 150  ,
        				editable : false,
        				search : false,
        				formatter:dateFormatter
        			}
					],
        			//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
        			sortname : "id",
        			sortorder : "desc",
        			viewrecords : true,
        			rowNum : 10,
        			rowList : [ 10, 20, 30 ],
        			pager : pager_selector,
        			altRows : true,
        			shrinkToFit:false,  
        			autoScroll: true, 
        			//toppager : true,
        			multiselect : false,
        			//multikey : "ctrlKey",
        	        multiboxonly : false,
        			loadComplete : function() {
        				var table = this;
        				setTimeout(function(){
        					styleCheckbox(table);
        					updateActionIcons(table);
        					updatePagerIcons(table);
        					enableTooltips(table);
        				}, 0);
        			},
        			//editurl : "${contextPath}/sys/allianceincome/operateAllianceIncome"
        			//caption : "用户管理列表",
        			//autowidth : true,
        			/**
        			grouping : true, 
        			groupingView : { 
        				 groupField : ['name'],
        				 groupDataSorted : true,
        				 plusicon : 'fa fa-chevron-down bigger-110',
        				 minusicon : 'fa fa-chevron-up bigger-110'
        			},
        			*/
        		});
        		
        //=====================================================
        	$('[data-rel=tooltip]').tooltip();
			$('[data-rel=popover]').popover({html:true});
				function booleanFormatter(cellvalue, options, rowdata) {  
					if(cellvalue==1)
						return "是";
					else
					return "否";
				}
				
				function dateFormatter(cellvalue, options, rowdata) {  
					if(cellvalue)
						return new Date(cellvalue).Format("yyyy-MM-dd hh:mm:ss");
					else
					return "";
				}
        		
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
        		
        		// navButtons
        		jQuery(grid_selector).jqGrid('navGrid', pager_selector, { // navbar options
        			edit : false,
					editicon : '',
					add : false,
					addicon : '', 
					del : false,
					delicon : 'ace-icon fa fa-trash-o red',
					search : true,
					searchicon : 'ace-icon fa fa-search orange',
					refresh : true,
					refreshicon : 'ace-icon fa fa-refresh blue',
					view : true,
					viewicon : 'ace-icon fa fa-search-plus grey'
        		}, {
        			// edit record form
        			// closeAfterEdit: true,
        			// width: 700,
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        				style_edit_form(form);
        			},
    				errorTextFormat: function (response) {
    					var result = eval('('+response.responseText+')');
    				    return result.message;
    				}
        		}, {
        			// new record form
        			// width: 700,
        			closeAfterAdd : true,
        			recreateForm : true,
        			viewPagerButtons : false,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        				style_edit_form(form);
        			},
    				errorTextFormat: function (response) {
    					var result = eval('('+response.responseText+')');
    				    return result.message;
    				}
        		}, {
        			// delete record form
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				if (form.data('styled'))
        					return false;
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
        				style_delete_form(form);
        				form.data('styled', true);
        			},
        			onClick : function(e) {
        				// alert(1);
        			}
        		}, {
        			// search form
        			recreateForm : true,
        			afterShowSearch : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
        				style_search_form(form);
        			},
        			afterRedraw : function() {
        				style_search_filters($(this));
        			},
        			multipleSearch : true 
	        		/**
	        		 * multipleGroup:true, showQuery: true
	        		 */
        		}, {
        			// view record form
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
        			}
        		})
        		
        		// add custom button to export the data to excel
				jQuery(grid_selector).jqGrid('navButtonAdd', pager_selector,{
				   caption : "",
			       title : "导出Excel",
			       buttonicon : "ace-icon fa fa-file-excel-o green", 
			       onClickButton : function () { 
			    	   var keys = [], ii = 0, rows = "";
			    	   var ids = $(grid_selector).getDataIDs(); // Get All IDs
			    	   var label = $(grid_selector).jqGrid('getGridParam','colNames');
   			    	   for(var i = 1 ; i < label.length; i++){
			    	   	   rows = rows + label[i] + "\t"; // output each Column as tab delimited
			    	   }
			    	   var row = $(grid_selector).getRowData(ids[0]); // Get First row to get the labels
			    	   
   			    	   for (var k in row) {
			    	   	   keys[ii++] = k; // capture col names
			    	   }
			    	   
			    	   rows = rows + "\n"; // Output header with end of line
			    	   for (i = 0; i < ids.length; i++) {
			    	   	   row = $(grid_selector).getRowData(ids[i]); // get each row
			    	   	   for (j = 0; j < keys.length; j++)
			    	   		   rows = rows + row[keys[j]] + "\t"; // output each Row as tab delimited
			    	   	   rows = rows + "\n"; // output each row with end of line
			    	   }
			    	   rows = rows + "\n"; // end of line at the end
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/sysuser/operateSysUser?oper=excel' method='post'>";
			    	   form = form + "<input type='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open('', '');
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		
        		function style_edit_form(form) {
        			// enable datepicker on "birthday" field and switches for "stock" field
        			form.find('input[name=birthday]').datepicker({
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
        			$(grid_selector).jqGrid('GridUnload');
        			$('.ui-jqdialog').remove();
        		});
        		
        	});
        });
</script>
