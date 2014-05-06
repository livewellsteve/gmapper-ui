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
          <form action="/GMapper-protov4/LoginControl" method="post" class="navbar-form navbar-right" role="form">
            <div class="form-group">
              <input type="text" name="user" placeholder="email" class="form-control">
            </div>
            <div class="form-group">
              <input type="password" name="password" placeholder="Password" class="form-control">
            </div>
            <button type="submit" class="btn btn-success"><i class="fam-user-go"></i>Sign in</button>
</form>
 <form action="createlogin.jsp" method="post" class="navbar-form navbar-right" role="form">
           
            <button id="register" class="btn btn-warning btn-large" data-toggle="popover" data-placement="bottom" data-content="You don't have an account?"><i class="fam-user-add"></i>Register</button>
          </form>
        </div><!--/.navbar-collapse -->
      </div>
    </div>

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h1>G-Mapper</h1>
        <p>is a DIaaS (Data Integration as a Service) solution that enables enterprise data interoperability of system objects. Integration of API objects with any application or data source will be possible without any programming knowledge. Easy-to-use web based UI tool simplifies the complex and time-consuming data integration process.  Data connectors for existing applications enable the direct collaboration between systems. A unified virtual data layer allows the business owner to have a single view of data across all the systems and applications rather than scattered and fractured data. With mobile/web-based mapping tool, data analyst can build and manage business rules without asking IT department. Core mapper APIs will be defined to give the ability for developers to add more connections to the G-Mapper. With a dashboard, administrator can monitor and control the service settings.</p>
        <p><a class="btn btn-primary btn-lg" role="button">Learn more &raquo;</a></p>
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
    <script>
      $('#register').popover();
    </script>
  </body>
</html>