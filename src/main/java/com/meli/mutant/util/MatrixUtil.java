package com.meli.mutant.util;

import java.util.ArrayList;
import java.util.List;

import com.meli.mutant.model.Node;

public class MatrixUtil {

	// Direction vectors N, S, E, W, NE, SE, NW, SW
	private int rowDirectionVector[] = { -1, 1, 0, 0, -1, 1, -1, 1 };
	private int colDirectionVector[] = { 0, 0, -1, 1, 1, 1, -1, -1 };

	public static String[][] createMatrixFromStringArray(String[] data) {

		String[][] returnMatrix = new String[data.length][data.length];

		for (int i = 0; i < data.length; i++) {
			returnMatrix[i] = data[i].split("");
		}

		return returnMatrix;
	}

	public List<Node> getAdjacentNodes(Node node, String[][] matrix) {

		List<Node> adjacentNodes = new ArrayList<Node>();

		for (int i = 0; i < colDirectionVector.length; i++) {
			int newRow = node.getRow() + rowDirectionVector[i];
			int newCol = node.getCol() + colDirectionVector[i];

			if (!isValid(newRow, newCol, matrix.length))
				continue;

			adjacentNodes.add(new Node(newRow, newCol, matrix[newRow][newCol], i));
		}

		return adjacentNodes;
	}

	public Node getNextNodeInLocation(Node adjacentNode, String[][] matrixDna) {

		int newRow = adjacentNode.getRow() + rowDirectionVector[adjacentNode.getLocation()];
		int newCol = adjacentNode.getCol() + colDirectionVector[adjacentNode.getLocation()];

		if (!isValid(newRow, newCol, matrixDna.length))
			return null;

		return new Node(newRow, newCol, matrixDna[newRow][newCol], adjacentNode.getLocation());
	}

	private boolean isValid(int row, int col, int matrixLength) {

		if (row < 0 || col < 0)
			return false;

		if (row >= matrixLength || col >= matrixLength)
			return false;

		return true;
	}

}
