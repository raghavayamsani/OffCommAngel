<?php
include "../connection.php";
if(isset($_POST['category'])&&(!empty($_POST['category']))&&isset($_POST['item'])&&(!empty($_POST['item']))){
$path="/var/www/menu/mainpage/digitalmenuimages/";
$category=$_POST['category'];
$category = stripslashes($category);
$category = mysql_real_escape_string($category);
$item=$_POST['item'];
$item = stripslashes($item);
$item = mysql_real_escape_string($item);
$rate=$_POST['rate'];
$rate = stripslashes($rate);
$rate = mysql_real_escape_string($rate);
$description=$_POST['description'];
$description = stripslashes($description);
$description = mysql_real_escape_string($description);
$image=$_POST['image'];

$content=$path.$image;

$query="insert into ".$category."(item,rate,description,image) values('".$item."','".$rate."','".$description."','".$content."')";

//echo $query;

$selectRes = mysql_query($query) or die(mysql_error()) ;

 if( !$selectRes ){

    echo 'creating category  Failed - #'.mysql_errno().': '.mysql_error();
  
}else{

 header('location:additems.php');
}



}
else{

echo "error..";

}

?>
