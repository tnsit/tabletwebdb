$(document).ready(function () {
   $(".add-inter-button").on("click", function () {
       var checked = $(".check:checked");
       if ($(this).parents(".element-container").find(".check").is(":checked")&&checked.length!==1){
           var col=0;
           var tablets = "";
           checked.each(function () {
               if ($(this).parents(".element-container").find(".element-info-container p:nth-child(4)").text()===""){
                   col++;
                   tablets = tablets+$(this).parents(".element-container").find(".element-info-container p:nth-child(1)").text()+" ";
               }
           });
           var tabletsResult="";
           if (col>1){
               tabletsResult = "["+col+"] "+tablets;
               $(".inter-tablet-result p").text(tabletsResult);
               $(".inter-element").each(function () {
                   if ($(this).find(".brigadir").attr("value")!=="1"){
                       $(this).hide();
                   }
               });
               $(".add-inter").show();
           }
           if (col===1){
               tabletsResult = tablets;
               $(".inter-tablet-result p").text(tabletsResult);
               
               $(".add-inter").show();
           }


       }
       else {
           $(".inter-element").each(function () {
               if ($(this).find(".can").attr("value")==="no"){
                   $(this).find(".inter-radio-group").prop("disabled", true);
               }
           });
           $(".inter-tablet-result p").text($(this).parents(".element-container").find(".element-info-container p:nth-child(1)").text());
           $(".add-inter").show();

       }
   });
});
