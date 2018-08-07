<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/static/assets/css/zTreeStyle/zTreeStyle.css" />
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
<div id="modal-table" class="modal fade" tabindex="-1" data-backdrop="static">
	<div class="modal-dialog">
		<form id="informationForm">
			<div class="modal-content">
				<div class="modal-header no-padding">
					<div class="table-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							<span class="white">&times;</span>
						</button>
						设置超链接
					</div>
				</div>
				<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
					<div id="modal-tip" class="red clearfix"></div>
					<input id="careertypeid" type="hidden" />
					<div class="widget-box widget-color-blue2">
						<div class="widget-body">
							<div class="widget-main padding-8">
								类别：<span id="careertypename"></span><br />
								链接：<input type="text"  id="data_url"  maxlength="128" style="width: 450px;"/>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer no-margin-top">
					<div class="text-center">
						<button id="submitButton" type="submit" class="btn btn-app btn-success btn-xs">
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
<div class="content_wrap">
	<div class="zTreeDemoBackground left" style="float: left;">
		<ul id="treeDemo" class="ztree"></ul>
<!-- 		<button onclick="savetree();">保存</button> -->
	</div>
<!-- 	<div class="right" style="float: right;"> -->
<!-- 		<p> -->
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp;[ <a id="addParent" href="#" title="增加父分类" onclick="return false;">增加一级分类</a> ] -->
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp;[ <a id="addLeaf" href="#" title="增加叶子分类" onclick="return false;">增加叶子分类</a> ] -->
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp;[ <a id="edit" href="#" title="编辑名称" onclick="return false;">编辑名称</a> ]<br> -->
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp;[ <a id="remove" href="#" title="删除分类" onclick="return false;">删除分类</a> ] -->
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp;[ <a id="clearChildren" href="#" title="清空子分类" onclick="return false;">清空子分类</a> ]<br> -->
<!-- 		</p> -->
<!-- 	</div> -->
</div>


<%-- <script type="text/javascript"src="${contextPath}/static/assets/js/jquery-1.4.4.min.js"></script>  --%>
<script type="text/javascript"src="${contextPath}/static/assets/js/jquery.ztree.core.js"></script> 
<script type="text/javascript"src="${contextPath}/static/assets/js/jquery.ztree.excheck.js"></script> 
<script type="text/javascript"src="${contextPath}/static/assets/js/jquery.ztree.exedit.js"></script> 

<script type="text/javascript">
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			
			callback: {
				//onClick: zTreeOnClick,
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename
			}
		};
		
