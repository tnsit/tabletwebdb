$(document).ready(function () {
    $(".list-element-form").each(function () {
        if($(this).find(".tablet-place").val()!=="склад"){
            $(this).find(".tablet-number").prop("readonly", true);
        }
    });
});