/**
 * file: DriverDFA.java
 * author: Juan Bancamper
 * course: CMPT 440
 * assignment: Lab 2
 * due date: February 15, 2016
 * 
 * This file contains the driver code for the ManWolf class
 */

/**
 * DriverDFA
 *
 * This class accepts input via command line and tests if ManWolf
 * solution is correct.
 */

public class DriverDFA{
	
	/**
	 * main
	 * 
	 * This is the method recives input and uses the ManWolf class to determine
	 * correctness of provided solution.
	 *
	 * Parameters:
	 *     args: a space separated list of strings when running the program
	 *
	 * Return value: none
	 */
	public static void main(String[] args) {
		if(args.length < 1){
			System.err.println("Usage: java driverDFA <solution>");
			System.exit(-1);
		}

		ManWolf.process(args[0]);

		if (ManWolf.isCorrect()) {
			System.out.println("This is a solution");
		}
		else{
			System.out.println("This is not a solution");
		}
	}
}