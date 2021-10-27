-- liquibase formatted sql
-- changeset rmecwaldowski:5

CREATE TABLE airport(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(6) NOT NULL,
    name VARCHAR(150) NOT NULL,
    country VARCHAR(100) NOT NULL
);
