--liquibase formatted sql
-- changeset kkorzeniewski:2
insert into role (name) values ("ADMIN");
insert into role (name) values ("USER");
insert into role (name) values ("STUFF");