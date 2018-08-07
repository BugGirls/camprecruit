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
</style>

<div class="row">
	<div class="col-xs-12">
	<ul class="nav nav-tabs">
	  <li role="presentation" class="active" id="refund_in_"><a href="javascript:void(0);">退款申请中</a></li>
	  <li role="presentation" id="refund_out_"><a href="javascript:void(0);">退款成功</a></li>
	</ul>
		<table id="grid-table"></table>
		<div id="grid-pager"></div>

		<script reservationdate="text/javascript">
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
		<script reservationdate="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
 
 <script reservationdate="text/javascript"> 
    $("#aid").draggable();
	$("#hid").draggable();

    </script>
<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
			<!-- 门店选择对话框 -->
<div id="modal-table" class="modal fade" tabindex="-1" data-backdrop="static">
	<div class="modal-dialog">
		<form id="informationForm">
			<div class="modal-content">
				<div class="modal-header no-padding">
					<div class="table-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							<span class="white">&times;</span>
						</button>
						订单支付详情
					</div>
				</div>
				<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
					<div id="modal-tip" class="red clearfix"></div>
					<input id="orderId" type="hidden" />
					<div class="widget-box widget-color-blue2">
						<div class="widget-body" id="refund_op_content_">
							<div class="widget-main padding-8" id="store_checkbox">
							<input type="hidden"  id="id" />
							
							订单编号：	&nbsp;<span id="orderid"> </span><br>
							订单金额：	&nbsp;<span id="totalfee" ></span><br>
							支付方式：	&nbsp;<span id="paytype" ></span><br>
							支付时间：	&nbsp;<span id="createtime" ></span><br>
							
							</div>
						</div>
						<div class="widget-body" id="refund_show_content_">
							<div class="widget-main padding-8" id="store_checkbox">
							<input type="hidden"  id="id" />
							
							订单编号：	&nbsp;<span id="orderid_"> </span><br>
							订单金额：	&nbsp;<span id="totalfee_" ></span><br>
							支付方式：	&nbsp;<span id="paytype_" ></span><br>
							退款时间：	&nbsp;<span id="createtime_" ></span><br>
							
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer no-margin-top">
					<div class="text-center" id="refund_op_btn_">
						<button id="submitButton" type="submit" class="btn btn-app btn-success btn-xs">
							<i class="ace-icon fa fa-floppy-o bigger-160"></i>
							退款
						</button>
						<button class="btn btn-app btn-pink btn-xs" data-dismiss="modal">
							<i class="ace-icon fa fa-share bigger-160"></i>
							取消
						</button>
					</div>
					<div class="text-center" id="refund_show_btn_">
						<button id="shutButton" class="btn btn-app btn-pink btn-xs" data-dismiss="modal">
							<i class="ace-icon fa fa-floppy-o bigger-160"></i>
							关闭
						</button>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</form>
	</div><!-- /.modal-dialog -->
