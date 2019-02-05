package recon118;
import java.util.Comparator;
public class sortByAuto implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return b.totalAutoScore - a.totalAutoScore;
    }
}
