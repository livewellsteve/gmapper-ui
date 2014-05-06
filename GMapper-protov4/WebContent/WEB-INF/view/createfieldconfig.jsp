<%@page import="controller.model.PostgresMeta"%>
<%@page import="controller.model.MysqlMeta"%>
<%@page import="controller.model.GetFullAccessName"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.sql.DriverManager,controller.model.ConfigData,java.util.ArrayList,java.sql.ResultSet,java.sql.SQLException,java.sql.DatabaseMetaData,java.sql.Connection,com.jolbox.bonecp.BoneCPDataSource"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
<h2>Final step! Submit kicks off mapping!</h2>

<%ConfigData bean = (ConfigData) session.getAttribute("bean"); 
Map<String,String> fieldSource = new HashMap<String,String>();
Map<String,String> fieldTarget = new HashMap<String,String>();

if (bean.getSourceType().equals("mysql")){
	fieldSource = new MysqlMeta(GetFullAccessName.getNameMysql(bean.getSourceEndpoint(), bean.getSourceDbName()),
			bean.getSourceUser(),bean.getSourcePassword()).getFieldsTypes(bean.getSourceObject());
	
}
else if (bean.getSourceType().equals("postgres")){
	fieldSource = new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getSourceEndpoint(), bean.getSourceDbName()),
			bean.getSourceUser(),bean.getSourcePassword()).getFieldsTypes(bean.getSourceObject());
}

if (bean.getTargetType().equals("mysql")){
	fieldTarget = new MysqlMeta(GetFullAccessName.getNameMysql(bean.getTargetEndpoint(), bean.getTargetDbName()),
			bean.getTargetUser(),bean.getTargetPassword()).getFieldsTypes(bean.getTargetObject());
}
else if (bean.getTargetType().equals("postgres")){
	fieldTarget = new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getTargetEndpoint(), bean.getTargetDbName()),
			bean.getTargetUser(),bean.getTargetPassword()).getFieldsTypes(bean.getTargetObject());
}

bean.setSourceField(fieldSource);
bean.setTargetField(fieldTarget);
session.setAttribute("bean", bean);
%>
<%-- 
for (Map.Entry<String,String> entry: fieldSource.entrySet()){
	out.println(entry.getKey()+" "+entry.getValue());
}

for (Map.Entry<String,String> entry: fieldTarget.entrySet()){
	out.println(entry.getKey()+" "+entry.getValue());
}
--%>
<form action="/GMapper-protov4/FieldControl" method="post">
<select name="action">
<option value="insert">Insert</option>
<option value="update">Update</option>
<option value="delete">Delete</option><br>
</select><br>
<fieldset>
<legend>Fields from source</legend>
<%
for (Map.Entry<String,String> entry: fieldSource.entrySet()){
	out.println(entry.getKey()+"("+entry.getValue()+"), ");
}
%>
</fieldset><br>
<fieldset>
<legend>Fields from target</legend>
<%
for (Map.Entry<String,String> entry: fieldTarget.entrySet()){
	out.println(entry.getKey()+"("+entry.getValue()+"), ");
}
%>
</fieldset><br>
Source field to be integrate: <input type="text" name="sourceField">
Target field to be integrate: <input type="text" name="targetField"><br>
<input type="submit" value="start mapper">
</form>


</body>
</html>