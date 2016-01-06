<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="ISO-8859-1">
<html>
<head>
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.css" />">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-2.1.4.min.js" />"></script>

<title>Github Issue</title>
</head>
<body>
	<div class="container">
		<div class="page">
			<div id='loadingmessage' style='display: none'>
				Sit back while we crunch numbers for you.<br> <img
					src='<c:url value="/resources/images/loader.gif" />' />
			</div>
			<h1>Github</h1>
			<hr />
			<div id="alert" class="alert alert-danger" style='display: none'>
				<strong>Alert!</strong>
				<div id='error'></div>
			</div>
			<form id="form">
				<label>Repo Url</label> <input id="repo" name="repo" type="text">
				<hr />
				<button type="submit" class="btn">Submit</button>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
$("form").submit(function (e) {
	e.preventDefault();
	$("html").fadeOut();
	var url = $('#repo').val();
	$('#loadingmessage').show(); // show the loading message.
	$.ajax({
		url : "/shippable/issues",
		type : "POST",
		cache : false,
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify({
			repoUrl : url
		}),
		success : function(html) {
			console.log("success");
			$('#loadingmessage').hide(); // hide the loading message
		},
		error : function(html) {
			$('#alert').text(html.responseJSON.message);
			$('#alert').show();
			$('#loadingmessage').hide();
			
			console.log("error");
		}
	});
});
</script>
</html>