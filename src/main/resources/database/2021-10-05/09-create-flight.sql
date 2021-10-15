-- liquibase formatted sql
-- changeset rmecwaldowski:3

CREATE TABLE flight(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type ENUM ('ECONOMY', 'PREMIUM', 'BUSINESS'),
    typeOfSeat VARCHAR(45) NOT NULL,
    typeOfLuggage VARCHAR(45) NOT NULL,
    departureDate TIMESTAMP,
    dateOfArrival TIMESTAMP
);