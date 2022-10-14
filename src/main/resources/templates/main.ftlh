<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as L>

<@C.page>
    <div>
        <@L.logout />
        <span><a href="/user">User List</a></span>
    </div>

    <div>
        <form method="post" action="/insert" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Text">
            <input type="text" name="tag" placeholder="Tag">
            <input type="file" name="file" class="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Create</button>
        </form>
    </div>

    <div>
        <form method="get" action="/main">
            <input type="text" name="filter" placeholder="Tag" value="${filter!}">
            <button type="submit">Find</button>
        </form>
    </div>

    <div>Messages list
        <#if filter??>
            filtered by <b>${filter}</b>
        </#if>
        :</div>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}">
                </#if>
            </div>
        </div>
    <#else>
        No messages
    </#list>
</@C.page>