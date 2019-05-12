$(document).ready(function(){
    $("#inter-search").keyup(function(){
        _this = this;

        $.each($(".label-for-inter"), function() {
            if($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                $(this).parents(".inter-element").hide();
            } else {
                $(this).parents(".inter-element").show();}
            });
    });
});