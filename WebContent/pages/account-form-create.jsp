<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>

<head>

<title>Account</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="styles/bootstrap.css">
	<script type="text/javascript" src="scripts/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="scripts/bootstrap.min.js"></script>



</head>
<body>


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
				<a class="navbar-brand" href="#">文件上传管理系统</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a
					href="${pageContext.request.contextPath}/file/list">Home</a></li>

			</ul>
		</div>
	</nav>

	<div id="container">
		<h3>创建用户</h3>

		<form:form action="createNewAccount.html" modelAttribute="sysUser"
			method="POST" class="form-horizontal">

			<!-- associate the data with file id -->
			<form:hidden path="id" />

			<div class="form-group">
				<label class="control-label col-sm-2">公证员姓名:</label>
				<div class="col-sm-6">
					<form:input type="text" path="userName" class="form-control" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2">用户名:</label>
				<div class="col-sm-6">
					<form:input type="text" path="account" class="form-control" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2">所属部门:</label>
				<div class="col-sm-6">
					<form:input type="text" path="departmentName" class="form-control" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2">密码:</label>
				<div class="col-sm-6">
					<form:input type="text" path="password" class="form-control" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2">创建时间:</label>
				<div class="col-sm-6">
					<form:input type="text" path="createtime" class="form-control" readonly="true"/>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2">用户权限:</label>
				<div class="col-sm-6">
					<form:input type="text" path="userRight" class="form-control" />
				</div>
			</div>
			

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" value="创建" class="btn btn-success" />
				</div>
			</div>

		</form:form>

		<div style=""></div>

		<p>
			<a href="${pageContext.request.contextPath}/listAccount.html">返回上一页</a>
		</p>
	</div>

</body>


</html>