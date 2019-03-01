 

 

import java.util.Comparator;
public class sortByAverageTeleop implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return b.getMatchTeleopAverage() - a.getMatchTeleopAverage();
    }
}