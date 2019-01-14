package recon118;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
public class Rankings
{
    private Comparator sorter = null;
    private LinkedList<Team> teams = null;
    private String rankingParameterStr = "";
    public Rankings(String sortType, LinkedList<Team> teams)
    {
        this.teams = teams;
        //Fill in a comparator
        this.sorter = null;
    }

    public void sort(String sortType)
    {
        Collections.sort(teams,sorter);
    }

    public Team getHighest()
    {
        sort("Highest");
        return teams.get(1);
    }

    public Team getTopScorer()
    {
        sort("Highest Scoring");
        return teams.get(1);
    }
    
    public void sendOutput() {
        String outputStr = "Top teams based on " + rankingParameterStr + ":/n";
        for (int t = 0; t < 10; t++) {
            Team team = teams.get(t);
            outputStr += t + ") ";
            outputStr += team;
            switch (rankingParameterStr.toLowerCase()) {
                case "auto points":
                    outputStr += team.
            }
            outputStr += "\n";
        }
        System.out.println(outputStr);
    }
}
