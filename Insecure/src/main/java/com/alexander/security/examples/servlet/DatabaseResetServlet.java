package com.alexander.security.examples.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseResetServlet extends DatabaseCapableServlet {

	private static final long serialVersionUID = 4619773769340919564L;
	private static final Logger log 	= LoggerFactory.getLogger(DatabaseResetServlet.class);
	private static int REDIRECT_TIME 	= 5;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties connectionProps = new Properties();
		connectionProps.put(DBConstants.USER_KEY, DBConstants.DB_USER);
		connectionProps.put(DBConstants.PASSWORD_KEY, DBConstants.DB_PASSWORD);
		
		String sqlSetupFile 	= this.getServletContext().getRealPath(DBConstants.DB_SETUP_SCRIPT);
		String sqlTeardownFile 	= this.getServletContext().getRealPath(DBConstants.DB_TEARDOWN_SCRIPT);
		
        String connectionString = "jdbc:mysql://localhost/"+DBConstants.DATABASE_NAME;
        PrintWriter out = response.getWriter(); //used for errors
		try {
			Connection conn = DriverManager.getConnection(connectionString, connectionProps);
			try {
				Statement statement = conn.createStatement();
				statement = this.addFileToBatch(statement, sqlTeardownFile);
				statement = this.addFileToBatch(statement, sqlSetupFile);
			
				statement.executeBatch();
				log.debug("Context path {}",request.getContextPath());
//				response.sendRedirect(HTMLConstants.INJECTION_INDEX);
				out.println(HTMLConstants.createMetaRedirect(REDIRECT_TIME, HTMLConstants.INJECTION_INDEX));
				out.println("<body><h4>Database reset successful .. redirecting in "+REDIRECT_TIME+" seconds</h4></body>");
			} catch (SQLException e) {
				log.error("Something database related went wrong", e);
		        out.print(e);
			} catch (Exception e){
		        out.print(e);
			} finally {
		        conn.close();
			}
		} catch (SQLException e) {
			log.error("There was an issue connecting to the database", e);
	        out.print(e);
		} finally {
			out.flush();
			out.close();  
		}
//		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(resp);
//		super.doGet(request, response);
		return;
    }
	
	protected Statement addFileToBatch(Statement statement, String filePath){
		BufferedReader setupFile;
		try {
			setupFile = new BufferedReader(new FileReader(filePath));
			String sqlStatement 		= setupFile.readLine();
			try {
				while (sqlStatement != null){
					log.debug(sqlStatement);
					sqlStatement = setupFile.readLine();		
					if(sqlStatement != null && !sqlStatement.trim().isEmpty()){
						statement.addBatch(sqlStatement);
					}
				}
			} catch (SQLException e) {
				log.error("Could not add statement {} to batch file", sqlStatement);
				log.error("",e);
			} finally {
					setupFile.close();
			}
		} catch (FileNotFoundException e) {
			log.error("Couldn't find the file {}", filePath, e);
			log.error("",e);
		} catch (IOException e) {
			log.error("Couldn't open the file {}", filePath, e);
			log.error("",e);
		}

		return statement;
	}
}



















