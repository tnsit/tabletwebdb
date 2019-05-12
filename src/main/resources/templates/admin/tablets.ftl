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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/tablets/numberDisable.js"></script>
    <script src="/script/admin/tablets/searchTablet.js"></script>
    <script src="/script/admin/tablets/tabletExcel.js"></script>
    <script src="/script/admin/tablets/searchClearTablet.js"></script>
    <script src="/script/admin/interviewers/interHeightDiv.js"></script>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>
<input type="hidden" id="hidden-for-menu" value="menu-2"/>

<div class="main-for-list">
    <div class="list-container">
        <div class="search-container">
            <form id="search-tablet-form" method="post" action="/tablets">
                <input placeholder="Номер, имя, место и т.д." class="search-tablet-input" name="tablet-search" type="text"/>
                <div class="search-tablet-submit-container"><input type="submit" class="search-tablet-submit" title="Поиск" value=""/></div>
                <div class="clear-fix"></div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <div class="excel" title="Выгрузить в Excel"></div>
            <div class="clear-fix"></div>
        </div>

        <div class="list-headline">
            <p>Номер</p>
            <p>Статус</p>
            <p>У кого</p>
            <p>Город</p>
            <p>Ответственный</p>
            <p>Дата</p>
        </div>
        <#if tablets??>
        <#list tablets as tablet>
        <div class="list-element-container">
            <div class="list-element-headline" onclick="interHeightDiv(this)">
                <p>${tablet.number}</p>
                <p>${tablet.status.getStatus()}</p>
                <p><#list tablet.placeTablet as place>
                        ${place.getName()}
                </#list>
                </p>
                <p>${tablet.city.getCity()}</p>

                <#if tablet.interviewer??>
                    <p>${tablet.interviewer.shortName}</p>
                <#elseif tablet.user??>
                    <p>${tablet.user.name}</p>
                <#elseif tablet.contractor??>
                    <p>${tablet.contractor.code}</p>
                    <#else><p></p>
                </#if>

                <#if tablet.transaction??>
                    <p>${tablet.transaction.date}</p>
                <#else><p></p>
                </#if>

            </div>

            <form class="list-element-form" id="list-element-form-${tablet.id?c}" method="post" action="/editTablet">
                <input type="hidden" id="tablet-id" name="tablet-id" value="${tablet.id?c}">
                <div class="left-container">
                    <div class="field-container">
                        <div class="label-container">
                            <label>Номер:</label>
                        </div>
                        <input type="text" title="Номер минимум из 5 цифр" name="tablet-number" class="tablet-number" id="tablet-number-${tablet.id?c}" pattern="[0-9]{4,}" value="${tablet.number}" required/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Модель:</label>
                        </div>
                        <select title="Модель" name="tablet-model" class="tablet-model" id="tablet-model-${tablet.id?c}" required>
                            <#list modelTablet as model>
                                <option <#if model.getModelTablet()==tablet.modelTablet.getModelTablet()>selected</#if>>${model.getModelTablet()}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Статус:</label>
                        </div>
                        <select title="Статус" name="tablet-status" class="tablet-status" id="tablet-status-${tablet.id?c}" required>
                            <#list status as st>
                                <option <#if st.getStatus()==tablet.status.getStatus()>selected</#if>>${st.status}</option>
                            </#list>
                        </select>
                    </div>

                    <textarea title="Комментарий к статусу" name="tablet-status-comment" class="tablet-status-comment" id="status-comment-${tablet.id?c}" placeholder="Комментарий к статусу"><#if tablet.statusComment??>${tablet.statusComment}</#if></textarea>

                    <div class="field-container">
                        <div class="label-container">
                            <label>IMEI:</label>
                        </div>
                        <input type="text" title="IMEI из 15 цифр" name="tablet-imei" class="tablet-imei" id="tablet-imei-${tablet.id?c}"  pattern="[0-9]{15,}" value="${tablet.imei}" required/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Версия OS:</label>
                        </div>
                        <select title="Версия OS" name="tablet-os" class="tablet-os" id="tablet-os-${tablet.id?c}" required>
                            <#list os as o>
                                <option <#if o.getOs()==tablet.os.getOs()>selected</#if>>${o.os}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Телефон:</label>
                        </div>
                        <input type="text" title="Телефон из 10 цифр: 9061234567" name="tablet-phone" class="tablet-phone" id="tablet-phone-${tablet.id?c}"  pattern="[0-9]{10}" value="${tablet.phone}" required/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Пин:</label>
                        </div>
                        <input type="text" title="Пин-код от Kids Place, 4 цифры" name="tablet-pin" class="tablet-pin" id="tablet-pin-${tablet.id?c}"  pattern="[0-9]{4,}" value="${tablet.pin}" required/>
                    </div>

                    <textarea title="Комментарий к планшету" id="tablet-comment-${tablet.id?c}" name="tablet-comment" class="tablet-comment" placeholder="Комментарий к планшету"><#if tablet.tabletComment??>${tablet.tabletComment}</#if></textarea>
                </div>




                <div class="right-container">
                    <div class="field-container">
                        <div class="label-container">
                            <label>У кого:</label>
                        </div>
                        <input type="text" title="У кого" class="tablet-place" id="tablet-place-${tablet.id?c}" value="<#list tablet.placeTablet as place>${place.getName()}</#list>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Город:</label>
                        </div>
                        <input type="text" title="Город" id="tablet-city-${tablet.id?c}" class="tablet-city" value="${tablet.city.getCity()}" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Менеджер:</label>
                        </div>
                        <input type="text" title="Менеджер" id="tablet-manager-${tablet.id?c}" class="tablet-user" value="<#if tablet.user??>${tablet.user.name}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Для чего:</label>
                        </div>
                        <input type="text" title="Для чего" id="tablet-purpose-${tablet.id?c}" class="tablet-purpose" value="<#if tablet.purpose??>${tablet.purpose.purpose}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Интервьюер:</label>
                        </div>
                        <input type="text" title="Интервьюер" id="tablet-interviewer-${tablet.id?c}" class="tablet-interviewer" value="<#if tablet.interviewer??>${tablet.interviewer.shortName}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Подрядчик:</label>
                        </div>
                        <input type="text" title="Подрядчик" id="tablet-contractor-${tablet.id?c}" class="tablet-contractor" value="<#if tablet.contractor??>${tablet.contractor.code}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Дата транзакции:</label>
                        </div>
                        <input type="text" title="Дата транзакции" id="tablet-trans-data-${tablet.id?c}" class="tablet-trans-date" value="<#if tablet.transaction??>${tablet.transaction.date}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Ответственный:</label>
                        </div>
                        <input type="text" title="Ответственный" id="tablet-resp-${tablet.id?c}" class="tablet-resp" value="<#if tablet.transaction??>${tablet.transaction.respUser.getName()}</#if>" disabled/>
                    </div>

                    <div class="field-container">
                        <div class="label-container">
                            <label>Дата инв-и:</label>
                        </div>
                        <input type="text" title="Дата инвернтаризации" id="tablet-inv-date-${tablet.id?c}" value="${tablet.inventoryDate}"  disabled/>
                    </div>

                    <textarea title="Основание" id="tablet-inventory-reason-${tablet.id?c}" class="tablet-inventory-reason" placeholder="Основание" disabled>${tablet.inventoryReason}</textarea>


                </div>
                <div class="submit-container">
                    <input type="submit" class="input-for-submit button-effect" id="submit-tablet" value="Сохранить"/>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </form>
        </div>
        </#list>
        </#if>
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