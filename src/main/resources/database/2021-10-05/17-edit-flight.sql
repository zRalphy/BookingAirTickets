-- liquibase formatted sql
-- changeset rmecwaldowski:5

ALTER TABLE flight
      ADD CONSTRAINT airportFlightId
      FOREIGN KEY (flightId) REFERENCES airport(id);