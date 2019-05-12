<#import "../parts/headerManager.ftl" as h>
<#import "../parts/message.ftl" as m>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org" >
<head>
    <link rel="stylesheet" href="/css/headerSV.css">
    <link rel="stylesheet" href="/css/search.css">
    <link rel="stylesheet" href="/css/errorMessage.css">
    <link rel="stylesheet" href="/css/manager/manager.css">
    <link rel="stylesheet" href="/css/manager/action.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="/script/menuActive.js"></script>
    <script src="/script/admin/users/searchStyle.js"></script>
    <script src="/script/admin/users/searchClear.js"></script>
    <script src="/script/admin/manager/tabActive.js"></script>
    <script src="/script/admin/manager/managerListHeightDiv.js"></script>
    <script src="/script/admin/manager/pinkLine.js"></script>
    <script src="/script/admin/manager/tabletsSelectAll.js"></script>
    <script src="/script/admin/manager/searchInter.js"></script>
    <script src="/script/admin/manager/selectInter.js"></script>
    <script src="/script/admin/manager/addInter.js"></script>
    <script src="/script/admin/manager/cancelAddInter.js"></script>
    <script src="/script/admin/manager/saveAddInter.js"></script>
    <script src="/script/admin/users/closeUserExists.js"></script>
    <script src="/script/admin/manager/closeInterSaved.js"></script>
    <script src="/script/admin/manager/editTablet.js"></script>
    <script src="/script/admin/manager/searchMain.js"></script>
    <script src="/script/admin/manager/searchMainClear.js"></script>
    <script src="/script/admin/manager/takeOffInter.js"></script>
    <script src="/script/admin/manager/cancelTakeOffInter.js"></script>
    <script src="/script/admin/manager/saveTakeOffInter.js"></script>
    <script src="/script/admin/manager/betweenShow.js"></script>
    <script src="/script/admin/manager/selectManager.js"></script>
    <script src="/script/admin/manager/searchManager.js"></script>
    <script src="/script/admin/manager/cancelBetween.js"></script>
    <script src="/script/admin/manager/saveBetween.js"></script>
    <script src="/script/admin/manager/pinkLineBetween.js"></script>
    <script src="/script/admin/manager/betweenSelectAll.js"></script>
    <script src="/script/admin/manager/betweenCancelWindow.js"></script>
    <script src="/script/admin/manager/betweenAcceptWindow.js"></script>
    <script src="/script/admin/manager/closeBetweenWindow.js"></script>
    <script src="/script/admin/manager/saveBetweenCancel.js"></script>
    <script src="/script/admin/manager/saveBetweenAccept.js"></script>
    <script src="/script/admin/manager/returnWindow.js"></script>
    <script src="/script/admin/manager/closeReturnWindow.js"></script>
    <script src="/script/admin/manager/saveReturn.js"></script>
    <script src="/script/admin/manager/returnSelectAll.js"></script>
    <script src="/script/admin/manager/returnCancelWindow.js"></script>
    <script src="/script/admin/manager/saveReturnCancel.js"></script>
    <script src="/script/admin/manager/managerExcel.js"></script>

    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>TabletDB</title>
</head>
<body>
<@h.header>
</@h.header>
<main>
<div id="tab-menu">
    <div class="tab-element tab-tablets tab-active" title="Спикок планшетов">
        <div class="tab-icon"></div>
        <div class="number"><p><#if tablets?size!=0>${tablets?size}<#else>0</#if></p></div>
    </div>
    <div class="tab-element tab-between" title="Передачи между пользователями">
        <div class="tab-icon"></div>
        <div class="number"><p>${size}</p></div>
    </div>
    <div class="tab-element tab-return" title="Возврат на склад">
        <div class="tab-icon"></div>
        <div class="number"><p><#if toWarehouse?size!=0>${toWarehouse?size}<#else>0</#if></p></div>
    </div>
    <div class="excel-history" title="История транзакций в Excel"></div>
    <div class="clear-fix"></div>
