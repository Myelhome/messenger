<#import "parts/common.ftlh" as C>

<@C.page>
    ${message!""}
    <p class="error-message">${error!""}</p>
    <h5>${username}</h5>
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-10">
                <input type="password" name="password" placeholder="password"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-10">
                <input type="email" name="email" placeholder="xxxx@xxxx.xxx" <#if email??> value="${email}"</#if>/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Save</button>
    </form>
</@C.page>