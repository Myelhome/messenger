<#import "parts/common.ftlh" as C>

<@C.page>
    User editor
    <form method="post" action="/user" xmlns="http://www.w3.org/1999/html">
        <div><lable>User Id : ${user.id}<input type="hidden" name="userId" value="${user.id}"></lable></div>
        <div><label> User Name : <input type="text" name="username" value="${user.username}"/> </label></div>
        <div><label> Password: <input type="text" name="password" value="${user.password}"/> </label></div>
        <#--        <div><label> Active: <input type="radio" name="active" value="${user.active}"/> </label></div>-->
                <div><label>
                        Roles:
                        <#list roles as role>
                            <div><lable><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</lable></div>
                        </#list>
                    </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Update</button>
    </form>
</@C.page>