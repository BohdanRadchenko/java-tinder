<#import "common/template.ftl" as t>

<@t.page>
    <@t.head title="Chat">
        <link rel="stylesheet" href="static/css/chat.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
              integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp"
              crossorigin="anonymous">

        <script type="text/javascript">
            document.addEventListener("DOMContentLoaded", () => {
                //ws
                const wsUrl = `ws://localhost:8080/chats/${chatId}`;
                let webSocket;

                const usersList = document.querySelector(".chat__users-list");
                const textarea = document.querySelector("#text-message");
                const form = document.querySelector("#form-message");
                const messageList = document.querySelector(".chat__messages-list");

                const scrollMessageList = () => {
                    messageList.scrollTo(0, messageList.scrollHeight);
                }

                const updateList = message => {
                    messageList.innerHTML += "<br/>" + message;
                    scrollMessageList();
                }

                const connect = () => {
                    if (webSocket !== undefined
                        && webSocket.readyState !== WebSocket.CLOSED) {
                        return;
                    }

                    webSocket = new WebSocket(wsUrl);

                    webSocket.onopen = event => {
                        console.log("onopen: Connected!", event);
                    };

                    webSocket.onmessage = event => {
                        console.log("onmessage event", event);
                        console.log("onmessage event.data", event.data);
                        updateList(event.data);
                    };

                    webSocket.onclose = event => {
                        console.log("onclose: Connection Closed", event);
                    };
                }

                const sendMessage = message => {
                    if (webSocket === undefined || webSocket === null) return;
                    webSocket.send(message);
                    textarea.value = "";
                }

                // messages
                usersList.onclick = e => {
                    e.preventDefault();
                    const chatId = e.target.getAttribute("data-chatId");
                    if (chatId === null) return;
                    const path = "/" + location.pathname.split("/")[1];
                    location.href = new URL(path + "/" + chatId, location)
                }

                textarea.onkeydown = e => {
                    if (e.keyCode === 13 && e.shiftKey) {
                        e.preventDefault();
                        sendMessage(e.target.value)
                    }
                }

                form.onsubmit = e => {
                    e.preventDefault();
                    sendMessage(new FormData(form).get("text"))
                }

                scrollMessageList();
                connect();
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