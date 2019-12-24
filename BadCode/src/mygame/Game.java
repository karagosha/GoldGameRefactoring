package mygame;



import java.util.Scanner;

public class Game {
	int n, holeNum, goldNum, robotNum;
	
	public void configureGame() {
		Scanner in = new Scanner(System.in);

		System.out.println("Ñreating room...");
        System.out.println("Use the default settings?\n'y' - yes, 'n' - no, 'q' - quit");
        String def = in.nextLine();
        if (def.equals("y")) {
            init(17, 60, 10, 10);
            return;
        }
        else if (def.equals("n")) {

            System.out.print("Please enter the room settings ( 5 < N < 40)\nN - ðàçìåð ïîëÿ = ");
            int N = in.nextInt();
            System.out.print("Hole = ");
            int HoleNum = in.nextInt();
            System.out.print("Gold = ");
            int GoldNum = in.nextInt();
            System.out.print("Robot = ");
            int RobotNum = in.nextInt();

            init(N, HoleNum, GoldNum, RobotNum);
        }
        else if(def.equals("q")) {
            System.exit(0);
        }
        else {
            System.out.print("Error: the command does not exist");
            in.close();
            System.exit(0);
        }
		return;
	}

	public void init(int n, int holeNum, int goldNum, int robotNum) {
		
		if (n < 6 || n > 39) {
			System.out.println("ERROR: Can't generate room!");
            System.exit(0);
		}		
		
		this.n = n;
		this.holeNum = holeNum;
		this.goldNum = goldNum;
		this.robotNum = robotNum;
		int centerRoom = n/2 + n%2;

		Generator.setCells(n);
		Generator.genPlayer(centerRoom,goldNum);
		Generator.genHoles(n, holeNum);
		Generator.genGold(goldNum,n);
		Generator.genRobots(n,goldNum,robotNum);
		
		return;
	}


	public void startNewGame() {
		configureGame();

		Player player = Generator.getPlayer();
		Robot[] robots = Generator.getRobots();
		Cell[][] gameMap = Generator.getGameMap();

		DrawerOutput.printWithStat(0,gameMap, player, goldNum, n);

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
							DrawerOutput.printWithStat(2, gameMap,player,goldNum,n);
							while (true) {
								buff = in.nextLine();
								if (buff.equals("y")) startNewGame();
								else if (buff.equals("n")) System.exit(0);
							}
						}
					}
					DrawerOutput.printWithStat(0, gameMap,player,goldNum,n);
					break;
				case 2:
					DrawerOutput.printWithStat(1, gameMap,player,goldNum,n);
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
