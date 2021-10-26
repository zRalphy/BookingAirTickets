-- liquibase formatted sql
-- changeset rmecwaldowski:5

CREATE TABLE airport(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(6) NOT NULL,
    name VARCHAR(45) NOT NULL,
    country VARCHAR(45) NOT NULL,
    city VARCHAR(45) NOT NULL
);
