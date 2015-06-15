create table shiro_user (
   id INT NOT NULL AUTO_INCREMENT,
   username VARCHAR(40) NOT NULL,
   password VARCHAR(256) NOT NULL,
   PRIMARY KEY ( id )
);
create unique index i_user_name on shiro_user(username);
create table shiro_role (
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(40) NOT NULL,
   PRIMARY KEY ( id )
);
create unique index i_role_name on shiro_role(name);
create table shiro_permission (
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(40) NOT NULL,
   PRIMARY KEY ( id )
);
create unique index i_permission_name on shiro_permission(name);

create table shiro_user_role (
   user_id INT NOT NULL,
   role_id INT NOT NULL,
   PRIMARY KEY ( user_id, role_id )
);
create table shiro_permission_role (
   permission_id INT NOT NULL,
   role_id INT NOT NULL,
   PRIMARY KEY ( permission_id, role_id )
);


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