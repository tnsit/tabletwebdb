$(document).ready(function(){
    $("#user-search").keyup(function(){
        _this = this;

        $.each($(".list-element-headline p:first-child"), function() {
            if($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                $(this).parents(".list-element-container").hide();
            } else {
                $(this).parents(".list-element-container").show();}
            });
    });
});