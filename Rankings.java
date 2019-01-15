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
    {   switch (sortType.toLowerCase()) {
            case "auto":
            Collections.sort(teams,new sortByAuto());
            break;
            case "teleop":
            Collections.sort(teams,new sortByTeleop());
            break;
            case "total points":
            Collections.sort(teams,new sortByPoints());
            break;
        }
    }

    public Team getHighest()
    {
        sort("Highest");
        return teams.get(1);
    }

    public Team getHighestAuto()
    {
        sort("auto");
        return teams.get(1);
    }

    public Team getHighestTeleop()
    {
        sort("teleop");
        return teams.get(1);
    }

    public Team getTopScorer()
    {
        sort("total points");
        return teams.get(1);
    }

    /**
     * Prints out the output 
     */
    public void sendOutput() {
        String outputStr = "Top teams based on " + sortType + ":/n";
        for (int t = 0; t < 10; t++) {
            Team team = teams.get(t);
            outputStr += t + ") ";
            outputStr += team;
            switch (sortType.toLowerCase()) {
                case "auto points":
                outputStr += team.toString();
            }
            outputStr += "\n";
        }
        System.out.println(outputStr);
    }
}
