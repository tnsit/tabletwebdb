<#import "../parts/headerAdmin.ftl" as h>
<#import "../parts/message.ftl" as m>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/search.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <link rel="stylesheet" href="/css/admin/listInterviewers.css">
    <link rel="stylesheet" href="/css/admin/createInterviewers.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/users/searchStyle.js"></script>
    <script src="/script/admin/users/searchClear.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/interviewers/addLine.js"></script>
    <script src="/script/admin/interviewers/interHeightDiv.js"></script>
    <script src="/script/admin/interviewers/searchInterviewer.js"></script>
    <script src="/script/admin/interviewers/searchClearInterviewer.js"></script>
    <meta charset="UTF-8">
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>
<input type="hidden" id="hidden-for-menu" value="menu-10"/>
<div class="main-for-create">
    <div class="create-inter-container">
        <div class="head-label">
            <p>СОЗДАТЬ ИНТЕРВЬЮЕРА</p>
        </div>
        <div class="create-headline">
            <p>Фамилия</p>
            <p>Имя</p>
            <p>Отчество</p>
            <p>Бригадир</p>
        </div>
        <form class="create-form" action="/createInterviewers" method="post">
            <div class="add-line-container">
                <div class="create-input-line-container" id="line-container">
                    <input title="Фамилия с большой буквы" class="fio" name="firstName" type="text" pattern="[А-Я][а-я]{2,}" required>
                    <input title="Имя с большой буквы" class="fio" name="secondName" type="text" pattern="[А-Я][а-я]{2,}" required>
                    <input title="Отчество с большой буквы" class="fio" name="thirdName" type="text" pattern="[А-Я][а-я]{2,}" required>
                    <div class="checkbox-container">
                        <input type="checkbox" name="brig" class="brig" title="Бригадир">
                        <input type="hidden" name="hidden-checkbox" class="hidden-checkbox" value="false">
                        <div class="fake-checkbox"></div>
                    </div>
                    <div class="remove"></div>

                </div>
            </div>
            <div class="submit-container-create">
                <div class="add"></div>
                <input type="submit" class="submit-for-create" value="Сохранить"/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>
<div class="main-for-list">
    <div class="list-inter-container">
        <div class="search-container">
            <div class="search-with-icon">
                <div class="search-icon-background">
                    <div class="search-icon"></div>
                </div>
                <input class="search-input" id="user-search" type="text" pattern="[а-я]" title="Поиск по интервьюерам"/>
                <div class="search-clear" title="Очистить поиск"></div>
            </div>
        </div>
        <div class="list-headline">
            <p>Интервьюер</p>
            <p>Планшет</p>
            <p>Менеджер</p>
        </div>
        <#list interviewers as interviewer>
        <div class="list-element-container">
            <div class="list-element-headline" onclick="interHeightDiv(this)">
                <p>${interviewer.fullName}</p>

                    <#if interviewer.tablets??>
                        <p title="${interviewer.tablets}">${interviewer.tablets}</p>
                    <#else>
                        <p></p>
                    </#if>

                <p>
                    <#if interviewer.manager??>
                        ${interviewer.manager}
                    <#else>

                    </#if>
                </p>
            </div>
            <form class="list-element-form" method="post" action="/editInterviewers">
                <input type="hidden" name="inter-id" value="${interviewer.id?c}"/>
                <div class="edit-container">
                    <div class="label-container"><label>Фамилия:</label></div>
                    <input title="Фамилия" name="fn-edit" class="inter-edit" type="text" pattern="[А-Я][а-я]{2,}" value="${interviewer.firstName}"/>
                </div>
                <div class="edit-container">
                    <div class="label-container"><label>Имя:</label></div>
                    <input title="Имя" name="sn-edit" class="inter-edit" type="text" pattern="[А-Я][а-я]{2,}" value="${interviewer.secondName}"/>
                </div>
                <div class="edit-container">
                    <div class="label-container"><label>Бригадир:</label></div>
                    <div class="checkbox-container">
                        <input type="checkbox" name="brig" class="brig" title="Бригадир" ${interviewer.brig?string("checked", "")}/>
                        <div class="fake-checkbox"></div>
                    </div>
                </div>
                <div class="edit-container">
                    <div class="label-container"><label>Отчество:</label></div>
                    <input title="Отчество" name="tn-edit" class="inter-edit" type="text" pattern="[А-Я][а-я]{2,}" value="${interviewer.thirdName}"/>
                </div>
                <div class="submit-container">
                    <input type="submit" class="input-for-submit button-effect" value="Сохранить"/>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
        </#list>
    </div>
</div>

<@m.messages>
</@m.messages>

</body>
</html>