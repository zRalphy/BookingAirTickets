--liquibase formatted sql
-- changeset kkorzeniewski:2
CREATE TABLE user(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(60) NOT NULL,
    accountNonExpired BOOLEAN NOT NULL,
    accountNonLocked BOOLEAN NOT NULL,
    credentialsNonExpired BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL
);