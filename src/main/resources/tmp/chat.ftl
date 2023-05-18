<#import "common/template.ftl" as t>

<@t.page>
    <@t.head title="Chat">
        <link rel="stylesheet" href="static/css/chat.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
              integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp"
              crossorigin="anonymous">

        <script type="text/javascript">
            document.addEventListener("DOMContentLoaded", () => {

                const usersList = document.querySelector(".chat__users-list");
                usersList.onclick = e => {
                    e.preventDefault();
                    const chatId = e.target.getAttribute("data-chatId");
                    if (chatId === null) return;
                    const path = "/" + location.pathname.split("/")[1];
                    location.href = new URL(path + "/" + chatId, location)
                }

                const textarea = document.querySelector("#text-message");
                const form = document.querySelector("#form-message");

                const sendMessage = message => {
                    console.log(message);
                }

                textarea.onkeydown = e => {
                    if (e.keyCode === 13 && e.shiftKey) {
                        sendMessage(e.target.value)
                    }
                }

                form.onsubmit = e => {
                    e.preventDefault();
                    sendMessage(new FormData(form).get("text"))
                }
            });
        </script>
    </@t.head>
    <@t.body_auth>
        <div class="chat">
            <#include "componnets/chat_list.ftl">
            <#include "componnets/chat_messages.ftl">
        </div>
    </@t.body_auth>
</@t.page>