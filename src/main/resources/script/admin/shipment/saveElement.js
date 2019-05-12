$(document).ready(function () {
    $("body").on("click",".save-element",function (event) {
        var id = $(this).parents(".list-shipment-element-container").attr("id");
        saveElement(id);
    });
});

function saveElement(id) {
    var parent = $("#"+id);
    var manager = parent.find(".manager-input").val();
    var contractor = parent.find(".contractor-select").val();
    var city = parent.find(".city-input").val();
    var person = parent.find(".person-input").val();
    var project = parent.find(".project-input").val();
    var date = parent.find(".date-input").val();
    var packet = parent.find(".packet-input").val();
    var tablets = parent.find(".result-tablet-hidden").val();
    var akb = parent.find(".result-akb-hidden").val();


    if (manager===""||contractor===""||city===""||person===""||project===""||date===""){
        messageSaveShipment("Заполните все поля (кроме комментария) в данных отправки.");
    } else
    if (packet===""){
        messageSaveShipment("Укажите номер пакета.");
    } else
    if (tablets===""&&akb===""){
        messageSaveShipment("Найдите в поиске хотя бы одно устройство.");
    } else {
        parent.find(".text-headline p:first-child").text(contractor);
        parent.find(".text-headline p:nth-child(2)").text(city);
        parent.find(".text-headline p:nth-child(4)").text(tablets);
        parent.find(".text-headline p:nth-child(5)").text(akb);
        parent.find(".text-headline p:nth-child(6)").text(packet);
        parent.toggleClass("height-inter-container");
    }
    $(".result-to-db").prop("disabled", true);
}

function messageSaveShipment(message) {
    return $("#message-for-ajax").html("<div class=\"error-background\">\n" +
        "            <div class=\"error-container\">\n" +
        "                <p class=\"error-message\">"+message+"</p>\n" +
        "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
        "            </div>\n" +
        "        </div>");
}