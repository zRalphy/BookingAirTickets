--liquibase formatted sql
-- changeset rmecwaldowski:2

CREATE TABLE reservation(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(45) NOT NULL
);