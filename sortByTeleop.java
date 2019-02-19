package recon118;

import java.util.Comparator;
public class sortByTeleop implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return b.getMatchTeleopAverage() - a.getMatchTeleopAverage();
    }
}