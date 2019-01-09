package recon118;
public class Team
{
    //Initilizable values
    private String name;
    private int teamNumber;
    //Storage Values
    private int totalScore;
    private int penaltylessScore;
    private int wins;
    private int losses;
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
}
