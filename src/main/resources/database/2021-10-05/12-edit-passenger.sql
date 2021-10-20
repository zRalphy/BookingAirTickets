--liquibase formatted sql
--changeset rmecwaldowski:4

    ALTER TABLE passenger
         ADD reservationId BIGINT NOT NULL;

   ALTER TABLE passenger
         ADD CONSTRAINT passengerReservationId
         FOREIGN KEY (reservationId) REFERENCES reservation(id);
