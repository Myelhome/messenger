create sequence hibernate_sequence start 1 increment 1;

create table usr
(
    id              int8 primary key not null,
    activation_code varchar(255),
    active          boolean          not null,
    email           varchar(255)     not null,
    password        varchar(255)     not null,
    username        varchar(255)     not null
);

create table message
(
    id       int8 primary key not null,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(255)     not null,
    user_id  int8 references usr (id)
);

create table user_role
(
    user_id int8         not null references usr (id),
    roles   varchar(255) not null
);
