package marty.codingame.puzzles.easy.asciiArt;

import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 19/11/16
 */
public class Solution {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        final int L = Integer.parseInt(input.nextLine());
        final int H = Integer.parseInt(input.nextLine());
        char [] print = input.nextLine().toUpperCase().toCharArray();
        String[] letters = new String[H];
        for (int i = 0; i < H; i++) {
            letters[i] = input.nextLine();
        }

        for(int i = 0; i < H; i++) {
            for(char c : print){
                try {
                    System.out.print(letters[i].substring((c - 'a') * L, (c - 'a' + 1) * L));

                }catch (Exception e){
                    System.out.print(letters[i].substring(letters[i].length() - L, letters[i].length()));
                }
            }
            System.out.println();
        }

    }
}
