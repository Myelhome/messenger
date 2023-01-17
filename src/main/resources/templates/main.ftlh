<#import "parts/common.ftlh" as C>

<@C.page>
    <form class="form-inline mb-4" method="get" action="/main">
        <div class="form-row">
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="filter" placeholder="tag" value="${filter!}">
            </div>
        </div>
        <button class="btn btn-primary ml-3" type="submit">Search</button>
    </form>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseIdForPost" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Post new message
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseIdForPost">
        <form class="mt-3" method="post" action="/insert" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control ${(textError??)?string('is-invalid', '')}" type="text"
                       value="<#if message??>${message.text}</#if>" name="text" placeholder="Text">
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input class="form-control ${(tagError??)?string('is-invalid', '')}" type="text"
                       value="<#if tag??>${message.tag}</#if>" name="tag" placeholder="Tag">
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFileChose">
                    <label class="custom-file-label" for="customFileChose">Chose file</label>
                </div>
            </div>
            <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Create</button>
            </div>
        </form>
    </div>



    <div class="mt-3">Messages list
        <#if filter??>
            filtered by <b>${filter}</b>
        </#if>
        :
    </div>
    <div class="card-columns">
        <#list messages as message>
            <div class="card card-global my-3 border-0" style="max-width: 18rem;">
                <div class="ml-3">
                    <img class="img-custom-icon" src="/img/8366fdf4-92ed-43cc-89e4-427001481688.among_us_not.png">
                    <a href="/user/${message.authorId}">${message.authorName}</a>
                </div>
                <#if message.filename??>
                    <div class="card border-0 my-1">
                        <img class="card-img-top" src="/img/${message.filename}">
                    </div>
                </#if>
                <div>
                    <div class="ml-3">
                        <span>${message.text}</span>
                    </div>
                    <#if message.tagExist>
                        <div class="ml-3">
                            <span>#<a href="main?filter=${message.tag}">${message.tag?lower_case}</a></span>
                        </div>
                    </#if>
                </div>
            </div>
        <#else>
            No messages
        </#list>
    </div>
</@C.page>