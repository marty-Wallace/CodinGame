package marty.codingame.puzzles.easy.marsLander1;

import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 17/11/16
 */
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        System.out.println(Integer.toUnsignedString(4, 3));
        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        Point[] surface = new Point[surfaceN];
        for (int i = 0; i < surfaceN; i++) {
            surface[i] = new Point(in.nextInt(), in.nextInt()); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
        }

        // game loop
        while (true) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int hSpeed = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            int vSpeed = in.nextInt(); // the vertical speed (in m/s), can be negative.
            int fuel = in.nextInt(); // the quantity of remaining fuel in liters.
            int rotate = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            int power = in.nextInt(); // the thrust power (0 to 4).

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            System.out.println(Integer.toUnsignedString(4, 3));


            // 2 integers: rotate power. rotate is the desired rotation angle (should be 0 for level 1), power is the desired thrust power (0 to 4).
            System.out.println(vSpeed <= -40 ? "0 4" : "0 0");
        }

    }


    static class Point {
        int x, y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

    }
}
