<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as L>

<@C.page>
<div class="mb-1"> Add new user ${err!""} </div>
<@L.login "/registration" true/>
</@C.page>