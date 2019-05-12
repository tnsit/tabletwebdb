$(document).ready(function(){
    $("#user-search").keyup(function(){
        _this = this;

        $.each($(".user-line-name"), function() {
            if($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                $(this).parents(".user-container").hide();
            } else {
                $(this).parents(".user-container").show();}
            });
    });
});