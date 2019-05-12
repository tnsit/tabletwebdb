$(document).ready(function () {
   $(".submit-edit-tablet").on("click", function () {
       var parents = $(this).parents(".element-container");
       if (parents.find(".element-info-container p:nth-child(4)").text()!==""&&
           parents.find(".element-info-container p:nth-child(3)").text()!==parents.find(".purpose-select").val()){
           $("#message-for-ajax").html("<div class=\"error-background\">\n" +
               "            <div class=\"error-container\">\n" +
               "                <p class=\"error-message\">Прежде чем менять статус, снимите интервьюера.</p>\n" +
               "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
               "            </div>\n" +
               "        </div>");
       }
       else {
           if (parents.find(".purpose-select").val()==="выдан"&&
               parents.find(".element-info-container p:nth-child(4)").text()===""){
               $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                   "            <div class=\"error-container\">\n" +
                   "                <p class=\"error-message\">Статус ВЫДАН ставится сам при выдаче планшета.</p>\n" +
                   "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                   "            </div>\n" +
                   "        </div>");
           }
           else {
                editTablet(parents);
           }
       }
   }) ;
});

function editTablet(parents) {

    var formData = {
        number: parents.find(".element-info-container p:nth-child(1)").text(),
        status: parents.find(".purpose-select").val(),
        comment: parents.find(".manager-comment").val()
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(".waiting-ajax").show();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "managerEditTablet",
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