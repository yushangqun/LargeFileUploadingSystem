<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>断点续传文件</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript" src="scripts/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="scripts/fileUpload.js"></script>
	<!-- -->
	
	<link rel="stylesheet" href="styles/bootstrap.css">
	<link rel="stylesheet" href="styles/bootstrap-datepicker.css">
	
	<script type="text/javascript" src="scripts/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="scripts/bootstrap.js"></script>
	<script type="text/javascript" src="scripts/bootstrap-datepicker.js"></script>
	<script src="scripts/bootstrap-datepicker.zh-CN.js"></script>
	
  </head>
  
 
<body onload="init();">

	<!-- prevent unauthenticated access -->
	<%
		//disable the goBack function after user logout. 
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

		if (session.getAttribute("account") == null) {
			response.sendRedirect("showFormForLogin.html");
		}
	%>

	<!-- navigation bar -->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="list.html">文件上传管理系统</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="list.html">首  页</a></li>

			</ul>
		</div>
	</nav>




<div id="container" class="col-xs-4 col-md-offset-4" >

    	<h2  >请选择需要上传的文件</h2>
      	<br></br>
      	
      	<!-- calendar picker-->
  <div class="input-group">
  <input type="text" id="dp1" class="span2 datepicker" placeholder="请选择文件月份..."  
           name="date"> <br>
      </div>
      
      <script>
      $(function(){
   $('.datepicker').datepicker({
      format: 'yyyy-mm',
      startView: 'year',
      minViewMode: 1,
	  language: 'zh-CN'
    });
    
    
});
      </script>

      	<br></br>
      	<div class="input-group">
      	
      	
      	
      	
      		<input type="text" id="filePathHolder" placeholder="请选择要上传的文件" class="form-control" readonly="true"/>
      		<div class="input-group-btn">
      			<label for="fileToUpload" class="btn btn-default">浏览..</label>
      		</div>
      		<p hidden>
      			<input type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();" class="form-control" multiple/>
			</p>
		
		
		</div>
		<br></br>
		<div class="input-group">
    		<button onclick="uploadFiles()" class="btn btn-default">上传</button>
    		<button onclick="pauseUpload()" class="btn btn-default">暂停</button>
    	</div>
    	<div class="input-group">
    		<label id="progressNumber"></label>
		</div>
		
		
		<div id="fileFrame" class="input-group"></div>
		<div id="msg" style="max-height: 400px; overflow:auto;min-height: 100px;"></div>
		

		</div>

	<br>
</div>	
	
	
	
	
</body>
</html>
