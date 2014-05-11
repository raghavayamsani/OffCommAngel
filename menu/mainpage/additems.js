$(document).ready(function(){



 $("#item_btn").click(function(){
   var category=$(".category").val();
   //alert(category);
var item=$(".item").val();
var rate=$(".rate").val();
var description=$(".description").val();
var image=$(".image").val();
alert(image);
$.ajax({
type: "POST",
url: "createitems.php",
data:{category:category,item:item,rate:rate,description:description,image:image}
})
.done(function( msg ) {


$("#feedback").html(msg);


});
 
});

$( ".item" ).focus(function() {
//alert( "Handler for .focus() called." );
$("#feedback").html("");
});


});
