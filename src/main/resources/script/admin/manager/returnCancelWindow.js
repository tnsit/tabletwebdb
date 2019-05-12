$(document).ready(function () {
    $(".return-element-button").on("click", function () {
        var checked = $(".return-check:checked");
        if ($(this).parents(".return-element").find(".return-check").is(":checked")&&checked.length!==1){
            var col=0;
            var tablets = "";
            checked.each(function () {
                col++;
                tablets = tablets+$(this).parents(".return-element").find(".return-element-number").text()+" ";
            });
            var tabletsResult="";
            if (col>1){
                tabletsResult = "["+col+"] "+tablets;
                $(".return-cancel-window .return-cancel-result p").text(tabletsResult);
                $(".return-cancel-window").show();
            }
            if (col===1){
                tabletsResult = tablets;
                $(".return-cancel-window .return-cancel-result p").text(tabletsResult);

                $(".return-cancel-window").show();
            }


        }
        else {
            $(".return-cancel-window .return-cancel-result p").text($(this).parents(".return-element").find(".return-element-number").text());
            $(".return-cancel-window").show();

        }
    })
});

$(document).ready(function () {
    $(".return-cancel-cancel").on("click", function () {
        $(".return-cancel-window").hide();
    })
});