<?php
$username = "root";
$password = "santhu";
$hostname = "localhost"; 

//connection to the database
$dbhandle = mysql_connect($hostname, $username, $password)
  or die("Unable to connect to MySQL");
//echo "Connected to MySQL<br>";
$db_selected = mysql_select_db('menu', $dbhandle);
if (!$db_selected) {
    die ('Can\'t use offcom : ' . mysql_error());
    
}
?>
