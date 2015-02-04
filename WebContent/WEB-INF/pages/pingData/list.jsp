<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<html>
<head>
	<title>Ping Data</title>
	<link rel="stylesheet" href="../js/jquery-ui-1.11.2.custom/jquery-ui.css" >
</head>
<body>
	<h1>${name }</h1>
	uname:<input type="text" id="uname" name="uname" value="" />
	startDate:<input type="text" class="datepicker" id="startDate" name="startDate" />
	endDate:<input type="text" class="datepicker" id="endDate" name="endDate" />
	<input type="button" id="btn" value="click" />
	<table id="content"></table>
</body>
<script type="text/javascript" src="../js/jquery-1.4.3.js" /></script>
<script type="text/javascript" src="../js/jquery-ui-1.11.2.custom/jquery-ui.js" /></script>
<script type="text/javascript">
$(function(){
	$('#btn').click(function(){
		list();
	});
	$( ".datepicker" ).datepicker();
});
function list(){
	var uname = $('#uname').val();
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	$.ajax({
		type:"GET",
		url:"../pingData/getData",
		data:{"uname":uname, "startDate":startDate, "endDate":endDate},
		success:function(data){
			var json = eval("("+data+")");
			var htmlstr = "";
			$.each(json, function(index, item){
				console.debug(item.id);
				htmlstr += "<tr><td>"+item.id+"</td>";
				htmlstr += "<td>"+item.uname+"</td>";
				htmlstr += "<td>"+item.email+"</td>";
				htmlstr += "<td>"+item.ipAddress+"</td>";
				htmlstr += "<td>"+item.pingTimes+"</td>";
				htmlstr += "<td>"+item.timeOut+"</td>";
				htmlstr += "<td>"+item.status+"</td>";
				htmlstr += "<td>"+item.createTime+"</td></tr>";
			});
			$('#content').html(htmlstr);
		}
	});
}
</script>
</html>