$(document).ready(function(){
    $("#search").keyup(function(){
        _this = this;

        $.each($(".list-container-visible .list-element"), function() {
            if($(this).val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                $(this).parents(".list-element-container").hide();
            } else {
                $(this).parents(".list-element-container").show();}
            });
    });
});