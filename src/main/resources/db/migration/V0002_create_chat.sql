create table chats
(
    id   int auto_increment primary key,
    uuid text not null
);

create table chat_user
(
    chat_id int not null,
    user_id int not null,
    primary key (chat_id, user_id),
    constraint chat_user_chats_id_fk
        foreign key (chat_id) references chats (id)
            on delete cascade,
    constraint chat_user_users_id_fk
        foreign key (user_id) references users (id)
            on delete cascade
);

create table messages
(
    id         int auto_increment primary key,
    chat       int                                not null,
    user_from  int                                not null,
    content    text                               null,
    type       int      default 1                 not null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint messages_chats_id_fk
        foreign key (chat) references chats (id)
            on delete cascade,
    constraint messages_messages_types_id_fk
        foreign key (type) references messages_types (id),
    constraint messages_users_id_fk
        foreign key (user_from) references users (id)
            on delete cascade
);

create table messages_types
(
    id   int auto_increment primary key,
    type text not null
);






