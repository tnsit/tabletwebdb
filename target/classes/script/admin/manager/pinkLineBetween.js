$(document).ready(function () {
   $(".between-check").on("change", function () {
       if ($(this).is(":checked")){
           $(this).parents(".between-element").css("background-color", "#ffdbef");
           $(this).parents(".between-element").addClass("border-bottom-pink");
       }
       else {
           $(this).parents(".between-element").css("background-color", "transparent");
           $(this).parents(".between-element").removeClass("border-bottom-pink");
       }
   })
});