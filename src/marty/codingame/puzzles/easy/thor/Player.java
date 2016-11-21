package marty.codingame.puzzles.easy.thor;

import java.util.*;

/**
 * @author marty
 * @version 1.0
 * @since 17/11/16
 */
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lightX = in.nextInt(); // the X position of the light of power
        int lightY = in.nextInt(); // the Y position of the light of power
        int initialTX = in.nextInt(); // Thor's starting X position
        int initialTY = in.nextInt(); // Thor's starting Y position

        // game loop
        while (true) {
            int remainingTurns = in.nextInt(); // The remaining amount of turns Thor can move. Do not remove this line.

            String print = "";
            if(lightY < initialTY){
                print += "N";
                initialTY--;
            }else if(lightY > initialTY){
                print += "S";
                initialTY++;
            }
            if(lightX < initialTX){
                print += "W";
                initialTX--;
            }else if(lightX > initialTX){
                print += "E";
                initialTX++;
            }

            // A single line providing the move to be made: N NE E SE S SW W or NW
            System.out.println(print);
        }
    }
}
