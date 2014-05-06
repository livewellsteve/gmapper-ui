<%@page import="controller.model.PostgresMeta"%>
<%@page import="controller.model.MysqlMeta"%>
<%@page import="controller.model.GetFullAccessName"%>
<%@page import="java.sql.DriverManager,controller.model.ConfigData,java.util.ArrayList,java.sql.ResultSet,java.sql.SQLException,java.sql.DatabaseMetaData,java.sql.Connection,com.jolbox.bonecp.BoneCPDataSource"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
<%ConfigData bean = (ConfigData) session.getAttribute("bean"); %>

<%
ArrayList<String> objSource = new ArrayList<String>();
ArrayList<String> objTarget = new ArrayList<String>();

if (bean.getSourceType().equals("mysql")){
	objSource = new MysqlMeta(GetFullAccessName.getNameMysql(bean.getSourceEndpoint(), bean.getSourceDbName()),
			bean.getSourceUser(),bean.getSourcePassword()).getTables();
	
}
else if (bean.getSourceType().equals("postgres")){
	objSource = new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getSourceEndpoint(), bean.getSourceDbName()),
			bean.getSourceUser(),bean.getSourcePassword()).getTables();
}


if (bean.getTargetType().equals("mysql")){
	objTarget = new MysqlMeta(GetFullAccessName.getNameMysql(bean.getTargetEndpoint(), bean.getTargetDbName()),
			bean.getTargetUser(),bean.getTargetPassword()).getTables();
}
else if (bean.getTargetType().equals("postgres")){
	objTarget = new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getTargetEndpoint(), bean.getTargetDbName()),
			bean.getTargetUser(),bean.getTargetPassword()).getTables();
}
%>
<form action="/GMapper-protov4/MapperConfigControl" method="post">
Mapper name: <input type="text" name="mapperName"><br>
<fieldset>
<legend>Objects from source</legend>
<select id="tables">
<option value="">Please select</option>
<% 
for (String s: objSource){
	out.println("<option value='"+s+"'>" + s +"</option>");
}

%></select></fieldset><br>

<fieldset>
<legend>Objects from target</legend>
<% 
for (String s: objTarget){
	out.println(s+",");
}
%></fieldset><br>
Source Object to be integrate: <input type="text" name="sourceObject">
Target Object to be integrate: <input type="text" name="targetObject"><br>
Description: <textarea name="description"></textarea>
<br><input type="submit" value="submit">
</form>
</body>
</html>