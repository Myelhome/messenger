<#import "login.ftlh" as L>
<#include "security.ftlh">

<nav class="navbar navbar-expand-lg navbar-dark bg-info">
    <a class="navbar-brand" href="/">Messenger</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">Users</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3"><a href="/login">${name_}</a></div>
        <#if isRegistered>
            <@L.logout />
        </#if>
    </div>
</nav>
