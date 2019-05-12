$(document).ready(function () {
   $(".element-container .checkbox-container input").on("change", function () {
       var element = $(this).parents(".element-container");
       if ($(this).is(":checked")){
           element.find(".checkbox-container").css("background-color", "#ffdbef");
           element.find(".element-info-container").css("background-color", "#ffdbef");
           element.find(".element-button-container").css("background-color", "#ffdbef");
           element.addClass("border-bottom-pink");
       }
       else {
           element.find(".checkbox-container").css("background-color", "transparent");
           element.find(".element-info-container").css("background-color", "transparent");
           element.find(".element-button-container").css("background-color", "transparent");
           element.removeClass("border-bottom-pink");
       }
   })
});