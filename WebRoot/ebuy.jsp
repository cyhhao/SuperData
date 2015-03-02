<!DOCTYPE html>
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
                            <li class="active"><a href="./ebuy">电商</a></li>
                            <li><a href="./social" style="color: #747474;">社交</a></li>
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
        <li><a href="" class="glyphicons home" style="font-size: large"> 购物专题</a></li>
        <li class="divider"></li>
        <li></li>
    </ul>


    <script>

        function showTheam() {
            //alert($('#theam').css('display'));
            $('#theam').slideDown();

        }
        function hideTheam() {
            $('#theam').slideUp();
        }

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

        function loadCharts() {
            var filel = document.getElementById("filename").value;

            var files = filel.split("\\");
            var file = files[files.length - 1]

            var myChart2 = echarts.init(document.getElementById("lineBord"));
            myChart2.showLoading({
                text: '正在挖掘中……',
                effect: 'whirling',
                textStyle: {
                    fontSize: 40
                }
            });

            myChart2 = echarts.init(document.getElementById("MapBord"));
            myChart2.showLoading({
                text: '正在挖掘中……',
                effect: 'whirling',
                textStyle: {
                    fontSize: 40
                }
            });

            myChart2 = echarts.init(document.getElementById("AssChart"));
            myChart2.showLoading({
                text: '正在挖掘中……',
                effect: 'whirling',
                textStyle: {
                    fontSize: 40
                }
            });


            $.post("./ebuy",
                    {
                        'file': file
                    },
                    function (data, status) {
                        var option = eval('(' + data + ')');
                        var myChart1 = echarts.init(document.getElementById("lineBord"));
                        myChart1.hideLoading();
                        myChart1.setOption(option[0]);



                        myChart1 = echarts.init(document.getElementById("MapBord"));
                        myChart1.hideLoading();
                        myChart1.setOption(option[1]);

                        myChart1 = echarts.init(document.getElementById("AssChart"));
                        myChart1.hideLoading();
                        var op = option[2];
                        op.legend.itemGap = document.getElementById("AssChart").offsetWidth / (op.legend.data.length * 2);
                        op.legend.y = 55;
                        myChart1.setOption(op);

                        disc(option);
                    }
            );
        }
        //var myChart = echarts.init(document.getElementById("MapBord"));
        //var opt = { "backgroundColor": "#1b1b1b", "title": { "text": "", "x": "center", "textStyle": { "color": "#fff" } }, "legend": { "orient": "VERTICAL", "x": "left", "textStyle": { "color": "WHITE" }, "selectedMode": "SINGLE", "data": [{ "name": "传播奶酪" }, { "name": "男性化妆品" }, { "name": "皮肤护理" }, { "name": "茶" }, { "name": "灯泡" }] }, "toolbox": { "show": true, "orient": "VERTICAL", "x": "right", "y": "center", "color": [], "feature": { "mark": { "show": true, "title": {} }, "dataView": { "show": true, "readOnly": false, "lang": ["Data View", "close", "refresh"] }, "restore": { "show": true }, "saveAsImage": { "show": true, "lang": ["点击保存"] } } }, "series": [{ "name": "传播奶酪", "type": "map", "mapType": "china", "itemStyle": { "normal": { "borderColor": "rgba(100,149,237,1)", "borderWidth": 1.5, "areaStyle": { "color": "#1b1b1b" } } }, "data": [], "markPoint": { "symbolSize": 2, "large": true, "effect": { "show": true }, "data": [{ "name": "济宁", "geoCoord": [113.0415788231273, 32.6362985859913] }, { "name": "济宁", "geoCoord": [113.82402767103322, 33.818860706167214] }, { "name": "石家庄", "geoCoord": [112.36417728585997, 35.80759304984552] }, { "name": "常熟", "geoCoord": [118.9672239131541, 30.763864843550298] }, { "name": "盘锦", "geoCoord": [118.65050520731633, 40.30119944636698] }, { "name": "盘锦", "geoCoord": [119.54534684457467, 39.04056706607727] }, { "name": "金昌", "geoCoord": [97.7194727870829, 37.463524182093] }, { "name": "金昌", "geoCoord": [98.08321294014029, 35.774382207089936] }, { "name": "三门峡", "geoCoord": [110.5340176289643, 33.64967973373062] }, { "name": "锦州", "geoCoord": [119.60537690718564, 40.574516118925914] }, { "name": "常德", "geoCoord": [108.7698147880635, 27.258631804479542] }, { "name": "常德", "geoCoord": [110.19977107664933, 28.16184830186385] }, { "name": "株洲", "geoCoord": [112.49007782511494, 25.056060073039344] }, { "name": "呼和浩特", "geoCoord": [109.07113239589414, 38.02391763310571] }, { "name": "呼和浩特", "geoCoord": [111.00839073689778, 39.59822752409077] }, { "name": "呼和浩特", "geoCoord": [109.30332323794408, 40.42188927385488] }, { "name": "呼和浩特", "geoCoord": [108.1851174025067, 38.90856388473671] }, { "name": "嘉峪关", "geoCoord": [94.4267884051348, 36.87154504879346] }, { "name": "嘉峪关", "geoCoord": [96.1643519538785, 39.66904080052405] }, { "name": "嘉峪关", "geoCoord": [93.32996659370197, 39.42129701362297] }, { "name": "菏泽", "geoCoord": [110.78371049209673, 33.44238806237449] }, { "name": "潍坊", "geoCoord": [118.20878280730979, 34.80370968692064] }, { "name": "哈尔滨", "geoCoord": [123.92858282153165, 44.21615407274827] }, { "name": "咸阳", "geoCoord": [106.24478435799263, 33.32231842810372] }, { "name": "银川", "geoCoord": [105.09734871356042, 37.54781432583278] }, { "name": "吴江", "geoCoord": [119.33400893727357, 29.523451686998044] }, { "name": "沈阳", "geoCoord": [121.305736394642, 39.078953309746964] }, { "name": "沈阳", "geoCoord": [122.02894897042866, 41.0919370262395] }, { "name": "衢州", "geoCoord": [114.8784346162272, 28.96474521892965] }, { "name": "平顶山", "geoCoord": [110.70062165926056, 32.60853260091643] }, { "name": "平顶山", "geoCoord": [110.08086040697894, 31.684824809171165] }, { "name": "平顶山", "geoCoord": [112.13043864054391, 33.5573765893434] }, { "name": "枣庄", "geoCoord": [113.69316532056544, 34.31507467598882] }, { "name": "枣庄", "geoCoord": [113.74590637504342, 34.098939229222026] }, { "name": "蓬莱", "geoCoord": [116.56523333478435, 35.61771410662219] }, { "name": "蓬莱", "geoCoord": [115.83048907147266, 36.337443925961196] }, { "name": "蓬莱", "geoCoord": [116.54360908547069, 36.15679944490946] }, { "name": "宁波", "geoCoord": [118.11386176842525, 27.91332429171249] }, { "name": "宁波", "geoCoord": [119.71156503275924, 27.573473501037924] }, { "name": "溧阳", "geoCoord": [119.12343174476634, 29.44413710215237] }, { "name": "葫芦岛", "geoCoord": [116.76947373150198, 38.45482941958863] }, { "name": "廊坊", "geoCoord": [114.82140559574002, 37.87145116705263] }, { "name": "莱芜", "geoCoord": [115.29496655370124, 35.11156545043931] }, { "name": "莱芜", "geoCoord": [113.91382478845323, 33.72658525871376] }, { "name": "大同", "geoCoord": [109.09298125885235, 38.18003937040601] }, { "name": "阳泉", "geoCoord": [109.94889383317673, 36.41466813679349] }, { "name": "温州", "geoCoord": [120.57288325774785, 25.967646398908933] }, { "name": "邢台", "geoCoord": [110.16157179623829, 35.76434651802565] }, { "name": "莱州", "geoCoord": [119.38143187073658, 34.314576656235104] }, { "name": "江阴", "geoCoord": [115.35979785730255, 31.649568541479386] }, { "name": "江阴", "geoCoord": [118.19633364776982, 30.10468884785205] }, { "name": "福州", "geoCoord": [116.1248684220277, 25.28021971206442] }, { "name": "宜昌", "geoCoord": [110.28589552329262, 29.090143233704765] }, { "name": "宜昌", "geoCoord": [107.14438304993405, 29.509993558276076] }, { "name": "诸暨", "geoCoord": [117.63479858400622, 26.750297193536838] }, { "name": "宝鸡", "geoCoord": [104.67082370789296, 34.15272681424101] }, { "name": "苏州", "geoCoord": [118.24485929301458, 29.256084256602694] }, { "name": "文登", "geoCoord": [118.15223234889226, 34.742533404307395] }, { "name": "郑州", "geoCoord": [111.41710327723271, 34.25787310995563] }, { "name": "德州", "geoCoord": [115.06359719900486, 35.6284218736646] }, { "name": "德州", "geoCoord": [115.5031591554443, 37.07225315646174] }, { "name": "沧州", "geoCoord": [113.10845936513647, 36.81068284811913] }, { "name": "盐城", "geoCoord": [117.0006374213598, 30.589288167122973] }, { "name": "盐城", "geoCoord": [119.40989817035332, 32.77418317802855] }, { "name": "盐城", "geoCoord": [120.08350672690975, 31.552267921903578] }, { "name": "合肥", "geoCoord": [116.7584532094547, 29.602808688652186] }, { "name": "马鞍山", "geoCoord": [115.4212566470635, 29.88671850272729] }, { "name": "湘潭", "geoCoord": [109.07168827955327, 27.08964397433152] }, { "name": "湘潭", "geoCoord": [109.35215849436025, 26.801406356022056] }, { "name": "邯郸", "geoCoord": [111.34600117657513, 35.29908376539085] }, { "name": "济南", "geoCoord": [114.96620786337738, 34.46129805260978] }, { "name": "富阳", "geoCoord": [116.43593363527648, 29.429919121831123] }, { "name": "富阳", "geoCoord": [118.06235804783317, 28.000915007028905] }, { "name": "青岛", "geoCoord": [120.24963020053049, 33.88254455557128] }, { "name": "延安", "geoCoord": [107.8624246863337, 35.60624772843748] }, { "name": "长治", "geoCoord": [110.9912479308858, 33.58878320760894] }, { "name": "宿迁", "geoCoord": [114.15056864268128, 32.204371931423786] }, { "name": "泸州", "geoCoord": [105.33201319188699, 27.089429045531563] }, { "name": "泰州", "geoCoord": [116.6925335471788, 31.07213514814618] }, { "name": "上海", "geoCoord": [117.0312725955589, 30.612456672129674] }, { "name": "莱西", "geoCoord": [117.84284084709807, 36.71875797908297] }, { "name": "威海", "geoCoord": [120.88728321957895, 35.05395016630068] }, { "name": "威海", "geoCoord": [119.00091783409012, 36.7019224639824] }, { "name": "西宁", "geoCoord": [97.63463505368411, 36.34415576574589] }, { "name": "遵义", "geoCoord": [105.11865718063119, 26.232894765357063] }, { "name": "荣成", "geoCoord": [120.79109788541813, 36.05266168167353] }, { "name": "张家口", "geoCoord": [112.04218017563272, 40.32345772073426] }, { "name": "昆明", "geoCoord": [100.10133167811499, 24.18956357643518] }, { "name": "胶南", "geoCoord": [117.61959928153232, 34.245903818406276] }, { "name": "九江", "geoCoord": [111.60830084007915, 27.413572690587344] }, { "name": "泰安", "geoCoord": [116.8943496610887, 33.42706659607681] }, { "name": "洛阳", "geoCoord": [108.34537731037724, 34.53337694389285] }, { "name": "胶州", "geoCoord": [119.16354066347394, 33.31296673158757] }] } }, { "name": "男性化妆品", "type": "map", "mapType": "china", "itemStyle": { "normal": { "borderColor": "rgba(100,149,237,1)", "borderWidth": 1.5, "areaStyle": { "color": "#1b1b1b" } } }, "data": [], "markPoint": { "symbolSize": 2, "large": true, "effect": { "show": true }, "data": [{ "name": "南通", "geoCoord": [118.98302044169716, 30.815166670921524] }, { "name": "丽水", "geoCoord": [117.51917593128998, 27.175900156787154] }, { "name": "宁波", "geoCoord": [119.10356506494104, 27.207086835542988] }, { "name": "溧阳", "geoCoord": [117.49818817804228, 29.448686920881048] }, { "name": "溧阳", "geoCoord": [117.57605231961486, 31.220481919440477] }, { "name": "溧阳", "geoCoord": [116.38185436463738, 31.216695505106546] }, { "name": "株洲", "geoCoord": [112.16018205319533, 27.198147558384612] }, { "name": "张家港", "geoCoord": [120.03769754738859, 29.873200884224858] }, { "name": "南京", "geoCoord": [115.43436796783006, 31.741408845614743] }, { "name": "枣庄", "geoCoord": [113.94252648985767, 33.91860528428124] }, { "name": "葫芦岛", "geoCoord": [116.54445380125834, 39.2217437340353] }, { "name": "宜昌", "geoCoord": [106.39136061081724, 30.323927987429258] }, { "name": "海门", "geoCoord": [118.92759333671371, 31.63836130931613] }, { "name": "铜川", "geoCoord": [107.00189847182438, 33.32775182232371] }, { "name": "岳阳", "geoCoord": [108.8020402266062, 27.647946522647565] }, { "name": "宝鸡", "geoCoord": [106.95154700473638, 33.54137348889683] }, { "name": "莱西", "geoCoord": [119.67630196898783, 33.920477214253104] }, { "name": "荣成", "geoCoord": [119.82332280782194, 34.68516324648219] }, { "name": "西安", "geoCoord": [105.45599687897658, 33.126507956005334] }, { "name": "舟山", "geoCoord": [122.06116640403756, 27.48989441479301] }, { "name": "延安", "geoCoord": [109.17696534468183, 34.381505569493754] }, { "name": "瓦房店", "geoCoord": [120.42328837277441, 39.19286087807385] }, { "name": "宜兴", "geoCoord": [115.45442492051546, 30.022629603083125] }, { "name": "宜兴", "geoCoord": [115.69473809323983, 29.311352454623833] }, { "name": "衢州", "geoCoord": [116.90840501830971, 28.00653538740482] }, { "name": "沈阳", "geoCoord": [122.6454430625766, 40.28595641315486] }, { "name": "文登", "geoCoord": [121.71727643075364, 35.000681288436525] }, { "name": "丹东", "geoCoord": [123.56634631908385, 38.59689676291436] }, { "name": "丹东", "geoCoord": [122.09458954826239, 39.11472378681638] }, { "name": "自贡", "geoCoord": [103.58059636031344, 28.216839338621593] }, { "name": "泰安", "geoCoord": [117.00658252532075, 34.023934779610514] }, { "name": "大连", "geoCoord": [119.8000115596122, 36.33220209933714] }, { "name": "咸阳", "geoCoord": [103.87862083891093, 31.85317811094696] }, { "name": "德州", "geoCoord": [116.06984811175853, 35.276910619829664] }, { "name": "潍坊", "geoCoord": [115.80847610332941, 34.21783218628604] }, { "name": "宜宾", "geoCoord": [99.6265619868139, 27.728716723597604] }, { "name": "本溪", "geoCoord": [122.12275300717346, 38.51714963006456] }] } }, { "name": "皮肤护理", "type": "map", "mapType": "china", "itemStyle": { "normal": { "borderColor": "rgba(100,149,237,1)", "borderWidth": 1.5, "areaStyle": { "color": "#1b1b1b" } } }, "data": [], "markPoint": { "symbolSize": 2, "large": true, "effect": { "show": true }, "data": [{ "name": "常德", "geoCoord": [108.27657302120195, 26.382139086973254] }, { "name": "常德", "geoCoord": [110.57991838871756, 27.80589351796477] }, { "name": "攀枝花", "geoCoord": [99.35144795401753, 25.244728734951785] }, { "name": "邢台", "geoCoord": [109.57928805876527, 36.17169433132218] }, { "name": "徐州", "geoCoord": [116.18247155212372, 33.97465257866321] }, { "name": "大庆", "geoCoord": [121.78997354145591, 45.188420014630296] }, { "name": "宝鸡", "geoCoord": [105.8308983439045, 33.29954085284656] }, { "name": "潍坊", "geoCoord": [114.99719048005687, 34.34995718110099] }, { "name": "银川", "geoCoord": [102.83272361223617, 36.33386815818759] }, { "name": "杭州", "geoCoord": [118.35547626645203, 27.47414842313662] }, { "name": "杭州", "geoCoord": [115.32792142206237, 29.53740521193264] }, { "name": "葫芦岛", "geoCoord": [119.16619997373859, 39.34428836812844] }, { "name": "温州", "geoCoord": [118.27570195652558, 26.42807370676241] }, { "name": "烟台", "geoCoord": [120.40093434912463, 35.215013973700806] }, { "name": "牡丹江", "geoCoord": [126.8732004731564, 44.42172611740868] }, { "name": "日照", "geoCoord": [114.90475794827171, 34.467983353910434] }, { "name": "临汾", "geoCoord": [111.34868338396433, 34.307327231607886] }, { "name": "临汾", "geoCoord": [110.80251123174448, 34.6441769833816] }, { "name": "岳阳", "geoCoord": [110.5405228390427, 28.484334238711234] }, { "name": "济宁", "geoCoord": [113.29216643053203, 32.83489543991091] }, { "name": "成都", "geoCoord": [102.98346060450363, 29.36629994728889] }, { "name": "招远", "geoCoord": [119.65593201237866, 34.59746996920297] }, { "name": "金昌", "geoCoord": [98.21075061841645, 36.92329011558722] }, { "name": "泰安", "geoCoord": [114.78720195460431, 34.78525652155562] }, { "name": "诸暨", "geoCoord": [115.28419747366046, 27.56471917758214] }, { "name": "枣庄", "geoCoord": [116.51815630415375, 34.06842178477797] }, { "name": "张家港", "geoCoord": [117.40165844128614, 28.953105811229676] }, { "name": "章丘", "geoCoord": [116.18064822766024, 34.14968311847299] }, { "name": "绵阳", "geoCoord": [100.69987161449181, 29.733068186840807] }, { "name": "即墨", "geoCoord": [118.63266174645682, 35.717014031414784] }] } }, { "name": "茶", "type": "map", "mapType": "china", "itemStyle": { "normal": { "borderColor": "rgba(100,149,237,1)", "borderWidth": 1.5, "areaStyle": { "color": "#1b1b1b" } } }, "data": [], "markPoint": { "symbolSize": 2, "large": true, "effect": { "show": true }, "data": [{ "name": "吉林", "geoCoord": [123.81049767796884, 41.46156924637072] }, { "name": "诸暨", "geoCoord": [116.69102062182318, 29.344431015772894] }, { "name": "诸暨", "geoCoord": [119.08364760855832, 28.741197982290682] }, { "name": "胶南", "geoCoord": [118.07818290964134, 33.39232708457478] }, { "name": "遵义", "geoCoord": [103.99737050172655, 26.02669835610855] }, { "name": "昆山", "geoCoord": [117.58684682843192, 29.41041944931387] }, { "name": "渭南", "geoCoord": [106.84152645205008, 34.27750848680109] }, { "name": "渭南", "geoCoord": [107.98074755946453, 33.01923154545797] }, { "name": "廊坊", "geoCoord": [111.71452457452004, 36.64961759753397] }, { "name": "金昌", "geoCoord": [100.16146333713864, 38.33021763914634] }, { "name": "胶州", "geoCoord": [118.52597233526184, 34.1509765035445] }, { "name": "青岛", "geoCoord": [119.65885550711128, 35.659281050426] }, { "name": "长治", "geoCoord": [111.34374542806763, 33.604123760424365] }, { "name": "聊城", "geoCoord": [115.31886629394211, 34.96752717939911] }, { "name": "滨州", "geoCoord": [116.41334240433754, 37.29133304507344] }, { "name": "泰安", "geoCoord": [116.10164353839984, 34.825963298569924] }, { "name": "泰安", "geoCoord": [114.2432504016761, 35.512231977393554] }, { "name": "承德", "geoCoord": [115.81436508508295, 40.11618564759037] }, { "name": "泰州", "geoCoord": [118.62802268688642, 32.19210845405713] }, { "name": "泰州", "geoCoord": [119.14987922658594, 29.954464984263634] }, { "name": "长春", "geoCoord": [123.1341316071561, 42.806265846890156] }, { "name": "湘潭", "geoCoord": [109.05337837905563, 25.62073206991253] }, { "name": "宜昌", "geoCoord": [106.97801394049289, 29.90243106119182] }, { "name": "平度", "geoCoord": [117.52962872423532, 35.376107338548174] }, { "name": "三门峡", "geoCoord": [108.25787029800236, 32.44471891723254] }, { "name": "丹东", "geoCoord": [124.20471928562678, 38.77680647718994] }, { "name": "开封", "geoCoord": [110.0519108664928, 31.827006232210636] }, { "name": "德阳", "geoCoord": [103.39844159935168, 30.247608256760326] }, { "name": "德州", "geoCoord": [111.67226711897858, 36.36454504857004] }, { "name": "盐城", "geoCoord": [116.36431229543662, 32.18466962926547] }, { "name": "蓬莱", "geoCoord": [117.94905893232584, 35.22486979537584] }, { "name": "金华", "geoCoord": [115.56389576232026, 27.915327202160714] }, { "name": "西宁", "geoCoord": [100.53313172504618, 33.99253292331003] }, { "name": "富阳", "geoCoord": [117.24048544138441, 27.28162338838397] }] } }, { "name": "灯泡", "type": "map", "mapType": "china", "itemStyle": { "normal": { "borderColor": "rgba(100,149,237,1)", "borderWidth": 1.5, "areaStyle": { "color": "#1b1b1b" } } }, "data": [], "markPoint": { "symbolSize": 2, "large": true, "effect": { "show": true }, "data": [{ "name": "溧阳", "geoCoord": [114.72875250560699, 30.596925606143973] }, { "name": "呼和浩特", "geoCoord": [106.7843483428218, 38.72464964528567] }, { "name": "呼和浩特", "geoCoord": [110.92907471840837, 38.78842126077595] }, { "name": "延安", "geoCoord": [104.59763896701715, 35.838398020494346] }, { "name": "昆明", "geoCoord": [98.37745753739861, 23.20530246973278] }, { "name": "贵阳", "geoCoord": [105.02173329085026, 24.08466649117073] }, { "name": "大庆", "geoCoord": [121.2967472925059, 44.59241101246408] }, { "name": "锦州", "geoCoord": [117.56313469400904, 38.42466289920994] }, { "name": "成都", "geoCoord": [102.19640313112532, 28.01218588770748] }, { "name": "扬州", "geoCoord": [117.43625234329967, 32.24830532122522] }, { "name": "丹东", "geoCoord": [120.58387104744882, 39.209730981905366] }, { "name": "葫芦岛", "geoCoord": [116.63918039954689, 39.01986938891556] }, { "name": "镇江", "geoCoord": [116.9118856443951, 31.255781335225457] }, { "name": "嘉峪关", "geoCoord": [95.24195350861089, 39.151199594809746] }, { "name": "张家口", "geoCoord": [110.63740146582239, 39.43491263141021] }, { "name": "抚顺", "geoCoord": [121.39603129560699, 41.78721759944991] }, { "name": "诸暨", "geoCoord": [119.97062843082308, 29.071065093850272] }, { "name": "济宁", "geoCoord": [112.72897669345902, 34.74754212320602] }, { "name": "桂林", "geoCoord": [108.05417075435389, 23.298430854978598] }, { "name": "太仓", "geoCoord": [118.23692131906418, 30.147995095947813] }, { "name": "包头", "geoCoord": [105.07254194945558, 40.29297847485632] }, { "name": "宝鸡", "geoCoord": [103.74450319442197, 32.74880862575468] }, { "name": "嘉兴", "geoCoord": [118.931350746411, 29.218366412598247] }, { "name": "嘉兴", "geoCoord": [117.73695110692918, 29.604624977355066] }, { "name": "宿迁", "geoCoord": [114.95322746064969, 33.91972300020511] }, { "name": "齐齐哈尔", "geoCoord": [121.86193174728845, 45.065958194940656] }, { "name": "宁波", "geoCoord": [117.09408317269306, 27.439747095785698] }, { "name": "滨州", "geoCoord": [115.11339422247173, 35.6043975967092] }, { "name": "金华", "geoCoord": [119.32856810468313, 27.843811191472156] }, { "name": "金华", "geoCoord": [115.29844619222249, 26.14481677077432] }, { "name": "武汉", "geoCoord": [113.11761773431587, 29.71422280550311] }, { "name": "拉萨", "geoCoord": [90.10669378706402, 28.42695238723092] }, { "name": "招远", "geoCoord": [118.1037507682144, 36.86853037744342] }, { "name": "日照", "geoCoord": [118.32862503766013, 32.539069266605686] }, { "name": "秦皇岛", "geoCoord": [117.07130383278775, 39.372731724064344] }, { "name": "临安", "geoCoord": [117.80551847351587, 27.40802831318862] }, { "name": "胶南", "geoCoord": [117.43906354069652, 35.07747499767454] }] } }] };
        //myChart.setOption(opt);

        //disc();
        function disc(obj) {
            //交易量
            var disc0 = document.getElementById('disc0');
            disc0.innerHTML = "所有商品中，关联度最大的商品为：<br>" + '<span style="font-size:18px;color:rgb(0, 103, 197);font-weight:bold;">' + obj[0].legend.data[0] + "</span>";
            for (var i = 1; i < obj[0].legend.data.length; i++) {
                disc0.innerHTML += "、" + '<span style="font-size:18px;color:rgb(0, 103, 197);font-weight:bold;">' + obj[0].legend.data[i] + "</span>";
            }
            disc0.innerHTML += "<br><br>其中：<br>"
            for (var i = 0; i < obj[0].series.length; i++) {
                var it = obj[0].series[i];
                var max = 0, maxp = 0;
                var min = 9999999, minp = 0;
                for (var j = 0; j < it.data.length; j++) {
                    if (it.data[j] >= max) { max = it.data[j]; maxp = j; }
                    if (it.data[j] <= min) { min = it.data[j]; minp = j; }
                }
                disc0.innerHTML += '<span style="font-size:18px;color:rgb(0, 103, 197);font-weight:bold;">' + it.name + "</span>" + "的成交量在" + obj[0].xAxis.data[maxp] + "达到最高<br>成交量为：" + '<span style="font-size:18px;color:rgb(255, 81, 81);font-weight:bold;">' + max + "</span>";
                if (i < it.data.length - 1) disc0.innerHTML += "<br><br>"

            }


            //地域细分
            var MapCount = {};
            for (var i = 0; i < obj[1].series.length; i++) {
                for (var j = 0; j < obj[1].series[i].markPoint.data.length; j++) {
                    MapCount[obj[1].series[i].markPoint.data[j].name]++;
                }
            }
            /*
            for(var i=0;i<MapCount.length;i++)
            {
                var k = -1;
                var max = -1;
                for (var j = i; j < MapCount.length; i++)
                {
                    if (MapCount[j] > max) { max = MapCount[j]; k = j;}
                }
                var t = MapCount[i];
                MapCount[i] = max;
                MapCount[k] = t;

            }

            var disc1 = document.getElementById('disc1');
            for(var i=0;i<MapCount.length;i++)
            {
                disc1.innerHTML += MapCount[i];
            }
            */

            //关联度
            var op3 = obj[2];
            var disc2 = document.getElementById('disc2');
            disc2.innerHTML = "关联结果如下：<br>";
            for (var i = 0; i < op3.series.length; i += 2) {
                for (var j = 0; j < op3.series[i].data.length; j++) {
                    if (op3.series[i].data[j] != 0) {
                        disc2.innerHTML += '<span style="font-size:18px;color:rgb(0, 103, 197);font-weight:bold;">' + op3.series[i].name + "--" + op3.legend.data[j].name + "</span>" + " 关联度：" + '<span style="font-size:18px;color:rgb(255, 81, 81);font-weight:bold;">' + op3.series[i].data[j] + "%</span><br>";
                    }
                }
            }
            disc2.innerHTML += "<br>建议：<br> 将关联度较大的商品放置到相近的货架或设法使它们成对出现"


        }




        // loadChart('line');

    </script>


    <script>
        function uploadFile() {
        
            $('#prog').fadeIn();
            iPerc = 100;
            $('#prog').children('.bar').css('width', iPerc + '%');
            if (iPerc >= 100) {
                $('#ptxt').html('<b>100% 上传完成</b>');
               
                // $('#prog').children('.elapsed').html('Finished');
            }
            var file = document.getElementById("file").value;
            if (file == "") {
                alert("请选项上传文件！");
                return false;
            }
            document.getElementById("editForm").submit();
            // document.getElementById("updateButton").disabled = "disabled";
            ajaxBackState();
            
        }

        var bool = true;

        var readedBytes = 0;
        var totalBytes = 0;
        function ajaxBackState() {

            $.post("./servlet/UploadFileProgressBar", { uploadStatus: "uploadStatus" }, function (result) {
                var obj = eval("(" + result + ")");
                var txt = document.getElementById("txt");

                readedBytes = obj["readedBytes"];
                totalBytes = obj["totalBytes"];

                if (obj["error"] == "0") {
                    txt.innerHTML = obj["statusMsg"] + "：" + readedBytes + "/" + totalBytes;
                } else if (obj["error"] == "1") {
                    txt.innerHTML = obj["statusMsg"];
                    bool = false;
                } else {
                    txt.innerHTML = obj["statusMsg"] + "：" + readedBytes + "/" + totalBytes;
                    bool = false;
                }
            	
            });
            progressbar(readedBytes, totalBytes)
            if (bool) {
                setTimeout("ajaxBackState()", 1000);
            }
        }

        function progressbar(readedBytes, totalBytes) {

            $('#prog').children('.bar').progressbar();
            iPerc = (readedBytes > 0) ? (readedBytes / totalBytes) * 100 : 0; // percentages

            $('#ptxt').html('<b>' + iPerc.toFixed(1) + '%</b>');
            $('#prog').children('.bar').css('width', iPerc + '%');
            
            iPerc = 100;
            $('#prog').children('.bar').css('width', iPerc + '%');
            if (iPerc >= 100) {
                $('#ptxt').html('<b>100% 上传完成</b>');
               
                // $('#prog').children('.elapsed').html('Finished');
            }

        }

    </script>


    <div id="showBord" style="width: 90%; min-height:1000px; margin: 1px auto 3px auto; margin-bottom: 50px; background-color: #eeeeee; padding: 8px 15px; border-radius: 5px" onmouseover=" hideTheam()">
        <div style="width: 100%; height:40px;">
            <div id="adddata" class="buttons" style="/*position:absolute;left:100px*/" onclick="$('#updiv').slideDown('slow');">
                <div class="btn btn-primary btn-icon glyphicons circle_plus mid-wite-text"><i></i> 添加数据</div>
            </div>

        </div>
        <div>
            <div id="updiv">
                <iframe name="uploadIfr" style="display:none;"></iframe>
                <form id="editForm" action="./servlet/UploadFileProgressBar" method="post" enctype="multipart/form-data" target="uploadIfr">
                    <input type="file" id="filename" name="file" class="" style="width: 300px;">&nbsp;

                    <div class="btn btn-danger" style="float:right;" onclick="$('#updiv').slideUp();">X</div>
                    <input type="button" id="updateButton" class="btn btn-primary" value="上传 " onclick="uploadFile()" style="float:right;"> <!--uploadFile()-->
                    <br>

                    <span id='txt'></span> <span id='ptxt'></span><br>
                    <div id="prog" class="progress progress-info" style="display:none">
                        <div class="percent"></div>
                        <div class="bar" style="width: 1%;"></div>
                        <div class="elapsed"></div>
                    </div>
                </form>

            </div>
            <button id="begin" class="btn btn-block btn-info btn-icon glyphicons flash" style="float:right;width:150px;display:none"><i></i>开始挖掘</button>

        </div>
        <span class="btn btn-large border-only btn-info" onclick="loadCharts();">开始挖掘</span>

        <div id="line" style="width:100%;display: inline-block; margin-top:10px;">
            <div class="head blue">
                <h2 class="mid-wite-text">交易量</h2>

            </div>
            <div>
                <div class="data-show" style="width: 70%; min-height: 400px; display: inline-block; margin-top: 10px; vertical-align: top; ">
                    <div id="lineBord" style="height:400px"></div>
                </div>

                <div style="background-color: #d9d9d9; width: 27%; min-height: 400px; border-radius: 30px; display: inline-block; margin-top: 10px; margin-left: 20px; vertical-align: top; ">
                    <div style=" padding:5px;text-align:center;"><div id="disc0" style="color: black; line-height: 23px; text-align: left; font-family: 'Microsoft YaHei UI'; font-size: 15px;"></div></div>
                </div>
            </div>
        </div>

        <div id="line" style="width:100%;display: inline-block; margin-top:30px;">
            <div class="head green">
                <h2 class="mid-wite-text">地域细分</h2>
                <!--
                <ul class="buttons">
                    <li><a href="#" onclick=""><div class="icon"><span class="ico-info"></span></div></a></li>
                    <li><a href="#" class="ublock"><div class="icon"><span class="ico-undo"></span></div></a></li>
                    <li><a href="#" class="cblock"><div class="icon"><span class="ico-remove"></span></div></a></li>
                </ul>
                   -->
            </div>
            <div>
                <div class="data-show" style="width: 100%; min-height:400px; display: inline-block; margin-top: 10px; vertical-align: top; ">
                    <div id="MapBord" style="height:400px"></div>
                </div>

                <div style="background-color: #d9d9d9; width: 27%; min-height: 400px; border-radius: 30px; display: none; margin-top: 10px; margin-left: 20px; vertical-align: top; ">
                    <div style=" padding:5px;text-align:center;"><div id="disc1" style="color: black; line-height: 23px; text-align: left; font-family: 'Microsoft YaHei UI'; font-size: 15px;">dfd</div></div>
                </div>
            </div>
        </div>

        <div id="line" style="width:100%;display: inline-block; margin-top:30px;">
            <div class="head blue">
                <h2 class="mid-wite-text">商品关联度</h2>
            </div>
            <div>
                <div class="data-show" style="width: 70%; min-height: 400px; display: inline-block; margin-top: 10px; vertical-align: top; ">
                    <div id="AssChart" style="height:400px"></div>
                </div>

                <div style="background-color: #d9d9d9; width: 27%; min-height: 400px; border-radius: 30px; display: inline-block; margin-top: 10px; margin-left: 20px; vertical-align: top; ">
                    <div style=" padding:5px;text-align:center;"><div id="disc2" style="color: black; line-height: 23px; text-align: left; font-family: 'Microsoft YaHei UI'; font-size: 17px;"></div></div>
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