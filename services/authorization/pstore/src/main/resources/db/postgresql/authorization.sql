-- alter table permissions_actions drop constraint FK85F82042E2DC84FD;
DROP TABLE IF EXISTS accounts_roles CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS permissions_actions CASCADE;
DROP TABLE IF EXISTS permissions_roles CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence;

create table accounts_roles (HJID int8 not null, account_id varchar(128) not null, created_at timestamp not null, role_id varchar(128) not null, 
	role_name varchar(255) not null, screen_name varchar(255), user_id varchar(128) not null, primary key (HJID), unique (account_id, role_id));

create table permissions (csid varchar(128) not null, action_group varchar(128), attribute_name varchar(128), created_at timestamp not null, description varchar(255), effect varchar(32) not null, 
	metadata_protection varchar(255), actions_protection varchar(255), 
	resource_name varchar(128) not null, tenant_id varchar(128) not null,
	updated_at timestamp, primary key (csid));

create table permissions_actions (HJID int8 not null, name varchar(128) not null, objectIdentity varchar(128) not null, objectIdentityResource varchar(128) not null, 
	ACTION__PERMISSION_CSID varchar(128), primary key (HJID));

	create table permissions_roles (HJID int8 not null, actionGroup varchar(255), created_at timestamp not null, permission_id varchar(128) not null, permission_resource varchar(255), role_id varchar(128) not null, role_name varchar(255), primary key (HJID), unique (permission_id, role_id));

create table roles (csid varchar(128) not null, created_at timestamp not null, description varchar(255), displayname varchar(200) not null, rolegroup varchar(255), 
	rolename varchar(200) not null, tenant_id varchar(128) not null, 
	metadata_protection varchar(255), perms_protection varchar(255), 
	updated_at timestamp, primary key (csid), unique (rolename, tenant_id), unique (displayname, tenant_id));

alter table permissions_actions add constraint FK85F82042E2DC84FD foreign key (ACTION__PERMISSION_CSID) references permissions;
create sequence hibernate_sequence;
