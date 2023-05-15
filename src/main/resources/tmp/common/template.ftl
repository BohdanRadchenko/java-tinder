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