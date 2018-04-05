<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/28
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/echarts.js"></script>
<script type="text/javascript">
    var myChart;
    var option;

    $(document).ready(function () {
        myChart = echarts.init(document.getElementById('main'));
        show2();
    });

    function show() {
        var checkurl = "http://localhost:8080/FundCollect/index/queryAll.do";//验证路径
        $.ajax({
            //async : false,
            cache : false,
            type : 'POST',
            datType : "json",
            contentType : "text/plain",
            url : checkurl,// 请求的action路径
            success : function(data) {
                if (data) {
                    option = JSON.parse(data);
                    myChart.setOption(option);
                }
            }
        });
    }
    
    function show2() {
        var checkurl = "http://localhost:8080/FundCollect/index/risefallrange.do";//验证路径
        $.ajax({
            //async : false,
            cache : false,
            type : 'POST',
            datType : "json",
            contentType : "text/plain",
            url : checkurl,// 请求的action路径
            success : function(data) {
                if (data) {
                    option = JSON.parse(data);
                    myChart.setOption(option);
                }
            }
        });
    }
</script>
<body>
<div id="main" style="width: 99%; height: 630px;"></div>
</body>
</html>
