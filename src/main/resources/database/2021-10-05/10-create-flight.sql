--liquibase formatted sql
--changeset rmecwaldowski:2
CREATE TABLE flight(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(45) NOT NULL,
    typeOfSeat VARCHAR(45) NOT NULL,
    typeOfLuggage VARCHAR(45) NOT NULL,
    departureDate TIMESTAMP,
    dateOfArrival TIMESTAMP
);

    ALTER TABLE reservation
             ADD CONSTRAINT reservation_id
             FOREIGN KEY (id) REFERENCES flight(id)

