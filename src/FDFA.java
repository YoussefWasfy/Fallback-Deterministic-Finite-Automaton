import java.util.*;

/**
 * Write your info here
 *
 * @name Youssef Ahmed Wasfy
 * @id 43-3793
 * @labNumber 01
 */
public class FDFA {
    /**
     * FDFA constructor
     *
     * @param description is the string describing a FDFA
     */
    ArrayList<String> acceptStates;
    Hashtable<String, String> zerosTable;
    Hashtable<String, String> onesTable;
    Hashtable<String, String> actionsTable;
    Stack<String> stack;
    String currentState;
    String firstStateInStack;
    boolean newStack;


    public FDFA(String description) {
        this.zerosTable = new Hashtable<>();
        this.onesTable = new Hashtable<>();
        this.actionsTable = new Hashtable<>();
        this.stack = new Stack<>();
        this.currentState = "0";
        this.newStack = true;
        String[] firstSplit = description.split("#");
        String[] desSplit = firstSplit[0].split(";");
        for (int i = 0; i < desSplit.length; i++) {
            String[] stateInfo = desSplit[i].split(",");

            this.zerosTable.put(stateInfo[0], stateInfo[1]);
            this.onesTable.put(stateInfo[0], stateInfo[2]);
            this.actionsTable.put(stateInfo[0], stateInfo[3]);
        }
        String[] acc = firstSplit[1].split(",");
        this.acceptStates = new ArrayList<>(Arrays.asList(acc));
        System.out.println(zerosTable);
        System.out.println(onesTable);
        System.out.println(actionsTable);

    }

    /**
     * Returns a string of actions.
     *
     * @param input is the string to simulate by the FDFA.
     * @return string of actions.
     */

    public void pushInStack(String input, int index) {
        System.out.println("J in pushing: "+index);
        for (int i = index; i < input.length(); i++) {
            if (Character.toString(input.charAt(i)).equals("0")) {
                this.currentState = this.zerosTable.get(this.currentState);
                System.out.println(this.currentState);
                this.stack.push(this.currentState);
            } else if (Character.toString(input.charAt(i)).equals("1")) {
                this.currentState = this.onesTable.get(this.currentState);
                this.stack.push(this.currentState);
            }
        }
        if (index >= input.length())
        {

            this.currentState = this.onesTable.get(this.currentState);
            System.out.println(this.currentState);
            this.stack.push(this.currentState);
        }

//		System.out.println(this.stack);

    }

    public String run(String input) {
        String output = "";
        int i = 0;
        int j = 0;
        String heldState;
        this.stack.push(this.currentState);
        while (true) {
            pushInStack(input, j);
            if (this.newStack)
            {
                if (!this.stack.empty())
                {
                    this.firstStateInStack = this.stack.peek();
                    System.out.println("new Stack: "+ this.firstStateInStack);
                    this.newStack = false;
                }

            }
            System.out.println(this.stack);
            j = input.length() +1;
            if (j <= 0 || i >= input.length()) {
                break;
            }
            while (true) {
                try
                {
                    System.out.println("Popping");
                    heldState = this.stack.pop();
                    System.out.println(heldState);
                    j--;
                    if (this.acceptStates.contains(heldState)) {
                        System.out.println("State accepted");
                        output += input.substring(i, j) + "," + this.actionsTable.get(heldState) + ";";
                        i = j ;
//                        j++;
                        this.stack.clear();
                        this.currentState = "0";
                        this.newStack = true;
                        break;
                    }

                }
                catch (EmptyStackException e)
                {
                    System.out.println( "Empty Stack");
                    System.out.println(this.currentState);
                    System.out.println(this.firstStateInStack);
                    return  output + input.substring(i)+","+this.actionsTable.get(this.firstStateInStack)+";";
                }
            }
        }

        System.out.println("Not empty");
        return output;
    }

    public static void main(String[] args) {
        FDFA fdfa = new FDFA("0,0,1,A;1,2,1,B;2,0,3,C;3,3,3,N#0,1,2");
        String output = fdfa.run("1011100");
        System.out.println(output);


    }
}
