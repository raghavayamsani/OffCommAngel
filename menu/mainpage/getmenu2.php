<?php
$host = "localhost";
$user = "root";
$pass = "santhu";
$database = "menu";
$x=0;
$linkID = mysql_connect($host, $user, $pass) or die("Could not connect to host.");
mysql_select_db($database, $linkID) or die("Could not find database.");

 
$tablequery="show tables";
if( !( $selectRes = mysql_query($tablequery) ) ){

}
else{

while ($row = mysql_fetch_array($selectRes)) {
    if($x==0){
$x++;
}else{
$result = mysql_query("SELECT *FROM ".$row["Tables_in_menu"]."") or die(mysql_error());	
	if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["id"] = $row["id"];
        $product["item"] = $row["item"];
        $product["rate"] = $row["rate"];
        $product["description"] = $row["description"];
        $product["image"] =$row["image"];
        // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
	
	
	}
	}
	
	}





?>
