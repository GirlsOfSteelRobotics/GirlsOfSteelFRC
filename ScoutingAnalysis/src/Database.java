import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
	private ArrayList<Team> data;
	
	public Database()
	{
		data = new ArrayList<Team>();
	}
	
	public static Database readDatabase(String filename) throws FileNotFoundException
	{
		Database d = new Database();
		InputStream fileInput = new FileInputStream(filename);
		Scanner file = new Scanner(fileInput);
		while (file.hasNext()) {
			int teamNumber = file.nextInt();
			Team team;
			if (!d.isTeamInDatabase(teamNumber)) 
			{
				team = new Team(teamNumber);
			}
			else
			{
				team = d.getTeam(teamNumber);
			}
			
			Match match = new Match();
			match.matchNumber = file.nextInt();
			match.win = (file.next().equals("Yes"));
			match.points = file.nextInt();
			team.addMatch(match);
			d.data.add(team);
		}
		
		file.close();
		return d;
	}
	
	public void printStats(int teamNumber, PrintStream output)
	{
		output.println("Team Stats for team #" + teamNumber + ":");
		Team team = getTeam(teamNumber);
		output.println("Win Percentage = " + (team.getWinPercentage()*100) + "%");
		output.println("Average Points = " + team.getAveragePoints());
	}
	
	public void printMatches(int teamNumber, PrintStream output)
	{
		output.println("Matches for team #" + teamNumber + ":");
		Team team = getTeam(teamNumber);
		for (int i = 0; i < team.matches.size(); i++)
		{
			team.matches.get(i).printMatch(output);
		}
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
	
	public Team getTeam(int teamNumber)
	{
		for (int i = 0; i < data.size(); i++)
		{
			if (data.get(i).teamNumber == teamNumber)
				return data.get(i);
		}
		System.out.println("ERROR! getTeam: Team not found.");
		return (new Team(-1));
	}

}
