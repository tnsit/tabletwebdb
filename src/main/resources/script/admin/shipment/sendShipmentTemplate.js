$(document).ready(function () {
    $("#send-template-shipment").submit(function (event) {
        event.preventDefault();
        sendShipmentTemplate();
    });

});

function sendShipmentTemplate() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: false,
        url: "templateParsing",
        data: new FormData($('#send-template-shipment')[0]),
        enctype: 'multipart/form-data',
        processData: false,
        cache: false,
        timeout: 600000,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        success: function (result) {
            $(".waiting-ajax").hide();
            $(".result-to-db").prop("disabled", true);
            if (result.status === "notFound"){
                $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                    "            <div class=\"error-container\">\n" +
                    "                <p class=\"error-message\">" + result.data["cnf"] + "</p>\n" +
                    "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                    "            </div>\n" +
                    "        </div>");
            }






            if (result.status === "Done") {

                $("#shipment-main").html("<div class=\"list-shipment-headline\">\n" +
                    "        <p title=\"Код подрядчика\">Кому</p>\n" +
                    "        <p>Город</p>\n" +
                    "        <p title=\"Общее количество планшетов, которые требуется отправить данному подрядчику в указанный город.\">К-во</p>\n" +
                    "        <p title=\"Количество планшетов в пакете\">Планшеты</p>\n" +
                    "        <p title=\"Количество аккумуляторов в пакете. Требуемое количество обычно указывается в комментарии к заявке.\">Акб</p>\n" +
                    "        <p>Пакет</p>\n" +
                    "    </div>");



                                $.each(result.data["list"], function (key, value) {
                    $("#shipment-main").append("<div class=\"list-shipment-element-container\" id=\""+key+"\">\n" +
                        "        <div class=\"list-element-headline\">\n" +
                        "            <div class=\"text-headline\" onclick=\"shipmentHeightDiv(this)\">\n" +
                        "            <p>"+JSON.parse(value)[1]+"</p>\n" +
                        "            <p>"+JSON.parse(value)[2]+"</p>\n" +
                        "            <p>"+JSON.parse(value)[7]+"</p>\n" +
                        "            <p></p>\n" +
                        "            <p></p>\n" +
                        "            <p></p>\n" +
                        "                <div class=\"clear-fix\"></div>\n" +
                        "            </div>\n" +
                        "            <div class=\"element-icons\">\n" +
                        "                <div class=\"element-copy\" title=\"Создать копию строки\"></div>\n" +
                        "                <div class=\"element-delete\" title=\"Удалить строку\"></div>\n" +
                        "                <div class=\"clear-fix\"></div>\n" +
                        "            </div>\n" +
                        "            <div class=\"clear-fix\"></div>\n" +
                        "        </div>\n" +
                        "\n" +
                        "        <div class=\"shipment-info\">\n" +
                        "            <p class=\"shipment-info-name\">Данные отправки</p>\n" +
                        "\n" +
                        "            <div class=\"manager-container info-input-container\">\n" +
                        "                <div class=\"label-container\"><label>Менеджер:</label><div class=\"clear-fix\"></div></div>\n" +
                        "                <input class=\"manager-input\" title=\"Менеджер\" type=\"text\" value=\""+JSON.parse(value)[0]+"\"/>\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <div class=\"contractor-city-container info-input-container\">\n" +
                        "                <div class=\"label-container\"><label>Подрядчик:</label><div class=\"clear-fix\"></div></div>\n" +
                        "                <select class=\"contractor-select\" title=\"Подрядчик\">\n" + optionSelected(result.data["contractor"],JSON.parse(value)[1])+
                        "                </select>\n" +
                        "                <select class=\"city-input\" title=\"Город\">\n" + optionSelected(result.data["city"],JSON.parse(value)[2])+
                        "                </select>\n" +
                        "                <div class=\"label-container label-city\"><label>Город:</label><div class=\"clear-fix\"></div></div>\n" +
                        "\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <div class=\"person-container info-input-container\">\n" +
                        "                <div class=\"label-container\"><label>Юр. лицо:</label><div class=\"clear-fix\"></div></div>\n" +
                        "                <input class=\"person-input\" title=\"Юр. лицо\" type=\"text\" value=\""+JSON.parse(value)[3]+"\"/>\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <div class=\"project-date-container info-input-container\">\n" +
                        "                <div class=\"label-container\"><label>Проект:</label><div class=\"clear-fix\"></div></div>\n" +
                        "                <input class=\"project-input\" title=\"Проект\" type=\"text\" value=\""+JSON.parse(value)[4]+"\"/>\n" +
                        "                <input class=\"date-input\" title=\"Дата\" type=\"text\" value=\""+JSON.parse(value)[5]+"\"/>\n" +
                        "                <div class=\"label-container  label-date\"><label>Дата:</label><div class=\"clear-fix\"></div></div>\n" +
                        "\n" +
                        "            </div>\n" +
                        "            <div class=\"comment-container info-input-container\">\n" +
                        "                <div class=\"label-container\"><label>Комментарий:</label><div class=\"clear-fix\"></div></div>\n" +
                        "                <input class=\"comment-input\" title=\"Комментарий\" type=\"text\" value=\""+JSON.parse(value)[6]+"\" readonly/>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "\n" +
                        "        <div class=\"shipment-devices\">\n" +
                        "            <p class=\"shipment-info-name\">Устройства и пакет</p>\n" +
                        "\n" +
                        "<div class=\"packet-container info-input-container\">\n" +
                        "                <div class=\"label-container\"><label>Пакет:</label><div class=\"clear-fix\"></div></div>\n" +
                        "                <input class=\"packet-input\" title=\"Пакет\" type=\"text\"/>\n" +
                        "                <div class=\"clear-fix\"></div>\n" +
                        "            </div>\n" +
                        "            <form class=\"search-tablet-form\" method=\"post\" action=\"/\">\n" +
                        "                <input placeholder=\"Номера планшетов и акб через запятую\" class=\"search-tablet-input\" type=\"text\"/>\n" +
                        "                <div class=\"search-tablet-submit-container\"><input type=\"submit\" class=\"search-tablet-submit\" title=\"Поиск\" value=\"\"/></div>\n" +
                        "                <div class=\"clear-fix\"></div>\n" +
                        "            </form>\n" +
                        "\n" +
                        "            <div class=\"list-headline-container\">\n" +
                        "\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <div class=\"list-element-container\">\n" +
                        "\n" +
                        "            </div>\n" +
                        "<div id=\"result-form\">\n" +
                        "                <div class=\"result-input-container\">\n" +
                        "                    <div class=\"label-container-result\"><label>Планшеты:</label>\n" +
                        "                        <div class=\"clear-fix\"></div>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"result result-tablet\"><p></p></div>\n" +
                        "<input type=\"hidden\" class=\"result-tablet-hidden\" value=\"\"/>\n" +
                        "                    <div class=\"clear-fix\"></div>\n" +
                        "                </div>\n" +
                        "                <div class=\"result-input-container\">\n" +
                        "                    <div class=\"label-container-result\"><label>Акб:</label>\n" +
                        "                        <div class=\"clear-fix\"></div>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"result result-akb\"><p></p></div>\n" +
                        "<input type=\"hidden\" class=\"result-akb-hidden\" value=\"\"/>\n" +
                        "                    <div class=\"clear-fix\"></div>\n" +
                        "                </div>\n" +
                        "\n" +
                        "                <div class=\"submit-container submit-container-result\">\n" +
                        "                    <input type=\"button\" class=\"input-for-submit button-effect save-element\" value=\"Сохранить\"/>\n" +
                        "                </div>\n" +
                        "                <div class=\"clear-fix\"></div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>");
                });
            }

            if (result.status === "Error") {
                $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                    "            <div class=\"error-container\">\n" +
                    "                <p class=\"error-message\">" + result.data["message"] + "</p>\n" +
                    "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                    "            </div>\n" +
                    "        </div>");
            }
        },
        error: function (xhr, textStatus, thrownError) {
            $(".waiting-ajax").hide();
            $error = "";
            if (xhr.status===0){
                $error = "Соединение с сервером потеряно."
            }
            $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                "            <div class=\"error-container\">\n" +
                "                <p class=\"error-message\">Упс.. Что то пошло не так. Обратитесь к администратору. "+$error+"</p>\n" +
                "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                "            </div>\n" +
                "        </div>");

        }
    });
}


function optionSelected(list, code) {
        var optionList = "<option></option>\n";
    $.each(list, function (key, value) {
        if (value===code){
            optionList = optionList+"<option selected>"+value+"</option>\n";
        }
        else {
            optionList = optionList+"<option>"+value+"</option>\n";
        }
    });
    return optionList;
}

