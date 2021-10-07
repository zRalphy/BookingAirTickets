--liquibase formatted sql
-- changeset rmecwaldowski:1
CREATE TABLE passenger(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(45) NOT NULL,
    lastName VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    country VARCHAR(45) NOT NULL,
    telephone VARCHAR(45) NOT NULL
);