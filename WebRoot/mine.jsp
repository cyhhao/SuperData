﻿<!DOCTYPE html>
<html>
<head>
    <title>SuperCally | Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


    <link href="css/styling.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/glyphicons.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.min.css" rel="stylesheet" type="text/css" media="all" />

    <link href="css/stylesheet.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/icons.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/slidebar.css" rel="stylesheet" type="text/css" media="all" />

    <link href="css/Mine.css" rel="stylesheet" type="text/css" media="all" />
    <script src="js/jquery.min.js"></script>
    <script src="js/echarts-plain-map.js"></script>
</head>
<body>
    <div id="undefined-sticky-wrapper" class="sticky-wrapper" style="height: 78px;">
        <nav id="nav" class="nav_wrapper">
            <div class="wrap">
                <div class="logo"><img src="images/logo1.png" alt="logo" /></div>
                <a href="#" class="header_toggle">Menu</a>
                <ul class="navigation">
                    <li><a href="./index">首页</a></li>
                    <li class="active"><a href="">仪表盘</a></li>
                    <li><a href="#services">专题</a></li>
                   <li onmousemove="showTheam();">
                        <a href=" ">专题</a>
                        <ul id="theam" style="display: none;height:50px">
                            <li><a href="./ebuy">电商</a></li>
                            <li><a href="./social">社交</a></li>
                        </ul>
                    </li>
                   <li><a href="http://localhost/discuz/forum.php">论坛</a></li>
                </ul>
                <div class="social_icons">
                    <ul>
                        <li style="padding-top:0.8em;font-family:'Segoe UI'"><a href="#"> Super_Cally</a></li>
                        <li><a href=""><img src="img/headico.gif" style="border-radius: 10px" /></a></li>

                    </ul>
                </div>
                <div class="clear"> </div>
            </div>
        </nav>
    </div>
    <div class="sidebar btn-info" style="width: 15%; margin-left: 4%; margin-top: 50px; position: fixed">

        <div class="larg-wite-text center" style="text-shadow: 1px 1px 2px #000; padding-top: 20px">选择挖掘类型</div>
        <!-- Navigation menu list -->
        <ul id="leftside" class="list-unstyled list">

            <li onclick="selectIt(this,'cluster')"><a class="anchorLink larg-wite-text"><i class="icon-home scolor"></i> 聚类挖掘</a></li>
            <li onclick="selectIt(this,'apriori')"><a class="anchorLink larg-wite-text"><i class="icon-user scolor"></i> 关联规则</a></li>
            <li onclick="selectIt(this,'wordcloud')"><a class="anchorLink larg-wite-text"><i class="icon-retweet scolor"></i> 字云</a></li>
            <!--    <li onclick="selectIt(this,'dectree')"><a class="anchorLink larg-wite-text"><i class="icon-road scolor"></i> 决策树</a></li>

                 Social media links -->

        </ul>
        <script src="js/cloud/Un.js"></script>
        <script src="js/cloud/unicode.js"></script>
        <script src="js/cloud/d3.layout.cloud.js"></script>
        <script src="js/cloud/cloud.js"></script>

    </div>
    <ul class="breadcrumb" style="width:76%; margin:1px 20px 3px auto; margin-bottom:20px ;">
        <li><a href="./home" class="glyphicons home" style="font-size: large"> 仪表盘</a></li>
        <li><a class="glyphicons home" style="font-size: large">>></a></li>
        <li><a href="" class="glyphicons home" style="font-size: large"> 数据挖掘</a></li>
        <li class="divider"></li>
        <li>推荐图表</li>
    </ul>

    <script>
        /*
        // 路径配置
        require.config({
            paths: {
                'echarts': 'http://echarts.baidu.com/build/echarts',
                'echarts/chart/bar': 'http://echarts.baidu.com/build/echarts'
            }
        });*/
        function getRequest() {
            var url = location.search;
            var theRequest = new Object();
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
                }
            }
            return theRequest;
        }
        function getId() {
            var requestObj = new Object();
            requestObj = getRequest();

            var id = requestObj['id'];
            return id;


        }

        var colorMap = ["dblue", "blue", "yellow", "green", "red", "purple", "orange", "cblue", "cyellow", "cgreen", "cred", "cdblue", "cpurple", "corange"];
        var fMap = { 'bar': '柱状图', 'line': '折线图', "pie": "饼图", "scatter": "散点图", "map": "地图", "wordcloud": "字云", "apriori": "关联图" };
        var mineMap = { 'cluster': '聚类挖掘', 'apriori': '关联规则', "wordcloud": "字云", "dectree": "决策树" };
        var chartCache = {};
        var count = 0;
        function addChart(chart) {
            var color_css = colorMap[Math.floor(Math.random() * colorMap.length)];
            var html =
                   ' <div id="' + chart + '" style="width:100%;display: inline-block; margin-top:10px;">' +
                       ' <div class="head ' + color_css + '">' +
                           ' <h2 class="mid-wite-text">' + fMap[chart] + '</h2>' +
                           ' <ul class="buttons">' +
                              '  <li><a href="#" onclick=""><div class="icon"><span class="ico-info"></span></div></a></li>' +
                              ' <li><a href="#" class="ublock"><div class="icon"><span class="ico-undo"></span></div></a></li>' +
                              '  <li><a href="#" class="cblock"><div class="icon"><span class="ico-remove"></span></div></a></li>' +
                           ' </ul>' +
                       ' </div>' +
                       ' <div class="data-show" style="min-height:400px">' +

                           ' <div id="' + chart + 'Bord" style="height:400px"></div>' +

                       ' </div>' +
                   ' </div>';

            $('#showBord').append(html);

        }
        function showChart(chart) {
            $('#' + chart).slideDown();
        }
        function hideChart(chart) {
            //$('#' + chart).slideUp("slow", setSize);
            $('#' + chart).slideUp();
        }
        var selectLi;

        function selectIt(it, type) {

            if (type == 'cluster') {
                if (it.style['background-color'] != '') {
                    //reload
                    loadChart(type);
                }
                else {
                    it.style['background-color'] = '#4666CA';
                    //$("#showBord").remove();
                    addChart('pie');
                    addChart('scatter');

                    loadChart(type);

                }
            }
            else if (type == 'wordcloud') {
                if (it.style['background-color'] != '') {
                    $("#word0").remove();
                    $("#word0").remove();
                    loadChart(type);
                }
                else {
                    it.style['background-color'] = '#4666CA';
                    $("#word0").remove();
                    $("#word0").remove();

                    addChart('wordcloud');
                    loadChart(type);

                }
            }
            else if (type == 'apriori') {
                if (it.style['background-color'] != '') {

                }
                else {
                    it.style['background-color'] = '#4666CA';
                    addChart('apriori');
                    loadChart(type);
                }
            }




            //loadChart(chart);

        }
        function setSize() {
            var Allshow = $('#showBord').children();
            var cnt = 0;
            var notClose = new Array();
            for (var i = 0; i < Allshow.length; i++) //alert(i);
            {
                var item = Allshow[i];
                if (item.style.display != 'none') {
                    notClose[cnt] = item;
                    cnt++;
                }
            }
            if (count == 1) {
                notClose[0].style.width = '100%';
                loadChart(notClose[0].id);
            }
            else if (count == 2) {
                notClose[0].style.width = '100%';
                notClose[1].style.width = '100%';
                loadChart(notClose[0].id);
                loadChart(notClose[1].id);
            }
            else if (count == 3) {
                notClose[0].style.width = '49%';
                notClose[1].style.width = '49%';
                notClose[2].style.width = '100%';
                loadChart(notClose[0].id);
                loadChart(notClose[1].id);
                loadChart(notClose[2].id);
            }
            else if (count == 4) {
                notClose[0].style.width = '49%';
                notClose[1].style.width = '49%';
                notClose[2].style.width = '49%';
                notClose[3].style.width = '49%';
                loadChart(notClose[0].id);
                loadChart(notClose[1].id);
                loadChart(notClose[2].id);
                loadChart(notClose[3].id);
            }
            else if (count == 5) {
                notClose[0].style.width = '49%';
                notClose[1].style.width = '49%';
                notClose[2].style.width = '100%';
                notClose[3].style.width = '49%';
                notClose[4].style.width = '49%';
                loadChart(notClose[0].id);
                loadChart(notClose[1].id);
                loadChart(notClose[2].id);
                loadChart(notClose[3].id);
                loadChart(notClose[4].id);
            }
        }
        function loadChart(type) {
            var file = getId();
            if (type == 'cluster') {
                var myChart2 = echarts.init(document.getElementById('pie' + "Bord"));
                
                myChart2.showLoading({
                    text: '正在挖掘……',
                    effect: 'whirling',
                    textStyle: {
                        fontSize: 40
                    }
                });

                myChart2 = echarts.init(document.getElementById('scatter' + "Bord"));
                myChart2.showLoading({
                    text: '正在挖掘……',
                    effect: 'whirling',
                    textStyle: {
                        fontSize: 40
                    }
                });
                $.post("./mine",
                {
                    'filename': file,
                    'mine': type
                },
                function (data, status) {

                    var option = eval('(' + data + ')');

                    var myChart1 = echarts.init(document.getElementById('pie' + "Bord"));
                    myChart1.hideLoading();
                    myChart1.setOption(option[0]);

                    myChart1 = echarts.init(document.getElementById('scatter' + "Bord"));
                    myChart1.hideLoading();
                    myChart1.setOption(option[1]);
                }
                );
            }
            else if (type == 'wordcloud') {
					/*
                var myChart2 = echarts.init(document.getElementById('wordcloud' + "Bord"));

                myChart2.showLoading({
                    text: '正在挖掘……',
                    effect: 'whirling',
                    textStyle: {
                        fontSize: 40
                    }
                });*/
                $.post("./mine",
                {
                    'filename': file,
                    'mine': type
                },
                function (data, status) {
                    var option = eval('(' + data + ')');
                    $("#word0").remove();
                    $("#word0").remove();
                    $("canvas").remove();
                    /*var myChart1 = echarts.init(document.getElementById('wordcloud' + "Bord"));
                    myChart1.hideLoading();*/
                    getrequst(option, 'wordcloud' + "Bord");
                });

            }
            else if (type == 'apriori') {
                var myChart2 = echarts.init(document.getElementById('apriori' + "Bord"));
                myChart2.showLoading({
                    text: 'Loading',
                    effect: 'whirling',
                    textStyle: {
                        fontSize: 50
                    }
                });
                $.post("./mine",
                {
                    'filename': file,
                    'mine': type
                },
                function (data, status) {
                    var option = eval('(' + data + ')');
                    option.legend.itemGap = document.getElementById('apriori' + "Bord").offsetWidth / (option.legend.data.length * 2);
                    option.legend.y = 55;

                    var myChart1 = echarts.init(document.getElementById('apriori' + "Bord"));
                    myChart1.hideLoading();
                    myChart1.setOption(option);

                });
            }
        }
        function showTheam() {
            //alert($('#theam').css('display'));
            $('#theam').slideDown();

        }
        function hideTheam() {
            $('#theam').slideUp();
        }
    </script>

    <script type="text/javascript">

    </script>

    <div id="showBord" style="width: 75%; min-height:1000px; margin: 1px 20px 3px auto; margin-bottom: 50px; background-color: #eeeeee; padding: 8px 15px; border-radius: 5px"  onmouseover=" hideTheam()">
        <!--<div class="block" style="width:50%">
                <div class="head orange">
                    <h2 class="mid-wite-text">柱状图</h2>
                    <ul class="buttons">
                        <li><a href="#" onclick="source('table_main'); return false;"><div class="icon"><span class="ico-info"></span></div></a></li>
                        <li><a href="#" class="ublock"><div class="icon"><span class="ico-undo"></span></div></a></li>
                        <li><a href="#" class="cblock"><div class="icon"><span class="ico-remove"></span></div></a></li>
                    </ul>
                </div>
                <div class="data-show" style="min-height:400px">
                    <div id="main" style="height:400px"></div>

                </div>
            </div>
               -->
    </div>

    <!--底部-->
    <div class="footer" style="margin-bottom:0px">
        <div class="wrap">
            <div class="footer-left">
                <div class="copy">
                    <p>Copyright &copy; 2014.SuperCally All rights reserved.</p>
                </div>
            </div>

            <div class="clear"> </div>
        </div>
        <!--底部-->
        <!-- scroll_top_btn -->

        <script type="text/javascript">
            $(document).ready(function () {

                var defaults = {
                    containerID: 'toTop', // fading element id
                    containerHoverID: 'toTopHover', // fading element hover id
                    scrollSpeed: 1200,
                    easingType: 'linear'
                };


                $().UItoTop({ easingType: 'easeOutQuart' });

            });
        </script>
        <script type="text/javascript">
            jQuery(document).ready(function ($) {
                $(".scroll").click(function (event) {
                    event.preventDefault();
                    $('html,body').animate({ scrollTop: $(this.hash).offset().top }, 1200);
                });
            });
        </script>
        <a href="#" id="toTop" style="display: block;"><span id="toTopHover" style="opacity: 1;"></span></a>
    </div>



    <script type="text/javascript" src="js/move-top.js"></script>
    <script type="text/javascript" src="js/easing.js"></script>

</body>
</html>