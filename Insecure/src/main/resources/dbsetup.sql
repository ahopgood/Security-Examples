CREATE database IF NOT EXISTS security_test;
 
GRANT ALL PRIVILEGES ON security_test.* TO 'test'@'localhost' IDENTIFIED BY 'test'WITH GRANT OPTION;

USE security_test;

CREATE TABLE IF NOT EXISTS unencrypted_users (username VARCHAR(255), password VARCHAR(255));

insert into unencrypted_users values ("admin","admin");
insert into unencrypted_users values ("bob","smith");
insert into unencrypted_users values ("john","doe");

CREATE TABLE IF NOT EXISTS encrypted_users (username VARCHAR(255), password VARCHAR(255));