 

   
import java.util.Comparator;
public class sortByAveragePoints implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return b.getMatchTotalAverage() - a.getMatchTotalAverage();
    }
}
