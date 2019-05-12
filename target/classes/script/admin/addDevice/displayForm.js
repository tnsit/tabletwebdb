$(document).ready(function () {
   $("#add-select").on("change", function () {
       if ($(this).val()==="Вручную"){
           $("#tablet-form").show();
           $("#from-excel").hide();
       }

       if ($(this).val()==="Из документа Excel"){
           $("#tablet-form").hide();
           $("#from-excel").show();
       }
   })
});