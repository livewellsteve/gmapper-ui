<%@page import="controller.model.ConfigData"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>

<%
ConfigData bean =(ConfigData) session.getAttribute("bean");

if (bean.getTurn() == null){
	bean.setTurn("source");
}
String turn = bean.getTurn();
%>
<form action="/GMapper-protov4/AdaptorAccessConfig" method="post">
<h2>Add <%=turn %> adaptor</h2>
<select name="type">
<option value="mysql">mysql</option>
<option value="postgres">postgres</option>
</select><br>
endpoint(with port): <input type="text" name="endpoint" value="<%if(turn.equals("source")) {out.println("mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306");} else{out.println("mydbinstancepg1.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:5432");} %>">
dbname: <input type="text" name="db" value="<%if(turn.equals("source")) {out.println("dummydata");} else{out.println("postgres");} %>">
username: <input type="text" name="user" value="cmpe">
password: <input type="password" name="password" value="password">
<input type="submit" value="Submit"><br>
</form>
</body>
</html>