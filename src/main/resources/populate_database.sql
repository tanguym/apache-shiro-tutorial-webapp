truncate shiro_permission;
truncate shiro_permission_role;
truncate shiro_role;
truncate shiro_user;
truncate shiro_user_role;

insert into shiro_user (username, password) values ('user', 'pass');
insert into shiro_role (name) values ('admin');
insert into shiro_user_role (user_id, role_id) values (
	(select id from shiro_user where username='user'),
    (select id from shiro_role where name='admin')
);
insert into shiro_permission (name) values ('read');
insert into shiro_permission_role (role_id, permission_id) values (
    (select id from shiro_role where name='admin'),
    (select id from shiro_permission where name='read')
);

insert into shiro_user (username, password) values ('guest', 'guest');
insert into shiro_role (name) values ('reading');

insert into shiro_user_role (user_id, role_id) values (
	(select id from shiro_user where username='guest'),
    (select id from shiro_role where name='reading')
);
insert into shiro_permission_role (role_id, permission_id) values (
    (select id from shiro_role where name='reading'),
    (select id from shiro_permission where name='read')
);
insert into shiro_user_role (user_id, role_id) values (
    (select id from shiro_user where username='user'),
    (select id from shiro_role where name='reading')
);



commit;