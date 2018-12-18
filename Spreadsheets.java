 

 

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
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
        for (List<Object> a: cells) {
            for (Object b: a) {
                System.out.print(b);
                System.out.print("\t");
            }
            System.out.println();
        }
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
    /*
    public static void createNewSheet(final String sheetID, Color color) {
        List<Request> request = new ArrayList<Request>();
        request.add(new Request().setAddSheet(new AddSheetRequest().setProperties(new SheetProperties()
            .setTitle(sheetID)
            .setGridProperties(new GridProperties().setRowCount(1000).setColumnCount(26))
            .setTabColor(color))));
        try {
            service.spreadsheets().batchUpdate(s, new BatchUpdateSpreadsheetRequest().setRequests(request)).execute(); 
            System.out.println("Created sheet " + sheetID + "\n");
        } catch (Exception e) {throw new RuntimeException(e);}
    }
    
    private static void deleteSheet(final String sheetID) {
        try {
            service.spreadsheets().batchUpdate(spreadsheetId[1], new BatchUpdateSpreadsheetRequest().setRequests(
                Collections.singletonList(new Request().setDeleteSheet(new DeleteSheetRequest()
                .setSheetId(Integer.parseInt((String)readCells(sheetID+"!Z1:Z1",1).get(0).get(0)))))))
                .execute();
            System.out.println("Sheet " + sheetID + " deleted");
        } catch (Exception e) {throw new RuntimeException(e);}
    }
    
    public static void copySheet(int from, int IDFrom, int to, String newName, Color color) {
        try {
            //copy the sheet
            CopySheetToAnotherSpreadsheetRequest requestBody = new CopySheetToAnotherSpreadsheetRequest();
            requestBody.setDestinationSpreadsheetId(spreadsheetId[to]);
            SheetProperties newSheetProperties = service.spreadsheets().sheets().copyTo(spreadsheetId[from], IDFrom, requestBody).execute();
            
            //change the name and tab color
            List<Request> request = Collections.singletonList(new Request().setUpdateSheetProperties(new UpdateSheetPropertiesRequest()
                .setProperties(newSheetProperties.setTitle(newName).setTabColor(color)).setFields("title,tabColor")));
            service.spreadsheets().batchUpdate(spreadsheetId[1], new BatchUpdateSpreadsheetRequest().setRequests(request)).execute();
            
            //write the sheetID in cell Z1 for easy access
            service.spreadsheets().values().update(spreadsheetId[to], newName + "!Z1:Z1", new ValueRange().setValues(
                Collections.singletonList(Collections.singletonList(Integer.toString(newSheetProperties.getSheetId())))))
                .setValueInputOption("RAW")
                .execute();
            
            System.out.println("Copied sheet from " + spreadsheetName[from] + " to " + spreadsheetName[to] + " with the name " + newName + "\n");
        } catch (Exception e) {throw new RuntimeException();}
    }
    
    public static boolean sheetExists(String sheetID) {
        try {
            //try to read values from sheet if the sheet does not exist then GoogleJsonResponseException is thrown
            ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId[1], sheetID+"!A1:C3")
                .execute();
        } catch (GoogleJsonResponseException e) {return false;}
        catch (Exception e) {throw new RuntimeException(e);}
        return true;
    }*/
}