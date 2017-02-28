import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author marty
 * @version 1.0
 * @since 26/11/16
 */
class Player {

    /*
        Game level constants
     */
    private static final int WIDTH = 16001;
    private static final int HEIGHT = 7501;
    private static final int GOAL_LENGTH = 4000;
    private static final int POLE_RADIUS = 300;
    private static final int LEFT_EDGE = 0;
    private static final int RIGHT_EDGE = WIDTH-1;
    private static final int GOAL_CENTER = HEIGHT/2;
    private static final int GOAL_TOP = GOAL_CENTER - (GOAL_LENGTH/2);
    private static final int GOAL_BOTTOM = GOAL_CENTER + (GOAL_LENGTH/2);
    private static final int PLAYER_ONE = 0;

    private static final int NUM_BLUDGERS = 2;
    private static final int MAX_WIZARDS = 2;
    private static final int MAX_THRUST = 150;
    private static final int MAX_THROW = 500;

    private static final String WIZARD = "WIZARD";
    private static final String OPPONENT_WIZARD = "OPPONENT_WIZARD";
    private static final String SNAFFLE = "SNAFFLE";
    private static final String BLUDGER = "BLUDGER";

    private static final String THROW = "THROW";
    private static final String MOVE = "MOVE";

    private static final String FLIPENDO = "FLIPENDO";
    private static final String ACCIO = "ACCIO";
    private static final String PETRIFICUS = "PETRIFICUS";
    private static final String OBLIVIATE = "OBLIVIATE";

    private static final int FLIPENDO_COST = 20;
    private static final int FLIPENDO_DURATION = 3;

    private static final int ACCIO_COST = 20;
    private static final int ACCIO_DURATION = 6;

    private static final int PETRIFICUS_COST = 10;
    private static final int PETRIFICUS_DURATION = 1;

    private static final int OBLIVIATE_COST = 5;
    private static final int OBLIVIATE_DURATION = 3;

    private static final double BLUDGER_WEIGHT = 8;
    private static final double WIZARD_WEIGHT = 1;
    private static final double SNAFFLE_WEIGHT = 0.5;

    private static final double BLUDGER_THRUST = 1000;

    private static final double WIZARD_FRICTION = 0.75;
    private static final double BLUDGER_FRICTION = 0.9;
    private static final double SNAFFLE_FRICTION = 0.75;

    private static int myTeamId;
    private static int myMana;

    private static List<Snaffle> snaffles;
    private static List<Bludger> bludgers;
    private static Wizard[] w;
    private static Wizard[] o_w;



    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        myTeamId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left

        w = new Wizard[MAX_WIZARDS];
        o_w = new Wizard[MAX_WIZARDS];

        w[0] = new OffensiveWizard(0, 0, 0, 0, 0, 0);
        w[1] = new DefensiveWizard(0, 0, 0, 0, 0, 0);
        o_w[0] = new Wizard(0, 0, 0, 0, 0, 0);
        o_w[1] = new DefensiveWizard(0, 0, 0, 0, 0, 0);

