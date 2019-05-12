$(document).ready(function(){
    $("#user-search").keyup(function(){
        _this = this;

        $.each($(".list-element-container"), function() {
            if(($(this).find(".tablet-number").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-model").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-status").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-status-comment").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-imei").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-os").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-phone").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-pin").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-comment").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-place").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-city").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-user").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-purpose").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-interviewer").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-contractor").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-trans-date").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)&&
                ($(this).find(".tablet-resp").val().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1)) {
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