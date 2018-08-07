<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/fe/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="${contextPath}/fe/assets/css/admin.css">
<link rel="stylesheet" href="${contextPath}/fe/assets/css/app.css">
<script src="../assets/js/echarts.min.js"></script>
<style>

    .tpl-page-header-fixed {
        margin-top: 150px;
    }
    .titleBox {
        float:left;
        position:relative;
        left:35%;
        height: 130px;
        line-height: 130px;
    }
    .titleBox p {
        float:left;
        position:relative;
        right:50%;
        color: black;
    }
    .tpl-portlet-components .portlet-title {
        border-bottom: none;
        margin-top: 20px;
    }
    .am-form .checkbox-style {
        width: 17px;
        height: 17px;
    }
</style>

<div class="row">
    <div class="tpl-page-container tpl-page-header-fixed">
        <div class="tpl-portlet-components" style="margin-top: -20px;">
            <!-- 图片上传按钮 -->
            <div class="portlet-title">
                <div class="caption">
                    <span style="font-size: 24px;margin-left: 20px;">图片</span>
                    <span>（共1323条）</span>
                </div>
                <div class="tpl-portlet-input tpl-fz-ml">
                    <div class="portlet-input input-small input-inline">
                        <div class="input-icon right">
                            <button type="file" class="am-btn am-btn-primary" style="width: 100px;border-radius: 5px;">上传</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 图片表格信息 -->
            <div class="am-g" style="margin-top: 20px;">
                <div class="am-u-sm-12">
                    <form class="am-form">
                        <table class="am-table table-main">
                            <thead>
                            <tr style="font-size: 18px">
                                <th class="table-check" style="width: 50px;"><input type="checkbox" class="tpl-table-fz-check checkbox-style"></th>
                                <th class="table-title">名称</th>
                                <th class="table-title">创建时间</th>
                                <th class="table-set">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style="height: 120px;line-height: 120px;"><input type="checkbox" class="checkbox-style"></td>
                                <td><img src="../assets/img/product01-2.jpg" style="width: 120px;height: 120px;" /></td>
                                <td style="height: 120px;line-height: 120px;">2018.07.08 11:57</td>
                                <td style="height: 120px;line-height: 120px;">
                                    <div style="font-size: 20px;cursor: pointer;">
                                        <span class="am-icon-trash-o"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td style="height: 120px;line-height: 120px;"><input type="checkbox" class="checkbox-style"></td>
                                <td><img src="../assets/img/alipay_flow.jpg" style="width: 120px;height: 120px;" /></td>
                                <td style="height: 120px;line-height: 120px;">2018.07.08 11:57</td>
                                <td style="height: 120px;line-height: 120px;">
                                    <div style="font-size: 20px;cursor: pointer;">
                                        <span class="am-icon-trash-o"></span>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="am-cf">
                            <div class="am-fr">
                                <ul class="am-pagination tpl-pagination">
                                    <li class="am-disabled"><a href="#">«</a></li>
                                    <li class="am-active"><a href="#">1</a></li>
                                    <li><a href="#">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">4</a></li>
                                    <li><a href="#">5</a></li>
                                    <li><a href="#">»</a></li>
                                </ul>
                            </div>
                        </div>
                        <hr>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${contextPath}/fe/assets/js/jquery.min.js"></script>
<script src="${contextPath}/fe/assets/js/amazeui.min.js"></script>
<script src="${contextPath}/fe/assets/js/toast.js"></script>

<script>
    $(function() {
        onLoad();
        bindEvent();
    });
    function onLoad() {
        initPage();
    }
    function initPage() {
        $.ajax({
            url : 'http://tomcat.empress.com/wechat/manager/getWechatMaterialImage',
            type : 'POST',
            dataType:'json',
            success : function(res) {
                console.log(res);
            },
            error : function(err) {
                console.log(err);
            }
        });
    }
    function bindEvent() {

    }
</script>
