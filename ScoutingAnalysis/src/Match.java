import java.io.PrintStream;

public class Match {
	public int matchNumber;
	public boolean win;
	public int points;
	
	public Match()
	{
		matchNumber = -1;
		win = false;
		points = -1;
	}
	
	public void printMatch(PrintStream output)
	{
		output.println("Match #" + matchNumber + ": " + (win ? "Win" : "Loss") + ", " + points + " points scored");
	}

}
