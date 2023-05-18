<#import "common/template.ftl" as t>

<@t.page>
    <@t.head title="Chat">
        <link rel="stylesheet" href="static/css/chat.css">

        <script type="text/javascript">
            document.addEventListener("DOMContentLoaded", () => {

                const usersList = document.querySelector(".chat__users-list");
                usersList.onclick = function handleChatItemClick(e) {
                    e.preventDefault();
                    const chatId = e.target.getAttribute("data-chatId");
                    const path = "/" + location.pathname.split("/")[1];
                    location.href = new URL(path + "/" + chatId, location)
                }
            });
        </script>
    </@t.head>
    <@t.body_auth>
        <div class="chat">
            <div class="chat__users">
                <div class="chat__users-header">
                    Chats
                </div>
                <ul class="chat__users-list">
                    <#list chats as chat>
                        <li
                                class="chat__users-item ${(chatId == chat.chatId())?then("active", "")}"
                                data-chatId=${chat.chatId()}
                        >
                            <div class="chat__users-item-container">
                                <div class="chat__users-item-avatar">
                                    <img src="https://www.w3schools.com/w3images/avatar2.png" alt="Avatar">
                                </div>
                                <div class="chat__users-item-content">
                                    <p>${chat.to().email()}</p>
                                    <p>${chat.lastMessage()}</p>
                                </div>
                            </div>
                        </li>
                    <#else>
                        <li class="chat__empty">
                            <p>No chats</p>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="chat__messages">
                <#if chatId?has_content>
                    <div class="chat__messages-header">
                        Chat header
                    </div>
                    <ul class="chat__messages-list">
                        <#list messages as message>
                            <li class="chat__messages-item ${(message.from().id() == userId)?then("right", "left")}">
                                <div class="chat__messages-item__wrap">
                                    <span>${message.msg()}</span>
                                </div>
                            </li>
                        </#list>
                    </ul>
                    <div class="chat__messages-text__container">
                        <textarea
                                class="chat__messages-text"
                                autofocus
                                placeholder="Write a message..."
                        >
                        </textarea>
                    </div>
                <#else>
                    <div class="chat__empty">
                        <p>Select a user for start messaging</p>
                    </div>
                </#if>
            </div>
        </div>
    </@t.body_auth>
</@t.page>