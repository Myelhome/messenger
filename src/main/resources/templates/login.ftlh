<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as L>

<@C.page>
    ${message!""}
    <p class="error-message">${error!""}</p>
    <@L.login "/login" false/>
</@C.page>