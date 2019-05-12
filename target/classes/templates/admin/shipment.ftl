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
    <link rel="stylesheet" href="/css/admin/shipment.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/issue/getPin.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/addDevice/getFileName.js"></script>
    <script src="/script/admin/shipment/displayForm.js"></script>
    <script src="/script/admin/shipment/shipmentHeightDiv.js"></script>
    <script src="/script/admin/shipment/getDevicesShipment.js"></script>
    <script src="/script/admin/shipment/sendShipmentTemplate.js"></script>
    <script src="/script/admin/shipment/saveElement.js"></script>
    <script src="/script/admin/shipment/deleteElement.js"></script>
    <script src="/script/admin/shipment/copyElement.js"></script>
    <script src="/script/admin/shipment/addLineShipment.js"></script>
    <script src="/script/admin/shipment/resultExcel.js"></script>
    <script src="/script/admin/shipment/resultSave.js"></script>
    <script src="/script/admin/shipment/getDevicesShipmentSoft.js"></script>
    <script src="/script/admin/shipment/getDevicesShipmentOther.js"></script>
    <script src="/script/admin/shipment/setContractor.js"></script>
    <script src="/script/admin/shipment/setCity.js"></script>
    <script src="/script/admin/shipment/submitShipmentSoft.js"></script>
    <script src="/script/admin/shipment/statusSelect.js"></script>
    <script src="/script/admin/shipment/submitShipmentOther.js"></script>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>

<@tr.transaction name="ОТПРАВКА ПАНШЕТОВ" menu="6">

<div class="type-shipment">
    <select  id="type" title="Выберите тип отправки">
        <option selected>По шаблону</option>
        <option>Подрядчик заберет сам</option>
        <option>Нестандартная отправка</option>
    </select>
</div>

<div id="template-shipment-container">
<div id="from-excel">
    <form id="send-template-shipment" method="post" action="/">
    <div id="load-file-container">
        <div class="get-file-label"><p>Заявка на отправку</p></div>
        <label id="label-for-load" for="file">Выбрать</label>
        <input type="file" id="file" name="upload-file" accept=".xls,.xlsx"/>
        <div class="clear-fix"></div>
    </div>

    <div class="submit-container">
        <div id="file-name"><p></p></div>
        <input type="submit" class="input-for-submit button-effect disabled-submit" id="submit-akb" value="Спарсить" disabled/>
        <div class="clear-fix"></div>
    </div>
    </form>

    <div id="add-hand-container">
        <div class="add-shipment-label"><p>Заполнить вручную</p></div>
        <input type="button" id="add-line" value="Добавить"/>
        <div class="clear-fix"></div>
    </div>
    <div class="clear-fix"></div>
</div>
<div id="shipment-main">

</div>
<div class="excel-and-save">
    <input title="Сохранить" value="Сохранить" type="button" class="result-to-db" disabled>
    <input title="Файл отправки" value="Файл отправки" type="button" class="result-file">

    <div class="clear-fix"></div>
</div>

</div>

<div id="soft-shipment-container" style="display: none;">
    <form id="soft-search-tablet-form" method="post" action="/">
        <input placeholder="Номера планшетов и акб через запятую" class="soft-search-tablet-input" type="text"/>
        <div class="soft-search-tablet-submit-container"><input type="submit" class="soft-search-tablet-submit" title="Поиск" value=""/></div>
        <div class="clear-fix"></div>
    </form>

    <div class="soft-list-headline-container">

    </div>

    <div class="soft-list-element-container">

    </div>



    <div class="contractor-soft">
        <select class="contractor-select-soft" title="Выберите подрядчика">
            <option>Выберите подрядчика</option>
            <#list contractors as c>
                <option>${c.getCode()}</option>
            </#list>
        </select>
    </div>

    <div class="city-soft">
        <select class="city-select-soft" title="Выберите город">
            <option>Выберите город</option>
            <#list city as c>
                <option>${c.getCity()}</option>
            </#list>
        </select>
    </div>



    <div id="result-form">
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <div class="result-soft" id="result-tablet-soft"><p></p></div>
            <div class="clear-fix"></div>
        </div>
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Акб:</label>
                <div class="clear-fix"></div>
            </div>
            <div class="result-soft" id="result-akb-soft"><p></p></div>
            <div class="clear-fix"></div>
        </div>
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Подрядчик:</label></div>
            <div class="result-soft" id="result-contractor-soft"><p></p></div>
        </div>
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Город:</label></div>
            <div class="result-soft" id="result-city-soft"><p></p></div>
        </div>

        <div class="submit-container-soft">
            <input type="button" class="input-for-submit-soft button-effect" id="submit-tablet-soft" value="Сохранить"/>
        </div>

    </div>
</div>







<div id="other-shipment-container" style="display: none;">
    <form id="other-search-tablet-form" method="post" action="/">
        <input placeholder="Номера планшетов и акб через запятую" class="other-search-tablet-input" type="text"/>
        <div class="other-search-tablet-submit-container"><input type="submit" class="other-search-tablet-submit" title="Поиск" value=""/></div>
        <div class="clear-fix"></div>
    </form>

    <div class="other-list-headline-container">

    </div>

    <div class="other-list-element-container">

    </div>

    <div class="city-soft">
        <select class="city-select-other" title="Выберите город">
            <option>Выберите город</option>
            <#list city as c>
                <option>${c.getCity()}</option>
            </#list>
        </select>
    </div>

    <div class="other-status">
        <select class="status-select-soft" title="Выберите статус">
            <option>Выберите статус</option>
            <#list status as s>
                <option>${s.getStatus()}</option>
            </#list>
        </select>
    </div>

    <textarea title="Комментарий к статусу" placeholder="Комментарий к статусу" class="status-comment"></textarea>


    <div id="result-form">
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <div class="result-other" id="result-tablet-other"><p></p></div>
            <div class="clear-fix"></div>
        </div>
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Акб:</label>
                <div class="clear-fix"></div>
            </div>
            <div class="result-other" id="result-akb-other"><p></p></div>
            <div class="clear-fix"></div>
        </div>
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Город:</label></div>
            <div class="result-other" id="result-city-other"><p></p></div>
        </div>
        <div class="result-input-container-soft">
            <div class="label-container-soft"><label>Статус:</label></div>
            <div class="result-other" id="result-status-other"><p></p></div>
        </div>

        <div class="submit-container-other">
            <input type="button" class="input-for-submit-other button-effect" id="submit-tablet-other" value="Сохранить"/>
        </div>

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