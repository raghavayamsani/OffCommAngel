<?php
include "../connection.php";
if(isset($_GET['category'])&&(!empty($_GET['category']))){

$category=$_GET['category'];
$category = stripslashes($category);
$category = mysql_real_escape_string($category);
//echo $category;
$query="create table ".$category."( id INT NOT NULL AUTO_INCREMENT,
     item CHAR(30) NOT NULL UNIQUE,rate int not null,description varchar(500),image varchar(30) NOT NULL,PRIMARY KEY (id,item))";
 if( !( $selectRes = mysql_query($query) ) ){
    echo 'creating category  Failed - #'.mysql_errno().': '.mysql_error();
  }else{

echo "category created";
}
}
else{

echo "errorr..";
}

?>
