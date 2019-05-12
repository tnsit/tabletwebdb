$(document).ready(function () {
   $(".status-select-soft").on("change", function () {
       if ($(this).val()!=="Выберите статус"){
           $("#result-status-other p").text($(this).val());
       }
       else {
           $("#result-status-other p").text("");
       }
   })
});