--liquibase formatted sql
--changeset rmecwaldowski:4

CREATE TABLE reservation(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status ENUM ('IN_PROGRESS', 'REALIZED', 'CANCELED'),
    flightId BIGINT,
    userId BIGINT NOT NULL
);

ALTER TABLE reservation
      ADD CONSTRAINT reservationFlightId
      FOREIGN KEY (flightId) REFERENCES flight(id);

ALTER TABLE reservation
      ADD CONSTRAINT reservationUserId
      FOREIGN KEY (userId) REFERENCES user(id);


