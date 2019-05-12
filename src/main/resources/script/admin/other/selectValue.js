$(document).ready(function () {
    $("#select-table").on('change', function () {
        $("#select-table option:selected").each(function () {
            $("#hidden-option-select").val($(this).attr("id"));
            if ($(this).text()==="Подрядчик"){
                $("#input-create").attr("placeholder", "4 заглавные англ. буквы");
            }
            else {
                $("#input-create").attr("placeholder", $(this).text());
            }

            $(".list-container").removeClass("list-container-visible");
            $("#"+$(this).attr("id")+"List").addClass("list-container-visible");
        });
   }).trigger("change");
});