package recon118;
import java.util.LinkedList;
public class Team
{
    //Initilizable values
    private String name;
    private int teamNumber;
    //Storage Values
    private LinkedList<Match> matches = new LinkedList<Match>();
    private int totalScore = 0,
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

    public int getNum()
    {
        return 1;
    }

    public void addMatch(Match m)
    {
        matches.add(m);
    }
}
