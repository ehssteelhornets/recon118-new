 
import java.util.Comparator;
public class sortByWin implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return b.wins - a.wins;}
}
