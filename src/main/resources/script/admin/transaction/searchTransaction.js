$(document).ready(function(){
    $("#user-search").keyup(function(){
        _this = this;

        $.each($(".list-element-container"), function() {
            if(($(this).find(".list-element-headline p:first-child").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".list-element-headline p:nth-child(2)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".list-element-headline p:nth-child(3)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".list-element-headline p:nth-child(4)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".list-element-headline p:nth-child(5)").text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)
                ) {
                $(this).hide();
                $(this).removeClass("found-elm");

            } else {
                $(this).show();
                $(this).addClass("found-elm");
            }
            });
        if ($(_this).val()===""){
            $(".found").hide();
        }
        else{
            $found = $(".found");
            $found.show();
            $found.text("Найдено: "+$(".found-elm").length);
        }

    });
});