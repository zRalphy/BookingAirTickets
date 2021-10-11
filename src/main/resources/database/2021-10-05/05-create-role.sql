--liquibase formatted sql
-- changeset kkorzeniewski:2
CREATE TABLE role(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL
)