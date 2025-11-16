package lab7.jolie;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        JSONArray arr = null;
        try {
            String loadedContent = new String(Files.readAllBytes(Paths.get("users.json")));
            arr = new JSONArray(loadedContent);
            for (int i = 0 ; i < arr.length() ; i++ ) {
                JSONObject newUser = arr.getJSONObject(i);
//                String username = newUser.getString("username");
//                String email = newUser.getString("email");
            }
        } catch (IOException e) {
            System.out.println("Error , Could not Load Users from file users.json.");
        }
        return arr;
    }
    public JSONArray loaadCourses() {
        JSONArray arr = null;
        try {
            String loadedContent = new String(Files.readAllBytes(Paths.get("courses.json")));
            arr = new JSONArray(loadedContent);
            for (int i = 0 ; i < arr.length() ; i++ ) {
                JSONObject newCourse = arr.getJSONObject(i);
//                String username = newCourse.getString("username");
//                String email = newCourse.getString("email");
            }
        } catch (IOException e) {
            System.out.println("Error , Could not Load Users from file courses.json.");
        }
        return arr;
    }
    
    public void saveUsers(JSONArray Users) {
        
    }
    public void saveCourses(JSONArray Courses) {
        
    }
    
    public User parseUser(JSONObject J) {
        User user = new User();
        return user;
    }
    public Student parseStudent(JSONObject J) {
        Student student = new Student();
        return student;
    }
    public Instructor parseInstructor(JSONObject J) {
        Instructor instructor = new Instructor();
        return instructor;
    }
    public Course parseCourse(JSONObject J) {
        Course course = new Course();
        return course;
    }
    public Lesson parseLesson(JSONObject J) {
        Lesson lesson = new Lesson();
        return lesson;
    }
    
    public JSONObject toJson(User user) {
        JSONObject J = new JSONObject();
        return J;
    }
    
    public JSONObject toJson(Student student) {
        JSONObject J = new JSONObject();
        return J;
    }
    
    public JSONObject toJson(Instructor imstructor) {
        JSONObject J = new JSONObject();
        return J;
    }
    
    public JSONObject toJson(Course course) {
        JSONObject J = new JSONObject();
        return J;
    }
    
    public JSONObject toJson( Lesson lesson) {
        JSONObject J = new JSONObject();
        return J;
    }
    
    public String generateId() {
        return UUID.randomUUID().toString();
    }

}
