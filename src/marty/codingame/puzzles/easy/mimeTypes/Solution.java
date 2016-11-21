package marty.codingame.puzzles.easy.mimeTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 19/11/16
 */
public class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // Number of elements which make up the association table.
        int Q = in.nextInt(); // Number Q of file names to be analyzed.
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String EXT = in.next().toLowerCase(); // file extension
            String MT = in.next(); // MIME type.
            map.put(EXT, MT);
        }
        in.nextLine();
        for (int i = 0; i < Q; i++) {
            String FNAME = in.nextLine().toLowerCase(); // One file name per line.
            try {
                if (map.containsKey(FNAME.substring(FNAME.lastIndexOf("."))) &&
                        FNAME.split(".")[FNAME.split(".").length - 1].equals(map.get(FNAME.substring(FNAME.lastIndexOf("."))))) {
                    String key = map.get(FNAME.substring(FNAME.lastIndexOf(".")));

                } else {
                    System.out.println("UNKNOWN");
                }
            }catch (Exception e){
                System.out.println("UNKNOWN");
            }
        }
    }
}