</div>
	
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script reservationdate="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js",
		                "${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js",
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                 null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		
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

        		var grid = jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/newTzzReservation/getReservationListForAlliance?reservationstate=4",
        			datatype : "json",
        			mtype:"POST",
        			height : 650,
        			colNames : ['ID', '订单号', '联系人', '手机号', '活动名称','预约日期','预约店面','订单状态','openid','创建时间','操作'],
        			colModel : [ {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sortreservationdate : "long",
        				search : false
        			}, {
        				name : 'orderid',
        				index : 'orderid',
        				label : '订单号',
        				width : 180,
        				editable : false,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'name',
        				index : 'name',
        				label : '姓名',
        				width : 100,
        				editable : false,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'phone',
        				index : 'phone',
        				label : '手机号',
        				width : 100,
        				editable : false,
        				editoptions : {size : "20", maxlength : "255"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'promotion.name',
        				index : 'promotion.name',
        				label : '活动名称',
        				width : 380,
        				editable : false,
        				editoptions : {size : "20", maxlength : "255"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}
        			}, {
        				name : 'reservationdate',
        				index : 'reservationdate',
        				label : '预约日期',
        				width : 100,
        				editable : false,
        				editreservationdate : "select",
        					search : false 
        			}, {
        				name : 'storename',
        				index : 'storename',
        				label : '预约店面',
        				width : 160,
        				hidden : true,
        				editreservationdate : "select",
        					search : false 
        			}, {
        				name : 'reservationstate',
        				index : 'reservationstate',
        				label : '订单状态',
        				width : 90,
        				editable : false,
        				editoptions : {value : "1:未付款;2:已付款;3:已核销;4:退款申请中;5:退款成功"},
        				searchoptions : {sopt:["eq"],value : "1:未付款;2:已付款;3:已核销;4:退款申请中;5:退款成功"},
        				editrules : {required : true},
        				formatter : stateChange
        			}, {
        				name : 'openid',
        				index : 'openid',
        				label : 'openid',
        				width : 200,
        				hidden:true,
        				editable : false,
        				editoptions : {size : "20", maxlength : "64"},
        				searchoptions : {sopt : ['cn']},
        				editrules : {required : true}	
        			}, {
        				name : 'creattime',
        				index : 'creattime',
        				label : '创建时间',
        				width : 180 ,
        				editable : false,
        				search : false 			
        			}, {
        				name : '',
        				index : '',
        				label : '操作',
        				width : 120,
        				editable : false,
        				search : false,
        				sortable : false,
        				formatter : operatorFormatter
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
        			multiselect : true,
        			//multikey : "ctrlKey",
        	        multiboxonly : true,
        			loadComplete : function() {
        				var table = this;
        				setTimeout(function(){
        					styleCheckbox(table);
        					updateActionIcons(table);
        					updatePagerIcons(table);
        					enableTooltips(table);
        				}, 0);
        			},
        			editurl : "${contextPath}/sys/newTzzReservation/operateReservation"
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
        		
       		console.log(grid);
       		
       		function operatorFormatter(cellvalue, options, cell) {
       			console.log(cell.reservationstate)
       			var template = "";
       			if(cell.reservationstate==4)
       			template = "<button data-toggle='modal' onclick='javascript:loadordertransaction(\"" + cell.orderid +"\");' class='btn btn-xs btn-warning'>退款</button>";
       			else if(cell.reservationstate==5)
       				template = "<button data-toggle='modal' onclick='javascript:loadorderrefundtransaction(\"" + cell.orderid +"\");' class='btn btn-xs btn-warning'>查看</button>";
       			return template;
       		}
       		
       		$('#refund_in_').on('click', function() { 
       			$('#refund_in_').addClass("active");
       			$('#refund_out_').removeClass("active");
       			jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/newTzzReservation/getReservationListForAlliance?reservationstate=4"}).trigger("reloadGrid");
       			
			});
       		
       		$('#refund_out_').on('click', function() {
       			$('#refund_in_').removeClass("active");
       			$('#refund_out_').addClass("active");
       			jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/newTzzReservation/getReservationListForAlliance?reservationstate=5"}).trigger("reloadGrid");
			});
       		
       		
       		$('#submitButton').on('click', function() {        			
				$.ajax({
					url : "${contextPath}/sys/order/refundorder",
					data : {
						orderid : $("#orderid").html()
					},
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						//$("#modal-table").modal("toggle");
						console.log(data);
						if(data.success){
							alert(data.msg);
							jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/newTzzReservation/getReservationListForAlliance?reservationstate=4"}).trigger("reloadGrid");
						}else{
							//jQuery(grid_selector).jqGrid('setGridParam',{url:"${contextPath}/sys/newTzzReservation/getReservationListForAlliance?reservationstate=4"}).trigger("reloadGrid");
							alert(data.msg);
						}
					}
				});
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
				
				$.ajax({//classifycn
			        reservationdate:"post", 
			        url:"${contextPath}/sys/Tzzdic/getTypeSelectList",
			        data:{"parentId":"139"},
			   		dataType:"html", 
			       	success:function(data){ 
			       		$("#freservationdateId").append(data); 
			       		$("#areservationdateId").append(data);    
			       	} 
			    });
				$.ajax({//classifycn
			        reservationdate:"post", 
			        url:"${contextPath}/sys/Tzzdic/getTypeSelectList",
			        data:{"parentId":"143"},
			   		dataType:"html", 
			       	success:function(data){   
			       		$("#freservationtimeId").append(data); 
			       		$("#areservationtimeId").append(data);    
			       	} 
			    });
//===========================================jieshu ================================================
        		
        		
        		
        		$(window).triggerHandler('resize.jqGrid');// trigger window resize to make the grid get the correct size
        		
        		// enable search/filter toolbar
        		// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
        		// jQuery(grid_selector).filterToolbar({});
        		// switch element when editing inline
        		function aceSwitch(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[reservationdate=checkbox]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			}, 0);
        		}
        		
        		// enable datepicker
        		function pickDate(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find('input[reservationdate=text]').datepicker({
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
					del : true,
					delicon : 'ace-icon fa fa-trash-o red',
					search : true,
					searchicon : 'ace-icon fa fa-search orange',
					refresh : true,
					refreshicon : 'ace-icon fa fa-refresh blue',
					view : false,
					viewicon : 'ace-icon fa fa-search-plus grey'
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
			    	   form = form + "<input reservationdate='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open('', '');
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		
        		function style_edit_form(form) {
        			// enable datepicker on "birthday" field and switches for "stock" field
        			form.find('input[name=reservationdate]').datepicker({
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
        		
        		function stateChange(cellvalue, options, cell) {
        			if(cell.reservationstate==1)cellvalue="未付款";
        			else if(cell.reservationstate==2)cellvalue="已付款";
        			else if(cell.reservationstate==3)cellvalue="已核销";
        			else if(cell.reservationstate==4)cellvalue="退款申请中";
        			else if(cell.reservationstate==5)cellvalue="退款成功";
        			else cellvalue ="其他";
        			return cellvalue;
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
        			 * .find('input.cbox[reservationdate=checkbox]').addClass('ace') .wrap('<label />').after('<span class="lbl align-top" />');
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
        function loadordertransaction(orderid){
   			if(orderid){
   				$.ajax({
   	   		   	   type:"get",
   	   		     	url:" ${contextPath}/sys/order/getTzzOrderDetailInfo?orderid="+orderid,
   	   		  		dataType:"json", 
   	   		  		success:function(dataT){
	   		   			console.log(dataT); 
	   		  			if(dataT.success){
	   		   				var data = dataT.tzzOrderDetail;
	   		   				console.log(data);  
   	   		   				
   	   		   				$("#refund_op_content_").show();
  		   					$("#refund_op_btn_").show();
  		   					
   	   		   				$("#refund_show_content_").hide();
   	   		   				$("#refund_show_btn_").hide();
   	   		   				
   	   		   				$("#orderid").html(data.orderid);
   	   		   				$("#totalfee").html(data.totalFee+"元");
   	   		   				var paytype = "微信支付";
   	   		   				if(data.paytype==1)
   	   		   					paytype = "微信支付";
   	   		   				else if(data.paytype==2)
   	   		   					paytype = "支付宝支付";
   	   		   				else
  		   						paytype = "不详";
   	   		  				$("#paytype").html(paytype);
   	   						$("#createtime").html(data.creattime);
   	   						$("#modal-table").modal("toggle");
	   	   		   		}else{
	   	   		   			alert("该订单未通过线上支付！");
	   	   		   		}
   	   		   	   }
   	   		   	});
   			}
   			else
   				return;
   		}
        Date.prototype.Format = function(fmt)   
        { //author: meizz   
          var o = {   
            "M+" : this.getMonth()+1,                 //月份   
            "d+" : this.getDate(),                    //日   
            "h+" : this.getHours(),                   //小时   
            "m+" : this.getMinutes(),                 //分   
            "s+" : this.getSeconds(),                 //秒   
            "q+" : Math.floor((this.getMonth()+3)/3), //季度   
            "S"  : this.getMilliseconds()             //毫秒   
          };   
          if(/(y+)/.test(fmt))   
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
          for(var k in o)   
            if(new RegExp("("+ k +")").test(fmt))   
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
          return fmt;   
        };
        function loadorderrefundtransaction(orderid){
   			if(orderid){
   				$.ajax({
   	   		   	   type:"get",
   	   		     	url:" ${contextPath}/sys/order/getTzzOrderRefundInfo?orderid="+orderid,
   	   		  		dataType:"json", 
   	   		     	success:function(dataT){
   	   		   			console.log(dataT); 
   	   		  			if(dataT.success){
   	   		   				var data = dataT.tzzOrderRefund;
   	   		   				console.log(data); 
   	   		   				
  		   					$("#refund_op_content_").hide();
		   					$("#refund_op_btn_").hide();
   	   		   				$("#refund_show_content_").show();
  		   					$("#refund_show_btn_").show();
  		   					
   	   		   				
   	   		   				$("#orderid_").html(data.orderid);
   	   		   				$("#totalfee_").html(data.totalFee/100+"元");
		   	   		   		var paytype = "微信支付";
	  		   				if(data.paytype==1)
	  		   					paytype = "微信支付";
	  		   				else if(data.paytype==2)
	  		   					paytype = "支付宝支付";
	  		   				else
	   							paytype = "不详";
   	   		  				$("#paytype_").html(paytype);
   	   						$("#createtime_").html(new Date(data.creattime.time).Format("yyyy-MM-dd hh:mm:ss"));
   	   						$("#modal-table").modal("toggle");
   	   		  			}else{
   	   		  				alert("未查询到改订单的退款详情！");
   	   		  			}
   	   		   	   }
   	   		   	});
   			}
   			else
   				return;
   			
   		}
        
         
</script>
