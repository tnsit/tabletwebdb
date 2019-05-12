$(document).ready(function () {
    $(".city-select-soft").on("change", function () {
        if ($(this).val()!=="Выберите город"){
            $("#result-city-soft p").text($(this).val());
        }
    })
});

$(document).ready(function () {
    $(".city-select-other").on("change", function () {
        if ($(this).val()!=="Выберите город"){
            $("#result-city-other p").text($(this).val());
        }
    })
});