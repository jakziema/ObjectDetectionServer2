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

/*
Klasa obslugująca żądanie typu POST
*/
public class SendKeypoints extends HttpServlet {

    DatabaseManager dbManager;
    JSONUtils jsonUtils;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        dbManager = new DatabaseManager();
        //połącz z bazą danych
        dbManager.connectToDB();
        
        jsonUtils = new JSONUtils();
        // pobierz wartość parametru "Przedmioty". Jest to JSON jako string
        String jsonAsString = request.getParameter("Przedmioty");
        //zapisz przedmiot w bazie
        jsonUtils.insertObjectsFromJSONAsString(jsonAsString, dbManager);
        //zapisz punkty charakterystyczne w bazie danych
        jsonUtils.insertKeypointsFromJSONAsString(jsonAsString, dbManager);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
