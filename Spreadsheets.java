  

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;
//Add libraries
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.DeleteSheetRequest;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.GridProperties;
import com.google.api.services.sheets.v4.model.Color;
import com.google.api.services.sheets.v4.model.CopySheetToAnotherSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.UpdateSheetPropertiesRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class Spreadsheets {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final String spreadsheetID = "1peZKQGBokN1ILdY9Hqhx8x9HzCtHHczAfVjfkdjVLjU";

    private static Sheets service = null;
    
    public static LinkedList<Team> teams;
    
    public static int occurances;
    
    public static void insertSorugateMatches() {
        Scanner scan = new Scanner(System.in);
        String data;
        do {
            data = scan.nextLine().toUpperCase();
            if (!data.equals("STOP")) {
                int matchNum = Integer.parseInt(data.substring(0,data.indexOf(" ")+1));
                int teamNum = Integer.parseInt(data.substring(data.indexOf(" "),data.length()));
                initialize();
                List<List<Object>> cells = readCells("Sheet1!2:1000");
                boolean cont = true;
                int row = 0;
                while (cont) {
                    if (Integer.parseInt((String)cells.get(row).get(1))==matchNum) {
                        if (Integer.parseInt((String)cells.get(row).get(2))==teamNum) {
                            ArrayList<ArrayList<Object>> newText = new ArrayList<ArrayList<Object>>();
                            ArrayList<Object> line = new ArrayList<Object>();
                            line.add("skip");
                            newText.add(line);
                            //writeCells(
                        } else {
                            row += 1;
                        }
                    } else {
                        row += 4;
                    }
                }
            }
        } while (!data.equals("STOP"));
    }

    public static void main() {
        initialize();
        occurances = 0;
        List<List<Object>> cells = readCells("Sheet1!2:1000");
        teams = new LinkedList<Team>();
        //keep every four matches grouped together to run the game class
        LinkedList<Match> matchGroupings = new LinkedList<Match>();
        int matches = 0;
        for (List<Object> row: cells) {
            Team t = new Team("",Integer.parseInt((String)row.get(2)));
            LinkedList<String> autoTasks = new LinkedList<String>();
            //Ethan add array seperation code here
            String[] autonomous = ((String)row.get(7)).split(", ",0);
            autoTasks.addAll(Arrays.asList(autonomous));
            Match m = null;
            m = new Match(Integer.parseInt((String)row.get(1)),Integer.parseInt((String)row.get(2)),(String)row.get(3),(String)row.get(4),(String)row.get(5),(String)row.get(6),autoTasks,Integer.parseInt((String)row.get(8)),Integer.parseInt((String)row.get(9)),Integer.parseInt((String)row.get(10)),(String)row.get(11),(String)row.get(11));
            if (teamContains(Integer.parseInt((String)row.get(2)))==-1) {
                teams.add(new Team("",Integer.parseInt((String)row.get(2))));
                teams.getLast().addMatch(m);
            }
            else
            {
                teams.get(teamContains((Integer.parseInt((String)row.get(2))))).addMatch(m);
            }
            matchGroupings.add(m);
            matches++;
            if (matches >= 4) {
                Game g = new Game(matchGroupings.get(0).teamNumber,matchGroupings);
                for(Match match:matchGroupings)
                {
                    teams.get(teamContains(match.teamNumber)).addGame(g);
                }
                matchGroupings = new LinkedList<Match>();
                matches = 0;
            }
        }
        Rankings r = new Rankings("",teams);
        r.getHighestTeleop();
        r.getTopScorer();
        r.getAverageTopScorer();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter team you would like to audit");
        Auditor a = new Auditor();
        a.auditTeam(scan.nextInt(),teams);
    }

    public static int teamContains(int teamNum) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamNum() == teamNum) {
                return i;
            }
        }
        return -1;
    }

    private static void initialize() {
        //Build a new authorized API service
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
        } catch (Exception e) {throw new RuntimeException(e);}
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = Spreadsheets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        List<String> SCOPES = new ArrayList<String>();
        //SCOPES.add(GmailScopes.MAIL_GOOGLE_COM);
        SCOPES.add(SheetsScopes.SPREADSHEETS);
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     */
    public static List<List<Object>> readCells(final String range){
        try {
            ValueRange response = service.spreadsheets().values()
                .get(spreadsheetID, range)
                .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            }
            return values;
        } catch (Exception e) {throw new RuntimeException(e);}
    }
}
 