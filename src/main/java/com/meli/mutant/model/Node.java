package com.meli.mutant.model;

public class Node {

	private int row;
	private int col;
	private int location;
	private String value;

	public Node(int row, int col, String value, int location) {
		this.row = row;
		this.col = col;
		this.value = value;
		this.location = location;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public String getValue() {
		return value;
	}

	public int getLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "([" + getRow() + "," + getCol() + "]) = " + getValue();
	}

}
