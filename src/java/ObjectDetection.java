
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ObjectDetection extends HttpServlet {

    DatabaseManager dbManager;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        dbManager = new DatabaseManager();
        dbManager.connectToDB();

        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print(getAllObjects());
        
        PrintWriter out = new PrintWriter("filename.txt");
        out.write(getAllObjects().toString());

    }

    public  JSONObject getAllObjects() {
        //wybieramy wszystkie przedmioty
        ArrayList<RecognisedObject> objects = dbManager.selectObjects();
        //tworzymy jsona
        JSONObject bigJSON = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        //dla każdego obiektu pobieramy jego keypointsy z odpowiednim id
        for (RecognisedObject object : objects) {
            object.keypointsArray = dbManager.selectKeypointsWhereId(object.getObjectID());
            jsonArray.put(object.toJSON());
            bigJSON.put("Przedmioty", jsonArray);
        }
        return bigJSON;
    }
    
    

}
