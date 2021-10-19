--liquibase formatted sql
-- changeset rmecwaldowski:3
insert into flight (id, type, departureDate, arrivalDate) values (1, 'ECONOMY', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into flight (id, type, departureDate, arrivalDate) values (2, 'PREMIUM', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into flight (id, type, departureDate, arrivalDate) values (3, 'BUSINESS', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into flight (id, type, departureDate, arrivalDate) values (4, 'ECONOMY', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());