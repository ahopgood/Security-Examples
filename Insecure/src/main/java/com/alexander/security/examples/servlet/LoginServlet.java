package com.alexander.security.examples.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	static {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String username	= request.getParameter(USERNAME_PARAM_KEY);
		System.out.println("Username: "+username);		
				
		String password = request.getParameter(PASSWORD_PARAM_KEY);
		System.out.println("Password: "+password);

        PrintWriter out = response.getWriter();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/"+DATABASE_NAME+"?user="+DB_USER+"&password="+DB_PASSWORD);
			PreparedStatement statement = conn.prepareStatement("Select * from "+DB_TABLE_NAME+" where username=\""+username+"\" and password=\""+password+"\";");
//			PreparedStatement statement = conn.prepareStatement("Select * from "+DB_TABLE_NAME+";");
			statement.execute();
			ResultSet result = statement.getResultSet();
			if (result.next()){
		        out.println( "User "+username+" logged in.");
			} else {
				out.println("Sorry your credentials are not sufficient.");
			}
//			while(result.next()){
//				String dbUname 		= result.getString(DB_USERNAME_COL);
//				String dbPassword 	= result.getString(DB_PASSWORD_COL);
//				System.out.println("DB Username "+dbUname+" Password "+dbPassword);
//			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        out.flush();
        out.close();
	}
}
