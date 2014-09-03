package com.alexander.security.examples.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	private static final Logger log = LoggerFactory.getLogger(DatabaseResetServlet.class);
	
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Properties connectionProps = new Properties();
		connectionProps.put(DBConstants.USER_KEY, DBConstants.DB_USER);
		connectionProps.put(DBConstants.PASSWORD_KEY, DBConstants.DB_PASSWORD);
		
		String sqlSetupFile = "WEB-INF/classes/dbsetup.sql";
		sqlSetupFile = this.getServletContext().getRealPath(sqlSetupFile);
		
        String connectionString = "jdbc:mysql://localhost/"+DBConstants.DATABASE_NAME;
		BufferedReader setupFile = new BufferedReader(new FileReader(sqlSetupFile));
        try {
			Connection conn = DriverManager.getConnection(connectionString, connectionProps);

			String sqlStatement = setupFile.readLine();
			Statement statement = conn.createStatement();
			statement.execute("drop table unencrypted_users;");
			while (sqlStatement != null){
				log.debug(sqlStatement);
				sqlStatement = setupFile.readLine();		
				if(sqlStatement != null && !sqlStatement.trim().isEmpty()){
					statement.addBatch(sqlStatement);
				}
			}
			setupFile.close();
			statement.executeBatch();
//			conn.createStatement().addBatch("");
		} catch (SQLException e) {
			log.error("Couldn't connect to the database", e);
		} finally {
			setupFile.close();
		}
		
		
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(resp);
				
//		super.doGet(req, resp);
    }
}



















