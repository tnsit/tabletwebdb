<#import "../parts/headerAdmin.ftl" as h>
<#import "../parts/message.ftl" as m>
<#import "../parts/transaction.ftl" as tr>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <link rel="stylesheet" href="/css/admin/templateWithPin.css">
    <link rel="stylesheet" href="/css/admin/issue.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/issue/getPin.js"></script>
    <script src="/script/admin/issue/getDevices.js"></script>
    <script src="/script/admin/issue/getUsers.js"></script>
    <script src="/script/admin/issue/submitIssue.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>

<@tr.transaction name="ВЫДАЧА УСТРОЙСТВ" menu="5">
<form id="search-tablet-form" method="post" action="/">
    <input placeholder="Номера планшетов и акб через запятую" class="search-tablet-input" type="text"/>
    <div class="search-tablet-submit-container"><input type="submit" class="search-tablet-submit" title="Поиск" value=""/></div>
    <div class="clear-fix"></div>
</form>

<div class="list-headline-container">

</div>

<div class="list-element-container">

</div>

<form id="search-user-form" method="post" action="/">
    <input placeholder="Фамилия пользователя" class="search-tablet-input" type="text"/>
    <div class="search-tablet-submit-container"><input type="submit" class="search-tablet-submit" title="Поиск" value=""/></div>
    <div class="clear-fix"></div>
</form>

<div class="list-manager-container">

</div>

<div id="result-form">
    <div class="result-input-container">
        <div class="label-container"><label>Планшеты:</label>
            <div class="clear-fix"></div>
        </div>
        <div class="result" id="result-tablet"><p></p></div>
        <div class="clear-fix"></div>
    </div>
    <div class="result-input-container">
        <div class="label-container"><label>Акб:</label>
            <div class="clear-fix"></div>
        </div>
        <div class="result" id="result-akb"><p></p></div>
        <div class="clear-fix"></div>
    </div>
    <div class="result-input-container">
        <div class="label-container"><label>Менеджер:</label></div>
        <div class="result" id="result-manager"><p></p></div>
    </div>

    <div class="submit-container">
        <input type="button" class="input-for-submit button-effect" id="submit-tablet" value="Сохранить"/>
    </div>
</div>

</@tr.transaction>

<@m.messages>
</@m.messages>
<div id="message-for-ajax">

</div>
<@m.waiting>
</@m.waiting>
</body>
</html>