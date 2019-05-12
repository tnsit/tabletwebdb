$(document).ready(function () {
    $( ".search-clear" ).click(function () {
        $( ".search-input" ).val("");
        $.each($(".list-container-visible .list-element-container"), function() {
            $(".list-container-visible .list-element-container").show();
        });
    });
});