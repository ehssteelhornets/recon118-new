package recon118;

import java.util.LinkedList;
public class Match
{
    private int matchNumber,teamNumber,gold,silver,depot;
    private String position,orientation,goldPosition,endgame,comment;
    private LinkedList<String> autoTasks;
    public Match(int matchNumber, int teamNumber,String position,String orientation,String goldPos, LinkedList<String> autoTasks, int gold, int silver, int depot, String endGame, String comment)
    {
        this.matchNumber = matchNumber;this.teamNumber = teamNumber; this.gold = gold;this.silver = silver;
        this.position = position;this.orientation = orientation;this.goldPosition = goldPosition;this.endgame = endgame; this.comment = comment;
        this.autoTasks = autoTasks;
    }

    public void reportScore()
    {
        int endgameTotal = 0;
        if(endgame.equals("Hanging on Lander"))
        {
            endgameTotal += 0;
        }
        
        return (gold * 5) + (silver * 5) + (depot * 2) + endgameTotal
    }
}
