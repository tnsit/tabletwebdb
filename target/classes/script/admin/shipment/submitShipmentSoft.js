$(document).ready(function () {
    $("#submit-tablet-soft").on("click", function () {
        if ($("#result-tablet-soft p").text()===""&&$("#result-akb-soft p").text()===""){
            messageSaveShipment("Найдите в поиске хотя бы одно устройство.");
        }else if($("#result-contractor-soft p").text()===""){
            messageSaveShipment("Выберите подрядчика.");
        }
        else if($("#result-city-soft p").text()===""){
            messageSaveShipment("Выберите город.");
        }else {
            submit_shipmentSoft();
        }

    });
});

function submit_shipmentSoft() {
    var formData = {
        tablets: $("#result-tablet-soft p").text(),
        akb: $("#result-akb-soft p").text(),
        manager: $("#result-contractor-soft p").text(),
        city: $("#result-city-soft p").text()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "saveShipmentSoft",
        data: JSON.stringify(formData),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        success: function (result) {
            $(".waiting-ajax").hide();
            if (result.status === "Done") {
                $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                    "            <div class=\"error-container\">\n" +
                    "                <p class=\"error-message\">" + result.data["message"] + "</p>\n" +
                    "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                    "            </div>\n" +
                    "        </div>");
                $(".soft-search-tablet-submit").click();
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