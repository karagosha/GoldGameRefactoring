package mygame;

import java.util.Random;
import java.util.Scanner;

public class Room {
	int n, holeNum, goldNum, robotNum;
	Cell[][] matrix;
	Player player;
	Robot[] robots;
	
	public boolean tryInit() {
		Scanner in = new Scanner(System.in);
		System.out.println("Ñreating room...");
		while (true) {
			System.out.println("Use the default settings?\n'y' - yes, 'n' - no, 'q' - quit");
			String def = in.nextLine();
			if (def.equals("y")) {
				init(17, 60, 10, 10);
				return true;
			}
			else if (def.equals("n")) break;
		}

		System.out.print("Please enter the room settings ( 5 < N < 40)\nN = ");
		int N = in.nextInt();
		System.out.print("H = ");
		int H = in.nextInt();
		System.out.print("G = ");
		int G = in.nextInt();
		System.out.print("R = ");
		int R = in.nextInt();
		
		return init(N, H, G, R);
	}
	public boolean init(int n, int holeNum, int goldNum, int robotNum) {
		
		if (n < 6 || n > 39) {
			System.out.println("ERROR: Can't generate room!");
			return false;
		}		
		
		this.n = n + 2;
		this.holeNum = holeNum;
		this.goldNum = goldNum;
		this.robotNum = robotNum;
		int middle = n/2 + n%2;
		
		
		setCells();

		if (!genPlayer(matrix[middle][middle])) return false;		
		if (!genHoles()) return false;
		if (!genGold()) return false;
		if (!genRobots()) return false;
		
		return true;
	}
	
	public Cell[][] getMatrix() {
		return matrix;
	}
	public Player getPlayer() {
		return player;
	}
	public Robot[] getRobots() {
		return robots;
	}
	
