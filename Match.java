package recon118;

public class Match
{
    private int matchNumber,teamNumber,gold,silver,depot;
    private String position,orientation,goldPosition,endgame,comment;
    private String[] autoTasks;
    public Match(int matchNumber, int teamNumber,String position,String orientation,String goldPos, String[] autoTasks, int gold, int silver, int depot, String endGame, String comment)
    {
        this.matchNumber = matchNumber;this.teamNumber = teamNumber; this.gold = gold;this.silver = silver;
        this.position = position;this.orientation = orientation;this.goldPosition = goldPosition;this.endgame = endgame; this.comment = comment;
        this.autoTasks = autoTasks;
    }
    
}
