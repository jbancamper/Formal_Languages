/**
 * file: ManWolf.java
 * author: Juan Bancamper
 * course: CMPT 440
 * assignment: Lab 2
 * due date: February 15, 2016
 * 
 * This file contains the the ManWolf DFA table and its behaviors
 */

/**
 * ManWolf
 *
 * This class implements a DFA in a table and processes input
 * to determine an accepting state.
 */

public class ManWolf {
    
    static private final int q0 = 0;
    static private final int q1 = 1;
    static private final int q2 = 2;
    static private final int q3 = 3;
    static private final int q4 = 4;
    static private final int q5 = 5;
    static private final int q6 = 6;
    static private final int q7 = 7;
    static private final int q8 = 8;
    static private final int q9 = 9;
    static private final int q10 = 10;

    static private int[][] delta = {
        {q10, q10, q1, q10},
        {q2, q10, q0, q10},
        {q1, q3, q10, q5},
        {q10, q2, q4, q10},
        {q10, q10, q3, q7},
        {q10, q10, q6, q2},
        {q10, q7, q5, q10},
        {q8, q10, q10, q4},
        {q7, q10, q9, q10},
        {q10, q10, q8, q10},
        {q10, q10, q10, q10}
    };

    static private int state = q0;

    /**
     * process
     * 
     * This function iterates over a string, character by character,
     * to be able to deterrmine an accepting state
     *
     * Parameters:
     *     in: the string to be iterated
     * 
     * Return value: none
     */
    static void process(String in){
        if(in.length() < 1){
            return;
        }

        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            int input;

            switch(c){
                case 'c':
                    input = 3;
                    break;
                case 'g':
                    input = 2;
                    break;
                case 'w':
                    input = 1;
                    break;
                case 'n':
                    input = 0;
                    break;
                default:
                    System.out.println("Please enter correct symbols:" +
                        "{c, g, n, w}");
                    return;
            }

            try{
                state = delta[state][input];
            }
            catch(ArrayIndexOutOfBoundsException ex){
                state = q10;
            }
        }
    }

    /**
     * isCorrect
     *
     * This function checks if the state is in an accepting state
     *
     * Parameters:
     *     none
     *
     * Return value: true or false if state is accepting
     */
    static boolean isCorrect(){
        return state == q9;
    }
}