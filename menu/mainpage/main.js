$(document).ready(function(){

 $(".addmenu").click(function(){
	//alert("test");
$("#content_div").load("addmenu.php",function(responseTxt,statusTxt,xhr){
    if(statusTxt=="error")
      alert("Error: "+xhr.status+": "+xhr.statusText);
  });



});
 $(".editmenu").click(function(){
	//alert("test");
$("#content_div").load("editmenu.php",function(responseTxt,statusTxt,xhr){
    if(statusTxt=="error")
      alert("Error: "+xhr.status+": "+xhr.statusText);
  });



});
 $(".next").click(function(){
	//alert("test");
$("#content_div").load("additems.php",function(responseTxt,statusTxt,xhr){
    if(statusTxt=="error")
      alert("Error: "+xhr.status+": "+xhr.statusText);
  });



});
}); 
