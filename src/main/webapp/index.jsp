<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="ISO-8859-1">
<html>
<head>
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.css" />">
<title>Github Issue</title>
</head>
<style>
.loading-gif {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -50px 0px 0px -50px;
}
</style>
<body>
	<div class="container">
		<div class="page">
			<div id='loadingmessage'>
				<div class='loading-gif' style='text-align: center'>
					Sit back while we crunch numbers for you. <br> <img
						src='<c:url value="/resources/images/loader.gif" />' />
				</div>

			</div>
			<h1>Github</h1>
			<hr />
			<div id="alert" class="alert alert-danger" style='display: none'>
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong>Alert!</strong>
				<div id='error'></div>
			</div>

			<form id="form">
				<label>Repo Url</label> <input id="repo" name="repo" type="text">
				<hr />
				<button type="button" id="submit" class="btn">Submit</button>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js" />"></script>
<script type="text/javascript">
	$("#submit").click(function() {
		var url = $('#repo').val();
		if (url == '') {
			$('#error').text("Please enter url");
			$('#alert').show();
			return;
		}
		$("html").fadeOut();

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
				$("html").fadeIn();
				console.log("success");
				$('#loadingmessage').hide(); // hide the loading message
				return;
			},
			error : function(html) {
				$("html").fadeIn();
				$('#alert').text(html.responseJSON.message);
				$('#alert').show();
				//$('#loadingmessage').hide();

				console.log("error");
				return;
			}
		});
	});
</script>
</html>