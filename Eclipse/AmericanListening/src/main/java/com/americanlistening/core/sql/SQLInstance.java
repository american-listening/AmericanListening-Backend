package com.americanlistening.core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A SQL Instance handles interfacing with a SQL server.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class SQLInstance {

	/**
	 * Creates a new SQL Instance.
	 * 
	 * @return The new SQL Instance.
	 */
	public static SQLInstance createInstnace() {
		return new SQLInstance();
	}

	private Connection con;

	private SQLHandler handler;

	private SQLInstance() { }

	/**
	 * Sets the SQL Handler for this instance.
	 * 
	 * @param handler
	 *            The handler to use.
	 */
	public void setSQLHandler(SQLHandler handler) {
		this.handler = handler;
	}

	/**
	 * Connects to a SQL server. If either <code>username</code> or
	 * <code>password</code> are <code>null</code> authentication will not be used.
	 * 
	 * @param url
	 *            The URL to connect to.
	 * @param username
	 *            The username to use.
	 * @param password
	 *            The password to use.
	 * @throws SQLException
	 *             If an SQL exception occurs.
	 */
	public void connect(String url, String username, String password) throws SQLException {
		con = (username != null && password != null) ? DriverManager.getConnection(url, username, password)
				: DriverManager.getConnection(url);
	}

	/**
	 * Executes a query on the SQL server.
	 * 
	 * @param query
	 *            The query to execute.
	 * @returns The result of the query.
	 * @throws SQLException
	 *             When an SQL exception occurs.
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(query);
		try {
			handler.execute(result, query);
		} finally {
			result.close();
		}
		return result;
	}

	/**
	 * Creates a new statement.
	 * 
	 * @param statement
	 *            The statement to create.
	 * @return The new statement.
	 * @throws SQLException
	 *             When an SQL exception occurs.
	 */
	public PreparedStatement createStatement(String statement) throws SQLException {
		return con.prepareStatement(statement);
	}

	/**
	 * Executes the statement on the SQL server.
	 * 
	 * @param statement
	 *            The statement to execute.
	 * @return The new statement.
	 * @throws SQLException
	 *             When an SQL exception occurs.
	 */
	public ResultSet executeStatement(PreparedStatement statement) throws SQLException {
		try {
			statement.execute();
		} finally {
			statement.close();
		}
		return statement.getResultSet();
	}
}
