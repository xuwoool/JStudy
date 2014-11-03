<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
    <title>在线预览</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />

	<script type="text/javascript" src="static/flexpaper/flexpaper.js"></script>
	<script type="text/javascript" src="static/flexpaper/flexpaper_handlers.js"></script>
	<style type="text/css">
		form > div,input {margin: 5px;}
	</style>
	<script type="text/javascript">
		$(function() {
			<c:if test="${!empty filename}">
				$("#documentViewer").css({"height":"590px"});
			</c:if>
			$("#upload").click(function() {
				$(this).hide();
				$("#uploading").show();
			});
			$("#uploading").hide();
		});
		
	</script>
</head>
<body>
<!-- 路径导航 -->
<ol class="breadcrumb">
  <li><a href="#"><span class="glyphicon glyphicon-home"></span>主页</a></li>
  <li><a href="#">文档管理</a></li>
  <li class="active">在线预览</li>
</ol>

<c:if test="${empty filename}">
<form name="uploadForm" method="post" enctype="multipart/form-data" action="fileupload">  
    <div>附件1:<input type="file" name="file1"/></div>
    <div>附件2:<input type="file" name="file2"/></div>
    <div>
    	<input type="submit" name="submit" value="上传" id="upload">
    	<input type="button" name="button" value="上传中..." id="uploading" disabled="disabled">
    	<input type="reset" name="reset" value="重置">
    </div>
 </form>
</c:if>

<c:if test="${!empty filename}">
<%--swf文件展示 --%>
<c:if test="${!empty flexpaper}">
<div id="documentViewer" class="flexpaper_viewer" style="height:450px"></div>
<script type="text/javascript">
    $('#documentViewer').FlexPaperViewer(
            { config : {
            	jsDirectory : 'static/flexpaper/',
                SWFFile : '${filename}',
                Scale : 0.6,
                ZoomTransition : 'easeOut',
                ZoomTime : 0.5,
                ZoomInterval : 0.2,
                FitPageOnLoad : true,
                FitWidthOnLoad : true,
                FullScreenAsMaxWindow : false,
                ProgressiveLoading : true,
                MinZoomSize : 0.2,
                MaxZoomSize : 5,
                SearchMatchAll : false,
                InitViewMode : 'Portrait',
                RenderingOrder : 'flash,html',
                StartAtPage : '',
                ViewModeToolsVisible : true,
                ZoomToolsVisible : true,
                NavToolsVisible : true,
                CursorToolsVisible : true,
                SearchToolsVisible : true,
                WMode : 'window',
                localeChain : 'zh_CN'
            }}
    );
</script>
</c:if>
<%--html文件展示 --%>
<c:if test="${empty flexpaper }">
<script type="text/javascript">
window.open("${filename}","fullscreen","fullscreen=1,left=0,top=0");
</script>
</c:if>
</c:if>
</body>
</html>
<%--
4.3 Flexpaper参数说明

SwfFile (String) 需要使用Flexpaper打开的文档
Scale (Number) 初始化缩放比例，参数值应该是大于零的整数
ZoomTransition (String) Flexpaper中缩放样式，它使用和Tweener一样的样式，默认参数值为easeOut.其他可选值包括: easenone, easeout, linear, easeoutquad
ZoomTime (Number) 从一个缩放比例变为另外一个缩放比例需要花费的时间，该参数值应该为0或更大。
ZoomInterval (Number) 缩放比例之间间隔，默认值为0.1，该值为正数。
FitPageOnLoad (Boolean) 初始化得时候自适应页面，与使用工具栏上的适应页面按钮同样的效果。
FitWidthOnLoad (Boolean) 初始化的时候自适应页面宽度，与工具栏上的适应宽度按钮同样的效果。
localeChain (String) 设置地区（语言），目前支持以下语言。
    en_US (English)
    fr_FR (French)
    zh_CN (Chinese, Simple)
    es_ES (Spanish)
    pt_BR (Brazilian Portugese)
    ru_RU (Russian)
    fi_FN (Finnish)
    de_DE (German)
    nl_NL (Netherlands)
    tr_TR (Turkish)
    se_SE (Swedish)
    pt_PT (Portugese)
    el_EL (Greek)
    da_DN (Danish)
    cz_CS (Czech)
    it_IT (Italian)
    pl_PL (Polish)
    pv_FN (Finnish)
    hu_HU (Hungarian)
FullScreenAsMaxWindow (Boolean) 当设置为true的时候，单击全屏按钮会打开一个flexpaper最大化的新窗口而不是全屏，当由于flash播放器因为安全而禁止全屏，而使用flexpaper作为独立的flash播放器的时候设置为true是个优先选择。
ProgressiveLoading (Boolean) 当设置为true的时候，展示文档时不会加载完整个文档，而是逐步加载，但是需要将文档转化为9以上的flash版本（使用pdf2swf的时候使用-T 9 标签）。
MaxZoomSize (Number) 设置最大的缩放比例。
MinZoomSize (Number) 最小的缩放比例。
SearchMatchAll (Boolean) 设置为true的时候，单击搜索所有符合条件的地方高亮显示。
InitViewMode (String) 设置启动模式如"Portrait" or "TwoPage".
ViewModeToolsVisible (Boolean) 工具栏上是否显示样式选择框。
ZoomToolsVisible (Boolean) 工具栏上是否显示缩放工具。
NavToolsVisible (Boolean) 工具栏上是否显示导航工具。
CursorToolsVisible (Boolean) 工具栏上是否显示光标工具。
SearchToolsVisible (Boolean) 工具栏上是否显示搜索。
 
对比本例子中的参数设置：
SwfFile : escape('FusionCharts.swf'),//加载的SWF文件为FusionCharts.swf
Scale : 0.6, //初始化缩放比例为0.6，即60%
ZoomTransition : 'easeOut',
ZoomTime : 0.5,
ZoomInterval : 0.2,
FitPageOnLoad : true,//加载时自动适应页面
FitWidthOnLoad : false, //加载时自动适应页面宽度
FullScreenAsMaxWindow : false,
ProgressiveLoading : false,
MinZoomSize : 0.2, //页面最小可缩小成20%
MaxZoomSize : 5, //页面最大可放大成500%
SearchMatchAll : false,
InitViewMode : 'Portrait',
PrintPaperAsBitmap : false,
ViewModeToolsVisible : true,
ZoomToolsVisible : true,
NavToolsVisible : true,
CursorToolsVisible : true,
SearchToolsVisible : true,                     
localeChain: 'en_US' //设置地区（语言）为en_US
 --%>