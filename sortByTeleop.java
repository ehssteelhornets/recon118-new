import java.util.Comparator;
public class sortByTeleop implements Comparator<Team>
{
    public int compare(Team a,Team b)
    {
        return a.totalTeleopScore -b.totalTeleopScore;
    }
}