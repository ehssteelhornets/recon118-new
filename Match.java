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

    /**
     * returns the total score of a match (excluding penalties)
     */
    public int reportScore()
    {
        int endgameTotal;
        int autoTotal = 0;
        switch (endgame){
            case "Hanging on Lander":
                endgameTotal = 50;
                break;
            case "Parked Partially in Crater":
                endgameTotal = 15;
                break;
            case "Parked Completely in Crater":
                endgameTotal = 25;
                break;
            default:
                endgameTotal = 0;
                break;
        }
        for (String task: autoTasks) {
            switch (task) {
                case "Drop and Detach from Lander":
                    autoTotal += 30;
                    break;
                case "Correct Mineral Hit":
                    autoTotal += 25;
                    break;
                case "Place Team Marker":
                    autoTotal += 15;
                    break;
                case "Park in Crater":
                    autoTotal += 10;
                    break;
            }
        }
        //Scales the precollected values to their point totals and sums the total points of the match.
        return (gold * 5) + (silver * 5) + (depot * 2) + endgameTotal + autoTotal;
    }
}