// 		function zTreeOnClick(event, treeId, treeNode) {
// 			$("#modal-table").modal("toggle");
// 			$("#careertypeid").val(treeNode.id);
// 			$("#data_url").val(treeNode.url);
// 			$("#careertypename").html(treeNode.name);
// 		};

		var zNodes =new Array();
		
		var log, className = "dark";
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			//showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			setTimeout(function() {
				zTree.editName(treeNode);
			}, 0);
			return false;
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			//showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			var confirmmsg = "确认删除分类 " + treeNode.name + " 吗？";
			var isparent = treeNode.isParent;
			if(isparent){
				confirmmsg = "此分类包含子分类，确认删除分类" + treeNode.name+"和它的子分类吗？";
			}
			return confirm(confirmmsg);
		}
		function onRemove(e, treeId, treeNode) {
			var isparent = treeNode.isParent;
			$.ajax({
				url : "${contextPath}/sys/provincesCity/deleteNodeOfTree",
				type : 'POST',
				data : {"nodeid": treeNode.id,"isparent": isparent},
				dataType : 'json',
				complete : function(response) {
					
				}
			});
			//showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			//showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
			if (newName.length == 0) {
				setTimeout(function() {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.cancelEditName();
					alert("分类名称不能为空.");
				}, 0);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
			$.ajax({
				url : "${contextPath}/sys/provincesCity/renameNodeOfTree",
				type : 'POST',
				data : {"treeNode" : JSON.stringify(treeNode)},
				dataType : 'json',
				complete : function(response) {
					
				}
			});
			//showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}
		function showRemoveBtn(treeId, treeNode) {
			return !(treeNode.level==0);
		}
		function showRenameBtn(treeId, treeNode) {
			return !treeNode.isLastNode;
		}
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			if(treeNode.id<10000){
				console.log("treeNode: "+treeNode);
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
				var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
					+ "' title='add node' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn = $("#addBtn_"+treeNode.tId);
				if (btn) btn.bind("click", function(){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					 
					$.ajax({
						url : "${contextPath}/sys/provincesCity/addChildNodeOfTree",
						type : 'POST',
						data : {"pId" : treeNode.id,"name": "new node"},
						dataType : 'json',
						success : function(data) {
							 var newnode = {
									  id: data.careerType.id,
									  pId: treeNode.id,
									  name: data.careerType.name
							       }
							var treenode = zTree.addNodes(treeNode, newnode);
							  console.log(newnode);
							zTree.editName(treenode[0]);
						}
					});
					return false;
				});
			}
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		
		function add(e) {
			$.ajax({
				url : "${contextPath}/sys/provincesCity/getMaxRootId",
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					var maxrootid = data.maxid;
					var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
					isParent = e.data.isParent,
					nodes = zTree.getSelectedNodes(),
					treeNode = nodes[0];
					var newnode = {
							  id: maxrootid,
							  pId:0,
							  isParent:isParent,
							  name:"new node" + (newCount++)
					       }
		// 			if (treeNode) {
		// 				treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, isParent:isParent, name:"new node" + (newCount++)});
		// 			} else {
						//treeNode = zTree.addNodes(null, {id:(100 + newCount), pId:0, isParent:isParent, name:"new node" + (newCount++)});
						treeNode = zTree.addNodes(null, newnode);
						$.ajax({
							url : "${contextPath}/sys/provincesCity/addNodeOfTree",
							type : 'POST',
							data : {"treeNode" : JSON.stringify(newnode)},
							dataType : 'json',
							complete : function(response) {
								if (treeNode) {
									zTree.editName(treeNode[0]);
								} else {
									alert("叶子分类被锁定，无法增加子分类");
								}
							}
						});
					//}
					
				}
			});
		};
// 		function addleaf(e) {
// 			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
// 			isParent = e.data.isParent,
// 			nodes = zTree.getSelectedNodes(),
// 			treeNode = nodes[0];
// 				treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, isParent:isParent, name:"new node" + (newCount++)});
// 			console.log("add treeNode: "+treeNode);
// 				if (treeNode) {
// 				zTree.editName(treeNode[0]);
// 			} else {
// 				alert("叶子分类被锁定，无法增加子分类");
// 			}
// 		};
		function edit() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes(),
			treeNode = nodes[0];
			if (nodes.length == 0) {
				alert("请先选择一个分类");
				return;
			}
			zTree.editName(treeNode);
		};
		function remove(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes(),
			treeNode = nodes[0];
			if (nodes.length == 0) {
				alert("请先选择一个分类");
				return;
			}
			var callbackFlag = $("#callbackTrigger").attr("checked");
			zTree.removeNode(treeNode, callbackFlag);
		};
		function clearChildren(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes(),
			treeNode = nodes[0];
			if (nodes.length == 0 || !nodes[0].isParent) {
				alert("请先选择一个父分类");
				return;
			}
			zTree.removeChildNodes(treeNode);
		};
		
		$(document).ready(function(){
			$.ajax({
				url : "${contextPath}/sys/provincesCity/getcareertypetree",
				type : 'POST',
				dataType : 'json',
				complete : function(response) {
						var returninfo = eval("(" + response.responseText + ")");
						if (returninfo.status == "OK") {
							$.fn.zTree.init($("#treeDemo"), setting, returninfo.data);
						}
				}
			});
			
			$("#selectAll").bind("click", selectAll);
			$("#addParent").bind("click", {isParent:true}, add);
			//$("#addLeaf").bind("click", {isParent:false}, addleaf);
			$("#edit").bind("click", edit);
			$("#remove").bind("click", remove);
			$("#clearChildren").bind("click", clearChildren);
			
			$('#submitButton').on('click', function() {
				var careertypeid = $("#careertypeid").val();
				var dataurl = $("#data_url").val();
				$.ajax({
					url : "${contextPath}/sys/provincesCity/saveCareerTypeUrl",
					data : {
						careertypeid : careertypeid,
						dataurl : dataurl
					},
					type : 'POST',
					dataType : 'json',
					complete : function(response) {
						$("#modal-table").modal("toggle");
					}
				});
			});
		});
		
		
	</script>
<!-- page specific plugin scripts -->
<script type="text/javascript">
		var scripts = [ null, "${contextPath}/static/assets/js/jqGrid/jquery.jqGrid.js", "${contextPath}/static/assets/js/jqGrid/i18n/grid.locale-cn.js", "${contextPath}/static/assets/js/fuelux/fuelux.tree.js", "${contextPath}/static/assets/js/jquery.gritter.js", null ]
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        	// inline scripts related to this page
        	jQuery(function($) {
        	
        		
        	});
        });
		
// 		function savetree(){
// 			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
// 			console.log("ztree: "+zTree.getNodes());
// 			$.ajax({
// 				url : "${contextPath}/sys/provincesCity/savetree",
// 				data : {
// 					tree: JSON.stringify(zTree.getNodes())
// 				},
// 				type : 'POST',
// 				dataType : 'json',
// 				complete : function(response) {
// 				}
// 			});
// 		}
</script>
