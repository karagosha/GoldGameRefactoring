package mygame;

import java.util.Random;

public class Generator {
    static Player player;
    static Cell[][] gameMap;
    static Robot[] robots;


    public static Cell[][] getGameMap() {
        return gameMap;
    }
    public static Player getPlayer() {
        return player;
    }
    public static Robot[] getRobots() {
        return robots;
    }

    public static void setCells(int n) {
        gameMap = new Cell[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == n-1 || j == n-1) {
                    gameMap[i][j] = new Cell(i, j, true);
                } else {
                    gameMap[i][j] = new Cell(i, j, false);
                }
            }
        }
        for (int i = 1; i < n-1; i++) {
            for (int j = 1; j < n-1; j++) {
                gameMap[i][j].setNearbyCell(0, gameMap[i-1][j-1]);
                gameMap[i][j].setNearbyCell(1, gameMap[i-1][j]);
                gameMap[i][j].setNearbyCell(2, gameMap[i-1][j+1]);
                gameMap[i][j].setNearbyCell(3, gameMap[i][j+1]);
                gameMap[i][j].setNearbyCell(4, gameMap[i+1][j+1]);
                gameMap[i][j].setNearbyCell(5, gameMap[i+1][j]);
                gameMap[i][j].setNearbyCell(6, gameMap[i+1][j-1]);
                gameMap[i][j].setNearbyCell(7, gameMap[i][j-1]);
            }
        }
    }

    public static void genPlayer(int centerRoom, int goldNum) {
        player = new Player(gameMap[centerRoom][centerRoom], goldNum, 3);
        gameMap[centerRoom][centerRoom].setPlayer(player);
    }

    public static void genHoles(int n, int holeNum) {
        if ((n-2)*(n-2) < holeNum) {
            System.out.println("ERROR: Can't generate holes!");
            System.exit(0);
        }

        Random rand = new Random();
        for (int k = 0; k < holeNum; k++) {
            int i = rand.nextInt(n-2) + 1, j = rand.nextInt(n-2) + 1;
            if (!gameMap[i][j].isEmpty()) {
                k--;
                continue;
            }
            gameMap[i][j].setHole();
        }

        markReachableCells(player.getCell());
    }

    public static void markReachableCells(Cell c) {
        int i = c.getI(), j = c.getJ();
        if (gameMap[i-1][j].isEmpty() && !gameMap[i-1][j].isReachable()) {
            gameMap[i-1][j].setReachable();
            markReachableCells(gameMap[i-1][j]);
        }
        if (gameMap[i+1][j].isEmpty() && !gameMap[i+1][j].isReachable()) {
            gameMap[i+1][j].setReachable();
            markReachableCells(gameMap[i+1][j]);
        }
        if (gameMap[i][j-1].isEmpty() && !gameMap[i][j-1].isReachable()) {
            gameMap[i][j-1].setReachable();
            markReachableCells(gameMap[i][j-1]);
        }
        if (gameMap[i][j+1].isEmpty() && !gameMap[i][j+1].isReachable()) {
            gameMap[i][j+1].setReachable();
            markReachableCells(gameMap[i][j+1]);
        }

    }

    public static void genGold(int goldNum, int n) {
        if (goldNum < 1 || getReachableCellsCnt(n) < goldNum) {
            System.out.println("ERROR: Can't generate gold!");
            System.exit(0);
        }

        Random rand = new Random();
        for (int k = 0; k < goldNum; k++) {
            int i = rand.nextInt(n-2) + 1, j = rand.nextInt(n-2) + 1;
            if (!gameMap[i][j].isReachable() || !gameMap[i][j].isEmpty()) {
                k--;
                continue;
            }
            gameMap[i][j].setGold();
        }
    }

    public static int getReachableCellsCnt(int n) {
        int count = 0;
        for (int i = 1; i < n-1; i++) {
            for (int j = 1; j < n-1; j++) {
                if (gameMap[i][j].isReachable()) count++;
            }
        }
        return count;
    }

    public static boolean genRobots(int n, int goldNum, int robotNum) {
        if (robotNum != 0 && getReachableCellsCnt(n) - goldNum - 24 < robotNum) {
            System.out.println("ERROR: Can't generate robots!");
            System.exit(0);
            return false;
        }

        robots = new Robot[robotNum];
        Random rand = new Random();
        for (int k = 0; k < robotNum; k++) {
            int i = rand.nextInt(n-2) + 1, j = rand.nextInt(n-2) + 1;
            if (!gameMap[i][j].isReachable() || !gameMap[i][j].isEmpty()) {
                k--;
                continue;
            }

            robots[k] = new Robot(gameMap[i][j]);
            gameMap[i][j].setRobot(robots[k]);
        }
        return true;
    }
}
