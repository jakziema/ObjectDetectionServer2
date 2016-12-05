
import java.util.ArrayList;
import org.json.JSONArray;
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
public class recognisedObject {
    
    public int objectID;
    public String localisation;
    public String name;
    ArrayList<Keypoint> keypointsArray = new ArrayList<Keypoint>();
    
    recognisedObject() {}

    recognisedObject(int id, String name, String localisation) {
        this.objectID = id;
        this.localisation = localisation;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int id) {
        this.objectID = id;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public ArrayList<Keypoint> getKeypointsArray() {
        return keypointsArray;
    }

    public void setKeypointsArray(ArrayList<Keypoint> keypointsArray) {
        this.keypointsArray = keypointsArray;
    }
    
    public void addToArray(Keypoint keypoint){
        keypointsArray.add(keypoint);
    }

    
    
    

    
    public JSONObject toJSON() {
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getObjectID());
        jsonObject.put("lokalizacja", getLocalisation());
        jsonObject.put("name", getName());
        JSONArray jsonArray = new JSONArray();
        for (Keypoint item: keypointsArray ){
            jsonArray.put(item.toJSON());
        }
        
        jsonObject.put("keypoints", jsonArray);
        
        
        return jsonObject;
    }
     
    
}
