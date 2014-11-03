<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jquery.plupload_1</title>

<!-- 配置界面上的css -->
<link href="static/plupload/jquery.plupload.queue/css/jquery.plupload.queue.css" rel="stylesheet">

<script src="static/plupload/plupload.full.min.js"></script>
<script src="static/plupload/jquery.plupload.queue/jquery.plupload.queue.min.js"></script>

<!-- 国际化中文支持 -->
<script type="text/javascript" src="static/plupload/i18n/zh_CN.js"></script>

<script type="text/javascript">
/* Convert divs to queue widgets when the DOM is ready */
$(function(){
    function plupload(){
        $("#uploader").pluploadQueue({
            // General settings
            runtimes : 'html5,flash',
            url : 'pluploadfileupload',
            max_file_size : '10mb',
            unique_names: true,
            chunk_size: '2mb',
            // Specify what files to browse for
            filters : [
                {title: "Image files", extensions: "jpg,gif,png"},
                {title: "Doc files", extensions: "txt,doc,docx,xls,xlsx,ppt,pptx,vsd"},
                {title: "Zip files", extensions: "zip,rar"}
            ],
            resize: {width: 640, height: 480, quality: 90},
            // Flash settings
            flash_swf_url: 'static/plupload/Moxie.swf',
            // Silverlight settings
            silverlight_xap_url: 'static/plupload/Moxie.xap',
            // 参数
            multipart_params: {'user': '1@1.com', 'time': '2014-09-16'}
        });
    }
    plupload();
    $('#Reload').click(function(){
        plupload();
    });
});
</script>
 

</head>
<body>
<div style="width:750px; margin:0 auto">
    <form id="formId" action="pluploadfileupload" method="post">
        <div id="uploader">
            <p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
        </div>
        <input value="重新上传" id="Reload" type="button">
    </form>
</div>

</body>
</html>