<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User name: </label>
            <div class="col-sm-10">
                <input type="text" name="username" placeholder="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-10">
                <input type="password" name="password" placeholder="password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Register<#else>Sing in</#if></button>
    </form>
    <#if !isRegisterForm><div><a  href="/registration">Registration</a></div><#else><div><a  href="/login">Authorization</a></div></#if>
</#macro>

<#macro logout>
    <form action=/logout method="post">
        <button class="btn btn-primary" type="submit">Sign Out</button>
        <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>
    </form>
</#macro>