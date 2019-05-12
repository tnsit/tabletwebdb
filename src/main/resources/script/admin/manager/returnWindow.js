$(document).ready(function () {
    $(".return-button").on("click", function () {
        var checked = $(".check:checked");
        if ($(this).parents(".element-container").find(".check").is(":checked")&&checked.length!==1){
            var col=0;
            var tablets = "";
            checked.each(function () {
                if ($(this).parents(".element-container").find(".element-info-container p:nth-child(4)").text()===""){
                    col++;
                    tablets = tablets+$(this).parents(".element-container").find(".element-info-container p:nth-child(1)").text()+" ";
                }
            });
            var tabletsResult="";
            if (col>1){
                tabletsResult = "["+col+"] "+tablets;
                $(".return-result p").text(tabletsResult);
                $(".return-window").show();
            }
            if (col===1){
                tabletsResult = tablets;
                $(".return-result p").text(tabletsResult);

                $(".return-window").show();
            }


        }
        else {
            $(".return-result p").text($(this).parents(".element-container").find(".element-info-container p:nth-child(1)").text());
            $(".return-window").show();

        }
    })
});