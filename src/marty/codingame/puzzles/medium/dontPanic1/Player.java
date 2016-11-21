package marty.codingame.puzzles.medium.dontPanic1;

import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 19/11/16
 */
public class Player {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int nbFloors = input.nextInt(); // number of floors
        int width = input.nextInt(); // width of the area
        int nbRounds = input.nextInt(); // maximum number of rounds
        int exitFloor = input.nextInt(); // floor on which the exit is found
        int exitPos = input.nextInt(); // position of the exit on its floor
        int nbTotalClones = input.nextInt(); // number of generated clones
        int nbAdditionalElevators = input.nextInt(); // ignore (always zero)
        int nbElevators = input.nextInt(); // number of elevators
        for (int i = 0; i < nbElevators; i++) {
            int elevatorFloor = input.nextInt(); // floor on which this elevator is found
            int elevatorPos = input.nextInt(); // position of the elevator on its floor
        }

        // game loop
        while (true) {
            int cloneFloor = input.nextInt(); // floor of the leading clone
            int clonePos = input.nextInt(); // position of the leading clone on its floor
            String direction = input.next(); // direction of the leading clone: LEFT or RIGHT

            System.out.println("WAIT"); // action: WAIT or BLOCK
        }
    }
}
