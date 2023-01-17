<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as L>

<@C.page>
    ${message!""}
    <p class="error-message">${error!""}</p>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <@L.login "/login" false/>
</@C.page>