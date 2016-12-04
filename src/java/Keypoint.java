
import org.json.JSONObject;

public class Keypoint {
    private int objectID;
    private int keypointID;
    private double x;
    private double y;
    private double size;
    private double angle;
    private double response;
    private int octave;
    private int classID;
    
    public Keypoint(int objectID,int keypointID, double x, double y, double size, double angle, 
            double response, int octave, int classID) {
        
        this.objectID = objectID;
        this.keypointID = keypointID;
        this.x = x;
        this.y = y;
        this.size = size;
        this.angle = angle;
        this.response = response;
        this.octave = octave;
        this.classID = classID;
               
    }

    Keypoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return objectID;
    }

    public void setId(int id) {
        this.objectID = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(Float angle) {
        this.angle = angle;
    }

    public double getResponse() {
        return response;
    }

    public void setResponse(Float response) {
        this.response = response;
    }

    public int getOctave() {
        return octave;
    }
    public int keypointID () {
        return keypointID;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }
    
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("x", getX());
        json.put("y", getY());
        json.put("size", getSize());
        json.put("angle", getAngle());
        json.put("response", getResponse());
        json.put("octave", getOctave());
        json.put("classID", getClassID());
        
        return json;
    }

}
