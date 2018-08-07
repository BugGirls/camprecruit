<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/ui.jqgrid.css" />
<link rel="stylesheet" href="${contextPath}/static/assets/css/jquery.gritter.css" />

<div class="row">
	<div class="col-xs-12">
		<div class="well well-sm"> 
				 
				<b id="editInformationButton" role="button" class="btn btn-purple btn-sm" data-toggle="modal">
					编辑记录
				</b>  			 
           
		</div>

		<table id="grid-table"></table>

		<div id="grid-pager"></div>

		<script type="text/javascript">
			var $path_base = "${contextPath}/static";//in Ace demo this will be used for editurl parameter
		</script>

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<div id="modal-table" style="display: none; position:fixed; top: 40px;left: 300px;" tabindex="-1"  >
	<div class="modal-dialog" style="min-width: 820px;">
		<form id="informationForm">
			<div class="modal-content">
				<div class="modal-header no-padding">
					<div class="table-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							<span class="white">&times;</span>
						</button>
						信息发布
					</div>
				</div>
				<div class="modal-body" style="max-height: 500px;overflow-y: scroll;">
					<div id="modal-tip" class="red clearfix"></div>
					<div>
						<input type="hidden" id="id" />
					</div>  
					<h4 class="header blue clearfix">内容：</h4>
					 <script id="editor"  type="text/plain"></script>  
				</div>
				<div class="modal-footer no-margin-top">
					<div class="text-center">
						<input id="submitButton" type="button"  class="btn btn-app btn-success btn-xs" value="提交"/>
							   
						<input id="closediv" class="btn btn-app btn-pink btn-xs" data-dismiss="modal" type="button" value="取消"/>
							  
					</div>
				</div>
			</div><!-- /.modal-content -->
		</form>
	</div><!-- /.modal-dialog -->
</div>
<script type="text/javascript" src= "${contextPath}/static/edit/ueditorconfig3.js"></script>
<script type="text/javascript" src= "${contextPath}/static/edit/ueditor.all.js"></script>
<script type="text/javascript">

