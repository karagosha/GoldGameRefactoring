package mygame;

public class DrawerOutput {


    public static void printWithStat(int gameStatus, Cell[][] gameMap, Player player, int goldNum, int n) {
        for (int i = 1; i < n-1; i++) {
            for (int j = 1; j < n-1; j++) {
                print(gameMap[i][j]);
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

    private static void print(Cell point) {
        if (point.value.equals("empty")) System.out.print(".");
        else
            if (point.value.equals("hole")) System.out.print("H");
        else
            if (point.value.equals("gold")) System.out.print("G");
        else
            if (point.value.equals("robot")) System.out.print("R");
        else
            if (point.value.equals("player")) System.out.print("P");
    }



}
