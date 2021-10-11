--liquibase formatted sql
-- changeset kkorzeniewski:2
CREATE TABLE `user_roles` (
    `user_id` bigint NOT NULL,
    `roles_id` bigint NOT NULL,
    KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
    KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
    CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
);