package marty.codingame.puzzles.hard.skynet2;

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
        Queue<Integer> q1 = new LinkedList<>();

        Set<Integer> v = new HashSet<>();
        q.add(agent);
        v.add(agent);

        int[] gateNeighbors = new int[adj.length];
        for(int i : gates){
            for(int j = 0; j < adj.length; j++){
                if(adj[i][j]){
                    gateNeighbors[j]++;
                }
            }
        }

        int[] hits = new int[adj.length];

        boolean hitGate = false;

        int best = 0;
        while(!q.isEmpty()){

            int current = q.poll();
            if(gateNeighbors[current] > 0){
                hits[current]++;
                best = Math.max(best, hits[current]);
                hitGate = true;
            }


            for(int i = 0; i < adj.length; i++) {
                if(adj[i][current] && !v.contains(i)){
                    q1.add(i);
                }
            }

            if(q.isEmpty() && !hitGate){
                q.addAll(q1);
                v.addAll(q1);
                q1 = new LinkedList<>();
            }
        }

        List<Integer> mostHits = new LinkedList<>();

        for(int i = 0; i < hits.length; i++) {
            if(hits[i] >= best && gateNeighbors[i] > 0) {
                mostHits.add(i);
            }
        }

        best = 0;
        for(int i : mostHits) {
            best = Math.max(gateNeighbors[i], best);
        }
        for(int i : mostHits){
            if(gateNeighbors[i] == best){
                for(int j = 0; j < adj.length; j++) {
                    if(adj[i][j] && gates.contains(j)){
                        System.out.println(i + " " + j);
                        adj[i][j] = false;
                        adj[j][i] = false;
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
