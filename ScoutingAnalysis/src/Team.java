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

}
