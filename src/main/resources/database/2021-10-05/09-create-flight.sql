-- liquibase formatted sql
-- changeset rmecwaldowski:3

CREATE TABLE flight(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type ENUM ('ECONOMY', 'PREMIUM', 'BUSINESS'),
    departureDate TIMESTAMP,
    arrivalDate TIMESTAMP
);