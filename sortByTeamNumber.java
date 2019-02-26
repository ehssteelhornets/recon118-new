 

import java.util.Comparator;
public class sortByTeamNumber implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return a.teamNumber - b.teamNumber;
    }
}