</div>

   <div class="main-container tablet-container show">
       <div class="search-container">
           <div class="search-with-icon">
               <div class="search-icon-background">
                   <div class="search-icon"></div>
               </div>
               <input class="search-input" id="inter-main-search" type="text" title="Поиск"/>
               <div class="search-clear" title="Очистить поиск"></div>
               <div class="clear-fix"></div>
           </div>
       </div>

       <div class="headline-container">
            <div class="checkbox-all-container">
                <input title="Выделить все" type="checkbox" class="check-all">
                <div class="fake-checkbox"></div>
            </div>
            <p>Номер</p>
            <p>Дата</p>
            <p>Статус</p>
            <p>Интервьюер</p>
            <p>Комментарий</p>
           <div class="clear-fix"></div>
       </div>

       <div class="list-container">
           <#if tablets?size!=0>
           <#list tablets as tablet>
           <div class="element-container">
               <div class="element-headline-container">
               <div class="checkbox-container">
                   <input title="Выделить" type="checkbox" class="check">
                   <div class="fake-checkbox"></div>
               </div>
               <div class="element-info-container" onclick="managerListHeightDiv(this)">
                   <p>${tablet.number}</p>
                   <p>${tablet.transaction.date}</p>
                   <p>${tablet.purpose.purpose}</p>
                   <#if tablet.interviewer??>
                   <p title="${tablet.interviewer.fullName}">${tablet.interviewer.shortName}</p>
                   <#else><p></p>
                   </#if>
                   <#if tablet.managerComment??>
                   <p>${tablet.managerComment}</p>
                   <#else><p></p>
                   </#if>
                   <div class="clear-fix"></div>
               </div>
               <div class="element-button-container">
                   <input class="add-inter-button element-button" title="Назначить интервьюера" type="button" <#if tablet.interviewer??>disabled</#if>/>
                   <input class="remove-inter-button element-button" title="Снять интервьюера" type="button" <#if tablet.interviewer??><#else>disabled</#if>/>
                   <input class="between-button element-button" title="Передать другому менеджеру" type="button" <#if tablet.interviewer??>disabled</#if>/>
                   <input class="return-button element-button" title="Вернуть на склад" type="button" <#if tablet.interviewer??>disabled</#if>/>
                   <div class="clear-fix"></div>
               </div>
               <div class="clear-fix"></div>
               </div>

               <div class="element-edit-container">
                    <select class="purpose-select" title="Выберите цель использования">
                        <#list purpose as p>
                            <#if p.purpose==tablet.purpose.purpose>
                            <option selected>${p.purpose}</option>
                            <#else>
                            <option>${p.purpose}</option>
                            </#if>
                        </#list>
                    </select>
                   <textarea title="Комментарий" class="manager-comment" placeholder="Комментарий"><#if tablet.managerComment??>${tablet.managerComment}</#if></textarea>
                   <div class="clear-fix"></div>
                   <div class="submit-container">
                       <input type="submit" class="input-for-submit button-effect submit-edit-tablet" value="Сохранить"/>
                   </div>

               </div>
           </div>
           </#list>

           <#else>
                <p class="empty-list">Планшеты в этой категории отсутствуют</p>
           </#if>
       </div>
   </div>

    <div class="main-container between-container">
        <div class="to-container">
            <div class="to-headline between-headline">
                <div class="checkbox-all-container">
                    <input title="Выделить все" type="checkbox" class="to-check-all">
                    <div class="fake-checkbox"></div>
                </div>
                <p>Номер</p>
                <p>Кому</p>
                <div class="clear-fix"></div>
            </div>

            <div class="to-element-container between-element-container">
                <#if toOtherUser?size!=0>
                <#list toOtherUser as to>
                <div class="to-element between-element">
                    <div class="checkbox-container">
                        <input title="Выделить" type="checkbox" class="between-check to-check">
                        <div class="fake-checkbox"></div>
                    </div>
                    <p class="to-element-number">${to.number}</p>
                    <p class="to-element-manager">${to.toOtherUser.name}</p>
                    <input class="to-cancel-button between-element-button" title="Отменить передачу" type="button"/>
                    <div class="clear-fix"></div>
                </div>
                </#list>
                <#else>
                <p class="empty-list2">Планшеты в этой категории отсутствуют</p>
                </#if>
            </div>
        </div>

        <div class="from-container">
            <div class="from-headline between-headline">
                <div class="checkbox-all-container">
                    <input title="Выделить все" type="checkbox" class="from-check-all">
                    <div class="fake-checkbox"></div>
                </div>
                <p>Номер</p>
                <p>От кого</p>
                <div class="clear-fix"></div>
            </div>

            <div class="from-element-container between-element-container">
                <#if fromOtherUser?size!=0>
                <#list fromOtherUser as from>
                    <div class="from-element between-element">
                        <div class="checkbox-container">
                            <input title="Выделить" type="checkbox" class="between-check from-check">
                            <div class="fake-checkbox"></div>
                        </div>
                        <p class="from-element-number">${from.number}</p>
                        <p class="from-element-manager">${from.user.name}</p>
                        <input class="from-accept-button between-element-button" title="Подтвердить получение" type="button"/>
                        <div class="clear-fix"></div>
                    </div>
                </#list>
                <#else>
                <p class="empty-list2">Планшеты в этой категории отсутствуют</p>
                </#if>
            </div>
        </div>
        <div class="clear-fix"></div>
    </div>





    <div class="main-container return-main-container">
        <div class="return-container">
            <div class="return-headline">
                <div class="checkbox-all-container">
                    <input title="Выделить все" type="checkbox" class="return-check-all">
                    <div class="fake-checkbox"></div>
                </div>
                <p>Номер</p>
                <div class="clear-fix"></div>
            </div>

            <div class="return-element-container">
                <#if toWarehouse?size!=0>
                <#list toWarehouse as to>
                <div class="return-element">
                    <div class="checkbox-container">
                        <input title="Выделить" type="checkbox" class="return-check">
                        <div class="fake-checkbox"></div>
                    </div>
                    <p class="return-element-number">${to.number}</p>
                    <input class="return-element-button" title="Отменить передачу" type="button"/>
                    <div class="clear-fix"></div>
                </div>
                </#list>
                <#else>
                <p class="empty-list2">Планшеты в этой категории отсутствуют</p>
                </#if>
            </div>
        </div>
        <div class="clear-fix"></div>
    </div>
