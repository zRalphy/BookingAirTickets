-- liquibase formatted sql
-- changeset rmecwaldowski:5

CREATE TABLE airport(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(3) NOT NULL,
    name VARCHAR(75) NOT NULL,
    country VARCHAR(50) NOT NULL
);
