$(document).ready(function () {
    $("#other-search-tablet-form").submit(function (event) {
        event.preventDefault();
        get_devicesShipmentOther();
    });
});

function get_devicesShipmentOther() {
    var formData = {
        numbers: $(".other-search-tablet-input").val()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "devices",
        data: JSON.stringify(formData),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        success: function (result) {
            $(".waiting-ajax").hide();

            $(".other-list-headline-container").html("<div class=\"list-headline\">\n" +
                "    <p>Номер</p>\n" +
                "    <p>Статус</p>\n" +
                "    <p>У кого</p>\n" +
                "    <div class=\"clear-fix\"></div>\n" +
                "</div>");
            if (result.status === "Null"){
                $("#result-tablet-other p").text("");
                $("#result-akb-other p").text("");
                $list = $(".other-list-element-container");
                $list.html("");
                $list.append("<div class=\"element\">\n" +
                    "        <p class=\"empty\" style='width: 900px' title=\"Нажмите, чтобы выбрать\">Устройства не найдены :(</p>\n" +
                    "    </div>");
            }
            if (result.status === "Done") {
                var correctTablet = "";
                var colTablet=0;
                var correctAkb = "";
                var colAkb=0;
                var resultTablet = $("#result-tablet-other");
                var resultAkb = $("#result-akb-other");
                var resultManager = $("#result-contractor-other");
                $(".other-list-element-container").html("");

                if (result.data["tablets"]!==""){
                    $.each(JSON.parse(result.data["tablets"]), function () {

                        var numberTablet = $(this)[0];
                        var statusTablet = $(this)[1];
                        var placeTablet = $(this)[2];
                        $(".other-list-element-container").append("<div class=\"element\">\n" +
                            "        <p>"+numberTablet+"</p>\n" +
                            "        <p>"+statusTablet+"</p>\n" +
                            "        <p>"+placeTablet+"</p>\n" +
                            "                    <div class=\"clear-fix\"></div>\n" +
                            "    </div>");
                        if (placeTablet!=="склад"){
                            $(".other-list-element-container .element:last-child p").css("color", "gray");
                        }
                        else {
                            colTablet++;
                            correctTablet = correctTablet+numberTablet+" ";
                        }
                    });
                    if (colTablet!==0){
                        $("#result-tablet-other p").text("["+colTablet+"] "+correctTablet);
                    }
                    else {
                        $("#result-tablet-other p").text("");
                    }

                }
                else {
                    $("#result-tablet-other p").text("");
                }


                if (result.data["akb"]!==""){
                    $.each(JSON.parse(result.data["akb"]), function () {

                        var number = $(this)[0];
                        var status = $(this)[1];
                        var place = $(this)[2];
                        $(".soft-list-element-container").append("<div class=\"element\">\n" +
                            "        <p>"+number+"</p>\n" +
                            "        <p>"+status+"</p>\n" +
                            "        <p>"+place+"</p>\n" +
                            "                    <div class=\"clear-fix\"></div>\n" +
                            "    </div>");
                        if (place!=="склад"){
                            $(".soft-list-element-container .element:last-child p").css("color", "gray");
                        }
                        else {
                            colAkb++;
                            correctAkb = correctAkb+number+" ";
                        }

                    });
                    if (colAkb!==0){
                        $("#result-akb-soft p").text("["+colAkb+"] "+correctAkb);
                    }
                    else {
                        $("#result-akb-soft p").text("");
                    }

                }
                else {
                    $("#result-akb-soft p").text("");
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
