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
                    <li><a href="./home">仪表盘</a></li>
                    <li class="active" onmousemove="showTheam();">
                        <a href="">专题</a>
                        <ul id="theam" style="display: none;height:50px">
                            <li><a href="./ebuy" style="color: #747474;">电商</a></li>
                            <li><a href="./social">社交</a></li>
                        </ul>
                    </li>
                    <li><a href="http://localhost/discuz/forum.php">论坛</a></li>
                    <!--
                    <li class="scroll"><a href="#portfolio">Portfolio</a></li>
                    <li class="scroll"><a href="#contact">Contact</a></li>
                    <div class="clear"> </div>
                        -->
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

    <ul class="breadcrumb" style="width:90%; margin:1px auto 3px auto; margin-bottom:20px ;">
        <li><a href="./home" class="glyphicons home" style="font-size: large"> 专题</a></li>
        <li><a class="glyphicons home" style="font-size: large">>></a></li>
        <li><a href="" class="glyphicons home" style="font-size: large"> 微博专题</a></li>
        <li class="divider"></li>
        <li></li>
    </ul>


    <script>
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
        var fMap = { 'bar': '柱状图', 'line': '折线图', "pie": "饼图", "scatter": "散点图", "radar": "雷达图", "map": "地图", "wordcloud": "字云" };
        var chartCache = {};
        var count = 0;

        function showChart(chart) {
            $('#' + chart).slideDown();
        }
        function hideChart(chart) {
            $('#' + chart).slideUp("slow", setSize);
        }


        function begin() {

            var leftid = $('#leftid').val();
            var rightid = $('#rightid').val();

            if (leftid != '' && rightid != '') {

                $('#lefti').text(leftid);
                $('#righti').text(rightid);



                var myinfo1 = echarts.init(document.getElementById("InfoBord"));
                myinfo1.showLoading({
                    text: 'whirling',
                    effect: 'whirling',
                    textStyle: {
                        fontSize: 50
                    }
                });
                // var myinfo2 = echarts.init(document.getElementById("InfoBord2"));
                //document.write("123");
                $.post("./social",
                   {
                       'type': 'info',
                       'leftid': leftid,
                       'rightid': rightid
                   },
                   function (data, status) {
                       var DATA = eval('(' + data + ')');

                       $('#leftimg').attr('src', DATA.image_url1);
                       $('#rightimg').attr('src', DATA.image_url2);

                       var option1 = DATA.personinfo;
                       //var option2 = DATA.personinfo2;
                       myinfo1.hideLoading();
                       myinfo1.setOption(DATA.personinfo);
                       //myinfo2.setOption(DATA.personinfo2);

                       //开始下一轮post
                       var myMap1 = echarts.init(document.getElementById("myMap1"));
                       var myMap2 = echarts.init(document.getElementById("myMap2"));
                       myMap1.showLoading({
                           text: '正在采集数据……',
                           effect: 'whirling',
                           textStyle: {
                               fontSize: 40
                           }
                       });
                       myMap2.showLoading({
                           text: '正在采集数据……',
                           effect: 'whirling',
                           textStyle: {
                               fontSize: 40
                           }
                       });

                       $.post("./social",
                       {
                           'type': 'myMap',
                           'uid1': DATA.uid1,
                           'uid2': DATA.uid2,
                           'p1': DATA.province1,
                           'p2': DATA.province2
                       },
                       function (data, status) {
                           var maps = eval('(' + data + ')');
                           var op1 = getMapOption();

                           var myMap1 = echarts.init(document.getElementById("myMap1"));
                           myMap1.hideLoading();
                           op1.series[1].markLine.data = maps['left'].followl;
                           op1.series[2].markLine.data = maps['left'].fansl;
                           op1.series[1].markPoint.data = maps['left'].follow;
                           op1.series[2].markPoint.data = maps['left'].fans;
                           myMap1.setOption(op1);

                           var myMap2 = echarts.init(document.getElementById("myMap2"));
                           myMap2.hideLoading();
                           var op2 = op1;
                           op2.series[1].markLine.data = maps['right'].followl;
                           op2.series[2].markLine.data = maps['right'].fansl;
                           op2.series[1].markPoint.data = maps['right'].follow;
                           op2.series[2].markPoint.data = maps['right'].fans;

                           myMap2.setOption(op2);
                       });

                       var soci = echarts.init(document.getElementById("soci"));
                       soci.showLoading({
                           text: '正在采集数据……',
                           effect: 'whirling',
                           textStyle: {
                               fontSize: 40
                           }
                       });
                       $.post("./social",
                      {
                          'type': 'soci',
                          'uid1': DATA.uid1,
                          'uid2': DATA.uid2,
                          'name1': leftid,
                          'name2': rightid
                      }, function (data, status) {
                          var obj = maps = eval('(' + data + ')');
                          var option = getAsOtion();
                          option.series[0].nodes = obj[0];
                          option.series[0].links = obj[1];
                          //document.write(option);
                          //alert(option);
                          var soci = echarts.init(document.getElementById("soci"));
                          soci.hideLoading();
                          soci.setOption(option);

                      });

                   });
            }
        }

        function getMapOption() {
            var option = {
                backgroundColor: '#1b1b1b',
                color: ['gold', 'aqua', 'lime'],
                title: {
                    text: '',
                    subtext: '',
                    x: 'center',
                    textStyle: {
                        color: '#fff'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function (v) {
                        return v[1].replace(':', ' > ');
                    }
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ['关注', '粉丝'],
                    selectedMode: 'single',
                    selected: {
                        '关注': true,
                        '粉丝': false
                    },
                    textStyle: {
                        color: '#fff'
                    }
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    x: 'right',
                    y: 'center',
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                dataRange: {
                    min: 0,
                    max: 20,
                    calculable: true,
                    color: ['#ff3333', 'orange', 'yellow', 'lime', 'aqua'],
                    textStyle: {
                        color: '#fff'
                    }
                },
                series: [
                    {
                        name: '全国',
                        type: 'map',
                        roam: true,
                        hoverable: false,
                        mapType: 'china',
                        itemStyle: {
                            normal: {
                                borderColor: 'rgba(100,149,237,1)',
                                borderWidth: 0.5,
                                areaStyle: {
                                    color: '#1b1b1b'
                                }
                            }
                        },
                        data: [],
                        markLine: {
                            smooth: true,
                            symbol: ['none', 'circle'],
                            symbolSize: 1,
                            itemStyle: {
                                normal: {
                                    color: '#fff',
                                    borderWidth: 1,
                                    borderColor: 'rgba(30,144,255,0.5)'
                                }
                            },
                            data: [],
                        },
                        geoCoord: {
                            '上海': [121.4648, 31.2891],
                            '北京': [116.28, 39.54],
                            '天津': [117.11, 39.09],
                            '重庆': [106.32, 29.32],
                            '黑龙江': [126.41, 45.45],
                            '吉林': [125.19, 43.52],
                            '辽宁': [123.24, 41.5],
                            '内蒙古': [111.48, 40.49],
                            '河北': [114.28, 38.02],
                            '山西': [112.34, 37.52],
                            '山东': [117, 36.38],
                            '河南': [113.42, 34.48],
                            '陕西': [108.54, 34.16],
                            '甘肃': [103.49, 36.03],
                            '宁夏': [106.16, 38.2],
                            '青海': [101.45, 36.38],
                            '新疆': [87.36, 43.48],
                            '安徽': [117.18, 31.51],
                            '江苏': [118.5, 32.02],
                            '浙江': [120.09, 30.14],
                            '湖南': [113, 28.11],
                            '江西': [115.52, 28.41],
                            '湖北': [114.21, 30.37],
                            '四川': [104.05, 30.39],
                            '贵州': [106.42, 26.35],
                            '福建': [119.18, 26.05],
                            '台湾': [121.31, 25.03],
                            '广东': [113.15, 23.08],
                            '海南': [110.2, 20.02],
                            '广西': [108.2, 22.48],
                            '云南': [102.41, 25],
                            '西藏': [90.08, 29.39],
                            '香港': [114.1, 22.18],
                            '澳门': [113.35, 22.14],
                            '其他': [118.5, 32.02],
                            '海外': [131.4648, 41.2891]
                        }
                    },
                    {
                        name: '关注',
                        type: 'map',
                        mapType: 'china',
                        data: [],
                        markLine: {
                            smooth: true,
                            effect: {
                                show: true,
                                scaleSize: 1,
                                period: 30,
                                color: '#fff',
                                shadowBlur: 10
                            },
                            itemStyle: {
                                normal: {
                                    borderWidth: 1,
                                    lineStyle: {
                                        type: 'solid',
                                        shadowBlur: 10
                                    }
                                }
                            },
                            data: [

                            ]
                        },
                        markPoint: {
                            symbol: 'emptyCircle',
                            symbolSize: function (v) {
                                return 10 + v / 10
                            },
                            effect: {
                                show: true,
                                shadowBlur: 0
                            },
                            itemStyle: {
                                normal: {
                                    label: { show: false }
                                }
                            },
                            data: [

                            ]
                        }
                    },
                    {
                        name: '粉丝',
                        type: 'map',
                        mapType: 'china',
                        data: [],
                        markLine: {
                            smooth: true,
                            effect: {
                                show: true,
                                scaleSize: 1,
                                period: 30,
                                color: '#fff',
                                shadowBlur: 10
                            },
                            itemStyle: {
                                normal: {
                                    borderWidth: 1,
                                    lineStyle: {
                                        type: 'solid',
                                        shadowBlur: 10
                                    }
                                }
                            },
                            data: [

                            ]
                        },
                        markPoint: {
                            symbol: 'emptyCircle',
                            symbolSize: function (v) {
                                return 10 + v / 10
                            },
                            effect: {
                                show: true,
                                shadowBlur: 0
                            },
                            itemStyle: {
                                normal: {
                                    label: { show: false }
                                }
                            },
                            data: [

                            ]
                        }
                    }
                ]
            };
            return option;
        }


        function getAsOtion() {
            var option = {
                title: {
                    text: '微博人际关系',
                    subtext: '数据爬取自新浪微博',
                    x: 'right',
                    y: 'bottom',
                    textStyle: {
                        fontSize: 18,
                        fontWeight: 'bolder',
                        color: 'green'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} : {b}'
                },
                toolbox: {
                    show: true,
                    feature: {
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                legend: {
                    x: 'left',
                    data: ['关注', '粉丝']
                },
                series: [
                    {
                        type: 'force',
                        name: "人际关系",
                        categories: [
                            {
                                name: '你'
                            },
                            {
                                name: '关注'
                            },
                            {
                                name: '粉丝'
                            }
                        ],
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    textStyle: {
                                        color: '#333'
                                    }
                                },
                                nodeStyle: {
                                    brushType: 'both',
                                    strokeColor: 'rgba(255,215,0,0.4)',
                                    lineWidth: 1
                                }
                            },
                            emphasis: {
                                label: {
                                    show: false
                                    // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                                },
                                nodeStyle: {
                                    //r: 30
                                },
                                linkStyle: {}
                            }
                        },
                        useWorker: false,
                        minRadius: 15,
                        maxRadius: 25,
                        gravity: 1.1,
                        scaling: 1.1,
                        linkSymbol: 'arrow'
                    }
                ]
            };
            return option;
        }
        function showTheam() {
            //alert($('#theam').css('display'));
            $('#theam').slideDown();

        }
        function hideTheam() {
            $('#theam').slideUp();
        }
    </script>





    <div id="showBord" style="width: 90%; min-height:1000px; margin: 1px auto 3px auto; margin-bottom: 50px; background-color: #eeeeee; padding: 8px 15px; border-radius: 5px" onmouseover=" hideTheam()">
        <div style="text-align:center;margin-bottom:10px">
            <input id="leftid" type="text" style="margin-bottom:0px;height:29px;" />
            <span>VS</span>
            <input id="rightid" type="text" style="margin-bottom: 0px; height: 29px" />
            <span class="btn btn-large border-only btn-info" style="" onclick="begin()">开始VS</span>
            <br>
        </div>

        <div style="width:100%;height:70px;display:block">
            <div style="width:49%;display:inline-block;">
                <img id="leftimg" src="http://tp1.sinaimg.cn/1587579460/180/5636040175/1" style="width: 68px; height: 68px; float:left;" />
                <span id="lefti" class="larg-text" style="float:left;height:68px;line-height:68px;margin-left:10px">cyhhao 的微博</span>
            </div>
            <div style="width:49%;display:inline-block;">
                <img id="rightimg" src="http://tp1.sinaimg.cn/1587579460/180/5636040175/1" style="width:68px;height:68px;float:right;" />
                <span id="righti" class="larg-text" style="float:right;height:68px;line-height:68px;margin-right:10px">cyhhao 的微博</span>
            </div>
        </div>
        <br>

        <div>
            <div id="line1" style="width:100%;display: inline-block; margin-top:10px;">
                <div class="head blue">
                    <h2 class="mid-wite-text">基本信息</h2>
                </div>
                <div>
                    <div class="data-show" style="width: 100%; min-height: 400px; display: inline-block; margin-top: 10px; vertical-align: top; ">
                        <div id="InfoBord" style="height:400px"></div>
                    </div>
                </div>
            </div>


        </div>

        <div id="line" style="width:100%;display: inline-block; margin-top:30px;">
            <div class="head green">
                <h2 class="mid-wite-text">关系图谱</h2>
            </div>
            <div>
                <div class="data-show" style="width: 100%; min-height: 400px; display: inline-block; margin-top: 10px; vertical-align: top; ">
                    <div id="soci" style="height:400px"></div>
                </div>


            </div>
        </div>


        <div id="line" style="width: 100%; display: inline-block; margin-top: 30px; height: 460px;">
            <div class="head cpurple">
                <h2 class="mid-wite-text">关注及粉丝地域分布</h2>
            </div>
            <div>
                <div class="data-show" style="width: 49.5%; min-height: 460px; display: inline-block; margin-top: 10px; vertical-align: top; ">
                    <div id="myMap1" style="height:400px"></div>
                </div>
                <div class="data-show" style="width: 49.5%; min-height: 460px; display: inline-block; margin-top: 10px; vertical-align: top; float: right">
                    <div id="myMap2" style="height:400px"></div>
                </div>

            </div>
        </div>








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