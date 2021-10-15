--liquibase formatted sql
-- changeset rmecwaldowski:2
   ALTER TABLE passenger
         ADD CONSTRAINT passenger_id
         FOREIGN KEY (id) REFERENCES reservation(id)