package marty.codingame.puzzles.easy.chuckNorris;

import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 19/11/16
 */
public class Solution {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        char[] message = input.nextLine().toCharArray();
        String bin = "";
        StringBuilder output = new StringBuilder();
        for(char c : message){
            bin += getBits(c);
        }
        char[] bits = bin.toCharArray();
        int i = 0;
        char current = '2';
        while(i < bits.length){
            if(current == bits[i]){
                output.append("0");
            }else{
                output.append(bits[i] == '1' ? " 0 0" : " 00 0");
                current = bits[i];
            }
            i++;
        }
        System.out.println(output.toString().substring(1));
    }

    public static String getBits(int c){
        String ret = Integer.toBinaryString(c);
        for(int i = ret.length(); i < 7; i++){
            ret = "0" + ret;
        }
        return ret;
    }
}
