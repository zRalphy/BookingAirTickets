--liquibase formatted sql
-- changeset kkorzeniewski:3
insert into role (name) values ("STAFF");
insert into user (username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled)
values ("staff", "$2a$10$MQ3gurCRjaLMydFnWm32A.bRDNni9W6sJi/mwed6CfJBZjx3u1A3W", true, true, true, true);