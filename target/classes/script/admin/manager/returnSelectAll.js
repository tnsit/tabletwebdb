$(document).ready(function () {
   $(".return-check-all").on("change", function () {
       if($(".return-check-all").is(":checked")){
           $(".return-check").each(function () {
               if($(this).is(":checked")){

               }
               else {
                   $(this).click();
               }
           });

       }
       else {
           $(".return-check").each(function () {
               if($(this).is(":checked")){
                   $(this).click();
               }
           });

       }

   });
});