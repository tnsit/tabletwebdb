<#macro header>
    <header>
        <div class="header-panel">
            <div class ="logo-container"><a href="/"><div class = "logo"></div></a></div>
            <div class="menu-container">
                <div class="menu">
                    <div class="tablet-menu"><a class="head-menu" href="/tabletsSV">ПЛАНШЕТЫ</a>

                    </div>
                    <div class="trans-menu"><a class="head-menu" href="/transactionSV">ТРАНЗАКЦИИ</a>
                        </div>
                </div>
            </div>
            <div class = "right-panel">
                <div class = "logout" title="Выход">
                    <form action="/logout" method="post">
                        <input class="exit" type="submit" value=""/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
                <div class="user">
                    <div class = "name">${user.name?upper_case}</div>
                    <div class = "role">${role}</div>
                </div>
            </div>
        </div>
    </header>
</#macro>