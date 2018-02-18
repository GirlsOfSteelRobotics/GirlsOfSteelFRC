import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
	private ArrayList<Team> data;
	
	public Database(String filename) throws NumberFormatException, IOException
	{
		data = new ArrayList<Team>();
		FileReader fileInput = new FileReader(filename);
		BufferedReader file = new BufferedReader(fileInput);
		String line = file.readLine();
		
		while ((line = file.readLine()) != null) {
			String[] lineInput = line.split(",");
			int teamNumber = Match.getInt(lineInput[1]);
			//Create match
			Match match = new Match(lineInput);
			
			
			int index = getTeamIndex(teamNumber);
			Team team;
			if (index == -1) 
			{
				team = new Team(teamNumber);
				data.add(team);
			}
			else
			{
				team = data.get(index);
			}
			
			
			team.addMatch(match);
		}
		
		file.close();
	}
	
	public void printStats(int teamNumber, PrintStream output)
	{
		output.println("Team Stats for team #" + teamNumber + ":");
		Team team = data.get(getTeamIndex(teamNumber));
		team.printStats(output);
	}
	
	public void printMatches(int teamNumber, PrintStream output)
	{
		output.println("Matches for team #" + teamNumber + ":");
		Team team = data.get(getTeamIndex(teamNumber));
		team.printMatches(output);

	}
	
	public boolean isTeamInDatabase(int teamNumber)
	{
		for (int i = 0; i < data.size(); i++)
		{
			if (data.get(i).teamNumber == teamNumber)
				return true;
		}
		return false;
	}
	
	public int getTeamIndex(int teamNumber)
	{
		for (int i = 0; i < data.size(); i++)
		{
			if (data.get(i).teamNumber == teamNumber)
				return i;
		}
		return -1;
	}
	
	public void writeDataSheets(String foldername) throws IOException
	{
		for (int i = 0; i < data.size(); i++)
		{
			String filename = foldername + "/" + Integer.toString(data.get(i).teamNumber) + ".txt";
			System.out.println("Writing " + filename);
			data.get(i).writeStatFile(filename);
		}
		
	}

}
