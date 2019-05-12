<#macro header>
    <header>
        <div class="header-panel">
            <div class ="logo-container"><a href="/"><div class = "logo"></div></a></div>
            <div class="menu-container">
                <div class="menu">
                    <div class="tablet-menu"><a class="head-menu" href="/">УСТРОЙСТВА</a>
                        <ul class="tablet-submenu">
                            <li><a href="/tablets" id="menu-2">ПЛАНШЕТЫ</a></li>
                            <li><a href="/akb" id="menu-3">АКБ</a></li>
                            <li><a href="/addDevice" id="menu-4">ДОБАВИТЬ</a></li>
                        </ul>
                        <div class="hidden-shadow"></div>
                    </div>
                    <div class="trans-menu"><a class="head-menu" href="/">ТРАНЗАКЦИИ</a>
                        <ul class="tablet-submenu">
                            <li><a href="/issue" id="menu-5">ВЫДАТЬ</a></li>
                            <li><a href="/shipment" id="menu-6">ОТПРАВКА</a></li>
                            <li><a href="/return" id="menu-7">ВОЗВРАТ</a></li>
                            <li><a href="/transaction" id="menu-8">ИСТОРИЯ</a></li>
                        </ul>
                        <div class="hidden-shadow"></div></div>
                    <div class="other-menu"><a class="head-menu" href="/">ТАБЛИЦЫ</a>
                        <ul class="tablet-submenu">
                            <li><a href="/users" id="menu-9">ПОЛЬЗОВАТЕЛИ</a></li>
                            <li><a href="/interviewers" id="menu-10">ИНТЕРВЬЮЕРЫ</a></li>
                            <li><a href="/otherTable" id="menu-12">РАЗНОЕ</a></li>
                        </ul>
                        <div class="hidden-shadow"></div></div>
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