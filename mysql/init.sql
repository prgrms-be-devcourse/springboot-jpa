CREATE
DATABASE IF NOT EXISTS test;

USE test;

create table customers
(
    id bigint primary key ,
    username varchar(20),
    name varchar(20),
    password varchar(50),
    nickname varchar(20)
);


CREATE
DATABASE IF NOT EXISTS test_code;
USE test_code;

create table customers
(
    id bigint primary key ,
    username varchar(20),
    name varchar(20),
    password varchar(50),
    nickname varchar(20)
);