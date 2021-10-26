--liquibase formatted sql
-- changeset kkorzeniewski:4
-- removing data from user_roles table as original roles has been granted to wrong users
DELETE FROM user_roles where user_id IS NOT NULL
