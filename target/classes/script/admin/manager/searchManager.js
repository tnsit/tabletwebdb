$(document).ready(function(){
    $("#between-search").keyup(function(){
        _this = this;

        $.each($(".label-for-between"), function() {
            if($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                $(this).parents(".manager-element").hide();
            } else {
                $(this).parents(".manager-element").show();}
            });
    });
});