var uec = UE.getEditor('editor', {
    autoHeight: false,
    wordCount:false ,
    initialFrameWidth:767 , //初始化编辑器宽度,默认1000
    initialFrameHeight:400  //初始化编辑器高度,默认320 
}) ;
</script>
<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                "${contextPath}/static/assets/js/jquery-ui.custom.js",
		        		"${contextPath}/static/assets/js/jquery.ui.touch-punch.js",
		        		"${contextPath}/static/assets/js/markdown/markdown.js",
		        		"${contextPath}/static/assets/js/markdown/bootstrap-markdown.js",
		        		"${contextPath}/static/assets/js/jquery.hotkeys.js", 
		        		"${contextPath}/static/assets/js/bootstrap-wysiwyg.js",
		        		"${contextPath}/static/assets/js/bootbox.js", 
		        		"${contextPath}/static/assets/js/jquery.gritter.js" null ];
        $(".page-content-area").ace_ajax("loadScripts", scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        		var grid_selector = "#grid-table";
        		var pager_selector = "#grid-pager";

        		// resize to fit page size
        		$(window).on("resize.jqGrid", function() {
        			$(grid_selector).jqGrid("setGridWidth", $(".page-content").width());
        		});
        		// resize on sidebar collapse/expand
        		var parent_column = $(grid_selector).closest("[class*='col-']");
        		$(document).on("settings.ace.jqGrid", function(ev, event_name, collapsed) {
        			if (event_name === "sidebar_collapsed" || event_name === "main_container_fixed") {
        				// setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        				setTimeout(function() {
        					$(grid_selector).jqGrid("setGridWidth", parent_column.width());
        				}, 10);
        			}
        		});
        		  
        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/pdfvip/getpdfvip",
        			datatype : "json",
        			height : 400,
        			colNames : ["ID"  ,"标题","创建时间",""],
        			colModel : [ {
        				name : "id",
        				index : "id",
        				label : "ID",
        				width : 100,
        				hidden : true,
        				sorttype : "long",
        				search : false
        			}, {
        				name : "image",
        				index : "image", 
        				label : "fenlei",
        				width : 250,
        				search : false
        			} , {
        				name : "refreshTime",
        				index : "refreshTime",
        				label : "发布时间",
        				width : 250,
        				sorttype : "date",
        				search : false
        			}, {
        				name : "content",
        				index : "content",
        				hidden : true,
        				label : "正文",
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
        				}, 10);
        			},
        			editurl : "${contextPath}/sys/pdfvip/operatepdfvip"
        			//caption : "用户管理列表",
        			//autowidth : true,
        			/**
        			grouping : true, 
        			groupingView : { 
        				 groupField : ["name"],
        				 groupDataSorted : true,
        				 plusicon : "fa fa-chevron-down bigger-110",
        				 minusicon : "fa fa-chevron-up bigger-110"
        			},
        			*/
        		});
        		
        		$(window).triggerHandler("resize.jqGrid");// trigger window resize to make the grid get the correct size
        		
        		// enable search/filter toolbar
        		// jQuery(grid_selector).jqGrid("filterToolbar",{defaultSearch:true,stringResult:true})
        		// jQuery(grid_selector).filterToolbar({});
        		// switch element when editing inline
        		function aceSwitch(cellvalue, options, cell) {
        			setTimeout(function() {
        				$(cell).find("input[type=checkbox]").addClass("ace ace-switch ace-switch-5").after("<span class='lbl'></span>");
        			}, 0);
        		}
        		  
        		function showErrorAlert(reason, detail) {
        			var msg = "";
        			if (reason === "unsupported-file-type") {
        				msg = "Unsupported format " + detail;
        			} else {
        				// console.log("error uploading file", reason, detail);
        			}
        			$("<div class='alert'> <button type='button' class='close' data-dismiss='alert'>&times;</button>" + "<strong>File upload error</strong> " + msg + " </div>").prependTo("#alerts");
        		}
        		
        		$("#addInformationButton").bind("click", function() { 
        			$("#modal-table").css("display","block");
    				$("#informationForm")[0].reset(); 
    			  
    				$("#modal-tip").html("");
    				
    				uec.ready(function() { 
    					uec.setContent("");  
    				});
    				 
        		});
        		$("#closediv").click(function(){
        			$("#modal-table").css("display","none");
    				$("#informationForm")[0].reset(); 
    			  
    				$("#modal-tip").html("");
    				
    				uec.ready(function() { 
    					uec.setContent("");  
    				}); 
        		});
        		$(".close").click(function(){
        			$("#modal-table").css("display","none");
    				$("#informationForm")[0].reset(); 
    			  
    				$("#modal-tip").html("");
    				 
    				uec.ready(function() { 
    					uec.setContent("");  
    				});
    				 
        		});
        		$("#editInformationButton").bind("click", function() {
        			var selectedId = $(grid_selector).jqGrid("getGridParam", "selrow");
        			if(null == selectedId){
        				$.gritter.add({
    		                title: "系统信息",
    		                text: "请选择记录",
    		                class_name: "gritter-info gritter-center"
    		            });        				
        			}else{ 
        				$("#modal-table").css("display","block");
        				$("#id").val(jQuery(grid_selector).jqGrid("getRowData",jQuery(grid_selector).jqGrid("getGridParam", "selrow")).id);
        				var content=jQuery(grid_selector).jqGrid("getRowData",jQuery(grid_selector).jqGrid("getGridParam", "selrow")).content;
        				 
        				uec.ready(function() { 
        					uec.setContent(content);  
        				});
        			 
        			}
        		}); 
        		
        		$("#submitButton").on("click", function(e) {  
        			var content=UE.getEditor('editor').getContent(); 
        		 
        			if (content == "") {
    					$("#modal-tip").html("请填写内容");
    					return false; 
    			    } else{     
    			    	 
    			    	 $.ajax({
            			 	dataType : "json",
            			 	url : "${contextPath}/sys/pdfvip/operatepdfvip?oper=save",
            			 	type : "post",
            			 	data : {
            			 		id : $("#id").val(), 
            			 		content :content  
            			 	},
            			 	success:function(){   
            			 		$("#modal-table").css("display","none");
            			 		$("#refresh_grid-table").click();
            			 	}
            		 	});
    			    } 
        		});
        		
        		// navButtons
        		jQuery(grid_selector).jqGrid("navGrid", pager_selector, { // navbar options
        			edit : false,
        			editicon : "ace-icon fa fa-pencil blue",
        			add : false,
        			addicon : "ace-icon fa fa-plus-circle purple",
        			del : false,
        			delicon : "ace-icon fa fa-trash-o red",
        			search : false,
        			searchicon : "ace-icon fa fa-search orange",
        			refresh : true,
        			refreshicon : "ace-icon fa fa-refresh blue",
        			view : true,
        			viewicon : "ace-icon fa fa-search-plus grey"
        		}, {
        			// edit record form
        			// closeAfterEdit: true,
        			// width: 700,
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				form.closest(".ui-jqdialog").find(".ui-jqdialog-titlebar").wrapInner("<div class='widget-header' />");
        				style_edit_form(form);
        			},
    				errorTextFormat: function (response) {
    					var result = eval("("+response.responseText+")");
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
        				form.closest(".ui-jqdialog").find(".ui-jqdialog-titlebar").wrapInner("<div class='widget-header' />")
        				style_edit_form(form);
        			},
    				errorTextFormat: function (response) {
    					var result = eval("("+response.responseText+")");
    				    return result.message;
    				}
        		}, {
        			// delete record form
        			recreateForm : true,
        			beforeShowForm : function(e) {
        				var form = $(e[0]);
        				if (form.data("styled"))
        					return false;
        				form.closest(".ui-jqdialog").find(".ui-jqdialog-titlebar").wrapInner("<div class='widget-header' />")
        				style_delete_form(form);
        				form.data("styled", true);
        			},
        			onClick : function(e) {
        				// alert(1);
        			}
        		}, {
        			// search form
        			recreateForm : true,
        			afterShowSearch : function(e) {
        				var form = $(e[0]);
        				form.closest(".ui-jqdialog").find(".ui-jqdialog-title").wrap("<div class='widget-header' />")
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
        				form.closest(".ui-jqdialog").find(".ui-jqdialog-title").wrap("<div class='widget-header' />")
        			}
        		})
        		
        		// add custom button to export the data to excel
				jQuery(grid_selector).jqGrid("navButtonAdd", pager_selector,{
				   caption : "",
			       title : "导出Excel",
			       buttonicon : "ace-icon fa fa-file-excel-o green", 
			       onClickButton : function () { 
			    	   var keys = [], ii = 0, rows = "";
			    	   var ids = $(grid_selector).getDataIDs(); // Get All IDs
			    	   var row = $(grid_selector).getRowData(ids[0]); // Get First row to get the labels
			    	   //var label = $(grid_selector).jqGrid("getGridParam","colNames");
   			    	   for (var k in row) {
			    	   	   keys[ii++] = k; // capture col names
			    	   	   rows = rows + k + "\t"; // output each Column as tab delimited
			    	   }
			    	   rows = rows + "\n"; // Output header with end of line
			    	   for (i = 0; i < ids.length; i++) {
			    	   	   row = $(grid_selector).getRowData(ids[i]); // get each row
			    	   	   for (j = 0; j < keys.length; j++)
			    	   		   rows = rows + row[keys[j]] + "\t"; // output each Row as tab delimited
			    	   	   rows = rows + "\n"; // output each row with end of line
			    	   }
			    	   rows = rows + "\n"; // end of line at the end
			    	   var form = "<form name='csvexportform' action='${contextPath}/sys/pdfvip/operatepdfvip?oper=excel' method='post'>";
			    	   form = form + "<input type='hidden' name='csvBuffer' value='" + encodeURIComponent(rows) + "'>";
			    	   form = form + "</form><script>document.csvexportform.submit();</sc" + "ript>";
			    	   OpenWindow = window.open("", "");
			    	   OpenWindow.document.write(form);
			    	   OpenWindow.document.close();
			       } 
				});
        		
        		function style_edit_form(form) {
        			// form.find("input[name=statusCn]").addClass("ace ace-switch ace-switch-5").after("<span class="lbl"></span>");
        			// don"t wrap inside a label element, the checkbox value won"t be submitted (POST"ed)
        			// .addClass("ace ace-switch ace-switch-5").wrap("<label class="inline" />").after("<span class="lbl"></span>");

        			// update buttons classes
        			var buttons = form.next().find(".EditButton .fm-button");
        			buttons.addClass("btn btn-sm").find("[class*='-icon']").hide();// ui-icon, s-icon
        			buttons.eq(0).addClass("btn-primary").prepend("<i class='ace-icon fa fa-check'></i>");
        			buttons.eq(1).prepend("<i class='ace-icon fa fa-times'></i>")

        			buttons = form.next().find(".navButton a");
        			buttons.find(".ui-icon").hide();
        			buttons.eq(0).append("<i class='ace-icon fa fa-chevron-left'></i>");
        			buttons.eq(1).append("<i class='ace-icon fa fa-chevron-right'></i>");
        		}

        		function style_delete_form(form) {
        			var buttons = form.next().find(".EditButton .fm-button");
        			buttons.addClass("btn btn-sm btn-white btn-round").find("[class*='-icon']").hide();// ui-icon, s-icon
        			buttons.eq(0).addClass("btn-danger").prepend("<i class='ace-icon fa fa-trash-o'></i>");
        			buttons.eq(1).addClass("btn-default").prepend("<i class='ace-icon fa fa-times'></i>")
        		}

        		function style_search_filters(form) {
        			form.find(".delete-rule").val("X");
        			form.find(".add-rule").addClass("btn btn-xs btn-primary");
        			form.find(".add-group").addClass("btn btn-xs btn-success");
        			form.find(".delete-group").addClass("btn btn-xs btn-danger");
        		}
        		
        		function style_search_form(form) {
        			var dialog = form.closest(".ui-jqdialog");
        			var buttons = dialog.find(".EditTable");
        			buttons.find(".EditButton a[id*='_reset']").addClass("btn btn-sm btn-info").find(".ui-icon").attr("class", "ace-icon fa fa-retweet");
        			buttons.find(".EditButton a[id*='_query']").addClass("btn btn-sm btn-inverse").find(".ui-icon").attr("class", "ace-icon fa fa-comment-o");
        			buttons.find(".EditButton a[id*='_search']").addClass("btn btn-sm btn-purple").find(".ui-icon").attr("class", "ace-icon fa fa-search");
        		}

        		function beforeDeleteCallback(e) {
        			var form = $(e[0]);
        			if (form.data("styled"))
        				return false;
        			form.closest(".ui-jqdialog").find(".ui-jqdialog-titlebar").wrapInner("<div class='widget-header' />")
        			style_delete_form(form);
        			form.data("styled", true);
        		}

        		function beforeEditCallback(e) {
        			var form = $(e[0]);
        			form.closest(".ui-jqdialog").find(".ui-jqdialog-titlebar").wrapInner("<div class='widget-header' />")
        			style_edit_form(form);
        		}

        		// it causes some flicker when reloading or navigating grid
        		// it may be possible to have some custom formatter to do this as the grid is being created to prevent this
        		// or go back to default browser checkbox styles for the grid
        		function styleCheckbox(table) {
        			/**
        			 * $(table).find("input:checkbox").addClass("ace") .wrap("<label />") .after("<span class="lbl align-top" />") $(".ui-jqgrid-labels th[id*="_cb"]:first-child")
        			 * .find("input.cbox[type=checkbox]").addClass("ace") .wrap("<label />").after("<span class="lbl align-top" />");
        			 */
        		}

        		// unlike navButtons icons, action icons in rows seem to be hard-coded
        		// you can change them like this in here if you want
        		function updateActionIcons(table) {
        			/**
        			 * var replacement = { "ui-ace-icon fa fa-pencil" : "ace-icon fa fa-pencil blue", "ui-ace-icon fa fa-trash-o" : "ace-icon fa fa-trash-o red", "ui-icon-disk" : "ace-icon fa fa-check green", "ui-icon-cancel" :
        			 * "ace-icon fa fa-times red" }; $(table).find(".ui-pg-div span.ui-icon").each(function(){ var icon = $(this); var $class = $.trim(icon.attr("class").replace("ui-icon", "")); if($class in replacement)
        			 * icon.attr("class", "ui-icon "+replacement[$class]); })
        			 */
        		}

        		// replace icons with FontAwesome icons like above
        		function updatePagerIcons(table) {
        			var replacement = {
        				"ui-icon-seek-first" : "ace-icon fa fa-angle-double-left bigger-140",
        				"ui-icon-seek-prev" : "ace-icon fa fa-angle-left bigger-140",
        				"ui-icon-seek-next" : "ace-icon fa fa-angle-right bigger-140",
        				"ui-icon-seek-end" : "ace-icon fa fa-angle-double-right bigger-140"
        			};
        			$(".ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon").each(function() {
        				var icon = $(this);
        				var $class = $.trim(icon.attr("class").replace("ui-icon", ""));

        				if ($class in replacement)
        					icon.attr("class", "ui-icon " + replacement[$class]);
        			})
        		}

        		function enableTooltips(table) {
        			$(".navtable .ui-pg-button").tooltip({
        				container : "body"
        			});
        			$(table).find(".ui-pg-div").tooltip({
        				container : "body"
        			});
        		}

        		// var selr = jQuery(grid_selector).jqGrid("getGridParam","selrow");

        		$(document).one("ajaxloadstart.page", function(e) {
        			$(grid_selector).jqGrid("GridUnload");
        			$(".ui-jqdialog").remove();
        		});
        		
        	});
        });
</script>
