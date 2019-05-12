$(document).ready(function () {
   $(".tab-element").on("click", function () {
       var index = $(this).index();
       console.log("tab: "+index);
       $(".tab-element").each(function () {
           $(this).removeClass("tab-active");
       });
       $(this).addClass("tab-active");

       $(".main-container").each(function () {
           $(this).removeClass("show");
           console.log("main: "+index);
           if ($(this).index()-1===index){
               $(this).addClass("show");
           }

       });
   })
});