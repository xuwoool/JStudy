<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jquery.plupload_2</title>

<script src="static/plupload/plupload.full.min.js"></script>

<!-- 国际化中文支持 -->
<script type="text/javascript" src="static/plupload/i18n/zh_CN.js"></script>

</head>
<body style="font: 13px Verdana; background: #eee; color: #333">

<h1>Custom example</h1>

<p>Shows you how to use the core plupload API.</p>

<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
<br />

<div id="container">
    <a id="pickfiles" href="javascript:;">[选择文件]</a> 
    <a id="uploadfiles" href="javascript:;">[上传]</a>
</div>

<br />
<pre id="console"></pre>


<script type="text/javascript">
// Custom example logic

var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'pickfiles', // you can pass in id...
	container: document.getElementById('container'), // ... or DOM Element itself
	url : 'pluploadfileupload',
	// Flash settings
    flash_swf_url: 'static/plupload/Moxie.swf',
    // Silverlight settings
    silverlight_xap_url: 'static/plupload/Moxie.xap',
	
	filters : {
		max_file_size : '20mb',
		mime_types: [
			{title: "图像文件", extensions: "jpg,gif,png"},
            {title: "文档文件", extensions: "txt,doc,docx,xls,xlsx,ppt,pptx,vsd"},
            {title: "压缩文件", extensions: "zip,rar"}
		]
	},

	init: {
		PostInit: function() {
			document.getElementById('filelist').innerHTML = '';

			document.getElementById('uploadfiles').onclick = function() {
				uploader.start();
				return false;
			};
		},

		FilesAdded: function(up, files) {
			plupload.each(files, function(file) {
				document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
			});
		},

		UploadProgress: function(up, file) {
			document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
		},

		Error: function(up, err) {
			document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
		}
	}
});

uploader.init();

</script>
</body>
</html>