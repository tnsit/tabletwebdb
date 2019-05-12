$(document).ready(function () {
    $("#search-user-form").submit(function (event) {
        $("#result-manager p").text("");
        event.preventDefault();
        get_managers();
    });
});

function get_managers() {
    var formData = {
        manager: $("#search-user-form input").val()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "managers",
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
                $("#result-manager p").text("");
                $list = $(".list-manager-container");
                $list.html("");
                $list.append("<div class=\"element\">\n" +
                    "        <p class=\"empty\" title=\"Нажмите, чтобы выбрать\">Пользователи не найдены :(</p>\n" +
                    "    </div>");
            }
            if (result.status === "Done") {
                $(".list-manager-container").html("");
                    $.each(JSON.parse(result.data["managers"]), function (key,value) {
                        $(".list-manager-container").append("<div class=\"element\">\n" +
                            "        <input name=\"radio\" title=\"Нажмите, чтобы выбрать\" type=\"radio\" class=\"radio\" id=\"radio-"+key+"\">\n" +
                            "        <div class=\"fake-radio\"></div>\n" +
                            "        <label for=\"radio-"+key+"\" title=\"Нажмите, чтобы выбрать\">"+value+"</label>\n" +
                            "    </div>");
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

$(document).ready(function () {
    $("body").on("change",".radio", function () {
        if ($(this).is(":checked")) {
            $("#result-manager p").text($(".element input:checked~label").text());
        }
    })
});
