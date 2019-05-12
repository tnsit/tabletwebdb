$(document).ready(function () {
    $("body").on("click",".element-copy",function (event) {
        var id = $(this).parents(".list-shipment-element-container").attr("id");
        copyElement(id);
    });
});
function copyElement(id) {
    var maxId = 0;
    $.each($(".list-shipment-element-container"), function () {
        console.log("id: "+parseInt($(this).attr("id")));

        if (parseInt($(this).attr("id"))>maxId){
            maxId=parseInt($(this).attr("id"));
        }
        console.log("max: "+maxId);
    });


    var parent = $("#"+id);
    var clone = parent.clone().attr("id", maxId+1);
    clone.find(".text-headline p:nth-child(4)").text("");
    clone.find(".text-headline p:nth-child(5)").text("");
    clone.find(".text-headline p:nth-child(6)").text("");
    clone.find(".packet-input").val("");
    clone.find(".search-tablet-input").val("");
    clone.find(".list-headline-container").empty();
    clone.find(".list-element-container").empty();
    clone.find(".result-tablet p").text("");
    clone.find(".result-akb p").text("");
    clone.insertAfter(parent);
    $(".result-to-db").prop("disabled", true);


}