	public void setCells() {
		matrix = new Cell[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 || j == 0 || i == n-1 || j == n-1) {
					matrix[i][j] = new Cell(i, j, true);
				} else {
					matrix[i][j] = new Cell(i, j, false);
				}
			}
		}
		
		for (int i = 1; i < n-1; i++) {
			for (int j = 1; j < n-1; j++) {
				matrix[i][j].setNearbyCell(0, matrix[i-1][j-1]);
				matrix[i][j].setNearbyCell(1, matrix[i-1][j]);
				matrix[i][j].setNearbyCell(2, matrix[i-1][j+1]);
				matrix[i][j].setNearbyCell(3, matrix[i][j+1]);
				matrix[i][j].setNearbyCell(4, matrix[i+1][j+1]);
				matrix[i][j].setNearbyCell(5, matrix[i+1][j]);
				matrix[i][j].setNearbyCell(6, matrix[i+1][j-1]);
				matrix[i][j].setNearbyCell(7, matrix[i][j-1]);
			}
		}
	}
	
	public boolean genPlayer(Cell c) {		
		player = new Player(c, goldNum, 3);
		c.setPlayer(player);
		return true;
	}
	
	public boolean genHoles() {
		if ((n-2)*(n-2) < holeNum) {
			System.out.println("ERROR: Can't generate holes!");
			return false;
		}
		
		Random rand = new Random();
		for (int k = 0; k < holeNum; k++) {
			int i = rand.nextInt(n-2) + 1, j = rand.nextInt(n-2) + 1;
			if (!matrix[i][j].isEmpty()) {
				k--;
				continue;
			}
			matrix[i][j].setHole();
		}
		
		markReachableCells(player.getCell());
		return true;
	}
	
	public int getReachableCellsCnt() {		
		int count = 0;
		for (int i = 1; i < n-1; i++) {
			for (int j = 1; j < n-1; j++) {
				if (matrix[i][j].isReachable()) count++;
			}
		}
		return count;
	}
	public void markReachableCells(Cell c) {
		int i = c.getI(), j = c.getJ();
		if (matrix[i-1][j].isEmpty() && !matrix[i-1][j].isReachable()) {
			matrix[i-1][j].setReachable();
			markReachableCells(matrix[i-1][j]);
		}
		if (matrix[i+1][j].isEmpty() && !matrix[i+1][j].isReachable()) {
			matrix[i+1][j].setReachable();
			markReachableCells(matrix[i+1][j]);
		}
		if (matrix[i][j-1].isEmpty() && !matrix[i][j-1].isReachable()) {
			matrix[i][j-1].setReachable();
			markReachableCells(matrix[i][j-1]);
		}
		if (matrix[i][j+1].isEmpty() && !matrix[i][j+1].isReachable()) {
			matrix[i][j+1].setReachable();
			markReachableCells(matrix[i][j+1]);
		}
		
	}

	public boolean genGold() {
		if (goldNum < 1 || getReachableCellsCnt() < goldNum) {
			System.out.println("ERROR: Can't generate gold!");
			return false;
		}
		
		Random rand = new Random();
		for (int k = 0; k < goldNum; k++) {
			int i = rand.nextInt(n-2) + 1, j = rand.nextInt(n-2) + 1;
			if (!matrix[i][j].isReachable() || !matrix[i][j].isEmpty()) {
				k--;
				continue;
			}
			matrix[i][j].setGold();
		}	
		return true;
	}
	
	public boolean genRobots() {
		if (robotNum != 0 && getReachableCellsCnt() - goldNum - 24 < robotNum) {
			System.out.println("ERROR: Can't generate robots!");
			return false;
		}
		
		robots = new Robot[robotNum];
		Random rand = new Random();
		for (int k = 0; k < robotNum; k++) {
			int i = rand.nextInt(n-2) + 1, j = rand.nextInt(n-2) + 1;
			if (!matrix[i][j].isReachable() || !matrix[i][j].isEmpty()) {
				k--;
				continue;
			}
			
			robots[k] = new Robot(matrix[i][j]);
			matrix[i][j].setRobot(robots[k]);
		}	
		return true;
	}

	
	public void print() {
		for (int i = 1; i < n-1; i++) {
			for (int j = 1; j < n-1; j++) {
				matrix[i][j].print();
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	public void printWithStat(int gameStatus) {
		for (int i = 1; i < n-1; i++) {
			for (int j = 1; j < n-1; j++) {
				matrix[i][j].print();
				System.out.print(" ");
			}
			if (i == 1) System.out.print("  gold: " + player.getCollectedGold() + "/" + goldNum);
			else if (i == 2) System.out.print("  moves: " + player.getMovesNum());
			else if (i == 3) System.out.print("  weapon charges: " + player.getWeaponCharges() + "/" + 3);
			else if (i == 4 && gameStatus == 1) System.out.print("  YOU WIN!");
			else if (i == 4 && gameStatus == 2) System.out.print("  YOU LOSE!");
			else if (i == 5 && (gameStatus == 1 || gameStatus == 2)) System.out.print("  Start new game?"); 
			else if (i == 6 && (gameStatus == 1 || gameStatus == 2)) System.out.print("  'y' - yes, 'n' - no");
			
			System.out.println();
		}	

	}
	public void startNewGame() {
		while (!tryInit()) {};
			
		printWithStat(0);

		Scanner in = new Scanner(System.in);		
		while (true) {

			String buff = in.nextLine();
			if (buff.equals("q")) {
				System.exit(0);
			}	
			else {
				switch (player.move(buff)) {
				case 0:
					break;
				case 1:
					for (int i = 0; i < robots.length; i++) {
						if (robots[i].moveRandom() == 2) {
							printWithStat(2);
							while (true) {
								buff = in.nextLine();
								if (buff.equals("y")) startNewGame();
								else if (buff.equals("n")) System.exit(0);
							}
						}
					}
					printWithStat(0);
					break;
				case 2:
					printWithStat(1);
					while (true) {
						buff = in.nextLine();
						if (buff.equals("y")) startNewGame();
						else if (buff.equals("n")) System.exit(0);
					}
				}
			}	

		}
	}

}
