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
                const formBtnSubmit = document.querySelector(".chat__messages-text__form button[type=\"submit\"]");
                const messageList = document.querySelector(".chat__messages-list");

                const scrollMessageList = () => {
                    messageList.scrollTo(0, messageList.scrollHeight);
                }

                const updateList = data => {
                    const parsedData = JSON.parse(data, (key, value) => value);
                    console.log(parsedData);


                    const li = document.createElement("li");
                    li.style.opacity = 0;
                    li.classList.add("chat__messages-item");
                    li.classList.add(${userId} === parsedData.from.id ? "right" : "left");

                    if (parsedData.from.id !== ${userId}) {
                        const avatarContainer = document.createElement("div");
                        avatarContainer.classList.add("chat__messages-item__avatar");
                        const avatar = document.createElement("img");
                        avatar.src = parsedData.from.avatar;
                        avatar.alt = parsedData.from.name;
                        avatarContainer.appendChild(avatar)
                        li.appendChild(avatarContainer)
                    }

                    const msgWrapper = document.createElement("div");
                    msgWrapper.classList.add("chat__messages-item__wrapper");

                    const msgInner = document.createElement("div");
                    msgWrapper.classList.add("chat__messages-item__inner");

                    const content = document.createElement("p");
                    content.style.whiteSpace = "pre-wrap";

                    if (parsedData.message.type === "LINK") {
                        const a = document.createElement("a");
                        a.href = parsedData.message.content;
                        a.innerText = parsedData.message.content;
                        a.target = "_blank";
                    } else {
                        content.innerText = parsedData.message.content;
                    }

                    msgInner.appendChild(content);
                    msgWrapper.appendChild(msgInner);
                    li.appendChild(msgWrapper);

                    messageList.appendChild(li)

                    const t = setTimeout(() => {
                        li.style.opacity = 1;
                        clearTimeout(t);
                    }, 1);

                    scrollMessageList();
                }

                const socketSend = message => {
                    const data = {
                        id: ${userId},
                        message: message,
                    }
                    webSocket.send(JSON.stringify(data));
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
                        console.log("onmessage event.data", event.data);
                        updateList(event.data);
                    };

                    webSocket.onclose = event => {
                        console.log("onclose: Connection Closed", event);
                    };
                }

                const sendMessage = message => {
                    if (webSocket === undefined
                        || webSocket === null
                        || message.length === 0) return;
                    socketSend(message)
                    textarea.value = "";
                    textarea.style.height = 'auto';
                    formBtnSubmit.disabled = true;
                }


                // chat list
                usersList.onclick = e => {
                    e.preventDefault();
                    const chatId = e.target.getAttribute("data-chatId");
                    if (chatId === null) return;
                    const path = "/" + location.pathname.split("/")[1];
                    location.href = new URL(path + "/" + chatId, location)
                }

                // messages
                textarea.onkeydown = e => {
                    if (e.keyCode === 13 && e.shiftKey) {
                        e.preventDefault();
                        sendMessage(e.target.value)
                    }
                    if (e.target.value.length !== 0) formBtnSubmit.disabled = false;
                    if (e.target.value.length === 0) formBtnSubmit.disabled = true;
                }

                form.onsubmit = e => {
                    e.preventDefault();
                    sendMessage(new FormData(form).get("text"))
                }


                // autoresize
                const autoResize = () => {
                    document.querySelectorAll('[data-autoresize]').forEach(element => {
                        const offset = element.offsetHeight - element.clientHeight;
                        element.addEventListener('input', event => {
                            event.target.style.height = 'auto';
                            if (event.target.style.height >= 400) {
                                event.target.style.height = event.target.scrollHeight + offset + 'px';
                            }
                        });
                        element.removeAttribute('data-autoresize');
                    });
                }

                // call
                scrollMessageList();
                autoResize();
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