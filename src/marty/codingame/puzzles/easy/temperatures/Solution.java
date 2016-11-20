package marty.codingame.puzzles.easy.temperatures;

import java.util.*;

/**
 * @author marty
 * @version 1.0
 * @since 17/11/16
 */
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of temperatures to analyse
        in.nextLine();
        if(n == 0){
            System.out.println(0);
        }else{
            String[] temps = in.nextLine().split(" "); // the n temperatures expressed as integers ranging from -273 to 5526


            int best = Integer.MAX_VALUE;

            for(int i = 0; i < temps.length; i++) {
                int current = Integer.parseInt(temps[i]);
                if(Math.abs(current) < Math.abs(best)){
                    best = current;
                }else if(Math.abs(current) == Math.abs(best)){
                    best = Math.max(current, best);
                }
            }
            System.out.println(best);
        }
    }
}
