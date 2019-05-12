$(document).ready(function () {
    $("body").on("submit",".search-tablet-form",function (event) {
        var id = $(this).parents(".list-shipment-element-container").attr("id");
        event.preventDefault();
        get_shipmentDevices(id);
    });
});

function get_shipmentDevices(id) {
    var parent = $("#"+id);
    var formData = {
        numbers: parent.find(".search-tablet-input").val()
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
            parent.find(".list-headline-container").html("<div class=\"list-headline\">\n" +
                "    <p>Номер</p>\n" +
                "    <p>Статус</p>\n" +
                "    <p>У кого</p>\n" +
                "    <div class=\"clear-fix\"></div>\n" +
                "</div>");
            if (result.status === "Null"){
                parent.find(".result-tablet p").text("");
                parent.find(".result-akb p").text("");
                parent.find(".result-tablet-hidden").val("");
                parent.find(".result-akb-hidden").val("");
                $list = parent.find(".list-element-container");
                $list.html("");
                $list.append("<div class=\"element\">\n" +
                    "        <p style='width: 900px' class=\"empty\" title=\"Нажмите, чтобы выбрать\">Устройства не найдены :(</p>\n" +
                    "                    <div class=\"clear-fix\"></div>\n" +
                    "    </div>");
            }
            if (result.status === "Done") {
                var correctTablet = "";
                var colTablet=0;
                var correctAkb = "";
                var colAkb=0;
                parent.find(".list-element-container").html("");

                if (result.data["tablets"]!==""){
                    $.each(JSON.parse(result.data["tablets"]), function () {

                        var numberTablet = $(this)[0];
                        var statusTablet = $(this)[1];
                        var placeTablet = $(this)[2];
                        parent.find(".list-element-container").append("<div class=\"element\">\n" +
                            "        <p>"+numberTablet+"</p>\n" +
                            "        <p>"+statusTablet+"</p>\n" +
                            "        <p>"+placeTablet+"</p>\n" +
                            "    </div>");
                        if (statusTablet!=="рабочий"||placeTablet!=="склад"){
                            parent.find(".list-element-container .element:last-child p").css("color", "gray");
                        }
                        else {
                            colTablet++;
                            correctTablet = correctTablet+numberTablet+" ";
                        }
                    });

                    if (colTablet!==0){
                        parent.find(".result-tablet p").text("["+colTablet+"] "+correctTablet);
                        parent.find(".result-tablet-hidden").val(colTablet);
                    }
                    else {
                        parent.find(".result-tablet p").text("");
                        parent.find(".result-tablet-hidden").val("");
                    }

                }
                else {
                    parent.find(".result-tablet p").text("");
                    parent.find(".result-tablet-hidden").val("");
                }


                if (result.data["akb"]!==""){
                    $.each(JSON.parse(result.data["akb"]), function () {

                        var number = $(this)[0];
                        var status = $(this)[1];
                        var place = $(this)[2];
                        parent.find(".list-element-container").append("<div class=\"element\">\n" +
                            "        <p>"+number+"</p>\n" +
                            "        <p>"+status+"</p>\n" +
                            "        <p>"+place+"</p>\n" +
                            "    </div>");
                        if (status!=="рабочий"||place!=="склад"){
                            parent.find(".list-element-container .element:last-child p").css("color", "gray");
                        }
                        else {
                            colAkb++;
                            correctAkb = correctAkb+number+" ";
                        }

                    });
                    if (colAkb!==0){
                        parent.find(".result-akb p").text("["+colAkb+"] "+correctAkb);
                        parent.find(".result-akb-hidden").val(colAkb);
                    }
                    else {
                        parent.find(".result-akb p").text("");
                        parent.find(".result-akb-hidden").val("");
                    }

                }
                else {
                    parent.find(".result-akb p").text("");
                    parent.find(".result-akb-hidden").val("");
                }

                parent.find(".list-element-container").append("<div class=\"clear-fix\"></div>");

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
