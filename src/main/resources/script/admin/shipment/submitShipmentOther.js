$(document).ready(function () {
    $("#submit-tablet-other").on("click", function () {
        if ($("#result-tablet-other p").text()===""&&$("#result-akb-other p").text()===""){
            messageSaveShipment("Найдите в поиске хотя бы одно устройство.");
        } else if($("#result-city-other p").text()===""){
            messageSaveShipment("Выберите город.");
        }else {
            submit_shipmentOther();
        }

    });
});

function submit_shipmentOther() {
    var formData = {
        tablets: $("#result-tablet-other p").text(),
        akb: $("#result-akb-other p").text(),
        status: $("#result-status-other p").text(),
        comment: $(".status-comment").val(),
        city: $("#result-city-other p").text()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "saveShipmentOther",
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