        while (true) {
            int w_i = 0, o_w_i = 0;
            int myScore = in.nextInt();
            myMana = in.nextInt();
            int oppScore = in.nextInt();
            int oppMana = in.nextInt();
            int entities = in.nextInt(); // number of entities still in game
            w_i = 0;
            snaffles = new ArrayList<>();
            bludgers = new ArrayList<>();
            while(entities-->0){
                int entityId = in.nextInt(); // entity identifier
                String entityType = in.next();
                int j = in.nextInt(); // position
                int i = in.nextInt(); // position
                int vj = in.nextInt(); // velocity
                int vi = in.nextInt(); // velocity
                int state = in.nextInt(); // 1 if the wizard is holding a Snaffle, 0 otherwise

                switch (entityType){
                    case WIZARD :
                        w[w_i++].set(i, j, vi, vj, entityId, state);
                        break;

                    case OPPONENT_WIZARD:
                        o_w[o_w_i++].set(i, j, vi, vj, entityId, state);
                        break;

                    case BLUDGER:
                        bludgers.add(new Bludger(i, j, vi, vj, entityId));
                        break;

                    case SNAFFLE:
                        snaffles.add(new Snaffle(i, j, vi, vj, entityId));
                        break;
                }
            }

            System.err.println("MANA: " + myMana);
            for (int i = 0; i < MAX_WIZARDS; i++) {
                w[i].makeMove();
            }
        }
    }

    static double distanceTo(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1-x2) + (y1-y2) * (y1-y2));
    }

    static boolean inGoal(int i, int j){
        return i < GOAL_TOP && i > GOAL_BOTTOM && (j == LEFT_EDGE || j == RIGHT_EDGE);
    }

    static double accioPower(double distance) {
        return distance == 0 ? 0 : Math.max(3000 / Math.pow((distance / 1000), 2), 1000);
    }

    static double flipendoPower(double distance) {
        return distance == 0 ? 0 : Math.max(3000 / Math.pow((distance / 1000), 2), 1000);
    }

    static class Wizard extends GameObject{

        boolean hasSnaffle;

        Wizard(int i, int j, int vi, int vj, int id, int state) {
            super(i, j, vi, vj, id);
            this.hasSnaffle = state == 1;
        }



        void moveTo(Snaffle s, int power) {
            moveTo(s.getI(), s.getJ(), power);
        }

        void moveTo(int i, int j, int power) {
            System.out.println(MOVE + " " +  j + " " + i + " " + power);
        }

        void throwTo(int i, int j, int power) {
            System.out.println(THROW + " " + j + " " + i + " " + power);
        }

        void castSpell(String spell, GameObject g){
            System.out.println(spell + " " + g.getID());
        }

        void set(int i, int j, int vi, int vj, int id, int state){
            super.set(i, j, vi, vj, id);
            this.hasSnaffle = state == 1;
        }

        public void makeMove() {
            if(hasSnaffle){
                if(Player.myTeamId == PLAYER_ONE){
                    throwTo(GOAL_CENTER, RIGHT_EDGE, MAX_THROW);
                }else{
                    throwTo(GOAL_CENTER, LEFT_EDGE, MAX_THROW);
                }
            }else{
                Snaffle best = null;
                double shortest = Double.MAX_VALUE;
                for(Snaffle s : snaffles) {
                    if(distanceFrom(s) < shortest){
                        best = s;
                        shortest = distanceFrom(s);
                    }
                }
                moveTo(best, MAX_THRUST);
            }
        }
    }

    private static class DefensiveWizard extends Wizard {

        DefensiveWizard(int i, int j, int vi, int vj, int id, int state) {
            super(i, j, vi, vj, id, state);
        }

        @Override
        public void makeMove() {
            if(hasSnaffle) {
                int edge = myTeamId == PLAYER_ONE ? RIGHT_EDGE: LEFT_EDGE;
                throwTo(GOAL_CENTER, edge, MAX_THROW);
            }
            else{
                int edge = myTeamId == PLAYER_ONE ? LEFT_EDGE: RIGHT_EDGE;
                Snaffle best = null;
                double shortest = Double.MAX_VALUE;
                for(Snaffle s : snaffles){
                    if(distanceTo(s.getI(), s.getJ(), GOAL_CENTER, edge) < shortest){
                        best = s;
                        shortest = distanceTo(s.getI(), s.getJ(), GOAL_CENTER, edge);
                    }
                }
                if(shortest < 2500 && shortest < distanceFrom(GOAL_CENTER, edge) && Player.myMana > ACCIO_COST){
                    castSpell(ACCIO, best);
                    myMana -= ACCIO_COST;
                }else{
                    boolean doneSomething = false;
                    /*
                    if(myMana > OBLIVIATE_COST){
                        Bludger closest = null;
                        shortest = 2000;
                        for(Bludger b : bludgers){
                            if(distanceFrom(b) < shortest && movingTowards(b)){
                                closest = b;
                                shortest = distanceFrom(b);
                            }
                        }
                        if(closest != null){
                            castSpell(OBLIVIATE, closest);
                            myMana -= OBLIVIATE_COST;
                            doneSomething = true;
                        }
                    }
                    */
                    if(!doneSomething) {
                        moveTo(best, MAX_THRUST);
                    }
                }
            }
        }
    }

    private static class OffensiveWizard extends Wizard {

        OffensiveWizard(int i, int j, int vi, int vj, int id, int state){
            super(i, j, vi, vj, id, state);
        }

        @Override
        public void makeMove() {
            if(hasSnaffle){
                int edge = myTeamId == PLAYER_ONE ? RIGHT_EDGE : LEFT_EDGE;
                throwTo(GOAL_CENTER, edge, MAX_THROW);
            }
            else{
                int edge = myTeamId == PLAYER_ONE ? RIGHT_EDGE : LEFT_EDGE;
                Snaffle best = null;
                Snaffle secondBest = null;
                Snaffle flip = null;
                double shortest = Double.MAX_VALUE;
                for(Snaffle s : snaffles){
                    if(myMana > FLIPENDO_COST && viableFlipendo(s) && flipendoPower(distanceFrom(s)) > 2000){
                        flip = s;
                    }
                    if(distanceTo(s.getI(), s.getJ(), GOAL_CENTER, edge) < shortest){
                        secondBest = best;
                        best = s;
                        shortest = distanceTo(s.getI(), s.getJ(), GOAL_CENTER, edge);
                    }
                }
                if(flip != null){
                    castSpell(FLIPENDO, flip);
                    myMana -= FLIPENDO_COST;
                }else {
                    moveTo(best, MAX_THRUST);
                }
            }
        }

        private boolean viableFlipendo(Snaffle s) {
            if((this.getI() < GOAL_TOP && s.getI() > GOAL_BOTTOM) || (this.getI() > GOAL_BOTTOM && s.getI() < GOAL_TOP)){
                System.err.println("Not viable p1");
                return false;
            }
            if(myTeamId == PLAYER_ONE && s.getJ() < this.getJ()){
                System.err.println("Not viable p2");
                return false;
            }else if(myTeamId != PLAYER_ONE && s.getJ() > this.getJ()){
                System.err.println("Not viable p3");
                return false;
            }
            if(myTeamId == PLAYER_ONE){
                double dist1 = RIGHT_EDGE - s.getJ();
                double ratio = dist1 / (s.getJ() - this.getJ());
                double dist2 = (s.getI() - this.getI()) * ratio;
                if(s.getI() + dist2 < GOAL_BOTTOM && s.getI() + dist2 > GOAL_TOP){
                    return true;
                }
                System.err.println("p4 check failed with this.i=:" + getI() + " this.j=" + getJ() + " s.getI()=" +
                        s.getI() + " s.getJ()=" + s.getJ() + " ratio=" + ratio + " dist1=" + dist1 + " dist2=" + dist2);
            }else{
                double dist1 = s.getJ();
                double ratio = dist1 / (this.getJ() - s.getJ());
                double dist2 = (s.getI() - this.getI()) * ratio;
                if(s.getI() + dist2 < GOAL_BOTTOM && s.getI() + dist2 > GOAL_TOP){
                    return true;
                }
                System.err.println("p4 check failed with this.i=:" + getI() + " this.j=" + getJ() + " s.getI()=" +
                        s.getI() + " s.getJ()=" + s.getJ() + " ratio=" + ratio + " dist1=" + dist1 + " dist2=" + dist2);
            }
            System.err.println("Not viable end reached");
            return false;
        }
    }

    private static class Snaffle extends GameObject {
        Snaffle(int i, int j, int vi, int vj, int id){
            super(i, j, vi, vj, id);
        }
    }

    private static class Bludger extends GameObject {
        Bludger(int i, int j, int vi, int vj, int id){
            super(i, j, vi, vj, id);
        }
    }

    static class GameObject {
        private int i, j, vi, vj, id;

        GameObject(int i, int j, int vi, int vj, int id){
            this.i = i;
            this.j = j;
            this.vi = vi;
            this.vj = vj;
            this.id = id;
        }

        public int getI() {
            return this.i;
        }

        int getJ() {
            return this.j;
        }

        int getVI() {
            return this.vi;
        }

        int getVJ() {
            return this.vj;
        }

        int getID() {
            return this.id;
        }

        void set(int i, int j, int vi, int vj, int id) {
            this.i = i;
            this.j = j;
            this.vi = vi;
            this.vj = vj;
            this.id = id;
        }

        double distanceFrom(GameObject s){
            return distanceFrom(s.getI(), s.getJ());
        }

        double distanceFrom(int i, int j) {
            return Player.distanceTo(this.getI(), this.getJ(), i, j);
        }

        boolean movingTowards(GameObject g){
            return this.j < g.j == vj > 0 && this.i < g.i == vi > 0;
        }

        boolean closestTo(GameObject target, GameObject...others){
            double distanceTo = this.distanceFrom(target);
            for(GameObject g : others){
                if(g.distanceFrom(target) < distanceTo){
                    return false;
                }
            }
            return true;
        }

    }
}
