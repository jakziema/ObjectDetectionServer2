/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Beata-MacBook
 */
public class SendKeypoints extends HttpServlet {

    DatabaseManager dbManager;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        dbManager = new DatabaseManager();
        dbManager.connectToDB();
        
        String jsonAsString = request.getParameter("Przedmioty");
        insertObjectsFromJSONAsString(jsonAsString, dbManager);
        insertKeypointsFromJSONAsString(jsonAsString, dbManager);
        
        

        

    }
    
    public void insertObjectsFromJSONAsString(String jsonAsString, DatabaseManager dbManager) {
        JSONObject receivedJSON = new JSONObject(jsonAsString);

        String name = receivedJSON.getString("nazwa");
        String localisation = receivedJSON.getString("lokalizacja");
        dbManager.insertObject(name, localisation);
    }
    
    public void insertKeypointsFromJSONAsString(String jsonAsString, DatabaseManager dbManager) {
        
        JSONObject receivedJSON = new JSONObject(jsonAsString);
        String name = receivedJSON.getString("nazwa");
        JSONArray keypointsArray = receivedJSON.getJSONArray("keypoints");
        for (int i = 0; i < keypointsArray.length(); i++) {

            JSONObject jsonLineItem = keypointsArray.getJSONObject(i);
            Double x = jsonLineItem.getDouble("x");
        Double y = jsonLineItem.getDouble("y");
            Double size = jsonLineItem.getDouble("size");
            Double angle = jsonLineItem.getDouble("angle");
            Double reponse = jsonLineItem.getDouble("response");
            int octave = jsonLineItem.getInt("octave");
            int classid = jsonLineItem.getInt("class_id");

            dbManager.insertKeypoint(name, x, y, size, angle, reponse, octave, classid);

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
