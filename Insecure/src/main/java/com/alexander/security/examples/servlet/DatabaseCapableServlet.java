package com.alexander.security.examples.servlet;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;

public abstract class DatabaseCapableServlet extends HttpServlet {

	private static final long serialVersionUID = -2324123221565332552L;

	static {
		BasicConfigurator.configure();
		try {
			//Initialise DB Driver for mysql
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
