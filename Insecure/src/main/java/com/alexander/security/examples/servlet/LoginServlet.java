package com.alexander.security.examples.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -1863888622010767349L;

	private static final String USERNAME_PARAM_KEY 	= "username";
	private static final String PASSWORD_PARAM_KEY 	= "password";
	
	private static final String DATABASE_NAME		= "security_test";
	private static final String DB_USER				= "test";
	private static final String DB_PASSWORD			= "test";
	
	private static final String DB_TABLE_NAME		= "unencrypted_users";
	private static final String DB_USERNAME_COL		= "username";
	private static final String DB_PASSWORD_COL		= "password";
	
	private static final String ENC_DB_TABLE_NAME	= "encrypted_users";
	private static final Logger log	= LoggerFactory.getLogger(LoginServlet.class);

	static {
		BasicConfigurator.configure();
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String username	= request.getParameter(USERNAME_PARAM_KEY);
		log.debug("Username: "+username);		
				
		String password = request.getParameter(PASSWORD_PARAM_KEY);
		log.debug("Password: "+password);

        PrintWriter out = response.getWriter();
        String connectionString = "jdbc:mysql://localhost/"+DATABASE_NAME+"?user="+DB_USER+"&password="+DB_PASSWORD+"&allowMultiQueries=true";
		try {
			log.debug("Connecting to the following database:");
			log.debug(connectionString);
			Connection conn = DriverManager.getConnection(connectionString);
			try {
				
				String sqlQuery = "Select * from "+DB_TABLE_NAME+" where "+DB_USERNAME_COL+"=\""+username+"\" and "+DB_PASSWORD_COL+"=\""+password+"\";";
				log.debug(sqlQuery);
				PreparedStatement statement = conn.prepareStatement(sqlQuery);
				statement.execute(); 
				ResultSet result = statement.getResultSet();
				statement.closeOnCompletion();
				if (result.next()){
					username = result.getString(1);
					out.println( "User "+username+" logged in.");
					out.println("SQL Query used:");
					out.println(conn.nativeSQL(sqlQuery));
				} else {
					out.println("Sorry your credentials are not sufficient.");
				}		
			} catch (SQLException e) {
				out.println(e.getLocalizedMessage());
				log.error("There was a issue with executing the SQL statement.",e);
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			out.println(e.getLocalizedMessage());
			log.error("There was a issue with executing the SQL connection.",e);
		}
        out.flush();
        out.close();
	}
}
