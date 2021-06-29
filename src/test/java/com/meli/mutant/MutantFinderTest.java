package com.meli.mutant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.meli.mutant.exception.MutantFinderException;
import com.meli.mutant.service.imp.MutantFinder;
import com.meli.mutant.util.MatrixUtil;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class MutantFinderTest {

	@InjectMocks
	private MutantFinder mutantFinder;

	@Mock
	MatrixUtil matrixUtil;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenDnaSequenceIsMutant() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };

		mutantFinder.setMatrixUtil(new MatrixUtil());
		assertTrue(mutantFinder.isMutant(dna));
	}

	@Test
	public void whenDnaSequenceIsNoMutant() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };

		mutantFinder.setMatrixUtil(new MatrixUtil());
		assertFalse(mutantFinder.isMutant(dna));
	}

	@Test(expected = MutantFinderException.class)
	public void whenDnaSequenceIsNull() {
		String[] dna = null;

		mutantFinder.setMatrixUtil(new MatrixUtil());
		assertFalse(mutantFinder.isMutant(dna));
	}

	@Test
	public void whenDnaSequenceIsSmallerThanFour() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATTT" };

		mutantFinder.setMatrixUtil(new MatrixUtil());
		assertFalse(mutantFinder.isMutant(dna));
	}

	@Test(expected = MutantFinderException.class)
	public void whenDnaSequenceHasDifferentSizeThanArray() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATG", "AGAAGG", "CCCCTA", "TCACTG" };

		mutantFinder.setMatrixUtil(new MatrixUtil());
		assertFalse(mutantFinder.isMutant(dna));
	}

	@Test(expected = MutantFinderException.class)
	public void whenDnaSequenceHasDifferentLetter() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGC", "XGAAGG", "CCCCTA", "TCACTG" };

		mutantFinder.setMatrixUtil(new MatrixUtil());
		assertFalse(mutantFinder.isMutant(dna));
	}
}
