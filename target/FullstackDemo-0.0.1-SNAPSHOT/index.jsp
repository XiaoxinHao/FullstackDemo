<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	function showUserJson() {
		$.ajax({
			url : 'userController/user/2',
			type : 'GET',
			beforeSend : function(req) {
				req.setRequestHeader("Accept", "application/json");
			},
			contentType : 'application/json'
		}).done(function(data, status, xhr) {
			alert("成功：" + data.name);
		}).fail(function(xhr, status, error) {
			alert("成功" + error);
		});
	}

	function showUserXML() {
		$.ajax({
			url : 'userController/user/2',
			type : 'GET',
			beforeSend : function(req) {
				req.setRequestHeader("Accept", "text/xml");
			},
			contentType : 'application/xml'
		}).done(function(data, status, xhr) {
			alert("成功：" + data);
		}).fail(function(xhr, status, error) {
			alert("成功：" + error);
		});
	}
</script>
</head>
<body>
	Hello World! Ajax Test:
	<br />
	<input type='button' onclick="showUserJson()" value="showUserJson" />
	<br />
	<input type='button' onclick="showUserXML()" value="showUserXML" />
	<br />
</body>
</html>