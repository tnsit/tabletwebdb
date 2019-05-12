<#macro transaction name menu>
    <input type="hidden" id="hidden-for-menu" value="menu-${menu}"/>

<div class="main-issue">
    <div class="main-for-action">
        <div class="head-label">
            <p>${name}</p>
        </div>
        <#nested>
    </div>



    <div class="main-for-pin">
        <div class="head-label">
            <p>ПИНЫ</p>
        </div>
        <form id="search-pin-form" method="post" action="/">
            <input title="Номера планшетов через запятую" class="search-pin-input" type="text"/>
            <div class="search-pin-submit-container"><input type="submit" class="search-pin-submit" title="Поиск" value=""/></div>
            <div class="clear-fix"></div>
        </form>

        <div class="list-pin-container">
        </div>
    </div>
    <div class="clear-fix"></div>
</div>




<div id="message-for-ajax">

</div>
</#macro>