<#import "../parts/headerAdmin.ftl" as h>
<#import "../parts/message.ftl" as m>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <link rel="stylesheet" href="/css/admin/addDevice.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/addDevice/saveAkb.js"></script>
    <script src="/script/admin/addDevice/saveTablet.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/addDevice/displayForm.js"></script>
    <script src="/script/admin/addDevice/getFileName.js"></script>
    <script src="/script/admin/addDevice/sendFile.js"></script>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>
<input type="hidden" id="hidden-for-menu" value="menu-4"/>
<div class="main">
    <div class="main-for-tablet">
        <div class="head-label">
            <p>ПЛАНШЕТ</p>
        </div>

        <div class="field-container">
            <select title="Вариант создания" id="add-select" required>
                <option>Вручную</option>
                <option>Из документа Excel</option>
            </select>
        </div>

        <form id="tablet-form" method="post" action="/">
            <div class="field-container">
                <div class="label-container">
                    <label>Номер:</label>
                </div>
                <input type="text" title="Номер минимум из 5 цифр" id="tablet-number" pattern="[0-9]{4,}" required/>
            </div>

            <div class="field-container">
                <div class="label-container">
                    <label>Модель:</label>
                </div>
                <select title="Модель" id="tablet-model" required>
                    <option></option>
                    <#list modelTablet as mt>
                    <option>${mt.modelTablet}</option>
                    </#list>
                </select>
            </div>

            <div class="field-container">
                <div class="label-container">
                    <label>Статус:</label>
                </div>
                <select title="Статус" id="tablet-status" required>
                    <#list status as st>
                        <option <#if st.status=="рабочий">selected</#if>>${st.status}</option>
                    </#list>
                </select>
            </div>

            <textarea title="Комментарий к статусу" id="status-comment" placeholder="Комментарий к статусу" rows="5"></textarea>

            <div class="field-container">
                <div class="label-container">
                    <label>IMEI:</label>
                </div>
                <input type="text" title="IMEI из 15 цифр" id="tablet-imei"  pattern="[0-9]{15,}" required/>
            </div>

            <div class="field-container">
                <div class="label-container">
                    <label>Версия OS:</label>
                </div>
                <select title="Версия OS" id="tablet-os" required>
                    <option></option>
                    <#list os as o>
                        <option>${o.os}</option>
                    </#list>
                </select>
            </div>

            <div class="field-container">
                <div class="label-container">
                    <label>Телефон:</label>
                </div>
                <input type="text" title="Телефон из 10 цифр: 9061234567" id="tablet-phone"  pattern="[0-9]{10}" required/>
            </div>

            <div class="field-container">
                <div class="label-container">
                    <label>Пин:</label>
                </div>
                <input type="text" title="Пин-код от Kids Place, 4 цифры" id="tablet-pin"  pattern="[0-9]{4,}" required/>
            </div>

            <textarea title="Комментарий к планшету" id="tablet-comment" placeholder="Комментарий к планшету" rows="5"></textarea>

            <div class="submit-container">
                <input type="submit" class="input-for-submit button-effect" id="submit-tablet" value="Сохранить"/>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <form id="from-excel" method="post" action="/" style="display: none;">
            <div class="a-container"><a href="/files/add_tablet_template.xlsx" id="down-templ"><div class="down-templ-container">Скачать шаблон</div></a></div>
            <div id="load-file-container">
                <label id="label-for-load" for="file">Выбрать файл</label>
                <input type="file" id="file" name="upload-file" accept=".xls,.xlsx"/>
                <div id="file-name"><p></p></div>
            </div>
            <div class="submit-container">
                <input type="submit" class="input-for-submit button-effect disabled-submit" id="submit-akb" value="Отправить" disabled/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>

    <div class="main-for-akb">
        <div class="head-label">
            <p>АКБ</p>
        </div>
        <form method="post" id="akb-form" action="/">
            <div class="field-container">
                <div class="label-container">
                    <label>Номер:</label>
                </div>
                <input type="text" title="Номер виде A1234, буква A латинская." id="akb-number" pattern="[A][0-9]{4,}" required/>
            </div>

            <div class="field-container">
                <div class="label-container">
                    <label>Модель:</label>
                </div>
                <select title="Модель" id="akb-model" required>
                    <option></option>
                    <#list modelAkb as ma>
                    <option>${ma.modelAkb}</option>
                    </#list>
                </select>
            </div>

            <div class="field-container">
                <div class="label-container">
                    <label>Статус:</label>
                </div>
                <select title="Статус" id="akb-status" required>
                    <#list status as st>
                        <option>${st.status}</option>
                    </#list>
                </select>
            </div>

            <div class="submit-container">
                <input type="submit" class="input-for-submit button-effect" id="submit-akb" value="Сохранить"/>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

    </div>
</div>
<div id="message-for-ajax">

</div>

<@m.waiting>
</@m.waiting>

</body>
</html>