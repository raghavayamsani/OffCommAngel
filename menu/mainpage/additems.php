


<html>
<head>
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="addmenu.css" rel="stylesheet"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- <script src="additems.js"></script> -->

</head>

<body>

 <form class="container" enctype="multipart/form-data" method="post" action="createitems.php">

      <div class="form-signin">
        <h2 class="form-signin-heading">Select The Category</h2>
<?php
include "../connection.php";
$x=0;
$query="Show tables";
echo '<select name="category" class="form-control input-lg category">'; // Open your drop down box
if( !( $selectRes = mysql_query($query) ) ){

}
else{
// Loop through the query results, outputing the options one by one
while ($row = mysql_fetch_array($selectRes)) {
    if($x==0){
$x++;
}
    else{
   echo '<option value="'.$row["Tables_in_menu"].'">'.$row["Tables_in_menu"].'</option>';
}
}

echo '</select>';

}
?>
        <input type="text" class="form-control item" name="item"  placeholder="enter item" required autofocus>
        <input type="text" class="form-control rate" name="rate"  placeholder="enter rate">
       <input type="text" class="form-control description" name="description"  placeholder="enter description">
       <input type="file"accept="image/jpeg" name="image" class="image"/>
       <h2 class="form-signin-heading" id="feedback"></h2>
        
        <button class="btn btn-lg btn-primary btn-block " id="item_btn" name="submit">Submit</button>
        
      </div>

    </form> 

</body>

</html>
