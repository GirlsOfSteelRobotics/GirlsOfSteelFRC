import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Team {
	public int teamNumber;
	public ArrayList<Match> matches;
	
	public Team(int teamNum)
	{
		teamNumber = teamNum;
		matches = new ArrayList<Match>();
	}
	
	public double getWinPercentage()
	{
		int wins = 0;
		for (int i = 0; i < matches.size(); i++)
		{
			if (matches.get(i).win) wins++;
		}
		return ((double)wins)/matches.size();
	}
	
	public double getAveragePoints()
	{
		int total = 0;
		for (int i = 0; i < matches.size(); i++)
		{
			total += matches.get(i).points;
		}
		return ((double)total)/matches.size();
	}
	
	public void addMatch(Match match)
	{
		matches.add(match);
	}
	
	public void printStats(PrintStream output)
	{
		output.println("Win Percentage = " + (getWinPercentage()*100) + "%");
		output.println("Average Points = " + getAveragePoints());
	}
	
	public void printMatches(PrintStream output)
	{
		for (int i = 0; i < matches.size(); i++)
		{
			matches.get(i).printMatch(output);
		}
	}
	
	public void writeStatFile(String filename) throws IOException
	{
		FileWriter outFile = new FileWriter(filename);
		BufferedWriter fout = new BufferedWriter(outFile);
		
		fout.newLine();
		writeStats(fout);
		fout.newLine();
		writeMatches(fout);
		fout.close();
	}
	
	public void writeStats(BufferedWriter fout) throws IOException
	{
		
		fout.write("Win Percentage = " + (getWinPercentage()*100) + "%");
		fout.newLine();
		fout.write("Average Points = " + getAveragePoints());
	}
	
	public void writeMatches(BufferedWriter fout) throws IOException
	{
		for (int i = 0; i < matches.size(); i++)
		{
			matches.get(i).writeMatch(fout);
		}
	}


}
