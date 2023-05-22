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
    <#include "../componnets/aside.ftl">
    <div class="container auth">
        <#nested>
    </div>
    </body>
</#macro>