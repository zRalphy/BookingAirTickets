--liquibase formatted sql
-- changeset kkorzeniewski:2
Insert into user_roles (user_id, roles_id) value(1, 1);
Insert into user_roles (user_id, roles_id) value(2,2)