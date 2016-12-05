
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
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Beata-MacBook
 */

public class ObjectDetection extends HttpServlet {
    
    DatabaseManager dbManager;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
     dbManager = new DatabaseManager();
     dbManager.connectToDB();
     
    
    response.setContentType("application/json");
    
    Keypoint keypoint1 = new Keypoint(1,1,235.0 ,192.0 , 31.0 , 173.09, 0.001567, 0 , -1);
    Keypoint keypoint2 = new Keypoint(1,1,235.0 ,192.0 , 31.0 , 173.09, 0.001567, 0 , -1);
    Keypoint keypoint3 = new Keypoint(1,1,235.0 ,192.0 , 31.0 , 173.09, 0.001567, 0 , -1);
    
    recognisedObject obraz = new recognisedObject();
    obraz.setObjectID(4);
    obraz.setLocalisation("salon");
    obraz.setName("krzeslo");
    
    for (Keypoint keypoint : dbManager.selectKeypointsWhereId(obraz.getObjectID())) {
        obraz.addToArray(keypoint);
    }
    
        
    PrintWriter writer = response.getWriter();
         // Wyświetlamy dane użytkownikowi
       
    //writer.print(obraz.toJSON());
    writer.print(obraz.toJSON());
    
    }
    
    
    
    
   
}
