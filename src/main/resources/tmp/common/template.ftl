<#macro page>
    <!doctype html>
    <html lang="en">
    <#nested>
    </html>
</#macro>

<#macro head title>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>${title}</title>
        <link rel="icon" href="static/img/favicon.ico">
        <link href="static/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="static/css/style.css">
        <#nested>
    </head>
</#macro>

<#macro body>
    <body>
    <div class="container">
        <#nested>
    </div>
    </body>
</#macro>

<#macro body_auth>
    <body>
    <aside class="sidebar d-none d-sm-flex">
        <nav class="d-flex flex-column mb-4">
            <a class="d-inline-flex mb-12 justify-content-center w-100 rst-btn mb-5" href="/">
                <img src="static/img/logo.png" height="52" alt="logo">
            </a>
            <a
                    href="/users"
                    class="d-none d-md-inline-flex mb-4 justify-content-center w-100 rst-btn"
            >
                <i class="fas fa-solid fa-users"></i>
            </a>
            <a
                    href="/liked"
                    class="d-none d-md-inline-flex mb-4 justify-content-center w-100 rst-btn"
            >
                <i class="fas fa-solid fa-heart"></i>
            </a>
            <a
                    href="/messages"
                    class="d-none d-md-inline-flex mb-4 justify-content-center w-100 rst-btn"
            >
                <i class="fas fa-solid fa-comments"></i>
            </a>
        </nav>
        <form action="/logout" method="POST">
            <button class="rst-btn d-flex justify-content-center w-100 align-content-center">
                <i class="fas fa-sign-out-alt"></i>
            </button>
        </form>
    </aside>
    <div class="container auth">
        <#nested>
    </div>
    </body>
</#macro>