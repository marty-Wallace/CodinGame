package marty.codingame.puzzles.easy.shadowsOfTheKnight1;

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
                    case 'U':
                        minI = X + 1;
                        break;

                    case 'D':
                        maxI = X - 1;
                        break;

                    case 'L':
                        maxJ = Y - 1;
                        break;

                    case 'R':
                        minJ = Y + 1;
                        break;
                }
            }
            X = (minI + maxI) / 2;
            Y = (minJ + maxJ) / 2;
            System.out.println(X + " " + Y);
        }
    }
}
