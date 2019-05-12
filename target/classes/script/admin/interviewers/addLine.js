$(document).ready(function () {
    var firstCheckbox = $(".line-container .checkbox-container input");
    var cloneCount = 1;
   $(".add").on("click", function () {
       $("#line-container").clone().attr('id', 'clone-container-'+ cloneCount++).appendTo(".add-line-container");
       var clone = $("#clone-container-"+(cloneCount-1));
       clone.children("input").val("");
       if (clone.children(".checkbox-container").children(".brig").prop("checked")){
           clone.children(".checkbox-container").children(".brig").click();
           clone.children(".checkbox-container").children(".hidden-checkbox").val("false");
       }
       clone.on("click",".remove", function () {
           $(this).parents(".create-input-line-container").remove();
       });

       clone.on("click",".brig", function () {
           setCheckbox($(this));
       });
   });
   
   $(".brig").on("click", function () {
       setCheckbox($(this));
   });

    function setCheckbox(elm) {
        if (elm.prop("checked")){
            elm.siblings(".hidden-checkbox").val("true");
        }
        else {
            elm.siblings(".hidden-checkbox").val("false");
        }
    }
});