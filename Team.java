 

import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;
/**
 * The team class records all of the data for al of the (5) matches
 * that a team will play in the qualifying matches.
 * The team class will process the total scores in different categories
 * that the team scores throughout all of the qualifying matches.
 */
public class Team
{
    //Initilizable values
    protected String name;
    protected int teamNumber;
    //Storage Values
    //Could be implemented as TreeSet
    //private Set<Match> matches = new TreeSet<Match>();
    protected LinkedList<Match> matches = new LinkedList<Match>();
    protected Set <Game> games = new TreeSet<Game>();
    protected int totalScore = 0,
    totalAutoScore = 0,
    totalTeleopScore = 0,
    penaltylessScore = 0,
    wins = 0,
    losses = 0;

    /**
     * Main constructor for Team class
     * Instantiates a Team object using the team name and number
     */
    public Team(String name, int teamNumber)
    {
        this.name = name;
        this.teamNumber = teamNumber;
    }

    /**
     * Secondary constructor for Team class
     * Instantiates a Team object using only the team number
     * Used when the team name is not recorded in the spreadsheet
     */
    public Team(int teamNumber)
    {
        this.teamNumber = teamNumber;
        name = "";
    }

    /**
     * Updates the score variables given the total score
     * and penalties for a single match
     */
    public void addScore(int gameScore, int penalties)
    {
        totalScore += gameScore;
        penaltylessScore += gameScore - penalties;
    }

    /**
     * Reports the data about a team
     */
    public String toString()
    {
        String teamStr = "Team " + teamNumber;
        if (!name.equals("")) 
            teamStr += ", " + name;
        return teamStr;
    }

    /**
     *Adds a win to the teams record
     */
    public void addWin()
    {
        wins++;
    }

    /**
     * Adds a loss to the teams record
     */
    public void addLoss()
    {
        losses++;
    }

    /**
     * Get the teams unique identifying number
     */
    public int getTeamNum()
    {
        return teamNumber;
    }

    /**
     * Adds a match to the list of matches for the team
     * Then adds the auto, teleop, and total scores of the match
     * to the respective total variables of this object.
     */
    public void addMatch(Match m)
    {
        totalAutoScore += m.autoTotal;
        totalTeleopScore += m.teleopTotal;
        totalScore += m.scoreTotal;
        matches.add(m);
    }
}
