$(document).ready(function () {
    $("#inter-save").on("click", function () {
        if ($(".inter-inter-result p").text()===""){
            $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                "            <div class=\"error-container\">\n" +
                "                <p class=\"error-message\">Выберите интервьюера</p>\n" +
                "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                "            </div>\n" +
                "        </div>");
        }
        else{
            saveAddInter();
        }

    });
});


function saveAddInter() {
    var formData = {
        tablets: $(".inter-tablet-result p").text(),
        inter: $(".inter-inter-result p").text()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "saveInter",
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
                $(".action-background").hide();
                $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                    "            <div class=\"error-container\">\n" +
                    "                <p class=\"error-message\">" + result.data["message"] + "</p>\n" +
                    "                <div class=\"button-container\"><input class=\"close-error close-inter-saved button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
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