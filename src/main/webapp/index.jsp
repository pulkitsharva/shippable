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
			<div id='loadingmessage'
				style="display: none; position: absolute; background-color: rgba(0, 0, 0, 0.6); width: 100%; height: 100%; left: 0; top: 0;">
				<div class='loading-gif' style='text-align: center'>
					Sit back while we crunch numbers for you. <br> <img
						src="<c:url value="/resources/images/loader.gif" />"/>
				</div>
			</div>
			<h1>Github</h1>
			<hr />
			<div id="alert" class="alert alert-danger" style='display: none'>
				<a href="#" id="close" class="close" onclick="close();"
					data-dismiss="alert" aria-label="close">&times;</a> <strong>Alert!</strong>
				<div id='error'></div>
			</div>

			<form id="form">
				<label>Repo Url</label> <input id="repo" name="repo" type="text">
				<hr />
				<button type="button" id="submit" class="btn">Submit</button>
			</form>
			<div id="result" style="display:none" >
				<hr>
				<table class="table table-bordered">
					<thead>
						<tr>
						<th>Total Issue</th><th>Issue &lt; 1 day</th><th>1 day &lt; Issue &lt; 7 days</th><th>Issue &gt; 7 days</th>
						</tr>
						<tr>
						<td id="totalissues"></td>
						<td id="issuesIn24hr"></td>
						<td id="issuesMoreThan24hr"></td>
						<td id="issuesMoreThan7days"></td>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-2.1.4.min.js" />"></script>
<!-- <script type="text/javascript" -->
<%-- 	src="<c:url value="/resources/js/bootstrap.js" />"></script> --%>
<script type="text/javascript">
	$("#submit").click(function() {
		$('#submit').addClass('disabled');
		var url = $('#repo').val();
		if (url == '') {
			$('#error').text("Please enter url");
			$('#alert').show();
			$(this).removeClass('disabled');
			return;
		}
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
				$(this).removeClass('disabled');
				console.log("success");
				$('#loadingmessage').hide(); // hide the loading message
				$('#result').show();
				$('#totalissues').text(html.totalissues);
				$('#issuesIn24hr').text(html.issuesIn24hr);
				$('#issuesMoreThan24hr').text(html.issuesMoreThan24hr);
				$('#issuesMoreThan7days').text(html.issuesMoreThan7days);
				
				$('#submit').removeClass('disabled');
				return;
			},
			error : function(html) {
				if (html.status == '404') {
					$('#error').text(html.responseJSON.message);
				} else {
					$('#error').text(html.statusText);
				}
				$('#alert').show();
				$('#loadingmessage').hide();
				$('#submit').removeClass('disabled');
				return;
			}
		});
	});
	$('#close').click(function() {
		$('#alert').css('display', 'none');
	});
</script>
</html>