package com.meli.mutant.infraestructure.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.meli.mutant.infraestructure.IDatabaseConnection;
import com.meli.mutant.util.io.IMutantFinderProperties;

public class AWSProxyDatabaseConnection implements IDatabaseConnection {

	// Injection
	IMutantFinderProperties mutantFinderProperties;

	private static final int MAX_RETRIES = 5;

	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Create a connection.
		System.out.println("Conectando Componente..");

		Connection connection = DriverManager.getConnection(mutantFinderProperties.getJdbc(),
				mutantFinderProperties.getDbUser(), mutantFinderProperties.getDbPwd());
		System.out.println("Conectado..");
		// Configure the connection.
		setInitialSessionState(connection);
		testConnection(connection);

		return connection;

	}

	private void testConnection(Connection conn) throws SQLException {
		try (ResultSet rs = ExecuteQuery(conn, "Select * from test")) {
			while (rs.next()) {
				System.out.println(rs.getString("id"));
			}
		}
	}

	private void setInitialSessionState(Connection conn) throws SQLException {
		// Your code here for the initial connection setup.
		try (Statement stmt1 = conn.createStatement()) {
			stmt1.executeUpdate("SET time_zone = \"+00:00\"");
		}
	}

	// A better executing query method when autocommit is set as the default value -
	// True.
	public ResultSet ExecuteQuery(Connection conn, String query) throws SQLException {
		// Create a boolean flag.
		boolean isSuccess = false;
		// Record the times of re-try.
		int retries = 0;

		ResultSet rs = null;
		while (!isSuccess) {
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				isSuccess = true;

			} catch (SQLException e) {

				// If the attempt to connect has failed MAX_RETRIES times,
				// throw the exception to inform users of the failed connection.
				if (retries > MAX_RETRIES) {
					throw e;
				}

				// Failover has occurred and the driver has failed over to another instance
				// successfully.
				if (e.getSQLState().equalsIgnoreCase("08S02")) {
					// Re-config the connection.
					setInitialSessionState(conn);
					// Re-execute that query again.
					retries++;

				} else {
					// If some other exception occurs, throw the exception.
					throw e;
				}
			}
		}

		// return the ResultSet successfully.
		return rs;
	}

	public void insertData(PreparedStatement insert) throws SQLException {
		insert.execute();
	}

	public void setMutantFinderProperties(IMutantFinderProperties mutantFinderProperties) {
		this.mutantFinderProperties = mutantFinderProperties;
	}

}
