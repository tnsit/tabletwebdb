<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/login.css">
    <title>TabletDB</title>
</head>
<body>
<div class="container">
    <div class="logo">
    </div>
    <div class="login-form">
    <form action="/login" method="post">
        <div class="login"><input type="text" name="username" placeholder="Логин"/></div>
        <div class="password"><input type="password" name="password" placeholder="Пароль" autocomplete="current-password"/></div>
        <div class="button">
            <div class="error">
                <#if RequestParameters.error??>
                <div>
                    Неверный логин или пароль
                </div>
                </#if>
            </div>
            <input class="submit" type="submit" value="Войти"/>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    </div>

</div>
</body>
</html>