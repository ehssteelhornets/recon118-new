 

import java.util.Comparator;
public class sortByTeleop implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return b.totalTeleopScore - a.totalTeleopScore;
    }
}