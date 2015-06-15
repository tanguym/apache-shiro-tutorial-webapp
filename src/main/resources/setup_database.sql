drop table shiro_permission;
drop table shiro_permission_role;
drop table shiro_role;
drop table shiro_user;
drop table shiro_user_role;

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