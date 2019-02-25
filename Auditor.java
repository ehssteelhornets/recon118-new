package recon118;
import java.util.LinkedList;
public class Auditor
{
    private LinkedList<Game> games = new LinkedList<Game>();
    public Auditor()
    {

    }

    public void auditTeam(int teamNumber, LinkedList<Team> teams)
    {
        Team temporary = teams.get(teamContains(teams,teamNumber));
        System.out.println(temporary.toString() + "\n");
        System.out.println("\t Matches");
        for(Game g : games)
        {
            System.out.println("\t\t" + g.toString());
        }
    }

    private static int teamContains(LinkedList<Team> teams, int teamNum) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamNum() == teamNum) {
                return i;
            }
        }
        return 0;
    }
}
