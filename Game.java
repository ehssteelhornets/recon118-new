
import java.util.Set;
import java.util.TreeSet;
//A game is a collection of 4 matches with the same game numbers
public class Game
{
    private int gameNum;
    private int blueScore;
    private int redScore;
    private Set<Match> matches = new TreeSet<Match>();
    public Game(int gameNum)
    {

    }

    public int getBlueScore()
    {
        return blueScore;
    }

    public int getRedScore()
    {
        return redScore;
    }

    public String getMatchScore()
    {
        return blueScore + " - " + redScore;
    }
}
