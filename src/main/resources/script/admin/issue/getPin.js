$(document).ready(function () {
    $("#search-pin-form").submit(function (event) {
        event.preventDefault();
        get_pin();
    });
});

function get_pin() {
    var formData = {
        numbers: $(".search-pin-input").val()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "pins",
        data: JSON.stringify(formData),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        success: function (result) {
            $(".waiting-ajax").hide();
            if (result.status === "Null"){
                $list = $(".list-pin-container");
                $list.html("");
                $list.append("<div class=\"element-empty-pin\">\n" +
                    "        <p class=\"empty\" title=\"Нажмите, чтобы выбрать\">Не найдены :(</p>\n" +
                    "                    <div class=\"clear-fix\"></div>\n" +
                    "    </div>");
            }

            if (result.status === "Done") {
                $(".list-pin-container").html("");
                $.each(JSON.parse(result.data["pins"]), function (key, value) {
                    $(".list-pin-container").append("<div class=\"element-pin-container\">\n" +
                        "                <p>"+key+"</p>\n" +
                        "                <p>"+value+"</p>\n" +
                        "            </div>");
                });
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