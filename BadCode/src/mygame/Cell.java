package mygame;

import java.util.ArrayList;

public class Cell {
	/*
	empty - .
	hole - H
	gold - G
	robot - R
	player - P
	*/
	String value = "empty";
	// Объект, который находится на клетке (золото/робот/игрок)
	Object obj = null;
	boolean reachable = false;
	int i = -1, j = -1;
	Cell[] nearbyCells = new Cell[8];
	
	public Cell(int i, int j, boolean hole) {
		this.i = i;
		this.j = j;
		if (hole) value = "hole";
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean haveEmptyNearbyCell() {
		ArrayList<Cell> emptyCells;
		emptyCells = getNearbyCellsWithValue(new String[]{"empty"});
		return !emptyCells.isEmpty();
	}
	
	public void setNearbyCell(int index, Cell c) {
		nearbyCells[index] = c;
	}
	public void setNearbyCell(String position, Cell c) {
		if (position.equals("leftUp")) nearbyCells[0] = c;
		else if (position.equals("up")) nearbyCells[1] = c;
		else if (position.equals("rightUp")) nearbyCells[2] = c;
		else if (position.equals("right")) nearbyCells[3] = c;
		else if (position.equals("rightDown")) nearbyCells[4] = c;
		else if (position.equals("down")) nearbyCells[5] = c;
		else if (position.equals("leftDown")) nearbyCells[6] = c;
		else if (position.equals("left")) nearbyCells[7] = c;
	}
	
	public Cell[] getNearbyCells() {
		return nearbyCells;
	}

	public ArrayList<Cell> getNearbyCellsWithValue(String[] values) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int k = 1; k < nearbyCells.length; k = k + 2) {
			for (int t = 0; t < values.length; t++) {
				if (values[t].equals(nearbyCells[k].getValue())) {
					cells.add(nearbyCells[k]);
					break;
				}
			}
		}
		return cells;
	}
	public ArrayList<Cell> getAllNearbyCellsWithValue(String[] values) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int k = 0; k < nearbyCells.length; k++) {
			for (int t = 0; t < values.length; t++) {
				if (values[t].equals(nearbyCells[k].getValue())) {
					cells.add(nearbyCells[k]);
					break;
				}
			}
		}
		return cells;
	}
	public Cell getNearbyCell(int index) {
		return nearbyCells[index];
	}
	public Cell getNearbyCell(String position) {
		if (position.equals("leftUp")) return nearbyCells[0];
		else if (position.equals("up")) return  nearbyCells[1];
		else if (position.equals("rightUp")) return nearbyCells[2];
		else if (position.equals("right")) return nearbyCells[3];
		else if (position.equals("rightDown")) return nearbyCells[4];
		else if (position.equals("down")) return nearbyCells[5];
		else if (position.equals("leftDown")) return nearbyCells[6];
		else if (position.equals("left")) return nearbyCells[7];
		else return null;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public int getI() {
		return i;
	}
	public int getJ() {
		return j;
	}
	
	public boolean isReachable() {
		return reachable;
	}
	public void setReachable() {
		reachable = true;
	}
	public void unsetReachable() {
		reachable = false;
	}
	
	public boolean isEmpty() {
		if (value.equals("empty")) return true;
		else return false;
	}
	public void setEmpty() {
		value = "empty";
		obj = null;
	}
	
	public boolean isHole() {
		if (value.equals("hole")) return true;
		else return false;
	}
	public void setHole() {
		value = "hole";
	}
	
	public boolean isGold() {
		if (value.equals("gold")) return true;
		else return false;
	}	
	public void setGold() {
		value = "gold";
		//obj = g;
	}
	
	public boolean isRobot() {
		if (value.equals("robot")) return true;
		else return false;
	}
	public void setRobot(Robot r) {
		value = "robot";
		obj = r;
	}	

	public boolean isPlayer() {
		if (value.equals("player")) return true;
		else return false;
	}
	public void setPlayer(Player p) {
		value = "player";
		obj = p;
	}
	
	public void print() {
		/*if (reachable) System.out.print("+");
		else*/ if (value.equals("empty")) System.out.print(".");
		else if (value.equals("hole")) System.out.print("H");
		else if (value.equals("gold")) System.out.print("G");
		else if (value.equals("robot")) System.out.print("R");
		else if (value.equals("player")) System.out.print("P");
	}

}
