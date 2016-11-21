package marty.codingame.puzzles.medium.shadowsOfTheKnight1;

import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 19/11/16
 */
public class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X = in.nextInt();
        int Y = in.nextInt();

        int minI = 0, maxI = H - 1, minJ = 0, maxJ = W - 1;

        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)
            for(char c : bombDir.toCharArray()){
                switch (c){
                    case 'L':
                        maxJ = X - 1;
                        break;
                    case 'R':
                        minJ = X + 1;
                        break;
                    case 'U':
                        maxI = Y - 1;
                        break;
                    case 'D':
                        minI = Y + 1;
                        break;
                }
            }
            Y = (minI + maxI) / 2;
            X = (minJ + maxJ) / 2;
            System.out.println(X + " " + Y);
        }
    }
}
