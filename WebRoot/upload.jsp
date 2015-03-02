<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>upload</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" href="css/jquery-ui-1.8.16.custom.css" type="text/css"></link>
    <link rel="stylesheet" href="css/main.css" type="text/css"></link>
<script src="http://libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js" ></script>
	<script type="text/javascript" src="js/script.js" ></script>
	<script type="text/javascript">	 	
	 	
		function uploadFile(){
			var file = document.getElementById("file").value;
			if(file == ""){
				alert("请选项上传文件！");
				return false;
			}
			document.getElementById("editForm").submit();
			document.getElementById("updateButton").disabled = "disabled";
			ajaxBackState();
		}
		
		var bool = true;
		
		var readedBytes = 0;
		var totalBytes = 0;
		function ajaxBackState(){
			
			$.post("./servlet/UploadFileProgressBar",{uploadStatus:"uploadStatus"},function(result){
				var obj = eval("("+result+")");
				var txt = document.getElementById("txt");
				
				readedBytes = obj["readedBytes"];
				totalBytes = obj["totalBytes"];
				
				if(obj["error"] == "0"){
					txt.innerHTML = obj["statusMsg"]+"："+ readedBytes +"/"+totalBytes;
				}else if(obj["error"] == "1"){
					txt.innerHTML = obj["statusMsg"];
					bool = false;
				}else{
				    txt.innerHTML = obj["statusMsg"]+"："+ readedBytes +"/"+totalBytes;
					bool = false;
				}
			});
			document.getElementById("bytes").innerHTML += readedBytes + "<br>";
			progressbar(readedBytes,totalBytes)
			if(bool){
					setTimeout("ajaxBackState()",1000); 					
			}
		}
		
		function progressbar(readedBytes,totalBytes){
				 
			 $('#progress').children('.pbar').progressbar();
			 iPerc = (readedBytes > 0) ? (readedBytes / totalBytes) * 100 : 0; // percentages
			 
			 $('#progress').children('.percent').html('<b>'+iPerc.toFixed(1)+'%</b>');
			 $('#progress').children('.pbar').children('.ui-progressbar-value').css('width', iPerc+'%');
			 if (iPerc >= 100) {
                  $('#progress').children('.percent').html('<b>100%</b>');
                  $('#progress').children('.elapsed').html('Finished');
             }
			 
		}

	</script>

	</head>
  
  <body>
  	<iframe name="uploadIfr" style="display:none;"></iframe>
    <form id="editForm" action="./servlet/UploadFileProgressBar" method="post" enctype="multipart/form-data" target="uploadIfr" > 
	    <input type="file" id="file" name="file" style="width: 300px;" >&nbsp;
	    <input type="button" style="height:20px;"  id="updateButton" value=" 上 传 " onclick="uploadFile()" ><br> 
	    <span id='txt' ></span><br><br>
	   <div id="progress" style="width: 400px" >
            <div class="percent"></div>
            <div class="pbar"></div>
            <div class="elapsed"></div>
        </div>
        <span id=bytes ></span><br>
    </form>
  </body>
</html>