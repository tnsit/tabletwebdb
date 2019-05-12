$(document).ready(function () {
    $(".between-radio-group").on("click", function () {
        if ($(this).is(":checked")){
            $(".between-manager-result p").text($(this).siblings([".label-for-between"]).text());
        }

    })
});