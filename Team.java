package recon118;
import java.util.LinkedList;
public class Team
{
    //Initilizable values
    private String name;
    private int teamNumber;
    //Storage Values
    private LinkedList<String> startingPosition = new LinkedList<String>(),
                               startingOrientation = new LinkedList<String>(),
                               goldPositions = new LinkedList<String>(),
                               endGames = new LinkedList<String>(),
                               comments = new LinkedList<String>();
    private LinkedList<Integer> goldInLander = new LinkedList<Integer>(),
                                silverInLander = new LinkedList<Integer>(),
                                mineralsInDepot = new LinkedList<Integer>(),
                                penaltylessScores = new LinkedList<Integer>();
    private LinkedList<LinkedList<String>> tasksInAuto = new LinkedList<LinkedList<String>>();
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

    public void updateInformation(String position,String orientation,String goldPos, String[] autoTasks, int gold, int silver, int depot, String endGame, String comment)
    {
        
        startingPosition.add(position);
        startingOrientation.add(orientation);
        comments.add(comment);

    }
}
