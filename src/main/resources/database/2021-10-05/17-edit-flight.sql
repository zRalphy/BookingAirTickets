-- liquibase formatted sql
-- changeset rmecwaldowski:5

ALTER TABLE flight
      ADD airportCode BIGINT;

ALTER TABLE flight
      ADD CONSTRAINT flightAirportCode
      FOREIGN KEY (airportCode) REFERENCES airport(code);