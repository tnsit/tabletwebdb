function setPattern(div) {
    if (div.value==""){
        div.removeAttribute("pattern");
        div.required = false;
    }
    else {
        div.setAttribute("pattern", "[A-Za-z0-9]{6,}");
        div.required = true;
    }

}