
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;
//A game is a collection of 4 matches with the same game numbers
public class Game
{
    private int gameNum;
    private int blueScore;
    private int redScore;
    private LinkedList<Match> matches = new LinkedList<Match>();
    private LinkedList<Team> gameplayTeams = new LinkedList<Team>();
    public Game(int gameNum, LinkedList matches, LinkedList<Team> gameplayTeams)
    {
        this.gameNum = gameNum;
        this.matches = matches;
        this.gameplayTeams = gameplayTeams;
    }

    public void runGame()
    {
        for(Match m: matches)
        {
            if(m.getColor() == "blue")
                blueGame(m);
            else
                redGame(m);
        }
    }

    private void blueGame(Match m)
    {
        blueScore += m.scoreTotal;
    }

    private void redGame(Match m)
    {
        redScore += m.scoreTotal;
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
