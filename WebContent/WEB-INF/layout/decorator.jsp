<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="xuxb">
    <link rel="icon" href="static/images/favicon.ico">

    <title>
	    <decorator:title /> - JStudy
	</title>

    <!-- Bootstrap core CSS -->
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- jquery-1.11.1.min.js -->
	<script src="static/jquery/jquery-1.11.1.min.js"></script>
	
	<!-- bootstrap.min.js -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- 自定义样式 -->
	<style type="text/css">
    	body { padding-top: 50px; }
	    .glyphicon {
			padding-right: 5px;
		}
		.s-bottom-ctner {
			margin: 8px auto 0 auto;
			width: 100%;
			position: absolute;
			bottom: 0;
			left: 0;
			overflow: hidden;
			padding-bottom: 8px;
			color: #CCC;
			word-spacing: 3px;
			zoom: 1;
			
			padding-top: 5px;
			text-align: center;
			font-size: 12px;
			border-top: 1px solid #bbb;
			background: #eee;
		}
		.menu {
			width:15%;
			float: left;
			position: relative;
		}
		.box {
			width:85%;
			float: left;
			position: relative;
			height: 650px;
			overflow: auto;
		}
    </style>
    
    <script type="text/javascript">
    	function loadPage(url) {
    		window.location.href=url;
    	}
    	$(function(){
	    	$("a.navbar-brand").click(function() {
	    		var menu = $("div.menu");
	    		if (menu.is(":hidden")) {
	    			menu.show();
		    		$(".box").css({"width":"85%"});
	    		} else {
	    			menu.hide();
		    		$(".box").css({"width":"100%"});
	    		}
	    	});
	    	
	    	$("a.navbar-brand").click();
	    	
	    	$(document).scroll(function(){
	    		var top = $(this).scrollTop();
    			$(".s-bottom-ctner").css({"margin":"8px auto "+(-top)+"px auto"});
	    		/* var wait = setTimeout(function() {
	    			 clearTimeout(wait);  
	    			$(".s-bottom-ctner").css({"margin":"8px auto "+(-top)+"px auto"});
	    		},100); */
	    	});
    	});
    </script>
	
	<!-- 被装载的页面head -->
	<decorator:head />
</head>
<body>
	<!-- 页面头部开始 -->
	<header>
   	<!-- 导航开始 -->
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-home"></span>JStudy</a>
	    </div>
	
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav navbar-left">
	        <li class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-book"></span>文档管理<span class="caret"></span></a>
	        	<ul class="dropdown-menu" role="menu">
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-bookmark"></span>在线预览</a></li>
		            <li><a href="#" onclick="loadPage('plupload.jsp');"><span class="glyphicon glyphicon-bookmark"></span>jquery.plupload_1</a></li>
		            <li><a href="#" onclick="loadPage('plupload2.jsp');"><span class="glyphicon glyphicon-bookmark"></span>jquery.plupload_2</a></li>
		          </ul>
	        </li>
	      </ul>
	      <ul class="nav navbar-nav navbar-left">
	        <li class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-inbox"></span>日志管理<span class="caret"></span></a>
	        	<ul class="dropdown-menu" role="menu">
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-ok"></span>日志监控</a></li>
		          </ul>
	        </li>
	      </ul>
	      <ul class="nav navbar-nav navbar-left">
	        <li class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-transfer"></span>工作流管理<span class="caret"></span></a>
	        	<ul class="dropdown-menu" role="menu">
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-send"></span>发布流程</a></li>
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-edit"></span>流程绘制</a></li>
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-check"></span>表单定制</a></li>
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-list-alt"></span>流程监控</a></li>
		          </ul>
	        </li>
	      </ul>
	      <ul class="nav navbar-nav navbar-left">
	        <li class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-calendar"></span>工作调度<span class="caret"></span></a>
	        	<ul class="dropdown-menu" role="menu">
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-ok"></span>指定调度</a></li>
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-ok"></span>调度监控</a></li>
		          </ul>
	        </li>
	      </ul>
	      <ul class="nav navbar-nav navbar-left">
	        <li class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-cog"></span>系统设置<span class="caret"></span></a>
	        	<ul class="dropdown-menu" role="menu">
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-user"></span>用户管理</a></li>
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-th-list"></span>资源管理</a></li>
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-eye-open"></span>权限管理</a></li>
		            <li><a href="#" onclick="loadPage('preview.jsp');"><span class="glyphicon glyphicon-tower"></span>组织机构</a></li>
		          </ul>
	        </li>
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-th-large"></span>${username }<span class="caret"></span></a>
	          <ul class="dropdown-menu" role="menu">
	            <li><a href="#"><span class="glyphicon glyphicon-user"></span>个人信息</a></li>
	            <li><a href="#"><span class="glyphicon glyphicon-ok"></span>我的待办</a></li>
	            <li class="divider"></li>
	            <li><a href="#"><span class="glyphicon glyphicon-off"></span>退出</a></li>
	          </ul>
	        </li>
	      </ul>
	    </div>
	  </div>
	</nav>
	<!-- 导航结束 -->
	</header>
    <!-- 页面头部结束 -->
    
    <!-- 左边菜单开始 -->
    <div class="menu">
	    <ul class="nav nav-pills nav-stacked" role="tablist">
		  <li role="presentation" class="active"><a href="#">左边菜单<span class="badge">12</span></a></li>
		  <li role="presentation"><a href="#">左边菜单<span class="badge">42</span></a></li>
		  <li role="presentation"><a href="#">左边菜单<span class="badge">56</span></a></li>
		</ul>
	</div>
	<!-- 左边菜单结束 -->

    <!-- 被装载的页面body开始 -->
    <!-- 右边内容开始 -->
    <div class="box">
   	<decorator:body />
   	</div>
   	<!-- 右边内容结束 -->
    <!-- 被装载的页面body结束 -->
   
    <!-- 页面底部开始 -->
    <footer>
	   	<div id="bottom_container" class="s-bottom-ctner">©2014 JStudy <a href="#">操作手册</a>
	   		<span>京ICP证123456号</span>
	   		<img width="13" height="16" data-loadfunc="1" src="static/images/copy_rignt_24.png" data-loaded="1">
	   	</div>
   	</footer>
    <!-- 页面底部结束 -->
</body>
</html>