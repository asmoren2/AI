import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class BFSPuzzle {
    String str = ""; // initial state
    String goal = "";

    LinkedList <String> fringeNodes;

    Map<String,Integer> levelDepth;//stores currState and level

    Map<String,String> stateHistory;//stores currstate => parent mapping

    int nodes = 0;
    int limit = 10000;
    int newNodeCounter = -1;
    int newValue;
    int a;

    String nextState;
    String currState;
    boolean solution = false;

    BFSPuzzle(String str,String goal) {
        levelDepth = new HashMap<String, Integer>();
        stateHistory = new HashMap<String,String>();
        this.str = str;
        this.goal = goal;
        fringeNodes = new LinkedList <String> ();
        openListContainer(str,null);//add root
        doBFSSearch();
    }

    void doBFSSearch() {
        while (!fringeNodes.isEmpty()){
            currState = fringeNodes.removeFirst();// remove first node of our openlist

            if (currState.equals(goal)) {// goal check
                solution = true;
                dispSol(currState);
                break;
            }

            if (levelDepth.get(currState) == limit){//check if under limit
                solution = false;
                dispSol(currState);
                break;
            } else {
                a = currState.indexOf("0");

                // down only if possible
                if (a < 12) {
                	
                    nextState = currState.substring(0,a)+currState.substring(a+4,a+5)+currState.substring(a+1,a+4)+"0"+currState.substring(a+5);

                    openListContainer(nextState, currState);
                    nodes++;
                }

                // move to left only if possible
                if (a!=0 && a!=4 && a!=8 && a!=12){
                    nextState = currState.substring(0,a-1)+"0"+currState.charAt(a-1)+currState.substring(a+1);
                    openListContainer(nextState, currState);
                    nodes++;
                }

                // right only if possible
                if(a!=3 && a!=7 && a!=11 && a!=15){
                    nextState = currState.substring(0,a)+currState.charAt(a+1)+"0"+currState.substring(a+2);
                    openListContainer(nextState, currState);
                    nodes++;
                }

                // up only if possible
                if (a > 3){
                    nextState = currState.substring(0,a-4)+"0"+currState.substring(a-3,a)+currState.charAt(a-4)+currState.substring(a+1);
                    openListContainer(nextState, currState);
                    nodes++;
                }
            }

        }

        if (solution) {
            System.out.println("Solution Exists");
        } else {
            System.out.println("Solution not found. Maybe due to lack of memory or depth limit set is reached");
        }

    }

    private void openListContainer (String newState, String oldState){
        if(!levelDepth.containsKey(newState)){// check repeated state
            newNodeCounter ++;
            fringeNodes.add(newState);
            stateHistory.put(newState, oldState);
            newValue = oldState == null ? 0 : levelDepth.get(oldState) + 1;
            levelDepth.put(newState, newValue);
        }

    }

    void dispSol(String currState) {
        if (solution){
            System.out.println("Solution in " + levelDepth.get(currState) +" step(s)");
            System.out.println("Nodes generated: "+ nodes);
            System.out.println("Unique Nodes: "+ newNodeCounter);
        } else{
            System.out.println("Solution not found!");
            System.out.println("Depth Limit Reached " + limit);
            System.out.println("Nodes generated: "+ nodes);
            System.out.println("Unique Nodes: "+ newNodeCounter);
        }

        String traceState = currState;
        LinkedList <String> finalAnswer;
        finalAnswer = new LinkedList <String> ();
        while (traceState != null) {
            finalAnswer.add(traceState);
            traceState = stateHistory.get(traceState);
        }

        while(!finalAnswer.isEmpty()) {
            traceState = finalAnswer.removeLast();
            System.out.println("Step " + levelDepth.get(traceState));
            try {
                printMatrix(traceState);
            } catch (NullPointerException e) {
            }
        }

    }

    public void printMatrix(String stateStr) {
        for(int z=0;z<16;z++) {
            if(String.valueOf(stateStr.charAt(z)).equals("0")) {
                System.out.print("  ");
            } else {
                System.out.print(String.valueOf(stateStr.charAt(z)) + " ");
            }
            if ((z+1) % 4 == 0) {
                System.out.println();
            }
        }
    }

    public static void main(String []args) {
        //String inputPuzzle = args[0].replace(",", "");
        String inputPuzzle = "123456789abcdef0";
        BFSPuzzle bfs = new BFSPuzzle(inputPuzzle, "123456789abc0def");
    }
}
