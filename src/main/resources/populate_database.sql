truncate shiro_permission;
truncate shiro_permission_role;
truncate shiro_role;
truncate shiro_user;
truncate shiro_user_role;
truncate shiro_configuration;

insert into shiro_user (username, password) values ('user', '$shiro1$SHA-256$1000$9Fm5pLxx0HKsBy8E4yREVA==$9/edfVtTZtHzA59fZEWL6FfkTJnRObv2e958DbfDAZs='); -- password = pass
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
insert into shiro_permission (name) values ('users:write');
insert into shiro_permission_role (role_id, permission_id) values (
    (select id from shiro_role where name='admin'),
    (select id from shiro_permission where name='users:write')
);
insert into shiro_permission (name) values ('users:read');
insert into shiro_permission_role (role_id, permission_id) values (
    (select id from shiro_role where name='admin'),
    (select id from shiro_permission where name='users:read')
);

insert into shiro_user (username, password) values ('guest', '$shiro1$SHA-256$1000$NsTSjKXaRNY7fH4eO5/Jpg==$jSkHapx1HWcOSTr8CFWSwvGqP2jo8P+LhhNU938a7cg='); -- password = guest
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
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_MAX_LENGTH','15');
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_MIN_LENGTH','6');
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_VALID_CHARACTERS','[\x21-\x7E]*');
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_COMPLEXITY_RULE','.*[A-Z].*');
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_COMPLEXITY_RULE','.*[a-z].*');
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_COMPLEXITY_RULE','.*[0-9].*');
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_COMPLEXITY_RULE','.*[\W_].*');
insert into shiro_configuration (configuration_key,configuration_value) values ('PASSWORD_MINIMUM_COMPLEXITY','3');



commit;