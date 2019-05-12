<#import "../parts/headerAdmin.ftl" as h>
<#import "../parts/message.ftl" as m>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/search.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <link rel="stylesheet" href="/css/admin/tablet.css">
    <link rel="stylesheet" href="/css/admin/akb.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/akb/numberDisable.js"></script>
    <script src="/script/admin/akb/searchAkb.js"></script>
    <script src="/script/admin/akb/searchClearAkb.js"></script>
    <script src="/script/admin/akb/akbExcel.js"></script>
    <script src="/script/admin/interviewers/interHeightDiv.js"></script>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>
<input type="hidden" id="hidden-for-menu" value="menu-3"/>
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
        </div>

        <div class="list-headline">
            <p>Номер</p>
            <p>Статус</p>
            <p>У кого</p>
            <p>Город</p>
            <p>Ответственный</p>
            <p>Дата</p>
        </div>

        <#list akbs as akb>
        <div class="list-element-container">
            <div class="list-element-headline" onclick="interHeightDiv(this)">
                <p>${akb.number}</p>
                <p>${akb.status.getStatus()}</p>
                <p><#list akb.placeAkb as place>
                        ${place.getName()}
                </#list>
                </p>
                <p>${akb.city.getCity()}</p>

                <#if akb.interviewer??>
                    <p>${akb.interviewer.shortName}</p>
                <#elseif akb.user??>
                    <p>${akb.user.getName()}</p>
                <#elseif akb.contractor??>
                    <p>${akb.contractor.code}</p>
                    <#else><p></p>
                </#if>

            </div>

            <form class="list-element-form" id="list-element-form-${akb.id?c}" method="post" action="/editAkb">
                <input type="hidden" id="tablet-id" name="akb-id" value="${akb.id?c}">
                <div class="left-container">
                    <div class="field-container">
                        <div class="label-container">
                            <label>Номер:</label>
                        </div>
                        <input type="text" title="Номер виде A1234, буква A латинская." name="akb-number" class="akb-number" id="akb-number-${akb.id?c}" pattern="[A][0-9]{4,}" value="${akb.number}" required/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Модель:</label>
                        </div>
                        <select title="Модель" name="akb-model" class="akb-model" id="akb-model-${akb.id?c}" required>
                            <#list modelAkb as model>
                                <option <#if model.getModelAkb()==akb.modelAkb.getModelAkb()>selected</#if>>${model.getModelAkb()}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Статус:</label>
                        </div>
                        <select title="Статус" name="akb-status" class="akb-status" id="akb-status-${akb.id?c}" required>
                            <#list status as st>
                                <option <#if st.getStatus()==akb.status.getStatus()>selected</#if>>${st.status}</option>
                            </#list>
                        </select>
                    </div>
                </div>




                <div class="right-container">
                    <div class="field-container">
                        <div class="label-container">
                            <label>У кого:</label>
                        </div>
                        <input type="text" title="У кого" class="akb-place" id="akb-place-${akb.id?c}" value="<#list akb.placeAkb as place>${place.getName()}</#list>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Город:</label>
                        </div>
                        <input type="text" title="Город" id="akb-city-${akb.id?c}" class="akb-city" value="${akb.city.getCity()}" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Менеджер:</label>
                        </div>
                        <input type="text" title="Менеджер" id="akb-manager-${akb.id?c}" class="akb-user" value="<#if akb.user??>${akb.user.getName()}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Интервьюер:</label>
                        </div>
                        <input type="text" title="Интервьюер" id="akb-interviewer-${akb.id?c}" class="akb-interviewer" value="<#if akb.interviewer??>${akb.interviewer.shortName}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Подрядчик:</label>
                        </div>
                        <input type="text" title="Подрядчик" id="akb-contractor-${akb.id?c}" class="akb-contractor" value="<#if akb.contractor??>${akb.contractor.code}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Дата транзакции:</label>
                        </div>
                        <input type="text" title="Дата транзакции" id="akb-trans-data-${akb.id?c}" class="akb-trans-date" value="<#if akb.date??>${akb.date}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Ответственный:</label>
                        </div>
                        <input type="text" title="Ответственный" id="akb-resp-${akb.id?c}" class="akb-resp" value="<#if akb.respUser??>${akb.respUser.getName()}</#if>" disabled/>
                    </div>

                </div>
                <div class="submit-container">
                    <input type="submit" class="input-for-submit button-effect" id="submit-akb" value="Сохранить"/>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </form>
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