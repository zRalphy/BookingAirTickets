--liquibase formatted sql
--changeset rmecwaldowski:4

CREATE TABLE reservation(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status ENUM (REALIZED, IN_PROGRESS, CANCELED),
    flight_id BIGINT NOT NULL,
    passenger_id BIGINT NOT NULL

    ALTER TABLE reservation
             ADD CONSTRAINT reservation_id
             FOREIGN KEY (id) REFERENCES flight(id)
);