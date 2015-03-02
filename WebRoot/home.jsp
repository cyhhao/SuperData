<!DOCTYPE html>
<html>
<head>
    <title>SuperCally | Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <link rel="stylesheet" href="css/jquery-ui-1.8.16.custom.css" type="text/css">
    <link rel="stylesheet" href="css/main.css" type="text/css">
    <link href="css/glyphicons.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.min.css" rel="stylesheet" type="text/css" media="all" />



    <link href="css/Mine.css" rel="stylesheet" type="text/css" media="all" />
    <script src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="js/script.js"></script>

</head>
<body>
    <script type="text/javascript">
        refresh();
        od();
        function uploadFile() {
            $('#prog').fadeIn();
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

            iPerc = 100;

            $('#prog').children('.bar').css('width', iPerc + '%');
            if (iPerc >= 100) {
                $('#ptxt').html('<b>100% 上传完成</b>');
                refresh();
                // $('#prog').children('.elapsed').html('Finished');
            }

        }

        function refresh() {
            $.get("./servlet/GetFileList", function (data, status) {
                // alert("Data: " + data + "\nStatus: " + status);
                var filelist = eval(data);
                var table = $('#filelist');
                table.empty();
                for (var i = 0; i < filelist.length; i++) {

                    var url = "./show?id=" + filelist[i];
                    var url2 = "./mine?id=" + filelist[i];
                    table.append('<tr>' +
                    '<td>' + filelist[i] + '</td>' +
                    '<td class="center zhai-td"><span class="label label-important btntext"><a class="btn-action border-only circle glyphicons pencil btn-default"><i></i></a><a href="#" class="awite-none">选取</a></span></td>' +
                    '<td class="center zhai-td"><span class="label label-important btntext btn-info"><a class="btn-action border-only circle glyphicons spade btn-info"><i></i></a><a href="' + url2 + '" target="_blank"  class="awite-none">挖掘</a></span></td>' +
                    '<td class="center zhai-td"><span class="label label-important btntext btn-success"><a class="btn-action border-only circle glyphicons charts btn-success"><i></i></a><a href="' + url + '" target="_blank" class="awite-none">展示</a></span></td>' +
                    '<td class="center zhai-td"><a href="#" class="btn-action border-only circle glyphicons remove_2 btn-danger"><i></i></a></td>' +
                    '</tr>')
                }
            });

        }


        function showTheam()
        {
            //alert($('#theam').css('display'));
            $('#theam').slideDown();
            
        }
        function hideTheam()
        {
            $('#theam').slideUp();
        }
    </script>
    <div id="undefined-sticky-wrapper" class="sticky-wrapper" style="height: 78px;">
        <nav id="nav" class="nav_wrapper">
            <div class="wrap">
                <div class="logo"><img src="images/logo1.png" alt="logo" /></div>
                <a href="#" class="header_toggle">Menu</a>
                <ul class="navigation">
                    <li><a href="./index">首页</a></li>
                    <li class="active"><a href="">仪表盘</a></li>
                    <li onmousemove="showTheam();">
                        <a href=" ">专题</a>
                        <ul id="theam" style="display: none;height:50px">
                            <li><a href="./ebuy">电商</a></li>
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

        <!-- <div style="position: absolute; float: right; right: 50px; top: 5px; z-index: 999; vertical-align: middle; "><div style=" display:inline-block; padding-bottom:10px">王尼玛</div> <img src="img/headico.gif" /></div>-->
    </div>
    <ul class="breadcrumb" style="width:75%; margin:auto; margin-bottom:20px">
        <li><a href="#" class="glyphicons home" style="font-size: large"> 仪表盘</a></li>
        <li class="divider"></li>
        <li>数据列表</li>
    </ul>

    <div style="width:75%; margin:auto; margin-bottom:50px; background-color:#eeeeee; padding:8px 15px; border-radius:5px" onmouseover=" hideTheam()">

        <div style="width: 100%; height:40px">
            <div id="adddata" class="buttons pull-right " onclick="$('#updiv').slideDown('slow');">
                <div class="btn btn-primary btn-icon glyphicons circle_plus mid-wite-text"><i></i> 添加数据</div>
            </div>
        </div>
        <div>
            <div id="updiv">
                <iframe name="uploadIfr" style="display:none;"></iframe>
                <form id="editForm" action="./servlet/UploadFileProgressBar" method="post" enctype="multipart/form-data" target="uploadIfr">
                    <input type="file" id="file" name="file" class="" style="width: 300px;">&nbsp;

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

        </div>
        <br><br>
        <table class="dynamicTable table table-striped table-bordered table-primary table-condensed" style="padding-top:10px">
            <thead>
                <tr>
                    <th class="mid-wite-text">文件名</th>
                    <th class="mid-wite-text">数据选取</th>
                    <th class="mid-wite-text">数据挖掘</th>
                    <th class="mid-wite-text">数据展示</th>
                    <th class="mid-wite-text">删除</th>
                </tr>
            </thead>
            <tbody id="filelist">
                <tr>
                    <td>Trident</td>
                    <td class="center zhai-td"><span class="label label-important btntext"><a class="btn-action border-only circle glyphicons pencil btn-default"><i></i></a><a href="#" class="awite-none">选取</a></span></td>
                    <td class="center zhai-td"><span class="label label-important btntext btn-info"><a class="btn-action border-only circle glyphicons spade btn-info"><i></i></a><a href="#" class="awite-none">挖掘</a></span></td>
                    <td class="center zhai-td"><span class="label label-important btntext btn-success"><a class="btn-action border-only circle glyphicons charts btn-success"><i></i></a><a href="show.html?id=1" class="awite-none">展示</a></span></td>
                    <td class="center zhai-td"><a href="#" class="btn-action border-only circle glyphicons remove_2 btn-danger"><i></i></a></td>
                </tr>

            </tbody>
        </table>
    </div>


    <div class="footer" style="margin-bottom:0px">
        <div class="wrap">
            <div class="footer-left">
                <div class="copy">
                    <p>Copyright &copy; 2014.SuperCally All rights reserved.</p>
                </div>
            </div>
            <div class="social_icons social">
                <ul>
                    <li class="in"><a href="#"><span> </span></a></li>
                    <li class="pro"><a href="#"><span> </span></a></li>
                    <li class="twitter"><a href="#"><span> </span></a></li>
                    <li class="facebook"><a href="#"><span> </span></a></li>
                </ul>
            </div>
            <div class="clear"> </div>
        </div>




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

    <!--table ctrl-->
    <script id="js1" src=""></script>
    <script id="js2" src=""></script>
    <script id="js3" src=""></script>
    <script id="js4" type="text/javascript" src=""></script>
    <script id="js5" type="text/javascript" src=""></script>
    <script id="js6" type="text/javascript" src=""></script>
    <script>
        function od() {
            document.getElementById('js' + 1).src = 'theme/scripts/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js';
            document.getElementById('js' + 2).src = 'theme/scripts/jquery.cookie.js';
            document.getElementById('js' + 3).src = 'theme/scripts/pixelmatrix-uniform/jquery.uniform.min.js';
            document.getElementById('js' + 4).src = 'js/jquery.dataTables.min.js';
            document.getElementById('js' + 5).src = 'js/DT_bootstrap.js';
            document.getElementById('js' + 6).src = 'js/load.js';
        }
        //setTimeout(od,1000);//延时3秒
    </script>
    <!--table ctrl-->


    <script type="text/javascript" src="js/move-top.js" defer></script>
    <script type="text/javascript" src="js/easing.js" defer></script>



</body>
</html>