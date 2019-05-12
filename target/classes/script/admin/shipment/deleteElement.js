$(document).ready(function () {
    $("body").on("click",".element-delete",function (event) {
        var id = $(this).parents(".list-shipment-element-container").attr("id");
        deleteElement(id);
    });
});
function deleteElement(id) {
    var parent = $("#"+id);
    parent.remove();
    $(".result-to-db").prop("disabled", true);
}