<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>



<html>

<head>
<title>List Accounts</title>

<!-- bootstrap stuff -->

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="styles/bootstrap.css">
	<script type="text/javascript" src="scripts/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="styles/jquery.dataTables.min.css"/>
	<script type="text/javascript" src="scripts/datatables.js"></script>

</head>


<body>

	<!-- prevent unauthenticated access -->
	<%
		//disable the goBack function after user logout. 
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

		if (session.getAttribute("account") == null) {
			response.sendRedirect("showFormForLogin");
		}
	%>


	<!-- navigation bar -->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">文件上传管理系统</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="list.html">主页</a></li>

			</ul>
			<!-- put a button upload file -->
			<input type="button" value="创建用户"
				onclick="window.location.href='showFormForAccountCreate.html'; return false;"
				class="btn btn-info navbar-btn" />
			<!-- put a button for logout -->
			<input type="button" value="登  出"
				onclick="window.location.href='logOut.html'; return false;"
				class="btn btn-danger navbar-btn" />
						<!-- put a button for managing account -->
			<c:if test="${userRight == '1'}">
				<input type="button" value="文件管理"
					onclick="window.location.href='list.html'; return false;"
					class="btn btn-warning navbar-btn" />
			</c:if>
				
		</div>
	</nav>

	<div class="container">
		<h2>用户列表</h2>


			<!-- add out html table here -->

			<table id="ListTable" class="table table-striped">
				<thead>
				<tr>
					<th>公证员姓名</th>
					<th>用户名</th>
					<th>所属部门</th>
					<th>密码</th>
					<th>用户权限</th>
					<th>创建日期</th>
					<th>更新日期</th>
					<th>动作</th>
				</tr>
				</thead>
				
				<tbody id="myTable">
				<!-- loop over and print our customers -->
				<c:forEach var="tempAccount" items="${accounts}">

					<!-- construct and "edit" link with file id -->
					<c:url var="updateLink" value="/showFormForAccountUpdate.html">
						<c:param name="id" value="${tempAccount.id}" />
					</c:url>

					<!-- construct and "delete" link with file id -->
					<c:url var="deleteLink" value="/deleteAccount.html">
						<c:param name="id" value="${tempAccount.id}" />
					</c:url>

					<tr>
						<td>${tempAccount.userName}</td>
						<td>${tempAccount.account}</td>
						<td>${tempAccount.departmentName}</td>
						<td>${tempAccount.password}</td>
						<td>${tempAccount.userRight}</td>
						<td>${tempAccount.createtime}</td>
						<td>${tempAccount.updatetime}</td>

						<td>
							<!-- display the edit link --> 
							<a href="${updateLink}">编辑</a> |
							<a href="${deleteLink}"
							onclick="if (!(confirm('你确定要删除这个用户吗?'))) return false">删除</a>
						</td>
					</tr>


				</c:forEach>
				</tbody>


			</table>

		
	</div>
	
<script>

$(document).ready(function() {
    $('#ListTable').DataTable( {
        "lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
        "ordering": false,
        language: {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项结果",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }
    
    } );
} );




</script>
	

</body>
</html>