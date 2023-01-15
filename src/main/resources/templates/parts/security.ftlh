<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>

<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUser()
        name_ = user.getUsername()
        isAdmin = user.isAdmin()
        isRegistered = true
    >
<#else>
    <#assign
        name_ = "unknown"
        isAdmin = false
        isRegistered = false
    >
</#if>