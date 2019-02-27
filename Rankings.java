package recon118;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
public class Rankings
{
    private LinkedList<Team> teams = null;
    private String sortType = "";
    public Rankings(String sortType, LinkedList<Team> teams)
    {
        this.teams = teams;
        this.sortType = sortType;
    }

    /**
     * Sorts the data by comparators defined by the sorter classes
     */
    public void sort(String sortType)
    {
        this.sortType = sortType;
        switch (sortType.toLowerCase()) {
            case "auto":
            Collections.sort(teams,new sortByAuto());
            break;
            case "average teleop":
            Collections.sort(teams,new sortByAverageTeleop());
            break;
            case "average total points":
            Collections.sort(teams,new sortByAveragePoints());
            break;
            case "teleop":
            Collections.sort(teams,new sortByTeleop());
            break;
            case "total points":
            Collections.sort(teams,new sortByPoints());
            break;
            case "wins":
            Collections.sort(teams,new sortByWin());
            break;
            case "team number":
            Collections.sort(teams,new sortByTeamNumber());
            break;
        }
    }

    public Team getHighestAuto()
    {
        sort("auto");
        sendOutput();
        return teams.get(1);
    }

    public Team getHighestTeleop()
    {
        sort("teleop");
        sendOutput();
        return teams.get(1);
    }

    public Team getAverageHighestTeleop()
    {
        sort("average teleop");
        sendOutput();
        return teams.get(1);
    }

    public Team getTopScorer()
    {
        sort("total points");
        sendOutput();
        return teams.get(1);
    }

    public Team getAverageTopScorer()
    {
        sort("average total points");
        sendOutput();
        return teams.get(1);
    }

    public Team getWinner()
    {
        sort("wins");
        sendOutput();
        return teams.get(1);
    }

    public Team getLowestTeamNumber()
    {
        sort("team number");
        sendOutput();
        return teams.get(1);
    }

    /**
     * Prints out the output 
     */
    public void sendOutput() {
        String outputStr = "Top teams based on " + sortType + ":\n";
        for (int t = 0; t < teams.size(); t++) {
            Team team = teams.get(t);
            outputStr += (t+1) + ") ";
            outputStr += team;
            switch (sortType.toLowerCase()) {
                case "auto points":
                outputStr += team.toString();
                break;
                case "wins":
                outputStr += " with " + team.wins + " wins";
                break;
                case "average teleop":
                outputStr += " with an average of " + team.getMatchTeleopAverage() + " points in Teleop";
                break;
                case "teleop":
                outputStr += " with " + team.getTeleop() + " total points in Teleop";
                break;
                case "average total points":
                outputStr += " with an average of " + team.getMatchTotalAverage() + " points per game";
                break;
                case "total points":
                outputStr += " with " + team.getTotalPoints() + " total points";
                break;
            }
            outputStr += "\n";
        }
        System.out.println(outputStr);
    }
}
