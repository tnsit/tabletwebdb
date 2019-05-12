$(document).ready(function(){
    $("#inter-main-search").keyup(function(){
        $(".element-container .check").each(function () {
            if($(this).is(":checked")){
                $(this).click();
            }
        });
        $(".check-all").prop("checked", false);
        _this = this;

        var element = $(".element-container");
        element.each(function() {
            if(($(this).find(".element-info-container p:nth-child(1)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".element-info-container p:nth-child(2)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".element-info-container p:nth-child(3)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".element-info-container p:nth-child(4)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".manager-comment").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)
            ) {
                $(this).hide();
                $(this).removeClass("found-elm");

            } else {
                $(this).show();
                $(this).addClass("found-elm");
            }
        });

        if ($(_this).val()===""){
            element.each(function() {
                $(this).removeClass("found-elm");
            })
        }
    });
});