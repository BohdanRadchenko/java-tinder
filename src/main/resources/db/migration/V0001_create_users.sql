create table users
(
    id         int auto_increment,
    email      varchar(255)                       null,
    password   varchar(255)                       null,
    first_name text                               null,
    last_name  text                               null,
    avatar     text                               null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint id
        unique (id)
);

create table user_login
(
    user_id int                                not null
        primary key,
    ip      text                               null,
    time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint user_login_users_id_fk
        foreign key (user_id) references users (id)
            on delete cascade
);

create table user_likes
(
    user_from int        not null,
    user_to   int        not null,
    value     tinyint(1) not null,
    constraint user_likes_users_id_fk
        foreign key (user_from) references users (id)
            on delete cascade,
    constraint user_likes_users_id_fk2
        foreign key (user_to) references users (id)
            on delete cascade
);




