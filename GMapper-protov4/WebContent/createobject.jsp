<%@page import="controller.model.PostgresMeta"%>
<%@page import="controller.model.MysqlMeta"%>
<%@page import="controller.model.GetFullAccessName"%>
<%@page import="java.sql.DriverManager,controller.model.ConfigData,java.util.ArrayList,java.sql.ResultSet,java.sql.SQLException,java.sql.DatabaseMetaData,java.sql.Connection,com.jolbox.bonecp.BoneCPDataSource"%>

<%@ page language="java" contentType="text/html"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <title>G-Mapper</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">        
        <meta name="description" content="">
        <meta name="author" content="Jinho Park, Sung Kim">

        <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
        <link href="css/fam-icons.css" rel="stylesheet" type="text/css" />
        <link href="css/jumbotron.css" rel="stylesheet" />
        <link rel="shortcut icon" href="img/favicon.ico" />

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">G-Mapper</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="dashboard.jsp"><i class="fam-house"></i> Dashboard</a></li>
            <li class="active"><a href="#about"><i class="fam-table-relationship"></i> Mappers</a></li>
            <li><a href="#contact"><i class="fam-comment"></i> Messages</a></li>
            <li><a href="#contact"><i class="fam-bell-error"></i> Alerts</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Hello<i class="fam-user"></i> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#"><i class="fam-user-edit"></i> Profile</a></li>
                <li><a href="#"><i class="fam-lock-edit"></i> Change Password</a></li>
                <li class="divider"></li>
                <li><a href="#"><i class="fam-house-go"></i> Logout</a></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

  
<div class="container">
      <div>
        <h1>Select Mapper Object</h1><br/>
        <h3>Select Object(table) from source and target DBs, Name Mapper Object and description</h3>
      </div>
      <div class="row">
        <div class="col-sm-12">
        
       <form action="/GMapper-protov4/MapperConfigControl" method="post" > 
        
        
              <button type="submit" class="btn btn-primary" style="margin-bottom: 10px;"><i class="fam-table-add"></i> Save and Next</button>
     <br/><br/>
     
         <h4>Source object section</h4>
              <!-- list of available adapter -->
              <div class="form-group">


              <%
              
              ConfigData bean = (ConfigData) session.getAttribute("bean");
              ArrayList<String> objSource = new ArrayList<String>();
              ArrayList<String> objTarget = new ArrayList<String>();
              
/*               objSource = new MysqlMeta(GetFullAccessName.getNameMysql("mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306", "dummydata"),"cmpe","password").getTables();
              objTarget = new PostgresMeta(GetFullAccessName.getNamePostgres("mydbinstancepg1.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:5432", "postgres"),"cmpe","password").getTables();
        */       
                if (bean.getSourceType().equals("mysql")){
               	objSource = new MysqlMeta(GetFullAccessName.getNameMysql(bean.getSourceEndpoint(), bean.getSourceDbName()),
              			bean.getSourceUser(),bean.getSourcePassword()).getTables(); 
             //			System.out.println("source is mysql");
              	
              }
              else if (bean.getSourceType().equals("postgres")){
               	objSource = new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getSourceEndpoint(), bean.getSourceDbName()),
             			bean.getSourceUser(),bean.getSourcePassword()).getTables(); 
            	//  System.out.println("source is post");
              }


               if (bean.getTargetType().equals("mysql")){
             	objTarget = new MysqlMeta(GetFullAccessName.getNameMysql(bean.getTargetEndpoint(), bean.getTargetDbName()),
              			bean.getTargetUser(),bean.getTargetPassword()).getTables(); 
            	//  System.out.println("target is mysql");
              }
              else if (bean.getTargetType().equals("postgres")){
              	objTarget = new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getTargetEndpoint(), bean.getTargetDbName()),
             			bean.getTargetUser(),bean.getTargetPassword()).getTables(); 
            	//  System.out.println("target is post");
              }  
          
            for (String s: objSource){
            %>
           
              <input type="radio" name="sourceObject" value="<%=s%>"><%=s%><br/>
            <%	  
              }
              %>
               </div>
              <br/><br/>
          <h4>Target object section</h4>
              <!-- list of available adapter -->
              <div class="form-group">
      
              <%
              for (String s: objTarget){
            %>
           <input type="radio" name="targetObject" value="<%=s%>"><%=s%><br/>
            <%	  
              }
              %> 
              </div>

          <br /><br />
          Mapper Name:<input type="text" name="mapperName"><br>
     Description: <textarea name="description"></textarea><br>
     </form>
     
        </div><!-- /.col-sm-4 -->
    </div>

    <div class="container">
      <hr>

      <footer>
        <p>&copy; G-Mapper 2014</p>
      </footer>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>