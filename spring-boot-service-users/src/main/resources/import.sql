insert into users (username, password, enabled, name, lastname, email) values ('dscifo', '12345', 1, 'Diego', 'Scifo', 'dscifo@mail.com');
insert into users (username, password, enabled, name, lastname, email) values ('admin', '12345', 1, 'John', 'Rambo', 'jrambo@mail.com');

insert into roles (name) values ('ROLE_USER');
insert into roles (name) values ('ROLE_ADMIN');

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (2, 2);
insert into users_roles (user_id, role_id) values (2, 1);