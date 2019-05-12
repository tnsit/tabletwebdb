$(document).ready(function () {
    $button = $(".element-button");
    $val="";
    $button.on("click", function () {
        if ($(this).hasClass("element-button-save")){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            if($(this).parents(".list-element-container").children(".list-element").val()!=="") {
                var formData = {
                    element: $(this).parents(".list-element-container").children(".list-element").val(),
                    table: $("#hidden-option-select").val(),
                    id: $(this).parents(".list-element-container").children(".list-element").attr('id')
                };

                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "saveElement",
                    data: JSON.stringify(formData),
                    dataType: 'json',
                    cache: false,
                    timeout: 600000,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token)
                    },
                    success: function (result) {
                        if (result.status === "Done") {
                            $val = result.data["element"];
                            $buttonSave = $(".element-button-save");
                            $buttonSave.parents(".list-element-container").children(".list-element").blur();
                            $buttonSave.css("background", "url(\"/image/edit.svg\") no-repeat");
                            $buttonSave.css("background-size", "contain");
                            $buttonSave.removeClass("element-button-save");
                        }

                        if (result.status === "alreadyExists") {
                            $("#message-for-ajax").html("<div class=\"error-background\">\n" +
                                "            <div class=\"error-container\">\n" +
                                "                <p class=\"error-message\">" + result.data["message"] + "</p>\n" +
                                "                <div class=\"button-container\"><input class=\"close-error button-effect\" type=\"button\" value=\"Закрыть\"/></div>\n" +
                                "            </div>\n" +
                                "        </div>");
                        }
                    },
                    error: function (xhr, textStatus, thrownError) {
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
            else {
                $(this).parents(".list-element-container").children(".list-element").setCustomValidity("Заполните поле");
            }
        }



        $(this).addClass("element-button-save");
        $(this).css("background", "url(\"/image/save.png\") no-repeat");
        $(this).css("background-size", "contain");
       $input = $(this).parents(".list-element-container").children(".list-element");
       $input.attr('readonly', false);
       $num = $input.val();
       $input.focus().val("").val($num);
   }) ;

    $button.parents(".list-element-container").on("mouseenter", function () {
        $val=$(this).children(".list-element").val();
        $elementButton = $(".list-element-container:hover .element-button");
        $elementButton.css("background", "url(\"/image/edit.svg\") no-repeat");
        $elementButton.css("background-size", "contain");
    }) ;

    $button.parents(".list-element-container").on("mouseleave", function () {
        $(this).children(".element-button").removeClass("element-button-save");
        $(this).children(".element-button").css("background", "none");
        $(this).children(".list-element").attr('readonly', true);
        $(this).children(".list-element").val($val);
    }) ;

    $(document).keypress(function (e) {
        if (e.which == 13) {
            $save = $(".element-button-save");
            if ($save.show()){
                $save.click();
            }
        }
    });




});