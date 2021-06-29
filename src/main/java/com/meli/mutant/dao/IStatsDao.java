package com.meli.mutant.dao;

import java.sql.SQLException;

import com.meli.mutant.model.Stats;

public interface IStatsDao {

	public void insertStats(String[] dnaSequence, int totalCount) throws SQLException;

	public Stats getStats() throws SQLException;

}
