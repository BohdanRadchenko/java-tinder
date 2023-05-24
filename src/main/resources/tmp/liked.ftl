<#import "common/template.ftl" as t>
<#import "common/links.ftl" as l>

<#--    variables-->
<#--    users  -->


<@t.page>
    <@t.head title="Liked list">
        <script type="text/javascript">
            document.addEventListener("DOMContentLoaded", () => {
                const nodeTr = document.querySelectorAll("tr");
                nodeTr.forEach(tr => {
                    tr.onclick = e => {
                        const form = e.currentTarget.querySelector("form");
                        form.submit();
                    }
                })
            });
        </script>

    </@t.head>
    <@t.body_auth>
        <div class="row my-5">
            <div class="col-8 offset-2">
                <div class="panel panel-default user_panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">User List</h3>
                    </div>
                    <div class="panel-body">
                        <div class="table-container">
                            <table class="table-users table" border="0">
                                <tbody>
                                <#list users as user>
                                    <tr>
                                        <form method="POST" style="width: 0; height: 0; opacity: 0;">
                                            <input type="text" value="${user.id()}" name="toId" hidden>
                                        </form>
                                        <td width="10">
                                            <div class="user-avatar">
                                                <img src="${user.avatar()}"/>
                                            </div>

                                        </td>
                                        <td class="align-middle">${user.fullName()}</td>
                                        <td class="align-middle">${user.profession()}</td>
                                        <td class="align-middle">
                                            <#if user.lastLogin()??>
                                                Last Login: ${user.lastLoginDate()}
                                                <br>
                                                <small class="text-muted">${user.lastLoginAgo()}</small>
                                            <#else >
                                                Last login info
                                            </#if>
                                        </td>
                                    </tr>
                                <#else >
                                    <tr>Nothing found</tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </@t.body_auth>
</@t.page>