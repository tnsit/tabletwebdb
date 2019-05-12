$(document).ready(function () {
   $(".from-check-all").on("change", function () {
       if($(".from-check-all").is(":checked")){
           $(".from-check").each(function () {
               if($(this).is(":checked")){

               }
               else {
                   $(this).click();
               }
           });

       }
       else {
           $(".from-check").each(function () {
               if($(this).is(":checked")){
                   $(this).click();
               }
           });

       }

   });


    $(".to-check-all").on("change", function () {
        if($(".to-check-all").is(":checked")){
            $(".to-check").each(function () {
                if($(this).is(":checked")){

                }
                else {
                    $(this).click();
                }
            });

        }
        else {
            $(".to-check").each(function () {
                if($(this).is(":checked")){
                    $(this).click();
                }
            });

        }

    });
});