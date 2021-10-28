-- liquibase formatted sql
-- changeset rmecwaldowski:6
DELETE FROM flight where id IS NOT NULL;
insert into flight (id, type, departureDate, arrivalDate, departureAirportId, arrivalAirportId) values (1, 'ECONOMY', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 7);
insert into flight (id, type, departureDate, arrivalDate, departureAirportId, arrivalAirportId) values (2, 'PREMIUM', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 8);
insert into flight (id, type, departureDate, arrivalDate, departureAirportId, arrivalAirportId) values (3, 'BUSINESS', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 3, 9);
insert into flight (id, type, departureDate, arrivalDate, departureAirportId, arrivalAirportId) values (4, 'ECONOMY', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 4, 10);