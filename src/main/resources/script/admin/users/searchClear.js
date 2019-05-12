$(document).ready(function () {
    $( ".search-clear" ).click(function () {
        $( ".search-input" ).val("");
        $.each($(".user-container"), function() {
            $(".user-container").show();
        });
    });
});