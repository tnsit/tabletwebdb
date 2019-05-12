$(document).ready(function () {
    $(".result-to-db").on("click", function () {
        var corr = true;
        $(".list-shipment-element-container").each(function () {
            if ($(this).find(".text-headline p:nth-child(6)").text()===""){
                messageSaveShipment("Есть незаполненные строки. Удалите их, если они не нужны.");
                corr = false;
            }

        });
        if (corr===true){
            resultSave();
        }

    })
});

function resultSave() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var elements = [];

    $(".list-shipment-element-container").each(function () {
        var element = {};
        element["manager"] = $(this).find(".manager-input").val();
        element["contractor"] = $(this).find(".contractor-select").val();
        element["city"] = $(this).find(".city-input").val();
        element["person"] = $(this).find(".person-input").val();
        element["project"] = $(this).find(".project-input").val();
        element["date"] = $(this).find(".date-input").val();
        element["packet"] = $(this).find(".packet-input").val();
        element["tablet"] = $(this).find(".result-tablet p").text();
        element["akb"] = $(this).find(".result-akb p").text();
        elements.push(element);
    });

    $.ajax({
        type: "POST",
        url: "resultSave",
        data: {elements: JSON.stringify(elements)},
        dataType: "json",
        timeout: 600000,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        success: function (result) {
            $(".waiting-ajax").hide();
            if (result.status === "Done") {
                $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                    "            <div class=\"error-container\">\n" +
                    "                <p class=\"error-message\">Данные отправки добавлены в базу.</p>\n" +
                    "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                    "            </div>\n" +
                    "        </div>");

            }
            if (result.status === "Error") {
                $error = result.data["error"];
                $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                    "            <div class=\"error-container\">\n" +
                    "                <p class=\"error-message\">Упс.. Что то пошло не так. Обратитесь к администратору. "+$error+"</p>\n" +
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