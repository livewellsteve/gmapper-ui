<%@page import="controller.model.ConfigData"%>
<%@page import="controller.MapperDB"%>
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
            <li class="active"><a href="#"><i class="fam-house"></i> Dashboard</a></li>
            <li><a href="#about"><i class="fam-table-relationship"></i> Mappers</a></li>
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
<%
ConfigData bean = (ConfigData) session.getAttribute("bean");
int userId = bean.getUserId();
int count = MapperDB.getMapperCount(userId);
%>
    <div class="container">

      <div class="page-header">
        <h1>Dashboard</h1>
      </div>
      <div class="row">
        <div class="col-sm-4">
          <div class="panel panel-success">
            <div class="panel-heading">
              <h3 class="panel-title"><i class="fam-table-relationship"></i> <a href="mappers.jsp">Mappers</a></h3>
            </div>
            <div class="panel-body">
              You have <%=count %> mappers running.
            </div>
          </div>
        </div><!-- /.col-sm-4 -->
        
        <div class="col-sm-4">
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"><i class="fam-comment"></i> Messages</h3>
            </div>
            <div class="panel-body">
              You have no messages.
            </div>
          </div>
        </div><!-- /.col-sm-4 -->
        
        <div class="col-sm-4">
          <div class="panel panel-danger">
            <div class="panel-heading">
              <h3 class="panel-title"><i class="fam-bell-error"></i> Alerts</h3>
            </div>
            <div class="panel-body">
              You have no alerts configured.
            </div>
          </div>
        </div><!-- /.col-sm-4 -->
      </div>

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