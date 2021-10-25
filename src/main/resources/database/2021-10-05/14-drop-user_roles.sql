--liquibase formatted sql
-- changeset kkorzeniewski:4
DELETE FROM user_roles where user_id=1;
DELETE FROM user_roles where user_id=2;
DELETE FROM user_roles where user_id=3;
