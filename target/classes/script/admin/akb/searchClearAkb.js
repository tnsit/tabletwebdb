$(document).ready(function () {
    $( ".search-clear" ).click(function () {
        $( ".search-input" ).val("");
        $.each($(".list-element-container"), function() {
            $(".list-element-container").show();
            $(".found").hide();
        });
    });
});