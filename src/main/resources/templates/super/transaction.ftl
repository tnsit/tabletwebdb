<#import "../parts/headerSuper.ftl" as h>
<#import "../parts/message.ftl" as m>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/headerSV.css">
    <link rel="stylesheet" href="/css/search.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <link rel="stylesheet" href="/css/admin/transaction.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>

    <script src="/script/admin/transaction/searchTransaction.js"></script>
    <script src="/script/admin/transaction/transactionExcel.js"></script>
    <script src="/script/admin/tablets/searchClearTablet.js"></script>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>
<input type="hidden" id="hidden-for-menu" value="menu-8"/>

<div class="main-for-list">
    <div class="list-container">
        <div class="search-container">
            <div class="search-with-icon">
                <div class="search-icon-background">
                    <div class="search-icon"></div>
                </div>
                <input class="search-input" id="user-search" type="text" pattern="[а-я]" title="Поиск"/>
                <div class="search-clear" title="Очистить поиск"></div>
            </div>
            <p class="found" title="Найдено"></p>
            <div class="excel" title="Выгрузить в Excel"></div>
            <div class="clear-fix"></div>
        </div>

        <div class="list-headline">
            <p>Номер</p>
            <p>От кого</p>
            <p>Кому</p>
            <p>Дата</p>
            <p>Ответственный</p>
            <div class="clear-fix"></div>
        </div>

        <#list transactions as tr>
        <div class="list-element-container">
            <div class="list-element-headline">
                <p>${tr.tablet.number}</p>

                <#if tr.fromUser??>
                    <p>${tr.fromUser.getName()}</p>
                <#elseif tr.fromContractor??>
                    <p>${tr.fromContractor.code}</p>
                <#elseif tr.fromInterviewer??>
                    <p>${tr.fromInterviewer.shortName}</p>
                </#if>

                <#if tr.toUser??>
                    <p>${tr.toUser.getName()}</p>
                <#elseif tr.toContractor??>
                    <p>${tr.toContractor.code}</p>
                <#elseif tr.toInterviewer??>
                    <p>${tr.toInterviewer.shortName}</p>
                </#if>

                <p>${tr.date}</p>
                <p>${tr.respUser.getName()}</p>
            </div>
        </div>
        </#list>

    </div>
</div>
<@m.messages>
</@m.messages>
<div id="message-for-ajax">

</div>
<@m.waiting>
</@m.waiting>
</body>
</html>