$(document).ready(function () {
   $("#type").on("change", function () {
       if ($(this).val()==="Подрядчик заберет сам"){
           $("#template-shipment-container").hide();
           $("#other-shipment-container").hide();
           $("#soft-shipment-container").show();
       }

       if ($(this).val()==="По шаблону"){
           $("#soft-shipment-container").hide();
           $("#other-shipment-container").hide();
           $("#template-shipment-container").show();
       }

       if ($(this).val()==="Нестандартная отправка"){
           $("#template-shipment-container").hide();
           $("#soft-shipment-container").hide();
           $("#other-shipment-container").show();
       }
   })
});