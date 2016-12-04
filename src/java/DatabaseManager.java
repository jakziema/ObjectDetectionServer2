
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Beata-MacBook
 */
public class DatabaseManager {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:/Users/Beata-MacBook/Desktop/"
            + "ObjectDetectionServer2/przedmioty.db";
    
    private Connection conn;
    private Statement stat;
    
    public void connectToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.print("Polaczono z  baza");
            
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
    }
    
    
        public boolean createTables()  {
        String createPrzedmioty = "CREATE TABLE IF NOT EXISTS przedmioty" 
                    + " (OBJECTID INT PRIMARY KEY     NOT NULL,"
                    + " OBJECTNAME      REAL    NOT NULL, "
                    + " ROOMNAME   REAL    NOT NULL)";
        
        String createKeypoints = "CREATE TABLE IF NOT EXISTS keypoints" 
                    + " (ID INT PRIMARY KEY     NOT NULL,"
                    + " OBJECTID INT NOT NULL, "
                    + " X       REAL    NOT NULL, "
                    + " Y       REAL    NOT NULL, "
                    + " SIZE    REAL    NOT NULL,"
                    + " ANGLE   REAL NOT NULL ,"
                    + "RESPONSE REAL NOT NULL,"
                    + "OCTAVE INT NOT NULL,"
                    + " CLASSID INT NOT NULL)";
        try {
            stat.execute(createPrzedmioty);
            stat.execute(createKeypoints);
            System.out.print("Stworzono tabele");
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }
        
    
    
    public ArrayList<recognisedObject> selectObjects() {
        ArrayList<recognisedObject> objects = new ArrayList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM objects");
            int id;
            String objectname, roomname;
            
            while(result.next()) {
                id = result.getInt("objectid");
                objectname = result.getString("objectname");
                roomname = result.getString("roomname");
                objects.add(new recognisedObject(id,objectname));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return objects;
    }
        
   
    
}
