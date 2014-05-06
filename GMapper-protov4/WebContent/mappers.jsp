<%@page import="controller.model.ConfigData"%>
<%@ page language="java" contentType="text/html"%>
<%@page import ="java.util.HashMap"%>
<%@page import="controller.MapperDB"%>
<%@page import="java.util.Map"%>
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
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Hello <i class="fam-user"></i> <b class="caret"></b></a>
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

      <div class="page-header">
        <h1>Mappers</h1>
      </div>
      <div class="row">
        <div class="col-sm-12">
        
         <form action="editmapper.jsp">
       <button type="submit" class="btn btn-primary" style="margin-bottom: 10px;"><i class="fam-table-add"></i> Add New Mapper</button>
           </form>
        
       <div class="panel panel-success">
            <div class="panel-heading">
              <h3 class="panel-title"><i class="fam-table-relationship"></i> Mappers</h3>
            </div>

            <div class="panel-body">
              <div class="list-group">
            <% 
            ConfigData bean = (ConfigData) session.getAttribute("bean");
            int userId = bean.getUserId();
            Map<String,String> map = MapperDB.getMapperDesc(userId);
            for (Map.Entry<String,String> entry: map.entrySet()){
            %>
                <a href="#" class="list-group-item">
                  <h4 class="list-group-item-heading"><%=entry.getKey()%></h4>
                  <p class="list-group-item-text"><%=entry.getValue()%></p>
                </a>
                <%} %>
              
          
              </div>
            </div>
          </div>
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