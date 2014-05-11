$(document).ready(function(){



 $("#category_btn").click(function(){
   var category=$(".category").val();
   //alert(category);
$.ajax({
type: "GET",
url: "createcategory.php",
data:"category="+category
})
.done(function( msg ) {


$("#feedback").html(msg);


});
 
});
$( ".category" ).focus(function() {
//alert( "Handler for .focus() called." );
$("#feedback").html("");
$(".category").html("");
});


});
