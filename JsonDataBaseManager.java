package lab7;


import org.json.JSONArray;
import org.json.JSONObject;

public class JsonDataBaseManager {
    
    private String usersPath;
    private String coursesPath;
    
    public JsonDataBaseManager(String usersPath, String coursesPath){
        this.usersPath = usersPath;
        this.coursesPath = coursesPath;
    }
    
    public JSONArray loadUsers() {
        return null;
    }
    public JSONArray loaadCourses() {
        return null;
    }
    
    public void saveUsers(JSONArray Users) {
        
    }
    public void saveCourses(JSONArray Courses) {
        
    }
    
    
}
