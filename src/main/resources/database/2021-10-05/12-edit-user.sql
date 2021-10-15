--liquibase formatted sql
-- changeset rmecwaldowski:2
   ALTER TABLE user
         ADD CONSTRAINT user_id
         FOREIGN KEY (id) REFERENCES reservation(id)
