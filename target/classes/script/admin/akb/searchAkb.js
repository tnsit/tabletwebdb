$(document).ready(function(){
    $("#user-search").keyup(function(){
        _this = this;

        $.each($(".list-element-container"), function() {
            if(($(this).find(".akb-number").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-model").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-status").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-place").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-city").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-user").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-interviewer").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-contractor").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".akb-resp").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)) {
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