 


import java.util.LinkedList;
/**
 * The match class records all of the data for a single team in a single match
 * including all of the details of the beginning of the game / autonomous
 * and performance in teleop and endgame
 */
public class Match
{
    protected int matchNumber,teamNumber,gold,silver,depot;
    protected String position,orientation,goldPosition,endgame,comment;
    private LinkedList<String> autoTasks;

    protected int autoTotal, teleopTotal, endgameTotal,scoreTotal;

    protected String allianceColor;

    /**
     * instantiates the match class variables given all of the values for the match
     * that have been parsed from the spreadsheet by the driver class
     */
    public Match(int matchNumber, int teamNumber,String allianceColor, String position,String orientation,String goldPos, LinkedList<String> autoTasks, int gold, int silver, int depot, String endGame, String comment)
    {
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber; 
        this.gold = gold;
        this.silver = silver;
        this.depot = depot;
        this.position = position;
        this.orientation = orientation;
        this.goldPosition = goldPosition;
        this.endgame = endGame; 
        this.comment = comment;
        this.autoTasks = autoTasks;
        this.allianceColor = allianceColor.trim().toLowerCase();
        //by callig this method the autoTotal, teleopTotal, and scoreTotal 
        //variables will be instantiated
        scoreTotal = reportScore();
    }

    /**
     * returns the total score of a match (excluding penalties)
     * by adding the total scores from auto teleop and endgame
     * 
     * Assigns values to the autoTotal and teleopTotal while running this method
     */
    public int reportScore()
    {
        autoTotal = reportAutoScore();
        teleopTotal = reportTeleopScore();
        endgameTotal = reportEndgame();
        //sums the total points of the match.
        return teleopTotal + endgameTotal + autoTotal;
    }

    /**
     * goes through the list of all tasks completed in auto
     * and returns the total score from autonomous
     */
    private int reportAutoScore() {
        int autoTotal = 0;
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
        return autoTotal;
    } 

    private int reportEndgame()
    {
        int sum = 0;
        switch (endgame){
            case "Hanging on Lander":
            sum = 50;
            break;
            case "Parked Partially in Crater":
            sum = 15;
            break;
            case "Parked Completely in Crater":
            sum = 25;
            break;
            default:
            sum = 0;
            break;
        }
        return sum;
    }

    /**
     * Scales the precollected values to their point totals 
     * and returns total points from teleop
     */
    private int reportTeleopScore() {
        return (gold * 5) + (silver * 5) + (depot * 2);
    }

    public String toString()
    { String output = "Team #: " + teamNumber + "\nTotal Points:" + reportScore() + "\nAlliance Color: " + allianceColor + "\nStarting Position: Started " + orientation + " on " + position + "\nWith Sample in Position:" + goldPosition;
        output += "\nAuto Points: " + reportAutoScore() + "\nAuto Tasks:" + "";
        for(String str:autoTasks)
        {
            output += str + ",";
        }
        output +="\nTeleOp Points: " + reportTeleopScore() + "\nGold Scored:" + gold + "\nSilver Scored:" + silver;
        output += "\nEndgame Points: " + reportEndgame() + "\nEndgame Tasks: " + endgame+ "\nComments: " + comment + "\n";
        return output;
    }
}