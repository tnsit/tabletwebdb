$(document).ready(function () {
    $(".contractor-select-soft").on("change", function () {
        if ($(this).val()!=="Выберите подрядчика"){
            $("#result-contractor-soft p").text($(this).val());
        }
    })
});