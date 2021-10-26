--liquibase formatted sql
-- changeset kkorzeniewski:5
INSERT INTO user_roles (user_id, roles_id)
VALUES ((SELECT id FROM user WHERE username = 'user' limit 1),
        (SELECT id FROM role WHERE name = 'USER' limit 1)),
       ((SELECT id FROM user WHERE username = 'admin' limit 1),
        (SELECT id FROM role WHERE name = 'ADMIN' limit 1)),
       ((SELECT id FROM user WHERE username = 'staff' limit 1),
        (SELECT id FROM role WHERE name = 'STAFF' limit 1));
