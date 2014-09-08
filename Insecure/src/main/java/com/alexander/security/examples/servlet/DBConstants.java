package com.alexander.security.examples.servlet;

public class DBConstants {
	static final String DATABASE_NAME		= "security_test";
	static final String DB_USER				= "test";
	static final String DB_PASSWORD			= "test";

	//Unencrypted database keys
	static final String UNENC_TABLE_NAME	= "unencrypted_users";
	static final String UNENC_PASSWORD_COL	= "password";
	static final String UNENC_USERNAME_COL	= "username";
	
	//Property keys
	static final String USER_KEY			= "user";
	static final String PASSWORD_KEY		= "password";
	
	//Web application root path
	static final String WEB_APP_ROOT		= "WEB-INF/classes/";
	
	//DB Scripts
	static final String DB_SETUP_SCRIPT		= WEB_APP_ROOT+"dbsetup.sql";
	static final String DB_TEARDOWN_SCRIPT	= WEB_APP_ROOT+"dbteardown.sql";
}
