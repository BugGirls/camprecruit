<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />

<div class="row">
<!-- <button id= \"" + value + "\" style=\"background-color:#eee;border:1px solid #777; color:#999\" onclick='newmima(" + value+","+rows.orderid+","+rows.phone+")'>核销</button>";} , -->
	<div class="col-xs-12">
	<div id="hidediv" style="z-index:99;width:300px;height:200px;padding:20px 20px; position:absolute;background-color:#fff; border:1px solid #dcd;  top: 100px ;left:30% ; ">
		<p style="text-align: center; font-size: 20px;">门店二维码制作 </p>
		门店：<div id="store_select"></div> <br><br>
		<p style="text-align: center;"><button style="border:1px solid #aaa;margin-right:30px;background-color:#eee; " id="ok">确定</button><button  style="border:1px solid #aaa;margin-right:30px;background-color:#eee; "id="divclose">取消</button></p>
	</div>
	<button id= "caozuo" style="background-color:#eee;border:1px solid #777; color:#999" onclick='newmima(" + value+","+rows.orderid+","+rows.phone+")'>生成门店二维码</button>
		<table id="grid-table"></table>

		<div id="grid-pager"></div>
		<input id="hideid" style="display: none;"/>

		<script type="text/javascript">
		   $.ajax({
		   	   type:"post",
		     	url:" ${contextPath}/sys/storecode/getStoreAvailableSelectList",
		   	   success:function(data){
		   				$("#store_select").append(data); 
		   	   }
		   	});
    		$("#ok").click(function(){
   				$.ajax({
	 	      		   type:"post",
	        		   url:" ${contextPath}/sys/storecode/makecode",
	        		   dataType:"json", 
	       		  	   data:{"id":$("#storecode_select_").val()},
	       		  	   success:function(data){
	       		  		   console.log(data)
	       		  		   if(null!=data.ticket){
 	       		  			alert("门店二维码制作成功"); 
 							$("#hidediv").hide(); 
 							$("#hideid").val("");
 							$("#grid-table").trigger("reloadGrid");
	       		  		   }else{
	       		  				alert("修改失败"); 
	       		  		   }
	       		  	   }
   				});
    		});
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
			function newmima(id,orderid,phone){
				$("#hideid").val(id);
    			$("#orderid").text(orderid);
    			$("#phoneno").text(phone);
    			$("#hidediv").show(); 
    		}
			$("#divclose").click(function(){
				$("#hideid").val("");
				$("#orderid").text("");
    			$("#phoneno").text("");
				$("#hidediv").hide(); 
			});
    		
    		
		</script>

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">


		var scripts = [ null, "${contextPath}/static/assets/js/date-time/bootstrap-datepicker.js", 
		                "${contextPath}/static/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js", 
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		var grid_selector = "#grid-table";
        		var pager_selector = "#grid-pager";

        		// resize to fit page size
        		$(window).on('resize.jqGrid', function() {
        			$(grid_selector).jqGrid('setGridWidth', $(".page-content").width());
        		});
        		// resize on sidebar collapse/expand
        		var parent_column = $(grid_selector).closest('[class*="col-"]');
        		$(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed) {
        			if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
        				// setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        				setTimeout(function() {
        					$(grid_selector).jqGrid('setGridWidth', parent_column.width());
        				}, 0);
        			}
        		});

        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/storecode/getStoreCode",
        			datatype : "json",
        			height : 450,
        			colNames : ['', '店面id','店名 ', '店面编号','二维码', '换取二维码票据', '过期时长', '二维码真实url'],
        			colModel : [ {
        				name : '',
        				index : '',
        				width : 80,
        				fixed : true,
        				sortable : false,
        				resize : false,
        				formatter : 'actions',
        				formatoptions : {
        					keys : true,
        					//delbutton : false,//disable delete button
        					delOptions : {
        						recreateForm : true,
        						beforeShowForm : beforeDeleteCallback
        					}
        					//editformbutton:true, editOptions:{recreateForm:true, beforeShowForm:beforeEditCallback}
        				}
        			}, {
        				name : 'storeid',
        				index : 'storeid',
        				label : '门店id',
        				width : 100,
        				search : true
        			}, {
        				name : 'storename',
        				index : 'storename',
        				label : '店名',
        				width : 100,
        				search : true,
        				searchoptions:{
        					sopt:["cn"]
        				}
        			}, {
        				name : 'storeno',
        				index : 'storeno',
        				label : '门店编号',
        				width : 110,
        				search : true
        			}, {
        				name : 'ticket',
        				index : 'ticket',
        				label : '票据',
        				width : 120,
        				formatter : ticketUrl
        			}, {
        				name : 'ticket',
        				index : 'ticket',
        				label : '票据',
        				width : 120
        			}, {
        				name : 'expire_seconds',
        				index : 'expire_seconds',
        				label : '过期时间',
        				width : 110,
        				readonly : true,
        				search : true
        			}, {
        				name : 'url',
        				index : 'url',
        				label : '实际请求url',
        				width : 100
        			}],
        			//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
        			sortname : "storeid",
        			sortorder : "desc",
        			viewrecords : true,
        			rowNum : 10,
        			rowList : [ 10, 20, 30 ],
        			pager : pager_selector,
        			altRows : true,
        			//toppager : true,
        			multiselect : true,
        			//multikey : "ctrlKey",
        	        multiboxonly : true,
        			loadComplete : function() {
        				var table = this;
        				setTimeout(function(){
        					styleCheckbox(table);
        					updatePagerIcons(table);
        					enableTooltips(table);
        				}, 0);
        			},
        		});
        		
        		$(window).triggerHandler('resize.jqGrid');// trigger window resize to make the grid get the correct size
        		
        		
        		function ticketUrl(cellvalue, options, cell) {
        			cellvalue = "<a target='_blank' href='https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+cellvalue+"' >打开</a>";
        			return cellvalue;
        		}
        		
        		function stateChange(cellvalue, options, cell) {
        			if(cell.reservationstate==1)cellvalue="审核通过";
        			else if(cell.reservationstate==2)cellvalue="已核销";
        			else cellvalue ="待审核";
        			return cellvalue;
        		}
        		
        		// navButtons
        		jQuery(grid_selector).jqGrid('navGrid', pager_selector, { // navbar options
        			del : <shiro:hasPermission name="ROLE_ADMIN:delete">true</shiro:hasPermission><shiro:lacksPermission name="ROLE_ADMIN:delete">false</shiro:lacksPermission>,
        			delicon : 'ace-icon fa fa-trash-o red',
        			search : <shiro:hasPermission name="ROLE_ADMIN:search">true</shiro:hasPermission><shiro:lacksPermission name="ROLE_ADMIN:search">false</shiro:lacksPermission>,
        			searchicon : 'ace-icon fa fa-search orange',
        			refresh : true,
        			refreshicon : 'ace-icon fa fa-refresh blue',
        			view : <shiro:hasPermission name="ROLE_ADMIN:view">true</shiro:hasPermission><shiro:lacksPermission name="ROLE_ADMIN:view">false</shiro:lacksPermission>,
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
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
        			}
        		});
        		
        		// add custom button to export the data to excel
				jQuery(grid_selector).jqGrid('navButtonAdd', pager_selector,{
				   caption : "",
			       title : "导出Excel",
			       buttonicon : "ace-icon fa fa-file-excel-o green", 
			       onClickButton : function () { 
			    	   var keys = [], ii = 0, rows = "";
			    	   var ids = $(grid_selector).getDataIDs(); // Get All IDs
			    	   var row = $(grid_selector).getRowData(ids[0]); // Get First row to get the labels
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
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/TzzReservation/operateReservation?oper=excel' method='post'>";
			    	   form = form + "<input type='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open('', '');
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		

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
        			var buttons = dialog.find('.EditTable');
        			buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
        			buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
        			buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
        		}

        		function beforeDeleteCallback(e) {
        			var form = $(e[0]);
        			if (form.data('styled'))
        				return false;
        			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
        			style_delete_form(form);
        			form.data('styled', true);
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
        			});
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
