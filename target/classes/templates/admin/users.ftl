<#import "../parts/headerAdmin.ftl" as h>
<#import "../parts/message.ftl" as m>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/admin/usersCreate.css">
    <link rel="stylesheet" href="/css/admin/usersList.css">
    <link rel="stylesheet" href="/css/search.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/users/passwordCheck.js"></script>
    <script src="/script/admin/users/searchStyle.js"></script>
    <script src="/script/admin/users/searchClear.js"></script>
    <script src="/script/admin/users/userHeightDiv.js"></script>
    <script src="/script/admin/users/changePassword.js"></script>
    <script src="/script/admin/users/search.js"></script>
    <script src="/script/menuActive.js"></script>

    <meta charset="UTF-8">
    <title>TabletDB</title>
</head>
<@h.header>
</@h.header>
<div class="main-for-create">
<input type="hidden" id="hidden-for-menu" value="menu-9"/>
<div class="create-main">
    <div class="create-container">
        <div class="head-label">
            <p>СОЗДАТЬ ПОЛЬЗОВАТЕЛЯ</p>
        </div>
        <div class="create-form-container">
            <form method="post" class="create-form" action="/users">
                <div class="name-container create">
                    <div class="label-container"><label class="label-for-name"> Фамилия Имя:</label></div>
                    <input type="text" class="input-for-name" name="regname" pattern="[А-Я][а-я]{2,} [А-Я][а-я]{2,}" title="Фамилия Имя" required/>
                </div>
                <div class="login-container create">
                    <div class="label-container"><label class="label-for-login">Логин:</label></div>
                    <input type="text" class="input-for-login" name="reglogin" pattern="[a-z]{6,}" title="Маленькие латинские буквы. Минимум 6 символов." required/>
                </div>
                <div class="role-container create">
                    <div class="label-container"><label class="label-for-role">Роль:</label></div>
                    <select class="select-for-role" name="regrole" required>
                        <option></option>
                        <option selected>Менеджер</option>
                        <option>Супервайзер</option>
                        <option>Администратор</option>
                    </select>
                </div>
                <div class="password-container create">
                    <div class="label-container"><label class="label-for-password">Пароль:</label></div>
                    <input type="password" class="input-for-password" name="regpass" id="regpass" pattern="[A-Za-z0-9]{6,}" title="Латинские буквы и цифры. Минимум 6 символов." required/>
                </div>
                <div class="activity-container create">
                    <div class="label-container"><label for="create-activity" class="label-for-activity">Активность:</label></div>
                    <div class="create-checkbox"><input type="checkbox" class="input-for-activity" id="create-activity" name="regactive" checked/><div class="fake-checkbox"></div></div>
                </div>
                <div class="password2-container create">
                    <div class="label-container"><label class="label-for-password2">Пароль еще раз:</label></div>
                    <input type="password" class="input-for-password2" id="regpass2" required oninput="removeAttrStyle()"/>
                </div>
                <div class="submit-container create">
                    <input type="submit" id="submit-save" class="input-for-submit button-effect" value="Сохранить" onclick="validateForm ()"/>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>

</div>
</div>

<div class="main-for-list">
<div class="userlist-main">
    <div class="userlist-container">
        <div class="search-container">
            <div class="search-with-icon">
            <div class="search-icon-background">
                <div class="search-icon"></div>
            </div>
            <input class="search-input" id="user-search" type="text" pattern="[а-я]" title="Поиск по пользователям"/>
                <div class="search-clear" title="Очистить поиск"></div>
            </div>
        </div>

        <div class="list-container">
            <div class="head-container">
                <div class="head-name">Имя</div>
                <div class="head-login">Логин</div>
                <div class="head-role">Роль</div>
                <div class="head-activ">Активность</div>
            </div>
            <#list users as user>
            <div class="user-container">
                <div class="user-line-container" onclick="userHeightDiv(this)">
                    <div class="user-line-name">${user.name}</div>
                    <div class="user-line-login">${user.login}</div>
                    <div class="user-line-role"><#list user.roles as role>
                        <#if role=="ADMIN">
                        Администратор
                        <#elseif role == "SUPER">
                        Супервайзер
                        <#elseif role == "MANAGER">
                        Менеджер
                        </#if>
                        </#list>
                    </div>
                    <div class="user-line-activity">${user.active?string("Да", "Нет")}</div>
                </div>
                <div class="user-form-container">
                    <form method="post" class="user-form" action="/userChange">
                        <input type="hidden" name="user-id" value="${user.id?c}">
                        <div class="user-name-container change-user">
                        <div class="label-container"><label class="label-for-name">Фамилия Имя:</label></div>
                            <input type="text" value="${user.name}" class="input-for-changename" name="changename" pattern="[А-Я][а-я]{2,} [А-Я][а-я]{2,}" title="Фамилия Имя" required/>
                        </div>

                        <div class="user-login-container change-user">
                            <div class="label-container"><label class="label-for-login">Логин:</label></div>
                            <input type="text" value="${user.login}" class="input-for-changelogin" name="changelogin" pattern="[a-z]{6,}" title="Маленькие латинские буквы. Минимум 6 символов." required/>
                        </div>

                        <div class="user-role-container change-user">
                            <div class="label-container"><label class="label-for-role">Роль:</label></div>
                            <select class="select-for-change-role" title="Выберите роль" name="changerole" required>
                                <#list user.roles as role>
                                <option <#if role=="MANAGER">selected</#if>>Менеджер</option>
                                <option <#if role=="SUPER">selected</#if>>Супервайзер</option>
                                <option <#if role=="ADMIN">selected</#if>>Администратор</option>
                                </#list>
                            </select>
                        </div>

                        <div class="user-password-container change-user">
                            <div class="label-container"><label class="label-for-password">Пароль:</label></div>
                            <input type="password" class="input-for-changepassword" name="changepass" id="password-user-${user.id?c}" oninput="setPattern(this)" title="Латинские буквы и цифры. Минимум 6 символов."/>
                        </div>

                        <div class="user-activity-container change-user">
                            <div class="label-container"><label for="create-activity" class="label-for-activity">Активность:</label></div>
                            <div class="create-checkbox"><input type="checkbox" class="input-for-changeactivity" name="changeactive" ${user.active?string("checked", "")}/><div class="fake-checkbox"></div></div>
                        </div>

                        <div class="user-password2-container change-user">
                            <div class="label-container"><label class="label-for-password2">Пароль еще раз:</label></div>
                            <input type="password" class="input-for-password2" id="password2-user-${user.id?c}" oninput="removeAttrStyleChange(this)"/>
                        </div>

                        <div class="submit-container create">
                            <input type="submit" class="input-for-submit button-effect" id="user-${user.id?c}" value="Сохранить" onclick="validateFormChange (this)"/>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
            </div>
            </#list>
        </div>
    </div>
</div>


<@m.messages>
</@m.messages>


</div>
</html>