</main>
<@m.messages>
</@m.messages>
<@m.waiting>
</@m.waiting>
<div id="message-for-ajax">

</div>

<div class="action-background add-inter" style="display: none;">
    <div class="add-inter-container">
        <p class="action-label">ПЕРЕДАЧА ПЛАНШЕТОВ ИНТЕРВЬЮЕРУ</p>

        <div class="search-inter-container">
            <div class="search-with-icon">
                <div class="search-icon-background">
                    <div class="search-icon"></div>
                </div>
                <input class="search-input" id="inter-search" type="text" title="Поиск" placeholder="Поиск по интервьюерам"/>
                <div class="clear-fix"></div>
            </div>
        </div>

        <div class="inter-list">
            <#list interviewers as inter>
                <div class="inter-element">
                    <input type="radio" class="inter-radio-group" id="inter-${inter.id?c}" name="radio"/>
                    <label class="label-for-inter" for="inter-${inter.id?c}">${inter.fullName}</label>
                    <input title="brigadir" class="brigadir" type="hidden" value="${inter.brig?then('1', '0')}">
                    <input title="can" type="hidden" class="can"
                    <#if inter.brig==false && inter.tablets??>
                        value="no"
                           <#else>
                           value="yes"
                    </#if>/>
                </div>
            </#list>
        </div>

        <div class="inter-tablet-result">
            <div class="inter-label-container">
                <label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>
        <div class="inter-inter-result">
            <div class="inter-label-container">
                <label>Интервьюер:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>

        <div class="inter-button-container">
            <input type="button" class="inter-cancel inter-button" id="inter-cancel" value="Закрыть"/>
            <input type="button" class="inter-save inter-button" id="inter-save" value="Сохранить"/>
        </div>
    </div>
</div>


