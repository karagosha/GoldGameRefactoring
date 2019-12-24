package mygame;

import java.util.ArrayList;

public class Player {
	int collectedGold = 0;
	int totalGold;
	int movesNum = 0;
	int weaponCharges;
	
	Cell myCell;
	
	public Player(Cell c, int totalGold, int weaponCharges) {
		myCell = c;
		this.totalGold = totalGold;
		this.weaponCharges = weaponCharges;
	}
	public int getCollectedGold() {
		return collectedGold;
	}
	public int getMovesNum() {
		return movesNum;
	}
	public int getWeaponCharges() {
		return weaponCharges;
	}
	public Cell getCell() {
		return myCell;
	}
	/*
	???????????? ????????:
	0 - ??? ?? ??????
	1 - ??? ??????
	2 - ??? ?????? ? ????????? ??????? ?????????? ????
	*/
	public int moveTo(Cell c) {
		if (!c.isHole() && !c.isRobot()) {
			int result = 1;
			movesNum++;
			myCell.setEmpty();
			
			if (c.isGold()) {
				collectedGold++;
				if (collectedGold == totalGold) result = 2;
			}
			c.setPlayer(this);
			myCell = c;
			
			return result;
		}
		else return 0;
	}
	public int move(String position) {
		if (position.equals("up") || position.equals("w"))
			return moveTo(myCell.getNearbyCell("up"));
		else if (position.equals("down") || position.equals("s"))
			return moveTo(myCell.getNearbyCell("down"));
		else if (position.equals("left") || position.equals("a"))
			return moveTo(myCell.getNearbyCell("left"));
		else if (position.equals("right") || position.equals("d"))
			return moveTo(myCell.getNearbyCell("right"));
		else if (position.equals("x"))
			return useWeapon();
		else return 0;
		
	}
	public int useWeapon() {
		if (weaponCharges < 1) {
			System.out.println("NO CHARGES!");
			return 0;
		}
		weaponCharges--;
		ArrayList<Cell> robots = myCell.getAllNearbyCellsWithValue(new String[]{"robot"});
		for (int i = 0; i < robots.size(); i++) {
			Robot r = (Robot) robots.get(i).getObj();
			r.paralyze(5);
		}
		return 1;
	}
	
	
}
