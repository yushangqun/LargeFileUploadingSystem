<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>



<html>

<head>
<title>List Files</title>

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
				<li class="active"><a href="#">主页</a></li>

			</ul>
			<!-- put a button upload file -->
			<input type="button" value="文件上传"
				onclick="window.location.href='fileUpload.html'; return false;"
				class="btn btn-info navbar-btn" />
			<!-- put a button for logout -->
			<input type="button" value="登  出"
				onclick="window.location.href='logOut.html'; return false;"
				class="btn btn-danger navbar-btn" />
						<!-- put a button for managing account -->
			<c:if test="${userRight == '1'}">
				<input type="button" value="用户管理"
					onclick="window.location.href='listAccount.html'; return false;"
					class="btn btn-warning navbar-btn" />
			</c:if>
				
		</div>
	</nav>

	<div class="container">
		

		<h2>文件列表</h2>
		

			<!-- add out html table here -->

			<table id="ListTable" class="table table-striped">
				<thead>
				<tr>
					<th>上传人姓名</th>
					<th>部门</th>
					<th>文件名</th>
					<th>录制时间</th>
					<th>上传日期</th>
					<th>文件大小</th>
					<th>动作</th>
				</tr>
				</thead>
				<tbody id="myTable">
				<!-- loop over and print files -->
				<c:forEach var="tempFile" items="${files}">

					<!-- construct and "edit" link with file id -->
										
					<c:url var="updateLink" value="/file/showFormForUpdate">
						<c:param name="id" value="${tempFile.filePath}" />
					</c:url>

					<!-- construct and "delete" link with file id -->
					<c:url var="deleteLink" value="deleteFile.html">
						<c:param name="id" value="${tempFile.id}" />
						<c:param name="filePath" value="${tempFile.filePath}" />
					</c:url>   
					
					<c:url var="dowloadLink" value="/downloadFile.html">
						<c:param name="filePath" value="${tempFile.filePath}" />
					</c:url>

					<tr>
						<td>${tempFile.sysUser.userName}</td>
						<td>${tempFile.sysUser.departmentName}</td>
						<td>${tempFile.fileName}</td>

						<td>${tempFile.recordDate}</td>
						<td>${tempFile.uploadDate}</td>
						<td class="fileSize">${tempFile.size}MB</td>
						
						<td>
							<!-- display the edit link --> 
							
							<a href="${dowloadLink}">下载</a> |
							<a href="${deleteLink}"
							onclick="if (!(confirm('你确定要删除这个文件吗?'))) return false">删除</a>
						</td>

					</tr>


				</c:forEach>
				</tbody>


			</table>

		
	</div>
	
	<script>
	
	function download(filePath){
		var FilePath = filePath;
		
		var basePath = "/FileUpload";
		jQuery.post(basePath + "/downloadFile.html", {
			"filename" : FilePath
		});
	}

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