<div class="action-background takeoff-inter" style="display: none;">
    <div class="add-inter-container">
        <p class="action-label">СНЯТИЕ ИНТЕРВЬЮЕРОВ</p>

        <div class="takeoff-inter-result">
            <div class="inter-label-container">
                <label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>

        <div class="inter-button-container">
            <input type="button" class="takeoff-inter-cancel inter-button" id="takeoff-inter-cancel" value="Закрыть"/>
            <input type="button" class="takeoff-inter-save inter-button" id="takeoff-inter-save" value="Сохранить"/>
        </div>
    </div>
</div>




<div class="action-background between-window" style="display: none;">
    <div class="from-to-container">
        <p class="action-label">ПЕРЕДАЧА ДРУГОМУ МЕНЕДЖЕРУ</p>

        <div class="search-inter-container">
            <div class="search-with-icon">
                <div class="search-icon-background">
                    <div class="search-icon"></div>
                </div>
                <input class="search-input" id="between-search" type="text" title="Поиск" placeholder="Поиск по пользователям"/>
                <div class="clear-fix"></div>
            </div>
        </div>

        <div class="between-list">
            <#list managers as manager>
                <div class="manager-element">
                    <input type="radio" class="between-radio-group" id="inter-${manager.id?c}" name="radio"/>
                    <label class="label-for-between" for="inter-${manager.id?c}">${manager.name}</label>
                </div>
            </#list>
        </div>

        <div class="between-tablet-result">
            <div class="between-label-container">
                <label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>
        <div class="between-manager-result">
            <div class="between-label-container">
                <label>Пользователь:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>

        <div class="inter-button-container">
            <input type="button" class="inter-cancel inter-button" id="between-cancel" value="Закрыть"/>
            <input type="button" class="inter-save inter-button" id="between-save" value="Сохранить"/>
        </div>
    </div>
</div>




<div class="action-background between-cancel-window" style="display: none;">
    <div class="add-inter-container">
        <p class="action-label">ОТМЕНИТЬ ПЕРЕДАЧУ ПЛАНШЕТОВ</p>

        <div class="takeoff-inter-result between-cancel-result">
            <div class="inter-label-container">
                <label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>

        <div class="inter-button-container">
            <input type="button" class="between-cancel-cancel inter-button" id="between-cancel-cancel" value="Закрыть"/>
            <input type="button" class="between-cancel-save inter-button" id="between-cancel-save" value="Сохранить"/>
        </div>
    </div>
</div>



<div class="action-background between-from-window" style="display: none;">
    <div class="add-inter-container">
        <p class="action-label">ПОДТВЕРДИТЬ ПОЛУЧЕНИЕ ПЛАНШЕТОВ</p>

        <div class="takeoff-inter-result between-accept-result">
            <div class="inter-label-container">
                <label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>

        <div class="inter-button-container">
            <input type="button" class="between-accept-cancel inter-button" id="between-accept-cancel" value="Закрыть"/>
            <input type="button" class="between-accept-save inter-button" id="between-accept-save" value="Сохранить"/>
        </div>
    </div>
</div>



<div class="action-background return-window" style="display: none;">
    <div class="return-window-container">
        <p class="action-label">ВОЗВРАТ НА СКЛАД</p>

        <div class="return-result">
            <div class="inter-label-container">
                <label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>

        <div class="inter-button-container">
            <input type="button" class="return-cancel inter-button" id="return-cancel" value="Закрыть"/>
            <input type="button" class="return-save inter-button" id="return-save" value="Сохранить"/>
        </div>
    </div>
</div>


<div class="action-background return-cancel-window" style="display: none;">
    <div class="add-inter-container">
        <p class="action-label">ОТМЕНИТЬ ВОЗВРАТ ПЛАНШЕТОВ</p>

        <div class="takeoff-inter-result return-cancel-result">
            <div class="inter-label-container">
                <label>Планшеты:</label>
                <div class="clear-fix"></div>
            </div>
            <p></p>
            <div class="clear-fix"></div>
        </div>

        <div class="inter-button-container">
            <input type="button" class="return-cancel-cancel inter-button" id="return-cancel-cancel" value="Закрыть"/>
            <input type="button" class="return-cancel-save inter-button" id="return-cancel-save" value="Сохранить"/>
        </div>
    </div>
</div>


</body>
</html>