$(document).ready(function () {
   $(".excel").on("click", function () {
       var token = $("meta[name='_csrf']").attr("content");
       var header = $("meta[name='_csrf_header']").attr("content");

       $(".waiting-ajax").show();
       $.ajax({
           type: "POST",
           contentType: "application/json",
           url: "tabletExcel",
           dataType: 'json',
           cache: false,
           timeout: 600000,
           beforeSend: function (xhr) {
               xhr.setRequestHeader(header, token)
           },
           success: function (result) {
               $(".waiting-ajax").hide();
               if (result.status === "Done") {
                       $url = result.data["url"];
                       window.open($url,"_self");
                }
               if (result.status === "Error") {
                   $error = result.data["error"];
                   $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                       "            <div class=\"error-container\">\n" +
                       "                <p class=\"error-message\">Упс.. Что то пошло не так. Обратитесь к администратору. "+$error+"</p>\n" +
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
   })
});