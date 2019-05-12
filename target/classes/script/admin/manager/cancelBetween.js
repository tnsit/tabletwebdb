$(document).ready(function () {
   $("#between-cancel").on("click", function () {
       $(".between-window").hide();
       $(".between-manager-result p").text("");
       $("#between-search").val("");
       $(".manager-element").each(function () {
           $(this).find(".between-radio-group").prop("checked", false);
           $(this).show();
       });
   })
});