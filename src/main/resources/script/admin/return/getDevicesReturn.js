$(document).ready(function () {
    $("#search-tablet-form").submit(function (event) {
        event.preventDefault();
        get_devices();
    });
});

function get_devices() {
    var formData = {
        numbers: $(".search-tablet-input").val()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "return",
        data: JSON.stringify(formData),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        success: function (result) {
            $(".waiting-ajax").hide();
            $(".list-headline-container").html("<div class=\"list-headline\">\n" +
                "        <p>Номер</p>\n" +
                "        <p>Статус</p>\n" +
                "        <p>От кого</p>\n" +
                "        <p title=\"Комментарий от менеджера\">Комментарий</p>\n" +
                "        <div class=\"clear-fix\"></div>\n" +
                "    </div>");
            if (result.status === "Null"){
                $("#result-tablet p").text("");
                $("#result-akb p").text("");
                $list = $(".list-element-container");
                $list.html("");
                $list.append("<div class=\"element\">\n" +
                    "        <p class=\"empty\" style='width: 900px' title=\"Нажмите, чтобы выбрать\">Устройства не найдены :(</p>\n" +
                    "                    <div class=\"clear-fix\"></div>\n" +
                    "    </div>");
            }
            if (result.status === "Done") {
                var correctTablet = "";
                var colTablet=0;
                var correctAkb = "";
                var colAkb=0;
                $(".list-element-container").html("");
                if (result.data["tablets"]!==""){
                    $.each(JSON.parse(result.data["tablets"]), function () {

                        var numberTablet = $(this)[0];
                        var statusTablet = $(this)[1];
                        var placeTablet = $(this)[2];
                        var statusComment = $(this)[3];
                        var commentTablet = $(this)[4];
                        $(".list-element-container").append("<div class=\"element\">\n" +
                            "        <div class=\"element-headline\" onclick=\"returnHeightDiv(this)\">\n" +
                            "            <p>"+numberTablet+"</p>\n" +
                            "            <p>"+statusTablet+"</p>\n" +
                            "            <p>"+placeTablet+"</p>\n" +
                            "            <p title=\"Комментарий от менеджера\">"+commentTablet+"</p>\n" +
                            "            <div class=\"clear-fix\"></div>\n" +
                            "        </div>\n" +
                            "        <form class=\"element-status\" action=\"/\" method=\"post\">\n" +
                            "            <div class=\"status-container\">\n" +
                            "                <div class=\"field-container\">\n" +
                            "                    <div class=\"status-label-container\">\n" +
                            "                        <label>Статус:</label>\n" +
                            "                    </div>\n" +
                            "                    <select title=\"Статус\" class=\"tablet-status\" required>\n" + statusSelected(result.data["status"],statusTablet)+
                            "                    </select>\n" +
                            "                </div>\n" +
                            "                <textarea class=\"status-comment\" placeholder=\"Комментарий к статусу\">"+statusComment+"</textarea>\n" +
                            "            </div>\n" +
                            "            <textarea class=\"manager-comment\" placeholder=\"Комментарий от менеджера\" readonly>"+commentTablet+"</textarea>\n" +
                            "            <div class=\"clear-fix\"></div>\n" +
                            "            <div class=\"submit-container submit-status-container\">\n" +
                            "                <input type=\"button\" class=\"input-for-submit button-effect\" id=\"submit-status\" value=\"Сохранить\"/>\n" +
                            "            </div>\n" +
                            "\n" +
                            "        </form>\n" +
                            "    </div>");
                        colTablet++;
                        correctTablet = correctTablet+numberTablet+" ";
                    });
                    if (colTablet!==0){
                        $("#result-tablet p").text("["+colTablet+"] "+correctTablet);
                    }
                    else {
                        $("#result-tablet p").text("");
                    }

                }
                else {
                    $("#result-tablet p").text("");
                }


                if (result.data["akb"]!==""){
                    $.each(JSON.parse(result.data["akb"]), function () {

                        var number = $(this)[0];
                        var status = $(this)[1];
                        var place = $(this)[2];
                        $(".list-element-container").append("<div class=\"element\">\n" +
                            "        <div class=\"element-headline\" onclick=\"returnHeightDiv(this)\">\n" +
                            "            <p>"+number+"</p>\n" +
                            "            <p>"+status+"</p>\n" +
                            "            <p>"+place+"</p>\n" +
                            "            <p title=\"Комментарий от менеджера\"></p>\n" +
                            "            <div class=\"clear-fix\"></div>\n" +
                            "        </div>\n" +
                            "        <form class=\"element-status\" action=\"/\" method=\"post\">\n" +
                            "            <div class=\"status-container\">\n" +
                            "                <div class=\"field-container\">\n" +
                            "                    <div class=\"status-label-container\">\n" +
                            "                        <label>Статус:</label>\n" +
                            "                    </div>\n" +
                            "                    <select title=\"Статус\" class=\"tablet-status\" required>\n" + statusSelected(result.data["status"],status)+
                            "                    </select>\n" +
                            "                </div>\n" +
                            "                <textarea class=\"status-comment\" placeholder=\"Комментарий к статусу\" disabled></textarea>\n" +
                            "            </div>\n" +
                            "            <textarea class=\"manager-comment\" placeholder=\"Комментарий от менеджера\" disabled></textarea>\n" +
                            "            <div class=\"clear-fix\"></div>\n" +
                            "            <div class=\"submit-container submit-status-container\">\n" +
                            "                <input type=\"button\" class=\"input-for-submit button-effect\" id=\"submit-status\" value=\"Сохранить\"/>\n" +
                            "            </div>\n" +
                            "\n" +
                            "        </form>\n" +
                            "    </div>");
                        colAkb++;
                        correctAkb = correctAkb+number+" ";

                    });
                    if (colAkb!==0){
                        $("#result-akb p").text("["+colAkb+"] "+correctAkb);
                    }
                    else {
                        $("#result-akb p").text("");
                    }

                }
                else {
                    $("#result-akb p").text("");
                }

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

function statusSelected(list, code) {
    var optionList = "";
    $.each(JSON.parse(list), function (key, value) {
        if (value===code){

            optionList = optionList+"<option selected>"+value+"</option>\n";
        }
        else {
            optionList = optionList+"<option>"+value+"</option>\n";
        }
    });
    return optionList;
}