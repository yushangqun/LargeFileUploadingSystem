<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>

<title>Upload File</title>

<!-- bootstrap stuff -->

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="styles/bootstrap.css">
	<script type="text/javascript" src="scripts/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="scripts/bootstrap.min.js"></script>

</head>

<body>


	<!-- navigation bar -->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">文件上传管理系统</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="#">主页</a></li>

			</ul>
		</div>
	</nav>

	<div class="container">
		<h2>用户登录</h2>
		<form:form action="login.html" modelAttribute="sysUser" method="POST"
			class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-2">用户名:</label>
				<div class="col-sm-6">
					<form:input type="text" path="account" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">密码:</label>
				<div class="col-sm-6">
					<form:input type="password" path="password" class="form-control" />
				</div>

			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" value="登陆" class="btn btn-primary" />

				</div>
			</div>
			<div class="form-group">
				<c:if test="${not empty error}">
					<h4>
						<span class="label label-danger col-sm-offset-3">请输入正确的用户名和密码</span>
					</h4>
				</c:if>
			</div>

		</form:form>


	</div>

</body>

</html>

