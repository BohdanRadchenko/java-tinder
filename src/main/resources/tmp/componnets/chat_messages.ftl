<div class="chat__messages">
    <#if chatId?has_content>
        <div class="chat__messages-header">
            <div class="chat__users-item-avatar">
                <img src="${currentChat.avatar()}" alt="avatar">
            </div>
            <span>${currentChat.subtitle()}</span>
        </div>
        <ul class="chat__messages-list">
            <#list messages as message>
                <li class="chat__messages-item ${(message.from().id() == userId)?then("right", "left")}">
                    <#if message.from().id() != userId>
                        <div class="chat__messages-item__avatar">
                            <img src="${message.from().avatar()}" alt="avatar">
                        </div>
                    </#if>
                    <div class="chat__messages-item__wrapper">
                        <div class="chat__messages-item__inner">
                            <#if message.content().type() == "LINK">
                                <p><a href="${message.content().message()}"
                                      target="_blank">${message.content().message()}</a></p>
                            <#else >
                                <p>${message.content().message()}</p>
                            </#if>
                        </div>
                        <span class="chat__messages-item__time">
                            <#if message.from().id() != userId>
                                ${message.from().firstName()},
                            </#if>
                            ${message.content().time()}
                        </span>
                    </div>
                </li>
            </#list>
        </ul>
        <div class="chat__messages-text__container">
            <textarea
                    form="form-message"
                    id="text-message"
                    name="text"
                    class="chat__messages-text"
                    placeholder="Write a message..."
                    rows="2"
                    autofocus
                    data-autoresize
            ></textarea>
            <form
                    id="form-message"
                    class="chat__messages-text__form"
            >
                <button id="form-message-submit" type="submit" disabled>
                    <i class="fa fa-paper-plane" aria-hidden="true"></i>
                </button>
            </form>
        </div>
    <#else>
        <div class="chat__empty">
            <p>Select a user for start messaging</p>
        </div>
    </#if>
</div>