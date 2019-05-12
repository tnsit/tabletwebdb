$(document).ready(function () {
    $(".to-cancel-button").on("click", function () {
        var checked = $(".to-check:checked");
        if ($(this).parents(".to-element").find(".to-check").is(":checked")&&checked.length!==1){
            var col=0;
            var tablets = "";
            checked.each(function () {
                col++;
                tablets = tablets+$(this).parents(".to-element").find(".to-element-number").text()+" ";
            });
            var tabletsResult="";
            if (col>1){
                tabletsResult = "["+col+"] "+tablets;
                $(".between-cancel-window .takeoff-inter-result p").text(tabletsResult);
                $(".between-cancel-window").show();
            }
            if (col===1){
                tabletsResult = tablets;
                $(".between-cancel-window .takeoff-inter-result p").text(tabletsResult);

                $(".between-cancel-window").show();
            }


        }
        else {
            $(".between-cancel-window .takeoff-inter-result p").text($(this).parents(".to-element").find(".to-element-number").text());
            $(".between-cancel-window").show();

        }
    })
});