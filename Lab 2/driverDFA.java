public class driverDFA{
	
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