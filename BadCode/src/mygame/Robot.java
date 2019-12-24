package mygame;

import java.util.ArrayList;
import java.util.Random;

public class Robot {
	int paralyzadMovesNum = 0;
	Cell myCell;
	
	public Robot(Cell c) {
		myCell = c;
	}
	public int moveTo(Cell c) {
		if (!c.isHole() && !c.isRobot() && !c.isGold()) {
			int result = 1;
			myCell.setEmpty();
			if (c.isPlayer()) {
				result = 2;
			}
			c.setRobot(this);
			myCell = c;
			
			return result;
		}
		else return 0;
	}

	public void paralyze(int paralyzadMovesNum) {
		this.paralyzadMovesNum = paralyzadMovesNum;
	}
	public int moveRandom() {
		if (paralyzadMovesNum > 0) {
			paralyzadMovesNum--;
			return 1;
		}
		ArrayList<Cell> emptyNearbyCells = myCell.getNearbyCellsWithValue(new String[]{"empty", "player"});
		if (emptyNearbyCells.isEmpty()) return 0;
		else {
			Random rand = new Random();
			int r = rand.nextInt(emptyNearbyCells.size());
			return moveTo(emptyNearbyCells.get(r));
		}
	}
}
