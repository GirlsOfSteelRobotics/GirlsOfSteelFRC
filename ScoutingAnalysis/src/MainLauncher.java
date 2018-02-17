import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MainLauncher {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		PrintStream output = new PrintStream(System.out);
		int i; String s;
		
		Database data = new Database();
		try {
			data = Database.readDatabase("ScoutingData.dat");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output.println("ERROR! main: file not found");
		}
		boolean done = false;
		while(!done)
		{
			output.println("Scouting Analysis Menu");
			output.println("1. Display Team Statistics");
			output.println("2. Quit");
			output.print("Choose an option: ");
			
			i = input.nextInt();
			output.println();
			
			if (i == 1) //Display Team Stats
			{
				output.print("Enter a team number: ");
				int teamNumber = input.nextInt();
				
				data.printStats(teamNumber, output);
				
				output.print("See matches? (y/n): ");
				if (input.next().equals("y"))
				{
					output.println();
					data.printMatches(teamNumber, output);
				}
				
				output.println();
				output.print("Return to menu? (y/n): ");
				if (!input.next().equals("y"))
					done = true;
			}
			else done = true;
			
			output.println();
		}
		
		output.println("Goodbye!");
		input.close();
		// TODO Auto-generated method stub

	}

}
