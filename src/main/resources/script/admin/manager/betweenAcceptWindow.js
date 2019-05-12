$(document).ready(function () {
    $(".from-accept-button").on("click", function () {
        var checked = $(".from-check:checked");
        if ($(this).parents(".from-element").find(".from-check").is(":checked")&&checked.length!==1){
            var col=0;
            var tablets = "";
            checked.each(function () {
                col++;
                tablets = tablets+$(this).parents(".from-element").find(".from-element-number").text()+" ";
            });
            var tabletsResult="";
            if (col>1){
                tabletsResult = "["+col+"] "+tablets;
                $(".between-from-window .takeoff-inter-result p").text(tabletsResult);
                $(".between-from-window").show();
            }
            if (col===1){
                tabletsResult = tablets;
                $(".between-from-window .takeoff-inter-result p").text(tabletsResult);

                $(".between-from-window").show();
            }


        }
        else {
            $(".between-from-window .takeoff-inter-result p").text($(this).parents(".from-element").find(".from-element-number").text());
            $(".between-from-window").show();

        }
    })
});