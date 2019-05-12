$(document).ready(function () {
   $(".check-all").on("change", function () {
       if ($("#inter-main-search").val()===""){
           if($(".check-all").is(":checked")){
               $(".element-container .check").each(function () {
                   if($(this).is(":checked")){

                   }
                   else {
                       $(this).click();
                   }
               });

           }
           else {
               $(".element-container .check").each(function () {
                   if($(this).is(":checked")){
                       $(this).click();
                   }
               });

           }
       }

       else {
           if($(".check-all").is(":checked")){
               $(".found-elm .check").each(function () {
                   if($(this).is(":checked")){

                   }
                   else {
                       $(this).click();
                   }
               });

           }
           else {
               $(".found-elm .check").each(function () {
                   if($(this).is(":checked")){
                       $(this).click();
                   }
               });

           }
       }

   });
});