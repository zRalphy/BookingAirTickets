--liquibase formatted sql
--changeset rmecwaldowski:4

   ALTER TABLE passenger
         ADD CONSTRAINT passenger_id
         FOREIGN KEY (id) REFERENCES reservation(id)
