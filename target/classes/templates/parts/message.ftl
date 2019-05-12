<#macro messages>
    <#if message?has_content>
        <div class="error-background">
            <div class="error-container">
                <p class="error-message">${message}</p>
                <div class="button-container"><input class="close-error button-effect" type="button" value="Закрыть"/></div>
            </div>
        </div>
    </#if>
</#macro>

<#macro waiting>
        <div class="error-background-waiting waiting-ajax" style="display: none">
            <div class="error-container">
                <p class="error-message">Взламываем пентагон...</p>
            </div>
        </div>
</#macro>

