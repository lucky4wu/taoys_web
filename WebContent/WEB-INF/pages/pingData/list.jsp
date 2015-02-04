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
	pageSize:<input type="text" id="pageSize" name="pageSize" value="20" />
	pageNum:<input type="text" id="pageNum" name="pageNum" value="1" />
	<input type="button" id="btn" value="clickToShow" />
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
	var pageSize = $('#pageSize').val();
	var pageNum = $('#pageNum').val();
	$.ajax({
		type:"GET",
		url:"../pingData/getData",
		data:{"uname":uname, "startDate":startDate, "endDate":endDate, 
			"pageSize":pageSize, "pageNum":pageNum},
		success:function(data){
			var json = eval("("+data+")");
			var htmlstr = "<tr>";
			htmlstr += "<td>ID</td>";
			htmlstr += "<td>UName</td>";
			htmlstr += "<td>EMail</td>";
			htmlstr += "<td>IPAddress</td>";
			htmlstr += "<td>PingTimes</td>";
			htmlstr += "<td>TimeOut(ms)</td>";
			htmlstr += "<td>Status</td>";
			htmlstr += "<td>CreateTime</td></tr>";
			$.each(json, function(index, item){
				console.debug(item.id);
				htmlstr += "<tr><td>"+item.id+"</td>";
				htmlstr += "<td>"+item.uname+"</td>";
				htmlstr += "<td>"+item.email+"</td>";
				htmlstr += "<td>"+item.ipAddress+"</td>";
				htmlstr += "<td>"+item.pingTimes+"</td>";
				htmlstr += "<td>"+item.timeOut+"</td>";
				htmlstr += "<td>"+status2txt(item.status)+"</td>";
				htmlstr += "<td>"+item.createTime+"</td></tr>";
			});
			$('#content').html(htmlstr);
		}
	});
}
function status2txt(status){
	var txt = "";
	switch(status){
	case "1" :txt="Begin";
		break;
	case "2" : txt="End";
		break;
	}
	return txt;
}
</script>
</html>