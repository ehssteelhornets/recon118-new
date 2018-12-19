import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private static final String[] spreadsheetName = {"ContactInfo", "MainSchedule", "Sample"};

    private static Sheets service = null;

    public static void main() {
        initialize();
        List<List<Object>> cells = readCells("Sheet1");
        /*
        for (List<Object> a: cells) {
        for (Object b: a) {
        System.out.print(b);
        System.out.print("\t");
        }
        System.out.println();
        }*/
        LinkedList<Team> teams = new LinkedList<Team>();
        for (List<Object> row: cells) {
            if (teamContains(teams,(int)row.get(2))==-1) 
                teams.add(new Team("",(int)row.get(2)));

        }
    }

    private static int teamContains(LinkedList<Team> teams, int teamNum) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getNum()==teamNum) {
                return i;
            }
        }
        return -1;
    }

    public static void initialize() {
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

            System.out.println("Read range |"+range+"| of " + spreadsheetID + " successfully");
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            }
            System.out.println();
            return values;
        } catch (Exception e) {throw new RuntimeException(e);}
    }

    /**
     * Updates Cells of range with values
     */
    public static void writeCells(final String range, final List<List<Object>> values) {
        try {
            ValueRange body = new ValueRange()
                .setValues(values);

            service.spreadsheets().values().update(spreadsheetID, range, body)
            .setValueInputOption("RAW")
            .execute();

            //System.out.println("Updated Cells of " +  + "/" + range.substring(0,range.indexOf("!")) + ":");
            char startCellLetter = range.charAt(range.indexOf("!")+1);
            String startCellNum = range.substring(range.indexOf("!")+2,range.indexOf(":"));
            for (int r = 0; r < values.size(); r++) {
                List<Object> row = values.get(r);
                String cellNum = Integer.toString(Integer.parseInt(startCellNum)+r);
                for (int c = 0; c < row.size(); c++) {
                    char cellLetter = (char)((int)startCellLetter+c);
                    System.out.println("Wrote \"" + row.get(c) + "\" to cell " + cellLetter + cellNum);
                }
            }
            System.out.println("\n");
        } catch (Exception e) {throw new RuntimeException(e);}
    }
    private static class Team
    {
        //Initilizable values
        private String name;
        private int teamNumber;
        //Storage Values
        private int totalScore;
        private int penaltylessScore;
        private int wins;
        private int losses;
        public Team(String name, int teamNumber)
        {
            this.name = name;
            this.teamNumber = teamNumber;
        }

        public void addScore(int gameScore, int penaltys)
        {
            totalScore += gameScore;
            penaltylessScore += gameScore - penaltys;
        }

        public String toString()
        {
            return "Team " + teamNumber + "," + name;
        }

        public void addWin()
        {
            wins++;
        }

        public void addLoss()
        {
            losses++;
        }

        public int getNum() {
            return teamNumber;
        }
    }
    private class Rankings
    {
        private Comparator sorter = null;
        private LinkedList teams = null;
        public Rankings(String sortType, LinkedList teams)
        {
            this.teams = teams;
            //Fill in a comparator
            this.sorter = null;
        }

        public void sort(String sortType)
        {
            Collections.sort(teams,sorter);
        }
    }
}