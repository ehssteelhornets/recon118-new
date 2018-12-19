public class Team
{
    //Initilizable values
    private String name;
    private int teamNumber;
    //Storage Values
    private int totalScore;
    private int penaltylessScore;

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
}
