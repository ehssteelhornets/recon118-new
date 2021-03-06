 


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
    private LinkedList<Integer> redTeams = new LinkedList<Integer>();
    private LinkedList<Integer> blueTeams = new LinkedList<Integer>();
    public Game(int gameNum, LinkedList matches)
    {
        this.gameNum = gameNum;
        this.matches = matches;
        runGame();
    }

    public void runGame()
    {
        for(Match m: matches)
        {
            switch (m.allianceColor) {
                case "blue": blueGame(m);break;
                case "red": redGame(m);break;
            }
        }
        if (getBlueScore() > getRedScore()) {
            for (Integer num: blueTeams) {
                Spreadsheets.teams.get(Spreadsheets.teamContains(num)).addWin();
            }
            for (Integer num: redTeams) {
                Spreadsheets.teams.get(Spreadsheets.teamContains(num)).addLoss();
            }
        } else if (getRedScore() > getBlueScore()) {
            for (Integer num: redTeams) {
                Spreadsheets.teams.get(Spreadsheets.teamContains(num)).addWin();
            }
            for (Integer num: blueTeams) {
                Spreadsheets.teams.get(Spreadsheets.teamContains(num)).addLoss();
            }
        }
    }

    private void blueGame(Match m)
    {
        blueScore += m.scoreTotal;
        blueTeams.add(m.teamNumber);
    }

    private void redGame(Match m)
    {
        redScore += m.scoreTotal;
        redTeams.add(m.teamNumber);
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
        return getBlueScore() + " - " + getRedScore();
    }

    public String toString()
    {
        String output = "\t" + getMatchScore() + "\n";
        for(Match m:matches)
        {
            output += m.toString() + "\n\n";
        }
        return output;
    }
}
