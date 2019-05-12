$(document). ready(function(){
    $('input[type="file"]'). change(function(e){
        $sumbit = $("#from-excel .input-for-submit");
        $sumbit.prop("disabled", true);
        $sumbit.addClass("disabled-submit");
        var fileName = e. target. files[0]. name;
        $p = $("#file-name p");
        $p.text(fileName);
        $p.attr("title", fileName);
        if (fileName.indexOf(".xls")!==-1&&fileName.indexOf(".xlsx")!==-1){
            $sumbit.prop("disabled", false);
            $sumbit.removeClass("disabled-submit");
        }

    });
});
