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
		
		<div style="display: none;position:absolute ;width:300px;z-index:999;top:0;left:0;background-color: #fff;border: 1px solid #aaa;" id="aid" >
		  <div style="border:1px solid #aaa ; background-color:#eee; color:#23b820;font-size:20px ;padding:5px ;text-align:center;">
		  	<input type="button"  style="float:left;" name="aup" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" value="提交"/>&nbsp;&nbsp;&nbsp;
			 <input type="button"  style="float:left;"name="aclose"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" value="取消">
				 
			</div><div style="padding: 10px;">
		  	<form id="form2" method="post" enctype="multipart/form-data" action="${contextPath}/sys/alliancepromotionurl/setStaffPercentage">  
		  	<input id="agentpromotionid" name="agentpromotionid" type="hidden" />
		  	<input id="astaffid" name="astaffid" type="hidden" />
			<div style="float:left;"> 
				<div style="float:left;"> 		
				手机号		&nbsp;<input type="text" name="phone" id="aphone" maxlength="11"/><br><br>
		  		姓名	&nbsp;<input type="text" name="name" id="aname" maxlength="12"/><br><br>
				提成（%）		&nbsp;<input type="text" name="percentage" id="apercentage" maxlength="8"/><br><br>
				</div>
				 </div>
				 <div style="clear: both;"></div>
				<input type="button"class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm btn-primary" name="aup" value="提交"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="fm-button ui-state-default ui-corner-all fm-button-icon-left btn btn-sm" name="aclose" value="取消">
			 </form>
			</div> 
		</div>
	
		<script type="text/javascript"src="${contextPath}/static/dist/js/jquery-ui.js"></script> 
		<script type="text/javascript">
	     function createUrl(id){
// 	    	 $("#hideid").val(promotionid);
// 	    	 $("#hidediv").show(); 
				$.ajax({
			        type:"post", 
			        url:"${contextPath}/sys/alliancepromotion/createPromotionUrl",
			        data:{"promotionid":id},
			   		dataType:"json", 
			       	success:function(data){ 
			       		if(data.err=="0"){
			       			alert("链接创建成功");
			       			jQuery("#grid-table").trigger("reloadGrid");
			       		}else{
			       			alert("链接创建异常");
			       		}
			       	}
				});
				
			}
	     
		</script> 
 
 <div id="modal-table1" class="modal fade" tabindex="-1" data-backdrop="static">
	<div class="modal-dialog">
		<form id="informationForm1">
			<div class="modal-content">
				<div class="modal-header no-padding">
					<div class="table-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							<span class="white">&times;</span>
						</button>
						设置员工提成比
					</div>
				</div>
				<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
					<div id="modal-tip1" class="red clearfix"></div>
					<input id="promotionid" type="hidden" />
					<div class="widget-box widget-color-blue2">
						<div class="widget-body">
							<div class="widget-main padding-8">
								<ul id="sysuserTree"></ul>
							</div>
							<div>提成比例(%)：<input type="text" id="percentage"/></div>
<!-- 							<div>每日报名人数：<input type="text" id="datelimit"/></div> -->
						</div>
					</div>
				</div>
				<div class="modal-footer no-margin-top">
					<div class="text-center">
						<button id="submitButton1" type="submit" class="btn btn-app btn-success btn-xs">
							<i class="ace-icon fa fa-floppy-o bigger-160"></i>
							保存
						</button>
						<button class="btn btn-app btn-pink btn-xs" data-dismiss="modal">
							<i class="ace-icon fa fa-share bigger-160"></i>
							取消
						</button>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</form>
	</div><!-- /.modal-dialog -->
</div>
 
