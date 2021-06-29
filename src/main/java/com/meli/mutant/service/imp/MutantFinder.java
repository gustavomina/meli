package com.meli.mutant.service.imp;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.meli.mutant.exception.MutantFinderException;
import com.meli.mutant.model.DNAString;
import com.meli.mutant.model.Node;
import com.meli.mutant.service.IMutantFinder;
import com.meli.mutant.util.MatrixUtil;

public class MutantFinder implements IMutantFinder {

	private MatrixUtil matrixUtil;

	public boolean isMutant(String[] dna) {
		// Validates the dna string
		if (!validateDnaString(dna))
			return false;
		// Generates the matrix from the String array
		String[][] matrixDna = MatrixUtil.createMatrixFromStringArray(dna);
		// get DNA sequences
		if (getDnaSequences(matrixDna) > 1)
			return true;

		return false;
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
	private int getDnaSequences(String[][] matrixDna) {

		int totalCount = 0;
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
						int stringlength = validateStringLength(adjacentNode, matrixDna);
						if (stringlength >= 4)
							totalCount++;
					}

				}
			}
		}
		return totalCount;
	}

	/**
	 * Get the adjacent node in the same location than adjacentNode
	 * 
	 * @param adjacentNode
	 * @param matrixDna
	 * @return count of consecutive adjacent nodes that have the same value than
	 *         adjacentNode
	 */
	private int validateStringLength(Node adjacentNode, String[][] matrixDna) {
		int nodeCount = 2;
		Node newNode = adjacentNode;

		while (true) {
			Node node = matrixUtil.getNextNodeInLocation(newNode, matrixDna);
			if (node == null)
				break;

			if (!node.getValue().equals(newNode.getValue()))
				break;
			else {
				nodeCount++;
				newNode = node;
			}
		}
		return nodeCount;
	}

	public void setMatrixUtil(MatrixUtil matrixUtil) {
		this.matrixUtil = matrixUtil;
	}

}
