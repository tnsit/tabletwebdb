$(document).ready(function () {
    var dom = $( "#user-search" );
    dom.click(function () {
        $( ".search-icon" ).css("-webkit-filter", "none");
    });
    dom.blur(function () {
        $( ".search-icon" ).css("-webkit-filter", "invert(47%) sepia(87%) saturate(0%) hue-rotate(39deg) brightness(92%) contrast(90%)");
    });
});