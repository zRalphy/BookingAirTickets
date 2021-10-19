--liquibase formatted sql
--changeset rmecwaldowski:4

CREATE TABLE reservation(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status ENUM (IN_PROGRESS, REALIZED, CANCELED),
    flight_id BIGINT NOT NULL
);

ALTER TABLE reservation
             ADD CONSTRAINT reservation_id
             FOREIGN KEY (id) REFERENCES flight(id)
