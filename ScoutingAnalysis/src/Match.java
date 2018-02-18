import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Match {
	public int matchNumber;
	public boolean win;
	public int points;
	public String comments;
	
	public Match()
	{
		matchNumber = -1;
		win = false;
		points = -1;
	}
	
	public void printMatch(PrintStream output)
	{
		output.println("Match #" + matchNumber + ": " + (win ? "Win" : "Loss") + ", " + points + " points scored, comments: " + comments);
	}
	
	public void writeMatch(BufferedWriter fout) throws IOException
	{
		fout.write("Match #" + matchNumber + ": " + (win ? "Win" : "Loss") + ", " + points + " points scored, comments: " + comments);
		fout.newLine();
	}
	

}
