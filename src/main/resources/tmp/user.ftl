<#import "common/template.ftl" as t>
<#import "common/links.ftl" as l>

<#--    variables-->
<#--    user  -->

<@t.page>
    <@t.head title="Users">
        <@l.fas/>
    </@t.head>
    <@t.body_auth>
        <div class="col-8 mt-5">
            <div class="card">
                <div class="card-body">
                    <div class="row flex-column">
                        <div class="user-avatar mb-4">
                            <img
                                    src="${user.avatar()}"
                                    alt="${user.fullName()}"
                            >
                        </div>
                        <h3 class="mb-0 text-truncated text-center mb-4">${user.fullName()}</h3>
                        <div class="row justify-content-center justify-content-lg-around ">
                            <div class="col-11 col-lg-5 mb-2">
                                <button type="button" class="btn btn-outline-danger btn-block"><span
                                            class="fa fa-times"></span> Dislike
                                </button>
                            </div>
                            <div class="col-11 col-lg-5">
                                <button class="btn btn-outline-success btn-block"><span class="fa fa-heart"></span> Like
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </@t.body_auth>
</@t.page>