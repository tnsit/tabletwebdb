$(document).ready(function () {
    $(".list-element-form").each(function () {
        if($(this).find(".akb-place").val()!=="склад"){
            $(this).find(".akb-number").prop("readonly", true);
        }
    });
});