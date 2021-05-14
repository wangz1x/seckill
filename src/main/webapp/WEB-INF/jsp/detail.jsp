<%--
  Created by IntelliJ IDEA.
  User: wangzx
  Date: 2021/5/14
  Time: 下午3:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time"></span>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>
<div id="killphoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话:
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <label for="killPhoneKey"></label>
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="请填写手机号" class="form-control"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
<%--<script src="https://cdn.bootcdn.net/ajax/libs/jquery-countdown/2.1.0/js/jquery.countdown.js"></script>--%>
<script src="https://cdn.rawgit.com/hilios/jQuery.countdown/2.2.0/dist/jquery.countdown.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/seckill.js"></script>
<script type="text/javascript">
    $(function () {
        seckill.detail.init({
            seckillId: ${seckill.seckillId},
            startTime: ${seckill.startTime.time},
            endTime: ${seckill.endTime.time}
        })
    })
</script>
</body>
</html>
