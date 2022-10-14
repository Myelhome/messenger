<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as L>

<@C.page>
Add new user ${err!""}
<@L.login "/registration" />
</@C.page>