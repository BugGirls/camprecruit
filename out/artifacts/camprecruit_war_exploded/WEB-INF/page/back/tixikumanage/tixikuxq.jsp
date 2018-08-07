<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />

<div class="row">
	<div class="col-xs-12">
	<a href="javascript:history.go(-1);">返回上一页</a>
		<table id="grid-table"></table>

		<div id="grid-pager"></div>
		
		<script type="text/javascript">
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
			
		</script>

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col --> 
<div> &nbsp;</div>
		<div style="float:left;">
        	<span style="line-height:30px; display：block; float:left;">分类：</span>
	        	<select id="f1" style=" display:block; width:120px; height:30px; border:1px solid #e5e5e5; float:left; margin-right:10px; outline:none;">
	       		<option value="" style="border:none; outline:none;">请选择分类</option>
		        <c:forEach items="${fllist }" var="fl">
		        	<c:if test="${fl.type==1&&fl.parentId==0 }">
		        		<option value="${fl.id }">${fl.name }</option>
		        	</c:if>
		        </c:forEach>
	        </select> 
	        <select id="f2" style=" display:block; width:120px; height:30px; border:1px solid #e5e5e5; float:left; margin-right:10px; outline:none;">
	        	<option style="border:none; outline:none;" value="">请选择分类</option>
		    </select>
	        <select id="f3" style=" display:block; width:120px; height:30px; border:1px solid #e5e5e5; float:left; margin-right:10px; outline:none;"> 
	      		 <option style="border:none; outline:none;" value="">请选择分类</option>
		    </select> 
		     <button id="submit">搜索</button>
		   <div class="clear"></div> 
		  
    	</div>
    	<div style="float:left; height:00px;width:600px;  margin-bottom: -32px;">
    		<p style="color:#4c8fbd; margin-left:60px;">点击加号添加</p>
    		<ol id="courseli" style="border:1px solid #ddd; height:180px;overflow-y: auto;">
    		
    		</ol>
    	
    	</div>
</div><!-- /.row -->
 




