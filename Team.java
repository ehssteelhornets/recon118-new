package recon118;

 
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;
public class Team
{
    //Initilizable values
    protected String name;
    protected int teamNumber;
    //Storage Values
    //Could be implemented as TreeSet
    //private Set<Match> matches = new TreeSet<Match>();
    private LinkedList<Match> matches = new LinkedList<Match>();
    private Set <Game> games = new TreeSet<Game>();
    protected int totalScore = 0,
        penaltylessScore = 0,
        wins = 0,
        losses = 0;
    public Team(String name, int teamNumber)
    {
        this.name = name;
        this.teamNumber = teamNumber;
    }
    
    public void addScore(int gameScore, int penaltys)
    {
        totalScore += gameScore;
        penaltylessScore += gameScore - penaltys;
    }

    public String toString()
    {
        return "Team " + teamNumber + "," + name;
    }

    public void addWin()
    {
        wins++;
    }
    
    public void addLoss()
    {
        losses++;
    }
    //Get the teams overall Ranking
    public int getNum()
    {
        return 1;
    }
    //Adds a new match to the list of matches for that team
    public void addMatch(Match m)
    {
        matches.add(m);
    }
    
    public int compareTo(Object team) {
        Team otherTeam = (Team)team;
        return this.totalScore - otherTeam.totalScore;
    }
    
    public int compareTo(Team otherTeam, int parameter) {
        return 0;
    }
}
