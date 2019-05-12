$(document).ready(function () {
    $("#submit-tablet").on("click", function () {
        if ($("#result-tablet p").text()===""&&$("#result-akb p").text()===""){
            messageIssue("Найдите в поиске хотя бы одно устройство.");
        }else if($("#result-manager p").text()===""){
            messageIssue("Найдите и выберите менеджера.");
        }
        else {
            submit_issue();
        }

    });
});

function submit_issue() {
    var formData = {
        tablets: $("#result-tablet p").text(),
        akb: $("#result-akb p").text(),
        manager: $("#result-manager p").text()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "saveIssue",
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
                $(".search-tablet-submit").click();
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

function messageIssue(message) {
    return $("#message-for-ajax").html("<div class=\"error-background\">\n" +
        "            <div class=\"error-container\">\n" +
        "                <p class=\"error-message\">"+message+"</p>\n" +
        "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
        "            </div>\n" +
        "        </div>");
}