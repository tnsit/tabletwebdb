function validateForm () {
    var password1 = document.getElementById('regpass');
    var password2 = document.getElementById('regpass2');
    if (password1.value !== password2.value) {
        console.log("Пароли не совпадают");
        password2.setAttribute('style', 'box-shadow:0 0 0 2px red;');
        password2.setCustomValidity('Пароли не совпадают');
    }
}

function validateFormChange (div) {
    var user = div.id;
    var password1 = document.getElementById('password-'+user);
    var password2 = document.getElementById('password2-'+user);
    if(password1.value==""&& password2.value==""){
        password2.removeAttribute('style');
        password2.setCustomValidity('');
    }
    else{
        if (password1.value !== password2.value) {
            console.log("Пароли не совпадают");
            password2.setAttribute('style', 'box-shadow:0 0 0 2px red;');
            password2.setCustomValidity('Пароли не совпадают');
        }
    }

}

function removeAttrStyle () {
    var password2 = document.getElementById('regpass2');
    password2.setCustomValidity('');
    password2.removeAttribute('style');
}

function removeAttrStyleChange (div) {
    div.setCustomValidity('');
    div.removeAttribute('style');
}