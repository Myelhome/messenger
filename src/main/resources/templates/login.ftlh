<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as L>

<@C.page>
${message!""}
<@L.login "/login" false/>
</@C.page>