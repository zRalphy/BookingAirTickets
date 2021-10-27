--liquibase formatted sql
--changeset rmecwaldowski:4

ALTER TABLE flight
      ADD airportId BIGINT;

ALTER TABLE flight
      ADD CONSTRAINT flightAirportId
      FOREIGN KEY (AirportId) REFERENCES airport(id);