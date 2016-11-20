package marty.codingame.puzzles.easy.skynet1;

import java.util.*;

/**
 * @author marty
 * @version 1.0
 * @since 19/11/16
 */
public class Player {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt(); // the total number of nodes input the level, including the gateways
        int L = input.nextInt(); // the number of links
        int E = input.nextInt(); // the number of exit gateways

        boolean[][] adj = new boolean[N][N];
        for (int i = 0; i < L; i++) {
            int N1 = input.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = input.nextInt();
            adj[N1][N2] = true;
            adj[N2][N1] = true;
        }

        List<Integer> gates = new LinkedList<>();
        for (int i = 0; i < E; i++) {
            int EI = input.nextInt(); // the index of a gateway node
            gates.add(EI);
        }

        // game loop
        while (true) {
            int agent = input.nextInt(); // The index of the node on which the Skynet agent is positioned this turn

            walkdown(agent, gates, adj);
        }
    }

    public static boolean walkdown(int agent, List<Integer> gates, boolean[][]adj) {
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> v = new HashSet<>();

        q.addAll(gates);

        while(!q.isEmpty()){

            int current = q.poll();

            if(adj[current][agent]){
                System.out.println(current + " " + agent);
                adj[current][agent] = false;
                adj[agent][current] = false;
                return true;
            }
            for(int i = 0; i < adj.length; i++) {
                if(adj[i][current] && !v.contains(i)){
                    q.add(i);
                    v.add(i);
                }
            }
        }
        return false;
    }
}
