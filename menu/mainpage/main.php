<?php

session_start();
if(!session_is_registered(username)){
header("location:../index.php");
}
?>
<html>
<head>
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="dashboard.css" rel="stylesheet"/>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script>
$("#content_div").load("addmenu.php",function(responseTxt,statusTxt,xhr){
    if(statusTxt=="error")
      alert("Error: "+xhr.status+": "+xhr.statusText);
  });


</script>

<script src="main.js"></script>

</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">CreateMenu</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#" class="addmenu">AddMenu</a></li>
            <li><a href="#" class="editmenu">EditMenu</a></li>
            
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </div>
<div id="content_div" >

<?php include("addmenu.php"); ?>
</div>
<footer>
<div class="centered">
<button class="btn btn-success btn-large active next" style="width:200px;">Enter Items <i class="icon-white icon-ok"></i></button></div>
</footer>
</body>
</html>

