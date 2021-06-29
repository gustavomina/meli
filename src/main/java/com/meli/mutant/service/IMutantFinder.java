package com.meli.mutant.service;

import com.meli.mutant.model.Stats;

public interface IMutantFinder {

	public boolean isMutant(String[] dna);

	public Stats getStats();
}
