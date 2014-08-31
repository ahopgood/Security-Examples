CREATE database IF NOT EXISTS security_test;
 
GRANT ALL PRIVILEGES ON security_test.* TO 'test'@'localhost' IDENTIFIED BY 'test'WITH GRANT OPTION;

USE security_test;

CREATE TABLE IF NOT EXISTS unencrypted_users (username VARCHAR(255), password VARCHAR(255));

insert into unencrypted_users values ("admin","admin");
insert into unencrypted_users values ("mjoel","jdfj");
insert into unencrypted_users values ("ahopgood","rikg");
insert into unencrypted_users values ("fkrona","ary7");
insert into unencrypted_users values ("npatel","4mh2");
insert into unencrypted_users values ("sgurr","mvj8");
insert into unencrypted_users values ("vvenkata","okf6");

/* Create a table where users are primary and unique keys */

CREATE TABLE IF NOT EXISTS encrypted_users (username VARCHAR(255), password VARCHAR(255));

