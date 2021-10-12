--liquibase formatted sql
-- changeset kkorzeniewski:2
insert into user (username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled)
values ("user", "$2a$10$DaTPhkbpwjb1FdCXQEJsqeUnm.78OO923mcanDJSMxbW9gfgr/nPW", true, true, true, true);
insert into user (username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled)
values ("admin", "$2a$10$0bWCc58roGUzLsO3Ep0JGu52JfMnZY78xK9l455ATuQTHhp5oPzx2", true, true, true, true);

