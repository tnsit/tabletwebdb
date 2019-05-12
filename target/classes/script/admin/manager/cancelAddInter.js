$(document).ready(function () {
   $("#inter-cancel").on("click", function () {
       $(".add-inter").hide();
       $(".inter-inter-result p").text("");
       $("#inter-search").val("");
       $(".inter-element").each(function () {
           $(this).find(".inter-radio-group").prop("checked", false);
           $(this).show();
       });
   })
});