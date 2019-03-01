 

import java.util.Comparator;
public class sortByPoints implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return b.getTotalPoints() - a.getTotalPoints();
    }
}
