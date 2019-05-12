$(document).ready(function () {
    $(".inter-radio-group").on("click", function () {
        if ($(this).is(":checked")){
            $(".inter-inter-result p").text($(this).siblings([".label-for-inter"]).text());
        }

    })
});