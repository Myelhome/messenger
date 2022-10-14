<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as L>

<@C.page>
Login page
<@L.login "/login" />
<div>If you are yet not registered please do by clicking <a  href="/registration">this</a></div>
</@C.page>