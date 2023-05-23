<#import "links.ftl" as l>

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
        <@l.basic/>
        <@l.fas/>

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