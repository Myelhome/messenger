<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User name: </label>
            <div class="col-sm-10">
                <input class="form-control ${(usernameError??)?string('is-invalid', '')}" type="text"
                       name="username" value="<#if user??>${user.username}</#if>" placeholder="username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-10">
                <input class="form-control ${(passwordError??)?string('is-invalid', '')}" type="password"
                       name="password" value="<#if user??>${user.password}</#if>" placeholder="password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Email: </label>
                <div class="col-sm-10">
                    <input class="form-control ${(emailError??)?string('is-invalid', '')}" type="email"
                           name="email" value="<#if user??>${user.email}</#if>" placeholder="xxxx@xxxx.xxx"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div>
                <div class="g-recaptcha" data-sitekey="6LcO_wkkAAAAAPlu8bInXkx5bW49znKF5K_4Ddio"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Register<#else>Sing in</#if></button>
    </form>
    <#if !isRegisterForm>
        <div><a href="/registration">Registration</a></div><#else>
        <div><a href="/login">Authorization</a></div></#if>
</#macro>

<#macro logout>
    <form action=/logout method="post">
        <button class="btn btn-primary" type="submit">Sign Out</button>
        <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>
    </form>
</#macro>