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
    <div class="collapse" id="collapseIdForPost">
        <form class="mt-3" method="post" action="/insert" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control" type="text" name="text" placeholder="Text">
            </div>
            <div class="form-group">
                <input class="form-control" type="text" name="tag" placeholder="Tag">
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
            <div class="card my-3">
                <#--            <b>${message.id}</b>-->
                <div>
                    <#if message.filename??>
                        <img class="card-img-top" src="/img/${message.filename}">
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span>
                    <span>#<a href="main?filter=${message.tag}">${message.tag?lower_case}</a></span>
                </div>
                <div class="card-footer text-muted">
                    <a href="/user/${message.authorId}">${message.authorName}</a>
                </div>
            </div>
        <#else>
            No messages
        </#list>
    </div>
</@C.page>