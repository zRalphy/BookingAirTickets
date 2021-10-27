--liquibase formatted sql
--changeset rmecwaldowski:4

ALTER TABLE flight
      ADD departureAirportId BIGINT;
      ADD arrivalAirportId BIGINT;

ALTER TABLE flight
      ADD CONSTRAINT departureAirportId
      FOREIGN KEY (departureAirportId) REFERENCES airport(id);

      ALTER TABLE flight
            ADD CONSTRAINT arrivalAirportId
            FOREIGN KEY (arrivalAirportId) REFERENCES airport(id);