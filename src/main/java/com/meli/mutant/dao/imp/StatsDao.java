package com.meli.mutant.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.meli.mutant.dao.IStatsDao;
import com.meli.mutant.infraestructure.IDatabaseConnection;
import com.meli.mutant.infraestructure.imp.AWSProxyDatabaseConnection;
import com.meli.mutant.model.Stats;

public class StatsDao implements IStatsDao {

	Connection databaseConnection;
	IDatabaseConnection data;

	@Override
	public void insertStats(String[] dnaSequence, int totalCount) throws SQLException {

		String mutant = "N";
		// Join The string sequence
		String joinedString = String.join(",", dnaSequence).trim();
		if (totalCount > 1)
			mutant = "Y";

		try {
			((AWSProxyDatabaseConnection) data).insertData(getStatement(joinedString, mutant));
		} catch (SQLException e) {

		}
	}

	@Override
	public Stats getStats() throws SQLException {
		float mutant = 0;
		float human = 0;
		Stats stats = new Stats();

		String query = "select ismutant, count(*) from stats group by ismutant";
		ResultSet answer = ((AWSProxyDatabaseConnection) data).ExecuteQuery(databaseConnection, query);

		while (answer.next()) {
			if (answer.getString(1).equals("Y"))
				mutant = answer.getFloat(2);
			else
				human = answer.getFloat(2);
		}

		stats.setCount_human_dna(human);
		stats.setCount_mutant_dna(mutant);

		if (human == 0)
			human = 1;

		stats.setRatio(Float.valueOf(mutant / human).floatValue());

		return stats;
	}

	private PreparedStatement getStatement(String dnaSeq, String mutant) throws SQLException {
		PreparedStatement insert = getDatabaseConnection().prepareStatement("insert into stats values(?, ?)");
		insert.setString(1, dnaSeq);
		insert.setString(2, mutant);
		return insert;
	}

	public void setDatabaseConnection(Connection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public Connection getDatabaseConnection() {
		return databaseConnection;
	}

	public IDatabaseConnection getData() {
		return data;
	}

	public void setData(IDatabaseConnection data) {
		this.data = data;
	}

}
