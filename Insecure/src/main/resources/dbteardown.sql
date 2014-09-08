CREATE database IF NOT EXISTS security_test;
 
GRANT ALL PRIVILEGES ON security_test.* TO 'test'@'localhost' IDENTIFIED BY 'test'WITH GRANT OPTION;

USE security_test;

DROP TABLE IF EXISTS unencrypted_users;

/* Create a table where users are primary and unique keys */

DROP TABLE IF EXISTS encrypted_users;

