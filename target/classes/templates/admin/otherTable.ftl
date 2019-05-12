<#import "../parts/headerAdmin.ftl" as h>
<#import "../parts/message.ftl" as m>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/search.css">
    <link rel="stylesheet" href="/css/admin/otherTable.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/other/searchOtherTable.js"></script>
    <script src="/script/admin/other/searchClearOtherTable.js"></script>
    <script src="/script/admin/users/searchStyle.js"></script>
    <script src="/script/admin/users/searchClear.js"></script>
    <script src="/script/admin/other/selectValue.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/other/edit.js"></script>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<@h.header>
</@h.header>
<body>
<input type="hidden" id="hidden-for-menu" value="menu-12"/>
    <div class="main-container">
        <p class="head-name-container">ТАБЛИЦЫ</p>
        <div class="select-container">
            <select id="select-table" name="select-table">
                <option id="cityOption">Город</option>
                <option id="modelTabletOption">Модель планшета</option>
                <option id="modelAkbOption">Модель AKБ</option>
                <option id="osOption">Операционная система</option>
                <option id="purposeOption">Цель для менеджеров</option>
                <option id="statusOption">Статус</option>
                <option id="contractorOption">Подрядчик</option>
            </select>
        </div>

        <form class="create-container" method="post" action="/create">
            <input id="input-create" name="input-create" placeholder="Город" type="text" required/>
            <input type="hidden" id="hidden-option-select" name="hidden-option-select" value=""/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit">Создать</button>
        </form>


        <div class="search-container">
            <div class="search-with-icon">
                <div class="search-icon-background">
                    <div class="search-icon"></div>
                </div>
                <input class="search-input" id="search" type="text" pattern="[а-я]" title="Поиск"/>
                <div class="search-clear" title="Очистить поиск"></div>
            </div>
        </div>


        <div class="list-container" id="cityOptionList">
            <#list city as cityElement>
            <div class="list-element-container">
                <input value="${cityElement.city}" id="city-${cityElement.id?c}" type="text" class="list-element" readonly/>
                <div class="element-button" title="Редактировать"></div>
            </div>
            </#list>
        </div>

        <div class="list-container" id="modelTabletOptionList">
            <#list modelTablet as modelTabletElement>
                <div class="list-element-container">
                    <input value="${modelTabletElement.modelTablet}" id="modelTablet-${modelTabletElement.id?c}" type="text" class="list-element" readonly/>
                    <div class="element-button" title="Редактировать"></div>
                </div>
            </#list>
        </div>

        <div class="list-container" id="modelAkbOptionList">
            <#list modelAkb as modelAkbElement>
                <div class="list-element-container">
                    <input value="${modelAkbElement.modelAkb}" id="modelAkb-${modelAkbElement.id?c}" type="text" class="list-element" readonly/>
                    <div class="element-button" title="Редактировать"></div>
                </div>
            </#list>
        </div>

        <div class="list-container" id="osOptionList">
            <#list os as osElement>
                <div class="list-element-container">
                    <input value="${osElement.os}" id="os-${osElement.id?c}" type="text" class="list-element" readonly/>
                    <div class="element-button" title="Редактировать"></div>
                </div>
            </#list>
        </div>

        <div class="list-container" id="purposeOptionList">
            <#list purpose as purposeElement>
                <div class="list-element-container">
                    <input value="${purposeElement.purpose}" id="purpose-${purposeElement.id?c}" type="text" class="list-element" readonly/>
                    <div class="element-button" title="Редактировать"></div>
                </div>
            </#list>
        </div>

        <div class="list-container" id="statusOptionList">
            <#list status as statusElement>
                <div class="list-element-container">
                    <input value="${statusElement.status}" id="status-${statusElement.id?c}" type="text" class="list-element" readonly/>
                    <div class="element-button" title="Редактировать"></div>
                </div>
            </#list>
        </div>

        <div class="list-container" id="contractorOptionList">
            <#list contractor as contractorElement>
                <div class="list-element-container">
                    <input value="${contractorElement.code}" id="contractor-${contractorElement.id?c}" type="text" class="list-element" pattern="[A-Z]{4}" readonly/>
                    <div class="element-button" title="Редактировать"></div>
                </div>
            </#list>
        </div>

        <@m.messages>
        </@m.messages>
        <div id="message-for-ajax">

        </div>

        <@m.waiting>
        </@m.waiting>
    </div>
</body>
</html>