package marty.codingame.puzzles.easy.thereIsNoSpoon1;

import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 19/11/16
 */
public class Player {

    /*
        Don't let the machines win. You are humanity's last hope...
     */
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);

        int w = input.nextInt(); // the number of cells on the X axis
        int h = input.nextInt(); // the number of cells on the Y axis

        char[][] map = new char[h][];
        input.nextLine();
        for (int i = 0; i < h; i++) {
            map[i] = input.nextLine().toCharArray(); // width characters, each either 0 or .
        }

        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                if(map[i][j] == '0') {
                    System.out.println(getAns(i, j, map));
                }
            }
        }
    }

    public static String getAns(int i, int j, char[][] map) {
        String ret = i + " " + j + " ";
        boolean found = false;
        for(int a = j + 1; a < map[0].length; a++) {
            if(map[i][a] == '0'){
                ret += a + " " + i + " ";
                found = true;
                break;
            }
        }
        if(!found) {
            ret += -1 + " " + -1 + " ";
        }
        found = false;
        for(int a = i + 1; a < map.length; a++){
            if(map[a][j] == '0'){
                ret += i + " " + a;
                found = true;
                break;
            }
        }
        if(!found) {
            ret += -1 + " " + -1;
        }
        return ret;
    }
}
