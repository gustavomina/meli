package com.meli.mutant.service.imp;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.meli.mutant.dao.IStatsDao;
import com.meli.mutant.exception.MutantFinderException;
import com.meli.mutant.model.DNAString;
import com.meli.mutant.model.Node;
import com.meli.mutant.model.Stats;
import com.meli.mutant.service.IMutantFinder;
import com.meli.mutant.util.MatrixUtil;

public class MutantFinder implements IMutantFinder {

	// private IDatabaseConnection databaseConnection;

	private IStatsDao statsDao;

	private MatrixUtil matrixUtil;

	public boolean isMutant(String[] dna) {
		// Validates the dna string
		if (!validateDnaString(dna))
			return false;
		// get DNA sequences
		return getDnaSequences(dna);
	}

	@Override
	public Stats getStats() {
		try {
			return statsDao.getStats();
		} catch (SQLException e) {
			e.printStackTrace();
			return new Stats();
		}
	}

	/**
	 * Validate the DNA input
	 * 
	 * @param dna
	 * @return
	 * @throws IllegalArgumentException
	 */
	private boolean validateDnaString(String[] dna) {
		if (dna == null)
			throw new MutantFinderException("DNA sequence can not be null");

		int dnaStringLength = dna.length;
		// Si el tamaño del arreglo es menor que 4
		if (dna.length < 4)
			return false;

		String result = Arrays.stream(DNAString.values()).map(i -> String.valueOf(i)).collect(Collectors.joining());

		// Validate each DNA string size
		for (String string : dna) {
			if (string.length() < dnaStringLength)
				throw new MutantFinderException("DNA sequence doesn't have correct size");

			if (!string.matches("^[" + result + "]+$"))
				throw new MutantFinderException("DNA sequence doesn't have correct strings");
		}

		return true;
	}

	/**
	 * this an implementation of BFS algorithm to visit all elements in matrix
	 * 
	 * @param matrixDna
	 * @return
	 */
	private boolean getDnaSequences(String[] dna) {

		List<List<Node>> currentDnaString = new ArrayList<>();

		// Generates the matrix from the String array
		String[][] matrixDna = MatrixUtil.createMatrixFromStringArray(dna);

		// List of visited elements
		Set<Node> visitedNode = new LinkedHashSet<Node>();

		// Stack of elements to visit in future
		Deque<Node> stack = new ArrayDeque<Node>();

		// Add the root node to de queue
		Node root = new Node(0, 0, matrixDna[0][0], -1);
		stack.push(root);

		// Iterate until de stack with elements to visit is empty
		while (!stack.isEmpty()) {

			Node currentNode = stack.pop();

			if (!visitedNode.contains(currentNode)) {
				visitedNode.add(currentNode);
			}

			// loops over the adjacents nodes of the visited one
			for (Node adjacentNode : matrixUtil.getAdjacentNodes(currentNode, matrixDna)) {
				// If an adjacent node has not been visited, is added to the stack to be
				// visit later
				if (!visitedNode.contains(adjacentNode)) {
					stack.push(adjacentNode);
					// If the adjacent node has the same value than de current node checks
					// the next adjacent nodes
					if (currentNode.getValue().equals(adjacentNode.getValue())) {
						List<Node> listString = new ArrayList<Node>();
						listString.add(currentNode);
						listString.add(adjacentNode);
						listString = validateStringLength(adjacentNode, matrixDna, listString);
						if (listString.size() >= 4) {
							if (currentDnaString.size() == 0)
								currentDnaString.add(listString);
							else {
								// Verifica si ya los nodos retornados se encuentran contados
								for (List<Node> lista : currentDnaString) {
									if (!lista.containsAll(listString)) {
										currentDnaString.add(listString);
										break;
									}
								}
							}
						}
						if (currentDnaString.size() > 1)
							break;
					}

				}
			}
		}

		try {
			saveDnaSequence(dna, currentDnaString.size());
		} catch (SQLException e) {
			throw new MutantFinderException(e.getMessage());
		}

		if (currentDnaString.size() > 1)
			return true;

		return false;
	}

	private void saveDnaSequence(String[] dna, int totalCount) throws SQLException {
		// Connection c = databaseConnection.getConnection();
		statsDao.insertStats(dna, totalCount);
	}

	/**
	 * Get the adjacent node in the same location than adjacentNode
	 * 
	 * @param adjacentNode
	 * @param matrixDna
	 * @return count of consecutive adjacent nodes that have the same value than
	 *         adjacentNode
	 */
	private List<Node> validateStringLength(Node adjacentNode, String[][] matrixDna, List<Node> string) {
		Node newNode = adjacentNode;

		List<Node> returnList = string;
		while (true) {
			Node node = matrixUtil.getNextNodeInLocation(newNode, matrixDna);
			if (node == null)
				break;

			if (!node.getValue().equals(newNode.getValue()))
				break;
			else {
				returnList.add(node);
				newNode = node;
			}
		}
		return returnList;
	}

	public void setMatrixUtil(MatrixUtil matrixUtil) {
		this.matrixUtil = matrixUtil;
	}

	public void setStatsDao(IStatsDao statsDao) {
		this.statsDao = statsDao;
	}

}
