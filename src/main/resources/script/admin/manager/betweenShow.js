$(document).ready(function () {
    $(".between-button").on("click", function () {
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
            tabletsResult = "["+col+"] "+tablets;
            $(".between-tablet-result p").text(tabletsResult);
            $(".between-window").show();


        }
        else {
            $(".between-tablet-result p").text($(this).parents(".element-container").find(".element-info-container p:nth-child(1)").text());
            $(".between-window").show();

        }
    })
});