
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/*
Klasa pozwalaja na polaczenie i wykonywanie operacji create, insert,select na 
bazie danych
*/
public class DatabaseManager {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:/Users/Beata-MacBook/Desktop/"
            + "ObjectDetectionServer2/przedmioty.db";

    private Connection conn;
    private Statement stat;
    
    /*
    Funkcja pozwalajaca na polaczenie z baza danych
    */
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
    /*
    Metoda tworząca tabele
    */
    public boolean createTables() {
        String createPrzedmioty = "CREATE TABLE IF NOT EXISTS objects"
                + " (OBJECTID INT PRIMARY KEY     NOT NULL,"
                + " OBJECTNAME      TEXT    NOT NULL, "
                + " ROOMNAME   TEXT    NOT NULL)";

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
    /*
    Metoda zwracająca tablicę obiektów typu RecognisedObjects z bazy danych
    */
    public ArrayList<RecognisedObject> selectObjects() {
        ArrayList<RecognisedObject> objects = new ArrayList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM objects");
            int id;
            String objectname, roomname;

            while (result.next()) {
                id = result.getInt("objectid");
                objectname = result.getString("objectname");
                roomname = result.getString("roomname");
                objects.add(new RecognisedObject(id, objectname, roomname));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objects;
    }
    
    /*
    Metoda zwracają tablice obiektów typu Keypoint, które należą do określonego 
    przedmiotu o id
    */
    public ArrayList<Keypoint> selectKeypointsWhereId(int id) {

        ArrayList<Keypoint> keypoints = new ArrayList<>();

        try {
            String sql  ="SELECT * FROM keypoints WHERE objectid = " + String.valueOf(id);
            ResultSet resultKeypoints = stat.executeQuery(sql);

            while (resultKeypoints.next()) {
                int objectID = resultKeypoints.getInt("objectid");
                int keypointID = resultKeypoints.getInt("id");
                double x = resultKeypoints.getDouble("x");
                double y = resultKeypoints.getDouble("y");
                double size = resultKeypoints.getDouble("size");
                double angle = resultKeypoints.getDouble("angle");
                double response = resultKeypoints.getDouble("response");
                int octave = resultKeypoints.getInt("octave");
                int classID = resultKeypoints.getInt("classid");

                keypoints.add(new Keypoint(objectID, keypointID, x, y,
                        size, angle, response, octave, classID));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return keypoints;
    }
   
   /*
    Metoda pozwalająca na zapisanie w bazie przedmiotów
    */ 
    public void insertObject(String name, String localisation) {
        try {
            
            String insertSQL = "INSERT into objects (objectname, roomname) "
                    + "values ('"+ name +"', '"+localisation+"')";
            stat.execute(insertSQL);
            
            System.out.println("\nDodano: " + name + " lokalizacja: "+ localisation); 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
    Metoda pozwalająca na zapisanie w bazie punktów charakterystycznych
    */
    public void insertKeypoint(String objectName, Double x, Double y, Double size, 
            Double angle, Double response, int octave, int classid) {
        try {
            String insertSQL = "Insert into keypoints(objectid,x,y,size,angle,response,octave,classid) "
                    + "values ((SELECT objectid from objects where objectname='"+ objectName +"'),"
                    +String.valueOf(x)+ ","+ String.valueOf(y)+ ","+String.valueOf(size)
                    + ","+ String.valueOf(angle)+ ","+ String.valueOf(response) 
                    + ","+ String.valueOf(octave)+ ","+ String.valueOf(classid)+")";
            
            stat.execute(insertSQL);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