<!-- 添加修改 -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">

		var scripts = [ null,
		                "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js",
		                "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js",
		                "${contextPath}/static/assets/js/fuelux/fuelux.tree.js",
		        		 "${contextPath}/static/assets/js/ace-extra.js",
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

        		jQuery(grid_selector).jqGrid({
        			subGrid : false,
        			url : "${contextPath}/sys/alliancepromotion/getAgentPromotionList",
        			datatype : "json",
        			height : 650,
        			colNames : ['活动id','活动名称','活动价格','活动有效期至','状态','代理or自营','活动链接','操作','二维码','设置员工提成比','查看'],
        			colModel : [ {
        				name : 'promotionid',
        				index : 'promotionid',
        				label : '活动id',
//         				width : 60,
        				sorttype : "long",
        				search : false
        			}, {
        				name : 'promotion.name',
        				index : 'promotion.name',
        				label : '活动名称',
//         				width : 160,
        				search : true
        			}, {
        				name : 'promotion.price',
        				index : 'promotion.price',
        				label : '活动价格',
//         				width : 100,
        				search : true
        			}, {
        				name : 'promotion.end_date',
        				index : 'promotion.end_date',
        				label : '有效期至',
//         				width : 160,
        				search : true,
        				formatter : timeformat1
        			}, {
        				name : 'promotion.end_date',
        				index : 'promotion.end_date',
        				label : '活动状态',
//         				width : 160,
        				search : true,
        				formatter : timeformat2
        			}, {
        				name : 'agenttype',
        				index : 'agenttype',
        				label : '代理or自营',
//         				width : 160,
        				search : true,
        				formatter : agenttypeformat
        			}, {
        				name : 'promotionurl',
        				index : 'promotionurl',
        				label : '链接地址',
//         				width : 255 ,
        				editable : true,
        				search : false
        			}, {
        				name : 'promotion.id',
        				index : 'promotion.id',
        				label : '操作',
//         				width : 110 ,
        				editable : true,
        				search : false,
         				formatter: generateUrlFormatter
//         				formatter: function (value, grid, rows, state) {
//           					 return "&nbsp;&nbsp;&nbsp;&nbsp;<button id= \"" + value + "\" style=\"background-color:#eee;border:1px solid #777; \" onclick='createUrl(" + value + ")'>生成链接</button>";} 
        			}, {
        				name : '',
        				index : '',
        				label : '二维码',
//         				width : 255 ,
        				editable : true,
        				formatter : qrcodeUrl,
        				search : false
        			}, {
        				name : '',
        				index : '',
        				label : '设置员工提成比',
//         				width : 110  ,
        				editable : false,
        				search : false,
        				sortable : false,
        				formatter : staffpercentageFormatter
        			}, {
        				name : 'promotion.id',
        				index : 'promotion.id',
        				label : '查看',
//         				width : 110  ,
        				formatter: function (value, grid, rows, state) 
    					{
    					 return "<a href=\"${contextPath}/sys/sysuser/home#page/alliancestaffurl?asid=" + value + "\" style=\"color:#9ab\">员工提成设置详情 </a>";
    					  
    					 } ,
    					 sorttype : "int",
    					 search : false
        			}
					],
        			//scroll : 1, // set the scroll property to 1 to enable paging with scrollbar - virtual loading of records
        			sortname : "id",
        			sortorder : "asc",
        			viewrecords : true,
        			rowNum : 10,
        			rowList : [ 10, 20, 30 ],
        			pager : pager_selector,
        			altRows : true,
        			shrinkToFit: true,  
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
        			//caption : "用户管理列表",
        			autowidth : true
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
        		//显示图片格式
				function alarmFormatter(cellvalue, options, rowdata) {  
					return '<img id="imagel" width="200px" height="100px" src="${contextPath}'+cellvalue+'" alt="' + cellvalue + '" />';
					
				}
				function generateUrlFormatter(cellvalue, options, rowdata) {  
					if(rowdata.agenttype==0){
						return "&nbsp;&nbsp;&nbsp;&nbsp;<button id= \"" + cellvalue + "\" style=\"background-color:#eee;border:1px solid #777; \" onclick='createUrl(" + cellvalue + ")'>生成链接</button>";
					}else{
						return "";
					}
					
				}
				function booleanFormatter(cellvalue, options, rowdata) {  
					if(cellvalue==1)
						return "是";
					else
					return "否";
				}
				function agenttypeformat(cellvalue, options, rowdata) {  
					if(cellvalue==1)
						return "自营";
					else
					return "代理";
				}
				
				function qrcodeUrl(cellvalue, options, cell) {
					if(cell.promotionurl!=null &&cell.promotionurl!=""){
        				cellvalue = "<a target='_blank' href='${contextPath}/jsp/qrcode/qrcode.jsp?url="+cell.promotionurl+"' >生成二维码</a>";
					}else{
						cellvalue = "";
					}
        			return cellvalue;
        		}
				
				function timeformat(cellvalue, options, cell) {
					if(cellvalue==""||cellvalue==null){
						return "";
					}else{
		       			return new Date(cellvalue).Format("yyyy-MM-dd");
					}
	       		}
				
				function timeformat1(cellvalue, options, cell) {
					if(cellvalue==""||cellvalue==null){
						return "";
					}else if(cellvalue.length>10){
		       			return cellvalue.substr(0,10);
					}else{
						return cellvalue;
					}
	       		}
				function timeformat2(cellvalue, options, cell) {
					if(cellvalue==""||cellvalue==null){
						return "";
					}else{
		       			var today = new Date().Format("yyyyMMdd");
		       			var enddate = new Date(cellvalue).Format("yyyyMMdd");
		       			if(enddate<today){
		       				return "已过期";
		       			}else{
		       				return "代理中";
		       			}
					}
	       		}
				
				function staffpercentageFormatter(cellvalue, options, cell) {
        			var template = "<button data-toggle='modal' onclick='javascript:$(\"#modal-table1\").modal(\"toggle\");$(\"#promotionid\").val(\"" + cell.promotion.id +"\");' class='btn btn-xs btn-warning'><i class='ace-icon fa fa-flag bigger-120'></i></button>";
        			return template;

// 					var template = "<button onclick='javascript:addstaff(\""+cell.promotionid+"\");' class='btn btn-xs btn-warning'><i class='ace-icon fa fa-flag bigger-120'></i></button>";
//          			return template;
        		}
				
				var treeflag = 0;
        		$("#modal-table1").on('shown.bs.modal', function() {
        			var remoteDateSource = function(options, callback) {
        				var parent_id = null;
            			if (!('text' in options || 'type' in options)) {
            				parent_id = 0;// load first level data
            			} else if ('type' in options && options['type'] == 'folder') {// it has children
            				if ('additionalParameters' in options && 'children' in options.additionalParameters)
            					parent_id = options.additionalParameters['id'];
            			}
            			if (parent_id !== null) {
            				$.ajax({
            					url : "${contextPath}/sys/alliancepromotionurl/getAllianceUserTreeList?rand=" + Math.random(1000),
            					type : 'POST',
            					data : {"promotionid" : $("#promotionid").val(),
            						"id" : parent_id
            						},
            					dataType : 'json',
            					complete : function(response) {
            						var returninfo = eval("(" + response.responseText + ")");
            						if (returninfo.status == "OK") {
            							callback({
            								data : returninfo.data
            							});
            						}
            					}
            				});
            			}
            		};
            		$('#sysuserTree').ace_tree({
            			dataSource : remoteDateSource,
            			multiSelect : false,
            			loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
            			'open-icon' : 'ace-icon tree-minus',
            			'close-icon' : 'ace-icon tree-plus',
            			'selectable' : true,
            			'selected-icon' : 'ace-icon fa fa-check',
            			'unselected-icon' : 'ace-icon fa fa-times',
            			cacheItems : false,
            			folderSelect : false
            		});
            		//$('#authorityTree').ace_tree('discloseAll');
        		    if(treeflag > 0){
        		    	$('#sysuserTree').tree('render');
        		    }
        		    treeflag++;
        		    $('#sysuserTree').one('loaded.fu.tree', function (event, data) {
        		    	$('#sysuserTree').data('ignore-disclosures-limit', true);
        		    	$('#sysuserTree').tree('discloseAll');
       		    	})
        		});
        		
        		$('#submitButton1').on('click', function() {        			
        			var output = '';
        			var dateprice= $("#percentage").val();
        			if(percentage==null||percentage==""){
        				alert("请填写员工提成比！");
        				return;
        			}
					var items = $('#sysuserTree').tree('selectedItems');
					for(var i in items) if (items.hasOwnProperty(i)) {
						var item = items[i];
						if(i>0){
							output += (","+item.additionalParameters['id']);
						}else{
							output += item.additionalParameters['id'];
						}
						
					}
					
					if(output == ''){
        				$.gritter.add({
    		                title: "日期选择",
    		                text: "请展开树勾选日期",
    		                class_name: "gritter-info gritter-center"
    		            });  
        				alert("请选择日期！");
	        			return;
					}
					$.ajax({
    					url : "${contextPath}/sys/alliancepromotionurl/setStaffPercentage",
    					data : {
    						promotionid : $("#promotionid").val(),
    						percentage : $("#percentage").val(),
    						id : output
    					},
    					type : 'POST',
    					dataType : 'json',
    					complete : function(response) {
    						$("#modal-table1").modal("toggle");
    					}
    				});
					//$('#authorityTree').tree('destroy');
				});
        		
        		$("input[name='aup']").click(function(){  
   				 
   				 $("#form2").submit();
   			});
   			//======================关闭
   
   			$("input[name='aclose']").click(function(){
   				$("#aid").css("display","none");
   			});
   			
   			$("#aphone").change( function() {
				  if($(this).val().length==11){
					  loadwxuser($(this).val());
				  }
			});
   			
   			function loadwxuser(phone){
				$.ajax({
 	      		   type:"post",
        		   url:" ${contextPath}/top/wxuser/getUserInfoByPhone",
        		   dataType:"json", 
       		  	   data:{"phone":phone},
       		  	   success:function(data){
       		  		   console.log(data)
       		  		   if(data.success){
       		  			   var user = data.userinfo;
	       		  			$("#aname").val(user.name);
	       		  			$("#astaffid").val(user.id);
	       		  		
       		  		   }else{
       		  				alert("获取会员信息失败，请确认输入手机号码为注册会员手机号！"); 
       		  		   } 
       		  	   }
 				});
			}

//===========================================jieshu ================================================
        		
        		
        		
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
        			form.find('input[name=begin_date]').datepicker({
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
        function addstaff(promotionid){
//         	var id=rowData.id;
// 			var promotionid=rowData.promotionid;
// 			console.log("openuser"+rowData);
			$("#agentpromotionid").val(promotionid);
			
			$("#aid").css("display","block"); 
        }
</script>
