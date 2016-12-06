
import org.json.JSONArray;
import org.json.JSONObject;

/*
Klasa pomocnicza z funkcjami do parsowania plików w formacie JSON
 */
public class JSONUtils {

    /*
    Metoda pozwalająca na parsowanie zeskanowaynch przez uzytkownika obiektow.
    Pozwala na zapisanie ich w bazie
     */
    public void insertObjectsFromJSONAsString(String jsonAsString, DatabaseManager dbManager) {
        // utworz obiekt typu JSONObject ze obiektu typu String
        JSONObject receivedJSON = new JSONObject(jsonAsString);

        //pobierz wartość pod kluczem: "nazwa"
        String name = receivedJSON.getString("nazwa");
        //pobierz wartość pod kluczem: "lokalizacja"
        String localisation = receivedJSON.getString("lokalizacja");
        //zapisz w bazie
        dbManager.insertObject(name, localisation);
    }

    /*
    Metoda pozwalają na parsowanie punktów charakterystycznych z pliku JSON i 
    zapisanie ich do bazy
     */
    public void insertKeypointsFromJSONAsString(String jsonAsString,
            DatabaseManager dbManager) {

        //utworz obiekty typu JSONObject z obiektu typu String
        JSONObject receivedJSON = new JSONObject(jsonAsString);
        // pobierz wartość pod kluczem "nazwa"
        String name = receivedJSON.getString("nazwa");
        // utworz JSONArray znajdujacy się pod kluczem "keypoints"
        JSONArray keypointsArray = receivedJSON.getJSONArray("keypoints");

        //iteracja kadzego elementu w tabeli
        for (int i = 0; i < keypointsArray.length(); i++) {

            // wyciagnij jeden punkt charakerystyczny z tablicy i pobierz jego 
            //wartosci
            JSONObject jsonLineItem = keypointsArray.getJSONObject(i);
            Double x = jsonLineItem.getDouble("x");
            Double y = jsonLineItem.getDouble("y");
            Double size = jsonLineItem.getDouble("size");
            Double angle = jsonLineItem.getDouble("angle");
            Double reponse = jsonLineItem.getDouble("response");
            int octave = jsonLineItem.getInt("octave");
            int classid = jsonLineItem.getInt("class_id");
            //zapisz w bazie
            dbManager.insertKeypoint(name, x, y, size, angle, reponse, octave, classid);

        }
    }
}
