<div class="chat__users d-none d-sm-flex">
    <div class="chat__users-header">
        Chats
    </div>
    <ul class="chat__users-list">
        <#list chats as chat>
            <li
                    class="chat__users-item ${(chatId == chat.chat())?then("active", "")}"
                    data-chatId=${chat.chat()}
            >
                <div class="chat__users-item-container">
                    <div class="chat__users-item-avatar">
                        <img src="${chat.avatar()}" alt="avatar">
                    </div>
                    <div class="chat__users-item-content">
                        <span>${chat.title()}</span>
                        <span>${chat.subtitle()}</span>
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