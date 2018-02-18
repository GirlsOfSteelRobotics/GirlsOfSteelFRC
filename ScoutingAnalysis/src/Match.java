import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Match {
	public int matchNumber;
	public String autoFunction;
	public String autoComments;
	public double autoRating;
	public int cubesEZ;
	public int cubesSwitch;
	public int cubesScale;
	public double cycleRating;
	public double drivingRating;
	public String endgameFunction;
	public String result;
	public int alliancePoints;
	public int opponentPoints;
	public String generalComments;
	
	public Match(String[] lineInput)
	{
		matchNumber = getInt(lineInput[2]);
		autoFunction = getString(lineInput[3]);
		System.out.println(lineInput[4]);
		autoComments = getString(lineInput[4]);
		autoRating = getInt(lineInput[5]);
		
		cubesEZ = getInt(lineInput[6]);
		cubesSwitch = getInt(lineInput[7]);
		cubesScale = getInt(lineInput[8]);
		
		cycleRating = getInt(lineInput[9]);
		drivingRating = getInt(lineInput[10]);
		
		endgameFunction = getString(lineInput[11]);
		result = getString(lineInput[12]);

		alliancePoints = getInt(lineInput[13]);
		opponentPoints = getInt(lineInput[14]);
		generalComments = getString(lineInput[15]);
	}
	
	public void printMatch(PrintStream output)
	{
		output.println("Match #" + matchNumber + ": " + result + ", " + alliancePoints + " - " + opponentPoints);
		output.println("\tComments: " + generalComments);
	}
	
	public void writeMatch(BufferedWriter fout) throws IOException
	{
		fout.write("Match #" + matchNumber + ": " + result + ", " + alliancePoints + " - " + opponentPoints);
		fout.newLine();
		fout.write("\tComments: " + generalComments);
		fout.newLine();
	}
	
	private static String getString(String input)
	{
		if (input.length() == 0) return "";
		if (input.charAt(0) == '"') return input.substring(1, input.length()-1);
		else return input;
	}
	
	public static int getInt(String input)
	{
		if (input.charAt(0) == '"')
		{
			input = input.substring(1, input.length()-1);
		}
		return Integer.parseInt(input);
	}
	

}
