$(document).ready(function () {
    $( ".search-clear" ).click(function () {
        $( ".inter-main-search" ).val("");
        $.each($(".element-container"), function() {
            $(".element-container").show();
            $(this).removeClass("found-elm");
        });
    });
});