<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		var grid_selector = "#grid-table";
        		var pager_selector = "#grid-pager";
        		
        		$(".row").on("click","refresh_grid-table",function(){
        			alert(123456);
        		})
        		
        		//搜索
        		$("#submit").click(function(){
        			if($("#f3").val()!=""){ 
        				//alert("ss");
        				 $.ajax({
      	 	      		   type:"post",
      	        		   url:"${contextPath}/sys/tixikucourse/searchc?txkdid=${txkdid}",
      	        		   dataType:"json", 
      	       		  	   data:{"fid":$("#f3").val()},
      	       		  	   success:function(data){ 
      	       		  		  // alert(data.clist[0].id);
      	       		  		   if(data.err==0){ 
      	       		  			   var html="";
      	       		  			   for(var i=0;i<data.clist.length;i++){
      	       		  				   html+="<li id='"+data.clist[i].id+"'><span class='jia' style='cursor: pointer;'>&nbsp;&nbsp;&nbsp;＋&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.clist[i].name+" </li>"
      	       		  			   }
      	       		  			   
      	       		  			   $("#courseli").html(html);
      	       		  			 //  alert(data.clist[0].id );
      	       		  		   } 
      	 		       	   }  
      	        	   }); 
        			}else{ 
        			} 
        		});
        		
        		//添加 ${contextPath}/sys/tixikucourse/operateTzztc?txkdid=${txkdid}
        		$("#courseli").on("click",".jia",function(){ 
        			var ft = confirm("确定添加吗？"); 
        			var courseid = $(this).parent().prop("id"); 
        			if(ft){ 
        			 	$.ajax({
    	 	      		   type:"post",
    	        		   url:" ${contextPath}/sys/tixikucourse/operateTzztc?txkdid=${txkdid}",
    	        		   dataType:"json", 
    	       		  	   data:{"oper":"add","coursecn":courseid },
    	       		  	   success:function(data){  
    	       		  			$("#refresh_grid-table").click();
    	       		  		  $.ajax({
    	      	 	      		   type:"post",
    	      	        		   url:"${contextPath}/sys/tixikucourse/searchc?txkdid=${txkdid}",
    	      	        		   dataType:"json", 
    	      	       		  	   data:{"fid":$("#f3").val()},
    	      	       		  	   success:function(data){ 
    	      	       		  		  // alert(data.clist[0].id);
    	      	       		  		   if(data.err==0){ 
    	      	       		  			   var html="";
    	      	       		  			   for(var i=0;i<data.clist.length;i++){
    	      	       		  				   html+="<li id='"+data.clist[i].id+"'><span class='jia' style='cursor: pointer;'>&nbsp;&nbsp;&nbsp;＋&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.clist[i].name+" </li>"
    	      	       		  			   }
    	      	       		  			   
    	      	       		  			   $("#courseli").html(html);
    	      	       		  			 //  alert(data.clist[0].id );
    	      	       		  		   } 
    	      	 		       	   }  
    	      	        	   }); 
    	 		       	   }  
    	        	   });
        			}  
        		});
        		
        		//级联
    			$("#f1").change(function(){    
    				$("#f2").html("");
    				if($("#f1").val()==""){
    					$("#f2").html("<option value=''>请选择分类</option>");
    					$("#f3").html("<option value=''>请选择分类</option>");
    					return;
    				}
    				$.ajax({
    		      		   type:"post",
    		      		   url:"${contextPath}/top/wenda/selects",
    		      		   dataType:"text", 
    	     		  	   data:{"pid":$("#f1").val()},
    	     		  	   success:function(data){ 
    	     		  			$("#f2").html(data);
    	     		  			$("#f3").html("");
    	     		  			$.ajax({
    	     		      		   type:"post",
    	     		      		   url:"${contextPath}/top/wenda/selects",
    	     		      		   dataType:"text", 
    	     	     		  	   data:{"pid":$("#f2").val()},
    	     	     		  	   success:function(data){ 
    	     	     		  			$("#f3").html(data); 
    	     			       	   }  
    	     	      	  	   });  
    			       	 }  
    	      	   }); 
    				 
    			});
    			$("#f2").change(function(){    
    				$("#f3").html("");
    				if($("#f2").val()==""){
    					$("#f3").html("<option value=''>请选择分类</option>");
    					return;
    				}
    				$.ajax({
    		      		   type:"post",
    		      		   url:"${contextPath}/top/wenda/selects",
    		      		   dataType:"text", 
    	     		  	   data:{"pid":$("#f2").val()},
    	     		  	   success:function(data){ 
    	     		  			$("#f3").html(data); 
    			       	 }  
    	      	   }); 
    				 
    			});
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
        			url : "${contextPath}/sys/tixikucourse/getTzztc?txkdid=${txkdid}",
        			datatype : "json",
        			height : 400,
        			colNames : ['', 'ID','课程名称','创建时间'],
        			colModel : [ {
        				name : '',
        				index : '',
        				width : 100,
        				fixed : true,
        				sortable : false,
        				resize : false,
        				formatter : 'actions',
        				formatoptions : {
        					keys : true,
        					editbutton : false,//disable delete button
        					delOptions : {
        						recreateForm : true,
        						beforeShowForm : beforeDeleteCallback
        					}
        					//editformbutton:true, editOptions:{recreateForm:true, beforeShowForm:beforeEditCallback}
        				}
        			}, {
        				name : 'id',
        				index : 'id',
        				label : 'ID',
        				width : 60,
        				sorttype : "int",
        				search : false
        			}, {
        				name : 'coursecn',
        				index : 'coursecn',
        				label : '课程名称',
        				width : 150,
        				editable : true,
        				editoptions :{ 
        					dataUrl : "${contextPath}/sys/TzzCourseWare/getCourse"
        				},
        				edittype : "select",
        				searchoptions : {sopt : ['en']},
        				editrules : {required : true}
        			}, {
        				name : 'createTime',
        				index : 'createTime',
        				label : '创建时间',
        				width : 250,  
        				sorttype : "date",
        				search : false
        			}],
        			//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
        			sortname : "id",
        			sortorder : "asc",
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
        					updateActionIcons(table);
        					updatePagerIcons(table);
        					enableTooltips(table);
        				}, 0);
        			},
        			editurl : "${contextPath}/sys/tixikucourse/operateTzztc?txkdid=${txkdid}"
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
        		
        		// navButtons
        		jQuery(grid_selector).jqGrid('navGrid', pager_selector, { // navbar options
        			edit : false,
        			editicon : 'ace-icon fa fa-pencil blue',
        			add : false,
        			addicon : 'ace-icon fa fa-plus-circle purple',
        			del : true,
        			delicon : 'ace-icon fa fa-trash-o red',
        			search : false,
        			searchicon : 'ace-icon fa fa-search orange',
        			refresh : true,
        			refreshicon : 'ace-icon fa fa-refresh blue',
        			view : true,
        			viewicon : 'ace-icon fa fa-search-plus grey'
        		}, {
        			// edit record form
        			// closeAfterEdit: true,
        			 width: 500,
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
        				style_edit_form(form);
        			},
    				errorTextFormat: function (response) {
    					var result = eval('('+response.responseText+')');
    				    return result.message;
    				}
        		}, {
        			// new record form
        			width: 500,
        			closeAfterAdd : true,
        			recreateForm : true,
        			viewPagerButtons : false,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
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
			    	   var label = $(grid_selector).jqGrid('getGridParam','colNames');
   			    	   for(var i = 2 ; i < label.length; i++){
			    	   	   rows = rows + label[i] + "\t"; // output each Column as tab delimited
			    	   }
			    	   var row = $(grid_selector).getRowData(ids[0]); // Get First row to get the labels
			    	   
   			    	   for (var k in row) {
			    	   	   keys[ii++] = k; // capture col names
			    	   }
			    	   
			    	   rows = rows + "\n"; // Output header with end of line
			    	   for (i = 0; i < ids.length; i++) {
			    	   	   row = $(grid_selector).getRowData(ids[i]); // get each row
			    	   	   for (j = 1; j < keys.length; j++)
			    	   		   rows = rows + row[keys[j]] + "\t"; // output each Row as tab delimited
			    	   	   rows = rows + "\n"; // output each row with end of line
			    	   }
			    	   rows = rows + "\n"; // end of line at the end
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/tixikucourse/operateTzztc?txkdid='${txkdid}'&oper=excel' method='post'>";
			    	   form = form + "<input type='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open('', '');
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		
        		function style_edit_form(form) {
        			// form.find('input[name=statusCn]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
        			// don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
        			// .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

        			// update buttons classes
        			var buttons = form.next().find('.EditButton .fm-button');
        			buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();// ui-icon, s-icon
        			buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
        			buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>');

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

        		function beforeEditCallback(e) {
        			var form = $(e[0]);
        			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
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
